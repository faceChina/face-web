package com.zjlp.face.web.server.trade.recharge.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.RechargeMapper;
import com.zjlp.face.web.server.trade.recharge.dao.RechargeDao;
import com.zjlp.face.web.server.trade.recharge.domain.Recharge;

@Repository
public class RechargeDaoImpl implements RechargeDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void add(Recharge recharge) {
		sqlSession.getMapper(RechargeMapper.class).insertSelective(recharge);
	}

	@Override
	public void edit(Recharge recharge) {
		sqlSession.getMapper(RechargeMapper.class).updateByPrimaryKeySelective(recharge);
	}

	@Override
	public void deleteRecharge(String rechargeNo) {
		sqlSession.getMapper(RechargeMapper.class).deleteByPrimaryKey(rechargeNo);
	}

	@Override
	public Recharge getRechargeByRechargeNo(String rechargeNo) {
		return sqlSession.getMapper(RechargeMapper.class).selectByPrimaryKey(rechargeNo);
	}

	@Override
	public List<Recharge> findRechargeList(Recharge recharge) {
		return sqlSession.getMapper(RechargeMapper.class).selectList(recharge);
	}

	@Override
	public Recharge getRechargeByTSN(String transactionSerialNumber) {
		return sqlSession.getMapper(RechargeMapper.class).getRechargeByTSN(transactionSerialNumber);
	}

	@Override
	public List<Recharge> findRechargeListByUserAccountAndAccountType(
			String memberCardId, String beginTime, String endTime) {
		return sqlSession.getMapper(RechargeMapper.class).findRechargeListByUserAccountAndAccountType(memberCardId, beginTime, endTime);
	}

}
