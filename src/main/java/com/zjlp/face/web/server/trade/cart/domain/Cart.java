package com.zjlp.face.web.server.trade.cart.domain;

import java.util.Date;
/**
 * 购物车表
 * @ClassName: Cart 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年5月13日 下午3:06:02
 */
public class Cart {
	//购物车id
    private Long id;
    //用户ID
    private Long userId;
    //商品所属店铺号
    private String shopNo;
    //商品所属店铺名称
    private String shopName;
    //分店ID
    private Long subbranchId;
    //分店店铺名称
    private String subbranchShopName;
    //商品ID
    private Long goodId;
    //商品名称
    private String goodName;
    //skuId
    private Long goodSkuId;
    //SKU属性名称
    private String skuPropertiesName;
    //SKU图
    private String skuPicturePath;
    //单价
    private Long unitPrice;
    //数量
    private Long quantity;
    //版本号
    private String version;
    //推广业务时使用推广者信息
    private String shareId;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo == null ? null : shopNo.trim();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

   

	public Long getSubbranchId() {
		return subbranchId;
	}

	public void setSubbranchId(Long subbranchId) {
		this.subbranchId = subbranchId;
	}

	public String getSubbranchShopName() {
		return subbranchShopName;
	}

	public void setSubbranchShopName(String subbranchShopName) {
		this.subbranchShopName = subbranchShopName;
	}

	public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName == null ? null : goodName.trim();
    }

    public Long getGoodSkuId() {
        return goodSkuId;
    }

    public void setGoodSkuId(Long goodSkuId) {
        this.goodSkuId = goodSkuId;
    }

    public String getSkuPropertiesName() {
        return skuPropertiesName;
    }

    public void setSkuPropertiesName(String skuPropertiesName) {
        this.skuPropertiesName = skuPropertiesName == null ? null : skuPropertiesName.trim();
    }

    public String getSkuPicturePath() {
        return skuPicturePath;
    }

    public void setSkuPicturePath(String skuPicturePath) {
        this.skuPicturePath = skuPicturePath == null ? null : skuPicturePath.trim();
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }
    
    public String getShareId() {
		return shareId;
	}

	public void setShareId(String shareId) {
		this.shareId = shareId;
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