package com.zjlp.face.web.server.trade.cart.dao;

import java.util.List;

import com.zjlp.face.web.server.trade.cart.domain.Cart;
import com.zjlp.face.web.server.trade.cart.domain.CartDto;
import com.zjlp.face.web.server.trade.cart.domain.CartVo;


public interface CartDao {
	
	void addCart(Cart cart);
	
	void editCart(Cart cart);
	
	void deleteCart(Long cartId);
	
	Cart getCartById(Long cartId);
	
	/**
	 * @Description: 查询用户购物车中的商品
	 * @param userId
	 * @return
	 * @date: 2015年3月12日 下午1:36:25
	 * @author: zyl
	 */
	List<CartDto> findCartListByUserId(Long userId);
	
	/**
	 * @Description: 查询购物车项
	 * @param shopNo
	 * @param userId
	 * @param goodSkuId
	 * @return
	 * @date: 2015年3月18日 上午10:06:46
	 * @author: zyl
	 */
	Cart getCart(String shopNo, Long userId, Long goodSkuId);
	
	Cart getCart(String shopNo, Long subbranchId, Long userId, Long goodSkuId);
	
	List<CartDto> findCartListByUserIdAndShopNo(CartVo cartVo);
	
	Integer getCount(Long userId);
	
	void delNullityByUserId(Long userId);

	void clearSubbranchInfo(Cart cart);


}
