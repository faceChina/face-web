package com.zjlp.face.web.server.trade.cart.domain.vo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import com.zjlp.face.web.server.user.user.domain.Address;


public class CartOrderVo {
	
	private Address address;
	
	private Integer buyType;
	
	private List<Address> addressList;
	
	private Map<Long,Set<SellerVo>> sellerMap;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Integer getBuyType() {
		return buyType;
	}

	public void setBuyType(Integer buyType) {
		this.buyType = buyType;
	}

	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}

	public Map<Long, Set<SellerVo>> getSellerMap() {
		return sellerMap;
	}

	public void setSellerMap(Map<Long, Set<SellerVo>> sellerMap) {
		this.sellerMap = sellerMap;
	}

	public static void main(String[] args) {
		CartOrderVo cartOrderVo = new CartOrderVo();
		Set<SellerVo> sellers = new HashSet<SellerVo>();
		SellerVo sellerVo1 = new SellerVo();
		sellerVo1.setSellerId(1L);
		SellerVo sellerVo2 = new SellerVo();
		sellerVo2.setSellerId(2L);
		sellers.add(sellerVo1);
		sellers.add(sellerVo2);
//		cartOrderVo.setSellers(sellers);
		System.out.println(JSONObject.fromObject(cartOrderVo).toString());
	}
}
