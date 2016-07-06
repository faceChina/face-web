package com.zjlp.face.web.server.trade.order.domain.dto;

import com.zjlp.face.web.server.trade.order.domain.PurchaseOrderItem;

public class PurchaseOrderItemDto extends PurchaseOrderItem {

	private static final long serialVersionUID = 2306557942099067739L;
	
	private boolean isPopularize = false;
	private Long skuPrice;
	
	public boolean isPopularize() {
		return isPopularize;
	}
	public void setPopularize(boolean isPopularize) {
		this.isPopularize = isPopularize;
	}
	public Long getSkuPrice() {
		return skuPrice;
	}
	public void setSkuPrice(Long skuPrice) {
		this.skuPrice = skuPrice;
	}
	
}
