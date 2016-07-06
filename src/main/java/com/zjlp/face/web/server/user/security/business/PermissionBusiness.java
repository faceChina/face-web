package com.zjlp.face.web.server.user.security.business;

import java.util.List;

import com.zjlp.face.web.server.user.security.domain.Permission;
import com.zjlp.face.web.server.user.security.domain.Role;
import com.zjlp.face.web.server.user.security.domain.RolePermissionRelation;

public interface PermissionBusiness {

	
	List<Permission> findResource() throws SecurityException;
	
	
	List<Role> findAllRole() throws SecurityException;
	
	
	void addRolePermissionRelation(RolePermissionRelation rpr) throws SecurityException;
	
}
