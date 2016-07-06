package com.zjlp.face.web.server.trade.cart.domain;

import java.util.List;


public class CartVo extends Cart {
	
	private List<Cart> cartList;
	
	public List<Cart> getCartList(){
		return cartList;
	}
	
	public void setCartList(List<Cart> cartList){
		this.cartList = cartList;
	}
}
