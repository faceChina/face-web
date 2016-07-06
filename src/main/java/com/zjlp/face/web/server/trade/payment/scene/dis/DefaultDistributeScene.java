package com.zjlp.face.web.server.trade.payment.scene.dis;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.PayException;
import com.zjlp.face.web.server.trade.account.producer.AccountProducer;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.payment.domain.CommissionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;
import com.zjlp.face.web.server.trade.payment.service.CommissionRecordService;
/**
 * 默认分配金额场景
* @ClassName: DefaultDistributeScene 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author phb 
* @date 2015年5月12日 下午2:10:12 
*
 */
@Component("defaultDistributeScene")
public class DefaultDistributeScene implements DistributeScene {

	@Autowired
	private AccountProducer accountProducer;
	
	@Autowired
	private CommissionRecordService commissionRecordService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "PayException"})
	public void distributeCalculation(SalesOrder salesOrder, FeeDto orderFee,Long amount,Date date)
			throws PayException {
		AssertUtil.notNull(salesOrder, "订单对象为空");
		AssertUtil.notNull(orderFee, "订单手续费对象为空");
		try {
			//计算商户支付完手续费后所得收益
			Long orderPrice = amount;
			Long totalfee = CalculateUtils.getNewVal(orderFee.getPayFee(), orderFee.getPlatformFee());
			Long sellerPrice = CalculateUtils.getDifference(orderPrice,totalfee);
			AssertUtil.isTrue(sellerPrice >= 0, "交易金额不足以支付手续费");
			//获取商户钱包
			Account shopAccount = accountProducer.getAccountByRemoteId(salesOrder.getShopNo(), Account.REMOTE_TYPE_SHOP);
			AssertUtil.notNull(shopAccount, "没有找到店铺钱包记录");
			//记录商户待获得收益
			CommissionRecord cr = new CommissionRecord();
			cr.setAccountId(shopAccount.getId());
			cr.setCommission(sellerPrice);
			cr.setIsToAccount(0);
			cr.setTransactionSerialNumber(salesOrder.getTransactionSerialNumber());
			cr.setOrderNo(salesOrder.getOrderNo());
			cr.setOrderStates(1);
			cr.setType(1);//商家利益
			cr.setIsToType(1);
			cr.setCreateTime(date);
			cr.setUpdateTime(date);
			commissionRecordService.addCommissionRecord(cr);
		} catch (Exception e) {
			throw new PayException("默认分配金额场景发生异常",e);
		}
	}

}
