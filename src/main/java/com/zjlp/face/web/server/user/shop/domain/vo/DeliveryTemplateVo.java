package com.zjlp.face.web.server.user.shop.domain.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.DeliveryTemplateItem;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateDto;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateItemDto;
import com.zjlp.face.web.server.user.user.domain.dto.VaearTree;

public class DeliveryTemplateVo implements Serializable {
	
	private static final long serialVersionUID = 8336383846168661122L;
	// 运费模版ID
	private Long id;
	// 店铺号
	private String shopNo;
	// 运费模版名称
	private String name;
	//快递子项
	private List<DeliveryTemplateItemVo> itemVoList;
	
	public DeliveryTemplateVo(){}
	public DeliveryTemplateVo(DeliveryTemplateDto templateDto, 
			VaearTree vaearTree) {
		if (null == templateDto) return; 
		this.id = templateDto.getId();
		this.shopNo = templateDto.getShopNo();
		this.name = templateDto.getName();
		this.setItemList(templateDto.getItemList(), vaearTree);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<DeliveryTemplateItemVo> getItemVoList() {
		return itemVoList;
	}
	public void setItemVoList(List<DeliveryTemplateItemVo> itemVoList) {
		this.itemVoList = itemVoList;
	}
	public void setItemList(List<DeliveryTemplateItemDto> itemList, VaearTree vaearTree) {
		if (null == itemList || itemList.isEmpty()) {
			return;
		}
		itemVoList = new ArrayList<DeliveryTemplateItemVo>();
		for (DeliveryTemplateItem item : itemList) {
			if (null == item) continue;
			itemVoList.add(new DeliveryTemplateItemVo(item, vaearTree));
		}
	}
	
}
