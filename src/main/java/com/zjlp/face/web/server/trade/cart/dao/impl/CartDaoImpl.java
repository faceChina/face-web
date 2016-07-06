package com.zjlp.face.web.server.trade.cart.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.CartMapper;
import com.zjlp.face.web.server.trade.cart.dao.CartDao;
import com.zjlp.face.web.server.trade.cart.domain.Cart;
import com.zjlp.face.web.server.trade.cart.domain.CartDto;
import com.zjlp.face.web.server.trade.cart.domain.CartVo;

@Repository
public class CartDaoImpl implements CartDao {
	
	@Autowired
	private SqlSession sqlSession;
	@Override
	public void addCart(Cart cart){
		sqlSession.getMapper(CartMapper.class).insertSelective(cart);
	}

	@Override
	public void editCart(Cart cart){
		sqlSession.getMapper(CartMapper.class).updateByPrimaryKeySelective(cart);
	}
	
	@Override
	public void deleteCart(Long cartId){
		sqlSession.getMapper(CartMapper.class).deleteByPrimaryKey(cartId);
	}
	
	@Override
	public Cart getCartById(Long cartId){
		return sqlSession.getMapper(CartMapper.class).selectByPrimaryKey(cartId);
	}
	
	@Override
	public List<CartDto> findCartListByUserId(Long userId){
		return sqlSession.getMapper(CartMapper.class).selectCartListByUserId(userId);
	}
	@Override
	public Cart getCart(String shopNo, Long userId, Long goodSkuId){
		Cart cart = new Cart();
		cart.setShopNo(shopNo);
		cart.setUserId(userId);
		cart.setGoodSkuId(goodSkuId);
		return sqlSession.getMapper(CartMapper.class).getCart(cart);
	}
	
	@Override
	public Cart getCart(String shopNo,Long subbranchId, Long userId, Long goodSkuId) {
		Cart cart = new Cart();
		cart.setShopNo(shopNo);
		cart.setSubbranchId(subbranchId);
		cart.setUserId(userId);
		cart.setGoodSkuId(goodSkuId);
		return sqlSession.getMapper(CartMapper.class).getCart(cart);
	}
	
	@Override
	public List<CartDto> findCartListByUserIdAndShopNo(CartVo cartVo){
		return sqlSession.getMapper(CartMapper.class).selectCartListByUserIdAndShopNo(cartVo);
	}
	
	@Override
	public Integer getCount(Long userId){
		return sqlSession.getMapper(CartMapper.class).getCount(userId);
	}
	
	@Override
	public void delNullityByUserId(Long userId){
		sqlSession.getMapper(CartMapper.class).delNullityByUserId(userId);
	}

	@Override
	public void clearSubbranchInfo(Cart cart) {
		sqlSession.getMapper(CartMapper.class).updateSubbranchInfo(cart);
	}


	
}
