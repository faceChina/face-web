package com.zjlp.face.web.server.operation.member.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.MemberEnactmentMapper;
import com.zjlp.face.web.server.operation.member.dao.MemberEnactmentDao;
import com.zjlp.face.web.server.operation.member.domain.MemberEnactment;

@Repository("memberEnactmentDao")
public class MemberEnactmentDaoImpl implements MemberEnactmentDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Long add(MemberEnactment enactment) {
		sqlSession.getMapper(MemberEnactmentMapper.class).insertSelective(enactment);
		return enactment.getId();
	}

	@Override
	public MemberEnactment getBySellerId(Long sellerId) {
		return sqlSession.getMapper(MemberEnactmentMapper.class).selectBySellerId(sellerId);
	}

	@Override
	public MemberEnactment getValidById(Long id) {
		return sqlSession.getMapper(MemberEnactmentMapper.class).selectValidById(id);
	}

	@Override
	public void edit(MemberEnactment enactment) {
		sqlSession.getMapper(MemberEnactmentMapper.class).updateByPrimaryKeySelective(enactment);
	}

}
