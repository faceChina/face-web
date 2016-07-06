package com.zjlp.face.web.server.user.shop.domain.dto;

import com.zjlp.face.web.server.user.shop.domain.Shop;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年6月23日 上午10:02:40
 *
 */
public class ShopSubbranchDto extends Shop {

	private static final long serialVersionUID = -8297275785873379616L;

	// 分店ID
	private Long subbrachId;
	// 店铺类型，1官网，2分店, 3历史分店
	private Integer role;
	// 招募下级代理权限：0无,1有
	private Integer permission;
	// 今日已经付款订单
	private Integer paidOrderNo;
	// 今日付款金额
	private String paidPrice;
	// 今日佣金
	private String liveProfit;
	// 佣金比例
	private Integer profit;
	// 分享链接
	private String shareLink;
	// 此分店代理的总店名
	private String shopName;
	private Integer status;
	//是否是历史分店, 0不是, 1是
	private Integer isHistorySubbranch;
	//今日支出佣金
	private String todayPayCommission;
	//是否是下级分店,0不是, 1是
	private Integer isBF;

	public ShopSubbranchDto() {

	}

	public Integer getIsHistorySubbranch() {
		return isHistorySubbranch;
	}

	public void setIsHistorySubbranch(Integer isHistorySubbranch) {
		this.isHistorySubbranch = isHistorySubbranch;
	}

	public Long getSubbrachId() {
		return subbrachId;
	}

	public void setSubbrachId(Long subbrachId) {
		this.subbrachId = subbrachId;
	}

	public Integer getPaidOrderNo() {
		return paidOrderNo;
	}

	public void setPaidOrderNo(Integer paidOrderNo) {
		this.paidOrderNo = paidOrderNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPaidPrice() {
		return paidPrice;
	}

	public void setPaidPrice(String paidPrice) {
		this.paidPrice = paidPrice;
	}

	public String getLiveProfit() {
		return liveProfit;
	}

	public void setLiveProfit(String liveProfit) {
		this.liveProfit = liveProfit;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Integer getProfit() {
		return profit;
	}

	public void setProfit(Integer profit) {
		this.profit = profit;
	}

	public String getShareLink() {
		return shareLink;
	}

	public void setShareLink(String shareLink) {
		this.shareLink = shareLink;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getPermission() {
		return permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	public String getTodayPayCommission() {
		return todayPayCommission;
	}

	public void setTodayPayCommission(String todayPayCommission) {
		this.todayPayCommission = todayPayCommission;
	}

	public Integer getIsBF() {
		return isBF;
	}

	public void setIsBF(Integer isBF) {
		this.isBF = isBF;
	}
	
	

}
