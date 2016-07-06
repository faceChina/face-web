package com.zjlp.face.web.server.user.user.domain.dto;

import java.math.BigDecimal;

import com.zjlp.face.web.server.user.user.domain.User;

public class UserGisDto extends User {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -980920658076578747L;
	
	/**经度**/
	private BigDecimal longitude;
	/**纬度**/
	private BigDecimal latitude;
	/**职位*/
	private String position;
	/**公司*/
	private String company;
	/**公司名称**/
	private String companyName;
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

}
