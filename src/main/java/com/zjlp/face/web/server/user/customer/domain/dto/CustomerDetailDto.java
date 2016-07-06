package com.zjlp.face.web.server.user.customer.domain.dto;

import java.io.Serializable;
import java.util.List;

import com.zjlp.face.web.server.user.customer.domain.AppCustomer;
import com.zjlp.face.web.server.user.customer.domain.AppCustomerGroupRelation;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月25日 上午8:35:37
 *
 */
public class CustomerDetailDto extends AppCustomer implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 生日 **/
	private String birthday;

	/** 电话 **/
	private String cell;

	/** 地址 **/
	private String address;

	/** 微信 **/
	private String weChat;

	/** 标签 **/
	private String tag;

	/** 分组 **/
	private List<AppCustomerGroupRelation> appGroupList;

	/** 客户订单 **/
	private List<CustomerOrder> customerOrderList;

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWeChat() {
		return weChat;
	}

	public void setWeChat(String weChat) {
		this.weChat = weChat;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<CustomerOrder> getCustomerOrderList() {
		return customerOrderList;
	}

	public void setCustomerOrderList(List<CustomerOrder> customerOrderList) {
		this.customerOrderList = customerOrderList;
	}

	public List<AppCustomerGroupRelation> getAppGroupList() {
		return appGroupList;
	}

	public void setAppGroupList(List<AppCustomerGroupRelation> appGroupList) {
		this.appGroupList = appGroupList;
	}


}
