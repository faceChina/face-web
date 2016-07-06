package com.zjlp.face.web.server.product.good.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.jredis.annotation.RedisCached;
import com.zjlp.face.jredis.annotation.enums.CachedMethod;
import com.zjlp.face.jredis.annotation.enums.CachedMode;
import com.zjlp.face.web.mapper.GoodPropertyMapper;
import com.zjlp.face.web.server.product.good.dao.GoodPropertyDao;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
@Repository
public class GoodPropertyDaoImpl implements GoodPropertyDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void add(GoodProperty goodProperty) {
		sqlSession.getMapper(GoodPropertyMapper.class).insertSelective(goodProperty);
	}

	@Override
	@RedisCached(mode=CachedMode.UPDATE,key={"goodProperty:id"},prop={"propValueAlias",""})
	public void edit(GoodProperty goodProperty) {
		sqlSession.getMapper(GoodPropertyMapper.class).updateByPrimaryKeySelective(goodProperty);
	}

	@Override
	@RedisCached(mode=CachedMode.GET,method=CachedMethod.GET_SET,key={"goodProperty"})
	public GoodProperty getById(Long id) {
		return sqlSession.getMapper(GoodPropertyMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long id) {
		sqlSession.getMapper(GoodPropertyMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public List<GoodProperty> findGoodPropertyListByGoodId(Long goodId) {
		return sqlSession.getMapper(GoodPropertyMapper.class).selectListByGoodId(goodId);
	}

	@Override
	public List<GoodProperty> findGoodProperties(GoodProperty goodProperty) {
		return sqlSession.getMapper(GoodPropertyMapper.class).selectList(goodProperty);
	}

	@Override
	public GoodProperty getByGoodIdAndPropValueId(Long goodId, Long propValueId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodId", goodId);
		map.put("propValueId", propValueId);
		return sqlSession.getMapper(GoodPropertyMapper.class).selectByGoodIdAndPropValueId(map);
	}

	@Override
	public List<GoodProperty> findGoodSalesProperties(
			GoodProperty salesGoodProperty) {
		return sqlSession.getMapper(GoodPropertyMapper.class).selectSalesList(salesGoodProperty);
	}

	@Override
	public List<GoodProperty> findInputNotKeyGoodProperties(
			GoodProperty gnotKeyoodProperty) {
		return sqlSession.getMapper(GoodPropertyMapper.class).selectInputNotKeyGoodProperties(gnotKeyoodProperty);
	}
	
	@Override
	public List<GoodProperty> findSalesInputKeyGoodProperties(
			GoodProperty salesInputProperty) {
		return sqlSession.getMapper(GoodPropertyMapper.class).selectSalesInputKeyGoodProperties(salesInputProperty);
	}

	@Override
	public void removeById(Long id) {
		 sqlSession.getMapper(GoodPropertyMapper.class).removeById(id);
	}



}
