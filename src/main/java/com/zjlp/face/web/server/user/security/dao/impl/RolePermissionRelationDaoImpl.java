package com.zjlp.face.web.server.user.security.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.jredis.annotation.RedisCached;
import com.zjlp.face.jredis.annotation.enums.CachedMethod;
import com.zjlp.face.jredis.annotation.enums.CachedMode;
import com.zjlp.face.jredis.annotation.enums.CachedName;
import com.zjlp.face.web.mapper.RolePermissionRelationMapper;
import com.zjlp.face.web.server.user.security.dao.RolePermissionRelationDao;
import com.zjlp.face.web.server.user.security.domain.RolePermissionRelation;



@Repository("rolePermissionRelationDao")
public class RolePermissionRelationDaoImpl implements RolePermissionRelationDao{

	@Autowired
	SqlSession sqlSession;
	
	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public List<String> findAllPermissionByUserId(Long userId) {
		return sqlSession.getMapper(RolePermissionRelationMapper.class).findAllPermissionByUserId(userId);
	}

	@Override
	public void addRolePermissionRelation(
			RolePermissionRelation rpr) {
		 sqlSession.getMapper(RolePermissionRelationMapper.class).insertSelective(rpr);
	}

}
