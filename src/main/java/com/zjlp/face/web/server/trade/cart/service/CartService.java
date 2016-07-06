package com.zjlp.face.web.server.trade.cart.service;

import java.util.List;

import com.zjlp.face.web.server.trade.cart.domain.Cart;
import com.zjlp.face.web.server.trade.cart.domain.CartDto;
import com.zjlp.face.web.server.trade.cart.domain.CartVo;

public interface CartService {
	
	/**
	 * @Description: 向购物车中添加商品
	 * @param cart
	 * @date: 2015年3月12日 下午1:48:58
	 * @author: zyl
	 */
	void addCart(Cart cart);
	
	/**
	 * @Description: 修改购物车中商品
	 * @param cart
	 * @date: 2015年3月12日 下午1:49:20
	 * @author: zyl
	 */
	void editCart(Cart cart);
	
	/**
	 * @Description: 删除购物车商品
	 * @param cartId
	 * @date: 2015年3月18日 上午10:05:31
	 * @author: zyl
	 */
	void deleteCart(Long cartId);
	
	/**
	 * @Description: 描述
	 * @param cartId
	 * @return
	 * @date: 2015年3月18日 上午10:05:49
	 * @author: zyl
	 */
	Cart getCartById(Long cartId);
	
	/**
	 * @Description: 查询用户购物车
	 * @param userId
	 * @return
	 * @date: 2015年3月12日 下午1:49:58
	 * @author: zyl
	 */
	List<CartDto> findCartListByUserId(Long userId);
	
	/**
	 * @Description: userId和shopNo查询购物车
	 * @param cartVo
	 * @date: 2015年3月19日 下午4:54:55
	 * @author: zyl
	 * @return
	 */
	List<CartDto> findCartListByUserIdAndShopNo(CartVo cartVo);
	
	Integer getCount(Long userId);
	
	/**
	 * @Description: 清除购物车无效商品
	 * @param userId
	 * @date: 2015年3月26日 下午3:32:12
	 * @author: zyl
	 */
	void delNullityByUserId(Long userId);

	/**
	 * 擦除代理信息
	 * @Title: clearSubbranchInfo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param subbranchId
	 * @date 2015年7月13日 下午2:53:20  
	 * @author lys
	 */
	void clearSubbranchInfo(Long subbranchId);
}
