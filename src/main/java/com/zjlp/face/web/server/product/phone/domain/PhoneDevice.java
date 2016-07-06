package com.zjlp.face.web.server.product.phone.domain;

import java.io.Serializable;
import java.util.Date;

public class PhoneDevice implements Serializable{
	
	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 4734162329501486323L;
	private Long id;
	// 用户ID
	private Long userId;
	//  pushUserId
	private String pushUserId;
	// 状态 -1 删除 1正常
	private Integer status;
	//设备ID 1 android 2 ios
	private Integer deviceType;
	// 账号类型  1:普通用户，2：超级用户，3：管理员帐户
	private Integer type;
	
	private Date createTime;
	
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
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
	public String getPushUserId() {
		return pushUserId;
	}
	public void setPushUserId(String pushUserId) {
		this.pushUserId = pushUserId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
