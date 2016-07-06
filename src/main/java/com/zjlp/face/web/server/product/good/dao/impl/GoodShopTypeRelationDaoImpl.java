package com.zjlp.face.web.server.product.good.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.GoodShopTypeRelationMapper;
import com.zjlp.face.web.server.product.good.dao.GoodShopTypeRelationDao;
import com.zjlp.face.web.server.product.good.domain.GoodShopTypeRelation;
@Repository
public class GoodShopTypeRelationDaoImpl implements GoodShopTypeRelationDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void add(GoodShopTypeRelation goodShopTypeRelation) {
		sqlSession.getMapper(GoodShopTypeRelationMapper.class).insert(goodShopTypeRelation);
	}

	@Override
	public void edit(GoodShopTypeRelation goodShopTypeRelation) {
		sqlSession.getMapper(GoodShopTypeRelationMapper.class).updateByPrimaryKeySelective(goodShopTypeRelation);
	}

	@Override
	public GoodShopTypeRelation getById(Long id) {
		return sqlSession.getMapper(GoodShopTypeRelationMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long id) {
		sqlSession.getMapper(GoodShopTypeRelationMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public List<GoodShopTypeRelation> findGoodShopTypeRelationByGoodId(
			Long goodId) {
		return sqlSession.getMapper(GoodShopTypeRelationMapper.class).selectByGoodId(goodId);
	}

	@Override
	public List<GoodShopTypeRelation> findGoodShopTypeRelationByShopTypeId(
			Long shopTypeId) {
		return sqlSession.getMapper(GoodShopTypeRelationMapper.class).selectByShopTypeId(shopTypeId);
	}

	@Override
	public void deleteAllGoodShopTypeByGoodId(Long goodId) {
		sqlSession.getMapper(GoodShopTypeRelationMapper.class).deleteAllByGoodId(goodId);
	}
	
	@Override
	public void deleteAllGoodShopTypeByShopTypeId(Long shopTypeId){
		sqlSession.getMapper(GoodShopTypeRelationMapper.class).deleteAllByShopTypeId(shopTypeId);
	}

}
