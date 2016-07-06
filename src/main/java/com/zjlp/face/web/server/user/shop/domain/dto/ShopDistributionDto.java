package com.zjlp.face.web.server.user.shop.domain.dto;

import com.zjlp.face.util.page.Aide;
import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;

public class ShopDistributionDto extends ShopDistribution {

	private static final long serialVersionUID = -2927433578736436354L;
	
	private Aide aide = new Aide();
	
	public ShopDistributionDto(){}
	public ShopDistributionDto(String shopNo) {
		this.setShopNo(shopNo);
	}

	public Aide getAide() {
		return aide;
	}
	public void setAide(Aide aide) {
		this.aide = aide;
	}

}
