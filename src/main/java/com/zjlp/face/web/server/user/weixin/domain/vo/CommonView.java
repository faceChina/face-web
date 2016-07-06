package com.zjlp.face.web.server.user.weixin.domain.vo;

public class CommonView extends Button {

	private static final long serialVersionUID = 2006695037685185839L;

	// 类型
	private String type;  
	// 键
    private String url;
    
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
