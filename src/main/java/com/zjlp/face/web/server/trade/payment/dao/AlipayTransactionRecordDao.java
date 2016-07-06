package com.zjlp.face.web.server.trade.payment.dao;

import com.zjlp.face.web.server.trade.payment.domain.AlipayTransactionRecord;

public interface AlipayTransactionRecordDao {

	AlipayTransactionRecord getByTransactionSerialNumber(String transactionSerialNumber);
	
	void add(AlipayTransactionRecord alipayTransactionRecord);
}
