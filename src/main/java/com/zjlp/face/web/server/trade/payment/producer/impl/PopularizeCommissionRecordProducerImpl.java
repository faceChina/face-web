package com.zjlp.face.web.server.trade.payment.producer.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.account.dto.DivideCommissions;
import com.zjlp.face.account.service.PaymentService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.server.trade.account.producer.AccountProducer;
import com.zjlp.face.web.server.trade.payment.domain.PopularizeCommissionRecord;
import com.zjlp.face.web.server.trade.payment.producer.PopularizeCommissionRecordProducer;
import com.zjlp.face.web.server.trade.payment.service.PopularizeCommissionRecordService;
import com.zjlp.face.web.server.user.user.domain.User;

@Service
public class PopularizeCommissionRecordProducerImpl implements
		PopularizeCommissionRecordProducer {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private PopularizeCommissionRecordService popularizeCommissionRecordService;
	
	@Autowired(required=false)
	private PaymentService paymentService;
	
	@Autowired
	private AccountProducer accountProducer;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void registeredManageRecord(User user) {
		Assert.notNull(user,"参数为空");
		//根据手机号码，查询出所有 未激活账户 的记录
		List<PopularizeCommissionRecord> list = popularizeCommissionRecordService.findRecordByPopuCell(user.getLoginAccount());
		for (PopularizeCommissionRecord r : list) {
			//锁定记录
			PopularizeCommissionRecord record = popularizeCommissionRecordService.getByIdForLock(r.getId());
			if(0 == record.getOrderStatus().intValue()){
				//未完成订单记录的处理  将status 改为未到账，修改用户编号
				popularizeCommissionRecordService.editRecordStatusNoArrival(record.getId(), user.getId());
			} else if(1 == record.getOrderStatus().intValue()){
				//已完成订单记录的处理 金额到账 将status 改为已到帐 ，修改用户编号 订单状态改为已完成
				Account account = accountProducer.getAccountByUserId(user.getId());
				AssertUtil.notNull(account, "用户钱包为空");
				List<DivideCommissions> divideCommissionsList = new ArrayList<DivideCommissions>();
				DivideCommissions divideCommissions = new DivideCommissions();
				divideCommissions.setMoney(record.getCommission());
				divideCommissions.setTransactionSerialNumber(record.getTransactionSerialNumber());
				divideCommissions.setType(19);//全民推广利益
				divideCommissions.setAccountId(account.getId());
				divideCommissionsList.add(divideCommissions);
				//到账
				boolean divideFlag = paymentService.divideCommission(divideCommissionsList);
	            AssertUtil.isTrue(divideFlag, "调用金额到账接口发生异常");
	            //修改用户编号 订单状态改为已完成
	            popularizeCommissionRecordService.editRecordOrderComplete(record.getId(), user.getId());
	            //将status 改为已到帐
	            popularizeCommissionRecordService.editRecordStatusArrival(record.getId(),null);
			} else {
				//异常状态
				log.error(new StringBuffer("当前记录状态异常:").append(record.getId()));
			}
		}
		
	}

	@Override
	public void compileOrderManageRecord(String orderNo) {
		Assert.hasLength(orderNo,"参数为空");
		List<PopularizeCommissionRecord> list = popularizeCommissionRecordService.getRecordByOrderNo(orderNo);
		if(null == list || list.isEmpty()){
			return;
		}
		for (PopularizeCommissionRecord r : list) {
			//锁定记录
			PopularizeCommissionRecord record = popularizeCommissionRecordService.getByIdForLock(r.getId());
			if(0 == record.getStatus().intValue()){
				//未激活账户 修改为 订单完成
				popularizeCommissionRecordService.editRecordOrderComplete(record.getId(), null);
			} else if (1 == record.getStatus().intValue()){
				//已激活账户 金额到账 将status 改为已到帐 ，修改用户编号 订单状态改为已完成
				Account account = accountProducer.getAccountByUserId(record.getUserId());
				AssertUtil.notNull(account, "用户钱包为空");
				List<DivideCommissions> divideCommissionsList = new ArrayList<DivideCommissions>();
				DivideCommissions divideCommissions = new DivideCommissions();
				divideCommissions.setMoney(record.getCommission());
				divideCommissions.setTransactionSerialNumber(record.getTransactionSerialNumber());
				divideCommissions.setType(19);//全民推广利益
				divideCommissions.setAccountId(account.getId());
				divideCommissionsList.add(divideCommissions);
				//到账
				boolean divideFlag = paymentService.divideCommission(divideCommissionsList);
	            AssertUtil.isTrue(divideFlag, "调用金额到账接口发生异常");
	            //修改用户编号 订单状态改为已完成
	            popularizeCommissionRecordService.editRecordOrderComplete(record.getId(), null);
	            //将status 改为已到帐
	            popularizeCommissionRecordService.editRecordStatusArrival(record.getId(),null);
			} else {
				//异常状态
				log.error(new StringBuffer("当前记录状态异常:").append(record.getId()));
			}
		}
	}

}
