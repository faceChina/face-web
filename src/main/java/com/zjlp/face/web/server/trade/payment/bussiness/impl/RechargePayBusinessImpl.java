package com.zjlp.face.web.server.trade.payment.bussiness.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.account.dto.DivideCommissions;
import com.zjlp.face.account.dto.Receivables;
import com.zjlp.face.account.dto.Wallet;
import com.zjlp.face.account.exception.AmountException;
import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.account.service.BindPayService;
import com.zjlp.face.account.service.PaymentService;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.date.DateStyle;
import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.PayException;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketOrderDetailDto;
import com.zjlp.face.web.server.operation.marketing.producer.MarketingProducer;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.producer.MemberProducer;
import com.zjlp.face.web.server.trade.account.producer.AccountProducer;
import com.zjlp.face.web.server.trade.payment.bussiness.RechargePayBusiness;
import com.zjlp.face.web.server.trade.payment.domain.CommissionRecord;
import com.zjlp.face.web.server.trade.payment.domain.MemberTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.TransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.WalletTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.WechatTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.DistributeDto;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;
import com.zjlp.face.web.server.trade.payment.domain.dto.MemberTransactionRecordDto;
import com.zjlp.face.web.server.trade.payment.domain.dto.WXNotice;
import com.zjlp.face.web.server.trade.payment.domain.vo.WechatPayVo;
import com.zjlp.face.web.server.trade.payment.scene.distribute.DistributeCalculation;
import com.zjlp.face.web.server.trade.payment.scene.fee.FeeCalculation;
import com.zjlp.face.web.server.trade.payment.service.CommissionRecordService;
import com.zjlp.face.web.server.trade.payment.service.MemberTransactionRecordService;
import com.zjlp.face.web.server.trade.payment.service.TransactionRecordService;
import com.zjlp.face.web.server.trade.payment.service.WalletTransactionRecordService;
import com.zjlp.face.web.server.trade.payment.service.WechatTransactionRecordService;
import com.zjlp.face.web.server.trade.payment.util.WXPayUtil;
import com.zjlp.face.web.server.trade.recharge.domain.Recharge;
import com.zjlp.face.web.server.trade.recharge.producer.RechargeProducer;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.bankcard.producer.BankCardProducer;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;

@Service
public class RechargePayBusinessImpl implements RechargePayBusiness {
	private static final String MESSAGE = "系统异常，请稍后重试";
	private static final Integer RECHARGE_WAY_BANKCARD = 1;
	private static final Integer RECHARGE_WAY_WALLET = 2;
	private static final Integer RECHARGE_WAY_WECHAT = 3;
	
	private Logger _InfoLogger = Logger.getLogger(this.getClass());
	private Logger _walletErrorLogger = Logger.getLogger("orderWalletErrorLog");
    private Logger _wechatErrorLogger = Logger.getLogger("orderWechatErrorLog");
	
    //钱包交易记录
	@Autowired
	private WalletTransactionRecordService walletTransactionRecordService;
	
	//微信交易记录
	@Autowired
	private WechatTransactionRecordService wechatTransactionRecordService;
	
	//会员卡交易记录
	@Autowired
	private MemberTransactionRecordService memberTransactionRecordService;
	
	//金额分配记录
	@Autowired
	private CommissionRecordService commissionRecordService;
	
	//会员卡
	@Autowired
	private MemberProducer memberProducer;
	
	//充值订单
	@Autowired
	private RechargeProducer rechargeProducer;
	
	//账户接口
	@Autowired
	private AccountProducer accountProducer;

	//店铺接口
	@Autowired
	private ShopProducer shopProducer;
	
	//营销接口
	@Autowired
	private MarketingProducer marketingProducer;
	
	@Autowired(required=false)
	private PaymentService paymentService;
	
	@Autowired
	private BankCardProducer bankCardProducer;
	
	@Autowired(required=false)
	private BindPayService bindPayService;
	
	@Autowired
	private TransactionRecordService transactionRecordService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	public WalletTransactionRecord paymentConsumerByWallet(String vaildateCode, String rechargeNo) throws PayException {
		_InfoLogger.info("【钱包支付】充值会员卡开始..");
		Assert.hasLength(vaildateCode,"参数[vaildateCode]为空");
		Assert.hasLength(rechargeNo, "参数[rechargeNo]为空");
		String baseLog = new StringBuffer("【钱包支付】充值会员卡[").append(rechargeNo).append("] ").toString();
		try {
			Date date = new Date();
			
			//1.1查询充值订单
			Recharge recharge = rechargeProducer.getRechargeByRechargeNo(rechargeNo);
			AssertUtil.notNull(recharge, new StringBuffer(baseLog).append("没有找到该充值订单").toString(),MESSAGE);
			AssertUtil.notNull(recharge.getUserId(), new StringBuffer(baseLog).append("订单userId为空").toString(),MESSAGE);
			AssertUtil.notNull(recharge.getUserAccount(), new StringBuffer(baseLog).append("订单UserAccount为空").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("验证订单完成").toString());
			
			//1.2验证订单状态(当前订单状态应该为 1.生成/未支付 状态)
			AssertUtil.isTrue(recharge.getStatus().intValue() == Constants.RECHARGE_STATUS_WAIT.intValue(), new StringBuffer(baseLog).append("订单状态异常").toString(),"当前订单不能支付");
			_InfoLogger.info(new StringBuffer(baseLog).append("订单状态验证完成").toString());
			
			//1.3验证订单支付金额，充值金额(金额必须大于零，充值金额=充值金额+赠送金额)
			AssertUtil.isTrue(recharge.getDiscountPrice().longValue() > 0,  new StringBuffer(baseLog).append("订单支付金额异常").toString(),MESSAGE);
			Long rechargePrice = CalculateUtils.getNewVal(recharge.getPrice(),recharge.getGiftPrice());
			AssertUtil.isTrue(rechargePrice > 0, new StringBuffer(baseLog).append("订单充值金额异常").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("订单金额验证完成").toString());
			
			//1.4验证会员卡有效性(根据充值订单中的会员卡ID查询会员卡信息)
			MemberCard memberCard = memberProducer.getMemberCardById(Long.valueOf(recharge.getUserAccount()));
			AssertUtil.notNull(memberCard, new StringBuffer(baseLog).append("没有找到会员卡").toString(),MESSAGE);
			AssertUtil.isTrue(memberCard.getStatus().intValue() == Constants.VALID.intValue(), new StringBuffer(baseLog).append("会员卡状态异常").toString(),"会员卡不可用，请联系客服");
			_InfoLogger.info(new StringBuffer(baseLog).append("会员卡验证完成").toString());
			
			//2.修改订单
			//2.1获取交易流水号
			String transactionSerialNumber = paymentService.getTransactionSerialNumber(recharge.getUserId().toString(), Constants.ORDER_TYPE_RECHARGE);
			AssertUtil.hasLength(transactionSerialNumber, new StringBuffer(baseLog).append("获取交易流水号失败").toString(),MESSAGE);
			//2.2修改订单状态(充值中-充值成功)
			rechargeProducer.editRechargeStatus(rechargeNo, Constants.RECHARGE_STATUS_PAYING);
			rechargeProducer.editRechargeStatus(rechargeNo, Constants.RECHARGE_STATUS_COMPILE);
			//2.3修改订单支付信息(支付方式：钱包支付)
			Recharge editRecharge = new Recharge();
			editRecharge.setRechargeNo(rechargeNo);//订单号
			editRecharge.setRechargeWay(RECHARGE_WAY_WALLET);//支付方式
			editRecharge.setTransactionSerialNumber(transactionSerialNumber);//交易流水号
			editRecharge.setUpdateTime(date);//修改时间
			rechargeProducer.editRecharge(editRecharge);
			_InfoLogger.info(new StringBuffer(baseLog).append("修改订单完成").toString());
			
			//3.金额分配
			DistributeDto distributeDto = _calculationDistribute(recharge,1);
			AssertUtil.notNull(distributeDto, new StringBuffer(baseLog).append("金额分配失败").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("金额分配完成").toString());
			
			//4.会员卡充值
			boolean rechargeFlag = memberProducer.sumMemberAmount(memberCard.getId(), recharge.getPrice(),recharge.getGiftPrice());
			AssertUtil.isTrue(rechargeFlag, new StringBuffer(baseLog).append("调用充值接口失败").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("充值到会员卡完成").toString());
			
			//5.赠送积分
			this._comsuerSendInteger(recharge);
			_InfoLogger.info(new StringBuffer(baseLog).append("赠送积分完成").toString());
			
			//6.调用钱包支付接口
			Wallet wallet = new Wallet();
            wallet.setUserId(recharge.getUserId().toString());
            wallet.setTotalPrice(recharge.getDiscountPrice());
            wallet.setPaymendCode(vaildateCode);
            wallet.setTransactionSerialNumber(transactionSerialNumber);
            boolean payFlag = paymentService.paymentByWallet(wallet);
			AssertUtil.isTrue(payFlag, new StringBuffer(baseLog).append("调用钱包付款接口失败").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("调用钱包支付完成").toString());
			
			//7.累计消费
			boolean sumConsumerFlag = this._sumCardAmountAndInteger(recharge.getRechargeNo());
			AssertUtil.isTrue(sumConsumerFlag, new StringBuffer(baseLog).append("累计消费失败").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("累计消费完成").toString());
			
			//8.金额转入卖家账户
			boolean divideCommissionFlag = this._divideCommissionsToAccount(recharge.getRechargeNo());
			AssertUtil.isTrue(divideCommissionFlag, new StringBuffer(baseLog).append("金额转入卖家账户失败").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("金额转入卖家账户完成").toString());
			
			//9.记录交易
			//9.1钱包交易记录
			WalletTransactionRecord wtr = new WalletTransactionRecord();
            wtr.setUserId(recharge.getUserId());
            wtr.setTransactionSerialNumber(transactionSerialNumber);
            wtr.setCash(recharge.getDiscountPrice());
            wtr.setType(1);
            wtr.setStatus(1);
            wtr.setAccountType(Constants.ORDER_TYPE_SW);
            wtr.setSettleDate(DateUtil.DateToString(DateUtil.getLastTimeForDays(date, 1), DateStyle.YYYYMMDD));
            wtr.setTransactionTime(date);
            wtr.setUpdateTime(date);
            wtr.setCreateTime(date);
            walletTransactionRecordService.addWalletTransactionRecord(wtr);
            //9.2会员卡交易记录(充值记录)
            MemberCard rechargeAftermemberCard = memberProducer.getMemberCardById(memberCard.getId());
            MemberTransactionRecord mtr = new MemberTransactionRecord();
            mtr.setMemberCardId(memberCard.getId());
            mtr.setSellerId(recharge.getSellerId().toString());
            mtr.setTransactionSerialNumber(transactionSerialNumber);
            mtr.setChannel(MemberTransactionRecordDto.CHANNEL_WALLET);//资金流入渠道
            mtr.setType(MemberTransactionRecordDto.TYPE_RECHAGE);
            mtr.setGoodInfo(new StringBuffer("充").append(recharge.getPrice()/100.0).append("送").append(recharge.getGiftPrice()/100.0).toString());
            mtr.setTransPrice(rechargePrice);//交易金额为充值金额
            mtr.setAmount(rechargeAftermemberCard.getAmount());
            mtr.setBeforeAmount(rechargeAftermemberCard.getAmount());
            mtr.setTransTime(date);
            mtr.setTransYear(DateUtil.DateToString(date, "yyyy"));
            mtr.setTransMonth(DateUtil.DateToString(date, "MM"));
            mtr.setCreateTime(date);
            memberTransactionRecordService.addMemberTransactionRecord(mtr);
			_InfoLogger.info(new StringBuffer(baseLog).append("记录交易完成").toString());
			_InfoLogger.info(new StringBuffer(baseLog).append("执行结束").toString());
			return wtr;
		}catch (AmountException e) {
			String str=e.getMessage().substring(e.getMessage().indexOf("{"));
			JSONObject jsonObject=JSONObject.fromObject(str);
			AssertUtil.isTrue(false, jsonObject.getString("err_msg"),jsonObject.getString("err_info"));
			throw new PayException(jsonObject.getString("err_info"));
        }catch (PaymentException e) {
			String str=e.getMessage().substring(e.getMessage().indexOf("{"));
			JSONObject jsonObject=JSONObject.fromObject(str);
			AssertUtil.isTrue(false, jsonObject.getString("err_msg"),jsonObject.getString("err_info"));
			throw new PayException(jsonObject.getString("err_info"));
		} catch (Exception e) {
			_walletErrorLogger.error(new StringBuffer(baseLog).append("发生异常").toString(),e);
			throw new PayException(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	public WechatPayVo paymentWechatOrder(Long userId, String openId,
			String spbillCreateIp, String rechargeNo) {
		Assert.notNull(userId,"参数[userId]为空");
		Assert.hasLength(openId,"参数[openId]为空");
		Assert.hasLength(rechargeNo,"参数[rechargeNo]为空");
		String baseLog = new StringBuffer("【微信下单】充值会员卡[").append(rechargeNo).append("] ").toString();
		try {
			Date date = new Date();
			
			//1.1查询充值订单
			Recharge recharge = rechargeProducer.getRechargeByRechargeNo(rechargeNo);
			AssertUtil.notNull(recharge, new StringBuffer(baseLog).append("没有找到该充值订单").toString(),MESSAGE);
			AssertUtil.notNull(recharge.getUserId(), new StringBuffer(baseLog).append("订单userId为空").toString(),MESSAGE);
			AssertUtil.notNull(recharge.getUserAccount(), new StringBuffer(baseLog).append("订单UserAccount为空").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("验证订单完成").toString());
			
			//1.2验证订单状态(当前订单状态应该为 1.生成/未支付 状态)
			AssertUtil.isTrue(recharge.getStatus().intValue() == Constants.RECHARGE_STATUS_WAIT.intValue(), new StringBuffer(baseLog).append("订单状态异常").toString(),"当前订单不能支付");
			_InfoLogger.info(new StringBuffer(baseLog).append("订单状态验证完成").toString());
			
			//1.3验证订单支付金额，充值金额(金额必须大于零，充值金额=充值金额+赠送金额)
			AssertUtil.isTrue(recharge.getDiscountPrice().longValue() > 0,  new StringBuffer(baseLog).append("订单支付金额异常").toString(),MESSAGE);
			Long rechargePrice = CalculateUtils.getNewVal(recharge.getPrice(),recharge.getGiftPrice());
			AssertUtil.isTrue(rechargePrice > 0, new StringBuffer(baseLog).append("订单充值金额异常").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("订单金额验证完成").toString());
			
			//1.4验证会员卡有效性(根据充值订单中的会员卡ID查询会员卡信息)
			MemberCard memberCard = memberProducer.getMemberCardById(Long.valueOf(recharge.getUserAccount()));
			AssertUtil.notNull(memberCard, new StringBuffer(baseLog).append("没有找到会员卡").toString(),MESSAGE);
			AssertUtil.isTrue(memberCard.getStatus().intValue() == Constants.VALID.intValue(), new StringBuffer(baseLog).append("会员卡状态异常").toString(),"会员卡不可用，请联系客服");
			_InfoLogger.info(new StringBuffer(baseLog).append("会员卡验证完成").toString());
			
			//2.修改订单
			//2.1获取交易流水号
			String transactionSerialNumber = paymentService.getTransactionSerialNumber(recharge.getUserId().toString(), Constants.ORDER_TYPE_RECHARGE);
			AssertUtil.hasLength(transactionSerialNumber, new StringBuffer(baseLog).append("获取交易流水号失败").toString(),MESSAGE);
			//2.2修改订单状态(充值中)
			rechargeProducer.editRechargeStatus(rechargeNo, Constants.RECHARGE_STATUS_PAYING);
			//2.3修改订单支付信息(支付方式：钱包支付)
			Recharge editRecharge = new Recharge();
			editRecharge.setRechargeNo(rechargeNo);//订单号
			editRecharge.setRechargeWay(RECHARGE_WAY_WECHAT);//支付方式
			editRecharge.setTransactionSerialNumber(transactionSerialNumber);//交易流水号
			editRecharge.setOpenId(openId);//支付微信号
			editRecharge.setUpdateTime(date);//修改时间
			rechargeProducer.editRecharge(editRecharge);
			_InfoLogger.info(new StringBuffer(baseLog).append("修改订单完成").toString());
			
			//3.处理商品名称 TODO 待定
			String goodsName = new StringBuffer("会员卡充值，充").append(recharge.getPrice()/100.0).append("送").append(recharge.getGiftPrice()/100.0).toString();
			_InfoLogger.info(new StringBuffer(baseLog).append("处理商品名称完成").toString());
			
			//4.获取支付标识，微信下单
			String noticeUrl = PropertiesUtil.getContexrtParam("WXPAY_NOTICE_RECHARGE_URL");
			AssertUtil.hasLength(noticeUrl, "WXPAY_NOTICE_RECHARGE_URL未配置");
            String params = WXPayUtil.getWechatOrderPrepayId(recharge.getDiscountPrice(),transactionSerialNumber, goodsName, openId, spbillCreateIp,noticeUrl);
            WechatPayVo vo = new WechatPayVo();
            vo.setPrepayId(params);
            vo.setTransactionSerialNumber(transactionSerialNumber);
			_InfoLogger.info(new StringBuffer(baseLog).append("微信下单获取支付标识完成").toString());
			_InfoLogger.info(new StringBuffer(baseLog).append("执行结束").toString());
			return vo;
		} catch (Exception e) {
			_wechatErrorLogger.error(new StringBuffer(baseLog).append("发生异常").toString(),e);
			throw new PayException(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	public WechatTransactionRecord wechatConsumer(WXNotice notice) {
		Assert.notNull(notice,"参数[notice]为空");
		Assert.hasLength(notice.getOut_trade_no(),"参数[notice.getOut_trade_no()]为空");
		String baseLog = new StringBuffer("【微信通知】充值会员卡[").append(notice.getOut_trade_no()).append("] ").toString();
		try {
			Date date = new Date();
			
			//1.1验证通知记录的准确性
			boolean checkNotice = WXPayUtil.getWechatPayOrder(notice);
			AssertUtil.isTrue(checkNotice, "支付不成功");
			_InfoLogger.info(new StringBuffer(baseLog).append("通知记录准确性验证完成").toString());
			
			//1.2 过滤重复交易信息
			String transactionSerialNumber = notice.getOut_trade_no();
			WechatTransactionRecord transactionRecord = wechatTransactionRecordService.getWechatTransactionRecordByTSN(transactionSerialNumber);
			if(null != transactionRecord){
				_InfoLogger.info(new StringBuffer(baseLog).append("系统已存在当前交易记录，属于重复信息，本次交易结束！"));
                return transactionRecord;
			}
			_InfoLogger.info(new StringBuffer(baseLog).append("过滤重复交易信息通过，继续完成交易").toString());
			
			//1.3 查询交易下的订单 验证金额(匹配通知金额与记录金额是否一致)
			Recharge recharge = rechargeProducer.getRechargeByTSN(transactionSerialNumber);
			AssertUtil.notNull(recharge, new StringBuffer(baseLog).append("没有找到当前充值订单").toString());
			AssertUtil.isTrue(recharge.getDiscountPrice().longValue() == Long.valueOf(notice.getTotal_fee()).longValue(), new StringBuffer(baseLog).append("交易金额不匹配").toString());
			_InfoLogger.info(new StringBuffer(baseLog).append("验证金额完成").toString());
			
			//1.4验证订单状态 (当前订单状态应该为 2.	正在充值/支付中 状态)
			AssertUtil.isTrue(recharge.getStatus().intValue() == Constants.RECHARGE_STATUS_PAYING.intValue(), new StringBuffer(baseLog).append("订单状态异常").toString());
			_InfoLogger.info(new StringBuffer(baseLog).append("验证订单状态完成").toString());
			
			//1.5验证会员卡有效性(根据充值订单中的会员卡ID查询会员卡信息)
			MemberCard memberCard = memberProducer.getMemberCardById(Long.valueOf(recharge.getUserAccount()));
			AssertUtil.notNull(memberCard, new StringBuffer(baseLog).append("没有找到会员卡").toString(),MESSAGE);
			AssertUtil.isTrue(memberCard.getStatus().intValue() == Constants.VALID.intValue(), new StringBuffer(baseLog).append("会员卡状态异常").toString(),"会员卡不可用，请联系客服");
			_InfoLogger.info(new StringBuffer(baseLog).append("会员卡验证完成").toString());
			
			//2.修改订单状态(充值完成)
			//2.2修改订单状态(充值中)
			rechargeProducer.editRechargeStatus(recharge.getRechargeNo(), Constants.RECHARGE_STATUS_COMPILE);
			_InfoLogger.info(new StringBuffer(baseLog).append("修改订单状态完成").toString());
			
			//3.金额分配
			DistributeDto distributeDto = _calculationDistribute(recharge,1);
			AssertUtil.notNull(distributeDto, new StringBuffer(baseLog).append("金额分配失败").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("金额分配完成").toString());
			
			//4.会员卡充值
			Long rechargePrice = CalculateUtils.getNewVal(recharge.getPrice(),recharge.getGiftPrice());
			boolean rechargeFlag = memberProducer.sumMemberAmount(memberCard.getId(), recharge.getPrice(),recharge.getGiftPrice());
			AssertUtil.isTrue(rechargeFlag, new StringBuffer(baseLog).append("调用充值接口失败").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("充值到会员卡完成").toString());
			
			//5.赠送积分
			this._comsuerSendInteger(recharge);
			_InfoLogger.info(new StringBuffer(baseLog).append("赠送积分完成").toString());
			
			//6.金额入账
			String openId = recharge.getOpenId();
            Receivables receivables = new Receivables();
            Long amount = recharge.getDiscountPrice() - distributeDto.getPayFeeMoney();
            receivables.setAmount(amount);
            receivables.setFee(distributeDto.getPayFeeMoney());
            receivables.setOpenId(openId);
            receivables.setTransactionSerialNumber(transactionSerialNumber);
            boolean flag = paymentService.receivablesWechat(receivables);
			AssertUtil.isTrue(flag, new StringBuffer(baseLog).append("金额入账(平台入账,微信手续费入账)失败").toString());
			_InfoLogger.info(new StringBuffer(baseLog).append("金额入账完成").toString());
			
			//7.累计消费
			boolean sumConsumerFlag = this._sumCardAmountAndInteger(recharge.getRechargeNo());
			AssertUtil.isTrue(sumConsumerFlag, new StringBuffer(baseLog).append("累计消费失败").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("累计消费完成").toString());
			
			//8.金额转入卖家账户
			boolean divideCommissionFlag = this._divideCommissionsToAccount(recharge.getRechargeNo());
			AssertUtil.isTrue(divideCommissionFlag, new StringBuffer(baseLog).append("金额转入卖家账户失败").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("金额转入卖家账户完成").toString());
			
			//9.交易记录
			//9.1微信交易记录
			WechatTransactionRecord tr = new WechatTransactionRecord();
            tr.setUserId(recharge.getUserId());
            tr.setOpenId(openId);
            tr.setTransactionSerialNumber(transactionSerialNumber);
            tr.setWechatSerialNumber(notice.getTransaction_id());
            tr.setAccountType(1);
            tr.setCash(recharge.getDiscountPrice());
            tr.setStatus(1);
            tr.setTransactionTime(date);
            tr.setCreateTime(date);
            tr.setUpdateTime(date);
            wechatTransactionRecordService.addWechatTransactionRecord(tr);
            //9.2会员卡交易记录(充值记录)
            MemberCard rechargeAftermemberCard = memberProducer.getMemberCardById(memberCard.getId());
            MemberTransactionRecord mtr = new MemberTransactionRecord();
            mtr.setMemberCardId(memberCard.getId());
            mtr.setSellerId(recharge.getSellerId().toString());
            mtr.setTransactionSerialNumber(transactionSerialNumber);
            mtr.setChannel(MemberTransactionRecordDto.CHANNEL_WECHAT);
            mtr.setType(MemberTransactionRecordDto.TYPE_RECHAGE);
            mtr.setGoodInfo(new StringBuffer("充").append(recharge.getPrice()/100.0).append("送").append(recharge.getGiftPrice()/100.0).toString());
            mtr.setTransPrice(rechargePrice);//交易金额
            mtr.setAmount(rechargeAftermemberCard.getAmount());
            mtr.setBeforeAmount(rechargeAftermemberCard.getAmount());
            mtr.setTransTime(date);
            mtr.setTransYear(DateUtil.DateToString(date, "yyyy"));
            mtr.setTransMonth(DateUtil.DateToString(date, "MM"));
            mtr.setCreateTime(date);
            memberTransactionRecordService.addMemberTransactionRecord(mtr);
			_InfoLogger.info(new StringBuffer(baseLog).append("记录交易完成").toString());
			_InfoLogger.info(new StringBuffer(baseLog).append("执行结束").toString());
			return tr;
		} catch (Exception e) {
			_wechatErrorLogger.error(new StringBuffer(baseLog).append("发生异常").toString(),e);
			throw new PayException(new StringBuffer(baseLog).append("发生异常").toString(),e);
		}
	}
	

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackForClassName={"PayException"})
	public Integer bindPayProducer(String vaildateCode, String rechargeNo,
			Long bankCardId) {
		Assert.hasLength(vaildateCode,"参数[vaildateCode]为空");
		Assert.hasLength(rechargeNo, "参数[rechargeNo]为空");
		String baseLog = new StringBuffer("【绑定支付】充值会员卡[").append(rechargeNo).append("] ").toString();
		try {
			//1.1查询充值订单
			Recharge recharge = rechargeProducer.getRechargeByRechargeNo(rechargeNo);
			AssertUtil.notNull(recharge, new StringBuffer(baseLog).append("没有找到该充值订单").toString(),MESSAGE);
			AssertUtil.notNull(recharge.getUserId(), new StringBuffer(baseLog).append("订单userId为空").toString(),MESSAGE);
			AssertUtil.notNull(recharge.getUserAccount(), new StringBuffer(baseLog).append("订单UserAccount为空").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("验证订单完成").toString());
			
			//1.2验证订单状态(当前订单状态应该为 1.生成/未支付 状态)
			AssertUtil.isTrue(recharge.getStatus().intValue() == Constants.RECHARGE_STATUS_WAIT.intValue(), new StringBuffer(baseLog).append("订单状态异常").toString(),"当前订单不能支付");
			_InfoLogger.info(new StringBuffer(baseLog).append("订单状态验证完成").toString());
			
			//1.3验证订单支付金额，充值金额(金额必须大于零，充值金额=充值金额+赠送金额)
			AssertUtil.isTrue(recharge.getDiscountPrice().longValue() > 0,  new StringBuffer(baseLog).append("订单支付金额异常").toString(),MESSAGE);
			Long rechargePrice = CalculateUtils.getNewVal(recharge.getPrice(),recharge.getGiftPrice());
			AssertUtil.isTrue(rechargePrice > 0, new StringBuffer(baseLog).append("订单充值金额异常").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("订单金额验证完成").toString());
			
			//1.4验证支付密码
			Boolean checkPaymentCode = accountProducer.checkPaymentCode(recharge.getUserId(), vaildateCode);
			AssertUtil.isTrue(checkPaymentCode, "支付密码不正确或者未设置支付密码","支付密码不正确");
			
			//1.5验证会员卡有效性(根据充值订单中的会员卡ID查询会员卡信息)
			MemberCard memberCard = memberProducer.getMemberCardById(Long.valueOf(recharge.getUserAccount()));
			AssertUtil.notNull(memberCard, new StringBuffer(baseLog).append("没有找到会员卡").toString(),MESSAGE);
			AssertUtil.isTrue(memberCard.getStatus().intValue() == Constants.VALID.intValue(), new StringBuffer(baseLog).append("会员卡状态异常").toString(),"会员卡不可用，请联系客服");
			_InfoLogger.info(new StringBuffer(baseLog).append("会员卡验证完成").toString());
			
			//1.6查询银行卡
			BankCard bankCard = bankCardProducer.getBankCardById(bankCardId);
			AssertUtil.notNull(bankCard, "没有查询到对应的银行卡信息","找不到该银行卡");
			AssertUtil.hasLength(bankCard.getBindId(), "捷蓝绑定号为空","银行卡信息有误");
			_InfoLogger.info(new StringBuffer(baseLog).append("银行卡验证完成").toString());
			
			//2.交易流水号
			String transactionSerialNumber = paymentService.getTransactionSerialNumber(recharge.getUserId().toString(), Constants.ORDER_TYPE_RECHARGE);
			AssertUtil.hasLength(transactionSerialNumber, new StringBuffer(baseLog).append("获取交易流水号失败").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("生成交易流水号为：").append(transactionSerialNumber).toString());
			
			//3.修改订单
			rechargeProducer.editRechargeStatus(rechargeNo, Constants.RECHARGE_STATUS_PAYING);
			Recharge editRecharge = new Recharge();
			editRecharge.setRechargeNo(rechargeNo);//订单号
			editRecharge.setTransactionSerialNumber(transactionSerialNumber);//交易流水号
			rechargeProducer.editRecharge(editRecharge);
			_InfoLogger.info(new StringBuffer(baseLog).append("修改订单完成").toString());
			
			//4.处理商品名称 TODO 待定
			String goodsName = new StringBuffer("会员卡充值，充").append(recharge.getPrice()/100.0).append("送").append(recharge.getGiftPrice()/100.0).toString();
			_InfoLogger.info(new StringBuffer(baseLog).append("处理商品名称完成").toString());
			Map<String,String> param = new HashMap<String, String>();
			param.put("serialNumner", transactionSerialNumber);
			param.put("bindId", bankCard.getBindId());
			param.put("amount", String.valueOf(recharge.getDiscountPrice()));
			param.put("remarks", goodsName);
			String retStr = bindPayService.singlePay(JSONObject.fromObject(param).toString());
			AssertUtil.hasLength(retStr, "【绑定支付】接口调用失败","网络异常，支付失败");
			_InfoLogger.info(retStr);
			JSONObject jsonObject = JSONObject.fromObject(retStr);
			AssertUtil.notNull(jsonObject, "接口返回参数转换JSON失败","网络异常，支付失败");
			AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), jsonObject.getString("desc"),jsonObject.getString("desc"));
			AssertUtil.isTrue(transactionSerialNumber.equals(jsonObject.getString("serialNumner")), "交易流水号不匹配","网络异常，支付失败");
			
			Integer retParam = null;
			if("0".equals(jsonObject.getString("orderStatus")) || "1".equals(jsonObject.getString("orderStatus"))){
				//埋点 使用JOB定时查询处理订单
//				paymentJobManager.bindPayRechargeQueryOrder(transactionSerialNumber, bankCard, memberCard);
				retParam = 1;
			} else if ("2".equals(jsonObject.getString("orderStatus"))){
				//支付成功
				this.bindPayConsumer(transactionSerialNumber,jsonObject.getString("processDate"),bankCard,memberCard);
				retParam = 2;
			} else {
				//支付失败
				AssertUtil.isTrue(false, "支付接口处理失败","支付失败，请稍后重试");
			}
			return retParam;
		} catch (Exception e) {
			_InfoLogger.error(new StringBuffer(baseLog).append("发生异常").toString(),e);
			throw new PayException(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException" })
	public void queryBindPayAndConsumer(String transactionSerialNumber, BankCard bankCard, MemberCard memberCard) throws PayException {
		try {
			if(StringUtils.isBlank(transactionSerialNumber)){
				_InfoLogger.info("【查询订单消费】参数交易流水号为空");
				return;
			}
			Recharge recharge = rechargeProducer.getRechargeByTSN(transactionSerialNumber);
			String retStr = bindPayService.queryOrder(transactionSerialNumber);
			AssertUtil.hasLength(retStr, "【绑定支付】查询订单接口调用失败","网络异常，支付失败");
			JSONObject jsonObject = JSONObject.fromObject(retStr);
			AssertUtil.notNull(jsonObject, "接口返回参数转换JSON失败","网络异常，支付失败");
			AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), jsonObject.getString("desc"),jsonObject.getString("desc"));
			if("0".equals(jsonObject.getString("orderStatus")) || "1".equals(jsonObject.getString("orderStatus"))){
				//已接收，处理中
				throw new PayException("支付还在处理中，抛出异常，下次继续执行");
			} else if ("2".equals(jsonObject.getString("orderStatus"))){
				//支付成功
				this.bindPayConsumer(transactionSerialNumber,jsonObject.getString("processDate"),bankCard,memberCard);
			} else {
				//支付失败 
				_InfoLogger.info(new StringBuffer("交易流水为【").append(transactionSerialNumber).append("】").append("支付失败"));
				//修改订单为支付失败
				rechargeProducer.editRechargeStatus(recharge.getRechargeNo(), Constants.RECHARGE_STATUS_FAILED);
			}
		} catch (Exception e) {
			throw new PayException("查询订单消费发生异常",e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException" })
	private void bindPayConsumer(String transactionSerialNumber,String processDate,BankCard bankCard,MemberCard memberCard) throws PayException{
		try {
			Date date = new Date();
			//1.1查询
			StringBuffer baseLog = new StringBuffer("【绑定支付消费服务").append(transactionSerialNumber).append("】");
			Recharge recharge = rechargeProducer.getRechargeByTSN(transactionSerialNumber);
			//1.2.验证订单状态
			AssertUtil.isTrue(recharge.getStatus().intValue() == Constants.RECHARGE_STATUS_PAYING.intValue(), new StringBuffer(baseLog).append("订单状态异常").toString(),"当前订单不能支付");
			
			// 1.3 过滤重复交易信息
			WechatTransactionRecord transactionRecord = wechatTransactionRecordService.getWechatTransactionRecordByTSN(transactionSerialNumber);
			if(null != transactionRecord){
				_InfoLogger.info(new StringBuffer(baseLog).append("系统已存在当前交易记录，属于重复信息，本次交易结束！"));
                return ;
			}
			_InfoLogger.info(new StringBuffer(baseLog).append("过滤重复交易信息通过，继续完成交易").toString());
			//2.修改订单
			rechargeProducer.editRechargeStatus(recharge.getRechargeNo(), Constants.RECHARGE_STATUS_COMPILE);
			_InfoLogger.info(new StringBuffer(baseLog).append("修改订单状态完成").toString());
			Recharge editRecharge = new Recharge();
			editRecharge.setRechargeNo(recharge.getRechargeNo());//订单号
			editRecharge.setRechargeWay(RECHARGE_WAY_BANKCARD);//支付方式
			editRecharge.setUpdateTime(date);//修改时间
			rechargeProducer.editRecharge(editRecharge);
			_InfoLogger.info(new StringBuffer(baseLog).append("修改订单完成").toString());
			
			//3.分配金额
			DistributeDto distributeDto = _calculationDistribute(recharge,5);//银行卡付款
			AssertUtil.notNull(distributeDto, new StringBuffer(baseLog).append("金额分配失败").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("金额分配完成").toString());
			
			//4.会员卡充值
			Long rechargePrice = CalculateUtils.getNewVal(recharge.getPrice(),recharge.getGiftPrice());
			boolean rechargeFlag = memberProducer.sumMemberAmount(memberCard.getId(), recharge.getPrice(),recharge.getGiftPrice());
			AssertUtil.isTrue(rechargeFlag, new StringBuffer(baseLog).append("调用充值接口失败").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("充值到会员卡完成").toString());
			
			//5.赠送积分
			this._comsuerSendInteger(recharge);
			_InfoLogger.info(new StringBuffer(baseLog).append("赠送积分完成").toString());
			
			//6.金额入账
            Receivables receivables = new Receivables();
            Long amount = recharge.getDiscountPrice() - distributeDto.getPayFeeMoney();
            receivables.setAmount(amount);
            receivables.setFee(distributeDto.getPayFeeMoney());
            receivables.setTransactionSerialNumber(transactionSerialNumber);
            receivables.setChannel(2);
            receivables.setBankCard(bankCard.getBankCard());
            receivables.setBankCardId(bankCard.getId());
            receivables.setBankName(bankCard.getBankName());
            boolean flag = paymentService.receivables(receivables);
			AssertUtil.isTrue(flag, new StringBuffer(baseLog).append("金额入账(平台入账,微信手续费入账)失败").toString());
			_InfoLogger.info(new StringBuffer(baseLog).append("金额入账完成").toString());
			
			//7.累计消费
			boolean sumConsumerFlag = this._sumCardAmountAndInteger(recharge.getRechargeNo());
			AssertUtil.isTrue(sumConsumerFlag, new StringBuffer(baseLog).append("累计消费失败").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("累计消费完成").toString());
			
			//8.金额转入卖家账户
			boolean divideCommissionFlag = this._divideCommissionsToAccount(recharge.getRechargeNo());
			AssertUtil.isTrue(divideCommissionFlag, new StringBuffer(baseLog).append("金额转入卖家账户失败").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("金额转入卖家账户完成").toString());
			
			//9.交易记录
			//9.1微信交易记录
			TransactionRecord tr = new TransactionRecord();
			tr.setUserId(recharge.getUserId());
			tr.setTransactionSerialNumber(transactionSerialNumber);
			tr.setCash(recharge.getDiscountPrice());
			tr.setFee(distributeDto.getPayFeeMoney());
			tr.setType(1);
			tr.setStatus(1);
			tr.setTransactionTime(DateUtil.StringToDate(processDate));
			tr.setBankCode(bankCard.getBankCode());
			tr.setBankCard(bankCard.getBankCard());
			tr.setCreateTime(date);
			tr.setUpdateTime(date);
			transactionRecordService.addTransactionRecord(tr);
            //9.2会员卡交易记录(充值记录)
            MemberCard rechargeAftermemberCard = memberProducer.getMemberCardById(memberCard.getId());
            MemberTransactionRecord mtr = new MemberTransactionRecord();
            mtr.setMemberCardId(memberCard.getId());
            mtr.setSellerId(recharge.getSellerId().toString());
            mtr.setTransactionSerialNumber(transactionSerialNumber);
            mtr.setChannel(MemberTransactionRecordDto.CHANNEL_BANK_CARD);
            mtr.setType(MemberTransactionRecordDto.TYPE_RECHAGE);
            mtr.setGoodInfo(new StringBuffer("充").append(recharge.getPrice()/100.0).append("送").append(recharge.getGiftPrice()/100.0).toString());
            mtr.setTransPrice(rechargePrice);//交易金额
            mtr.setAmount(rechargeAftermemberCard.getAmount());
            mtr.setBeforeAmount(rechargeAftermemberCard.getAmount());
            mtr.setTransTime(date);
            mtr.setTransYear(DateUtil.DateToString(date, "yyyy"));
            mtr.setTransMonth(DateUtil.DateToString(date, "MM"));
            mtr.setCreateTime(date);
            memberTransactionRecordService.addMemberTransactionRecord(mtr);
			_InfoLogger.info(new StringBuffer(baseLog).append("记录交易完成").toString());
			_InfoLogger.info(new StringBuffer(baseLog).append("执行结束").toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new PayException(e);
		}
	}

	/**
	 * 金额分配
	 * @Title: _calculationDistribute
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param recharge
	 * @param type
	 * @return
	 * @throws PayException
	 * @return DistributeDto
	 * @author phb
	 * @date 2015年4月16日 下午2:54:07
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	private DistributeDto _calculationDistribute(Recharge recharge,Integer type) throws PayException{
		Assert.notNull(recharge,"参数[recharge]为空");
		Assert.notNull(type,"参数[type]为空");
		try {
			//支付总的手续费
			FeeCalculation feeCalculation = new FeeCalculation(type);
	        FeeDto feeDto = feeCalculation.calculation(recharge.getDiscountPrice());
	        AssertUtil.notNull(feeDto, "计算手续费失败");
	        //分配金额
			DistributeCalculation distributeCalculation = new DistributeCalculation(1);
			DistributeDto orderDistribute = distributeCalculation.calculation(feeDto, recharge.getDiscountPrice());
			AssertUtil.notNull(orderDistribute, "金额分配失败");
			
			//添加待分配金额记录
			Account shopAccount = accountProducer.getAccountByRemoteId(recharge.getShopNo(), Account.REMOTE_TYPE_SHOP);
			Date date = new Date();
			CommissionRecord cr = new CommissionRecord();
			cr.setAccountId(shopAccount.getId());
			cr.setCommission(orderDistribute.getSellerMoney());
			cr.setIsToAccount(0);
			cr.setTransactionSerialNumber(recharge.getTransactionSerialNumber());
			cr.setOrderNo(recharge.getRechargeNo());
			cr.setOrderStates(1);
			cr.setType(1);
			cr.setIsToType(1);
			cr.setCreateTime(date);
			cr.setUpdateTime(date);
			commissionRecordService.addCommissionRecord(cr);
			return orderDistribute;
		} catch (Exception e) {
			throw new PayException(e);
		}
	}

	/**
	 * 累计消费
	 * @Title: _sumCardAmountAndInteger
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param rechargeNo
	 * @return
	 * @throws PayException
	 * @return boolean
	 * @author phb
	 * @date 2015年4月16日 下午5:15:58
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	private boolean _sumCardAmountAndInteger(String rechargeNo) throws PayException {
		Assert.hasLength(rechargeNo,"参数[rechargeNo]为空");
		try {
			Recharge recharge = rechargeProducer.getRechargeByRechargeNo(rechargeNo);
			AssertUtil.isTrue(recharge.getStatus().intValue() == Constants.RECHARGE_STATUS_COMPILE,"订单状态非充值完成");
			boolean flag = paymentService.sumConsumeAmount(recharge.getUserId(), recharge.getDiscountPrice());
			AssertUtil.isTrue(flag, "累计用户消费金额失败");
			
			//累计用户在当前卖家的消费金额
			boolean mflag = memberProducer.sumConsumAmount(recharge.getUserId(), recharge.getShopNo(), recharge.getDiscountPrice());
			AssertUtil.isTrue(mflag, "累计用户在当前卖家的消费金额失败");
			return mflag;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PayException("累计消费额发生异常",e);
		}
	}
	
	/**
	 * 金额结算到卖家账户
	 * @Title: _divideCommissionsToAccount
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param recharge
	 * @return
	 * @throws PayException
	 * @return boolean
	 * @author phb
	 * @date 2015年4月16日 下午5:16:07
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	private boolean _divideCommissionsToAccount(String rechargeNo)
			throws PayException {
		Assert.hasLength(rechargeNo,"参数[rechargeNo]为空");
		try {
			Recharge recharge = rechargeProducer.getRechargeByRechargeNo(rechargeNo);
			AssertUtil.isTrue(recharge.getStatus().intValue() == Constants.RECHARGE_STATUS_COMPILE,"订单状态非充值完成");
			
			List<CommissionRecord> list = commissionRecordService.findByOrderNo(recharge.getRechargeNo());
        	List<DivideCommissions> divideCommissionsList = new ArrayList<DivideCommissions>();
        	for (CommissionRecord cr : list) {
				DivideCommissions divideCommissions = new DivideCommissions();
				divideCommissions.setMoney(cr.getCommission());
				divideCommissions.setTransactionSerialNumber(recharge.getTransactionSerialNumber());
				divideCommissions.setType(11);
				divideCommissions.setAccountId(cr.getAccountId());
				divideCommissionsList.add(divideCommissions);
			}
        	boolean divideFlag = paymentService.divideCommission(divideCommissionsList);
            AssertUtil.isTrue(divideFlag, "调用金额到账接口发生异常");
            boolean extractFlag = this._extractAmount(divideCommissionsList);
            AssertUtil.isTrue(extractFlag, "金额自动转出失败");
            // 修改记录为到账
            commissionRecordService.changeIsToAccount(recharge.getRechargeNo());
            return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PayException("订单收货后金额到账失败",e);
		}
	}
	
	/**
	 * 店铺余额转入用户账户
	 * @Title: extractAmount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param divideCommissionsList
	 * @return
	 * @throws PayException
	 * @date 2015年4月3日 下午3:58:58  
	 * @author lys
	 */
	@Transactional
	private boolean _extractAmount(List<DivideCommissions> divideCommissionsList) throws PayException {
		try {
			AssertUtil.notEmpty(divideCommissionsList, "Param[divideCommissionsList] can not be empty.");
			for (DivideCommissions dc : divideCommissionsList) {
				//非利益类型
				if (null == dc || null == dc.getType() || 11 != dc.getType().intValue()) {
					continue;
				}
				//参数准备
				Account shopAccount = accountProducer.getAccountById(dc.getAccountId());
				AssertUtil.isTrue(Account.REMOTE_TYPE_SHOP.equals(shopAccount.getRemoteType()), 
						"Param[id={}] is not a account of shop.");
				Shop shop = shopProducer.getShopByNo(shopAccount.getRemoteId());
				//金额转出
				accountProducer.extractAmount(shop.getUserId(), shop.getNo(), 
						dc.getMoney(), dc.getTransactionSerialNumber(),dc.getRemark());
			}
			return true;
		} catch (Exception e) {
			throw new PayException("金额自动转出失败", e);
		}
	}
	
	/**
	 * 
	 * @Title: _comsuerSendInteger
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param salesOrders
	 * @throws PayException
	 * @return void
	 * @author phb
	 * @date 2015年4月21日 下午5:49:41
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	private void _comsuerSendInteger(Recharge recharge) throws PayException{
		Assert.notNull(recharge,"参数为空");
		try {
			MarketOrderDetailDto dto = marketingProducer.getConsumptionOfBonusPoints(recharge.getShopNo(), recharge.getUserId(), recharge.getDiscountPrice());
			if(null != dto){
				Recharge edit = new Recharge();
				edit.setRechargeNo(recharge.getRechargeNo());
				edit.setObtainIntegral(dto.getGiftIntegral());
				rechargeProducer.editRecharge(edit);
				marketingProducer.consumptionOfBonusPoints(recharge.getShopNo(), recharge.getUserId(), dto.getGiftIntegral());
			}
		} catch (Exception e) {
			throw new PayException(e);
		}
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	public WechatPayVo paymentWechatOrder(Long userId, String openId,String spbillCreateIp, String rechargeNo, String tradeType) {
		if("JSAPI".equals(tradeType)){
			return paymentWechatOrder(userId, openId, spbillCreateIp, rechargeNo);
		}
		
		Assert.notNull(userId,"参数[userId]为空");
		Assert.hasLength(rechargeNo,"参数[rechargeNo]为空");
		String baseLog = new StringBuffer("【微信下单】充值会员卡[").append(rechargeNo).append("] ").toString();
		try {
			Date date = new Date();
			
			//1.1查询充值订单
			Recharge recharge = rechargeProducer.getRechargeByRechargeNo(rechargeNo);
			AssertUtil.notNull(recharge, new StringBuffer(baseLog).append("没有找到该充值订单").toString(),MESSAGE);
			AssertUtil.notNull(recharge.getUserId(), new StringBuffer(baseLog).append("订单userId为空").toString(),MESSAGE);
			AssertUtil.notNull(recharge.getUserAccount(), new StringBuffer(baseLog).append("订单UserAccount为空").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("验证订单完成").toString());
			
			//检测订单支付类型是否为微信公众号支付和微信支付类型是否为二维码支付
			boolean flag = false;
			if(RECHARGE_WAY_WECHAT==recharge.getRechargeWay() && "NATIVE".equals(tradeType)){
//				recharge.setStatus(Constants.RECHARGE_STATUS_WAIT);
				_InfoLogger.info(new StringBuffer(baseLog).append("微信支付无法使用公众号支付,改使用二维码支付").toString());
				flag = true;
			}
			
			//1.2验证订单状态(当前订单状态应该为 1.生成/未支付 状态)
			if(!flag){
				AssertUtil.isTrue(recharge.getStatus().intValue() == Constants.RECHARGE_STATUS_WAIT.intValue() , new StringBuffer(baseLog).append("订单状态异常").toString(),"当前订单不能支付");
			}
			
//			AssertUtil.isTrue(recharge.getStatus().intValue() == Constants.RECHARGE_STATUS_PAYING.intValue() , new StringBuffer(baseLog).append("订单状态异常").toString(),"当前订单不能支付");
			_InfoLogger.info(new StringBuffer(baseLog).append("订单状态验证完成").toString());
			
			//1.3验证订单支付金额，充值金额(金额必须大于零，充值金额=充值金额+赠送金额)
			AssertUtil.isTrue(recharge.getDiscountPrice().longValue() > 0,  new StringBuffer(baseLog).append("订单支付金额异常").toString(),MESSAGE);
			Long rechargePrice = CalculateUtils.getNewVal(recharge.getPrice(),recharge.getGiftPrice());
			AssertUtil.isTrue(rechargePrice > 0, new StringBuffer(baseLog).append("订单充值金额异常").toString(),MESSAGE);
			_InfoLogger.info(new StringBuffer(baseLog).append("订单金额验证完成").toString());
			
			//1.4验证会员卡有效性(根据充值订单中的会员卡ID查询会员卡信息)
			MemberCard memberCard = memberProducer.getMemberCardById(Long.valueOf(recharge.getUserAccount()));
			AssertUtil.notNull(memberCard, new StringBuffer(baseLog).append("没有找到会员卡").toString(),MESSAGE);
			AssertUtil.isTrue(memberCard.getStatus().intValue() == Constants.VALID.intValue(), new StringBuffer(baseLog).append("会员卡状态异常").toString(),"会员卡不可用，请联系客服");
			_InfoLogger.info(new StringBuffer(baseLog).append("会员卡验证完成").toString());
			
			//2.修改订单
			//2.1获取交易流水号
			String transactionSerialNumber = paymentService.getTransactionSerialNumber(recharge.getUserId().toString(), Constants.ORDER_TYPE_RECHARGE);
			AssertUtil.hasLength(transactionSerialNumber, new StringBuffer(baseLog).append("获取交易流水号失败").toString(),MESSAGE);
			//2.2修改订单状态(充值中)
			//TODO 使用微信二维码支付时订单状态已为充值中,所以不进行修改订单状态
			if(!flag){
				rechargeProducer.editRechargeStatus(rechargeNo, Constants.RECHARGE_STATUS_PAYING);
			}
			//2.3修改订单支付信息(支付方式：钱包支付)
			Recharge editRecharge = new Recharge();
			editRecharge.setRechargeNo(rechargeNo);//订单号
			editRecharge.setRechargeWay(RECHARGE_WAY_WECHAT);//支付方式
			editRecharge.setTransactionSerialNumber(transactionSerialNumber);//交易流水号
			editRecharge.setOpenId(openId);//支付微信号
			editRecharge.setUpdateTime(date);//修改时间
			rechargeProducer.editRecharge(editRecharge);
			_InfoLogger.info(new StringBuffer(baseLog).append("修改订单完成").toString());
			
			//3.处理商品名称 TODO 待定
			String goodsName = new StringBuffer("会员卡充值，充").append(recharge.getPrice()/100.0).append("送").append(recharge.getGiftPrice()/100.0).toString();
			_InfoLogger.info(new StringBuffer(baseLog).append("处理商品名称完成").toString());
			
			//4.获取支付标识，微信下单
			String noticeUrl = PropertiesUtil.getContexrtParam("WXPAY_NOTICE_RECHARGE_URL");
			AssertUtil.hasLength(noticeUrl, "WXPAY_NOTICE_RECHARGE_URL未配置");
            String params = WXPayUtil.getWechatOrderPrepayId(recharge.getDiscountPrice(),transactionSerialNumber, goodsName, openId, spbillCreateIp,noticeUrl,tradeType);
            WechatPayVo vo = new WechatPayVo();
            vo.setPrepayId(params);
            vo.setTransactionSerialNumber(transactionSerialNumber);
			_InfoLogger.info(new StringBuffer(baseLog).append("微信下单获取支付标识完成").toString());
			_InfoLogger.info(new StringBuffer(baseLog).append("执行结束").toString());
			return vo;
		} catch (Exception e) {
			_wechatErrorLogger.error(new StringBuffer(baseLog).append("发生异常").toString(),e);
			throw new PayException(e);
		}
	}
}
