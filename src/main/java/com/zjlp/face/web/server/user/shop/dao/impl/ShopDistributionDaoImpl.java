package com.zjlp.face.web.server.user.shop.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.ShopDistributionMapper;
import com.zjlp.face.web.server.user.shop.dao.ShopDistributionDao;
import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDistributionDto;

@Repository("shopDistributionDao")
public class ShopDistributionDaoImpl implements ShopDistributionDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void removeById(Long id) {
		sqlSession.getMapper(ShopDistributionMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public ShopDistribution getById(Long id) {
		return sqlSession.getMapper(ShopDistributionMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public ShopDistribution getByIdAndShopNo(ShopDistribution distribution) {
		return sqlSession.getMapper(ShopDistributionMapper.class).selectByIdAndShopNo(distribution);
	}

	@Override
	public List<ShopDistribution> findList(String shopNo) {
		return sqlSession.getMapper(ShopDistributionMapper.class).findListByShopNo(shopNo);
	}

	@Override
	public void editById(ShopDistribution distribution) {
		sqlSession.getMapper(ShopDistributionMapper.class).updateByPrimaryKeySelective(distribution);
	}

	@Override
	public Long add(ShopDistribution distribution) {
		sqlSession.getMapper(ShopDistributionMapper.class).insertSelective(distribution);
		return distribution.getId();
	}

	@Override
	public Integer getPsCount(ShopDistributionDto dto) {
		return sqlSession.getMapper(ShopDistributionMapper.class).selectPsCount(dto);
	}

	@Override
	public List<ShopDistributionDto> findPsPage(ShopDistributionDto dto) {
		return sqlSession.getMapper(ShopDistributionMapper.class).selectPsPage(dto);
	}

}
