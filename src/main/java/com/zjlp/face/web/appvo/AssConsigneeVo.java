package com.zjlp.face.web.appvo;

import java.io.Serializable;

/**
 * 
* @ClassName: AssConsigneeVo
* @Description: 收货人信息
* @author wxn
* @date 2014年12月16日 下午1:43:10
*
 */
public class AssConsigneeVo implements Serializable{

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 7483483537683748493L;
	
    //收货人姓名
    private String name;
    //收货人手机
    private String phone;
    //收货详细地址
    private String addressDetail;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}

}
