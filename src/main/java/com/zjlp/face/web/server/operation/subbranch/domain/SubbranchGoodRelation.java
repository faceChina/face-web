package com.zjlp.face.web.server.operation.subbranch.domain;

import java.io.Serializable;
import java.util.Date;

public class SubbranchGoodRelation implements Serializable {

	private static final long serialVersionUID = 9093592222321681316L;
	
    private Long id;

    private Long userId;

    private Long subbranchId;

    private Long goodId;

    private Integer rate;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSubbranchId() {
        return subbranchId;
    }

    public void setSubbranchId(Long subbranchId) {
        this.subbranchId = subbranchId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
	@Override
	public String toString() {
		return "SubbranchGoodRelation [id=" + id + ", userId=" + userId
				+ ", subbranchId=" + subbranchId + ", goodId=" + goodId
				+ ", rate=" + rate + ", createTime=" + createTime + "]";
	}
}