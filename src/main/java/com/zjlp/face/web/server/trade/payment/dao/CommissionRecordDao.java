package com.zjlp.face.web.server.trade.payment.dao;

import java.util.Date;
import java.util.List;

import com.zjlp.face.web.server.trade.payment.domain.CommissionRecord;

public interface CommissionRecordDao {

	void addCommissionRecord(CommissionRecord commissionRecord);
	
	void changeIsToAccout(CommissionRecord commissionRecord);
	
	void editCommissionRecord(CommissionRecord commissionRecord);
	
	List<CommissionRecord> findByOrderNo(String orderNo);

	Long getUserFreezeAmount(Long accountId);

	Long getUsersShopsFreezeAmount(Long userId);

	Long getShopFreezeIncome(String shopNo);

	Long getShopIncomeByTime(String shopNo, Date startTime, Date endTime);
	
	String getShopName(Long id);
	
	String getSubbranchShopName(Long id);
}
