package com.zjlp.face.web.server.trade.payment.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.trade.payment.dao.PopularizeCommissionRecordDao;
import com.zjlp.face.web.server.trade.payment.domain.PopularizeCommissionRecord;
import com.zjlp.face.web.server.trade.payment.service.PopularizeCommissionRecordService;
@Service
public class PopularizeCommissionRecordServiceImpl implements
		PopularizeCommissionRecordService {

	@Autowired
	private PopularizeCommissionRecordDao popularizeCommissionRecordDao;
	
	@Override
	public void addRecord(PopularizeCommissionRecord popularizeCommissionRecord) {
		popularizeCommissionRecordDao.addPopularizeCommissionRecord(popularizeCommissionRecord);
	}

	@Override
	public void editRecordOrderComplete(Long id,Long userId) {
		PopularizeCommissionRecord record = new PopularizeCommissionRecord();
		record.setId(id);
		record.setUserId(userId);
		record.setOrderStatus(1);
		record.setUpdateTime(new Date());
		popularizeCommissionRecordDao.editPopularizeCommissionRecord(record);
	}

	@Override
	public void editRecordStatusNoArrival(Long id,Long userId) {
		PopularizeCommissionRecord record = new PopularizeCommissionRecord();
		record.setId(id);
		record.setUserId(userId);
		record.setStatus(1);
		record.setUpdateTime(new Date());
		popularizeCommissionRecordDao.editPopularizeCommissionRecord(record);
	}

	@Override
	public void editRecordStatusArrival(Long id,Long userId) {
		PopularizeCommissionRecord record = new PopularizeCommissionRecord();
		record.setId(id);
		record.setUserId(userId);
		record.setStatus(2);
		record.setUpdateTime(new Date());
		popularizeCommissionRecordDao.editPopularizeCommissionRecord(record);
	}

	@Override
	public List<PopularizeCommissionRecord> getRecordByOrderNo(String orderNo) {
		return popularizeCommissionRecordDao.getByOrderNo(orderNo);
	}

	@Override
	public List<PopularizeCommissionRecord> findRecordByPopuCell(String popuCell) {
		return popularizeCommissionRecordDao.findByPopuCell(popuCell);
	}

	@Override
	public PopularizeCommissionRecord getByIdForLock(Long id) {
		return popularizeCommissionRecordDao.getByIdForLock(id);
	}

	@Override
	public Long getUsersPopularizeAmount(Long userId) {
		return popularizeCommissionRecordDao.getUsersPopularizeAmount(userId);
	}

}
