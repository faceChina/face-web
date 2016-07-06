package com.zjlp.face.web.server.trade.payment.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.MemberTransactionRecordMapper;
import com.zjlp.face.web.server.trade.payment.dao.MemberTransactionRecordDao;
import com.zjlp.face.web.server.trade.payment.domain.MemberTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.MemberTransactionRecordDto;

@Repository
public class MemberTransactionRecordDaoImpl implements
		MemberTransactionRecordDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void add(MemberTransactionRecord transactionRecord) {
		sqlSession.getMapper(MemberTransactionRecordMapper.class).insertSelective(transactionRecord);
	}

	@Override
	public MemberTransactionRecord getByTransactionSerialNumber(
			String transactionSerialNumber) {
		return sqlSession.getMapper(MemberTransactionRecordMapper.class).selectByTSN(transactionSerialNumber);
	}

	@Override
	public List<MemberTransactionRecordDto> findTransactionRecordList(
			MemberTransactionRecordDto dto) {
		return sqlSession.getMapper(MemberTransactionRecordMapper.class).selectTransactionRecordList(dto);
	}

	@Override
	public Integer getTransactionCount(MemberTransactionRecordDto dto) {
		return sqlSession.getMapper(MemberTransactionRecordMapper.class).selectTransactionCount(dto);
	}

	@Override
	public List<MemberTransactionRecord> findMemberTransactionRecordListByTime(
			Long memberCardId, String beginTime, String endTime) {
		return this.sqlSession.getMapper(MemberTransactionRecordMapper.class).findMemberTransactionRecordListByTime(memberCardId, beginTime, endTime);
	}

	
}
