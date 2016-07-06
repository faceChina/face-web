package com.zjlp.face.web.appvo;

import java.io.Serializable;

/**
 * 手机联系人
 * 
 * @author Baojiang Yang
 * @date 2015年9月1日 下午8:21:37
 *
 */
public class Contacts implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 主键 **/
	private Integer id;
	/** 用户ID **/
	private Long userId;
	/** 联系电话 **/
	private String phoneNo;
	/** 联系人名 **/
	private String name;
	/** 类型: 0未注册; 1注册 **/
	private Integer type;
	/** 刷脸好 **/
	private String lpNo;
	/** 头像 **/
	private String headimgurl;

	public Contacts() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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

	public String getLpNo() {
		return lpNo;
	}

	public void setLpNo(String lpNo) {
		this.lpNo = lpNo;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

}
