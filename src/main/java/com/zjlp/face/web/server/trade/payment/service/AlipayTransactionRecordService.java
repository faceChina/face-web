package com.zjlp.face.web.server.trade.payment.service;

import com.zjlp.face.web.server.trade.payment.domain.AlipayTransactionRecord;

public interface AlipayTransactionRecordService {

	AlipayTransactionRecord getByTransactionSerialNumber(String transactionSerialNumber);
	
	void add(AlipayTransactionRecord alipayTransactionRecord);
}
