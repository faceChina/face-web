package com.zjlp.face.web.server.user.weixin.domain.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 * @ClassName: Menu 
 * @Description: (菜单) 
 * @author Administrator
 * @date 2014年10月21日 下午7:07:29
 */
public class Menu implements Serializable {

	private static final long serialVersionUID = -8846443523604025862L;

	private List<Button> button;

	public List<Button> getButton() {
		return button;
	}

	public void setButton(List<Button> button) {
		this.button = button;
	}
}
