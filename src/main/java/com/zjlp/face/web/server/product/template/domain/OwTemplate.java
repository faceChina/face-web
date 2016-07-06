package com.zjlp.face.web.server.product.template.domain;

import java.io.Serializable;
import java.util.Date;

public class OwTemplate implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6450732408686972122L;
	//编号
	private Long id;
	//店铺编号
    private String shopNo;
    //模版CODE
    private String code;
    //模版名称
    private String name;
    //模版图片路径
    private String path;
    //模版状态 -1：删除，0：停用，1：激活
    private Integer status;
    //模版类型1：官网模板，2：商城模板，3：脸谱模板，4：商城分类页模板，5：脸谱分类页模板  6：文章分类列表模版 7：相册模版
    private Integer type;
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

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo == null ? null : shopNo.trim();
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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