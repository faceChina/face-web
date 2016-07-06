package com.zjlp.face.web.server.user.weixin.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.MessageBodyMapper;
import com.zjlp.face.web.server.user.weixin.dao.MessageBodyDao;
import com.zjlp.face.web.server.user.weixin.domain.MessageBody;

@Repository("MessageBodyDao")
public class MessageBodyDaoImpl implements MessageBodyDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void add(MessageBody messageBody) {
		sqlSession.getMapper(MessageBodyMapper.class).insertSelective(messageBody);
	}

	@Override
	public void edit(MessageBody messageBody) {
		sqlSession.getMapper(MessageBodyMapper.class).updateByPrimaryKeySelective(messageBody);
	}

	@Override
	public MessageBody getMessageBodyById(Long id) {
		return sqlSession.getMapper(MessageBodyMapper.class).selectByPrimaryKey(id);
	}

}
