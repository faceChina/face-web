package com.zjlp.face.web.server.trade.payment.dao;

import com.zjlp.face.web.server.trade.payment.domain.TransactionRecord;

public interface TransactionRecordDao {

	void addTransactionRecord(TransactionRecord transactionRecord);
	
	TransactionRecord getTransactionRecordByTSN(String transactionSerialNumber);
}
