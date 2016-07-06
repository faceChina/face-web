package com.zjlp.face.web.server.trade.payment.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.PopularizeCommissionRecordMapper;
import com.zjlp.face.web.server.trade.payment.dao.PopularizeCommissionRecordDao;
import com.zjlp.face.web.server.trade.payment.domain.PopularizeCommissionRecord;
@Repository
public class PopularizeCommissionRecordDaoImpl implements
		PopularizeCommissionRecordDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addPopularizeCommissionRecord(
			PopularizeCommissionRecord popularizeCommissionRecord) {
		sqlSession.getMapper(PopularizeCommissionRecordMapper.class).insertSelective(popularizeCommissionRecord);
	}

	@Override
	public void editPopularizeCommissionRecord(
			PopularizeCommissionRecord popularizeCommissionRecord) {
		sqlSession.getMapper(PopularizeCommissionRecordMapper.class).updateByPrimaryKeySelective(popularizeCommissionRecord);
	}

	@Override
	public List<PopularizeCommissionRecord> getByOrderNo(String orderNo) {
		return sqlSession.getMapper(PopularizeCommissionRecordMapper.class).selectByOrderNo(orderNo);
	}

	@Override
	public List<PopularizeCommissionRecord> findByPopuCell(String popuCell) {
		return sqlSession.getMapper(PopularizeCommissionRecordMapper.class).selectByPopuCell(popuCell);
	}

	@Override
	public PopularizeCommissionRecord getByIdForLock(Long id) {
		return sqlSession.getMapper(PopularizeCommissionRecordMapper.class).selectByPrimaryKeyForLock(id);
	}

	@Override
	public Long getUsersPopularizeAmount(Long userId) {
		return sqlSession.getMapper(PopularizeCommissionRecordMapper.class).getUsersPopularizeAmount(userId);
	}

}
