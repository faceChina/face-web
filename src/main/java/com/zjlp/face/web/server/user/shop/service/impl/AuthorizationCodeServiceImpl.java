package com.zjlp.face.web.server.user.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.user.shop.dao.AuthorizationCodeDao;
import com.zjlp.face.web.server.user.shop.domain.AuthorizationCode;
import com.zjlp.face.web.server.user.shop.service.AuthorizationCodeService;


@Service
public class AuthorizationCodeServiceImpl implements AuthorizationCodeService {
	
	@Autowired
	private AuthorizationCodeDao authorizationCodeDao;
	@Override
	public void add(AuthorizationCode authorizationCode){
		authorizationCodeDao.add(authorizationCode);
	}
	
	@Override
	public AuthorizationCode getById(Long id){
		return authorizationCodeDao.getById(id);
	}

	@Override
	public void editById(AuthorizationCode authorizationCode){
		authorizationCodeDao.editById(authorizationCode);
	}
	
	@Override
	public AuthorizationCode getAuthorizationCodeByCode(String code){
		return authorizationCodeDao.getAuthorizationCodeByCode(code);
	}
}
