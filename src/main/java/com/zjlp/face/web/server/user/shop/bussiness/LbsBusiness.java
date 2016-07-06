package com.zjlp.face.web.server.user.shop.bussiness;

import com.zjlp.face.web.server.user.shop.domain.ShopLocation;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopLocationDto;

/**
 * 店铺地理位置业务层
 * @ClassName: LbsBusiness 
 * @Description: (店铺地理位置业务层) 
 * @author ah
 * @date 2014年9月28日 下午8:28:53
 */
public interface LbsBusiness {

	/**
	 * 查询店铺地理位置
	 * @Title: getShopLocationByShopNo 
	 * @Description: (查询店铺地理位置) 
	 * @param shopNo
	 * @return
	 * @date 2014年9月28日 下午8:30:23  
	 * @author ah
	 */
	ShopLocationDto getShopLocationByShopNo(String shopNo);

	/**
	 * 保存店铺地理位置
	 * @Title: saveShopLocation 
	 * @Description: (保存店铺地理位置) 
	 * @param shopLocation
	 * @date 2014年9月28日 下午8:32:38  
	 * @author ah
	 */
	void saveShopLocation(ShopLocation shopLocation);

	/**
	 * 查询店铺地理位置
	 * @Title: getShopLocationByShopNoForApp 
	 * @Description: (查询店铺地理位置) 
	 * @param shopNo
	 * @return
	 * @date 2014年9月29日 下午4:06:52  
	 * @author ah
	 */
	ShopLocationDto getShopLocationByShopNoForApp(String shopNo);

}
