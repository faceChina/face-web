package com.zjlp.face.web.server.trade.payment.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.trade.payment.dao.CommissionRecordDao;
import com.zjlp.face.web.server.trade.payment.domain.CommissionRecord;
import com.zjlp.face.web.server.trade.payment.service.CommissionRecordService;
@Service
public class CommissionRecordServiceImpl implements CommissionRecordService {

	@Autowired
	private CommissionRecordDao commissionRecordDao;
	
	@Override
	public void addCommissionRecord(CommissionRecord commissionRecord) {
		commissionRecordDao.addCommissionRecord(commissionRecord);
	}

	@Override
	public void changeIsToAccount(String orderNo) {
		CommissionRecord commissionRecord = new CommissionRecord();
		commissionRecord.setOrderNo(orderNo);
		commissionRecord.setIsToAccount(1);
		commissionRecord.setUpdateTime(new Date());
		commissionRecordDao.changeIsToAccout(commissionRecord);
	}

	@Override
	public List<CommissionRecord> findByOrderNo(String orderNo) {
		return commissionRecordDao.findByOrderNo(orderNo);
	}

	@Override
	public Long getUserFreezeAmount(Long accountId) {
		return commissionRecordDao.getUserFreezeAmount(accountId);
	}

	@Override
	public Long getUsersShopsFreezeAmount(Long userId) {
		return commissionRecordDao.getUsersShopsFreezeAmount(userId);
	}

	@Override
	public Long getShopFreezeIncome(String shopNo) {
		return commissionRecordDao.getShopFreezeIncome(shopNo);
	}

	@Override
	public Long getShopIncomeByTime(String shopNo, Date startTime, Date endTime) {
		return commissionRecordDao.getShopIncomeByTime(shopNo, startTime, endTime);
	}

	@Override
	public String getShopName(Long id) {
		return commissionRecordDao.getShopName(id);
	}

	@Override
	public String getSubbranchShopName(Long id) {
		return commissionRecordDao.getSubbranchShopName(id);
	}

}
