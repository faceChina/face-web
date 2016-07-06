package com.zjlp.face.web.server.user.shop.domain.dto;

import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.DeliveryTemplate;

public class DeliveryTemplateDto extends DeliveryTemplate {

	private static final long serialVersionUID = 5863872841179881114L;

	private List<DeliveryTemplateItemDto> itemList = null;
	
	public List<DeliveryTemplateItemDto> getItemList() {
		return itemList;
	}
	public void setItemList(List<DeliveryTemplateItemDto> itemList) {
		this.itemList = itemList;
	}
}
