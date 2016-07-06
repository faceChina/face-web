package com.zjlp.face.web.server.user.weixin.domain.wechat;

import java.io.Serializable;

/**
 * 图文
 * @ClassName: Article 
 * @Description: (图文) 
 * @author ah
 * @date 2014年8月7日 下午4:34:36
 */
public class Article implements Serializable{
  
	private static final long serialVersionUID = 7713181428974513587L;
	//图文消息名称
	private String Title;
	//图文消息描述
	private String Description;
	//图片URL
	private String PicUrl;
	//链接地址URL
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}
	
	public void setUrl(String uRL) {
		Url = uRL;
	}
	
	
	
	
}
