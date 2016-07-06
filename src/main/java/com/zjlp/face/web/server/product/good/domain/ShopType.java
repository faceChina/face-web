package com.zjlp.face.web.server.product.good.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 店铺商品分类
 * @ClassName: ShopType 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月23日 下午7:49:34
 */
public class ShopType implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1018902478285313327L;
	//主键
	private Long id;
	//业务能力ID
	private Long serviceId;
	//店铺号
    private String shopNo;
    //分类名称
    private String name;
    //分类描述
    private String typeDescribe;
    //背景颜色
    private String backgroundColor;
    //文字颜色
    private String fontColor;
    //图片路径
    private String imgPath;
    //状态
    private Integer status;
    //排序
    private Long sort;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

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

    public String getTypeDescribe() {
        return typeDescribe;
    }

    public void setTypeDescribe(String typeDescribe) {
        this.typeDescribe = typeDescribe == null ? null : typeDescribe.trim();
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor == null ? null : backgroundColor.trim();
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor == null ? null : fontColor.trim();
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath == null ? null : imgPath.trim();
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
}