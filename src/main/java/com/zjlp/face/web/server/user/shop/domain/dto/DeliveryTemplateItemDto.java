package com.zjlp.face.web.server.user.shop.domain.dto;

import java.util.ArrayList;
import java.util.List;

import com.zjlp.face.util.constants.ConstantsFiled;
import com.zjlp.face.web.job.init.impl.LoadAddressJob;
import com.zjlp.face.web.server.user.shop.domain.DeliveryTemplateItem;
import com.zjlp.face.web.server.user.user.domain.dto.VaearTree;

public class DeliveryTemplateItemDto extends DeliveryTemplateItem {

	private static final long serialVersionUID = -5850458767580034646L;

	public static final String ALL = "ALL";

	private static String allDestination;

	private List<String> codeList;
	
	private String startPostageYuan;
	
    private String addPostageYuan;

	public static List<DeliveryTemplateItem> cover(
			List<DeliveryTemplateItemDto> dtoList) {
		if (null == dtoList || dtoList.isEmpty()) {
			return null;
		}
		List<DeliveryTemplateItem> list = new ArrayList<DeliveryTemplateItem>();
		for (DeliveryTemplateItemDto dto : dtoList) {
			list.add(dto);
		}
		return list;
	}

	public static List<DeliveryTemplateItem> coverAndParse(
			List<DeliveryTemplateItemDto> dtoList) {
		if (null == dtoList || dtoList.isEmpty()) {
			return null;
		}
		List<DeliveryTemplateItem> list = new ArrayList<DeliveryTemplateItem>();
		for (DeliveryTemplateItemDto dto : dtoList) {
			if (ALL.equals(dto.getDestination())) {
				dto.setDestination(getAllDestination());
			}
			list.add(dto);
		}
		return list;
	}

	public static String getAllDestination() {
		if (null != allDestination) {
			return allDestination;
		}
		VaearTree vaearTree = LoadAddressJob.getProviceVaear();
		if (null == vaearTree || null == vaearTree.getChildren()
				|| vaearTree.getChildren().isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (VaearTree province : vaearTree.getChildren()) {
			sb.append(province.getCode()).append(ConstantsFiled.COMMA);
		}
		if (sb.length() > 0) {
			sb.substring(0, sb.length() - 1);
		}
		return sb.toString();
	}
	
	public String getDestinationString() {
		if (null == super.getDestination() 
				|| ALL.equals(super.getDestination())) return null;
		String[] arr = super.getDestination().split(ConstantsFiled.COMMA);
		VaearTree vaearTree = LoadAddressJob.getProviceVaear();
		StringBuilder sb = new StringBuilder();
		for (String code : arr) {
			sb.append(vaearTree.getAreaByCode(code)).append(ConstantsFiled.COMMA);
		}
		return sb.substring(0, sb.length() - 1);
	}
	
	public static void parse(DeliveryTemplateItem templateItem) {
		if (ALL.equals(templateItem.getDestination())) {
			templateItem.setDestination(getAllDestination());
		}
	}
	
	public static void parseList(List<DeliveryTemplateItem> itemList) {
		if (null == itemList) {
			return;
		}
		for (DeliveryTemplateItem item : itemList) {
			if (null == item) continue;
			parse(item);
		}
	}

	public List<String> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<String> codeList) {
		this.codeList = codeList;
	}

	public String getStartPostageYuan() {
		return startPostageYuan;
	}

	public void setStartPostageYuan(String startPostageYuan) {
		this.startPostageYuan = startPostageYuan;
	}

	public String getAddPostageYuan() {
		return addPostageYuan;
	}

	public void setAddPostageYuan(String addPostageYuan) {
		this.addPostageYuan = addPostageYuan;
	}
	
}
