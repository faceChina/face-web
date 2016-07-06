package com.zjlp.face.web.server.user.user.service;



public interface VaearService {
	
	/**
	 * 根据区域名称查询地区编码
	 * @param areaName
	 * @return
	 */
	Integer getAreaByAreaName(String areaName);
}
