package com.zjlp.face.web.server.user.weixin.domain;

import java.io.Serializable;
import java.util.Date;

public class ToolSetting implements Serializable{
    
	private static final long serialVersionUID = -2461620078242679162L;
	//主键
	private Long id;
	//产品编号
    private String shopNo;
    //关注时回复开关
    private Integer attentionSwitch;
    //消息时回复开关
    private Integer replySwitch;
    //关键词回复开关
    private Integer keywordRecoverySwitch;
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

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo == null ? null : shopNo.trim();
    }

    public Integer getAttentionSwitch() {
        return attentionSwitch;
    }

    public void setAttentionSwitch(Integer attentionSwitch) {
        this.attentionSwitch = attentionSwitch;
    }

    public Integer getReplySwitch() {
        return replySwitch;
    }

    public void setReplySwitch(Integer replySwitch) {
        this.replySwitch = replySwitch;
    }

    public Integer getKeywordRecoverySwitch() {
        return keywordRecoverySwitch;
    }

    public void setKeywordRecoverySwitch(Integer keywordRecoverySwitch) {
        this.keywordRecoverySwitch = keywordRecoverySwitch;
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
    
    public static ToolSetting initToolSetting(String shopNo, Date time) {
    	ToolSetting toolSetting = new ToolSetting();
    	int autoswitch = 1;
    	toolSetting.setShopNo(shopNo);
		toolSetting.setAttentionSwitch(autoswitch);
		toolSetting.setReplySwitch(autoswitch);
		toolSetting.setKeywordRecoverySwitch(autoswitch);
		toolSetting.setCreateTime(time);
		toolSetting.setUpdateTime(time);
    	return toolSetting;
    }
}