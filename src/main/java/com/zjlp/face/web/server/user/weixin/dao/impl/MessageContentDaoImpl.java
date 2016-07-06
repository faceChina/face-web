package com.zjlp.face.web.server.user.weixin.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.MessageContentMapper;
import com.zjlp.face.web.server.user.weixin.dao.MessageContentDao;
import com.zjlp.face.web.server.user.weixin.domain.MessageContent;

@Repository("MessageContentDao")
public class MessageContentDaoImpl implements MessageContentDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void add(MessageContent messageContent) {
		sqlSession.getMapper(MessageContentMapper.class).insertSelective(messageContent);
		
	}

	@Override
	public MessageContent getById(Long id) {
		return sqlSession.getMapper(MessageContentMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public List<MessageContent> findByMessageBodyId(Long messageBodyId) {
		return sqlSession.getMapper(MessageContentMapper.class).selectByMessageBodyId(messageBodyId);
	}

	@Override
	public void delMessageContentByMessageBodyId(Long messageBodyId) {
		sqlSession.getMapper(MessageContentMapper.class).deleteByMessageBodyId(messageBodyId);
	}

	@Override
	public void editMessageContent(MessageContent messageContent) {
		sqlSession.getMapper(MessageContentMapper.class).updateMessageContent(messageContent);
	}

	@Override
	public void delMessageContentById(Long id) {
		sqlSession.getMapper(MessageContentMapper.class).deleteByPrimaryKey(id);
		
	}

}
