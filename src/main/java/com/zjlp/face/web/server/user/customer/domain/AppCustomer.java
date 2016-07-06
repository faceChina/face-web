package com.zjlp.face.web.server.user.customer.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月11日 上午9:28:15
 *
 */
public class AppCustomer implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 客户类型 */
	public static final Integer TYPE_CUSTOMER = 0;
	/** 朋友类型 */
	public static final Integer TYPE_FRIEND = 1;

	/** 主键 **/
	private Long id;

	/** 类型 , 0：客户 , 1:朋友 , 2：标签 **/
	private Integer type;

	/** 用户ID **/
	private Long userId;

	/** 客户ID **/
	private Long customerId;

	/** 客户名称 **/
	private String customerName;

	/** 客户名称 **/
	private String loginAccount;

	/** 备注名：客户列表显示 **/
	private String remarkName;

	/** 备注 : 对客户的购物喜好备注 **/
	private String remark;

	/** 创建时间 **/
	private Date createTime;

	/** 新建时间 **/
	private Date updateTime;

	/** 客户头像 **/
	private String picture;

	public AppCustomer() {

	}

	public AppCustomer(Integer type) {
		this.type = type;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName != null ? customerName : StringUtils.EMPTY;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRemarkName() {
		return remarkName;
	}

	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

}
