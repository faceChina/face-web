package com.zjlp.face.web.server.user.weixin.domain;

import java.io.Serializable;
import java.util.Date;

public class MessageBody implements Serializable{
    
	private static final long serialVersionUID = -698094272470652589L;
	//主键
	private Long id;
	//产品编号
    private String shopNo;
    //消息类型 1：关注时回复 2：消息时回复 3：关键词回复
    private Integer type;
    //信息类型 1：消息库，2：素材库
    private Integer informationType;
    //状态 -1：删除，0：未激活，1：激活
    private Integer status;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //消息内容体
    private String messageContent;

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

    public Integer getInformationType() {
        return informationType;
    }

    public void setInformationType(Integer informationType) {
        this.informationType = informationType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }
}