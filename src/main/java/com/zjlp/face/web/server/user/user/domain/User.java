package com.zjlp.face.web.server.user.user.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	
	/** 性别：男 */
	public static final Integer SEX_BOY = 1;
	/** 性别：女 */
	public static final Integer SEX_GIRL = 2;
	/** 性别：未知  */
	public static final Integer SEX_NONE = 0;
	
	/** 0、正常流程注册(默认)   */
    public static final Integer REGISTERTYPE_DEFAULT = 0;
    /** 1、微信免登陆注册 */
    public static final Integer REGISTERTYPE_WECHAT = 1;
    
    /**
	 * 已修改过刷脸号/我的邀请码
	 */
	public final static Integer IS_UPDATE = 1;
	
	/**
	 * 未修改过刷脸号/我的邀请码
	 */
	public final static Integer IS_NOT_UPDATE = 0;
	
	private static final long serialVersionUID = -3216535731295806612L;
	// 主键
	private Long id;
	//刷脸号
	private String lpNo;
	// 来源：0、正常流程注册(默认)  1、来源于微信
	private Integer source;
	// 我的邀请码，必填，系统自动生成
	private String myInvitationCode;
	//邀请人userId
	private Long invitationUserId;
	// 昵称
	private String nickname;
	// 微信用户标识，对当前公众号唯一
	private String openId;
	// 用户唯一标识
	private String fakeId;
	// 站内唯一识别ID
	private String token;
	// 登录名
	private String loginAccount;
	// /密码
	private String passwd;
	// 账户类型
	private Integer type;
	// /性别：值为1时是男性，值为2是女性，值为0是未知
	private Integer sex;
	// 邀请码
	private String invitationCode;
	// 用户语言
	private String language;
	// 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
	private String headimgurl;
	// 用户关注时间
	private Date subscribeTime;
	// 手机号码
	private String cell;
	// /身份证
	private String identity;
	// 联系人(真实姓名)
	private String contacts;
	// 地区编码
	private Integer areaCode;
	// 地址
	private String address;
	// 状态 -1：删除，0：冻结，1：正常
	private Integer status;
	// 电子邮箱
	private String email;
	// 安全级别（1低 2中 3高）
	private Integer lev;
	// 0 匿名用户 1 实名用户
	private Integer realType;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date updateTime;
	// 公司简介
	private String company;
    //微信账户
    private String wechat;
	//个性签名
	private String signature;
	//生意圈封面图片
	private String circlePictureUrl;
	
	//注册类型：0、正常流程注册(默认)  1、微信免登陆注册
	private Integer registerSourceType;
	//注册Id：如果是微信免登陆注册，则保存微信OpenId
	private String registerSourceUserId;
	//是否修改过刷脸号(我的邀请码):0未修改 1已修改
	private Integer isUpdateCode;

	public Integer getIsUpdateCode() {
		return isUpdateCode;
	}
	public void setIsUpdateCode(Integer isUpdateCode) {
		this.isUpdateCode = isUpdateCode;
	}
	public Integer getRegisterSourceType() {
		return registerSourceType;
	}
	public void setRegisterSourceType(Integer registerSourceType) {
		this.registerSourceType = registerSourceType;
	}

	public String getRegisterSourceUserId() {
		return registerSourceUserId;
	}
	public void setRegisterSourceUserId(String registerSourceUserId) {
		this.registerSourceUserId = registerSourceUserId;
	}

	public User() {
	}

	public User(Long userId) {
		this.id = userId;
	}

	public User(String nickname, String email) {
		this.nickname = nickname;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLpNo() {
		return lpNo;
	}

	public void setLpNo(String lpNo) {
		this.lpNo = lpNo;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getMyInvitationCode() {
		return myInvitationCode;
	}

	public void setMyInvitationCode(String myInvitationCode) {
		this.myInvitationCode = myInvitationCode;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId == null ? null : openId.trim();
	}

	public String getFakeId() {
		return fakeId;
	}

	public void setFakeId(String fakeId) {
		this.fakeId = fakeId == null ? null : fakeId.trim();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token == null ? null : token.trim();
	}

	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount == null ? null : loginAccount.trim();
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd == null ? null : passwd.trim();
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode == null ? null : invitationCode
				.trim();
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language == null ? null : language.trim();
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl == null ? null : headimgurl.trim();
	}

	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell == null ? null : cell.trim();
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity == null ? null : identity.trim();
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts == null ? null : contacts.trim();
	}

	public Integer getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public Integer getLev() {
		return lev;
	}

	public void setLev(Integer lev) {
		this.lev = lev;
	}

	public Integer getRealType() {
		return realType;
	}

	public void setRealType(Integer realType) {
		this.realType = realType;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company == null ? null : company.trim();
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getCirclePictureUrl() {
		return circlePictureUrl;
	}

	public void setCirclePictureUrl(String circlePictureUrl) {
		this.circlePictureUrl = circlePictureUrl;
	}
	
	public Long getInvitationUserId() {
		return invitationUserId;
	}
	public void setInvitationUserId(Long invitationUserId) {
		this.invitationUserId = invitationUserId;
	}
	@Override
	public String toString() {
		return new StringBuilder("User [id=").append(id).append(", source=")
				.append(source).append(", myInvitationCode=")
				.append(myInvitationCode).append(", nickname=")
				.append(nickname).append(", openId=").append(openId)
				.append(", fakeId=").append(fakeId).append(", token=")
				.append(token).append(", loginAccount=").append(loginAccount)
				.append(", passwd=").append(passwd).append(", type=")
				.append(type).append(", sex=").append(sex)
				.append(", invitationCode=").append(invitationCode)
				.append(", language=").append(language).append(", headimgurl=")
				.append(headimgurl).append(", subscribeTime=")
				.append(subscribeTime).append(", cell=").append(cell)
				.append(", identity=").append(identity).append(", contacts=")
				.append(contacts).append(", areaCode=").append(areaCode)
				.append(", address=").append(address).append(", status=")
				.append(status).append(", email=").append(email)
				.append(", lev=").append(lev).append(", realType=")
				.append(realType).append(", createTime=").append(createTime)
				.append(", updateTime=").append(updateTime)
				.append(", company=").append(company).append("circlePictureUrl=").append(circlePictureUrl).append("]").toString();
	}

}