package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.trade.cart.domain.Cart;
import com.zjlp.face.web.server.trade.cart.domain.CartDto;
import com.zjlp.face.web.server.trade.cart.domain.CartVo;

public interface CartMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
	
	List<CartDto> selectCartListByUserId(Long userId);
	
	Cart getCart(Cart cart);
	
	List<CartDto> selectCartListByUserIdAndShopNo(CartVo cartVo);
	
	Integer getCount(Long userId);
	
	void delNullityByUserId(Long userId);

	void updateSubbranchInfo(Cart cart);
}