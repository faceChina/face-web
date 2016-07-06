package com.zjlp.face.web.server.user.security.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.UserRoleRelationMapper;
import com.zjlp.face.web.server.user.security.dao.UserRoleRelationDao;
import com.zjlp.face.web.server.user.security.domain.UserRoleRelation;
import com.zjlp.face.web.server.user.security.domain.dto.UserRoleRelationDto;


@Repository("userRoleRelationDao")
public class UserRoleRelationDaoImpl implements UserRoleRelationDao{

	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void addUserRoleRelation(UserRoleRelation userRoleRelation) {
		 sqlSession.getMapper(UserRoleRelationMapper.class).insertSelective(userRoleRelation);
	}

	@Override
	public int countRoleAndRelationByUserIdAndCode(
			UserRoleRelationDto userRoleRelationDto) {
		return sqlSession.getMapper(UserRoleRelationMapper.class).countRoleAndRelationByUserIdAndCode(userRoleRelationDto);
	}

}
