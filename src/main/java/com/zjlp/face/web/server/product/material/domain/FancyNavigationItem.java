package com.zjlp.face.web.server.product.material.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 刷脸精选导航细项
 * 
 * @author Baojiang Yang
 * @date 2015年8月24日 下午4:12:25
 *
 */
public class FancyNavigationItem implements Serializable{

	private static final long serialVersionUID = -5925570577558805653L;

	/** 主键 **/
	private Long id;

	/** 导航外键ID **/
	private Long fancyNavigationId;

	/** 细项名 **/
	private String name;
	
	/** 协议类型:1,网页;2,电话;3,短信;4,聊天 **/
	private Integer type;

	/**
	 * 跳转协议
	 * 网页lpprotocol:"www.baidu.com" 
	 * 聊天 lpprotocol://chat/[刷脸账号] 
	 * 拨号 lpprotocol://tel/[电话号码] 
	 * 短信lpprotocol://smsto/[电话号码]
	 * **/
	private String link;

	/** 状态:1,有效;-1:删除 **/
	private Integer status;

	/** 创建时间 **/
	private Date createTime;

	/** 更新时间 **/
	private Date updateTime;

	public FancyNavigationItem() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFancyNavigationId() {
		return fancyNavigationId;
	}

	public void setFancyNavigationId(Long fancyNavigationId) {
		this.fancyNavigationId = fancyNavigationId;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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
