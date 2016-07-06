package com.zjlp.face.web.server.operation.member.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.IntegralRecodeMapper;
import com.zjlp.face.web.server.operation.member.dao.IntegralRecodeDao;
import com.zjlp.face.web.server.operation.member.domain.IntegralRecode;

@Repository("IntegralRecodeDao")
public class IntegralRecodeDaoImpl implements IntegralRecodeDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void add(IntegralRecode integralRecode) {
		sqlSession.getMapper(IntegralRecodeMapper.class).insert(integralRecode);
	}

	@Override
	public List<IntegralRecode> findIntegralRecordListByMemberCardId(
			Long memberCardId, String beginTime, String endTime) {
		return sqlSession.getMapper(IntegralRecodeMapper.class).findIntegralRecordListByMemberCardId(memberCardId, beginTime, endTime);
	}

}
