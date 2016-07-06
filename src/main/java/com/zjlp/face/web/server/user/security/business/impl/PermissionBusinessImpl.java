package com.zjlp.face.web.server.user.security.business.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.user.security.business.PermissionBusiness;
import com.zjlp.face.web.server.user.security.domain.Permission;
import com.zjlp.face.web.server.user.security.domain.Role;
import com.zjlp.face.web.server.user.security.domain.RolePermissionRelation;
import com.zjlp.face.web.server.user.security.service.PermissionService;

@Service
public class PermissionBusinessImpl implements PermissionBusiness{

	@Autowired
	private PermissionService permissionService;
	
	
	
	@Override
	public List<Permission> findResource() throws SecurityException{
		return permissionService.findResource();
	}


	@Override
	public List<Role> findAllRole() throws SecurityException{
		return permissionService.findAllRole();
	}


	@Override
	public void addRolePermissionRelation(RolePermissionRelation rpr) throws SecurityException{
		Permission permission = permissionService.getPermission(rpr.getPermissionId());
		
		Date date = new Date();
		rpr.setPermissionCode(permission.getCode());
		rpr.setPermissionUrl(permission.getUrl());
		rpr.setCreateTime(date);
		rpr.setUpdateTime(date);
		
		permissionService.addRolePermissionRelation(rpr);
	}

}
