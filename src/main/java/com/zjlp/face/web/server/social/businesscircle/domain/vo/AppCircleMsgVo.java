package com.zjlp.face.web.server.social.businesscircle.domain.vo;

import java.util.List;

import com.zjlp.face.web.server.social.businesscircle.domain.AppCircleMsg;
import com.zjlp.face.web.server.social.businesscircle.domain.AppComment;
import com.zjlp.face.web.server.social.businesscircle.domain.AppPraise;

public class AppCircleMsgVo extends AppCircleMsg {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 3219023494306128220L;
	
	/**点赞人集合**/
	private List<AppPraise>	praiseList;
	/**评论集合**/
	private List<AppComment> commentList;
	/**发布消息人头像**/
	private String headimgurl;
	/**发布消息人昵称**/
	private String userName;
	/**用户登录账户**/
	private String userAccount;
	// 生意圈封面图片
	private String circlePictureUrl;
	

	public List<AppPraise> getPraiseList() {
		return praiseList;
	}

	public void setPraiseList(List<AppPraise> praiseList) {
		this.praiseList = praiseList;
	}

	public List<AppComment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<AppComment> commentList) {
		this.commentList = commentList;
	}
	

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	@Override
	public String toString() {
		return "AppCircleMsgVo super "+super.toString()+" [ headimgurl="+ headimgurl +",userName= "+userName+", praiseList=" + praiseList + ", commentList="
				+ commentList + "]";
	}

}
