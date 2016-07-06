package com.zjlp.face.web.server.operation.popularize.service;

import java.util.List;

import com.zjlp.face.web.server.operation.popularize.domain.ShopPopularizeSetting;

public interface PopularizeService {

	/**
	 * 
	 * @Title: findPopularize 
	 * @Description: 查询店铺的所有推广设置，包括店铺推广和分销推广
	 * @param param
	 * @return
	 * @date 2015年4月29日 下午5:22:08  
	 * @author cbc
	 */
	List<ShopPopularizeSetting> findPopularize(ShopPopularizeSetting param);

	/**
	 * 
	 * @Title: savePopularizeSetting 
	 * @Description: 保存推广设置
	 * @param shopPopularizeSetting
	 * @date 2015年4月29日 下午7:58:59  
	 * @author cbc
	 */
	void savePopularizeSetting(ShopPopularizeSetting shopPopularizeSetting);

	/**
	 * 
	 * @Title: updatePopularizeSetting 
	 * @Description: 更新推广设置
	 * @param shopPopularizeSetting
	 * @date 2015年4月29日 下午8:19:24  
	 * @author cbc
	 */
	void updatePopularizeSetting(ShopPopularizeSetting shopPopularizeSetting);

	/**
	 * 查询
	 * @Title: getShopPopularizeSetting 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param setting
	 * @return
	 * @date 2015年5月13日 下午3:22:30  
	 * @author dzq
	 */
	ShopPopularizeSetting getShopPopularizeSetting(ShopPopularizeSetting setting);


}
