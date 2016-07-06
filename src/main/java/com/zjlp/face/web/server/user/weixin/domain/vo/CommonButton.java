package com.zjlp.face.web.server.user.weixin.domain.vo;

/**
 * 普通按钮
 * @ClassName: CommonButton 
 * @Description: (普通按钮) 
 * @author ah
 * @date 2014年10月21日 下午6:59:21
 */
public class CommonButton extends Button {

	private static final long serialVersionUID = 5148270731723579501L;
	// 类型
	private String type;  
	// 键
    private String key;
    
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
