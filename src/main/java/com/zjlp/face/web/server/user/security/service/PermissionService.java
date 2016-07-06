package com.zjlp.face.web.server.user.security.service;

import java.util.List;

import com.zjlp.face.web.server.user.security.domain.Permission;
import com.zjlp.face.web.server.user.security.domain.Role;
import com.zjlp.face.web.server.user.security.domain.RolePermissionRelation;
import com.zjlp.face.web.server.user.security.domain.dto.UserRoleRelationDto;

public interface PermissionService {

	
	
	
	
	
	List<String> findAllPermissionByUserId(Long userId);
	
	
	List<Permission> findResource();
	
	
	List<Role> findAllRole();
	
	Permission getPermission(Long id);
	
	
	void addRolePermissionRelation(RolePermissionRelation rpr);
	
	
	
	void addUserRoleRelation(Long userId,String code);
	
	Integer countRoleAndRelationByUserIdAndCode(UserRoleRelationDto userRoleRelationDto);
	
	
	
	
	
}
