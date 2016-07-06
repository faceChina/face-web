package com.zjlp.face.web.server.operation.popularize.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.ShopPopularizeSettingMapper;
import com.zjlp.face.web.server.operation.popularize.dao.PopularizeDao;
import com.zjlp.face.web.server.operation.popularize.domain.ShopPopularizeSetting;

@Repository
public class PopularizeDaoImpl implements PopularizeDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<ShopPopularizeSetting> findPopularize(
			ShopPopularizeSetting param) {
		return this.sqlSession.getMapper(ShopPopularizeSettingMapper.class).selectByParam(param);
	}

	@Override
	public void savePopularizeSetting(
			ShopPopularizeSetting shopPopularizeSetting) {
		this.sqlSession.getMapper(ShopPopularizeSettingMapper.class).insertSelective(shopPopularizeSetting);
	}

	@Override
	public void updatePopularizeSetting(
			ShopPopularizeSetting shopPopularizeSetting) {
		this.sqlSession.getMapper(ShopPopularizeSettingMapper.class).updateByPrimaryKeySelective(shopPopularizeSetting);
	}

	@Override
	public ShopPopularizeSetting getShopPopularizeSetting(
			ShopPopularizeSetting setting) {
		return sqlSession.getMapper(ShopPopularizeSettingMapper.class).selectShopPopularizeSetting(setting);
	}

}
