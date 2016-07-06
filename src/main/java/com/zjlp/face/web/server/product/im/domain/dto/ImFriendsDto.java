package com.zjlp.face.web.server.product.im.domain.dto;

import java.io.Serializable;

import com.zjlp.face.web.server.product.im.domain.ImFriends;

public class ImFriendsDto extends ImFriends implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2326795542195687312L;

	//好友登陆名 账号
	private String friendsUserName;

	public String getFriendsUserName() {
		return friendsUserName;
	}

	public void setFriendsUserName(String friendsUserName) {
		this.friendsUserName = friendsUserName;
	}
	
	
}
