package com.zjlp.face.web.server.operation.marketing.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.MarketingActivityDetailMapper;
import com.zjlp.face.web.server.operation.marketing.dao.MarketingActivityDetailDao;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDetailDto;

@Repository("marketingActivityDetailDao")
public class MarketingActivityDetailDaoImpl implements
		MarketingActivityDetailDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Long add(MarketingActivityDetail activityDetail) {
		sqlSession.getMapper(MarketingActivityDetailMapper.class).insertSelective(activityDetail);
		return activityDetail.getId();
	}

	@Override
	public MarketingActivityDetail getValidById(Long id) {
		return sqlSession.getMapper(MarketingActivityDetailMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public List<MarketingActivityDetail> findList(MarketingActivityDetail detail) {
		return sqlSession.getMapper(MarketingActivityDetailMapper.class).selectByDetail(detail);
	}

	@Override
	public void edit(MarketingActivityDetail detail) {
		sqlSession.getMapper(MarketingActivityDetailMapper.class).updateByPrimaryKeySelective(detail);
	}

	@Override
	public List<MarketingActivityDetailDto> findDtoList(
			MarketingActivityDetail detail) {
		return sqlSession.getMapper(MarketingActivityDetailMapper.class).selectDtoList(detail);
	}

	@Override
	public void delByActivityId(Long activityId) {
		sqlSession.getMapper(MarketingActivityDetailMapper.class).delByActivityId(activityId);
	}

	@Override
	public void delById(Long id) {
		sqlSession.getMapper(MarketingActivityDetailMapper.class).delById(id);
	}

	@Override
	public void editStatus(MarketingActivityDetail detail) {
		sqlSession.getMapper(MarketingActivityDetailMapper.class).updateStatusByToolId(detail);
	}

}
