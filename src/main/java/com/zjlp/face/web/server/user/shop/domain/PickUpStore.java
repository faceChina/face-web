package com.zjlp.face.web.server.user.shop.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 门店自提信息表
 * @ClassName: PickUpStore 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年3月27日 上午9:51:13
 */
public class PickUpStore implements Serializable {
	
	private static final long serialVersionUID = -7112525630465476004L;
	//主键
	private Long id;
	//店铺编号
    private String shopNo;
    //自提点设置
    private String name;
    //省
    private String province;
    //市
    private String city;
    //区、县
    private String county;
    //详细街道地址，不需要重复填写省/市/区
    private String address;
    //联系方式
    private String phone;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    
    public PickUpStore(){}
    public PickUpStore(Long id, String shopNo) {
    	this.id = id;
    	this.shopNo = shopNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo == null ? null : shopNo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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
}