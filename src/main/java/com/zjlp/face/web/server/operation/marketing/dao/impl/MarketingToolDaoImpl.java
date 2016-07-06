package com.zjlp.face.web.server.operation.marketing.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.MarketingToolMapper;
import com.zjlp.face.web.server.operation.marketing.dao.MarketingToolDao;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingTool;

@Repository("marketingToolDao")
public class MarketingToolDaoImpl implements MarketingToolDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Long add(MarketingTool tool) {
		sqlSession.getMapper(MarketingToolMapper.class).insertSelective(tool);
		return tool.getId();
	}

	@Override
	public MarketingTool getById(Long id) {
		return sqlSession.getMapper(MarketingToolMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public MarketingTool getToolByType(MarketingTool tool) {
		return sqlSession.getMapper(MarketingToolMapper.class).selectByUserAndType(tool);
	}

	@Override
	public void edit(MarketingTool tool) {
		sqlSession.getMapper(MarketingToolMapper.class).updateByPrimaryKeySelective(tool);
	}

}
