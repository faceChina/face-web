package com.zjlp.face.web.server.user.shop.producer;

import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.dto.ShopLocationDto;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;

public interface ShopLocationProducer {

	/**
	 * 根据用户地理信息查询周边店铺
	 * 
	 * @param userGisVo
	 * @return
	 */
	List<ShopLocationDto> findShopsInNear(UserGisVo userGisVo);


}
