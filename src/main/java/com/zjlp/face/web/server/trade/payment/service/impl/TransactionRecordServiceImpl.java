package com.zjlp.face.web.server.trade.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.trade.payment.dao.TransactionRecordDao;
import com.zjlp.face.web.server.trade.payment.domain.TransactionRecord;
import com.zjlp.face.web.server.trade.payment.service.TransactionRecordService;
@Service
public class TransactionRecordServiceImpl implements TransactionRecordService {

	@Autowired
	private TransactionRecordDao transactionRecordDao;
	
	@Override
	public void addTransactionRecord(TransactionRecord transactionRecord) {
		transactionRecordDao.addTransactionRecord(transactionRecord);
	}

	@Override
	public TransactionRecord getTransactionRecordByTSN(
			String transactionSerialNumber) {
		return transactionRecordDao.getTransactionRecordByTSN(transactionSerialNumber);
	}

}
