package com.zjlp.face.web.server.operation.appoint.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DynamicForm {
	private Long id;

	private String remoteId;

	private Integer remoteCode;

	private String type;

	private String lable;

	private String placeHolder;

	private Boolean isRequired;

	private Integer sort;
	
	private Short innerSort;
	
	private String memo;

	private Date createTime;

	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRemoteId() {
		return remoteId;
	}

	public void setRemoteId(String remoteId) {
		this.remoteId = remoteId == null ? null : remoteId.trim();
	}

	public Integer getRemoteCode() {
		return remoteCode;
	}

	public Short getInnerSort() {
		return innerSort;
	}

	public void setInnerSort(Short innerSort) {
		this.innerSort = innerSort;
	}

	public void setRemoteCode(Integer remoteCode) {
		this.remoteCode = remoteCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable == null ? null : lable.trim();
	}

	public String getPlaceHolder() {
		return placeHolder;
	}

	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder == null ? null : placeHolder.trim();
	}

	public Boolean getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
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

	public List<String> getSelectOption() {
		if("SELECT".equals(type)){
			if(null != placeHolder){
				return Arrays.asList(placeHolder.split("\\|"));
			}
		}
		return null;
	}
}