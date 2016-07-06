package com.zjlp.face.web.server.user.security.dao;

import java.util.List;

import com.zjlp.face.web.server.user.security.domain.RolePermissionRelation;

public interface RolePermissionRelationDao {
	
	
	
	
	List<String> findAllPermissionByUserId(Long userId);
	
	
	void addRolePermissionRelation(RolePermissionRelation rpr);
}
