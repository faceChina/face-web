package com.zjlp.face.web.server.user.shop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.web.server.user.shop.dao.LbsDao;
import com.zjlp.face.web.server.user.shop.domain.ShopLocation;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopLocationDto;
import com.zjlp.face.web.server.user.shop.service.LbsService;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class LbsServiceImpl implements LbsService {

	@Autowired
	private LbsDao lbsDao;
	
	@Override
	public void addShopLocation(ShopLocation shopLocation) {
		lbsDao.addShopLocation(shopLocation);
	}

	@Override
	public void editShopLocation(ShopLocation shopLocation) {
		lbsDao.editShopLocation(shopLocation);
	}

	@Override
	public ShopLocation getShopLocationByShopNo(String shopNo) {
		return lbsDao.getShopLocationByShopNo(shopNo);
	}

	@Override
	public ShopLocationDto getShopLocationDtoByShopNo(String shopNo) {
		return lbsDao.getShopLocationDtoByShopNo(shopNo);
	}

	@Override
	public List<ShopLocationDto> findShopInNear(UserGisVo userGisVo) {
		return lbsDao.findShopsInNear(userGisVo);
	}

}
