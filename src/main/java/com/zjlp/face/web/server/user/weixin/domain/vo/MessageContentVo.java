package com.zjlp.face.web.server.user.weixin.domain.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.server.user.weixin.domain.MessageContent;

public class MessageContentVo implements Serializable{

	private static final long serialVersionUID = -2264509136926470079L;
	//主键
	private Long messageContentId;
	//图片路径
	private String picPath;
    //标题
    private String title;
    //描述
    private String description;
    //链接地址
    private String linkPath;
    //链接类型：1:首页 2.链接
    private Integer linkType;
    //正文
    private String content;
    //图片显示路径
    private String pic;
	
	public String getPicPath() {
		return picPath;
	}
	
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLinkPath() {
		return linkPath;
	}
	
	public void setLinkPath(String linkPath) {
		this.linkPath = linkPath;
	}
	
	public Integer getLinkType() {
		return linkType;
	}
	
	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getPic() {
		return pic;
	}
	
	public void setPic(String pic) {
		this.pic = pic;
	}

	public Long getMessageContentId() {
		return messageContentId;
	}

	public void setMessageContentId(Long messageContentId) {
		this.messageContentId = messageContentId;
	}

	
	/**
	 * 编辑页面显示数据
	 * @Title: _generateVo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param mContents
	 * @return
	 * @date 2015年3月24日 上午11:37:40  
	 * @author ah
	 */
	public static List<MessageContentVo> _generateVo(List<MessageContent> mContents) {
		
		List<MessageContentVo> mVos = new ArrayList<MessageContentVo>();
		for (int i = 0; i < mContents.size(); i++) {
			MessageContent messageContent  = mContents.get(i);
			MessageContentVo vo = new MessageContentVo();
			StringBuffer pic = new StringBuffer();
			vo.setMessageContentId(messageContent.getId());
			vo.setTitle(messageContent.getTitle());
			vo.setContent(messageContent.getContent());
			vo.setDescription(messageContent.getDescription());
			vo.setLinkPath(messageContent.getLinkPath());
			vo.setLinkType(messageContent.getLinkType());
			vo.setPicPath(messageContent.getPicPath());
			
			if(i==0) {
				pic.append(PropertiesUtil.getContexrtParam("pic_url")).append(ImageConstants.getCloudstZoomPath(messageContent.getPicPath(), Constants.WECHAT_BIG_SIZE));
			} else {
				pic.append(PropertiesUtil.getContexrtParam("pic_url")).append(ImageConstants.getCloudstZoomPath(messageContent.getPicPath(), Constants.WECHAT_SMALL_SIZE));
			}
			vo.setPic(pic.toString());
			mVos.add(vo);
		}
		return mVos;
	}
}
