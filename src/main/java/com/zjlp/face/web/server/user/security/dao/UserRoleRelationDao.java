package com.zjlp.face.web.server.user.security.dao;

import com.zjlp.face.web.server.user.security.domain.UserRoleRelation;
import com.zjlp.face.web.server.user.security.domain.dto.UserRoleRelationDto;

public interface UserRoleRelationDao {
	
	
	
	void addUserRoleRelation(UserRoleRelation userRoleRelation);
	
	
	 int countRoleAndRelationByUserIdAndCode(UserRoleRelationDto userRoleRelationDto);
}
