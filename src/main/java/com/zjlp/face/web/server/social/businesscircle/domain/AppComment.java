package com.zjlp.face.web.server.social.businesscircle.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 生意圈评论类
* @ClassName: Comment
* @Description: TODO(这里用一句话描述这个类的作用)
* @author wxn
* @date 2015年5月7日 下午1:56:13
*
 */
public class AppComment implements Serializable {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 8275586334031729835L;

	/**评论ID**/
	private Long id;
	/**生意圈ID**/
	private Long circleMsgId;
	/**发布评论人**/
	private Long senderId;
	/**评论接受人**/
	private Long  receiveId;
	private Integer flag;
	/**评论内容**/
	private String content;
	/**评论时间**/
	private Date createTime;
	/** sender发布评论人头像 **/
	private String headimgurl;
	/** receive发布评论人头像 **/
	private String headimgurlReceive;
	/**发布评论人昵称**/
	private String senderName;
	/**发布评论人昵称**/
	private String receiveName;
	/** 发布评论人账号 **/
	private String senderUserName;
	/** 接收评论人账号 **/
	private String receiveUserName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCircleMsgId() {
		return circleMsgId;
	}
	public void setCircleMsgId(Long circleMsgId) {
		this.circleMsgId = circleMsgId;
	}
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	public Long getReceiveId() {
		return receiveId;
	}
	public void setReceiveId(Long receiveId) {
		this.receiveId = receiveId;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getHeadimgurlReceive() {
		return headimgurlReceive;
	}

	public void setHeadimgurlReceive(String headimgurlReceive) {
		this.headimgurlReceive = headimgurlReceive;
	}

	public String getSenderUserName() {
		return senderUserName;
	}

	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}

	public String getReceiveUserName() {
		return receiveUserName;
	}

	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}
	@Override
	public String toString() {
		return "AppComment [id=" + id + ", circleMsgId=" + circleMsgId
				+ ", senderId=" + senderId + ", receiveId=" + receiveId
				+ ", flag=" + flag + ", content=" + content + ", createTime="
				+ createTime + "]";
	}
}
