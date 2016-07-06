package com.zjlp.face.web.server.product.template.domain;

import java.io.Serializable;
import java.util.Date;

public class CarouselDiagram implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7109125421676314689L;
	//轮播图编号
	private Long id;
	//轮播图所属模版编号
    private Long owTemplateId;
    //轮播图CODE
    private String code;
    //轮播图名称
    private String name;
    //轮播图路径
    private String imgPath;
    //轮播图资源（链接）
    private String resourcePath;
    //排序
    private Integer sort;
    //状态
    private Integer status;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwTemplateId() {
        return owTemplateId;
    }

    public void setOwTemplateId(Long owTemplateId) {
        this.owTemplateId = owTemplateId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath == null ? null : imgPath.trim();
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath == null ? null : resourcePath.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
    
    @Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}