package com.zjlp.face.web.server.trade.cart.business;

import java.util.List;

import com.zjlp.face.web.server.trade.cart.domain.Cart;
import com.zjlp.face.web.server.trade.cart.domain.CartDto;
import com.zjlp.face.web.server.trade.cart.domain.CartVo;
import com.zjlp.face.web.server.trade.cart.domain.vo.SellerVo;
import com.zjlp.face.web.server.user.shop.domain.vo.ShopVo;


public interface CartBusiness {
	
	/**
	 * @Description: 添加商品
	 * @param cart
	 * @date: 2015年3月18日 上午10:04:18
	 * @author: zyl
	 */
	void addCart(Cart cart);
	
	/**
	 * @Description: 删除购物车
	 * @param id
	 * @date: 2015年3月18日 上午10:04:21
	 * @author: zyl
	 */
	void deleteCart(Long id);
	
	/**
	 * @Description: 修改购物车商品
	 * @param cart
	 * @date: 2015年3月18日 上午10:04:25
	 * @author: zyl
	 */
	void editCart(Cart cart);
	
	/**
	 * @Description: 查询用户购物车
	 * @param userId
	 * @return
	 * @date: 2015年3月18日 上午10:04:29
	 * @author: zyl
	 */
	List<CartDto> findCartListByUserId(Long userId);
	
	/**
	 * @Description: 保存购物车修改
	 * @param cartVo
	 * @date: 2015年3月19日 下午4:49:08
	 * @author: zyl
	 */
	void saveCart(CartVo cartVo);
	
	Integer getCount(Long userId);
	
	/**
	 * @Description: 清除无效商品
	 * @param userId
	 * @date: 2015年3月26日 下午3:31:24
	 * @author: zyl
	 */
	void delNullityByUserId(Long userId);
	
	/**
	 * 购买时处理信息
	 * @Title: proccessByShop 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopVo 店铺号
	 * @param userId 用户号
	 * @param cartDtoList 购买该店铺的商品集合
	 * @return
	 * @date 2015年4月21日 上午10:36:57  
	 * @author dzq
	 */
	SellerVo proccessByShop(ShopVo shopVo, Long userId, List<CartDto> cartDtoList);

}
