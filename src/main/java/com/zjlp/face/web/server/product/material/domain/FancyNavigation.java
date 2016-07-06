package com.zjlp.face.web.server.product.material.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 刷脸精选导航
 * 
 * @author Baojiang Yang
 * @date 2015年8月24日 下午4:11:48
 *
 */
public class FancyNavigation implements Serializable{

	private static final long serialVersionUID = -1312493831370079115L;

	/** 主键 **/
	private Long id;

	/** 导航名 **/
	private String name;

	/** 类型:0：无item,直接跳转;1有item,根据item细项跳转 **/
	private Integer type;

	/**
	 * 跳转协议 网页lpprotocol:"www.baidu.com" 聊天 lpprotocol://chat/[刷脸账号] 拨号
	 * lpprotocol://tel/[电话号码] 短信lpprotocol://smsto/[电话号码]
	 * **/
	private String link;

	/** 状态:1,有效;-1:删除 **/
	private Integer status;

	/** 创建时间 **/
	private Date createTime;

	/** 更新时间 **/
	private Date updateTime;

	public FancyNavigation() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
