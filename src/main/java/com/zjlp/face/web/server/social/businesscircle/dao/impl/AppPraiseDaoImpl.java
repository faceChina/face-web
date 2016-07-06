package com.zjlp.face.web.server.social.businesscircle.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.AppPraiseMapper;
import com.zjlp.face.web.server.social.businesscircle.dao.AppPraiseDao;
import com.zjlp.face.web.server.social.businesscircle.domain.AppPraise;

@Repository("appPraiseDao")
public class AppPraiseDaoImpl implements AppPraiseDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Long add(AppPraise appPraise) {
		sqlSession.getMapper(AppPraiseMapper.class).insert(appPraise);
		return appPraise.getId();
	}

	@Override
	public void deleteByCircleMsgId(Long circleMsgId) {
		sqlSession.getMapper(AppPraiseMapper.class).deleteByCircleMsgId(circleMsgId);
	}

	@Override
	public List<AppPraise> queryAppPraise(Long circleMsgId) {
		return sqlSession.getMapper(AppPraiseMapper.class).selectByCircleMsgId(circleMsgId);
	}

	@Override
	public AppPraise getAppPraise(Long id) {
		return sqlSession.getMapper(AppPraiseMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public int delete(Long id) {
		return sqlSession.getMapper(AppPraiseMapper.class).deleteByPrimaryKey(id);
	}

}
