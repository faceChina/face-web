package com.zjlp.face.web.server.user.customer.domain.vo;

import java.io.Serializable;

import com.zjlp.face.web.server.user.customer.domain.AppCustomer;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月14日 上午8:42:02
 *
 */
public class AppCustomerVo extends AppCustomer implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 昵称 **/
	private String nickOrRemark;

	/** 头像图片路径 **/
	private String headImgUrl;


	public String getNickOrRemark() {
		return nickOrRemark;
	}

	public void setNickOrRemark(String nickOrRemark) {
		this.nickOrRemark = nickOrRemark;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

}
