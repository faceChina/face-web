package com.zjlp.face.web.server.trade.payment.dao;

import com.zjlp.face.web.server.trade.payment.domain.WechatTransactionRecord;

public interface WechatTransactionRecordDao {

	void addWechatTransactionRecord(WechatTransactionRecord wechatTransactionRecord);
	
	WechatTransactionRecord getWechatTransactionRecordByTSN(String transactionSerialNumber);
}
