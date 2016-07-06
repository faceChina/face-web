package com.zjlp.face.web.server.user.shop.dao;

import com.zjlp.face.web.server.user.shop.domain.AuthorizationCode;



public interface AuthorizationCodeDao {
	
	void add(AuthorizationCode authorizationCode);
	
	AuthorizationCode getById(Long id);
	
	void editById(AuthorizationCode authorizationCode);
	
	AuthorizationCode getByOrderItemId(Long orderItemId);
	
	AuthorizationCode getAuthorizationCodeByCode(String code);
}
