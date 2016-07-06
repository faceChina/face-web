package com.zjlp.face.web.server.user.shop.dao;

import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.ShopLocation;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopLocationDto;
import com.zjlp.face.web.server.user.user.domain.vo.UserGisVo;

/** 
 * 店铺地理位置持久层
 * @ClassName: LbsDao 
 * @Description: (店铺地理位置持久层) 
 * @author ah
 * @date 2014年9月28日 下午8:58:37  
 */
public interface LbsDao {

	/**
	 * 新增店铺地理位置
	 * @Title: addShopLocation 
	 * @Description: (新增店铺地理位置) 
	 * @param shopLocation
	 * @date 2014年9月28日 下午9:00:21  
	 * @author ah
	 */
	void addShopLocation(ShopLocation shopLocation);

	/**
	 * 编辑店铺地理位置
	 * @Title: editShopLocation 
	 * @Description: (编辑店铺地理位置) 
	 * @param shopLocation
	 * @date 2014年9月28日 下午9:00:52  
	 * @author ah
	 */
	void editShopLocation(ShopLocation shopLocation);

	/**
	 * 根据店铺编号查询店铺地理位置
	 * @Title: getShopLocationByShopNo 
	 * @Description: (根据店铺编号查询店铺地理位置) 
	 * @param shopNo
	 * @return
	 * @date 2014年9月28日 下午9:01:23  
	 * @author ah
	 */
	ShopLocation getShopLocationByShopNo(String shopNo);

	/**
	 * 根据店铺编号查询店铺地理位置
	 * @Title: getShopLocationDtoByShopNo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @return
	 * @date 2014年9月29日 下午4:12:26  
	 * @author Administrator
	 */
	ShopLocationDto getShopLocationDtoByShopNo(String shopNo);

	/**
	 * @param userGisVo
	 * @return
	 */
	List<ShopLocationDto> findShopsInNear(UserGisVo userGisVo);

}
