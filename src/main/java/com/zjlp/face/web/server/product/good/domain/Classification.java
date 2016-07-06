package com.zjlp.face.web.server.product.good.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 商品类目表
 * @ClassName: Classification 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月16日 下午1:19:16
 */
public class Classification implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7740092297677482963L;

	private Long id;
	//业务能力ID
	private Long serviceId;
	//类目名称
    private String name;
    //类目等级
    private Integer level;
    //类目上级IDS
    private Long parentId;
    
    private Integer leaf;
	
	/** 1,普通商品;2,协议商品 */
	private Integer category;

    private Date createTime;

    private Date updateTime;
    //状态(-1:删除;0:未发布;1已发布)
    private Integer status;

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getLeaf() {
        return leaf;
    }

    public void setLeaf(Integer leaf) {
        this.leaf = leaf;
    }

	public Integer getCategory(){
		return category;
	}
	
	public void setCategory(Integer category){
		this.category = category;
	}
	
	public Date getCreateTime(){
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

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}