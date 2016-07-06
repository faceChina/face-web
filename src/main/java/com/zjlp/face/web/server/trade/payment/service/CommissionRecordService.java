package com.zjlp.face.web.server.trade.payment.service;

import java.util.Date;
import java.util.List;

import com.zjlp.face.web.server.trade.payment.domain.CommissionRecord;

public interface CommissionRecordService {

	void addCommissionRecord(CommissionRecord commissionRecord);
	
	void changeIsToAccount(String orderNo);
	
	List<CommissionRecord> findByOrderNo(String orderNo);

	Long getUserFreezeAmount(Long accountId);

	Long getUsersShopsFreezeAmount(Long userId);

	Long getShopFreezeIncome(String shopNo);

	Long getShopIncomeByTime(String shopNo, Date startTime, Date endTime);
	
	String getShopName(Long id);
	
	String getSubbranchShopName(Long id);
}
