package com.zjlp.face.web.server.user.shop.producer.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopLocationDto;
import com.zjlp.face.web.server.user.shop.producer.ShopLocationProducer;
import com.zjlp.face.web.server.user.shop.service.LbsService;
import com.zjlp.face.web.server.user.shop.service.ShopService;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;
import com.zjlp.face.web.util.GisUtil;

@Repository("shopLocationProducer")
public class ShopLocationProducerImpl implements ShopLocationProducer {

	/**查找人数**/
	private static final int PERSON_NUMBER = 180;
	/**初始查找半径**/
	private static final long INITIAL_RADIUS= 1000L;
	/**最大查找半径**/
	private static final long MAX_RADIUS= 20000L;
	/**半径倍数**/
	private static final int MULTIPLE= 2;

	@Autowired
	private LbsService lbsService;
	@Autowired
	private ShopService shopService;

	@Override
	public List<ShopLocationDto> findShopsInNear(UserGisVo userGisVo) {
		List<ShopLocationDto> list = new ArrayList<ShopLocationDto>();
		list = this.find(list, INITIAL_RADIUS, userGisVo);
		// 店铺图片URL
		List<ShopLocationDto> results = new ArrayList<ShopLocationDto>();
		for (ShopLocationDto current : list) {
			if (StringUtils.isNotBlank(current.getShopNo())) {
				Shop shop = shopService.getShopByNo(current.getShopNo());
				current.setType(shop.getType());
				results.add(current);
			}
		}
		return results;
	}
	
	private List<ShopLocationDto> find(List<ShopLocationDto> list,long radius,UserGisVo userGisVo){
		double[] d = GisUtil.getAround(userGisVo.getLatitude().doubleValue(), userGisVo.getLongitude().doubleValue(), radius);
		userGisVo.setLeftLongitude(BigDecimal.valueOf(d[1]));
		userGisVo.setRightLongitude(BigDecimal.valueOf(d[3]));
		userGisVo.setDownLatitude(BigDecimal.valueOf(d[0]));
		userGisVo.setTopLatitude(BigDecimal.valueOf(d[2]));
		userGisVo.setNumber(200);
		list = lbsService.findShopInNear(userGisVo);
		if(list.size() < PERSON_NUMBER && radius < MAX_RADIUS){
			radius = radius*MULTIPLE;
			list = this.find(list, radius,userGisVo);
		}
		return list;
	}

}
