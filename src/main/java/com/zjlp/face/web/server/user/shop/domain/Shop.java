package com.zjlp.face.web.server.user.shop.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.web.context.ContextLoaderListener;

import com.zjlp.face.shop.util.ConstantsUtil;
import com.zjlp.face.shop.util.GenerateCode;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.server.user.shop.dao.ShopDao;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;
import com.zjlp.face.web.server.user.user.domain.User;

public class Shop implements Serializable{
	
	private static final long serialVersionUID = -2527650203722599804L;
	/**店招(默认1)*/
	public static final Integer SIGN_PIC_DEFAULT = 1;
	/**店招(用户上传2)*/
	public static final Integer SIGN_PIC_UPLOAD = 2;
	
	//店铺编码
	private String no;
	//用户ID
    private Long userId;
    //店铺名称
    private String name;
    //联系人
    private String contacts;
    //市场人员
    private String marketer;
    //运营人员
    private String operater;
    //手机
    private String cell;
    //地区编码
    private Integer areaCode;
    //地址
    private String address;
    //激活时间
    private Date activationTime;
    //微信账户
    private String wechat;
    //密码
    private String passwd;
    //来源:1、微信
    private Integer source;
    //绑定时间
    private Date bindingTime;
    //签约时间
    private Date signingTime;
    //有效时间
    private Date effectiveTime;
    //状态 -1：删除，0：未激活，1：正常
    private Integer status;
    //授权码
    private String authorizationCode;
    //类型：1官网 2 商城
    private Integer type;
    //代理类型
    private Integer proxyType;
    //站内唯一识别ID
    private String token;
    //
    private String invitationCode;
    //店铺邀请码
    private String onInvitationCode;
    //是否收费（1 收费  2 免费）
    private Integer isFree;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //简介
    private String shopContent;
    //配送方式
    private Integer logisticsTypes;
    //应用ID
    private String appId;
    //应用秘钥
    private String appSecret;
    //是否是认证的服务号或者订阅号
    private Integer authenticate;
	// 是否精确查找,有值：是,null：不是
	private String searchKey;
    //引导关注公共号页面
    private String guideUrl;
    //店铺二维码
    private String twoDimensionalCode;
    //是否开启店铺推广
    private Boolean isShopPopularizeOpenSession;
    //是否开启代理推广
    private Boolean isAgencyPopularizeOpenSession;
    //店铺logo
    private String shopLogoUrl;
    
    //店铺招牌图片URL
    private String signPic;
    //店铺招牌图片是否默认(1默认， 2用户上传)
    private Integer isDefaultSignPic;
    //是否显示招募按钮(0不显示，1显示)
    private Integer recruitButton;
    //是否开启公告
    private Integer isNotice;
    
    public Integer getIsNotice() {
		return isNotice;
	}

	public void setIsNotice(Integer isNotice) {
		this.isNotice = isNotice;
	}

	/**
     * 
     * @Title: getZoomSignPic 
     * @Description: 获取店铺招牌图片
     * @return
     * @date 2015年9月14日 下午2:57:36  
     * @author cbc
     */
    public String getZoomSignPic() {
    	if (isDefaultSignPic != null && SIGN_PIC_UPLOAD == isDefaultSignPic && signPic != null) {
			String shopSignPic = PropertiesUtil.getContexrtParam(ImageConstants.SHOP_SIGN_PIC);
			return ImageConstants.getCloudstZoomPath(signPic, shopSignPic);
		}
		return signPic;
    }
    
	public String getSignPic() {
		return signPic;
	}
	public void setSignPic(String signPic) {
		this.signPic = signPic;
	}
	public Integer getIsDefaultSignPic() {
		return isDefaultSignPic;
	}
	public void setIsDefaultSignPic(Integer isDefaultSignPic) {
		this.isDefaultSignPic = isDefaultSignPic;
	}
	public Integer getRecruitButton() {
		return recruitButton;
	}
	public void setRecruitButton(Integer recruitButton) {
		this.recruitButton = recruitButton;
	}
	public String getShopLogoUrl() {
		return shopLogoUrl;
	}
	public void setShopLogoUrl(String shopLogoUrl) {
		this.shopLogoUrl = shopLogoUrl;
	}
	public Boolean getIsShopPopularizeOpenSession() {
		return isShopPopularizeOpenSession;
	}
	public void setIsShopPopularizeOpenSession(Boolean isShopPopularizeOpenSession) {
		this.isShopPopularizeOpenSession = isShopPopularizeOpenSession;
	}
	public Boolean getIsAgencyPopularizeOpenSession() {
		return isAgencyPopularizeOpenSession;
	}
	public void setIsAgencyPopularizeOpenSession(
			Boolean isAgencyPopularizeOpenSession) {
		this.isAgencyPopularizeOpenSession = isAgencyPopularizeOpenSession;
	}
	public String getGuideUrl() {
		return guideUrl;
	}
	public void setGuideUrl(String guideUrl) {
		this.guideUrl = guideUrl;
	}
	public Shop(){}
    public Shop(String no) {
    	this.no = no;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getMarketer() {
        return marketer;
    }

    public void setMarketer(String marketer) {
        this.marketer = marketer == null ? null : marketer.trim();
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater == null ? null : operater.trim();
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell == null ? null : cell.trim();
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

    public Date getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(Date activationTime) {
        this.activationTime = activationTime;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Date getBindingTime() {
        return bindingTime;
    }

    public void setBindingTime(Date bindingTime) {
        this.bindingTime = bindingTime;
    }

    public Date getSigningTime() {
        return signingTime;
    }

    public void setSigningTime(Date signingTime) {
        this.signingTime = signingTime;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode == null ? null : authorizationCode.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getProxyType() {
        return proxyType;
    }

    public void setProxyType(Integer proxyType) {
        this.proxyType = proxyType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode == null ? null : invitationCode.trim();
    }

    public String getOnInvitationCode() {
        return onInvitationCode;
    }

    public void setOnInvitationCode(String onInvitationCode) {
        this.onInvitationCode = onInvitationCode == null ? null : onInvitationCode.trim();
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
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

    public String getShopContent() {
        return shopContent;
    }

    public void setShopContent(String shopContent) {
        this.shopContent = shopContent == null ? null : shopContent.trim();
    }
	public Integer getLogisticsTypes() {
		return logisticsTypes;
	}
	public void setLogisticsTypes(Integer logisticsTypes) {
		this.logisticsTypes = logisticsTypes;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public Integer getAuthenticate() {
		return authenticate;
	}
	public void setAuthenticate(Integer authenticate) {
		this.authenticate = authenticate;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	/**
	 * @Description: 组装店铺
	 * @param no
	 * @param user
	 * @param dto
	 * @return
	 * @date: 2015年3月20日 上午10:55:53
	 * @author: zyl
	 */
	public static Shop _generateShop(String no, User user, ShopDto dto){
		Date date = new Date();
		Shop shop = new Shop();
		// 生成产品邀请码
		String invitationCode = GenerateCode.generateInvitationCode(user.getId().toString(), dto.getType());
		shop.setNo(no);
		shop.setName(dto.getName());
		shop.setType(dto.getType());
		if(null != dto.getAuthorizationCodeType()){
			switch (dto.getAuthorizationCodeType()) {
			case 1:
			case 2:
				shop.setProxyType(1);
				break;
			case 3:
				shop.setProxyType(2);
				break;
			default:
				shop.setProxyType(0);
			}
		}
		/** 生成招募码*/
		shop.setAuthorizationCode(generateRecruitCode());
		shop.setActivationTime(dto.getSigningTime());
		shop.setSigningTime(dto.getSigningTime());
		shop.setUserId(user.getId());
		shop.setMarketer(dto.getMarketer());
		shop.setOperater(dto.getOperater());
		shop.setOnInvitationCode(dto.getOnInvitationCode());
		shop.setContacts(user.getContacts());
		shop.setCell(user.getCell());
		shop.setInvitationCode(invitationCode);
		shop.setLogisticsTypes(1);
		shop.setToken(user.getToken());
		shop.setSource(dto.getSource());
		shop.setStatus(Constants.VALID);
		if(ShopDto.FREE.equals(dto.getIsFree())){
			shop.setIsFree(2);
		}else{
			shop.setIsFree(1);
		}
		shop.setCreateTime(date);
		shop.setUpdateTime(date);
		// 有效期
		shop.setEffectiveTime(_settingEffectiveTime(dto.getSigningTime(), dto.getType()));
		// 二维码
		shop.setTwoDimensionalCode(dto.getTwoDimensionalCode());
		return shop;
	}
	
	/** 生成招募码,4位数字+一个大写字母*/
	private static String generateRecruitCode(){
		Random r=new Random();
		String recruitCode=com.zjlp.face.web.constants.GenerateCode.createRandom(true,4)+((char)('A'+r.nextInt(26)));
		Shop shop=ContextLoaderListener.getCurrentWebApplicationContext().getBean(ShopDao.class).getByAuthCode(recruitCode);
		while(null!=shop){
			recruitCode=com.zjlp.face.web.constants.GenerateCode.createRandom(true,4)+((char)('A'+r.nextInt(26)));
			shop=ContextLoaderListener.getCurrentWebApplicationContext().getBean(ShopDao.class).getByAuthCode(recruitCode);
		}
		return recruitCode;
	}
	
	/**
	 * @Description: 获得有效时间(普通产品：签约时间加上一年零七天 平台产品：2099年12月31日)
	 * @param signingTime
	 * @param type
	 * @return
	 * @date: 2015年3月20日 上午10:58:29
	 * @author: zyl
	 */
	public static Date _settingEffectiveTime(Date signingTime, Integer type){
		Calendar cal = Calendar.getInstance();
		if(null != signingTime && !ConstantsUtil.CLASSIFICATION_PF.equals(type)){
			cal.setTime(signingTime);
			// 一年
			cal.add(Calendar.YEAR, 1);
			// 七天
			cal.add(Calendar.DAY_OF_MONTH, 7);
		}else{
			cal.set(Calendar.YEAR, 2099);
			cal.set(Calendar.MONTH, 11);
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DAY_OF_MONTH, lastDay);
		}
		return cal.getTime();
	}
	public String getTwoDimensionalCode() {
		return twoDimensionalCode;
	}
	public void setTwoDimensionalCode(String twoDimensionalCode) {
		this.twoDimensionalCode = twoDimensionalCode;
	}
	
	/**
	 * 
	 * @Title: checkIsNotice 
	 * @Description:验证isNotice合法性
	 * @param isNotice
	 * @return
	 * @date 2015年9月21日 下午5:08:56  
	 * @author cbc
	 */
	public static boolean checkIsNotice(Integer isNotice) {
		return Constants.VALID.equals(isNotice) || Constants.NOTDEFAULT.equals(isNotice);
	}
	
}