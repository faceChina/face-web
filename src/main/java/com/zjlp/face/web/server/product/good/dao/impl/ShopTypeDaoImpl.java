package com.zjlp.face.web.server.product.good.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.ShopTypeMapper;
import com.zjlp.face.web.server.product.good.dao.ShopTypeDao;
import com.zjlp.face.web.server.product.good.domain.ShopType;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;

@Repository
public class ShopTypeDaoImpl implements ShopTypeDao{

	@Autowired
	private SqlSession sqlSession;

	@Override
	public void add(ShopType shopType) {
		sqlSession.getMapper(ShopTypeMapper.class).insertSelective(shopType);
	}

	@Override
	public void edit(ShopType shopType) {
		sqlSession.getMapper(ShopTypeMapper.class).updateByPrimaryKeySelective(shopType);
	}

	@Override
	public ShopType getById(Long id) {
		return sqlSession.getMapper(ShopTypeMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long id) {
		sqlSession.getMapper(ShopTypeMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public Integer getPageCount(ShopType shopType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shopType", shopType);
		return sqlSession.getMapper(ShopTypeMapper.class).selectPageCount(map);
	}

	@Override
	public List<ShopTypeVo> findPageShopType(ShopType shopType,Integer start,Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shopType", shopType);
		map.put("start", start);
		map.put("pageSize", pageSize);
		return sqlSession.getMapper(ShopTypeMapper.class).selectPageShopType(map);
	}

	@Override
	public List<ShopTypeVo> findShopType(String shopNo, Long goodId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shopNo", shopNo);
		map.put("goodId", goodId);
		return sqlSession.getMapper(ShopTypeMapper.class).selectListByShopNoAndGoodId(map);
	}

	@Override
	public List<ShopTypeVo> findShopTypeList(ShopTypeVo shopTypeVo) {
		return sqlSession.getMapper(ShopTypeMapper.class).findShopTypeList(shopTypeVo);
	}
	
	@Override
	public List<ShopTypeVo> findAppointShopTypeList(ShopTypeVo shopTypeVo) {
		return sqlSession.getMapper(ShopTypeMapper.class).findAppointShopTypeList(shopTypeVo);
	}
}
