package com.zjlp.face.web.server.trade.cart.domain.vo;

import java.util.List;

import com.zjlp.face.web.server.trade.cart.domain.CartDto;
import com.zjlp.face.web.server.trade.coupon.domain.dto.CouponSetDto;

public class CartWithCouponVo {

	private List<CartDto> cardDtoList;
	
	private List<CouponSetDto> couponSetList;

	public List<CartDto> getCardDtoList() {
		return cardDtoList;
	}

	public void setCardDtoList(List<CartDto> cardDtoList) {
		this.cardDtoList = cardDtoList;
	}

	public List<CouponSetDto> getCouponSetList() {
		return couponSetList;
	}

	public void setCouponSetList(List<CouponSetDto> couponSetList) {
		this.couponSetList = couponSetList;
	}
}
