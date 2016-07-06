package com.zjlp.face.web.server.user.weixin.domain;

import java.io.Serializable;
import java.util.Date;

public class MessageContent implements Serializable {
   
	private static final long serialVersionUID = 7136060470182161704L;
	//主键
	private Long id;
	//消息主体ID
    private Long messageBodyId;
    //图片相对路径
    private String picPath;
    //标题
    private String title;
    //描述
    private String description;
    //排序
    private Integer sort;
    //链接地址
    private String linkPath;
    //链接类型：1:首页 2.链接
    private Integer linkType;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //正文
    private String content;

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

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? null : picPath.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getLinkPath() {
        return linkPath;
    }

    public void setLinkPath(String linkPath) {
        this.linkPath = linkPath == null ? null : linkPath.trim();
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}