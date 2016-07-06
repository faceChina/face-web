package com.zjlp.face.web.server.user.shop.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.ShopLocationMapper;
import com.zjlp.face.web.server.user.shop.dao.LbsDao;
import com.zjlp.face.web.server.user.shop.domain.ShopLocation;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopLocationDto;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;

@Repository("LbsDao")
public class LbsDaoImpl implements LbsDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addShopLocation(ShopLocation shopLocation) {
		sqlSession.getMapper(ShopLocationMapper.class).insertSelective(shopLocation);
	}

	@Override
	public void editShopLocation(ShopLocation shopLocation) {
		sqlSession.getMapper(ShopLocationMapper.class).updateByPrimaryKeySelective(shopLocation);

	}

	@Override
	public ShopLocation getShopLocationByShopNo(String shopNo) {
		return sqlSession.getMapper(ShopLocationMapper.class).selectByShopNo(shopNo);
	}

	@Override
	public ShopLocationDto getShopLocationDtoByShopNo(String shopNo) {
		return sqlSession.getMapper(ShopLocationMapper.class).selectShopLocationDtoByShopNo(shopNo);
	}

	@Override
	public List<ShopLocationDto> findShopsInNear(UserGisVo userGisVo) {
		List<ShopLocationDto> list = this.sqlSession.getMapper(ShopLocationMapper.class).selectShopGisPage(userGisVo);
		return list;
	}

}
