package com.zjlp.face.web.server.product.material.domain.dto;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.FancyNavigation;
import com.zjlp.face.web.server.product.material.domain.FancyNavigationItem;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月24日 下午5:34:37
 *
 */
public class FancyNavigationDto extends FancyNavigation{

	private static final long serialVersionUID = -7764110416727798187L;

	List<FancyNavigationItem> subItems;

	public FancyNavigationDto() {
		super();
	}

	public FancyNavigationDto(FancyNavigation fancyNavigation) {
		super();
		this.setId(fancyNavigation.getId());
		this.setName(fancyNavigation.getName());
		this.setStatus(fancyNavigation.getStatus());
		this.setCreateTime(fancyNavigation.getCreateTime());
		this.setUpdateTime(fancyNavigation.getUpdateTime());
	}

	public List<FancyNavigationItem> getSubItems() {
		return subItems;
	}

	public void setSubItems(List<FancyNavigationItem> subItems) {
		this.subItems = subItems;
	}

	@Override
	public String toString() {
		return "FancyNavigationDto[subItems=" + subItems + "]";
	}

}
