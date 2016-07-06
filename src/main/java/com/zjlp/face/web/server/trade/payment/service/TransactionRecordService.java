package com.zjlp.face.web.server.trade.payment.service;

import com.zjlp.face.web.server.trade.payment.domain.TransactionRecord;

public interface TransactionRecordService {
	/**
	 * 添加交易记录
	 * @Title: addTransactionRecord
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param transactionRecord
	 * @return void
	 * @author phb
	 * @date 2015年3月14日 上午10:27:02
	 */
	void addTransactionRecord(TransactionRecord transactionRecord);
	/**
	 * 根据交易流水号查询交易记录
	 * @Title: getTransactionRecordByTSN
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param transactionSerialNumber
	 * @return
	 * @return TransactionRecord
	 * @author phb
	 * @date 2015年3月14日 上午10:27:10
	 */
	TransactionRecord getTransactionRecordByTSN(String transactionSerialNumber);
}
