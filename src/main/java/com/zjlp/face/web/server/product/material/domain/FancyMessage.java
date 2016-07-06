package com.zjlp.face.web.server.product.material.domain;

import java.io.Serializable;
import java.util.Date;

public class FancyMessage implements Serializable{
	
    /**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1L;

	private Long id;

    private Integer type;

    private String name;

    private Integer status;

    private Date publishTime;

    private Date createTime;

    private Date updateTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
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

	@Override
	public String toString() {
		return "FancyMessage [id=" + id + ", type=" + type + ", name=" + name
				+ ", status=" + status + ", publishTime=" + publishTime
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}
}