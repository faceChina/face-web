package com.zjlp.face.web.server.product.good.domain;

import java.util.Date;
/**
 * 标准属性值表
 * @ClassName: PropValue 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月16日 上午9:50:09
 */
public class PropValue {
	//主键
    private Long id;
    //属性名ID
    private Long propertyId;
    //商品类目ID
    private Long sort;
    //属性值名称
    private String name;
    //属性值别名
    private String alias;
    //Hex色值
    private String hex;
    //编码
    private String code;
    //状态( -1 删除 1正常)
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

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex == null ? null : hex.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
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