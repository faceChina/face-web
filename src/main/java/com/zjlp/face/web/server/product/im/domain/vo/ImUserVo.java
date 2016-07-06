package com.zjlp.face.web.server.product.im.domain.vo;

import java.io.Serializable;

import com.zjlp.face.web.server.product.im.domain.ImUser;

public class ImUserVo implements Serializable{
	
	//登录时用户是否需要注册 
	public static final String REGISTER_TRUE = "1";
	
	public static final String REGISTER_FALSE = "0";

	private static final long serialVersionUID = -4151414194326018932L;
	//IM账户编号
	public Long id;
	//IM登录用户名
	public String userName;
	//IM登录密码
	public String password;
	//IM是否注册
	public String register;
	//IM发起聊天对象用户名
	public String sendTo;

	public void setImUser(ImUser imUser){
		if (null != imUser.getId()) {
			this.id = imUser.getId();
		}
		this.userName=imUser.getUserName();
		this.password=imUser.getUserPwd();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}
	
	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
