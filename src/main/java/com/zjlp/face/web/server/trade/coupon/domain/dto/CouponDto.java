package com.zjlp.face.web.server.trade.coupon.domain.dto;

import com.zjlp.face.web.server.trade.coupon.domain.Coupon;

public class CouponDto extends Coupon {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -166503917634258051L;
	/**未使用1*/
	public static final Integer STATUS_UNUSED = 1;
	/**已使用2*/
	public static final Integer STATUS_USERD = 2;
	/**被用户删除*/
	public static final Integer STATUS_DELETE = -1;
	
	/**总店领取1*/
	public static final Integer DRAW_TYPE_SHOP = 1;
	/**分店领取2*/
	public static final Integer DRAW_TYPE_SUBBRANCH = 2;
	
	/**是否失效 1进行中 2未开始 3失效*/
	private Integer timeStatus;
	/**店铺LOGO*/
	private String shopLogo;
	/**店铺名称*/
	private String shopName;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopLogo() {
		return shopLogo;
	}

	public void setShopLogo(String shopLogo) {
		this.shopLogo = shopLogo;
	}

	public Integer getTimeStatus() {
		return timeStatus;
	}

	public void setTimeStatus(Integer timeStatus) {
		this.timeStatus = timeStatus;
	}
	
}
