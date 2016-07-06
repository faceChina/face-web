package com.zjlp.face.web.server.user.shop.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺运费模版
 * @ClassName: DeliveryTemplate 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年3月27日 上午9:48:44
 */
public class DeliveryTemplate implements Serializable {
	
	private static final long serialVersionUID = -263293757662107177L;
	//运费模版ID
	private Long id;
	//店铺号
    private String shopNo;
    //运费模版名称
    private String name;
    //运费承担者（1 买家承担 2 卖家承担（预留））
    private Integer assumer;
    //估价规则(1.按件数计算运费 2 按重量计算（预留）3 按体积计算（预留）)
    private Integer valuation;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    
    public DeliveryTemplate(){}
    public DeliveryTemplate(Long id, String shopNo) {
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

    public Integer getAssumer() {
        return assumer;
    }

    public void setAssumer(Integer assumer) {
        this.assumer = assumer;
    }

    public Integer getValuation() {
        return valuation;
    }

    public void setValuation(Integer valuation) {
        this.valuation = valuation;
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