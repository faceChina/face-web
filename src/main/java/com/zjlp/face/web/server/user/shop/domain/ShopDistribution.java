package com.zjlp.face.web.server.user.shop.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺配送范围表
 * @ClassName: ShopDistribution 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年3月27日 上午9:50:01
 */
public class ShopDistribution implements Serializable {
	
	private static final long serialVersionUID = 2652318752033793837L;
	//主键
	private Long id;
	//店铺号
    private String shopNo;
    //名称
    private String name;
    //配送范围
    private String distributionRange;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

    public ShopDistribution(){}
    public ShopDistribution(Long id, String shopNo) {
    	this.id = id;
    	this.shopNo = shopNo;
    }
    
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

    public String getDistributionRange() {
        return distributionRange;
    }

    public void setDistributionRange(String distributionRange) {
        this.distributionRange = distributionRange == null ? null : distributionRange.trim();
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