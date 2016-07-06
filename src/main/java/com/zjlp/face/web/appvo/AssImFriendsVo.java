package com.zjlp.face.web.appvo;

import java.io.Serializable;

public class AssImFriendsVo implements Serializable{
	
	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 2361578508841172004L;
	private Long userid;
	//好友登陆名 账号
	private String userName;
	// 昵称
	private String nickName ;
	// 头像图片路径
	private String headImgUrl ;
	// 生意圈封面图片
	private String circlePictureUrl;
	//名片页面
	private String cardUrl;
	
	public String getCardUrl() {
		return cardUrl;
	}
	public void setCardUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		if (null == nickName){
			this.nickName = "";
		}else{
			this.nickName = nickName;
		}
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		if (null == headImgUrl){
			this.headImgUrl = "";
		}else{
			this.headImgUrl = headImgUrl;
		}
	}

	public String getCirclePictureUrl() {
		return circlePictureUrl;
	}

	public void setCirclePictureUrl(String circlePictureUrl) {
		this.circlePictureUrl = circlePictureUrl;
	}
	
}
