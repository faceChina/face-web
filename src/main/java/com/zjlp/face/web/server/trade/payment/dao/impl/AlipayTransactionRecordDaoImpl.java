package com.zjlp.face.web.server.trade.payment.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.AlipayTransactionRecordMapper;
import com.zjlp.face.web.server.trade.payment.dao.AlipayTransactionRecordDao;
import com.zjlp.face.web.server.trade.payment.domain.AlipayTransactionRecord;
@Repository
public class AlipayTransactionRecordDaoImpl implements
		AlipayTransactionRecordDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public AlipayTransactionRecord getByTransactionSerialNumber(
			String transactionSerialNumber) {
		return sqlSession.getMapper(AlipayTransactionRecordMapper.class).selectByTransactionSerialNumber(transactionSerialNumber);
	}

	@Override
	public void add(AlipayTransactionRecord alipayTransactionRecord) {
		sqlSession.getMapper(AlipayTransactionRecordMapper.class).insertSelective(alipayTransactionRecord);
	}

}
