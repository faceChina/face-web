package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.user.shop.domain.AuthorizationCode;


public interface AuthorizationCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AuthorizationCode record);

    int insertSelective(AuthorizationCode record);

    AuthorizationCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AuthorizationCode record);

    int updateByPrimaryKey(AuthorizationCode record);
	
	AuthorizationCode getByOrderItemId(Long orderItemId);
	
	AuthorizationCode getAuthorizationCodeByCode(String code);
}