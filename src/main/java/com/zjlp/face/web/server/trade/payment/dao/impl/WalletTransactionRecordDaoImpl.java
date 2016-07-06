package com.zjlp.face.web.server.trade.payment.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.WalletTransactionRecordMapper;
import com.zjlp.face.web.server.trade.payment.dao.WalletTransactionRecordDao;
import com.zjlp.face.web.server.trade.payment.domain.WalletTransactionRecord;
@Repository
public class WalletTransactionRecordDaoImpl implements
		WalletTransactionRecordDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addWalletTransactionRecord(
			WalletTransactionRecord walletTransactionRecord) {
		sqlSession.getMapper(WalletTransactionRecordMapper.class).insertSelective(walletTransactionRecord);
	}

}
