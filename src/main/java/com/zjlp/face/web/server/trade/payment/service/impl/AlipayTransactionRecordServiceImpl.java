package com.zjlp.face.web.server.trade.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.trade.payment.dao.AlipayTransactionRecordDao;
import com.zjlp.face.web.server.trade.payment.domain.AlipayTransactionRecord;
import com.zjlp.face.web.server.trade.payment.service.AlipayTransactionRecordService;
@Service
public class AlipayTransactionRecordServiceImpl implements
		AlipayTransactionRecordService {

	@Autowired
	private AlipayTransactionRecordDao alipayTransactionRecordDao;
	
	@Override
	public AlipayTransactionRecord getByTransactionSerialNumber(
			String transactionSerialNumber) {
		return alipayTransactionRecordDao.getByTransactionSerialNumber(transactionSerialNumber);
	}

	@Override
	public void add(AlipayTransactionRecord alipayTransactionRecord) {
		alipayTransactionRecordDao.add(alipayTransactionRecord);
	}

}
