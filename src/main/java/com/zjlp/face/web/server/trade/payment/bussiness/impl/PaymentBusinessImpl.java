package com.zjlp.face.web.server.trade.payment.bussiness.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;

import com.alibaba.fastjson.JSON;
import com.zjlp.face.account.domain.Account;
import com.zjlp.face.account.dto.AlipayReq;
import com.zjlp.face.account.dto.BankInfo;
import com.zjlp.face.account.dto.LakalaReq;
import com.zjlp.face.account.dto.Receivables;
import com.zjlp.face.account.dto.Wallet;
import com.zjlp.face.account.dto.WapPayReq;
import com.zjlp.face.account.dto.WapPayRsp;
import com.zjlp.face.account.exception.AmountException;
import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.account.service.AlipayService;
import com.zjlp.face.account.service.BindPayService;
import com.zjlp.face.account.service.LakalaService;
import com.zjlp.face.account.service.PaymentService;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.date.DateStyle;
import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.PayException;
import com.zjlp.face.web.server.operation.marketing.producer.MarketingProducer;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.producer.MemberProducer;
import com.zjlp.face.web.server.operation.redenvelope.domain.SendRedenvelopeRecord;
import com.zjlp.face.web.server.operation.redenvelope.producer.RedenvelopeProducer;
import com.zjlp.face.web.server.product.good.producer.GoodProducer;
import com.zjlp.face.web.server.trade.account.producer.AccountProducer;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.trade.order.domain.OrderItem;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderDto;
import com.zjlp.face.web.server.trade.order.producer.PurchaseOrderProducer;
import com.zjlp.face.web.server.trade.order.producer.SalesOrderProducer;
import com.zjlp.face.web.server.trade.payment.bussiness.PaymentBusiness;
import com.zjlp.face.web.server.trade.payment.classify.OrderClassify;
import com.zjlp.face.web.server.trade.payment.classify.OrderClassifyContext;
import com.zjlp.face.web.server.trade.payment.domain.AlipayTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.CommissionRecord;
import com.zjlp.face.web.server.trade.payment.domain.LakalaTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.MemberTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.TransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.WalletTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.WechatTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.DistributeDto;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;
import com.zjlp.face.web.server.trade.payment.domain.dto.MemberTransactionRecordDto;
import com.zjlp.face.web.server.trade.payment.domain.dto.WXNotice;
import com.zjlp.face.web.server.trade.payment.domain.vo.WechatPayVo;
import com.zjlp.face.web.server.trade.payment.scene.dis.DistributeScene;
import com.zjlp.face.web.server.trade.payment.scene.dis.DistributeSceneContext;
import com.zjlp.face.web.server.trade.payment.scene.distribute.DistributeCalculation;
import com.zjlp.face.web.server.trade.payment.scene.fee.FeeCalculation;
import com.zjlp.face.web.server.trade.payment.service.AlipayTransactionRecordService;
import com.zjlp.face.web.server.trade.payment.service.CommissionRecordService;
import com.zjlp.face.web.server.trade.payment.service.LakalaTransactionRecordService;
import com.zjlp.face.web.server.trade.payment.service.MemberTransactionRecordService;
import com.zjlp.face.web.server.trade.payment.service.TransactionRecordService;
import com.zjlp.face.web.server.trade.payment.service.WalletTransactionRecordService;
import com.zjlp.face.web.server.trade.payment.service.WechatTransactionRecordService;
import com.zjlp.face.web.server.trade.payment.util.WXPayUtil;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.bankcard.domain.vo.BankCardVo;
import com.zjlp.face.web.server.user.bankcard.producer.BankCardProducer;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.producer.UserProducer;
@Service
public class PaymentBusinessImpl implements PaymentBusiness {
	private Logger payInfoLog = Logger.getLogger("payInfoLog");
	private Logger wechatLog = Logger.getLogger("wechatfoLog");
    private Logger _alipayNoticeLogger = Logger.getLogger("alipayNoticeLog");

	@Autowired
	private TransactionRecordService transactionRecordService;
	
	@Autowired
	private WalletTransactionRecordService walletTransactionRecordService;
	
	@Autowired
	private WechatTransactionRecordService wechatTransactionRecordService;
	
	@Autowired
	private MemberTransactionRecordService memberTransactionRecordService;
	
	@Autowired
	private AlipayTransactionRecordService alipayTransactionRecordService;
	
	@Autowired
	private LakalaTransactionRecordService lakalaTransactionRecordService;
	
	@Autowired
	private OrderClassifyContext orderClassifyContext;
	
	@Autowired
	private DistributeSceneContext distributeSceneContext;
	
	@Autowired
	private CommissionRecordService commissionRecordService;
	
	//订单接口
	@Autowired
	private SalesOrderProducer salesOrderProducer;
	
	//银行卡接口
	@Autowired
	private BankCardProducer bankCardProducer;
	
	@Autowired
	private UserProducer userProducer;
	
	//商品接口
	@Autowired
	private GoodProducer goodProduer;
	
	//店铺接口
	@Autowired
	private ShopProducer shopProducer;
	
	//钱包接口
	@Autowired
	private AccountProducer accountProducer;
	
	//会员卡接口
	@Autowired
	private MemberProducer memberProducer;
	
	//营销接口
	@Autowired
	private MarketingProducer marketingProducer;
	
	@Autowired(required=false)
	private PaymentService paymentService;
	
	@Autowired(required=false)
	private BindPayService bindPayService;
	
	@Autowired(required=false)
	private AlipayService alipayService;
	
	@Autowired(required=false)
	private LakalaService lakalaService;
	
	@Autowired
	private RedenvelopeProducer redenvelopeProducer;
	
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	//采购订单接口
	@Autowired
	private PurchaseOrderProducer purchaseOrderProducer;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException" })
	public WalletTransactionRecord paymentConsumerByWallet(Long userId,
			String vaildateCode, List<String> orderNoList)
			throws PayException {
		Assert.notNull(userId, "用户ID为空,钱包支付失败");
		Assert.notEmpty(orderNoList, "订单列表为空,钱包支付失败");
		try {
			//1.2查询订单
			List<SalesOrder> salesOrderList = new ArrayList<SalesOrder>();
			for (String orderNo : orderNoList) {
				SalesOrder so = salesOrderProducer.getSalesOrderByOrderNo(orderNo);
				AssertUtil.notNull(so, new StringBuffer("没有订单号为：").append(orderNo).append("的记录").toString());
				AssertUtil.isTrue(null == so.getPayStatus() || so.getPayStatus().intValue() != 2, "订单支付处理中，请不要重复支付");
				salesOrderList.add(so);
			}
			AssertUtil.notEmpty(salesOrderList, "没有要付款的订单记录");
			//1.3.验证订单状态
			Boolean statesFlag = _checkSalesOrderStatus(salesOrderList, Constants.STATUS_WAIT);
			AssertUtil.isTrue(statesFlag, "订单状态异常","当前订单不能支付");
			//1.4.计算支付金额，判定支付金额有效性
			OrderClassify orderClassify = orderClassifyContext.getOrderClassify(1);//TODO 可拓展
			Long consumerPrice = orderClassify.cacluOrderPayAmount(salesOrderList);
			payInfoLog.info(new StringBuffer("【钱包支付】该笔支付总金额为:").append(consumerPrice).toString());
			payInfoLog.info("【钱包支付】验证完成");
			
			//2.修改订单
			String transactionSerialNumber = paymentService.getTransactionSerialNumber(userId.toString(), Constants.ORDER_TYPE_SW);
			AssertUtil.isTrue(StringUtils.isNotBlank(transactionSerialNumber), "获取交易流水号失败");
			payInfoLog.info(new StringBuffer("【钱包支付】生成订单流水号为:").append(transactionSerialNumber).toString());
            Integer type = salesOrderList.size() == 1 ? 1 : 2;
			payInfoLog.info(new StringBuffer("【钱包支付】支付类型1.单笔支付2.合并支付，当前为：").append(type).toString());
            for(SalesOrder sod : salesOrderList){
				//2.1修改订单状态
            	salesOrderProducer.editSalesOrderStatus(sod.getOrderNo(), Constants.STATUS_PAY);
				//2.2记录订单支付方式 修改订单交易流水号 银行卡支付还是钱包支付标示  单笔支付合适合并支付标示
                SalesOrder so = new SalesOrder();
                so.setOrderNo(sod.getOrderNo());
                so.setPayChannel(Constants.PAYMENT_WALLET);
                so.setPayType(type);
                so.setTransactionSerialNumber(transactionSerialNumber);
                so.setPayPrice(sod.getTotalPrice());//支付金额
                salesOrderProducer.editSalesOrder(so);
            }
			payInfoLog.info("【钱包支付】修改订单状态和信息完成");
			
			//3.金额分配
            DistributeDto distributeDto = _calculationDistribute_V3_3(salesOrderList,consumerPrice,1);
			AssertUtil.notNull(distributeDto, "金额分配失败");
			payInfoLog.info("【钱包支付】金额分配完成");
			
			//4.处理库存，销量
			orderClassify.deductionStock(salesOrderList);
			payInfoLog.info("【钱包支付】处理库存完成");
			
			//5.消费送积分（确认收货时赠送）
//			this._comsuerSendInteger(salesOrderList);
//			payInfoLog.info("【钱包支付】消费送积分完成");
			
			//当支付金额为0时,不调用支付服务
			if(consumerPrice.equals(0l)){
				payInfoLog.info("【钱包支付】支付金额0元,不调用支付服务钱包付!");
			}else{
				Assert.hasLength(vaildateCode, "支付密码为空,钱包支付失败");
				//6.调用接口 支付服务
				Wallet wallet = new Wallet();
	            wallet.setUserId(userId.toString());
	            wallet.setTotalPrice(consumerPrice);
	            wallet.setPaymendCode(vaildateCode);
	            wallet.setTransactionSerialNumber(transactionSerialNumber);
	            //账单流水备注信息
	            String remark = _getPayRemark(salesOrderList);
	            wallet.setRemark(remark);
	            boolean payFlag = paymentService.paymentByWallet(wallet);
				AssertUtil.isTrue(payFlag, "调用钱包付款接口发生异常");
				payInfoLog.info("【钱包支付】调用支付服务钱包付款完成!");
			}
			
			//7.记录
            Date date = new Date();
            WalletTransactionRecord wtr = new WalletTransactionRecord();
            wtr.setUserId(salesOrderList.get(0).getUserId());
            wtr.setTransactionSerialNumber(transactionSerialNumber);
            wtr.setCash(consumerPrice);
            wtr.setType(1);
            wtr.setStatus(1);
            wtr.setAccountType(Constants.ORDER_TYPE_SW);
            wtr.setSettleDate(DateUtil.DateToString(DateUtil.getLastTimeForDays(date, 1), DateStyle.YYYYMMDD));
            wtr.setTransactionTime(date);
            wtr.setUpdateTime(date);
            wtr.setCreateTime(date);
            walletTransactionRecordService.addWalletTransactionRecord(wtr);
			payInfoLog.info("【钱包支付】记录钱包支付记录完成!");
			payInfoLog.info("【钱包支付】支付结束!");
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
	    }catch(Exception e){
			payInfoLog.info(_getErrorInfoMsg(e.getMessage(), "钱包支付发生异常，无法完成支付", new StringBuffer("{\"orderNoList\":")
            .append(JSONArray.fromObject(orderNoList).toString()).append(",\"userId\":\"").append(userId).append("\"}").toString()));
        	e.printStackTrace();
            throw new PayException(e);
        }
	}
	/**
	 * 购买商品账单流水备注
	* @Title: _getPayRemark
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param salesOrderList
	* @return
	* @throws Exception
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2015年8月19日 下午3:01:42
	 */
	private String _getPayRemark(List<SalesOrder> salesOrderList) throws Exception{
		
	      StringBuilder shopName = new StringBuilder();
	      
	      StringBuilder goodName = new StringBuilder();
          
	      StringBuilder remark = new StringBuilder();
          
          for(SalesOrder sod : salesOrderList){
          	if (1 == sod.getSellerRemoteType()) {
          		if (null != shopName && shopName.length() > 0) {
          			shopName.append(",");
          		}
          		shopName.append(sod.getShopName());
          	}else {
          		// 获取分店店铺名称
          		
          		PurchaseOrderDto purchaseOrder = purchaseOrderProducer.getPurchaseOrder(sod.getOrderNo(), sod.getSellerRemoteId());
          		if (null != shopName && shopName.length() > 0) {
          			shopName.append(",");
          		}
          		shopName.append(purchaseOrder.getPurchaserNick());
          	}
          	List<OrderItem> orderItem = salesOrderProducer.getOrderItemListByOrderNo(sod.getOrderNo());
      		for (OrderItem item : orderItem) {
      			if (null != goodName && goodName.length() > 0) {
      				goodName.append(",");
          		}
      			goodName.append(item.getGoodName());
				}
          }
          remark.append("购买").append(shopName).append("店铺").append(goodName).append("商品");
		return remark.toString();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PaymentException"})
	public WechatPayVo paymentWechatOrder(Long userId,String openId,String spbillCreateIp,List<String> orderNoList) throws PaymentException{
		wechatLog.info("【微信支付】统一下单接口开始...");
		Assert.notNull(userId);
		Assert.hasLength(openId);
		Assert.notEmpty(orderNoList);
		try {
			if(orderNoList.size() == 1){
				wechatLog.info("【微信支付】当前为单笔支付");
            }else if(orderNoList.size() > 1){
            	wechatLog.info("【微信支付】当前为合并支付");
            }else{
				String error = new StringBuffer("订单参数异常,salesOrders.size()=").append(orderNoList.size()).toString();
				wechatLog.info(error);
                throw new PaymentException(error);
            }
			
			//2.获取需要支付的订单集合
			List<SalesOrder> salesOrderList = new ArrayList<SalesOrder>();
			for (String orderNo : orderNoList) {
				SalesOrder so = salesOrderProducer.getSalesOrderByOrderNo(orderNo);
				AssertUtil.notNull(so, new StringBuffer("没有订单号为：").append(orderNo).append("的记录").toString());
				AssertUtil.isTrue(null == so.getPayStatus() || so.getPayStatus().intValue() != 2, "订单支付处理中，请不要重复支付");
				salesOrderList.add(so);
			}
			AssertUtil.notEmpty(salesOrderList, "没有要支付的订单");
			
			//3.生成交易流水号
            String transactionSerialNumber = paymentService.getTransactionSerialNumber(userId.toString(), Constants.ORDER_TYPE_SW);
			AssertUtil.isTrue(StringUtils.isNotBlank(transactionSerialNumber), "获取交易流水号失败");
			wechatLog.info(new StringBuffer("【微信支付】生成订单流水号为：").append(transactionSerialNumber).toString());
			
			//4.验证支付金额，订单状态（区分订单类型：普通商品订单，预约订单，预定促销打折订单）
			//4.1获取支付金额
			OrderClassify orderClassify = orderClassifyContext.getOrderClassify(1);//TODO 可拓展
			Long consumerPrice = orderClassify.cacluOrderPayAmount(salesOrderList);
			AssertUtil.isTrue(consumerPrice > 0, "支付金额异常");
			//4.2验证订单状态
            Boolean statesFlag = _checkSalesOrderStatus(salesOrderList, Constants.STATUS_WAIT);
			AssertUtil.isTrue(statesFlag, "订单状态异常","当前订单不能支付");
			
			//5.修改订单交易流水号,支付方式，支付银行卡
            Integer type = salesOrderList.size() == 1 ? 1 : 2;
            for (SalesOrder so : salesOrderList) {
				// 修改多条订单交易流水号为同一流水号 记录付款银行卡编号
                SalesOrder salesOrder = new SalesOrder();
                salesOrder.setOrderNo(so.getOrderNo());
				salesOrder.setTransactionSerialNumber(transactionSerialNumber);//交易流水号
				salesOrder.setPayChannel(Constants.PAYMENT_WECHAT);//银行卡支付还是钱包支付
				salesOrder.setPayType(type);//单笔支付还是合并支付
				salesOrder.setOpenId(openId);//付款的微信OPENID
				salesOrder.setPayStatus(1);//支付中状态，限制不可以改价
                salesOrderProducer.editSalesOrder(salesOrder);
			}
			
			//6.处理商品名称，组装支付参数
            String goodsName = orderClassify.dispTransactionGoodsName(salesOrderList,32);
			AssertUtil.notNull(goodsName, "获取支付商品名称异常");
            
			//7.获取支付标识
			String noticeUrl = PropertiesUtil.getContexrtParam("WXPAY_NOTICE_URL");
			AssertUtil.hasLength(noticeUrl, "WXPAY_NOTICE_URL未配置");
            String params = WXPayUtil.getWechatOrderPrepayId(consumerPrice,transactionSerialNumber, goodsName, openId, spbillCreateIp,noticeUrl);
            WechatPayVo vo = new WechatPayVo();
            vo.setPrepayId(params);
            vo.setTransactionSerialNumber(transactionSerialNumber);
			return vo;
		} catch (Exception e) {
			wechatLog.error("微信支付，调用统一下单接口发生异常", e);
			throw new PaymentException("调用微信支付失败", e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PaymentException"})
	public WechatTransactionRecord wechatConsumer(WXNotice notice) throws PaymentException{
		wechatLog.info("微信支付消费开始...");
		Assert.notNull(notice);
		try {
			//1.1验证通知记录的准确性
			if(!WXPayUtil.getWechatPayOrder(notice)){
				throw new PaymentException("支付不成功");
			}
			String transactionSerialNumber = notice.getOut_trade_no();
			StringBuffer baseLog = new StringBuffer("【微信消费服务").append(transactionSerialNumber).append("】");
			// 1.2 过滤重复交易信息
			WechatTransactionRecord transactionRecord = wechatTransactionRecordService.getWechatTransactionRecordByTSN(transactionSerialNumber);
			if(null != transactionRecord){
				wechatLog.info(new StringBuffer(baseLog).append("系统已存在当前交易记录，属于重复信息，本次交易结束！"));
                return transactionRecord;
            }
			wechatLog.info(new StringBuffer(baseLog).append("通过重复过滤信息，可以继续进行支付动作").toString());
			
			// 1.3 查询交易下的所有订单 验证订单状态，订单金额
            List<SalesOrder> salesOrderList = salesOrderProducer.findSalesOrderListByTSN(transactionSerialNumber);
			AssertUtil.notEmpty(salesOrderList, new StringBuffer("没有找到交易流水号为：").append(transactionSerialNumber).append("的订单").toString());
			//1.3.1验证金额
			Long consumerPrice = Long.valueOf(notice.getTotal_fee());//支付金额
			OrderClassify orderClassify = orderClassifyContext.getOrderClassify(1);//TODO 可拓展
			Long orderPrice = orderClassify.cacluOrderPayAmount(salesOrderList);
			AssertUtil.isTrue(consumerPrice.longValue() == orderPrice.longValue(), "交易金额不匹配");
			//1.3.2验证订单状态 当前是否是未付款状态
            Boolean statesFlag = _checkSalesOrderStatus(salesOrderList, Constants.STATUS_WAIT);
			AssertUtil.isTrue(statesFlag, "订单状态异常");
			wechatLog.info(new StringBuffer(baseLog).append("验证完成").toString());
            
			//2.修改订单状态
            for (SalesOrder so : salesOrderList) {
            	salesOrderProducer.editSalesOrderStatus(so.getOrderNo(), Constants.STATUS_PAY);
            	//修改支付金额
            	SalesOrder order = new SalesOrder();
            	order.setOrderNo(so.getOrderNo());
            	order.setPayPrice(so.getTotalPrice());
            	salesOrderProducer.editSalesOrder(order);
			}
            wechatLog.info(new StringBuffer(baseLog).append("修改订单状态完成").toString());
			
			//3.计算手续费 处理佣金分成 记录订单手续费，佣金分成情况
            DistributeDto distributeDto = _calculationDistribute_V3_3(salesOrderList,consumerPrice,4);
			AssertUtil.notNull(distributeDto, "金额分配失败");
			wechatLog.info(new StringBuffer(baseLog).append("金额分配完成").toString());
            
			//4.处理库存，销量
            orderClassify.deductionStock(salesOrderList);
            wechatLog.info(new StringBuffer(baseLog).append("处理库存，销量完成").toString());
			
			//5.消费送积分（确认收货时赠送）
//			this._comsuerSendInteger(salesOrderList);
//			payInfoLog.info(new StringBuffer(baseLog).append("消费送积分完成").toString());
            
			//6.金额入账
            String openId = salesOrderList.get(0).getOpenId();
            Receivables receivables = new Receivables();
            Long amount = consumerPrice - distributeDto.getPayFeeMoney();
            receivables.setAmount(amount);
            receivables.setFee(distributeDto.getPayFeeMoney());
            receivables.setOpenId(openId);
            receivables.setTransactionSerialNumber(transactionSerialNumber);
			String remark = _getPayRemark(salesOrderList);
			receivables.setRemark(remark);
            boolean flag = paymentService.receivablesWechat(receivables);
			AssertUtil.isTrue(flag, "金额入账(平台入账,微信手续费入账)失败");
			wechatLog.info(new StringBuffer(baseLog).append("金额入账完成").toString());
            
			//7.记录（交易记录）
            Date date = new Date();
            WechatTransactionRecord tr = new WechatTransactionRecord();
            tr.setUserId(salesOrderList.get(0).getUserId());
            tr.setOpenId(openId);
            tr.setTransactionSerialNumber(transactionSerialNumber);
            tr.setWechatSerialNumber(notice.getTransaction_id());
            tr.setAccountType(1);
            tr.setCash(consumerPrice);
            tr.setStatus(1);
            tr.setTransactionTime(date);
            tr.setCreateTime(date);
            tr.setUpdateTime(date);
            wechatTransactionRecordService.addWechatTransactionRecord(tr);
            wechatLog.info(new StringBuffer(baseLog).append("结束...").toString());
            return tr;
		} catch (Exception e) {
			wechatLog.error("微信支付通知处理发生异常",e);
			throw new PaymentException("微信支付通知处理发生异常", e);
		}
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	public MemberTransactionRecord paymentConsumerByMemberCard(Long userId,
			String vaildateCode, List<String> orderNoList)
			throws PayException {
		Assert.notNull(userId, "用户ID为空,会员卡支付失败");
		Assert.hasLength(vaildateCode, "支付密码为空,会员卡支付失败");
		Assert.notEmpty(orderNoList, "订单列表为空,会员卡支付失败");
		try {
			//卖家编号 会员卡支付只适用于同一个卖家
			Long sellerId = null;
			//1.2查询订单
			List<SalesOrder> salesOrderList = new ArrayList<SalesOrder>();
			MemberCard consumerMemberCard = null;
			for (String orderNo : orderNoList) {
				SalesOrder so = salesOrderProducer.getSalesOrderByOrderNo(orderNo);
				AssertUtil.notNull(so, new StringBuffer("没有订单号为：").append(orderNo).append("的记录").toString());
				salesOrderList.add(so);

				AssertUtil.isTrue(null == so.getPayStatus() || so.getPayStatus().intValue() != 2, "订单支付处理中，请不要重复支付");
				
				//1.3验证用户在当前店铺是否可以使用会员卡
				consumerMemberCard = memberProducer.getMemberCardByUserIdAndShopNo(userId, so.getShopNo());
				AssertUtil.notNull(consumerMemberCard, new StringBuffer("该用户在店铺").append(so.getShopNo()).append("没有可用的会员卡").toString(),"该用户没有可用的会员卡");
				
				//验证当前交易的订单是不是同一个卖家
				Shop shop = shopProducer.getShopByNo(so.getShopNo());
				if(null == sellerId){
					sellerId = shop.getUserId();
					continue;
				}
				AssertUtil.isTrue(sellerId.longValue() == shop.getUserId().longValue(), "当前交易的订单是不是同一个卖家,不能使用会员卡支付","当前交易的订单不支持会员卡付款");
			}
			AssertUtil.notEmpty(salesOrderList, "没有要付款的订单记录");
			
			//1.4.验证订单状态
			Boolean statesFlag = _checkSalesOrderStatus(salesOrderList, Constants.STATUS_WAIT);
			AssertUtil.isTrue(statesFlag, "订单状态异常","当前订单不能支付");
			//1.5.计算支付金额，判定支付金额有效性
			OrderClassify orderClassify = orderClassifyContext.getOrderClassify(1);//TODO 可拓展
			Long consumerPrice = orderClassify.cacluOrderPayAmount(salesOrderList);
			payInfoLog.info(new StringBuffer("【会员卡支付】该笔支付总金额为:").append(consumerPrice).toString());
			payInfoLog.info("【会员卡支付】验证完成");
			
			//1.6验证支付密码
			Boolean checkPaymentCode = accountProducer.checkPaymentCode(userId, vaildateCode);
			AssertUtil.isTrue(checkPaymentCode, "支付密码不正确或者未设置支付密码","支付密码不正确");
			
			//2.修改订单
			String transactionSerialNumber = paymentService.getTransactionSerialNumber(userId.toString(), Constants.ORDER_TYPE_SW);
			AssertUtil.isTrue(StringUtils.isNotBlank(transactionSerialNumber), "获取交易流水号失败");
			payInfoLog.info(new StringBuffer("【会员卡支付】生成订单流水号为:").append(transactionSerialNumber).toString());
            Integer type = salesOrderList.size() == 1 ? 1 : 2;
			payInfoLog.info(new StringBuffer("【会员卡支付】支付类型1.单笔支付2.合并支付，当前为：").append(type).toString());
            for(SalesOrder sod : salesOrderList){
				//2.1修改订单状态
            	salesOrderProducer.editSalesOrderStatus(sod.getOrderNo(), Constants.STATUS_PAY);
				//2.2记录订单支付方式 修改订单交易流水号 银行卡支付还是钱包支付标示  单笔支付合适合并支付标示
                SalesOrder so = new SalesOrder();
                so.setOrderNo(sod.getOrderNo());
                so.setPayChannel(Constants.PAYMENT_MEMBER_CARD);
                so.setPayType(type);
                so.setTransactionSerialNumber(transactionSerialNumber);
                so.setPayPrice(0L);//会员卡付款金额为0
                salesOrderProducer.editSalesOrder(so);
            }
			payInfoLog.info("【会员卡支付】修改订单状态和信息完成");
			
			//4.处理库存，销量
			orderClassify.deductionStock(salesOrderList);
			payInfoLog.info("【会员卡支付】处理库存完成");
			
			//5.扣除会员卡余额
			Boolean miniAmount = memberProducer.miniMemberAmount(consumerMemberCard.getId(), consumerPrice);
			AssertUtil.isTrue(miniAmount, "扣除会员卡发生异常","支付异常，请稍后重试");
			payInfoLog.info("【会员卡支付】调用会员卡支付接口完成!");
			
			//6.记录
			MemberCard consumerAfterMemberCard = memberProducer.getMemberCardByUserIdAndShopNo(userId, salesOrderList.get(0).getShopNo());
			String goodName = orderClassify.dispTransactionGoodsName(salesOrderList, 120);
			Date date = new Date();
            MemberTransactionRecord mtr = new MemberTransactionRecord();
            mtr.setMemberCardId(consumerMemberCard.getId());
            mtr.setSellerId(sellerId.toString());
            mtr.setTransactionSerialNumber(transactionSerialNumber);
            mtr.setChannel(MemberTransactionRecordDto.CHANNEL_MEMBER_CARD);//会员卡消费
            mtr.setType(MemberTransactionRecordDto.TYPE_CONSUM);
            mtr.setGoodInfo(goodName);
            mtr.setTransPrice(consumerPrice);
            mtr.setAmount(consumerAfterMemberCard.getAmount());
            mtr.setBeforeAmount(consumerMemberCard.getAmount());
            mtr.setTransTime(date);
            mtr.setTransYear(DateUtil.DateToString(date, "yyyy"));
            mtr.setTransMonth(DateUtil.DateToString(date, "MM"));
            mtr.setCreateTime(date);
            memberTransactionRecordService.addMemberTransactionRecord(mtr);
			payInfoLog.info("【会员卡支付】记录钱包支付记录完成!");
			payInfoLog.info("【会员卡支付】支付结束!");
			return mtr;
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
        }catch(Exception e){
        	payInfoLog.info(_getErrorInfoMsg(e.getMessage(), "会员卡支付发生异常，无法完成支付", new StringBuffer("{\"orderNoList\":")
            .append(JSONArray.fromObject(orderNoList).toString()).append(",\"userId\":\"").append(userId).append("\"}").toString()));
        	e.printStackTrace();
            throw new PayException(e);
        }
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	public Integer bindPayProducer(Long userId,String vaildateCode,List<String> orderNoList,Long bankCardId)
			throws PayException{
		AssertUtil.notNull(userId, "用户ID为空,会员卡支付失败","出错啦!!!");
		AssertUtil.hasLength(vaildateCode, "支付密码为空,会员卡支付失败","出错啦!!!");
		AssertUtil.notEmpty(orderNoList, "订单列表为空,会员卡支付失败","出错啦!!!");
		AssertUtil.notNull(bankCardId,"支付银行卡号为空","出错啦!!!");
		List<SalesOrder> salesOrderList = new ArrayList<SalesOrder>();
		String transactionSerialNumber = null;
		BankCard bankCard = null;
		try {
			//1.1查询订单
			for (String orderNo : orderNoList) {
				SalesOrder so = salesOrderProducer.getSalesOrderByOrderNo(orderNo);
				AssertUtil.notNull(so, new StringBuffer("没有订单号为：").append(orderNo).append("的记录").toString());
				//1.1.1验证订单是否处于银商受理中
				AssertUtil.isTrue(null == so.getPayStatus() || so.getPayStatus().intValue() != 2, "订单支付处理中，请不要重复支付","支付处理中，请不要重复支付");
				salesOrderList.add(so);
			}
			AssertUtil.notEmpty(salesOrderList, "没有要付款的订单记录");
			//1.2.验证订单状态
			Boolean statesFlag = _checkSalesOrderStatus(salesOrderList, Constants.STATUS_WAIT);
			AssertUtil.isTrue(statesFlag, "订单状态异常","当前订单不能支付");
			//1.3.计算支付金额，判定支付金额有效性
			OrderClassify orderClassify = orderClassifyContext.getOrderClassify(1);//TODO 可拓展
			Long consumerPrice = orderClassify.cacluOrderPayAmount(salesOrderList);
			payInfoLog.info(new StringBuffer("【绑定支付】该笔支付总金额为:").append(consumerPrice).toString());
			//1.4验证支付密码
			Boolean checkPaymentCode = accountProducer.checkPaymentCode(userId, vaildateCode);
			AssertUtil.isTrue(checkPaymentCode, "支付密码不正确或者未设置支付密码","支付密码不正确");
			//1.5查询银行卡
			bankCard = bankCardProducer.getBankCardById(bankCardId);
			AssertUtil.notNull(bankCard, "没有查询到对应的银行卡信息","找不到该银行卡");
			AssertUtil.hasLength(bankCard.getBindId(), "捷蓝绑定号为空","银行卡信息有误");
			payInfoLog.info("【绑定支付】验证完成");
			
			transactionSerialNumber = paymentService.getTransactionSerialNumber(userId.toString(), Constants.ORDER_TYPE_SW);
			AssertUtil.isTrue(StringUtils.isNotBlank(transactionSerialNumber), "获取交易流水号失败");
			payInfoLog.info(new StringBuffer("【绑定支付】生成订单流水号为:").append(transactionSerialNumber).toString());
			
			//设置一个回滚点
			DataSourceTransactionManager jzTransactionManager = (DataSourceTransactionManager) ContextLoader.getCurrentWebApplicationContext().getBean("jzTransactionManager");
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			TransactionStatus status = jzTransactionManager.getTransaction(def);
			
			//2.修改订单交易流水
			for(SalesOrder sod : salesOrderList){
				SalesOrder so = new SalesOrder();
				so.setOrderNo(sod.getOrderNo());
				so.setTransactionSerialNumber(transactionSerialNumber);
				salesOrderProducer.editSalesOrder(so);
			}
			
			//3.消费支付（起验证作用,如果支付成功就提交，如果支持处理中就回滚等查询到成功再消费。为的是支付成功消费失败的情况）
			this.bindPayConsumer(transactionSerialNumber,DateUtil.DateToString(new Date(), "yyyyMMdd"),bankCard);
			
			//4.支付接口
			Map<String,String> param = new HashMap<String, String>();
			param.put("serialNumner", transactionSerialNumber);
			param.put("bindId", bankCard.getBindId());
			param.put("amount", String.valueOf(consumerPrice));
			param.put("remarks", orderClassify.dispTransactionGoodsName(salesOrderList, 32));
			String retStr = bindPayService.singlePay(JSONObject.fromObject(param).toString());
			AssertUtil.hasLength(retStr, "【绑定支付】接口调用失败","网络异常，支付失败");
			payInfoLog.info(retStr);
//			retStr = retStr.replace("\"orderStatus\":\"2\"", "\"orderStatus\":\"1\"");//测试支付处理中使用
			JSONObject jsonObject = JSONObject.fromObject(retStr);
			AssertUtil.notNull(jsonObject, "接口返回参数转换JSON失败","网络异常，支付失败");
			AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), jsonObject.getString("desc"),jsonObject.getString("desc"));
			AssertUtil.isTrue(transactionSerialNumber.equals(jsonObject.getString("serialNumner")), "交易流水号不匹配","网络异常，支付失败");
			
			Integer retParam = null;
			if("0".equals(jsonObject.getString("orderStatus")) || "1".equals(jsonObject.getString("orderStatus"))){
				//手动回滚前面的事物
				jzTransactionManager.rollback(status);
				
				//修改订单为银商处理中
				for (String orderNo : orderNoList) {
					SalesOrder editPayStatus = new SalesOrder();
				    editPayStatus.setOrderNo(orderNo);
				    editPayStatus.setPayStatus(2);//银商受理中 
				    editPayStatus.setTransactionSerialNumber(transactionSerialNumber);
				    salesOrderProducer.editSalesOrder(editPayStatus);
				}
				//埋点 使用JOB定时查询处理订单
//				paymentJobManager.bindPayQueryOrder(transactionSerialNumber, bankCard);
				retParam = 1;
			} else if ("2".equals(jsonObject.getString("orderStatus"))){
				jzTransactionManager.commit(status);
				//支付成功
				retParam = 2;
			} else {
				jzTransactionManager.rollback(status);
				//支付失败
				AssertUtil.isTrue(false, "支付接口处理失败","支付失败，请稍后重试");
			}
			return retParam;
		} catch (Exception e) {
			payInfoLog.error("支付失败",e);
			throw new PayException(e);
		}
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,noRollbackForClassName={"PayException"})
	public void queryBindPayAndConsumer(String transactionSerialNumber,BankCard bankCard)
			throws PayException {
		try {
			if(StringUtils.isBlank(transactionSerialNumber)){
				payInfoLog.info("【查询订单消费】参数交易流水号为空");
				return;
			}
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
				this.bindPayConsumer(transactionSerialNumber,jsonObject.getString("processDate"),bankCard);
			} else {
				//支付失败 
				payInfoLog.info(new StringBuffer("交易流水为【").append(transactionSerialNumber).append("】").append("支付失败"));
				// 修改订单为支付失败
				List<SalesOrder> salesOrderList = salesOrderProducer.findSalesOrderListByTSN(transactionSerialNumber);
				for(SalesOrder sod : salesOrderList){
					//2.1修改订单状态
					salesOrderProducer.editSalesOrderStatus(sod.getOrderNo(), Constants.STATUS_PAY);
					//2.2记录订单支付方式 修改订单交易流水号 银行卡支付还是钱包支付标示  单笔支付合适合并支付标示
				    SalesOrder so = new SalesOrder();
				    so.setOrderNo(sod.getOrderNo());
				    so.setPayStatus(4);//支付失败 
				    salesOrderProducer.editSalesOrder(so);
				}
			}
		} catch (Exception e) {
			throw new PayException("查询订单消费发生异常",e);
		}
	}


	/***
	 * 绑定支付 消费
	 * @Title: bindPayConsumer
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param transactionSerialNumber
	 * @param processDate
	 * @param bankCard
	 * @throws PayException
	 * @return void
	 * @author phb
	 * @date 2015年5月29日 上午11:46:33
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException" })
	private void bindPayConsumer(String transactionSerialNumber,String processDate,BankCard bankCard) throws PayException{
		try {
			//1.1查询
			StringBuffer baseLog = new StringBuffer("【绑定支付消费服务").append(transactionSerialNumber).append("】");
			List<SalesOrder> salesOrderList = salesOrderProducer.findSalesOrderListByTSN(transactionSerialNumber);
			//1.2.验证订单状态
			Boolean statesFlag = _checkSalesOrderStatus(salesOrderList, Constants.STATUS_WAIT);
			AssertUtil.isTrue(statesFlag, "订单状态异常","当前订单不能支付");
			OrderClassify orderClassify = orderClassifyContext.getOrderClassify(1);//TODO 可拓展
			Long consumerPrice = orderClassify.cacluOrderPayAmount(salesOrderList);
			// 1.3 过滤重复交易信息
            TransactionRecord transactionRecord = transactionRecordService.getTransactionRecordByTSN(transactionSerialNumber);
            if(null != transactionRecord){
				payInfoLog.info(new StringBuffer(baseLog).append("系统已存在当前交易记录，属于重复信息，本次交易结束！"));
                return ;
            }
			//2.修改订单
			Integer type = salesOrderList.size() == 1 ? 1 : 2;
			payInfoLog.info(new StringBuffer("【绑定支付】支付类型1.单笔支付2.合并支付，当前为：").append(type).toString());
			for(SalesOrder sod : salesOrderList){
				//2.1修改订单状态
				salesOrderProducer.editSalesOrderStatus(sod.getOrderNo(), Constants.STATUS_PAY);
				//2.2记录订单支付方式 修改订单交易流水号 银行卡支付还是钱包支付标示  单笔支付合适合并支付标示
			    SalesOrder so = new SalesOrder();
			    so.setOrderNo(sod.getOrderNo());
			    so.setPayChannel(Constants.PAYMENT_BANKCARD);
			    so.setPayType(type);
			    so.setTransactionSerialNumber(transactionSerialNumber);
			    so.setPayPrice(sod.getTotalPrice());//支付金额
			    so.setPayStatus(3);//支付成功
			    salesOrderProducer.editSalesOrder(so);
			}
			payInfoLog.info(new StringBuffer(baseLog).append("修改订单状态和信息完成"));
			
			//3.分配金额
			DistributeDto distributeDto = _calculationDistribute_V3_3(salesOrderList,consumerPrice,5);
			AssertUtil.notNull(distributeDto, "金额分配失败");
			payInfoLog.info(new StringBuffer(baseLog).append("金额分配完成"));
			
			//4.处理库存
			orderClassify.deductionStock(salesOrderList);
			payInfoLog.info(new StringBuffer(baseLog).append("处理库存完成"));
			
			//5.消费送积分(确认收货时赠送)
//			this._comsuerSendInteger(salesOrderList);
//			payInfoLog.info(new StringBuffer(baseLog).append("消费送积分完成"));
			
			//6.金额入账
			Receivables receivables = new Receivables();
			Long amount = consumerPrice - distributeDto.getPayFeeMoney();
			receivables.setAmount(amount);
			receivables.setFee(distributeDto.getPayFeeMoney());
			receivables.setBankCard(bankCard.getBankCard());
			receivables.setBankCardId(bankCard.getId());
			receivables.setBankName(bankCard.getBankName());
			receivables.setTransactionSerialNumber(transactionSerialNumber);
			receivables.setChannel(2);//捷蓝接口钱包
			boolean flag = paymentService.receivables(receivables);
			AssertUtil.isTrue(flag, "金额入账(平台入账,捷蓝手续费入账)");
			payInfoLog.info(new StringBuffer(baseLog).append("金额转入系统钱包和捷蓝手续费钱包完成").toString());
			
			//7.记录
			Date date = new Date();
			TransactionRecord tr = new TransactionRecord();
			tr.setUserId(salesOrderList.get(0).getUserId());
			tr.setTransactionSerialNumber(transactionSerialNumber);
			tr.setCash(consumerPrice);
			tr.setFee(distributeDto.getPayFeeMoney());
			tr.setType(1);
			tr.setStatus(1);
			tr.setTransactionTime(DateUtil.StringToDate(processDate));
			tr.setBankCode(bankCard.getBankCode());
			tr.setBankCard(bankCard.getBankCard());
			tr.setCreateTime(date);
			tr.setUpdateTime(date);
			transactionRecordService.addTransactionRecord(tr);
			payInfoLog.info(new StringBuffer(baseLog).append("支付消费结束").toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new PayException(e);
		}
	}

	/**********************************************************************/
	/**
	 * 统一组装异常信息
	 * @Title: _getErrorInfoMsg
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param errorMsg
	 * @param errorInfo
	 * @param errorData
	 * @return
	 * @date 2014年11月24日 下午3:03:56
	 * @author phb
	 */
	private String _getErrorInfoMsg(String errorMsg, String errorInfo, String errorData){
        String str = new StringBuffer("{\"err_msg\":\"").append(errorMsg).append("\",\"err_info\":\"").append(errorInfo).append("\",\"err_data\":\"").append(errorData).append("\"}").toString();
        return str;
    }
	
	/**
	 * 验证订单状态
	 * @Title: _checkSalesOrderStatus
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param list
	 * @param status
	 * @return
	 * @throws PaymentException
	 * @return Boolean
	 * @author phb
	 * @date 2015年3月20日 上午10:59:26
	 */
	private Boolean _checkSalesOrderStatus(List<SalesOrder> list,Integer status) throws PaymentException{
		AssertUtil.notEmpty(list, "参数【订单集合】为空","出错啦!!!");
		AssertUtil.notNull(status, "参数【状态】为空","出错啦!!!");
		try {
			for (int i = 0; i < list.size(); i++) {
				if(status.intValue() != list.get(i).getStatus().intValue()){
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			throw new PaymentException("验证订单状态发生异常", e);
		}
	}
	
	/**
	 * 组装请求参数
	 * @Title: _createProducerParams
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param goodsName
	 * @param transactionSerialNumber
	 * @param price
	 * @param bankCard
	 * @return
	 * @throws PaymentException
	 * @return WapPayReq
	 * @author phb
	 * @date 2015年3月20日 上午11:30:08
	 */
	private WapPayReq _createProducerParams(String goodsName,String transactionSerialNumber,String price,BankCard bankCard) 
			throws PaymentException{
		try{
	          String host = PropertiesUtil.getContexrtParam(Constants.SWITCH_UNIONPAY_HOST);
			AssertUtil.hasLength(host, "SWITCH_UNIONPAY_HOST未配置");
	          String notifyUrl = host + PropertiesUtil.getContexrtParam(Constants.SWITCH_UNIONPAY_NOTIFYURL);
			AssertUtil.hasLength(notifyUrl, "SWITCH_UNIONPAY_NOTIFYURL未配置");
	          String urlReturn = host + PropertiesUtil.getContexrtParam(Constants.SWITCH_UNIONPAY_RETURNURL);
			AssertUtil.hasLength(urlReturn, "SWITCH_UNIONPAY_RETURNURL未配置");
	          WapPayReq wapPayReq = new WapPayReq();
			wapPayReq.setUser_id(_supplementZero(bankCard.getRemoteId()));// 用户编号
			wapPayReq.setNo_order(transactionSerialNumber);// 交易流水号
			wapPayReq.setName_goods(goodsName);// 商品名称
			wapPayReq.setMoney_order(price);// 支付金额
			wapPayReq.setPay_type(bankCard.getPayType().toString());// 支付方式，借记卡还是信用卡
			wapPayReq.setNo_agree(bankCard.getNoAgree());// 协议号
			wapPayReq.setNotify_url(notifyUrl);// 异步通知地址
			wapPayReq.setUrl_return(urlReturn);// 同步通知地址
	          return wapPayReq;
	      }catch(Exception e){
	          throw new PaymentException(e);
	      }
	}
	
	/**
	 * 用户编号向前补零
	 * @Title: _supplementZero
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 * @return
	 * @throws RuntimeException
	 * @return String
	 * @author phb
	 * @date 2015年3月20日 上午11:16:42
	 */
	private static String _supplementZero(String userId) throws RuntimeException{
        if(userId.length() < 8){
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = userId.length(); i <= 8; i++){
                stringBuilder.append("0");
            }
            return userId = stringBuilder.append(userId).toString();
        }
        return userId;
    }
	
	/**
	 * 验证连连支付消费参数
	 * @Title: _checkConsumerRspParams
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param wapPayRsp
	 * @throws PaymentException
	 * @return void
	 * @author phb
	 * @date 2015年3月20日 上午11:37:50
	 */
	private void _checkConsumerRspParams(WapPayRsp wapPayRsp) throws PaymentException{
        try{
			AssertUtil.notNull(wapPayRsp, "支付消费对象WapRspDto为空");
			AssertUtil.isTrue(StringUtils.isNotBlank(wapPayRsp.getNo_order()), "景正交易流水号no_order为空");
			AssertUtil.isTrue(StringUtils.isNotBlank(wapPayRsp.getOid_paybill()), "连连交易流水号oid_paybill为空");
			AssertUtil.isTrue(StringUtils.isNotBlank(wapPayRsp.getResult_pay()), "返回结果参数result_pay为空");
			AssertUtil.isTrue(StringUtils.isNotBlank(wapPayRsp.getMoney_order()), "订单金额money_order为空");
			AssertUtil.isTrue(StringUtils.isNotBlank(wapPayRsp.getSettle_date()), "清算日期settle_date为空");
			AssertUtil.isTrue(StringUtils.isNotBlank(wapPayRsp.getDt_order()), "交易时间dt_order为空");
        }catch(Exception e){
            throw new PaymentException(e);
        }
    }
	
	/**
	 * 交易金额分配
	 * @Title: _calculationDistribute
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param list
	 * @param totalPrice
	 * @param type
	 * @return
	 * @throws PaymentException
	 * @return DistributeDto
	 * @author phb
	 * @date 2015年3月20日 下午3:46:21
	 */
	@Deprecated
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PaymentException"})
	private DistributeDto _calculationDistribute(List<SalesOrder> list,Long totalPrice, Integer type) throws PaymentException{
		Assert.notEmpty(list);
		Assert.notNull(totalPrice);
		Assert.notNull(type);
		try {
			//支付总的手续费
			FeeCalculation feeCalculation = new FeeCalculation(type);
	        FeeDto feeDto = feeCalculation.calculation(totalPrice);
	        
	        Long sellerMoney = 0l;
	        Long payFeeMoney = 0l;
	        Long platformFeeMoney = 0l;
	        for (int i = 0;i<list.size();i++) {
				//1.订单金额
				Long orderPrice = list.get(i).getTotalPrice();
				//2.计算订单手续费
				FeeDto orderFee = new FeeDto();
				if(i == list.size() - 1){
					orderFee.setPayFee(feeDto.getPayFee() - payFeeMoney);
					orderFee.setPlatformFee(feeDto.getPlatformFee() - platformFeeMoney);
				} else {
					Long payFee = feeDto.getPayFee().longValue() == 0l ? 0l : CalculateUtils.getFeeUp(orderPrice, feeDto.getPayFee(), totalPrice);
					Long platformFee = feeDto.getPlatformFee().longValue() == 0l ? 0l : CalculateUtils.getFeeUp(orderPrice, feeDto.getPlatformFee(), totalPrice);
					orderFee.setPayFee(payFee);
					orderFee.setPlatformFee(platformFee);
				}
				
				//3.分配金额
				DistributeCalculation distributeCalculation = new DistributeCalculation(1);
				DistributeDto orderDistribute = distributeCalculation.calculation(orderFee, orderPrice);
				//4.添加金额待分配记录(当订单完成时，需要操作的金额)
				_saveCommissionRecord(list.get(i),orderDistribute);
				//5.累计当前支付金额各分配情况
				sellerMoney = CalculateUtils.getSum(sellerMoney,orderDistribute.getSellerMoney());
				payFeeMoney = CalculateUtils.getSum(payFeeMoney,orderDistribute.getPayFeeMoney());
				platformFeeMoney = CalculateUtils.getSum(platformFeeMoney, orderDistribute.getPlatformFeeMoney());
			}
	        DistributeDto distributeDto = new DistributeDto();
	        distributeDto.setSellerMoney(sellerMoney);
	        distributeDto.setPayFeeMoney(payFeeMoney);
	        distributeDto.setPlatformFeeMoney(platformFeeMoney);
			return distributeDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PaymentException(e);
		}
	}
	
	/***
	 * 交易金额分配
	 * @Title: _calculationDistributeV3_3
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param list
	 * @param totalPrice
	 * @param type
	 * 			1.钱包付款
	 * 			2.连连借记卡
	 * 			3.连连信用卡
	 * 			4.微信支付
	 * 			5.捷蓝支付
	 * 			6.支付宝支付
	 *          7.拉卡拉信用卡
	 * 			8.拉卡拉借记卡
	 * @return
	 * @throws PaymentException
	 * @return DistributeDto
	 * @author phb
	 * @date 2015年5月12日 下午2:58:48
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	private DistributeDto _calculationDistribute_V3_3(List<SalesOrder> list,Long totalPrice, Integer type) throws PayException{
		Assert.notEmpty(list);
		Assert.notNull(totalPrice);
		Assert.notNull(type);
		try {
			//支付总的手续费
			FeeCalculation feeCalculation = new FeeCalculation(type);
	        FeeDto feeDto = feeCalculation.calculation(totalPrice);
	        
	        Long payFeeMoney = 0l;
	        Long platformFeeMoney = 0l;
	        for (int i = 0;i<list.size();i++) {
	        	SalesOrder salesOrder = salesOrderProducer.getSalesOrderByOrderNo(list.get(i).getOrderNo());
	        	AssertUtil.notNull(salesOrder, "没有查询到订单");
				//1.订单金额
				Long orderPrice = salesOrder.getTotalPrice();
				//2.计算订单手续费
				FeeDto orderFee = new FeeDto();
				if(i == list.size() - 1){
					orderFee.setPayFee(feeDto.getPayFee() - payFeeMoney);
					orderFee.setPlatformFee(feeDto.getPlatformFee() - platformFeeMoney);
				} else {
					Long payFee = feeDto.getPayFee().longValue() == 0l ? 0l : CalculateUtils.getFeeUp(orderPrice, feeDto.getPayFee(), totalPrice);
					Long platformFee = feeDto.getPlatformFee().longValue() == 0l ? 0l : CalculateUtils.getFeeUp(orderPrice, feeDto.getPlatformFee(), totalPrice);
					orderFee.setPayFee(payFee);
					orderFee.setPlatformFee(platformFee);
				}
				
				//3.1分配金额场景
				Integer scene = salesOrder.getOrderCategory();
				boolean flag = purchaseOrderProducer.hasPopularizeForSalesOrder(salesOrder.getOrderNo());
				if(flag){
					scene = 5;//有推广
				}
				boolean empFlag = purchaseOrderProducer.hasEmployeeForSalesOrder(salesOrder.getOrderNo());
				if(empFlag){
					scene = 6;//员工佣金
				}
				DistributeScene distributeScene = distributeSceneContext.getDistributeScene(scene);
				//3.2分配金额，并记录
				Date date = new Date();
				distributeScene.distributeCalculation(salesOrder, orderFee,orderPrice,date);

				payFeeMoney = CalculateUtils.getSum(payFeeMoney,feeDto.getPayFee());
				platformFeeMoney = CalculateUtils.getSum(platformFeeMoney, feeDto.getPlatformFee());
			}
	        DistributeDto distributeDto = new DistributeDto();
	        distributeDto.setPayFeeMoney(payFeeMoney);
	        distributeDto.setPlatformFeeMoney(platformFeeMoney);
			return distributeDto;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PayException(e);
		}
	}
	
	/**
	 * 添加金额待分配记录(当订单完成时，需要操作的金额)
	 * @Title: _saveCommissionRecord
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param salesOrder
	 * @param orderDistribute
	 * @throws PaymentException
	 * @return void
	 * @author phb
	 * @date 2015年3月20日 下午4:48:51
	 */
	@Deprecated
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PaymentException"})
	private void _saveCommissionRecord(SalesOrder salesOrder,DistributeDto orderDistribute) throws PaymentException{
		Assert.notNull(salesOrder);
		Assert.notNull(orderDistribute);
		try {
			Account shopAccount = accountProducer.getAccountByRemoteId(salesOrder.getShopNo(), Account.REMOTE_TYPE_SHOP);
			AssertUtil.notNull(shopAccount, "没有找到店铺钱包记录");
			Date date = new Date();
			CommissionRecord cr = new CommissionRecord();
			cr.setAccountId(shopAccount.getId());
			cr.setCommission(orderDistribute.getSellerMoney());
			cr.setIsToAccount(0);
			cr.setTransactionSerialNumber(salesOrder.getTransactionSerialNumber());
			cr.setOrderNo(salesOrder.getOrderNo());
			cr.setOrderStates(1);
			cr.setType(1);
			cr.setIsToType(1);
			cr.setCreateTime(date);
			cr.setUpdateTime(date);
			commissionRecordService.addCommissionRecord(cr);
		} catch (Exception e) {
			throw new PaymentException("保存待分配金额记录失败", e);
		}
	}
	
	/**
	 * 消费送积分
	 * @Title: _sendInteger
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param salesOrders
	 * @throws PaymentException
	 * @return void
	 * @author phb
	 * @date 2015年4月21日 下午5:38:53
	 */
	@Deprecated
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PaymentException"})
	private void _comsuerSendInteger(List<SalesOrder> salesOrders) throws PaymentException{
		Assert.notEmpty(salesOrders,"参数为空");
		try {
			for (SalesOrder salesOrder : salesOrders) {
				//是否是店销
				if(1 != salesOrder.getOrderCategory().intValue()){
					continue;
				}
				//如果有会员卡，就消费送积分
				MemberCard memberCard = memberProducer.getMemberCardByUserIdAndShopNo(salesOrder.getUserId(), salesOrder.getShopNo());
				if(null != memberCard){
					marketingProducer.consumptionOfBonusPoints(salesOrder.getShopNo(),salesOrder.getUserId(),salesOrder.getObtainIntegral());
				}
			}
		} catch (Exception e) {
			throw new PaymentException(e);
		}
	}

	/**
	 * 微信支付统一下单
	 * @param userId			用户Id
	 * @param openId			用户微信授权id
	 * @param spbillCreateIp	主机id
	 * @param orderNoList		下单商品
	 * @param tradeType			微信支付类型 	NATIVE:二维码支付;JSAPI:公众号支付
	 * @return
	 * @throws PaymentException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PaymentException"})
	public WechatPayVo paymentWechatOrder(Long userId, String openId,String spbillCreateIp, List<String> orderNoList, String tradeType)throws PaymentException {
		payInfoLog.info("【微信支付】统一下单接口开始...");
		Assert.notNull(userId);
		
		if("JSAPI".equals(tradeType)){
			Assert.hasLength(openId);
		}
		
		Assert.notEmpty(orderNoList);
		try {
			if(orderNoList.size() == 1){
				payInfoLog.info("【微信支付】当前为单笔支付");
            }else if(orderNoList.size() > 1){
				payInfoLog.info("【微信支付】当前为合并支付");
            }else{
				String error = new StringBuffer("订单参数异常,salesOrders.size()=").append(orderNoList.size()).toString();
                payInfoLog.info(error);
                throw new PaymentException(error);
            }
			
			//2.获取需要支付的订单集合
			List<SalesOrder> salesOrderList = new ArrayList<SalesOrder>();
			for (String orderNo : orderNoList) {
				SalesOrder so = salesOrderProducer.getSalesOrderByOrderNo(orderNo);
				AssertUtil.notNull(so, new StringBuffer("没有订单号为：").append(orderNo).append("的记录").toString());
				AssertUtil.isTrue(null == so.getPayStatus() || so.getPayStatus().intValue() != 2, "订单支付处理中，请不要重复支付");
				salesOrderList.add(so);
			}
			AssertUtil.notEmpty(salesOrderList, "没有要支付的订单");
			
			//3.生成交易流水号
            String transactionSerialNumber = paymentService.getTransactionSerialNumber(userId.toString(), Constants.ORDER_TYPE_SW);
			AssertUtil.isTrue(StringUtils.isNotBlank(transactionSerialNumber), "获取交易流水号失败");
			payInfoLog.info(new StringBuffer("【微信支付】生成订单流水号为：").append(transactionSerialNumber).toString());
			
			//4.验证支付金额，订单状态（区分订单类型：普通商品订单，预约订单，预定促销打折订单）
			//4.1获取支付金额
			OrderClassify orderClassify = orderClassifyContext.getOrderClassify(1);//TODO 可拓展
			Long consumerPrice = orderClassify.cacluOrderPayAmount(salesOrderList);
			AssertUtil.isTrue(consumerPrice > 0, "支付金额异常");
			//4.2验证订单状态
            Boolean statesFlag = _checkSalesOrderStatus(salesOrderList, Constants.STATUS_WAIT);
			AssertUtil.isTrue(statesFlag, "订单状态异常","当前订单不能支付");
			
			//5.修改订单交易流水号,支付方式，支付银行卡
            Integer type = salesOrderList.size() == 1 ? 1 : 2;
            for (SalesOrder so : salesOrderList) {
				// 修改多条订单交易流水号为同一流水号 记录付款银行卡编号
                SalesOrder salesOrder = new SalesOrder();
                salesOrder.setOrderNo(so.getOrderNo());
				salesOrder.setTransactionSerialNumber(transactionSerialNumber);//交易流水号
				salesOrder.setPayChannel(Constants.PAYMENT_WECHAT);//银行卡支付还是钱包支付
				salesOrder.setPayType(type);//单笔支付还是合并支付
				salesOrder.setOpenId(openId);//付款的微信OPENID
				salesOrder.setPayStatus(1);//支付中状态，限制不可以改价
                salesOrderProducer.editSalesOrder(salesOrder);
			}
			
			//6.处理商品名称，组装支付参数
            String goodsName = orderClassify.dispTransactionGoodsName(salesOrderList,32);
			AssertUtil.notNull(goodsName, "获取支付商品名称异常");
            
			//7.获取支付标识
			String noticeUrl = PropertiesUtil.getContexrtParam("WXPAY_NOTICE_URL");
			AssertUtil.hasLength(noticeUrl, "WXPAY_NOTICE_URL未配置");
            String params = WXPayUtil.getWechatOrderPrepayId(consumerPrice,transactionSerialNumber, goodsName, openId, spbillCreateIp,noticeUrl,tradeType);
            WechatPayVo vo = new WechatPayVo();
            vo.setPrepayId(params);
            vo.setTransactionSerialNumber(transactionSerialNumber);
			return vo;
		} catch (Exception e) {
			payInfoLog.error("微信支付，调用统一下单接口发生异常", e);
			throw new PaymentException("调用微信支付失败", e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	public String alipayProducer(Long userId,List<String> orderNoList) throws PayException {
		//1.验证参数
		Assert.notNull(userId);
		Assert.notEmpty(orderNoList);
		try {
			if(orderNoList.size() == 1){
				payInfoLog.info("【支付宝生产支付】当前为单笔支付");
            }else if(orderNoList.size() > 1){
				payInfoLog.info("【支付宝生产支付】当前为合并支付");
            }else{
				String error = new StringBuffer("订单参数异常,salesOrders.size()=").append(orderNoList.size()).toString();
                payInfoLog.info(error);
                throw new PayException(error);
            }
			//2.获取需要支付的订单集合
			List<SalesOrder> list = new ArrayList<SalesOrder>();
			for (String orderNo : orderNoList) {
				SalesOrder so = salesOrderProducer.getSalesOrderByOrderNo(orderNo);
				AssertUtil.notNull(so, new StringBuffer("该订单号不存在：").append(orderNo).toString());
				list.add(so);
			}
			AssertUtil.notEmpty(list, "未找到要支付的订单列表","支付订单不存在");
			//3.生成交易流水号
			String transactionSerialNumber = paymentService.getTransactionSerialNumber(String.valueOf(userId), Constants.ORDER_TYPE_SW);
			AssertUtil.isTrue(StringUtils.isNotBlank(transactionSerialNumber), "生成交易流水号失败","系统异常");
			payInfoLog.info(new StringBuffer("【支付宝生产支付】生成订单流水号为：").append(transactionSerialNumber).toString());
			//4.验证支付金额，订单状态（区分订单类型：普通商品订单，预约订单，预定促销打折订单）
			//4.1获取支付金额
			OrderClassify orderClassify = orderClassifyContext.getOrderClassify(1);//TODO 可拓展
			Long totalPrice = orderClassify.cacluOrderPayAmount(list);
			AssertUtil.isTrue(totalPrice > 0l, "订单付款金额异常","订单错误");
			String payPrice = CalculateUtils.converPennytoYuan(totalPrice);
			AssertUtil.isTrue(StringUtils.isNotBlank(payPrice), "付款金额分转元失败","订单错误");
			payInfoLog.info(new StringBuffer("【支付宝生产支付】该笔支付总价格：").append(payPrice).append("元").toString());
			//4.2验证订单状态
			Boolean statsFlag = _checkSalesOrderStatus(list, Constants.STATUS_WAIT);
			AssertUtil.isTrue(statsFlag, "订单状态异常","订单状态异常");
			//5.修改订单交易流水号,支付方式，支付银行卡
            Integer type = list.size() == 1 ? 1 : 2;
            for (int i=0;i<list.size();i++) {
				// 修改多条订单交易流水号为同一流水号 记录付款银行卡编号
                SalesOrder salesOrder = new SalesOrder();
                salesOrder.setOrderNo(list.get(i).getOrderNo());
				salesOrder.setTransactionSerialNumber(transactionSerialNumber);//交易流水号
				salesOrder.setPayChannel(Constants.PAYMENT_ALIPAY);//支付宝支付
				salesOrder.setPayType(type);//单笔支付还是合并支付
				salesOrder.setPayStatus(1);//支付中状态，限制不可以改价
                salesOrderProducer.editSalesOrder(salesOrder);
			}
			//6.处理商品名称，组装支付参数
			String goodsName = orderClassify.dispTransactionGoodsName(list,128);
			AssertUtil.hasLength(goodsName, "支付商品名称异常","订单错误");
			//7.组装支付服务 生产支付所需参数
			AlipayReq alipayReq = _createAlipayReq(goodsName, null, transactionSerialNumber, payPrice);
			//8.调用支付服务 得到支付生产参数
			String param = alipayService.alipayProducer(alipayReq);
			AssertUtil.hasLength(param, "调用支付接口获取支付参数异常","支付异常");
			JSONObject jsonObject = JSONObject.fromObject(param);
			AssertUtil.notNull(jsonObject, "获取的接口参数转换JSON对象失败","支付异常");
			AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "调用支付接口获取支付参数失败","支付异常");
			//9.处理参数
			String flag = _buildRequest(jsonObject);
			AssertUtil.hasLength(flag, "组装页面提交代码失败","支付异常");
			return flag;
		} catch (Exception e) {
			//TODO 支付宝错误日志
			payInfoLog.error(e.getMessage(),e);
			throw new PayException(e.getMessage(),e);
		}
	}

	/**
	* @Title: alipayConsumer
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param notice
	* @return
	* @throws PayException
	* @return AlipayTransactionRecord    返回类型
	* @throws
	* @author wxn  
	* @date 2015年8月21日 下午2:57:14 
	*/ 
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	public AlipayTransactionRecord alipayConsumer(Map<String, String> notice) throws PayException{
		Assert.notEmpty(notice);
		try {
			_alipayNoticeLogger.info(notice);
			//1.1验证通知记录的准确性 签名验证
			String checkFlag = alipayService.alipayCheckSign(notice);
			AssertUtil.hasLength(checkFlag, "调用验证签名接口失败");
			JSONObject jsonObject = JSONObject.fromObject(checkFlag);
			AssertUtil.notNull(jsonObject, "转换JSON对象失败");
			AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "签名验证失败");
			
			//1.2 过滤重复交易信息
			AssertUtil.hasLength(notice.get("out_trade_no"), "通知的交易流水号为空");
			AssertUtil.hasLength(notice.get("total_fee"), "通知的交易金额为空");
			String transactionSerialNumber = notice.get("out_trade_no");
			StringBuffer baseLog = new StringBuffer("【支付宝消费服务").append(transactionSerialNumber).append("】");
			AlipayTransactionRecord record = alipayTransactionRecordService.getByTransactionSerialNumber(transactionSerialNumber);
			if(null != record){
				payInfoLog.info(new StringBuffer(baseLog).append("系统已存在当前交易记录，属于重复信息，本次交易结束！"));
				return record;
			}
			payInfoLog.info(new StringBuffer(baseLog).append("通过重复过滤信息，可以继续进行支付消费动作"));
			//1.3 查询交易下的所有订单
			List<SalesOrder> salesOrderList = salesOrderProducer.findSalesOrderListByTSN(transactionSerialNumber);
			AssertUtil.notEmpty(salesOrderList, new StringBuffer("未找到该交易流水号对应的订单").append(transactionSerialNumber).toString());
			//1.3.1验证金额
			Long consumerPrice = CalculateUtils.converYuantoPenny(notice.get("total_fee"));//支付金额
			OrderClassify orderClassify = orderClassifyContext.getOrderClassify(1);//TODO 可拓展
			Long totalPrice = orderClassify.cacluOrderPayAmount(salesOrderList);
			AssertUtil.isTrue(consumerPrice.longValue() == totalPrice.longValue(), new StringBuffer("订单金额不匹配,连连金额:").append(consumerPrice).append(",订单金额：").append(totalPrice).toString());
			//1.3.2验证订单状态 当前是否是未付款状态
			Boolean statesFlag = _checkSalesOrderStatus(salesOrderList, Constants.STATUS_WAIT);
			AssertUtil.isTrue(statesFlag, "订单状态异常");
			payInfoLog.info(new StringBuffer(baseLog).append("验证完成").toString());
			
			//2.修改订单状态
			for (SalesOrder salesOrder : salesOrderList) {
				salesOrderProducer.editSalesOrderStatus(salesOrder.getOrderNo(), Constants.STATUS_PAY);
				//支付金额
				SalesOrder so = new SalesOrder();
				so.setOrderNo(salesOrder.getOrderNo());
				so.setPayPrice(salesOrder.getTotalPrice());
				salesOrder.setPayStatus(3);
				salesOrderProducer.editSalesOrder(so);
			}
			payInfoLog.info(new StringBuffer(baseLog).append("修改订单状态完成").toString());
			
			//3.计算手续费 处理佣金分成 记录订单手续费，佣金分成情况
			DistributeDto distributeDto = _calculationDistribute_V3_3(salesOrderList,consumerPrice,6);
			AssertUtil.notNull(distributeDto, "金额分配失败");
			payInfoLog.info(new StringBuffer(baseLog).append("金额分配完成").toString());
			
			//4.处理库存，销量
			orderClassify.deductionStock(salesOrderList);
			payInfoLog.info(new StringBuffer(baseLog).append("处理库存销量完成").toString());
			
			//5.金额入账
			Receivables receivables = new Receivables();
			Long amount = consumerPrice - distributeDto.getPayFeeMoney();
			receivables.setAmount(amount);
			receivables.setFee(distributeDto.getPayFeeMoney());
			receivables.setTransactionSerialNumber(transactionSerialNumber);
			receivables.setChannel(3);//捷蓝接口钱包
			String remark = _getPayRemark(salesOrderList);
			receivables.setRemark(remark);
			boolean flag = paymentService.receivables(receivables);
			AssertUtil.isTrue(flag, "金额入账(平台入账,捷蓝手续费入账)");
			payInfoLog.info(new StringBuffer(baseLog).append("金额转入系统钱包和支付宝手续费钱包完成").toString());
			
			//6.记录
			Date date = new Date();
			AlipayTransactionRecord tr = new AlipayTransactionRecord();
			tr.setUserId(salesOrderList.get(0).getUserId());
			tr.setTransactionSerialNumber(transactionSerialNumber);
			tr.setAliSerialNumber(notice.get("trade_no"));
			tr.setCash(consumerPrice);
			tr.setFee(distributeDto.getPayFeeMoney());
			tr.setTrueFee(0l);//TODO 目前协议没有谈好，谈好后补上支付宝实际收取手续费金额，目前10万一下不收
			tr.setType(1);
			tr.setStatus(1);
			tr.setAccountType(Constants.ORDER_TYPE_SW);
			tr.setSettleDate(DateUtil.DateToString(DateUtil.getLastTimeForDays(date, 1), DateStyle.YYYYMMDD));
			tr.setTransactionTime(date);
			tr.setOrderInfo(notice.get("body"));
			tr.setCreateTime(date);
			tr.setUpdateTime(date);
			alipayTransactionRecordService.add(tr);
			payInfoLog.info(new StringBuffer(baseLog).append("记录完成完成").toString());
			return tr;
		} catch (Exception e) {
			payInfoLog.error(e.getMessage(),e);
			throw new PayException(e);
		}
	}
	/**
	 * 组装支付宝接口请求支付参数
	 * @Title: _createAlipayReq 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodsName
	 * @param showUrl
	 * @param transactionSerialNumber
	 * @param price
	 * @return
	 * @author Hongbo Peng
	 */
	private AlipayReq _createAlipayReq(String goodsName,String showUrl,String transactionSerialNumber,String price){
		String host = PropertiesUtil.getContexrtParam(Constants.SWITCH_UNIONPAY_HOST);
		AssertUtil.hasLength(host, "SWITCH_UNIONPAY_HOST未配置");
		String alipay_notify_rul = PropertiesUtil.getContexrtParam("SWITCH_ALIPAY_NOTIFY_URL");
		AssertUtil.hasLength(alipay_notify_rul, "参数【SWITCH_ALIPAY_NOTIFY_URL】未配置");
		String alipay_return_url = PropertiesUtil.getContexrtParam("SWITCH_ALIPAY_RETURN_URL");
		AssertUtil.hasLength(alipay_return_url, "参数【SWITCH_ALIPAY_RETURN_URL】未配置");
		
		AlipayReq req = new AlipayReq();
		req.setNotify_url(host+alipay_notify_rul);
		req.setReturn_url(host+alipay_return_url);
		req.setOut_trade_no(transactionSerialNumber);
		req.setSubject(goodsName);
		req.setTotal_fee(price);
		req.setShow_url("");
		return req;
	}
	
	/**
	 * 根据支付接口返回的参数组装页面提交代码
	 * @Title: _buildRequest 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param jsonObject
	 * @return
	 * @author Hongbo Peng
	 */
	@SuppressWarnings("rawtypes")
	private String _buildRequest(JSONObject jsonObject){
		StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"").append(jsonObject.getString("alipay_gateway"))
                      .append("?_input_charset=").append(jsonObject.getString("_input_charset")).append("\" method=\"get\">");
		
		Iterator keys = jsonObject.keys();
		while (keys.hasNext()) {
			String name = (String)keys.next();
			
			if(JSONNull.getInstance().equals(jsonObject.get(name)))
				continue;
			String value = (String)jsonObject.get(name);
			if("flag".equals(name) || "desc".equals(name) || "alipay_gateway".equals(name))
				continue;
			sbHtml.append("<input type=\"hidden\" name=\"").append(name).append("\" value=\"").append(value).append("\"/>");
		}
		
		sbHtml.append("<input type=\"submit\" value=\"确认\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

        return sbHtml.toString();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	public Map<String, String> lakalaProducer(List<SalesOrder> list,Long userId, Long bankCardId) throws PayException {
		//1.验证参数
		Assert.notNull(userId);
		AssertUtil.notEmpty(list, "未找到要支付的订单列表","支付订单不存在");
		try {
			StringBuilder sb=new StringBuilder();
			for(SalesOrder so:list){
				sb.append(so.getOrderNo()+",");
			}
			//2.获取需要支付的订单集合
			//3.生成交易流水号
			String transactionSerialNumber = paymentService.getTransactionSerialNumber(String.valueOf(userId), Constants.ORDER_TYPE_SW);
			payInfoLog.info("[拉卡拉支付]交易流水号:"+transactionSerialNumber+";支付订单号:["+sb.toString()+"]");
			//4.验证支付金额，订单状态（区分订单类型：普通商品订单，预约订单，预定促销打折订单）
			//4.1获取支付金额
			OrderClassify orderClassify = orderClassifyContext.getOrderClassify(1);//TODO 可拓展
			Long totalPrice = orderClassify.cacluOrderPayAmount(list);
			AssertUtil.isTrue(totalPrice > 0l, "订单付款金额异常","订单错误");
			//String payPrice = CalculateUtils.converPennytoYuan(totalPrice);
			//4.2验证订单状态
			Boolean statsFlag = _checkSalesOrderStatus(list, Constants.STATUS_WAIT);
			AssertUtil.isTrue(statsFlag, "订单状态异常","订单状态异常");
			//5.修改订单交易流水号,支付方式，支付银行卡
            Integer type = list.size() == 1 ? 1 : 2;
            for (int i=0;i<list.size();i++) {
				// 修改多条订单交易流水号为同一流水号 记录付款银行卡编号
                SalesOrder salesOrder = new SalesOrder();
                salesOrder.setOrderNo(list.get(i).getOrderNo());
				salesOrder.setTransactionSerialNumber(transactionSerialNumber);//交易流水号
				salesOrder.setPayChannel(Constants.PAYMENT_LAKALA);//拉卡拉支付
				salesOrder.setPayType(type);//单笔支付还是合并支付
				salesOrder.setPayStatus(1);//支付中状态，限制不可以改价
                salesOrderProducer.editSalesOrder(salesOrder);
			}
			//6.处理商品名称，组装支付参数
			/*String goodsName = orderClassify.dispTransactionGoodsName(list,128);
			AssertUtil.hasLength(goodsName, "支付商品名称异常","订单错误");*/
			//7.组装支付服务 生产支付所需参数
			
			BankCard bc=bankCardProducer.getBankCardById(bankCardId);
			DecimalFormat df=new DecimalFormat("0.00");
			String price=df.format(new BigDecimal(totalPrice).divide(new BigDecimal(100)));
			LakalaReq lr=new LakalaReq();
			lr.setOrderAmount(price);
			lr.setPayeeAmount(price);
			lr.setMerOrderId(transactionSerialNumber);
			lr.setClientName(bc.getName());
			lr.setClientId(bc.getIdentity());
			lr.setCardNo(bc.getBankCard());
			lr.setDateOfExpire(bc.getEndTime());
			lr.setCvv(bc.getCvv());
			lr.setMobile(bc.getCell());
			lr.setBgUrl("");
			Map<String,String> resultMap=lakalaService.lakalaProducer(lr);
			//{smsStatus=1, transactionId=2015091600054162, retCode=0000, ext1=, ext2=, exchangeRate=1.0, retMsg=商户下单：获取支付短信成功, orderTime=20150916163529, merOrderId=1015091616929455, cnyAmount=0.10, currency=CNY}
			payInfoLog.info(resultMap);
			if(!"0000".equals(resultMap.get("retCode")) && lklTest()){
				resultMap.put("retCode", "0000");
				resultMap.put("cnyAmount", price);
				resultMap.put("merOrderId", transactionSerialNumber);
				resultMap.put("transactionId", "20150912");
			}
			return resultMap;
		} catch (Exception e) {
			payInfoLog.error(e.getMessage(),e);
			throw new PayException(e.getMessage(),e);
		}
	}
	
	@Transactional
	public void dealOrderAfterLakalaPay(Map<String,String> resultMap,Long bankCardId){
		List<SalesOrder> salesOrderList=null;
		try{
			String tsn=resultMap.get("merOrderId")!=null?resultMap.get("merOrderId").toString():"";
			LakalaTransactionRecord record = lakalaTransactionRecordService.getByTransactionSerialNumber(tsn);
			if(null != record){
				payInfoLog.info("交易流水号"+tsn+"在系统中已存在，属于重复信息，本次交易结束！");
				return ;
			}
			payInfoLog.info("通过重复过滤信息，可以继续进行支付消费动作,交易流水号"+tsn);
			//1.3 查询交易下的所有订单
			salesOrderList = salesOrderProducer.findSalesOrderListByTSN(tsn);
			AssertUtil.notEmpty(salesOrderList, "未找到该交易流水号"+tsn+"对应的订单");
			//1.3.1验证金额
			Long consumerPrice = CalculateUtils.converYuantoPenny(String.valueOf(resultMap.get("cnyAmount")));//支付金额
			OrderClassify orderClassify = orderClassifyContext.getOrderClassify(1);//TODO 可拓展
			Long totalPrice = orderClassify.cacluOrderPayAmount(salesOrderList);
			AssertUtil.isTrue(consumerPrice.longValue() == totalPrice.longValue(), new StringBuffer("订单金额不匹配,连连金额:").append(consumerPrice).append(",订单金额：").append(totalPrice).toString());
			//1.3.2验证订单状态 当前是否是未付款状态
			Boolean statesFlag = _checkSalesOrderStatus(salesOrderList, Constants.STATUS_WAIT);
			AssertUtil.isTrue(statesFlag, "订单状态异常");
			
			BankCard bc=bankCardProducer.getBankCardByPk(bankCardId);
			//2.修改订单状态
			for (SalesOrder salesOrder : salesOrderList) {
				salesOrderProducer.editSalesOrderStatus(salesOrder.getOrderNo(), Constants.STATUS_PAY);
				//支付金额
				SalesOrder so = new SalesOrder();
				so.setOrderNo(salesOrder.getOrderNo());
				so.setPayPrice(salesOrder.getTotalPrice());
				so.setBankCard(Long.valueOf(bc.getBankCard()));
				salesOrder.setPayStatus(3);
				salesOrderProducer.editSalesOrder(so);
			}
			
			//3.计算手续费 处理佣金分成 记录订单手续费，佣金分成情况
			/** bankType:2,借记卡;3,信用卡*/
			DistributeDto distributeDto = _calculationDistribute_V3_3(salesOrderList,consumerPrice,bc.getBankType());
			AssertUtil.notNull(distributeDto, "金额分配失败");
			
			//4.处理库存，销量
			orderClassify.deductionStock(salesOrderList);
			
			//5.金额入账
			Receivables receivables = new Receivables();
			Long amount = consumerPrice - distributeDto.getPayFeeMoney();
			receivables.setAmount(amount);
			receivables.setFee(distributeDto.getPayFeeMoney());
			receivables.setTransactionSerialNumber(tsn);
			receivables.setChannel(6);//拉卡拉
			String remark=null;
			try{
				remark = _getPayRemark(salesOrderList);
			}catch(Exception e){
				e.printStackTrace();
			}
			receivables.setRemark(remark);
			payInfoLog.info(JSON.toJSON(receivables));
			try{
				boolean flag = paymentService.receivables(receivables);
				AssertUtil.isTrue(flag, "金额入账(平台入账,拉卡拉手续费入账)");
			}catch(Exception e){
				payInfoLog.error(e.getMessage(),e);
			}
			//6.记录
			
			Date date = new Date();
			TransactionRecord tr = new TransactionRecord();
			tr.setUserId(salesOrderList.get(0).getUserId());
			tr.setTransactionSerialNumber(tsn);
			tr.setSerialNumber(String.valueOf(resultMap.get("transactionId")));
			tr.setCash(consumerPrice);
			tr.setFee(distributeDto.getPayFeeMoney());
			tr.setType(1);
			tr.setStatus(1);
			tr.setTransactionTime(date);
			if(bc != null){
				tr.setBankCard(bc.getBankCard());
				tr.setBankCode(bc.getBankCode());
				tr.setBankName(bc.getBankName());
				tr.setCell(bc.getCell());
				tr.setCvv(bc.getCvv());
				tr.setEndTime(bc.getEndTime());
			}
			tr.setOrderInfo("");
			tr.setCreateTime(date);
			tr.setUpdateTime(date);
			transactionRecordService.addTransactionRecord(tr);
			payInfoLog.info("拉卡拉付款完成,"+tsn);
		}catch(Exception e){
			payInfoLog.error("拉卡拉订单处理失败:"+resultMap+","+bankCardId);
			payInfoLog.error(e.getMessage(),e);
			throw new PaymentException(e);
		}
		/** 短信通知*/
		salesOrderBusiness.dealOrderAfterPayV2(salesOrderList);
	}
	@Override
	@Transactional
	public Map<String, String> lakalaConsumer(HttpServletRequest request, String orderNos, Long userId, final Long bankCardId) {
		LakalaReq lr=new LakalaReq();
		String merOrderId = request.getParameter("merOrderId");
		String orderAmount = request.getParameter("orderAmount");
		String payeeAmount = request.getParameter("payeeAmount");
		String msgCode = request.getParameter("msgCode");
		String transactionId = request.getParameter("transactionId");
		lr.setMerOrderId(merOrderId);
		lr.setOrderAmount(orderAmount);
		lr.setPayeeAmount(payeeAmount);
		lr.setMsgCode(msgCode);
		lr.setTransactionId(transactionId);
		payInfoLog.info("拉卡拉付款开始,"+merOrderId);
		final Map<String, String> resultMap=lakalaService.lakalaConsumer(lr);
		payInfoLog.info(resultMap);
		if(!"0000".equals(resultMap.get("retCode")) && lklTest()){
			resultMap.put("retCode", "0000");
			resultMap.put("merOrderId", merOrderId);
			resultMap.put("transactionId", transactionId);
			resultMap.put("cnyAmount", orderAmount);
		}
		if(!"0000".equals(resultMap.get("retCode"))){
			return resultMap;
		}
		Thread t=new Thread(){
			public void run(){
				ContextLoaderListener.getCurrentWebApplicationContext().getBean(PaymentBusiness.class).dealOrderAfterLakalaPay(resultMap, bankCardId);
			}
		};
		Executors.newCachedThreadPool().execute(t);
		
		/*String tsn=resultMap.get("merOrderId")!=null?resultMap.get("merOrderId").toString():"";
		LakalaTransactionRecord record = lakalaTransactionRecordService.getByTransactionSerialNumber(tsn);
		if(null != record){
			payInfoLog.info("交易流水号"+tsn+"在系统中已存在，属于重复信息，本次交易结束！");
			return new HashMap<String,String>();
		}
		payInfoLog.info("通过重复过滤信息，可以继续进行支付消费动作,交易流水号"+tsn);
		//1.3 查询交易下的所有订单
		List<SalesOrder> salesOrderList = salesOrderProducer.findSalesOrderListByTSN(tsn);
		AssertUtil.notEmpty(salesOrderList, "未找到该交易流水号"+tsn+"对应的订单");
		//1.3.1验证金额
		Long consumerPrice = CalculateUtils.converYuantoPenny(String.valueOf(resultMap.get("cnyAmount")));//支付金额
		OrderClassify orderClassify = orderClassifyContext.getOrderClassify(1);//TODO 可拓展
		Long totalPrice = orderClassify.cacluOrderPayAmount(salesOrderList);
		AssertUtil.isTrue(consumerPrice.longValue() == totalPrice.longValue(), new StringBuffer("订单金额不匹配,连连金额:").append(consumerPrice).append(",订单金额：").append(totalPrice).toString());
		//1.3.2验证订单状态 当前是否是未付款状态
		Boolean statesFlag = _checkSalesOrderStatus(salesOrderList, Constants.STATUS_WAIT);
		AssertUtil.isTrue(statesFlag, "订单状态异常");
		
		BankCard bc=bankCardProducer.getBankCardByPk(bankCardId);
		//2.修改订单状态
		for (SalesOrder salesOrder : salesOrderList) {
			salesOrderProducer.editSalesOrderStatus(salesOrder.getOrderNo(), Constants.STATUS_PAY);
			//支付金额
			SalesOrder so = new SalesOrder();
			so.setOrderNo(salesOrder.getOrderNo());
			so.setPayPrice(salesOrder.getTotalPrice());
			so.setBankCard(Long.valueOf(bc.getBankCard()));
			salesOrder.setPayStatus(3);
			salesOrderProducer.editSalesOrder(so);
		}
		
		//3.计算手续费 处理佣金分成 记录订单手续费，佣金分成情况
		*//** bankType:2,借记卡;3,信用卡*//*
		DistributeDto distributeDto = _calculationDistribute_V3_3(salesOrderList,consumerPrice,bc.getBankType());
		AssertUtil.notNull(distributeDto, "金额分配失败");
		
		//4.处理库存，销量
		orderClassify.deductionStock(salesOrderList);
		
		//5.金额入账
		Receivables receivables = new Receivables();
		Long amount = consumerPrice - distributeDto.getPayFeeMoney();
		receivables.setAmount(amount);
		receivables.setFee(distributeDto.getPayFeeMoney());
		receivables.setTransactionSerialNumber(tsn);
		receivables.setChannel(6);//拉卡拉
		String remark=null;
		try{
			remark = _getPayRemark(salesOrderList);
		}catch(Exception e){
			e.printStackTrace();
		}
		receivables.setRemark(remark);
		payInfoLog.info(JSON.toJSON(receivables));
		try{
			boolean flag = paymentService.receivables(receivables);
			AssertUtil.isTrue(flag, "金额入账(平台入账,拉卡拉手续费入账)");
		}catch(Exception e){
			payInfoLog.error(e.getMessage(),e);
		}
		//6.记录
		
		Date date = new Date();
		TransactionRecord tr = new TransactionRecord();
		tr.setUserId(salesOrderList.get(0).getUserId());
		tr.setTransactionSerialNumber(tsn);
		tr.setSerialNumber(String.valueOf(resultMap.get("transactionId")));
		tr.setCash(consumerPrice);
		tr.setFee(distributeDto.getPayFeeMoney());
		tr.setType(1);
		tr.setStatus(1);
		tr.setTransactionTime(date);
		if(bc != null){
			tr.setBankCard(bc.getBankCard());
			tr.setBankCode(bc.getBankCode());
			tr.setBankName(bc.getBankName());
			tr.setCell(bc.getCell());
			tr.setCvv(bc.getCvv());
			tr.setEndTime(bc.getEndTime());
		}
		tr.setOrderInfo("");
		tr.setCreateTime(date);
		tr.setUpdateTime(date);
		transactionRecordService.addTransactionRecord(tr);
		payInfoLog.info("拉卡拉付款完成,"+tsn);*/
		return resultMap;
	}
	
	@Override
	public Map<String, String> getLklPayCode(LakalaReq lr) {
		Map<String, String> resultMap=lakalaService.getPayCode(lr);
		payInfoLog.info(resultMap);
		return resultMap;
	}

	@Override
	public BankCardVo getBankCardMsgByCardNo(String bankCard) {
		BankCardVo vo=null;
		BankInfo bi=paymentService.getBankInfoByBankCard(bankCard);
		if(bi!=null && ("0".equals(bi.getType())||"1".equals(bi.getType()))){
			vo=new BankCardVo();
			vo.setBankName(bi.getBankName());
			vo.setBankCode(bi.getBankCode());
			String type=bi.getType();
			if("0".equals(type)){
				vo.setBankType(2);
			}else{
				vo.setBankType(3);
			}
		}
		return vo;
	}

	@Override
	public Map<String, String> getLklSignCode(LakalaReq lr) {
		Map<String,String> resultMap=lakalaService.getSignCode(lr);
		payInfoLog.info(resultMap);
		return resultMap;
	}

	@Override
	public Map<String, String> lklSign(LakalaReq lr) {
		Map<String,String> resultMap=lakalaService.sign(lr);
		if(!"0000".equals(resultMap.get("retCode")) && lklTest()){
			resultMap.put("retCode", "0000");
			resultMap.put("retMsg", "成功了！！");
		}
		payInfoLog.info(resultMap);
		return resultMap;
	}

	@Override
	public String signCheck(LakalaReq lakalaReq) {
		return lakalaService.signCheck(lakalaReq);
	}

	@Override
	public void addLklSignBankCard(BankCard bankCard, Long userId, Integer type) {
		User user=userProducer.getUserById(userId);
		BankCard bc=bankCardProducer.getBankCardByCardNoAndUserId(bankCard.getBankCard(), userId);
		BankCardVo vo = getBankCardMsgByCardNo(bankCard.getBankCard());
		Date date=new Date();
		if(bc==null){
			bankCard.setIdentity(user.getIdentity());
			bankCard.setRemoteId(userId.toString());
			if(type==1){
				bankCard.setLklSign(1);
			}else{
				bankCard.setLklSign(2);
			}
			bankCard.setRemoteType(1);
			bankCard.setStatus(1);
			bankCard.setName(user.getContacts());
			bankCard.setBankName(vo.getBankName());
			bankCard.setBankCode(vo.getBankCode());
			bankCard.setBankType(vo.getBankType());
			bankCard.setType(1);
			bankCard.setIsDefault(0);
			bankCard.setCreateTime(date);
			bankCard.setUpdateTime(date);
			bankCardProducer.addBankCard(bankCard);
		}else{
			bankCard.setId(bc.getId());
			bankCard.setIdentity(user.getIdentity());
			bankCard.setRemoteId(userId.toString());
			bankCard.setLklSign(1);
			bankCard.setRemoteType(1);
			bankCard.setStatus(1);
			bankCard.setName(user.getContacts());
			bankCard.setBankName(vo.getBankName());
			bankCard.setBankCode(vo.getBankCode());
			bankCard.setBankType(vo.getBankType());
			bankCard.setType(1);
			bankCard.setUpdateTime(date);
			bankCardProducer.editBankCard(bankCard);
		}
	}
	
	@Override
	@Transactional
	public WalletTransactionRecord redenvelopeWalletPayConsumer(Long userId, String paymentCode, Long id) {
		try {
			Assert.notNull(userId, "用户ID为空,钱包支付失败");
			SendRedenvelopeRecord redenvelope=redenvelopeProducer.getSendRocordById(id);
			AssertUtil.isTrue(redenvelope!=null && redenvelope.getStatus()==0, "红包状态异常","当前红包不能支付");
			Long consumerPrice = redenvelope.getAmount();
			payInfoLog.info(new StringBuffer("【红包钱包支付】红包ID:").append(id).append(",金额:").append(consumerPrice).toString());
			String transactionSerialNumber = paymentService.getTransactionSerialNumber(userId.toString(), Constants.ORDER_TYPE_SW);
			AssertUtil.isTrue(StringUtils.isNotBlank(transactionSerialNumber), "获取交易流水号失败");
			payInfoLog.info(new StringBuffer("【红包钱包支付】生成订单流水号为:").append(transactionSerialNumber).toString());
			Account account=accountProducer.getAccountByUserId(userId);
			AssertUtil.isTrue(StringUtils.isNotBlank(account.getPaymentCode()), "支付密码不存在", "支付密码不存在");
			//当支付金额为0时,不调用支付服务
			if(consumerPrice.equals(0l)){
				payInfoLog.info("【钱包支付】支付金额0元,不调用支付服务钱包付!");
			}else{
				Assert.hasLength(paymentCode, "支付密码为空,钱包支付失败");
				Wallet wallet = new Wallet();
	            wallet.setUserId(userId.toString());
	            wallet.setTotalPrice(consumerPrice);
	            wallet.setPaymendCode(paymentCode);
	            wallet.setTransactionSerialNumber(transactionSerialNumber);
	            //账单流水备注信息
	            String remark = "红包钱包支付";
	            wallet.setRemark(remark);
	            boolean payFlag = paymentService.paymentByWallet(wallet);
				AssertUtil.isTrue(payFlag, "调用钱包付款接口发生异常");
			}
			
            Date date = new Date();
            WalletTransactionRecord wtr = new WalletTransactionRecord();
            wtr.setUserId(userId);
            wtr.setTransactionSerialNumber(transactionSerialNumber);
            wtr.setCash(consumerPrice);
            wtr.setType(1);
            wtr.setStatus(1);
            wtr.setAccountType(Constants.ORDER_TYPE_REDENVELOPE);
            wtr.setSettleDate(DateUtil.DateToString(DateUtil.getLastTimeForDays(date, 1), DateStyle.YYYYMMDD));
            wtr.setTransactionTime(date);
            wtr.setUpdateTime(date);
            wtr.setCreateTime(date);
            walletTransactionRecordService.addWalletTransactionRecord(wtr);
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
	    }catch(Exception e){
			payInfoLog.error(_getErrorInfoMsg(e.getMessage(), "红包钱包支付发生异常，无法完成支付", new StringBuffer("{\"红包ID\":")
            .append(id).append(",\"userId\":\"").append(userId).append("\"}").toString()),e);
            throw new PayException(e);
        }
	}
	public static boolean lklTest(){
		return false;
	}
}
