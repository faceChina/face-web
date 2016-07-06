package com.zjlp.face.web.appvo;

import java.io.Serializable;

/**
* @ClassName: AssLogisticsVo
* @Description: 物流信息
* @author wxn
* @date 2014年12月16日 下午1:43:29
*
 */
public class AssLogisticsVo implements Serializable {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 2171331302405945789L;
	
    // 物流公司(
    private Integer logisticsCompeny;
    // 物流编号
    private String logisticsNumber;
    // 物流描述
    private String logistics;
    
    // 物流公司名称
    private String logisticsCompenyName;
    //发货方式
	private Integer deliveryType;
	//送货人
	private String deliveryUser;
	//送货人联系号码
	private String deliveryPhone;
	
	public Integer getLogisticsCompeny() {
		return logisticsCompeny;
	}
	public void setLogisticsCompeny(Integer logisticsCompeny) {
		this.logisticsCompeny = logisticsCompeny;
	}
	public String getLogisticsNumber() {
		return logisticsNumber;
	}
	public void setLogisticsNumber(String logisticsNumber) {
		this.logisticsNumber = logisticsNumber;
	}
	public String getLogistics() {
		return logistics;
	}
	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}
	public String getLogisticsCompenyName() {
		return logisticsCompenyName;
	}
	public void setLogisticsCompenyName(String logisticsCompenyName) {
		this.logisticsCompenyName = logisticsCompenyName;
	}
	public Integer getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getDeliveryUser() {
		return deliveryUser;
	}
	public void setDeliveryUser(String deliveryUser) {
		this.deliveryUser = deliveryUser;
	}
	public String getDeliveryPhone() {
		return deliveryPhone;
	}
	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}
	
	

}
