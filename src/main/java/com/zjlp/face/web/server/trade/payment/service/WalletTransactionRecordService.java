package com.zjlp.face.web.server.trade.payment.service;

import com.zjlp.face.web.server.trade.payment.domain.WalletTransactionRecord;

public interface WalletTransactionRecordService {

	/**
	 * 添加钱包交易记录
	 * @Title: addWalletTransactionRecord
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param walletTransactionRecord
	 * @return void
	 * @author phb
	 * @date 2015年3月14日 上午10:28:03
	 */
	void addWalletTransactionRecord(WalletTransactionRecord walletTransactionRecord);
}
