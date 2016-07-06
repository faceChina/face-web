package com.zjlp.face.web.server.trade.payment.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.TransactionRecordMapper;
import com.zjlp.face.web.server.trade.payment.dao.TransactionRecordDao;
import com.zjlp.face.web.server.trade.payment.domain.TransactionRecord;
@Repository
public class TransactionRecordDaoImpl implements TransactionRecordDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addTransactionRecord(TransactionRecord transactionRecord) {
		sqlSession.getMapper(TransactionRecordMapper.class).insertSelective(transactionRecord);
	}

	@Override
	public TransactionRecord getTransactionRecordByTSN(
			String transactionSerialNumber) {
		return sqlSession.getMapper(TransactionRecordMapper.class).selectOneByTSN(transactionSerialNumber);
	}

}
