package com.zjlp.face.web.server.user.mulchat.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Baojiang Yang
 *
 */
public class MulChatInformation implements Serializable {

	private static final long serialVersionUID = 5666100592938906573L;

	// 主键
	private String groupId;
	// 群名
	private String name;
	// 群头像
	private String pictureUrl;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date updateTime;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
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
