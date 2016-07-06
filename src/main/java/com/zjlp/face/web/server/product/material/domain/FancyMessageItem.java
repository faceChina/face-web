package com.zjlp.face.web.server.product.material.domain;

import java.io.Serializable;
import java.util.Date;

public class FancyMessageItem  implements Serializable{
	
    /**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 8091396782363451909L;

	private Long id;

    private Long fancyMessageId;

    private String name;

    private String picturePath;

    private String author;

    private String brief;

    private Integer type;

    private String link;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private String content;

    /** 浏览量 */
    private Long pageViews;
    
    /** 点赞数 */
    private Long likeNum;

	public Long getPageViews() {
		return pageViews;
	}

	public void setPageViews(Long pageViews) {
		this.pageViews = pageViews;
	}

	public Long getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Long likeNum) {
		this.likeNum = likeNum;
	}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFancyMessageId() {
        return fancyMessageId;
    }

    public void setFancyMessageId(Long fancyMessageId) {
        this.fancyMessageId = fancyMessageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath == null ? null : picturePath.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	@Override
	public String toString() {
		return "FancyMessageItem [id=" + id + ", fancyMessageId="
				+ fancyMessageId + ", name=" + name + ", picturePath="
				+ picturePath + ", author=" + author + ", brief=" + brief
				+ ", type=" + type + ", link=" + link + ", status=" + status
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", content=" + content + "]";
	}
    
}