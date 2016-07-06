package com.zjlp.face.web.server.user.shop.producer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zjlp.face.web.server.user.shop.domain.AuthorizationCode;
import com.zjlp.face.web.server.user.shop.producer.AuthorizationCodeProducer;
import com.zjlp.face.web.server.user.shop.service.AuthorizationCodeService;


@Component
public class AuthorizationCodeProducerImpl implements AuthorizationCodeProducer {
	
	@Autowired
	private AuthorizationCodeService authorizationCodeService;
	@Override
	public void add(AuthorizationCode authorizationCode){
		authorizationCodeService.add(authorizationCode);
	}
	
}
