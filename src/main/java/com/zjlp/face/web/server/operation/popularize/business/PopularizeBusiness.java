package com.zjlp.face.web.server.operation.popularize.business;

import com.zjlp.face.web.server.operation.popularize.domain.ShopPopularizeSetting;

public interface PopularizeBusiness {

	/**
	 * 
	 * @Title: findShopPopularizeByShopNo 
	 * @Description: 通过shopNo查询该店铺的店铺推广设置 
	 * @param shopNo
	 * @return
	 * @date 2015年4月29日 下午5:08:30  
	 * @author cbc
	 */
	ShopPopularizeSetting findShopPopularizeByShopNo(String shopNo);

	/**
	 * 
	 * @Title: savePopularizeSetting 
	 * @Description: 保存推广设置
	 * @param shopPopularizeSetting
	 * @return
	 * @date 2015年4月29日 下午7:52:44  
	 * @author cbc
	 */
	boolean savePopularizeSetting(ShopPopularizeSetting shopPopularizeSetting);

	/**
	 * 
	 * @Title: updatePopularizeSetting 
	 * @Description: 更新推广设置 
	 * @param shopPopularizeSetting
	 * @return
	 * @date 2015年4月29日 下午8:18:21  
	 * @author cbc
	 */
	boolean updatePopularizeSetting(ShopPopularizeSetting shopPopularizeSetting);

	/**
	 * 
	 * @Title: DistributionPopularizeByShopNo 
	 * @Description: 通过店铺NO获得该店铺的分销推广设置
	 * @param shopNo
	 * @return
	 * @date 2015年4月30日 上午10:07:15  
	 * @author cbc
	 */
	ShopPopularizeSetting getDistributionPopularizeByShopNo(String shopNo);

}
