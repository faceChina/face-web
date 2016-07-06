package com.zjlp.face.web.server.user.user.domain.vo;

import java.io.Serializable;

import org.springframework.web.context.ContextLoaderListener;

import com.zjlp.face.util.constants.ConstantsMethod;
import com.zjlp.face.web.server.user.user.dao.UserDao;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.RandomUtil;

public class UserVo implements Serializable {

	private static final long serialVersionUID = 5552422394035569949L;

	// 昵称
	private String nickname;
	// 登录名
	private String loginAccount;
	// /性别：值为1时是男性，值为2是女性，值为0是未知
	private String sex;
	// 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
	private String headimgurl;
	// 手机号码
	private String cell;
	// /身份证
	private String identity;
	// 联系人(真实姓名)
	private String contacts;
	// 地址
	private String address;
	// 公司简介
	private String company;
	//邮箱
	private String email;
	
	/** 手机号*/
	private String phone;
	/** 密码*/
	private String pwd;
	/**	验证结果*/
	private Integer flag;
	/**	邀请码*/
	private String invitationCode;
	/**邀请人userId*/
	private Long invitationUserId;
	/** 我的邀请人数 **/
	private Integer countMyInvitationAmount;
	/**	二度邀请人数 **/
	private Integer countBisInvitationAmount;
	
	
	public Integer getCountMyInvitationAmount() {
		return countMyInvitationAmount;
	}
	public void setCountMyInvitationAmount(Integer countMyInvitationAmount) {
		this.countMyInvitationAmount = countMyInvitationAmount;
	}
	
	public Integer getCountBisInvitationAmount() {
		return countBisInvitationAmount;
	}
	public void setCountBisInvitationAmount(Integer countBisInvitationAmount) {
		this.countBisInvitationAmount = countBisInvitationAmount;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getInvitationCode() {
		return invitationCode;
	}
	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}
	public UserVo() {
	}
	public UserVo(String userName, String identity) {
		this.setContacts(userName);
		this.setIdentity(identity);
	}
	public UserVo(User user){
		this.nickname = user.getNickname();
		this.loginAccount = user.getLoginAccount();
		this.setSex(user.getSex());
		this.headimgurl = user.getHeadimgurl();
		this.setCell(user.getCell());
		this.setIdentity(user.getIdentity());
		this.contacts = user.getContacts();
		this.address = user.getAddress();
		this.company = user.getCompany();
		this.email = user.getEmail();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		if (User.SEX_BOY.equals(sex)) {
			this.sex = "男";
		} else if (User.SEX_GIRL.equals(sex)) {
			this.sex = "女";
		} else if (User.SEX_NONE.equals(sex)) {
			this.sex = "未知";
		}
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		if (null == cell) {
			return;
		}
		this.cell = ConstantsMethod.replaceToHide(cell, 3, 3);
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		if (null == identity) {
			return;
		}
		this.identity = ConstantsMethod.replaceToHide(identity, 3, 3);
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public static String generateMyInvitationCode(){
		String myInvitationCode=null;
		Integer count=1;
		while(count!=0){
			myInvitationCode=RandomUtil.randomString(6, "100");
			count=ContextLoaderListener.getCurrentWebApplicationContext().getBean(UserDao.class).getCountByMyInvitationCode(myInvitationCode);
		}
		System.out.println("myInvitationCode:"+myInvitationCode);
		return myInvitationCode;
	}
	public Long getInvitationUserId() {
		return invitationUserId;
	}
	public void setInvitationUserId(Long invitationUserId) {
		this.invitationUserId = invitationUserId;
	}

}
