package com.zjlp.face.web.server.product.im.domain;

import java.io.Serializable;
import java.util.Date;

public class ImGroup implements Serializable{
	
	private static final long serialVersionUID = 775796721688665295L;

	private Long id;

    private Long imUserId;

    private String groupName;

    private Integer type;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImUserId() {
        return imUserId;
    }

    public void setImUserId(Long imUserId) {
        this.imUserId = imUserId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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