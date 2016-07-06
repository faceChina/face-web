package com.zjlp.face.web.server.product.im.domain;

import java.io.Serializable;
import java.util.Date;

public class ImFriends implements Serializable {
	
	private static final long serialVersionUID = 1173411541923331633L;

	private Long id;

    private Long imUserId;

    private Long friendId;

    private String friendName;

    private Long imGroupId;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImUserId() {
        return imUserId;
    }

    public void setImUserId(Long imUserId) {
        this.imUserId = imUserId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName == null ? null : friendName.trim();
    }

    public Long getImGroupId() {
        return imGroupId;
    }

    public void setImGroupId(Long imGroupId) {
        this.imGroupId = imGroupId;
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