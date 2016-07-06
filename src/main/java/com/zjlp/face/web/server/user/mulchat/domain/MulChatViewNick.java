package com.zjlp.face.web.server.user.mulchat.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Baojiang Yang
 *
 */
public class MulChatViewNick implements Serializable {

	private static final long serialVersionUID = -8116464165664945770L;

	// 主键
	private String groupId;
	// 登录名
	private String loginAccount;
	// 显示昵称
	private String viewNick;
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

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getViewNick() {
		return viewNick;
	}

	public void setViewNick(String viewNick) {
		this.viewNick = viewNick;
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
