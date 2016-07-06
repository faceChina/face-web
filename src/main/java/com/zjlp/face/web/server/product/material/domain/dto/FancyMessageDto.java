package com.zjlp.face.web.server.product.material.domain.dto;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.FancyMessage;
import com.zjlp.face.web.server.product.material.domain.FancyMessageItem;

public class FancyMessageDto extends FancyMessage {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -7368329616469450936L;
	
	private List<FancyMessageItem> itemList;
	
	
	

	public FancyMessageDto() {
		super();
	}
	public FancyMessageDto(FancyMessage fancyMessage) {
		super();
		this.setId(fancyMessage.getId());
		this.setName(fancyMessage.getName());
		this.setStatus(fancyMessage.getStatus());
		this.setType(fancyMessage.getType());
		this.setCreateTime(fancyMessage.getCreateTime());
		this.setUpdateTime(fancyMessage.getUpdateTime());
		this.setPublishTime(fancyMessage.getPublishTime());
	}
	
	

	public List<FancyMessageItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<FancyMessageItem> itemList) {
		this.itemList = itemList;
	}

	@Override
	public String toString() {
		return "FancyMessageDto [itemList=" + itemList + "]";
	}
}
