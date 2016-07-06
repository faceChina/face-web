package com.zjlp.face.web.server.user.security.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.jredis.annotation.RedisCached;
import com.zjlp.face.jredis.annotation.enums.CachedMethod;
import com.zjlp.face.jredis.annotation.enums.CachedMode;
import com.zjlp.face.jredis.annotation.enums.CachedName;
import com.zjlp.face.web.mapper.RoleMapper;
import com.zjlp.face.web.server.user.security.dao.RoleDao;
import com.zjlp.face.web.server.user.security.domain.Role;

@Repository("roleDao")
public class RoleDaoImpl implements RoleDao{

	@Autowired
	SqlSession sqlSession;
	
	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public List<Role> findAllRole() {
		return sqlSession.getMapper(RoleMapper.class).findAllRole();
	}

	@Override
	@RedisCached(mode=CachedMode.GET,name=CachedName.NORMAL,method=CachedMethod.GET_SET)
	public Role getRoleByCode(String code) {
		return sqlSession.getMapper(RoleMapper.class).getRoleByCode(code);
	}

}
