package com.zjlp.face.web.server.user.mulchat.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Baojiang Yang
 *
 */
/**
 * @author user
 *
 */
public class MulChatQRCode implements Serializable {

	private static final long serialVersionUID = 1L;

	// 主键
	private Long id;
	// 群ID
	private String groupId;
	// 二维码
	private String qRCode;
	// 二维码url
	private String qRCodeUrl;
	// 状态（0：失效 1：有效）
	private Integer status;
	// 创建者
	private String createUser;
	// 生成时间
	private Date createTime;

	// 返回错误码
	private Integer errorCode;
	// 过期时间
	private Date overdueDate;

	public MulChatQRCode() {
	}

	public MulChatQRCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getqRCode() {
		return qRCode;
	}

	public void setqRCode(String qRCode) {
		this.qRCode = qRCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public Date getOverdueDate() {
		return overdueDate;
	}

	public void setOverdueDate(Date overdueDate) {
		this.overdueDate = overdueDate;
	}

	public String getqRCodeUrl() {
		return qRCodeUrl;
	}

	public void setqRCodeUrl(String qRCodeUrl) {
		this.qRCodeUrl = qRCodeUrl;
	}

}
