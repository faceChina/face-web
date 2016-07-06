package com.zjlp.face.web.server.trade.payment.service;

import com.zjlp.face.web.server.trade.payment.domain.LakalaTransactionRecord;


public interface LakalaTransactionRecordService {

	LakalaTransactionRecord getByTransactionSerialNumber(String transactionSerialNumber);
	
	void add(LakalaTransactionRecord lakalaTransactionRecord);
}
