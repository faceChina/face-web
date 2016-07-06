package com.zjlp.face.web.server.operation.subbranch.domain.vo;

import java.io.Serializable;


public class GoodSkuDifferenceVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//商品sku最大销售价
	private Long max;

	//商品sku最小销售价
	private Long min;

	public Long getMax() {
		return max;
	}

	public void setMax(Long max) {
		this.max = max;
	}

	public Long getMin() {
		return min;
	}

	public void setMin(Long min) {
		this.min = min;
	}
}
