package com.zjlp.face.web.server.trade.payment.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.CommissionRecordMapper;
import com.zjlp.face.web.server.trade.payment.dao.CommissionRecordDao;
import com.zjlp.face.web.server.trade.payment.domain.CommissionRecord;
@Repository
public class CommissionRecordDaoImpl implements CommissionRecordDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addCommissionRecord(CommissionRecord commissionRecord) {
		sqlSession.getMapper(CommissionRecordMapper.class).insertSelective(commissionRecord);
	}

	@Override
	public void changeIsToAccout(CommissionRecord commissionRecord) {
		sqlSession.getMapper(CommissionRecordMapper.class).updateIsToAccount(commissionRecord);
	}

	@Override
	public void editCommissionRecord(CommissionRecord commissionRecord) {
		sqlSession.getMapper(CommissionRecordMapper.class).updateByPrimaryKeySelective(commissionRecord);
	}

	@Override
	public List<CommissionRecord> findByOrderNo(String orderNo) {
		return sqlSession.getMapper(CommissionRecordMapper.class).selectByOrderNo(orderNo);
	}

	@Override
	public Long getUserFreezeAmount(Long accountId) {
		return sqlSession.getMapper(CommissionRecordMapper.class).getUserFreezeAmount(accountId);
	}

	@Override
	public Long getUsersShopsFreezeAmount(Long userId) {
		return sqlSession.getMapper(CommissionRecordMapper.class).getUsersShopsFreezeAmount(userId);
	}

	@Override
	public Long getShopFreezeIncome(String shopNo) {
		return sqlSession.getMapper(CommissionRecordMapper.class).getShopFreezeIncome(shopNo);
	}

	@Override
	public Long getShopIncomeByTime(String shopNo, Date startTime, Date endTime) {
		return sqlSession.getMapper(CommissionRecordMapper.class).getShopIncomeByTime(shopNo, startTime, endTime);
	}

	@Override
	public String getShopName(Long id) {
		return sqlSession.getMapper(CommissionRecordMapper.class).getShopName(id);
	}

	@Override
	public String getSubbranchShopName(Long id) {
		return sqlSession.getMapper(CommissionRecordMapper.class).getSubbranchShopName(id);
	}

}
