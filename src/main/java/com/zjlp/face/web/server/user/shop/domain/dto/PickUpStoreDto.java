package com.zjlp.face.web.server.user.shop.domain.dto;

import com.zjlp.face.util.page.Aide;
import com.zjlp.face.web.server.user.shop.domain.PickUpStore;

public class PickUpStoreDto extends PickUpStore {

	private static final long serialVersionUID = 3231182770217198972L;
	
	private Aide aide = new Aide();
	
	public PickUpStoreDto(){}
	
	public PickUpStoreDto(String shopNo) {
		this.setShopNo(shopNo);
	}
	public Aide getAide() {
		return aide;
	}
	public void setAide(Aide aide) {
		this.aide = aide;
	}
	
}
