package com.zjlp.face.web.server.operation.marketing.domain;

import java.io.Serializable;
import java.util.Date;
/**
 * 营销工具表
 * @ClassName: MarketingTool 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年4月13日 下午4:49:09
 */
public class MarketingTool implements Serializable {
	private static final long serialVersionUID = 851169431227312911L;
	//营销工具ID
	private Long id;
	//归属类型
    private Integer remoteType;
    //归属ID
    private String remoteId;
    //营销场景: 1.订购  2.支付 3.签到  4.签到
    private Integer marketingType;
    //产品范围: 1.会员卡 2.商品 3. 钱包 4.积分
    private Integer productType;
    //是否开启 1.开启  -1.未开启
    private Integer status;
    //是否标准活动 -1 不是  1 是
    private Integer standardType;
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
	public Integer getRemoteType() {
		return remoteType;
	}
	public void setRemoteType(Integer remoteType) {
		this.remoteType = remoteType;
	}
	public String getRemoteId() {
		return remoteId;
	}
	public void setRemoteId(String remoteId) {
		this.remoteId = remoteId;
	}
	public Integer getMarketingType() {
		return marketingType;
	}
	public void setMarketingType(Integer marketingType) {
		this.marketingType = marketingType;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getStandardType() {
		return standardType;
	}
	public void setStandardType(Integer standardType) {
		this.standardType = standardType;
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
	@Override
	public String toString() {
		return new StringBuilder("MarketingTool [id=").append(id)
				.append(", remoteType=").append(remoteType).append(", remoteId=")
				.append(remoteId).append(", marketingType=")
				.append(marketingType).append(", productType=").append(productType)
				.append(", status=").append(status).append(", standardType=")
				.append(standardType).append(", createTime=").append(createTime)
				.append(", updateTime=").append(updateTime).append("]")
				.toString();
	}
	
}