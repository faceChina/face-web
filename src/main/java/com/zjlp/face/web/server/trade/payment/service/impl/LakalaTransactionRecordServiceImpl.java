package com.zjlp.face.web.server.trade.payment.service.impl;

import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.trade.payment.domain.LakalaTransactionRecord;
import com.zjlp.face.web.server.trade.payment.service.LakalaTransactionRecordService;
@Service
public class LakalaTransactionRecordServiceImpl implements
		LakalaTransactionRecordService {

	/*@Autowired
	private AlipayTransactionRecordDao alipayTransactionRecordDao;*/
	
	@Override
	public LakalaTransactionRecord getByTransactionSerialNumber(
			String transactionSerialNumber) {
		//return alipayTransactionRecordDao.getByTransactionSerialNumber(transactionSerialNumber);
		return null;
	}

	@Override
	public void add(LakalaTransactionRecord alipayTransactionRecord) {
		//alipayTransactionRecordDao.add(alipayTransactionRecord);
	}

}
