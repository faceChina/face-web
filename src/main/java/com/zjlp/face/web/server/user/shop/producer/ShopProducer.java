package com.zjlp.face.web.server.user.shop.producer;

import com.zjlp.face.web.exception.ext.ShopException;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;

public interface ShopProducer {

	/**
	 * 通过店铺编号查询店铺信息
	 * 
	 * @Title: getShopByNo
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 *            店铺编号
	 * @return
	 * @throws ShopException
	 * @date 2015年3月26日 上午10:00:00
	 * @author lys
	 */
	Shop getShopByNo(String shopNo) throws ShopException;

	/**
	 * 通过店铺编号查询店铺管理员ID
	 * 
	 * @Title: getShopUserIdByNo
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 * @return
	 * @throws ShopException
	 * @date 2015年4月15日 下午9:08:29
	 * @author dzq
	 */
	Long getShopUserIdByNo(String shopNo) throws ShopException;

	/**
	 * 保存店铺的账户信息
	 * 
	 * @Title: updateShopInfo
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shop
	 * @throws ShopException
	 * @date 2015年5月3日 上午10:30:15
	 * @author cbc
	 * @return
	 */
	boolean updateShopInfo(Shop shop) throws ShopException;

	/**
	 * 是否为外部供货商
	 * 
	 * @Title: isOuterSupplier
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 *            店铺编号
	 * @return
	 * @throws ShopException
	 * @date 2015年5月6日 下午3:14:05
	 * @author lys
	 */
	boolean isOuterSupplier(String shopNo) throws ShopException;

	/**
	 * 是否为内部供货商
	 * 
	 * @Title: isInnerSupplier
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 *            店铺编号
	 * @return
	 * @throws ShopException
	 * @date 2015年5月6日 下午3:14:25
	 * @author lys
	 */
	boolean isInnerSupplier(String shopNo) throws ShopException;

	/**
	 * 根据店铺的授权码获取店铺
	 * 
	 * @Title: getShopByAuthCode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param code
	 * @return
	 * @throws ShopException
	 * @date 2015年5月6日 下午4:34:23
	 * @author lys
	 */
	Shop getShopByAuthCode(String code) throws ShopException;

	/**
	 * 是否为免费店铺
	 * 
	 * @Title: isFreeShop
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 *            店铺编号
	 * @return
	 * @throws ShopException
	 * @date 2015年5月9日 下午4:48:55
	 * @author lys
	 */
	boolean isFreeShop(String shopNo) throws ShopException;
	
	/**
	 * 新增免费店铺（APP端）
	 * @Title: addFreeShopForApp 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopDto
	 * @return
	 * @throws ShopException
	 * @date 2015年5月27日 下午4:04:16  
	 * @author ah
	 */
	Shop addFreeShopForApp(ShopDto shopDto) throws ShopException;

	/**
	 * 根据用户id查询用户唯一店铺
	 * @Title: getShopByUserId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId 用户id
	 * @return
	 * @throws ShopException
	 * @date 2015年7月21日 下午3:21:47  
	 * @author lys
	 */
	Shop getShopByUserId(Long userId) throws ShopException;
}
