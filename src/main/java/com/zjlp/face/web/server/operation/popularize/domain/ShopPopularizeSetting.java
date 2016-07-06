package com.zjlp.face.web.server.operation.popularize.domain;

import java.io.Serializable;
import java.util.Date;

public class ShopPopularizeSetting implements Serializable {
	
	
	private static final long serialVersionUID = 3272180633179950441L;
	
	/**
	 * 开启推广status
	 */
	public static final Integer STATUS_ON = 1;
	/**
	 * 关闭推广status
	 */
	public static final Integer STATUS_OFF = 2;
	
	/**
	 * 店铺推广type
	 */
	public static final Integer TYPE_SHOP = 1;
	/**
	 * 分销推广type
	 */
	public static final Integer TYPE_DISTRIBUTION = 2;
	//主键ID
    private Long id;
    //店铺NO
    private String shopNo;
    //推广类型 1 普通商品推广 2 分销商品推广
    private Integer type;
    //状态：1开启，2关闭
    private Integer status;
    //佣金比率 单位[百分比] 取值范围[1-99]
    private Integer commissionRate;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Integer commissionRate) {
        this.commissionRate = commissionRate;
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