package com.zjlp.face.web.server.operation.member.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.MemberWechatRelationMapper;
import com.zjlp.face.web.server.operation.member.dao.MemberWechatRelationDao;
import com.zjlp.face.web.server.operation.member.domain.MemberWechatRelation;

@Deprecated
@Repository("memberWechatRelationDao")
public class MemberWechatRelationDaoImpl implements MemberWechatRelationDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void add(MemberWechatRelation memberWechatRelation) {
		sqlSession.getMapper(MemberWechatRelationMapper.class).insertSelective(memberWechatRelation);
	}

	@Override
	public MemberWechatRelation getMemberWechatRelation(
			MemberWechatRelation memberWechatRelation) {
		return sqlSession.getMapper(MemberWechatRelationMapper.class).getMemberWechatRelation(memberWechatRelation);
	}

	@Override
	public void edit(MemberWechatRelation memberWechatRelation) {
		sqlSession.getMapper(MemberWechatRelationMapper.class).updateByPrimaryKeySelective(memberWechatRelation);
	}

}
