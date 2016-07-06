package com.zjlp.face.web.server.user.weixin.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.TemplateMessageMapper;
import com.zjlp.face.web.server.user.weixin.dao.TemplateMessageDao;
import com.zjlp.face.web.server.user.weixin.domain.TemplateMessage;

@Repository
public class TemplateMessageDaoImpl implements TemplateMessageDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public TemplateMessage getTemplateMessage(TemplateMessage templateMessage) {
		return sqlSession.getMapper(TemplateMessageMapper.class).getTemplateMessage(templateMessage);
	}

	@Override
	public void addTemplateMessage(TemplateMessage templateMessage) {
		sqlSession.getMapper(TemplateMessageMapper.class).insertSelective(templateMessage);
	}

	@Override
	public void editTemplateMessage(TemplateMessage templateMessage) {
		sqlSession.getMapper(TemplateMessageMapper.class).updateByPrimaryKeySelective(templateMessage);
	}

	@Override
	public Integer getTemplateMessageCountByShopNo(String shopNo) {
		return sqlSession.getMapper(TemplateMessageMapper.class).getTemplateMessageCountByShopNo(shopNo);
	}

}
