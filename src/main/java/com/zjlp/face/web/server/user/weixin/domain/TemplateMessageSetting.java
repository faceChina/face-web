package com.zjlp.face.web.server.user.weixin.domain;

import java.io.Serializable;
import java.util.Date;

public class TemplateMessageSetting implements Serializable{
    
	private static final long serialVersionUID = 377576998702836969L;

	private Long id;

    private String shopNo;

    private Integer memberCardSwitch;

    private Integer orderStatusSwitch;

    private Integer commissionSwitch;

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

    public Integer getMemberCardSwitch() {
        return memberCardSwitch;
    }

    public void setMemberCardSwitch(Integer memberCardSwitch) {
        this.memberCardSwitch = memberCardSwitch;
    }

    public Integer getOrderStatusSwitch() {
        return orderStatusSwitch;
    }

    public void setOrderStatusSwitch(Integer orderStatusSwitch) {
        this.orderStatusSwitch = orderStatusSwitch;
    }

    public Integer getCommissionSwitch() {
        return commissionSwitch;
    }

    public void setCommissionSwitch(Integer commissionSwitch) {
        this.commissionSwitch = commissionSwitch;
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

	public static TemplateMessageSetting init(String no, Date date) {
		TemplateMessageSetting templateMessageSetting = new TemplateMessageSetting();
		templateMessageSetting.setShopNo(no);
		templateMessageSetting.setMemberCardSwitch(0);
		templateMessageSetting.setOrderStatusSwitch(0);
		templateMessageSetting.setCommissionSwitch(0);
		templateMessageSetting.setCreateTime(date);
		templateMessageSetting.setUpdateTime(date);
		return templateMessageSetting;
	}
}