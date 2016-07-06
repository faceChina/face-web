package com.zjlp.face.web.server.user.shop.service;

import com.zjlp.face.web.server.user.shop.domain.AuthorizationCode;


public interface AuthorizationCodeService {
	
	void add(AuthorizationCode authorizationCode);
	
	AuthorizationCode getById(Long id);
	
	void editById(AuthorizationCode authorizationCode);
	
	AuthorizationCode getAuthorizationCodeByCode(String code);
}
