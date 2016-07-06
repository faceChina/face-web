package com.zjlp.face.web.server.product.material.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.FancyNavigationItemMapper;
import com.zjlp.face.web.server.product.material.dao.FancyNavigationItemDao;
import com.zjlp.face.web.server.product.material.domain.FancyNavigationItem;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月24日 下午5:23:51
 *
 */
@Repository
public class FancyNavigationItemDaoImpl implements FancyNavigationItemDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long add(FancyNavigationItem fancyNavigationItem) {
		this.sqlSession.getMapper(FancyNavigationItemMapper.class).insert(fancyNavigationItem);
		return null;
	}

	@Override
	public FancyNavigationItem getById(Long id) {
		return this.sqlSession.getMapper(FancyNavigationItemMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void updateById(FancyNavigationItem fancyNavigationItem) {
		this.sqlSession.getMapper(FancyNavigationItemMapper.class).updateByPrimaryKey(fancyNavigationItem);
	}

	@Override
	public void deleteById(Long id) {
		this.sqlSession.getMapper(FancyNavigationItemMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public List<FancyNavigationItem> selectItemByNavigationId(Long navigationId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fancyNavigationId", navigationId);
		return this.sqlSession.getMapper(FancyNavigationItemMapper.class).selectItemByNavigationId(map);
	}

}
