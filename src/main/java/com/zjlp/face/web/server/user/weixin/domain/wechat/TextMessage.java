package com.zjlp.face.web.server.user.weixin.domain.wechat;

import java.io.Serializable;

/**
 * 文本消息体类
 * @ClassName: TextMessage 
 * @Description: (文本消息体类) 
 * @author ah
 * @date 2014年8月7日 下午4:37:52
 */
public class TextMessage extends BaseMessage implements Serializable{

	private static final long serialVersionUID = -4997787739540596259L;
	//文本字段
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		this.Content = content;
	}
	
	
	
}
