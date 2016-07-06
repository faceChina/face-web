package com.zjlp.face.web.server.user.shop.producer;

import com.zjlp.face.web.server.user.shop.domain.AuthorizationCode;


public interface AuthorizationCodeProducer {
	
	void add(AuthorizationCode authorizationCode);
}
