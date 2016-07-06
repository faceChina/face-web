package com.zjlp.face.web.server.trade.payment.dao;

import java.util.List;

import com.zjlp.face.web.server.trade.payment.domain.PopularizeCommissionRecord;

public interface PopularizeCommissionRecordDao {

	
	void addPopularizeCommissionRecord(PopularizeCommissionRecord popularizeCommissionRecord);
	
	void editPopularizeCommissionRecord(PopularizeCommissionRecord popularizeCommissionRecord);
	
	List<PopularizeCommissionRecord> getByOrderNo(String orderNo);
	
	List<PopularizeCommissionRecord> findByPopuCell(String popuCell);
	
	PopularizeCommissionRecord getByIdForLock(Long id);

	Long getUsersPopularizeAmount(Long userId);
}
