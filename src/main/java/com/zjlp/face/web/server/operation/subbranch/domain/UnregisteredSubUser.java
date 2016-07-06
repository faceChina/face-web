package com.zjlp.face.web.server.operation.subbranch.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年7月15日 下午2:54:07
 *
 */
public class UnregisteredSubUser implements Serializable {

	private static final long serialVersionUID = 6549540040583871275L;

	// 主键
	private Long id;
	// 成为分店手机号/注册手机号
	private String loginAccount;
	// 用户名
	private String name;
	// 状态:1,未注册;0,已经注册;-1,删除
	private Integer status;
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

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
