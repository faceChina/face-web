package com.zjlp.face.web.server.user.shop.service;

import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.ShopLocation;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopLocationDto;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;

/**
 * 店铺地理位置基础服务
 * @ClassName: LbsService 
 * @Description: (店铺地理位置基础服务) 
 * @author ah
 * @date 2014年9月28日 下午8:39:50
 */
public interface LbsService {

	/**
	 * 新增店铺地理位置
	 * @Title: addShopLocation 
	 * @Description: (新增店铺地理位置) 
	 * @param shopLocation
	 * @date 2014年9月28日 下午8:53:50  
	 * @author ah
	 */
	void addShopLocation(ShopLocation shopLocation);

	/**
	 * 编辑店铺地理位置
	 * @Title: editShopLocation 
	 * @Description: (编辑店铺地理位置) 
	 * @param shopLocation
	 * @date 2014年9月28日 下午8:54:14  
	 * @author ah
	 */
	void editShopLocation(ShopLocation shopLocation);

	/**
	 * 根据店铺编号查询店铺地理位置
	 * @Title: getShopLocationByShopNo 
	 * @Description: (根据店铺编号查询店铺地理位置) 
	 * @param shopNo
	 * @return
	 * @date 2014年9月28日 下午8:54:43  
	 * @author ah
	 */
	ShopLocation getShopLocationByShopNo(String shopNo);

	/**
	 * 根据店铺编号查询店铺地理位置
	 * @Title: getShopLocationDtoByShopNo 
	 * @Description: (根据店铺编号查询店铺地理位置) 
	 * @param shopNo
	 * @return
	 * @date 2014年9月29日 下午4:10:26  
	 * @author ah
	 */
	ShopLocationDto getShopLocationDtoByShopNo(String shopNo);

	/**
	 * 根据用户地理位置信息查找附近的店铺
	 * 
	 * @param userGisVo
	 * @return
	 */
	List<ShopLocationDto> findShopInNear(UserGisVo userGisVo);

}
