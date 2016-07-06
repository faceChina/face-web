package com.zjlp.face.web.server.user.mulchat.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.MulChatViewNickMapper;
import com.zjlp.face.web.server.user.mulchat.dao.MulChatViewNickDao;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatViewNick;

/**
 * @author Baojiang Yang
 *
 */
@Repository("mulChatViewNickDao")
public class MulChatViewNickDaoImpl implements MulChatViewNickDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public String insert(MulChatViewNick mulChatViewNick) {
		this.sqlSession.getMapper(MulChatViewNickMapper.class).insert(mulChatViewNick);
		return mulChatViewNick.getGroupId();
	}

	@Override
	public void delete(MulChatViewNick mulChatViewNick) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", mulChatViewNick.getGroupId());
		map.put("loginAccount", mulChatViewNick.getLoginAccount());
		this.sqlSession.getMapper(MulChatViewNickMapper.class).delete(map);
	}

	@Override
	public MulChatViewNick select(MulChatViewNick mulChatViewNick) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", mulChatViewNick.getGroupId());
		map.put("loginAccount", mulChatViewNick.getLoginAccount());
		return this.sqlSession.getMapper(MulChatViewNickMapper.class).select(map);
	}

	@Override
	public String update(MulChatViewNick mulChatViewNick) {
		this.sqlSession.getMapper(MulChatViewNickMapper.class).updateNickName(mulChatViewNick);
		return mulChatViewNick.getGroupId();
	}

	@Override
	public List<MulChatViewNick> findGroupNickList(String groupId) {
		return this.sqlSession.getMapper(MulChatViewNickMapper.class).findGroupNickList(groupId);
	}

}
