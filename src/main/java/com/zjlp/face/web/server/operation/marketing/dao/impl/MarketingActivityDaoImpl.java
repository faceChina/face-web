package com.zjlp.face.web.server.operation.marketing.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.MarketingActivityMapper;
import com.zjlp.face.web.server.operation.marketing.dao.MarketingActivityDao;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivity;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDto;

@Repository("marketingActivityDao")
public class MarketingActivityDaoImpl implements MarketingActivityDao {

	@Autowired
	private SqlSession sqlSession;
	@Override
	public Long add(MarketingActivity activity) {
		sqlSession.getMapper(MarketingActivityMapper.class).insertSelective(activity);
		return activity.getId();
	}
	@Override
	public MarketingActivity getValidActivityByToolId(Long toolId) {
		return sqlSession.getMapper(MarketingActivityMapper.class).selectValidActivityByToolId(toolId);
	}
	@Override
	public MarketingActivity getById(Long id) {
		return sqlSession.getMapper(MarketingActivityMapper.class).selectByPrimaryKey(id);
	}
	@Override
	public List<MarketingActivity> findListByToolId(Long toolId) {
		return sqlSession.getMapper(MarketingActivityMapper.class).selectListByToolId(toolId);
	}
	@Override
	public List<MarketingActivityDto> findPage(MarketingActivityDto dto) {
		return sqlSession.getMapper(MarketingActivityMapper.class).selectPage(dto);
	}
	@Override
	public Integer getCount(MarketingActivityDto dto) {
		return sqlSession.getMapper(MarketingActivityMapper.class).selectCount(dto);
	}
	@Override
	public void edit(MarketingActivity activity) {
		sqlSession.getMapper(MarketingActivityMapper.class).updateByPrimaryKeySelective(activity);
	}
	@Override
	public void delById(Long id) {
		sqlSession.getMapper(MarketingActivityMapper.class).delById(id);
	}
	@Override
	public void editStatus(MarketingActivity activity) {
		sqlSession.getMapper(MarketingActivityMapper.class).updateStatusByToolId(activity);
	}

}
