package com.zjlp.face.web.server.operation.popularize.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.operation.popularize.dao.PopularizeDao;
import com.zjlp.face.web.server.operation.popularize.domain.ShopPopularizeSetting;
import com.zjlp.face.web.server.operation.popularize.service.PopularizeService;

@Service
public class PopularizeServiceImpl implements PopularizeService {
	
	@Autowired
	private PopularizeDao popularizeDao;

	@Override
	public List<ShopPopularizeSetting> findPopularize(
			ShopPopularizeSetting param) {
		return this.popularizeDao.findPopularize(param);
	}

	@Override
	public void savePopularizeSetting(
			ShopPopularizeSetting shopPopularizeSetting) {
		this.popularizeDao.savePopularizeSetting(shopPopularizeSetting);
	}

	@Override
	public void updatePopularizeSetting(
			ShopPopularizeSetting shopPopularizeSetting) {
		this.popularizeDao.updatePopularizeSetting(shopPopularizeSetting);
	}

	@Override
	public ShopPopularizeSetting getShopPopularizeSetting(
			ShopPopularizeSetting setting) {
		return popularizeDao.getShopPopularizeSetting(setting);
	}
	
	


}
