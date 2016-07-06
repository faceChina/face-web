package com.zjlp.face.web.server.user.weixin.domain.vo;

import java.util.List;

import com.zjlp.face.web.server.user.weixin.domain.CustomMenu;

public class CustomMenuVo extends CustomMenu{

	private static final long serialVersionUID = -8428113333766204302L;
    // 二级菜单
	private List<CustomMenuVo> subCustomMenus;
	// 二级菜单JSON
	private String subMenu;
	
	public List<CustomMenuVo> getSubCustomMenus() {
		return subCustomMenus;
	}
	public void setSubCustomMenus(List<CustomMenuVo> subCustomMenus) {
		this.subCustomMenus = subCustomMenus;
	}
	public String getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(String subMenu) {
		this.subMenu = subMenu;
	}
	
	
}