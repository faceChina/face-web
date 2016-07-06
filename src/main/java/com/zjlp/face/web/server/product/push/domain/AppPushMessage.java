package com.zjlp.face.web.server.product.push.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ybj
 *
 */
public class AppPushMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键
	private Long id;
	// 用户ID
	private Long userId;
	// 数据状态：-1删除 1正常
	private Integer dataStatus;
	// 推送内容
	private String content;
	// 是否被读取:0已经读取,1未被读取
	private Integer isRead;
	// 息类型 终端使用,0交易消息, 1商品消息, 2系统消息 3 预定消息 4 预约消息
	private Integer msgType;
	// 设备类型1Andoid,2,IOS,3其他
	private Integer deviceType;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date updateTime;

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

	public Integer getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus == null ? 1 : dataStatus;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
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
