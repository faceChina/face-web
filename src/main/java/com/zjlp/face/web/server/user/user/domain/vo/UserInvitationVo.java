package com.zjlp.face.web.server.user.user.domain.vo;

import java.util.List;

import com.zjlp.face.web.server.user.user.domain.User;

/**
 * 用户邀请加入的情况
 * @author Baojiang Yang
 * @date 2015年9月28日 下午12:05:22
 *
 */
public class UserInvitationVo extends User {

	private static final long serialVersionUID = 6838678388303745250L;

	/** 邀请层级: 0本级，1一级，2二级，3三级 **/
	private Integer level;

	/** 用户的邀请者 **/
	private UserInvitationVo preUser;

	/** 用户的被邀请者 **/
	private List<UserInvitationVo> nextUsers;

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public UserInvitationVo getPreUser() {
		return preUser;
	}

	public void setPreUser(UserInvitationVo preUser) {
		this.preUser = preUser;
	}

	public List<UserInvitationVo> getNextUsers() {
		return nextUsers;
	}

	public void setNextUsers(List<UserInvitationVo> nextUsers) {
		this.nextUsers = nextUsers;
	}

}
