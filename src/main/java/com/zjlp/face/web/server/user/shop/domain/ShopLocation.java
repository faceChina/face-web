package com.zjlp.face.web.server.user.shop.domain;

import java.io.Serializable;
import java.util.Date;

public class ShopLocation implements Serializable {
	
	private static final long serialVersionUID = -5990228335606941453L;

	private Long id;

    private String shopNo;

    private String shopName;

    private String lat;

    private String lng;
    
    private Integer areaCode;

    private String address;

    private Date createTime;

    private Date updateTime;
    
    private String areaName;
    
    public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
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
        this.shopNo = shopNo == null ? null : shopNo.trim();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng == null ? null : lng.trim();
    }

	public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public static <T extends ShopLocation> ShopLocation createBySub(T t) {
    	ShopLocation loction = new ShopLocation();
    	loction.setId(t.getId());
    	loction.setShopNo(t.getShopNo());
    	loction.setShopName(t.getShopName());
    	loction.setLat(t.getLat());
    	loction.setLng(t.getLng());
    	loction.setAreaCode(t.getAreaCode());
    	loction.setAddress(t.getAddress());
    	loction.setCreateTime(t.getCreateTime());
    	loction.setUpdateTime(t.getUpdateTime());
    	loction.setAreaName(t.getAreaName());
    	return loction;
    }

	@Override
	public String toString() {
		return "ShopLocation [id=" + id + ", shopNo=" + shopNo + ", shopName="
				+ shopName + ", lat=" + lat + ", lng=" + lng + ", areaCode="
				+ areaCode + ", address=" + address + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}
}