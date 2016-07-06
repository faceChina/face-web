package com.zjlp.face.web.server.user.shop.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.AuthorizationCodeMapper;
import com.zjlp.face.web.server.user.shop.dao.AuthorizationCodeDao;
import com.zjlp.face.web.server.user.shop.domain.AuthorizationCode;

@Repository
public class AuthorizationCodeDaoImpl implements AuthorizationCodeDao {
	
	@Autowired
	private SqlSession sqlSession;
	@Override
	public void add(AuthorizationCode authorizationCode){
		sqlSession.getMapper(AuthorizationCodeMapper.class).insertSelective(authorizationCode);
	}

	@Override
	public AuthorizationCode getById(Long id){
		return sqlSession.getMapper(AuthorizationCodeMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void editById(AuthorizationCode authorizationCode){
		sqlSession.getMapper(AuthorizationCodeMapper.class).updateByPrimaryKeySelective(authorizationCode);
	}
	
	@Override
	public AuthorizationCode getByOrderItemId(Long orderItemId){
		return sqlSession.getMapper(AuthorizationCodeMapper.class).getByOrderItemId(orderItemId);
	}
	
	@Override
	public AuthorizationCode getAuthorizationCodeByCode(String code){
		return sqlSession.getMapper(AuthorizationCodeMapper.class).getAuthorizationCodeByCode(code);
	}
}
