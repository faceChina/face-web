package com.zjlp.face.web.appvo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zjlp.face.web.server.social.businesscircle.domain.AppComment;
import com.zjlp.face.web.server.social.businesscircle.domain.AppPraise;
import com.zjlp.face.web.server.social.businesscircle.domain.vo.AppCircleMsgVo;

public class AssCircleMsgVo implements Serializable{

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1L;
	
	private Long id;
	/**用户ID**/
	private Long userId;
	/**发布的文本内容**/
	private String content;
	/**分享的url链接**/
	private String url;
	/**图片路径**/
	private String img;
	/**创建时间**/
	private Long createTimeStamp;
	
	/**点赞人集合**/
	private List<AppPraise>	praiseList;
	/**评论集合**/
	private List<AppComment> commentList;
	/**发布消息人头像**/
	private String headimgurl;
	/**发布消息人昵称**/
	private String userName;
	
	/**用户登录账号**/
	private String userAccount;
	// 生意圈封面图片
	private String circlePictureUrl;
	
	public AssCircleMsgVo() {
		super();
	}
	public AssCircleMsgVo(AppCircleMsgVo vo ) {
		super();
		this.id = vo.getId();
		this.userId = vo.getUserId();
		this.circlePictureUrl = vo.getCirclePictureUrl();
		this.setContent(vo.getContent());
		this.setUrl(vo.getUrl());
		this.setImg(vo.getImg1());
		this.setCreateTimeStamp(vo.getCreateTime().getTime());
		this.setCommentList(vo.getCommentList());
		this.setPraiseList(vo.getPraiseList());
		this.setUserName(vo.getUserName());
		this.setHeadimgurl(vo.getHeadimgurl());
		this.setUserAccount(vo.getUserAccount());
	}
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		if (null == content){
			this.content = "";
		}else{
			this.content = content;
		}
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		if (null == url){
			this.url = "";
		}else{
			this.url = url;
		}
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		if (null == img){
			this.img = "";
		}else{
			this.img = img;
		}
	}
	public Long getCreateTimeStamp() {
		return createTimeStamp;
	}
	public void setCreateTimeStamp(Long createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}
	public List<AppPraise> getPraiseList() {
		return praiseList;
	}
	public void setPraiseList(List<AppPraise> praiseList) {
		if (null == praiseList) {
			this.praiseList = new ArrayList<AppPraise>();
		}else{
			this.praiseList = praiseList;
		}
		
	}
	public List<AppComment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<AppComment> commentList) {
		if (null == commentList) {
			this.commentList = new ArrayList<AppComment>();
		}else{
			this.commentList = commentList;
		}
		
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		if (null == headimgurl){
			this.headimgurl = "";
		}else{
			this.headimgurl = headimgurl;
		}
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		if (null == userName){
			this.userName = "";
		}else{
			this.userName = userName;
		}
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getCirclePictureUrl() {
		return circlePictureUrl;
	}

	public void setCirclePictureUrl(String circlePictureUrl) {
		this.circlePictureUrl = circlePictureUrl;
	}

}
