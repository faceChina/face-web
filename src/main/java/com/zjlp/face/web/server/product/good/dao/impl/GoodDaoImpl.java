package com.zjlp.face.web.server.product.good.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.jredis.annotation.RedisCached;
import com.zjlp.face.jredis.annotation.enums.CachedMethod;
import com.zjlp.face.jredis.annotation.enums.CachedMode;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.mapper.GoodMapper;
import com.zjlp.face.web.server.product.good.dao.GoodDao;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.vo.GoodProfitVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodVo;
import com.zjlp.face.web.server.product.good.domain.vo.RecommendGoodVo;
@Repository
public class GoodDaoImpl implements GoodDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	@RedisCached(mode=CachedMode.CLEAR,container={"goodList:shopNo"})
	public void add(Good good) {
		sqlSession.getMapper(GoodMapper.class).insertSelective(good);
	}

	@Override
	@RedisCached(mode=CachedMode.CLEAR,container={"goodList:shopNo"},key={"good:id"})
	public void editGood(Good good) {
		sqlSession.getMapper(GoodMapper.class).updateByPrimaryKeySelective(good);
	}
	
	@Override
	@RedisCached(mode=CachedMode.UPDATE,key={"good:id"},prop={"sort","status","salesVolume","browerTime","inventory"})
	public void edit(Good good) {
		sqlSession.getMapper(GoodMapper.class).updateByPrimaryKeySelective(good);
	}
	
	@Override
	@RedisCached(mode=CachedMode.GET,method=CachedMethod.GET_SET,key={"good"})
	public Good getById(Long id) {
		return sqlSession.getMapper(GoodMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public Integer getPageCount(GoodVo goodVo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodVo", goodVo);
		return sqlSession.getMapper(GoodMapper.class).selectPageCount(map);
	}

	@Override
	@RedisCached(mode=CachedMode.GET,method=CachedMethod.GET_SET,key={"findPageShopType"},container={"goodList:shopNo"})
	public List<GoodVo> findPageShopType(GoodVo goodVo, int start, int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodVo", goodVo);
		map.put("start", start);
		map.put("pageSize", pageSize);
		return sqlSession.getMapper(GoodMapper.class).selectPageList(map);
	}

	@Override
	@RedisCached(mode=CachedMode.GET,method=CachedMethod.GET_SET,key={"findGoodsList"},container={"goodList:shopNo"})
	public List<Good> findGoodsList(Good good) {
		return sqlSession.getMapper(GoodMapper.class).selectList(good);
	}

	@Override
	@RedisCached(mode=CachedMode.GET,method=CachedMethod.GET_SET,key={"findGoodByShopTypeId"},container={"goodList:shopNo"})
	public List<GoodVo> findGoodByShopTypeId(Good good, Long id, int goodNum) {
		 Map<String, Object> map = new HashMap<String, Object>();
        map.put("shopTypeId", id);
        map.put("goodNum", goodNum);
        return sqlSession.getMapper(GoodMapper.class).findGoodByShopTypeId(map);
	}

	@Override
	public Integer countGoodNum(GoodVo goodVo){
		return sqlSession.getMapper(GoodMapper.class).countGoodNum(goodVo);
	}

	@Override
	public List<Good> findGoodByIds(List<String> goodIdList) {
		if(goodIdList.size()>0){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("goodIdList", goodIdList);
			return sqlSession.getMapper(GoodMapper.class).findGoodByIds(map);
		}
		return new ArrayList<Good>();
	}


	@Override
	@RedisCached(mode=CachedMode.GET,method=CachedMethod.GET_SET,key={"findGoodVoPageForWap"},container={"goodList"})
	public Pagination<GoodVo> findGoodVoPageForWap(String shopNo, GoodVo goodVo, Pagination<GoodVo> pagination) {
		Integer count=sqlSession.getMapper(GoodMapper.class).countGoodVoNum(goodVo);
		pagination.setTotalRow(count);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("goodVo", goodVo);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		pagination.setDatas(sqlSession.getMapper(GoodMapper.class).findGoodVoPageForWap(map));
		return pagination;
	}

	@Override
	public Integer countGoodVoNum(GoodVo goodVo) {
		return sqlSession.getMapper(GoodMapper.class).countGoodVoNum(goodVo);
	}

	@Override
	@RedisCached(mode=CachedMode.GET,method=CachedMethod.GET_SET,key={"findGoodVoForWapByShopTypeId"},container={"goodList:shopNo"})
	public List<GoodVo> findGoodVoForWapByShopTypeId(Good good, Long shopTypeId, int goodNum) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("shopTypeId", shopTypeId);
		map.put("goodNum", goodNum);
		return sqlSession.getMapper(GoodMapper.class).findGoodVoForWapByShopTypeId(map);
	}

	@Override
	public Integer getCountByStatus(String shopNo, Integer status) {
		return sqlSession.getMapper(GoodMapper.class).getCountByStatus(shopNo, status);
	}

	@Override
	public void editBrowerTimeById(Long id) {
		sqlSession.getMapper(GoodMapper.class).editBrowerTimeById(id);
	}

	@Override
	public Pagination<RecommendGoodVo> getRecommendGood(Long userId,String goodName,
			Pagination<RecommendGoodVo> pagination) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("goodName", goodName);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		
		List<RecommendGoodVo> datas = sqlSession.getMapper(GoodMapper.class).getRecommendGood(map);
		Integer count = sqlSession.getMapper(GoodMapper.class).getRecommendGoodCount(map);
		pagination.setDatas(datas);
		pagination.setTotalRow(count);
		return pagination;
	}

	@Override
	public Integer countProfitGoods(Long subbranchId, Long userId, String searchKey) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subbranchId", subbranchId);
		map.put("userId", userId);
		map.put("searchKey", searchKey);
		return this.sqlSession.getMapper(GoodMapper.class).countProfitGoods(map);
	}

	@Override
	public List<GoodProfitVo> selectProfitGoods(Long subbranchId, Long userId, Integer sortBy, String searchKey,
			Pagination<GoodProfitVo> pagination) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subbranchId", subbranchId);
		map.put("userId", userId);
		map.put("sortBy", sortBy);
		map.put("searchKey", searchKey);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		return this.sqlSession.getMapper(GoodMapper.class).selectProfitGoods(map);
	}

}
