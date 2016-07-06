package com.zjlp.face.web.server.product.material.domain;

import java.io.Serializable;
import java.util.Date;

public class AricleColumn implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7806233468734667623L;

	private Long id;

    private String shopNo;

    private String name;

    private String picPath;

    private String articleTemplateType;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo == null ? null : shopNo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? null : picPath.trim();
    }

    public String getArticleTemplateType() {
        return articleTemplateType;
    }

    public void setArticleTemplateType(String articleTemplateType) {
        this.articleTemplateType = articleTemplateType == null ? null : articleTemplateType.trim();
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