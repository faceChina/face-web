package com.zjlp.face.web.server.user.customer.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月10日 下午5:46:44
 *
 */
public class AppGroup implements Serializable {

	private static final long serialVersionUID = 3315515076788245375L;

	/** 主键 **/
	private Long id;

	/** 类型 , 0：客户 , 1:朋友 , 2：标签 **/
	private Integer type;

	/** 用户ID **/
	private Long userId;

	/** 分组、标签名称 **/
	private String name;

	/** 排序权重 **/
	private Integer sort;

	/** 未分组标志位,1：是, 0:不是 **/
	private Integer ungrouped;

	/** 创建时间 **/
	private Date createTime;

	/** 更新时间 **/
	private Date updateTime;

	public AppGroup() {

	}

	public AppGroup(Integer type) {
		this.type = type;
	}

	public AppGroup(Integer type, Integer sort) {
		this.type = type;
		this.sort = sort;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getUngrouped() {
		return ungrouped;
	}

	public void setUngrouped(Integer ungrouped) {
		this.ungrouped = ungrouped;
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
