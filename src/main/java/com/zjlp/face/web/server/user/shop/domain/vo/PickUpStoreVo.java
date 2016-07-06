package com.zjlp.face.web.server.user.shop.domain.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.user.domain.dto.VaearTree;

public class PickUpStoreVo implements Serializable {

	private static final long serialVersionUID = -157163782377464722L;

	// 主键
	private Long id;
	// 店铺编号
	private String shopNo;
	// 自提点设置
	private String name;
	// 省
	private String province;
	// 市
	private String city;
	// 区、县
	private String county;
	// 详细街道地址，不需要重复填写省/市/区
	private String address;
	// 联系方式
	private String phone;
	
	private String fullAddress;
	
	public PickUpStoreVo() {
	}

	public PickUpStoreVo(PickUpStore store, VaearTree vaearTree) {
		this.id = store.getId();
		this.shopNo = store.getShopNo();
		this.name = store.getName();
		this.setProvince(store.getProvince(), vaearTree);
		this.setCity(store.getCity(), vaearTree);
		this.setCounty(store.getCounty(), vaearTree);
		this.address = store.getAddress();
		this.phone = store.getPhone();
	}

	public Long getId() {
		return id;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province, VaearTree vaearTree) {
		if (null == vaearTree) return;
		this.province = vaearTree.getAreaByCode(province);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city, VaearTree vaearTree) {
		if (null == vaearTree) return;
		this.city = vaearTree.getAreaByCode(city);
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county, VaearTree vaearTree) {
		if (null == vaearTree) return;
		this.county = vaearTree.getAreaByCode(county);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public static List<PickUpStoreVo> cover(List<? extends PickUpStore> lists,
			VaearTree vaearTree) {
		if (null == lists || lists.isEmpty()) {
			return null;
		}
		List<PickUpStoreVo> results = new ArrayList<PickUpStoreVo>();
		for (PickUpStore store : lists) {
			if (null == store)
				continue;
			results.add(new PickUpStoreVo(store, vaearTree));
		}
		return results;
	}
}
