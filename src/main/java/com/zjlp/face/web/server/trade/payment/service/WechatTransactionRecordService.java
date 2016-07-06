package com.zjlp.face.web.server.trade.payment.service;

import com.zjlp.face.web.server.trade.payment.domain.WechatTransactionRecord;

public interface WechatTransactionRecordService {

	void addWechatTransactionRecord(WechatTransactionRecord wechatTransactionRecord);
	
	WechatTransactionRecord getWechatTransactionRecordByTSN(String transactionSerialNumber);
}
