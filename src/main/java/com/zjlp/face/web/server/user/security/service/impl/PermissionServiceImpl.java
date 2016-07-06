package com.zjlp.face.web.server.user.security.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.user.security.dao.PermissionDao;
import com.zjlp.face.web.server.user.security.dao.RoleDao;
import com.zjlp.face.web.server.user.security.dao.RolePermissionRelationDao;
import com.zjlp.face.web.server.user.security.dao.UserRoleRelationDao;
import com.zjlp.face.web.server.user.security.domain.Permission;
import com.zjlp.face.web.server.user.security.domain.Role;
import com.zjlp.face.web.server.user.security.domain.RolePermissionRelation;
import com.zjlp.face.web.server.user.security.domain.UserRoleRelation;
import com.zjlp.face.web.server.user.security.domain.dto.UserRoleRelationDto;
import com.zjlp.face.web.server.user.security.service.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService{

	@Autowired
	private RolePermissionRelationDao rolePermissionRelationDao;
	
	@Autowired
	private PermissionDao permissionDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserRoleRelationDao userRoleRelationDao;
	
	@Override
	public List<String> findAllPermissionByUserId(Long userId) {
		return rolePermissionRelationDao.findAllPermissionByUserId(userId);
	}

	@Override
	public List<Permission> findResource() {
		return permissionDao.findResource();
	}

	@Override
	public List<Role> findAllRole() {
		return roleDao.findAllRole();
	}

	@Override
	public Permission getPermission(Long id) {
		return permissionDao.getPermission(id);
	}

	@Override
	public void addRolePermissionRelation(RolePermissionRelation rpr) {
		rolePermissionRelationDao.addRolePermissionRelation(rpr);
	}

	@Override
	public void addUserRoleRelation(Long userId,String code) {
		Date date = new Date();
		Role roleId = roleDao.getRoleByCode(code);
		UserRoleRelation userRoleRelation = new UserRoleRelation();
		userRoleRelation.setRoleId(roleId.getId());
		userRoleRelation.setUserId(userId);
		userRoleRelation.setCreateTime(date);
		userRoleRelation.setUpdateTime(date);
		userRoleRelationDao.addUserRoleRelation(userRoleRelation);
	}

	@Override
	public Integer countRoleAndRelationByUserIdAndCode(
			UserRoleRelationDto userRoleRelationDto) {
		return userRoleRelationDao.countRoleAndRelationByUserIdAndCode(userRoleRelationDto);
	}

	
	
	
}
