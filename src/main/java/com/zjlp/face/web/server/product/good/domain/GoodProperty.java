package com.zjlp.face.web.server.product.good.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 商品基本属性对象
 * @ClassName: GoodProperty 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月16日 上午9:47:41
 */
public class GoodProperty implements Serializable {
	
	private static final long serialVersionUID = 5669042874837434260L;
	
	
	/**
	 * 使用商品属性ID模糊查询SKU时使用
	 * @Title: getProerptyIdStr 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodPropertyId
	 * @return
	 * @date 2015年4月3日 上午2:09:10  
	 * @author Administrator
	 */
	public static String getProerptyIdStr(Long goodPropertyId){
		if (null == goodPropertyId) {
			return null;
		}
		return new StringBuilder(";").append(goodPropertyId).append(";").toString();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null){
            return false;
        }
        if(this == obj){
            return true;
        }
        GoodProperty gp =(GoodProperty) obj;
        if (goodId.equals(gp.getGoodId()) && propId.equals(gp.getPropId())
        	&& propName.equals(gp.getPropName()) && propValueId.equals(gp.getPropValueId())
        	&& propValueName.equals(gp.getPropValueName()) && propValueAlias.equals(gp.getPropValueAlias())
        	&& isColorProp.equals(gp.getIsColorProp()) && isEnumProp.equals(gp.getIsEnumProp())
        	&& isInputProp.equals(gp.getIsInputProp()) && isKeyProp.equals(gp.getIsKeyProp())
        	&& isStandard.equals(gp.getIsStandard()) && isSalesProp.equals(gp.getIsSalesProp())
        	) {
			return true;
		}
		return super.equals(obj);
	}
	/** 获得颜色属性 */
	public static GoodProperty getColorProp(){
		GoodProperty goodProperty = new GoodProperty();
		goodProperty.setIsColorProp(true);
		goodProperty.setIsStandard(true);
		goodProperty.setIsSalesProp(true);
		return goodProperty;
	}
	/** 获得枚举属性 */
	public static GoodProperty getEnumProp(){
		GoodProperty goodProperty = new GoodProperty();
		goodProperty.setIsEnumProp(true);
		goodProperty.setIsStandard(true);
		goodProperty.setIsSalesProp(true);
		return goodProperty;
	}
	/** 获得销售属性 */
	public static GoodProperty getSalesProp(){
		GoodProperty goodProperty = new GoodProperty();
		goodProperty.setIsSalesProp(true);
		return goodProperty;
	}
	
	/** 获得输入销售属性 */
	public static GoodProperty getSalesInputProp(){
		GoodProperty goodProperty = new GoodProperty();
		goodProperty.setIsSalesProp(true);
		goodProperty.setIsInputProp(true);
		return goodProperty;
	}
	
	
	/** 获得输入的非销售属性 */
	public static GoodProperty getInputNoKeyProp(){
		GoodProperty goodProperty = new GoodProperty();
		goodProperty.setIsColorProp(false);
		goodProperty.setIsEnumProp(false);
		goodProperty.setIsKeyProp(false);
		goodProperty.setIsInputProp(true);
		goodProperty.setIsStandard(false);
		goodProperty.setIsSalesProp(false);
		return goodProperty;
	}
	
	
	/** 获得输入的非销售属性 */
	public static GoodProperty setIsNull(GoodProperty goodProperty){
		if (null == goodProperty) {
			return null;
		}
		goodProperty.setIsColorProp(null);
		goodProperty.setIsEnumProp(null);
		goodProperty.setIsKeyProp(null);
		goodProperty.setIsInputProp(null);
		goodProperty.setIsStandard(null);
		goodProperty.setIsSalesProp(null);
		return goodProperty;
	}

	private Long id;
    //所属商品 
    private Long goodId;
    //单条属性全称（如：颜色：红色 若有别名，则显示 颜色：大红）
    private String name;
    //是否颜色属性 颜色属性下选择
    private Boolean isColorProp = false;
    //是否枚举属性 枚举类型如尺寸属性可使用
    private Boolean isEnumProp = false;
    //是否输入属性 用户可自由输入的属性
    private Boolean isInputProp = false;
    //是否关键属性
    private Boolean isKeyProp = false;
    //是否标准属性
    private Boolean isStandard = false;
    //是否销售属性 组成SKU的必要元素
    private Boolean isSalesProp = false;
    //属性名ID PROP表ID
    private Long propId;
    //属性名称 (如: 颜色）
    private String propName;
    //属性值ID
    private Long propValueId;
    //属性值名称（如：红色）
    private String propValueName;
    //属性值code 如 RED
    private String propValueCode;
    //属性值别名（如：大红）
    private String propValueAlias;
    //属性图片
    private String picturePath;
    //状态( -1：删除，1：正常 2.冻结 3 下架 )
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

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public String getName() {
        return name;
    }
    
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

    public Boolean getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(Boolean isStandard) {
        this.isStandard = isStandard;
    }

    public Boolean getIsSalesProp() {
        return isSalesProp;
    }

    public void setIsSalesProp(Boolean isSalesProp) {
        this.isSalesProp = isSalesProp;
    }

    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName == null ? null : propName.trim();
    }

    public Long getPropValueId() {
        return propValueId;
    }

    public void setPropValueId(Long propValueId) {
        this.propValueId = propValueId;
    }

    public String getPropValueName() {
        return (null!=propValueAlias&&!"".equals(propValueAlias))?propValueAlias:propValueName;
    }

    public void setPropValueName(String propValueName) {
        this.propValueName = propValueName == null ? null : propValueName.trim();
    }

    public String getPropValueCode() {
        return propValueCode;
    }

    public void setPropValueCode(String propValueCode) {
        this.propValueCode = propValueCode == null ? null : propValueCode.trim();
    }

    public String getPropValueAlias() {
        return propValueAlias;
    }

    public void setPropValueAlias(String propValueAlias) {
        this.propValueAlias = propValueAlias == null ? null : propValueAlias.trim();
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
    
	public String getPicturePath() {
		return picturePath;
	}
	
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
    
}