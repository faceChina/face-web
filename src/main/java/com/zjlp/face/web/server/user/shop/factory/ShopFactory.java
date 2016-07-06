package com.zjlp.face.web.server.user.shop.factory;

import com.zjlp.face.web.exception.ext.ShopException;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;



public interface ShopFactory {
	
	/**
	 * 新增产品
	 * @Title: addShop
	 * @Description: (新增产品)
	 * @param dto
	 * @date 2014年7月17日 下午9:44:33
	 * @author ah
	 */
	public Shop addShop(ShopDto dto) throws ShopException;
	
	/**
	 * 新增产品,有加锁
	 * @param dto
	 * @return
	 * @throws ShopException
	 */
	public Shop addShopLock(ShopDto dto) throws ShopException;
}
