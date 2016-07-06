package com.zjlp.face.web.server.social.businesscircle.domain;

import java.io.Serializable;

/**
 * 生意圈点赞信息类
* @ClassName: Praise
* @Description: TODO(这里用一句话描述这个类的作用)
* @author wxn
* @date 2015年5月7日 下午1:54:04
*
 */
public class AppPraise implements Serializable {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -1057645746366520642L;
	
	private Long id;
	/**生意圈内容ID**/
	private Long circleMsgId;
	/**点赞人ID**/
	private Long userId;
	/**点赞人姓名**/
	private String userName;
	/**发布评论人头像**/
	private String headimgurl;
	/** 点赞用户账户 **/
	private String userAccount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCircleMsgId() {
		return circleMsgId;
	}
	public void setCircleMsgId(Long circleMsgId) {
		this.circleMsgId = circleMsgId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	@Override
	public String toString() {
		return "AppPraise [id=" + id + ", circleMsgId=" + circleMsgId
				+ ", userId=" + userId + ", userName=" + userName + "]";
	}
}
