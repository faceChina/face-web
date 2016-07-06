package com.zjlp.face.web.server.product.good.domain.vo;

import com.zjlp.face.web.server.product.good.domain.GoodSku;

public class GoodSkuVo extends GoodSku {
	private Long quantity;

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
}
