package com.zjlp.face.web.server.user.security.dao;

import java.util.List;

import com.zjlp.face.web.server.user.security.domain.Permission;

public interface PermissionDao{
	
	
	
	List<Permission> findResource();
	
	
	Permission getPermission(Long id);
	
	
}
