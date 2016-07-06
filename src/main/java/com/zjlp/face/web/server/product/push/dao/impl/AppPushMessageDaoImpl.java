package com.zjlp.face.web.server.product.push.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.AppPushMessageMapper;
import com.zjlp.face.web.server.product.push.dao.AppPushMessageDao;
import com.zjlp.face.web.server.product.push.domain.AppPushMessage;

@Repository
public class AppPushMessageDaoImpl implements AppPushMessageDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public int add(AppPushMessage appPushMessage) {
		return sqlSession.getMapper(AppPushMessageMapper.class).insert(appPushMessage);
	}

	@Override
	public void updateAppPushMessage(AppPushMessage appPushMessage) {
		sqlSession.getMapper(AppPushMessageMapper.class).update(appPushMessage);
	}

	@Override
	public List<AppPushMessage> findByUserId(AppPushMessage appPushMessage) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", appPushMessage.getUserId());
		map.put("isRead", appPushMessage.getIsRead());
		map.put("msgType", appPushMessage.getMsgType());
		map.put("createTime", appPushMessage.getCreateTime());
		return sqlSession.getMapper(AppPushMessageMapper.class).selectByUserId(map);
	}

	@Override
	public void removeAppPushMessage(Long id) {
		sqlSession.getMapper(AppPushMessageMapper.class).deleteByPrimarykey(id);

	}

	@Override
	public AppPushMessage findReadMsgById(AppPushMessage appPushMessage) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", appPushMessage.getId());
		map.put("isRead", appPushMessage.getIsRead());
		return sqlSession.getMapper(AppPushMessageMapper.class).findReadMsgById(map);
	}

	@Override
	public List<AppPushMessage> findAppPushMessage(AppPushMessage appPushMessage) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", appPushMessage.getId());
		map.put("msgType", appPushMessage.getMsgType());
		map.put("createTime",null == appPushMessage.getId()?new Date(): null );
		return sqlSession.getMapper(AppPushMessageMapper.class).selectByIdAndMsgType(map);
	}

	@Override
	public void deleteMsgMonthAgo(Long userId, Date createTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("createTime", createTime);
		this.sqlSession.getMapper(AppPushMessageMapper.class).deleteMsgMonthAgo(map);
	}

}
