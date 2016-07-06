package com.zjlp.face.web.server.operation.popularize.producer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.log.EC;
import com.zjlp.face.web.server.operation.popularize.domain.ShopPopularizeSetting;
import com.zjlp.face.web.server.operation.popularize.producer.PopularizeProducer;
import com.zjlp.face.web.server.operation.popularize.service.PopularizeService;

@Service
public class PopularizeProducerImpl implements PopularizeProducer {
	
	@Autowired
	private PopularizeService popularizeService;

	@Override
	public Integer getShopPopularizeRate(String shopNo, Integer type) {
		
		AssertUtil.hasLength(shopNo, EC.prtNull("shopNo"));
		AssertUtil.notNull(type,  EC.prtNull("type"));
		ShopPopularizeSetting setting = new ShopPopularizeSetting();
		setting.setShopNo(shopNo);
		setting.setStatus(1);
		setting.setType(type);
		setting = popularizeService.getShopPopularizeSetting(setting);
		if (null != setting) {
			return setting.getCommissionRate();
		}
		return null;
	}
	
	

}
