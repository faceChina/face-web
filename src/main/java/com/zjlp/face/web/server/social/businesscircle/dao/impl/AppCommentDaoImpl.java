package com.zjlp.face.web.server.social.businesscircle.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.AppCommentMapper;
import com.zjlp.face.web.server.social.businesscircle.dao.AppCommentDao;
import com.zjlp.face.web.server.social.businesscircle.domain.AppComment;

@Repository("appCommentDao")
public class AppCommentDaoImpl implements AppCommentDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long add(AppComment appComment) {
		 sqlSession.getMapper(AppCommentMapper.class).insert(appComment);
		 return appComment.getId();
	}

	@Override
	public int delete(Long id,Long senderId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id",id);
		map.put("senderId",senderId);
		return sqlSession.getMapper(AppCommentMapper.class).deleteByIdAndUserId(map);

	}

	@Override
	public void deleteByCircleMsgId(Long circleMsgId) {
		sqlSession.getMapper(AppCommentMapper.class).deleteByCircleMsgId(circleMsgId);

	}

	@Override
	public List<AppComment> queryAppComment(Long circleMsgId) {
		return sqlSession.getMapper(AppCommentMapper.class).selectByCircleMsgId(circleMsgId);
		
	}

	@Override
	public AppComment getAppComment(Long id) {
		return sqlSession.getMapper(AppCommentMapper.class).selectByPrimaryKey(id);
	}

}
