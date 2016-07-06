package com.zjlp.face.web.server.user.mulchat.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.MulChatInfomationMapper;
import com.zjlp.face.web.server.user.mulchat.dao.MulChatInformationDao;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatInformation;

/**
 * @author Boajiang Yang
 *
 */
@Repository("mulChatInfomationDao")
public class MulChatInformationDaoImpl implements MulChatInformationDao {

	@Autowired
	SqlSession sqlSession;

	@Override
	public String insert(MulChatInformation mulChatInformation) {
		this.sqlSession.getMapper(MulChatInfomationMapper.class).insert(mulChatInformation);
		return mulChatInformation.getGroupId();
	}

	@Override
	public void deleteByPrimarykey(String id) {
		this.sqlSession.getMapper(MulChatInfomationMapper.class).deleteByPrimarykey(id);
	}

	@Override
	public MulChatInformation selectByPrimarykey(String id) {
		return this.sqlSession.getMapper(MulChatInfomationMapper.class).selectByPrimarykey(id);
	}

	@Override
	public String updateByPrimaryKey(MulChatInformation mulChatInformation) {
		this.sqlSession.getMapper(MulChatInfomationMapper.class).updateByPrimaryKey(mulChatInformation);
		return mulChatInformation.getGroupId();
	}

}
