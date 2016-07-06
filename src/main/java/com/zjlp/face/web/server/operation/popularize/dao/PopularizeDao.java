package com.zjlp.face.web.server.operation.popularize.dao;

import java.util.List;

import com.zjlp.face.web.server.operation.popularize.domain.ShopPopularizeSetting;

public interface PopularizeDao {

	/**
	 * 
	 * @Title: findPopularize 
	 * @Description: 查询所有推广设定 
	 * @param param
	 * @return
	 * @date 2015年4月29日 下午5:25:39  
	 * @author cbc
	 */
	List<ShopPopularizeSetting> findPopularize(ShopPopularizeSetting param);

	/**
	 * 
	 * @Title: savePopularizeSetting 
	 * @Description: 保存商铺推广设置 
	 * @param shopPopularizeSetting
	 * @date 2015年4月29日 下午7:59:44  
	 * @author cbc
	 */
	void savePopularizeSetting(ShopPopularizeSetting shopPopularizeSetting);

	/**
	 * 
	 * @Title: updatePopularizeSetting 
	 * @Description: 更新商铺推广设置
	 * @param shopPopularizeSetting
	 * @date 2015年4月29日 下午8:20:12  
	 * @author cbc
	 */
	void updatePopularizeSetting(ShopPopularizeSetting shopPopularizeSetting);
	/**
	 * 查询
	 * @Title: getShopPopularizeSetting 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param setting
	 * @return
	 * @date 2015年5月13日 下午3:23:19  
	 * @author dzq
	 */
	ShopPopularizeSetting getShopPopularizeSetting(ShopPopularizeSetting setting);

}
