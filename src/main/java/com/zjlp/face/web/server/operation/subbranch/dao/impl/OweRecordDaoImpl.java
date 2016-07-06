package com.zjlp.face.web.server.operation.subbranch.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.OweRecordMapper;
import com.zjlp.face.web.server.operation.subbranch.dao.OweRecordDao;
import com.zjlp.face.web.server.operation.subbranch.domain.OweRecord;

@Repository
public class OweRecordDaoImpl implements OweRecordDao {
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return sqlSession.getMapper(OweRecordMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public Long insertSelective(OweRecord record) {
		sqlSession.getMapper(OweRecordMapper.class).insertSelective(record);
		return record.getId();
	}

	@Override
	public OweRecord selectByPrimaryKey(Long id) {
		return sqlSession.getMapper(OweRecordMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OweRecord record) {
		return sqlSession.getMapper(OweRecordMapper.class).updateByPrimaryKeySelective(record);
	}

}
