package com.zjlp.face.web.server.user.customer.domain;

import java.io.Serializable;
import java.util.Date;

/**
* 
* @author Baojiang Yang
* @date 2015年8月11日 下午5:00:50
*
*/ 
public class AppCustomerGroupRelation implements Serializable {

	private static final long serialVersionUID = -2918924710451965617L;

	/** 主键 **/
	private Long id;

	/** 类型 , 0：客户 , 1:朋友 , 2：标签 **/
	private Integer type;

	/** 朋友ID **/
	private Long friendId;

	/** 客户ID **/
	private Long customerId;

	/** 分组ID **/
	private Long groupId;

	/** 分组名称 **/
	private String groupName;

	/** 创建名称 **/
	private Date createTime;

	/** 更新名称 **/
	private Date updateTime;

	public AppCustomerGroupRelation() {

	}

	public AppCustomerGroupRelation(Integer type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public Long getFriendId() {
		return friendId;
	}

	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
