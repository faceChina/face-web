package com.zjlp.face.web.server.user.security.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.jredis.annotation.RedisCached;
import com.zjlp.face.jredis.annotation.enums.CachedMethod;
import com.zjlp.face.jredis.annotation.enums.CachedMode;
import com.zjlp.face.jredis.annotation.enums.CachedName;
import com.zjlp.face.web.mapper.PermissionMapper;
import com.zjlp.face.web.server.user.security.dao.PermissionDao;
import com.zjlp.face.web.server.user.security.domain.Permission;

@Repository("permissionDao")
public class PermissionDaoImpl implements PermissionDao{

	
	@Autowired
	SqlSession sqlSession;
	
	
	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public List<Permission> findResource() {
		return sqlSession.getMapper(PermissionMapper.class).findResource();
	}


	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public Permission getPermission(Long id) {
		return sqlSession.getMapper(PermissionMapper.class).selectByPrimaryKey(id);
	}

}
