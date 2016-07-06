package com.zjlp.face.web.server.trade.payment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.trade.payment.dao.WalletTransactionRecordDao;
import com.zjlp.face.web.server.trade.payment.domain.WalletTransactionRecord;
import com.zjlp.face.web.server.trade.payment.service.WalletTransactionRecordService;
@Service
public class WalletTransactionRecordServiceImpl implements
		WalletTransactionRecordService {

	@Autowired
	private WalletTransactionRecordDao walletTransactionRecordDao;
	
	@Override
	public void addWalletTransactionRecord(
			WalletTransactionRecord walletTransactionRecord) {
		walletTransactionRecordDao.addWalletTransactionRecord(walletTransactionRecord);
	}

}
