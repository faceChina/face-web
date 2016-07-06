package com.zjlp.face.web.server.user.shop.domain.vo;

import java.io.Serializable;

/**
 * 推荐商品(使用)
 * @author talo
 *
 */
public class RecommendShopVo implements Serializable {
	private static final long serialVersionUID = 5109978208568431542L;
	
	private String shopNo;		 //店铺No
	private String shopName;	 //店铺名称
	private String shopLogo; 	 //店铺Logo
	private Integer goodCount; 	 //商品数量
	private String shopUrl; 	 //店铺地址
	private boolean isSubbranch; //是否是代理
	private String subbranchId;  //代理id
	
	
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
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
	public Integer getGoodCount() {
		return goodCount;
	}
	public void setGoodCount(Integer goodCount) {
		this.goodCount = goodCount;
	}
	
	public String getShopUrl() {
		StringBuffer rul = new StringBuffer("/wap/").append(this.getShopNo()).append("/any/gwscIndex.htm");
		if (this.isSubbranch) {
			rul.append("?subbranchId=").append(this.getSubbranchId());
		}
		return rul.toString();
	}
	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}
	
	public boolean getIsSubbranch() {
		return isSubbranch;
	}
	public void setSubbranch(boolean isSubbranch) {
		this.isSubbranch = isSubbranch;
	}
	public String getSubbranchId() {
		return subbranchId;
	}
	public void setSubbranchId(String subbranchId) {
		this.subbranchId = subbranchId;
	}
	
}
