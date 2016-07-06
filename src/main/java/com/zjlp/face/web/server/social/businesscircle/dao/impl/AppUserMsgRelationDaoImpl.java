package com.zjlp.face.web.server.social.businesscircle.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.AppUserMsgRelationMapper;
import com.zjlp.face.web.server.social.businesscircle.dao.AppUserMsgRelationDao;
import com.zjlp.face.web.server.social.businesscircle.domain.AppUserMsgRelation;

@Repository("appUserMsgRelationDao")
public class AppUserMsgRelationDaoImpl implements AppUserMsgRelationDao {
	
	@Autowired
	private SqlSession	sqlSession;

	@Override
	public Long add(AppUserMsgRelation appUserMsgRelation) {
		 sqlSession.getMapper(AppUserMsgRelationMapper.class).insert(appUserMsgRelation);
		 return appUserMsgRelation.getId();
	}

	@Override
	public void delete(Long id) {
		 sqlSession.getMapper(AppUserMsgRelationMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public void deleteByUserId(Long userId) {
		 sqlSession.getMapper(AppUserMsgRelationMapper.class).deleteByUserId(userId);
	}

	@Override
	public void deleteByCircleMsgId(Long circleMsgId) {
		 sqlSession.getMapper(AppUserMsgRelationMapper.class).deleteByCircleMsgId(circleMsgId);

	}

	@Override
	public List<AppUserMsgRelation> queryAppUserMsgRelation(Long userId,Long startId,int resultNumber) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("startId", startId);
		map.put("resultNumber", resultNumber);
		return  sqlSession.getMapper(AppUserMsgRelationMapper.class).selectAppUserMsgRelation(map);
	}

}
