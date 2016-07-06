package com.zjlp.face.web.security.bean;

import java.io.Serializable;
import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.Shop;

public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2919914096283693178L;

	private Long userId;
	
	private String loginAccount;
	
	private String passwd;
	
	private String token;
	
	//用户类型，1:普通用户，2：超级用户，3：管理员帐户
	private Integer type;
	
	private String name;
	
	
	private List<String> roleList;
	
	private List<Shop> shopNoList;
	
	public List<String> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public List<Shop> getShopNoList() {
		return shopNoList;
	}
	public void setShopNoList(List<Shop> shopNoList) {
		this.shopNoList = shopNoList;
	}




}
