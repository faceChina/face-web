package com.zjlp.face.web.server.user.businesscard.domain.dto;

import com.zjlp.face.util.page.Aide;
import com.zjlp.face.web.server.user.businesscard.domain.BusinessCard;

/**
 * 
 * @author Administrator
 *
 */
public class BusinessCardVo extends BusinessCard {

	private static final long serialVersionUID = 6299198106669375670L;
	
	//名片夹ID
	private Long cardId;
	//用户名字拼音
	private String userPingyin;
	//登录账户
	private String loginAccount;
	//头像
	private String headimgurl;
	//昵称
	private String nickName;
	//
	private String condition;
	//辅助分页信息等
	private Aide aide = new Aide();
	public Aide getAide() {
		return aide;
	}
	public void setAide(Aide aide) {
		this.aide = aide;
	}
	
	public BusinessCardVo(){
		super();
	}
	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public String getUserPingyin() {
		return userPingyin;
	}
	public void setUserPingyin(String userPingyin) {
		this.userPingyin = userPingyin;
	}
	
}
