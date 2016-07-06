package com.zjlp.face.web.server.user.weixin.domain.dto;

import com.zjlp.face.web.server.user.weixin.domain.MessageSetting;

public class MessageSettingDto extends MessageSetting {

	private static final long serialVersionUID = 6946100122875515984L;
	//标题
	private String title;
	//消息内容
	private String messageContent;
	//用户id
	private Long userId;

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
