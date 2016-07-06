package com.zjlp.face.web.server.trade.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.trade.payment.dao.WechatTransactionRecordDao;
import com.zjlp.face.web.server.trade.payment.domain.WechatTransactionRecord;
import com.zjlp.face.web.server.trade.payment.service.WechatTransactionRecordService;
@Service
public class WechatTransactionRecordServiceImpl implements
		WechatTransactionRecordService {

	@Autowired
	private WechatTransactionRecordDao wechatTransactionRecordDao;
	
	@Override
	public void addWechatTransactionRecord(
			WechatTransactionRecord wechatTransactionRecord) {
		wechatTransactionRecordDao.addWechatTransactionRecord(wechatTransactionRecord);
	}

	@Override
	public WechatTransactionRecord getWechatTransactionRecordByTSN(
			String transactionSerialNumber) {
		return wechatTransactionRecordDao.getWechatTransactionRecordByTSN(transactionSerialNumber);
	}

}
