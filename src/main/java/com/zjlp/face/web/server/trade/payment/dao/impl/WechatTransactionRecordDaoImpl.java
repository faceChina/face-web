package com.zjlp.face.web.server.trade.payment.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.WechatTransactionRecordMapper;
import com.zjlp.face.web.server.trade.payment.dao.WechatTransactionRecordDao;
import com.zjlp.face.web.server.trade.payment.domain.WechatTransactionRecord;
@Repository
public class WechatTransactionRecordDaoImpl implements
		WechatTransactionRecordDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addWechatTransactionRecord(
			WechatTransactionRecord wechatTransactionRecord) {
		sqlSession.getMapper(WechatTransactionRecordMapper.class).insertSelective(wechatTransactionRecord);
	}

	@Override
	public WechatTransactionRecord getWechatTransactionRecordByTSN(
			String transactionSerialNumber) {
		return sqlSession.getMapper(WechatTransactionRecordMapper.class).selectByTSN(transactionSerialNumber);
	}

}
