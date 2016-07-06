package com.zjlp.face.web.server.operation.subbranch.domain.vo;

import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年6月19日 下午4:11:53
 *
 */
public class SubbranchVo extends Subbranch {

	private static final long serialVersionUID = 1L;
	// 排序
	private String orderBy;
	// 分店账号
	private String subAccount;
	// 联系方式
	private String phoneNo;
	// 查找关键字
	private String keyword;
	// 分店登录号，用于聊天
	private String subPhone;
	//总店名称
	private String supplierShopName;
	//已设置佣金比例的商品数量
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getSupplierShopName() {
		return supplierShopName;
	}

	public void setSupplierShopName(String supplierShopName) {
		this.supplierShopName = supplierShopName;
	}

	/**
	 * 
	 * @Title: getSubbranchName 
	 * @Description: 分店名称（总店-分店）
	 * @return
	 * @date 2015年8月14日 上午10:59:13  
	 * @author cbc
	 */
	public String getSubbranchName() {
		return supplierShopName + "-" + super.getShopName();
	}

	/**
	 * 
	 * @Title: getDelieveryStr 
	 * @Description: 页面上获取是否有发货权限
	 * @return
	 * @date 2015年8月14日 上午11:08:02  
	 * @author cbc
	 */
	public String getDelieveryStr() {
		if (super.getDeliver() == 0) {
			return "无";
		}
		if (super.getDeliver() == 1) {
			return "有";
		}
		return null;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSubAccount() {
		return subAccount;
	}

	public void setSubAccount(String subAccount) {
		this.subAccount = subAccount;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSubPhone() {
		return subPhone;
	}

	public void setSubPhone(String subPhone) {
		this.subPhone = subPhone;
	}

}
