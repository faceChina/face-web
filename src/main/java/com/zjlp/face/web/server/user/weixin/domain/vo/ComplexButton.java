package com.zjlp.face.web.server.user.weixin.domain.vo;

import java.util.List;

/**
 * 复杂按钮
 * @ClassName: ComplexButton 
 * @Description: (复杂按钮) 
 * @author ah
 * @date 2014年10月21日 下午7:03:50
 */
public class ComplexButton extends Button {

	private static final long serialVersionUID = -7382655803695580458L;
	//子按钮
	private List<Button> sub_button;

	public List<Button> getSub_button() {
		return sub_button;
	}

	public void setSub_button(List<Button> sub_button) {
		this.sub_button = sub_button;
	}

}
