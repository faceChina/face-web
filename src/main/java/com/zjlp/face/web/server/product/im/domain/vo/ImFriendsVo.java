package com.zjlp.face.web.server.product.im.domain.vo;

import java.io.Serializable;

public class ImFriendsVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8819001681869770660L;
	
	//IM好友   登陆名
	public String userName;
	//IM好友   编号
	public String id;
	//好友备注名
	public String friendName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	
}
