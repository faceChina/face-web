package com.zjlp.face.web.server.user.security.dao;

import java.util.List;

import com.zjlp.face.web.server.user.security.domain.Role;

public interface RoleDao {
	
	
	List<Role> findAllRole();
	
	
	Role getRoleByCode(String code);

}
