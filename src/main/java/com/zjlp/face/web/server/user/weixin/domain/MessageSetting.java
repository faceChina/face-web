package com.zjlp.face.web.server.user.weixin.domain;

import java.io.Serializable;
import java.util.Date;

public class MessageSetting implements Serializable {
    
	private static final long serialVersionUID = -9146595292830263524L;
	//主键
	private Long id;
	//消息主体ID
    private Long messageBodyId;
    //产品编号
    private String shopNo;
    //事件类型：1：关注时回复 2：消息时回复 3：关键词回复
    private Integer eventType;
    //回复模式：1：文本 2：单图文 3：多图文 
    private Integer recoveryMode;
    //关键词
    private String keyWord;
    //匹配类型: 1：模糊比配 2：精确匹配
    private Integer matchingType;
    //状态 -1：删除，0：未激活，1：激活
    private Integer status;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //名称
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMessageBodyId() {
        return messageBodyId;
    }

    public void setMessageBodyId(Long messageBodyId) {
        this.messageBodyId = messageBodyId;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo == null ? null : shopNo.trim();
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public Integer getRecoveryMode() {
        return recoveryMode;
    }

    public void setRecoveryMode(Integer recoveryMode) {
        this.recoveryMode = recoveryMode;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord == null ? null : keyWord.trim();
    }

    public Integer getMatchingType() {
        return matchingType;
    }

    public void setMatchingType(Integer matchingType) {
        this.matchingType = matchingType;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}