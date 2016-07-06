package com.zjlp.face.web.server.product.good.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 商品类目标准属性表
 * @ClassName: Prop 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年3月16日 上午9:48:25
 */
public class Prop implements Serializable,Comparable<Prop> {
	private static final long serialVersionUID = 1L;
	//标准属性名ID
    private Long id;
    //属性父级ID 没有上级取0
    private Long pid;
    //属性名称（用于页面显示）
    private String name;
    //是否颜色属性
    private Boolean isColorProp= false;
    //是否枚举属性
    private Boolean isEnumProp= false;
    //是否输入属性
    private Boolean isInputProp= false;
    //是否关键属性
    private Boolean isKeyProp= false;
    //是否销售属性
    private Boolean isSalesProp= false;
    //是否允许别名
    private Boolean isAllowAlias= false;
    //是否标准属性
    private Boolean isStandard= false;
    //发布商品时是否为必选属性
    private Boolean isRequired= false;
    //发布商品时是否可以多选属性
    private Boolean isMulti= false;
    //状态(-1:删除 ;0:未发布;1:已发布)
    private Integer status;
    //排序,取值范围:大于零的整数
    private Long sort;
    //属性别名，内部显示
    private String alias;
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

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getIsColorProp() {
        return isColorProp;
    }

    public void setIsColorProp(Boolean isColorProp) {
        this.isColorProp = isColorProp;
    }

    public Boolean getIsEnumProp() {
        return isEnumProp;
    }

    public void setIsEnumProp(Boolean isEnumProp) {
        this.isEnumProp = isEnumProp;
    }

    public Boolean getIsInputProp() {
        return isInputProp;
    }

    public void setIsInputProp(Boolean isInputProp) {
        this.isInputProp = isInputProp;
    }

    public Boolean getIsKeyProp() {
        return isKeyProp;
    }

    public void setIsKeyProp(Boolean isKeyProp) {
        this.isKeyProp = isKeyProp;
    }

    public Boolean getIsSalesProp() {
        return isSalesProp;
    }

    public void setIsSalesProp(Boolean isSalesProp) {
        this.isSalesProp = isSalesProp;
    }

    public Boolean getIsAllowAlias() {
        return isAllowAlias;
    }

    public void setIsAllowAlias(Boolean isAllowAlias) {
        this.isAllowAlias = isAllowAlias;
    }

    public Boolean getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(Boolean isStandard) {
        this.isStandard = isStandard;
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    public Boolean getIsMulti() {
        return isMulti;
    }

    public void setIsMulti(Boolean isMulti) {
        this.isMulti = isMulti;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
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

    /**
     * 名称显示时，若别名为空，直接显示名称
     */
	public String getAlias() {
		if (null!=this.alias && !"".equals(this.alias)) {
			return this.alias;
		}else {
			return this.name;
		}
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public int compareTo(Prop o) {
		return 0;
	}
}