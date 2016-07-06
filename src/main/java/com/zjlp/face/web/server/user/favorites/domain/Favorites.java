package com.zjlp.face.web.server.user.favorites.domain;

import java.io.Serializable;
import java.util.Date;

public class Favorites implements Serializable {
	
	private static final long serialVersionUID = -6337654720413251894L;
	
	public static final Integer TYPE_GOOD = 1;
	
	public static final Integer TYPE_SHOP = 2;
	
	public static final Integer TYPE_SUBBRANCH = 3;
	
	private Long id;

    private Long userId;
    //收藏类型：1商品 2店铺 3分店
    private Integer remoteType;
    //收藏主键
    private String remoteId;
    //状态：0无效 1有效
    private Integer status;

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
        this.remoteId = remoteId == null ? null : remoteId.trim();
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
    
    public static boolean checkStatus(Integer status) {
    	return (status == 1 || status == 0);
    }
}