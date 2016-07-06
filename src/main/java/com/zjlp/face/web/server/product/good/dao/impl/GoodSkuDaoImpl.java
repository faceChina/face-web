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
import com.zjlp.face.web.mapper.GoodSkuMapper;
import com.zjlp.face.web.server.product.good.dao.GoodSkuDao;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.vo.GoodSkuVo;

@Repository
public class GoodSkuDaoImpl implements GoodSkuDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public void add(GoodSku goodSku) {
		sqlSession.getMapper(GoodSkuMapper.class).insertSelective(goodSku);
	}
	
	@Override
	@RedisCached(mode=CachedMode.CLEAR,key={"goodSku:id"})
	public void editGoodSku(GoodSku goodSku) {
		sqlSession.getMapper(GoodSkuMapper.class).updateByPrimaryKeySelective(goodSku);
	}
	
	@Override
	@RedisCached(mode=CachedMode.UPDATE,key={"goodSku:id"},prop={"status","stock"})
	public void edit(GoodSku goodSku) {
		sqlSession.getMapper(GoodSkuMapper.class).updateByPrimaryKeySelective(goodSku);
	}
	
	@Override
	@RedisCached(mode=CachedMode.GET,method=CachedMethod.GET_SET,key={"goodSku"})
	public GoodSku getById(Long id) {
		return sqlSession.getMapper(GoodSkuMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public void delete(Long id) {
		sqlSession.getMapper(GoodSkuMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public List<GoodSku> findGoodSkusByGoodId(Long goodId) {
		return sqlSession.getMapper(GoodSkuMapper.class).selectGoodSkusByGoodId(goodId);
	}
	
	@Override
	public List<GoodSku> findAllGoodSkuByGoodId(Long goodId) {
		return sqlSession.getMapper(GoodSkuMapper.class).selectAllGoodSkusByGoodId(goodId);
	}

	@Override
	public GoodSku selectGoodskuByGoodIdAndPrprerty(Long goodId, String skuProperties) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodId", goodId);
		map.put("skuProperties", skuProperties);
		return sqlSession.getMapper(GoodSkuMapper.class).selectByGoodIdAndPrprerty(map);
	}

	@Override
	public void removeByGoodId(Long goodId) {
		sqlSession.getMapper(GoodSkuMapper.class).removeByGoodId(goodId);
	}

	@Override
	public void removeIdById(Long id) {
		sqlSession.getMapper(GoodSkuMapper.class).removeById(id);
	}

	@Override
	public List<GoodSku> selectGoodskuByGoodIdAndPrprerty(GoodSku goodSku) {
		return sqlSession.getMapper(GoodSkuMapper.class).selectGoodskuByGoodIdAndPrprerty(goodSku);
	}

	@Override
	public List<GoodSkuVo> findGoodSkuByAppointmentIdAndShopTypeId(Long appointmentId, Long shopTypeId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("appointmentId", appointmentId);
		map.put("shopTypeId", shopTypeId);
		return sqlSession.getMapper(GoodSkuMapper.class).findGoodSkuByAppointmentIdAndShopTypeId(map);
	}


	
}
