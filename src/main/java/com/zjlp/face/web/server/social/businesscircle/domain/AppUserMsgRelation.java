package com.zjlp.face.web.server.social.businesscircle.domain;

import java.io.Serializable;
import java.util.Date;

public class AppUserMsgRelation implements Serializable{

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -2109810927628644577L;
	
	private Long id;
	/**用户ID**/
	private Long userId;
	/**生意圈内容ID**/
	private Long circleMsgId;
	/**创建时间**/
	private Date createTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getCircleMsgId() {
		return circleMsgId;
	}
	public void setCircleMsgId(Long circleMsgId) {
		this.circleMsgId = circleMsgId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "AppUserMsgRelation [id=" + id + ", userId=" + userId
				+ ", circleMsgId=" + circleMsgId + ", createTime=" + createTime + "]";
	}
}
