package com.zjlp.face.web.server.trade.payment.producer.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.account.dto.DivideCommissions;
import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.account.service.PaymentService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.operation.marketing.producer.MarketingProducer;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.producer.MemberProducer;
import com.zjlp.face.web.server.trade.account.producer.AccountProducer;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderDto;
import com.zjlp.face.web.server.trade.order.producer.PurchaseOrderProducer;
import com.zjlp.face.web.server.trade.order.producer.SalesOrderProducer;
import com.zjlp.face.web.server.trade.payment.domain.CommissionRecord;
import com.zjlp.face.web.server.trade.payment.producer.PaymentProducer;
import com.zjlp.face.web.server.trade.payment.service.CommissionRecordService;
import com.zjlp.face.web.server.trade.payment.service.PopularizeCommissionRecordService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;
import com.zjlp.face.web.util.Logs;
@Service
public class PaymentProducerImpl implements PaymentProducer {
	@Autowired
	private SalesOrderProducer salesOrderProducer;
	@Autowired(required=false)
	private PaymentService paymentService;
	@Autowired
	private CommissionRecordService commissionRecordService;
	@Autowired
	private AccountProducer accountProducer;
	@Autowired
	private ShopProducer shopProducer;
	@Autowired
	private MemberProducer memberProducer;
	@Autowired
	private PopularizeCommissionRecordService popularizeCommissionRecordService;
	@Autowired
	private MarketingProducer marketingProducer;
	@Autowired
	private PurchaseOrderProducer purchaseOrderProducer;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void sumCardAmountAndInteger(String orderNo) throws PaymentException {
		Assert.hasLength(orderNo,"订单号为空");
		try {
			SalesOrder salesOrder = salesOrderProducer.getSalesOrderByOrderNo(orderNo);
			//会员卡支付不累计消费额
			if(Constants.PAYMENT_MEMBER_CARD.intValue() == salesOrder.getPayChannel().intValue()){
				return;
			}
			AssertUtil.notNull(salesOrder, "没有找到该订单");
			AssertUtil.isTrue(salesOrder.getStatus().intValue() == Constants.STATUS_RECEIVE,"订单状态非已收货");
			boolean flag = paymentService.sumConsumeAmount(salesOrder.getUserId(), salesOrder.getPayPrice());
			AssertUtil.isTrue(flag, "累计用户消费金额失败");
			
			//累计用户在当前卖家的消费金额
			boolean mflag = memberProducer.sumConsumAmount(salesOrder.getUserId(), salesOrder.getShopNo(), salesOrder.getPayPrice());
			AssertUtil.isTrue(mflag, "累计用户在当前卖家的消费金额失败");
		} catch (Exception e) {
			e.printStackTrace();
			throw new PaymentException("累计消费额发生异常",e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void divideCommissionsToAccount(String orderNo)
			throws PaymentException {
		Assert.hasLength(orderNo,"订单号为空");
		try {
			SalesOrder salesOrder = salesOrderProducer.getSalesOrderByOrderNo(orderNo);
			AssertUtil.notNull(salesOrder, "没有找到该订单");
			AssertUtil.isTrue(salesOrder.getStatus().intValue() == Constants.STATUS_RECEIVE,"订单状态非已收货");
			
			if(salesOrder.getPayPrice().longValue() == 0){
				return;
			}
			
			List<CommissionRecord> list = commissionRecordService.findByOrderNo(orderNo);
        	List<DivideCommissions> divideCommissionsList = new ArrayList<DivideCommissions>();
        	for (CommissionRecord cr : list) {
        		Integer type = null;
        		String remark = null;
				if( 1 == cr.getType().intValue() ){
					type = 11;//商户利益
					remark = _getDivideCommissionsRemark(cr.getId(),0);
				}
				if( 2 == cr.getType().intValue() || 3 == cr.getType().intValue() ){
					type = 13;//佣金利益
					remark = _getDivideCommissionsRemark(cr.getId(),1);
				}
				if( 4 == cr.getType().intValue() ){
					type = 18;//分销利益
					remark = _getDivideCommissionsRemark(cr.getId(),0);
				}
				if( 5 == cr.getType().intValue() ){
					type = 19;//全民推广利益
				}
				if( 6 == cr.getType().intValue() ){
					type = 20;//员工利益
				}
				if(7==cr.getType().intValue()){
					type=21;//BF收益
					remark = _getDivideCommissionsRemark(cr.getId(),1);
				}
				DivideCommissions divideCommissions = new DivideCommissions();
				divideCommissions.setMoney(cr.getCommission());
				divideCommissions.setTransactionSerialNumber(salesOrder.getTransactionSerialNumber());
				divideCommissions.setType(type);
				divideCommissions.setAccountId(cr.getAccountId());
				divideCommissions.setRemark(remark);
				divideCommissionsList.add(divideCommissions);
			}
        	boolean divideFlag = paymentService.divideCommission(divideCommissionsList);
            AssertUtil.isTrue(divideFlag, "调用金额到账接口发生异常");
            boolean extractFlag = this.extractAmount(divideCommissionsList);
            AssertUtil.isTrue(extractFlag, "金额自动转出失败");
            // 修改记录为到账
            commissionRecordService.changeIsToAccount(orderNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PaymentException("订单收货后金额到账失败",e);
		}
	}
	
	private String _getDivideCommissionsRemark(Long id,int flag) throws Exception{
		String remark = "";
		String shopName = null;
		if (1 ==  flag) {
			 shopName = commissionRecordService.getSubbranchShopName(id);
		}else {
			 shopName = commissionRecordService.getShopName(id);
		}
		remark = "从"+shopName+"店铺转入";
		return remark;
	}
	
	/**
	 * 
	 * @Title: extractAmount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param divideCommissionsList
	 * @return
	 * @throws PaymentException
	 * @date 2015年4月3日 下午3:58:58  
	 * @author lys
	 */
	@Transactional
	private boolean extractAmount(List<DivideCommissions> divideCommissionsList) throws PaymentException {
		try {
			AssertUtil.notEmpty(divideCommissionsList, "Param[divideCommissionsList] can not be empty.");
			for (DivideCommissions dc : divideCommissionsList) {
				Logs.print(dc);
				//非利益类型
				if (null == dc || null == dc.getType() || (11 != dc.getType().intValue()
						&& 18 != dc.getType().intValue() && 20 != dc.getType().intValue())) {
					continue;
				}
				//参数准备
				Account shopAccount = accountProducer.getAccountById(dc.getAccountId());
				Logs.print(shopAccount);
				Logs.print(Account.REMOTE_TYPE_SHOP);
				AssertUtil.isTrue(Account.REMOTE_TYPE_SHOP.equals(shopAccount.getRemoteType()), 
						"Param[id={}] is not a account of shop.");
				Shop shop = shopProducer.getShopByNo(shopAccount.getRemoteId());
				//金额转出
				accountProducer.extractAmount(shop.getUserId(), shop.getNo(), 
						dc.getMoney(), dc.getTransactionSerialNumber(),dc.getRemark());
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PaymentException("金额自动转出失败", e);
		}
	}
	
	/**
	 * 赠送积分
	 * @Title: _comsuerSendInteger
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param orderNo
	 * @throws PaymentException
	 * @return void
	 * @author phb
	 * @date 2015年6月4日 下午3:32:22
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void comsuerSendInteger(String orderNo) throws PaymentException{
		Assert.hasLength(orderNo,"订单号为空");
		try {
			SalesOrder salesOrder = salesOrderProducer.getSalesOrderByOrderNo(orderNo);
			AssertUtil.notNull(salesOrder, "没有找到该订单");
			//是否是店销
//			if(1 != salesOrder.getOrderCategory().intValue()){
//				return;
//			}
			//如果有会员卡，就消费送积分
			MemberCard memberCard = memberProducer.getMemberCardByUserIdAndShopNo(salesOrder.getUserId(), salesOrder.getShopNo());
			if(null != memberCard){
				marketingProducer.consumptionOfBonusPoints(salesOrder.getShopNo(),salesOrder.getUserId(),salesOrder.getObtainIntegral());
			}
		} catch (Exception e) {
			throw new PaymentException(e);
		}
	}

	@Override
	public Long getUserFreezeAmount(Long accountId) {
		Long userFreezeAmount = commissionRecordService.getUserFreezeAmount(accountId);
		return userFreezeAmount == null ? 0l : userFreezeAmount;
	}

	@Override
	public Long getUsersShopsFreezeAmount(Long userId) {
		Long usersShopsFreezeAmount = commissionRecordService.getUsersShopsFreezeAmount(userId);
		return usersShopsFreezeAmount == null ? 0l : usersShopsFreezeAmount;
	}

	@Override
	public Long getUsersPopularizeAmount(Long userId) {
		Long usersPopularizeAmount = popularizeCommissionRecordService.getUsersPopularizeAmount(userId);
		return usersPopularizeAmount == null ? 0l : usersPopularizeAmount;
	}

	@Override
	public Long getShopFreezeIncome(String shopNo) {
		Long shopFreezeIncome = commissionRecordService.getShopFreezeIncome(shopNo);
		return shopFreezeIncome == null ? 0l : shopFreezeIncome;
	}

	@Override
	public Long getShopIncomeByTime(String shopNo, Date startTime, Date endTime) {
		Long shopIncome = commissionRecordService.getShopIncomeByTime(shopNo, startTime, endTime);
		return shopIncome == null ? 0l : shopIncome;
	}

}
