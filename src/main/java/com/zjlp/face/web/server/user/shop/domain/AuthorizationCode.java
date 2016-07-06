package com.zjlp.face.web.server.user.shop.domain;

import java.util.Date;

import org.springframework.web.context.ContextLoaderListener;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.GenerateCode;
import com.zjlp.face.web.server.user.shop.dao.AuthorizationCodeDao;

/**
 * 授权码列表
 * @ClassName: AuthorizationCode
 * @Description: (这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2014年7月14日 下午8:50:18
 */
public class AuthorizationCode {
    private Long id;
	
	private Long orderItemId;

	private String code;
	
	private String sourceShopNo;

	private String destinationShopNo;

    private Date activationTime;

    private Integer type;

	private Integer status;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(Date activationTime) {
        this.activationTime = activationTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

	public Integer getStatus(){
		return status;
    }

	public void setStatus(Integer status){
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
	
	public Long getOrderItemId(){
		return orderItemId;
	}
	
	public void setOrderItemId(Long orderItemId){
		this.orderItemId = orderItemId;
	}

	/**
	 * 授权码规则 :8位随机数
	 * @throws Exception
	 */
	public void generateCode(Long classificationId) throws Exception{
		Integer type = null;
		if(10 == classificationId){
			type = 1;//官网
		}else if(11 == classificationId){
			type = 2;//内部商城
		}else if(13 == classificationId){
			type = 3;//外部商城
		}else{
			throw new Exception("错误的商品类目！");
		}
		this.setType(type);
		String code = GenerateCode.generateWord(8);
		AuthorizationCode ac = ContextLoaderListener.getCurrentWebApplicationContext().getBean(AuthorizationCodeDao.class).getAuthorizationCodeByCode(code);
		while(ac != null){
			code=GenerateCode.generateWord(8);
			ac = ContextLoaderListener.getCurrentWebApplicationContext().getBean(AuthorizationCodeDao.class).getAuthorizationCodeByCode(code);
		}
		this.setCode(code);
	}
	
	/** 通过授权码类型获取店铺类型 */
	public Integer getShopTypeByAuthorationCodeType() {
		if(this.type == null){
			return null;
		}
		switch(type){
			case 1:
				return Constants.SHOP_GW_TYPE;
			case 2:
				return Constants.SHOP_SC_TYPE;
			case 3:
				return Constants.SHOP_SC_TYPE;
			default:
				return null;
		}
	}
	public String getCode(){
		return code;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
	public String getSourceShopNo(){
		return sourceShopNo;
	}
	
	public void setSourceShopNo(String sourceShopNo){
		this.sourceShopNo = sourceShopNo;
	}
	
	public String getDestinationShopNo(){
		return destinationShopNo;
	}
	
	public void setDestinationShopNo(String destinationShopNo){
		this.destinationShopNo = destinationShopNo;
	}
}