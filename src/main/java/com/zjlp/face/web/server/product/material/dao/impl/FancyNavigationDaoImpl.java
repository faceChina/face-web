package com.zjlp.face.web.server.product.material.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.FancyNavigationMapper;
import com.zjlp.face.web.server.product.material.dao.FancyNavigationDao;
import com.zjlp.face.web.server.product.material.domain.FancyNavigation;
import com.zjlp.face.web.server.product.material.domain.dto.FancyNavigationDto;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月24日 下午5:02:49
 *
 */
@Repository
public class FancyNavigationDaoImpl implements FancyNavigationDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long add(FancyNavigation fancyNavigation) {
		this.sqlSession.getMapper(FancyNavigationMapper.class).insert(fancyNavigation);
		return fancyNavigation.getId();
	}

	@Override
	public FancyNavigation getById(Long id) {
		return this.sqlSession.getMapper(FancyNavigationMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void updateById(FancyNavigation fancyNavigation) {
		this.sqlSession.getMapper(FancyNavigationMapper.class).updateByPrimaryKey(fancyNavigation);
	}

	@Override
	public void deleteById(Long id) {
		this.sqlSession.getMapper(FancyNavigationMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public List<FancyNavigationDto> findAllNavigation() {
		Map<String, Object> map = new HashMap<String, Object>();
		return this.sqlSession.getMapper(FancyNavigationMapper.class).selectAllNavigation(map);
	}

}
