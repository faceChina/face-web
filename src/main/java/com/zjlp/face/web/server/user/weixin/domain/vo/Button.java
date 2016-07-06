package com.zjlp.face.web.server.user.weixin.domain.vo;

import java.io.Serializable;

/**
 * 按钮基类
 * @ClassName: Button 
 * @Description: (按钮基类) 
 * @author ah
 * @date 2014年10月21日 下午6:56:34
 */
public class Button implements Serializable {

	private static final long serialVersionUID = -61277843577437100L;
	//按钮名称
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
