package com.zjlp.face.web.server.product.good.domain.vo;

import com.zjlp.face.web.server.product.good.domain.Good;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月31日 下午8:40:37
 *
 */
public class GoodProfitVo extends Good {

	private static final long serialVersionUID = 1L;

	/** 收藏数 **/
	private Integer favoritesQuantity;
	/** 预计佣金 **/
	private String preProfitStr;
	/** 佣金 **/
	private Integer profit;
	/** 佣金积 **/
	private Double profitDou;
	/** 市场价str **/
	private String marketPriceStr;
	/** 排序 : 0上架时间;1佣金;2销量;3收藏; **/
	private Integer sortBy;
	/** 商品详情路径 **/
	@SuppressWarnings("unused")
	private String goodInfopath;
	/** 商品详情路径卖家自己看 **/
	@SuppressWarnings("unused")
	private String goodInfopathForSeller;
	/**分店ID**/
	private Long subbranchId;

	public Integer getFavoritesQuantity() {
		return favoritesQuantity;
	}

	public void setFavoritesQuantity(Integer favoritesQuantity) {
		this.favoritesQuantity = favoritesQuantity;
	}

	public String getPreProfitStr() {
		return preProfitStr;
	}

	public void setPreProfitStr(String preProfitStr) {
		this.preProfitStr = preProfitStr;
	}

	public Integer getProfit() {
		return profit;
	}

	public void setProfit(Integer profit) {
		this.profit = profit;
	}

	public Integer getSortBy() {
		return sortBy;
	}

	public void setSortBy(Integer sortBy) {
		this.sortBy = sortBy;
	}

	public String getMarketPriceStr() {
		return marketPriceStr;
	}

	public void setMarketPriceStr(String marketPriceStr) {
		this.marketPriceStr = marketPriceStr;
	}

	public String getGoodInfopath() {
		StringBuilder builder = new StringBuilder("/wap/");
		builder.append(this.getShopNo()).append("/any/item/").append(this.getId()).append(".htm?subbranchId=").append(this.subbranchId);
		return builder.toString();
	}

	public void setGoodInfopath(String goodInfopath) {
		this.goodInfopath = goodInfopath;
	}

	public String getGoodInfopathForSeller() {
		StringBuilder builder = new StringBuilder("/wap/");
		builder.append(this.getShopNo()).append("/any/item/seller/").append(this.getId()).append(".htm");
		return builder.toString();
	}

	public void setGoodInfopathForSeller(String goodInfopathForSeller) {
		this.goodInfopathForSeller = goodInfopathForSeller;
	}

	public Long getSubbranchId() {
		return subbranchId;
	}

	public void setSubbranchId(Long subbranchId) {
		this.subbranchId = subbranchId;
	}

	public Double getProfitDou() {
		return profitDou;
	}

	public void setProfitDou(Double profitDou) {
		this.profitDou = profitDou;
	}
	

}
