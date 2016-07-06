package com.zjlp.face.web.server.user.user.domain.dto;

import com.zjlp.face.web.server.user.user.domain.VArea;


public class VaearDto extends VArea {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3800984319099374057L;
	//市编码
	private String cityCode;
	//市名称
	private String cityName;
	//省编码
	private String provinceCode;
	//省名称
	private String provinceName;
	//地区名称
	private String areaName;
	//地区编码
	private String areaCode;

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
