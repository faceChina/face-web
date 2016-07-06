package com.zjlp.face.web.server.user.weixin.domain.dto;

import com.zjlp.face.web.server.user.weixin.domain.CustomMenu;

public class CustomMenuDto extends CustomMenu{

	private static final long serialVersionUID = -8820426238276610340L;
	// 自定义菜单JSON
	private String customMenuJson;

	public String getCustomMenuJson() {
		return customMenuJson;
	}

	public void setCustomMenuJson(String customMenuJson) {
		this.customMenuJson = customMenuJson;
	}
}
