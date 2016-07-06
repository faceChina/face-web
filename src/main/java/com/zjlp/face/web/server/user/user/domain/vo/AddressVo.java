package com.zjlp.face.web.server.user.user.domain.vo;

import java.io.Serializable;
import java.util.List;

import com.zjlp.face.web.server.user.user.domain.Address;
import com.zjlp.face.web.server.user.user.domain.dto.VaearTree;

public class AddressVo implements Serializable {

	private static final long serialVersionUID = 6838678388303745250L;
	// 主键
	private Long id;
	// 收货人姓名
	private String name;
	// 手机号
	private String cell;
	// 详细地址
	private String addressDetail;
	// 是否默认（0 否 1是）
	private Integer isDefault;
	
	private String provice;
	
	private String city;
	
	private String county;
	
	public AddressVo(){}
	public AddressVo(Address address) {
		this.id = address.getId();
		this.name = address.getName();
		this.cell = address.getCell();
		this.isDefault = address.getIsDefault();
		this.addressDetail = address.getAddressDetail();
	}
	
	public void setVArea(List<VaearTree> treeList) {
		if (null == treeList || treeList.isEmpty()) {
			return;
		}
		this.provice = String.valueOf(treeList.get(0).getCode());
		if (treeList.size() > 1) {
			this.city = String.valueOf(treeList.get(1).getCode());
		}
		if (treeList.size() > 2) {
			this.county = String.valueOf(treeList.get(2).getCode());
		}
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public String getProvice() {
		return null == provice ? "0" : provice;
	}
	public void setProvice(String provice) {
		this.provice = provice;
	}
	public String getCity() {
		return null == city ? "0" : city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return null == county ? "0" : county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	

}
