package com.zjlp.face.web.server.user.businesscard.domain;


import java.io.Serializable;
import java.util.Date;

import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.constants.ImageConstants;

public class BusinessCard implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2988687337444345835L;
	/**联系方式公开*/
	public static final Integer PHONE_PUBLIC = 1;
	/**联系方式仅好友可见*/
	public static final Integer PHONE_FRIEND = 2;
	/**联系方式仅自己可见*/
	public static final Integer PHONE_SELF = 3;
	/**总店*/
	public static final Integer TYPE_P = 1;
	/**分店*/
	public static final Integer TYPE_BF = 2;
	/**官网*/
	public static final Integer TYPE_GW = 3;
	/**自定义网址*/
	public static final Integer TYPE_DU = 4;
	/**默认背景图片*/
	public static final Integer PIC_TYPE_DEFAULT = 1;
	/**用户自定义图片*/
	public static final Integer PIC_TYPE_CUSTOM = 2;

	private Long id;

    private Long userId;
    //店铺类型（1.总店2.分店3.官网 4.自定义网址）
    private Integer shopType;
    //店铺主键
    private String shopId;
    //地区编码
    private Integer vAreaCode;
    //地区名称
    private String vAreaName;
    //公司编码
    private Integer companyCode;
    //公司名称
    private String companyName;
    //职位
    private String position;
    //联系号码
    private String phone;
    //行业编码
    private Integer industryCode;
    //行业名称
    private String industryName;
    //行业供应
    private String industryProvide;
    //行业需求
    private String industryRequirement;
    //状态:-1失效 1有效
    private Integer status;
    //联系方式可见性(1公开 2仅好友可见 3仅自己可见)
    private Integer phoneVisibility;
    //名片背景图片
    private String backgroundPic;
    //是否是默认背景图片//1是默认图片2是用户自定义图片
    private Integer picType;
    //名片模板
    private String template;
    //自定义网址
    private String defineUrl;
    
    public BusinessCard(){}
    
    public BusinessCard(Long userId,String position,String companyName){
    	this.userId = userId;
    	this.position = position;
    	this.companyName = companyName;
    }
    
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	private Date createTime;

    private Date updateTime;
    
    public Integer getPicType() {
    	return picType;
    }
    
    public void setPicType(Integer picType) {
    	this.picType = picType;
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

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public Integer getvAreaCode() {
        return vAreaCode;
    }

    public void setvAreaCode(Integer vAreaCode) {
        this.vAreaCode = vAreaCode;
    }

    public String getvAreaName() {
        return vAreaName;
    }

    public void setvAreaName(String vAreaName) {
        this.vAreaName = vAreaName == null ? null : vAreaName.trim();
    }

    public Integer getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(Integer companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(Integer industryCode) {
        this.industryCode = industryCode;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName == null ? null : industryName.trim();
    }

    public String getIndustryProvide() {
        return industryProvide;
    }

    public void setIndustryProvide(String industryProvide) {
        this.industryProvide = industryProvide == null ? null : industryProvide.trim();
    }

    public String getIndustryRequirement() {
        return industryRequirement;
    }

    public void setIndustryRequirement(String industryRequirement) {
        this.industryRequirement = industryRequirement == null ? null : industryRequirement.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

	public Integer getPhoneVisibility() {
		return phoneVisibility;
	}

	public void setPhoneVisibility(Integer phoneVisibility) {
		this.phoneVisibility = phoneVisibility;
	}

	public String getBackgroundPic() {
		return backgroundPic;
	}

	public void setBackgroundPic(String backgroundPic) {
		this.backgroundPic = backgroundPic;
	}
	
	/**
	 * 
	 * @Title: getZoomBackgroundPic 
	 * @Description: 获取缩略图
	 * @return
	 * @date 2015年9月7日 下午4:43:53  
	 * @author cbc
	 */
	public String getZoomBackgroundPic() {
		if (null != this.getBackgroundPic()) {
			return ImageConstants.getCloudstZoomPath(this.getBackgroundPic(), PropertiesUtil.getContexrtParam("bussinessCardBackgroundFile"));
		}
		return null;
	}
	
	/**
	 * 
	 * @Title: getDefaultPicForWap 
	 * @Description: 给wap页面用的默认背景图片路径
	 * @return
	 * @date 2015年9月10日 下午2:31:50  
	 * @author cbc
	 */
	public String getDefaultPicForWap() {
		if (null != this.getBackgroundPic()) {
			return getBackgroundPic().replace("1080", "640");
		}
		return null;
	}
	/**店铺链接*/
	private String shopUrl;
	/**与我合作链接*/
	private String cooperationUrl;

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	public String getCooperationUrl() {
		return cooperationUrl;
	}

	public void setCooperationUrl(String cooperationUrl) {
		this.cooperationUrl = cooperationUrl;
	}

	public String getDefineUrl() {
		return defineUrl;
	}

	public void setDefineUrl(String defineUrl) {
		this.defineUrl = defineUrl;
	}

	/**
	 * 
	 * @Title: checkShopType 
	 * @Description: 校验shopType合法性
	 * @param shopType2
	 * @return
	 * @date 2015年9月21日 上午11:39:03  
	 * @author cbc
	 */
	public static boolean checkShopType(Integer shopType) {
		return TYPE_BF.equals(shopType)||TYPE_GW.equals(shopType)||TYPE_P.equals(shopType) || TYPE_DU.equals(shopType);
	} 
	
}