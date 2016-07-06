package com.zjlp.face.web.server.operation.popularize.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.web.server.operation.popularize.business.PopularizeBusiness;
import com.zjlp.face.web.server.operation.popularize.domain.ShopPopularizeSetting;
import com.zjlp.face.web.server.operation.popularize.service.PopularizeService;

@Service
public class PopularizeBusinessImpl implements PopularizeBusiness {

	@Autowired
	private PopularizeService popularizeService;
	
	@Override
	public ShopPopularizeSetting findShopPopularizeByShopNo(String shopNo) {
		ShopPopularizeSetting param = new ShopPopularizeSetting();
		param.setShopNo(shopNo);
		param.setType(ShopPopularizeSetting.TYPE_SHOP);
		List<ShopPopularizeSetting> shopPopularizeSettings = this.popularizeService.findPopularize(param);
		if (null !=shopPopularizeSettings && !shopPopularizeSettings.isEmpty()) {
			return shopPopularizeSettings.get(0);
		}
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackForClassName="PopularizeException")
	public boolean savePopularizeSetting(
			ShopPopularizeSetting shopPopularizeSetting) {
		try {
			this.popularizeService.savePopularizeSetting(shopPopularizeSetting);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updatePopularizeSetting(
			ShopPopularizeSetting shopPopularizeSetting) {
		try {
			this.popularizeService.updatePopularizeSetting(shopPopularizeSetting);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ShopPopularizeSetting getDistributionPopularizeByShopNo(String shopNo) {
		ShopPopularizeSetting param = new ShopPopularizeSetting();
		param.setShopNo(shopNo);
		param.setType(ShopPopularizeSetting.TYPE_DISTRIBUTION);
		List<ShopPopularizeSetting> shopPopularizeSettings = this.popularizeService.findPopularize(param);
		if (null !=shopPopularizeSettings && !shopPopularizeSettings.isEmpty()) {
			return shopPopularizeSettings.get(0);
		}
		return null;
	}


}
