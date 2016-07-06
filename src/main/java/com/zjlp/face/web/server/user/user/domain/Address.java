package com.zjlp.face.web.server.user.user.domain;

import java.io.Serializable;
import java.util.Date;

import com.zjlp.face.web.constants.Constants;

/**
 * 收货地址
 * 
 * @ClassName: Address
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年3月13日 下午5:08:22
 */
public class Address implements Serializable {
	private static final long serialVersionUID = 8548791511896044463L;
	// 主键
	private Long id;
	// 用户编码
	private Long userCode;
	// 地区CODE
	private Integer vAreaCode;
	// 邮编
	private String zipCode;
	// 收货人姓名
	private String name;
	// 手机号
	private String cell;
	// 电话
	private String telephone;
	// 详细地址
	private String addressDetail;
	// 排序
	private Integer sort;
	// 状态 -1：删除，1：正常
	private Integer status;
	// 是否默认（0 否 1是）
	private Integer isDefault;
	// 0 匿名用户 1 实名用户
	private Integer realType;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date updateTime;

	public Address() {
	}

	public Address(Long userId, Integer vAreaCode, String zipCode,
			String userName, String cell, String addressDetail) {
		this.userCode = userId;
		this.vAreaCode = vAreaCode;
		this.zipCode = zipCode;
		this.name = userName;
		this.cell = cell;
		this.addressDetail = addressDetail;
		//默认值
		this.telephone = null;
		this.status = Constants.VALID;
		this.isDefault = Constants.ISDEFAULT;
		this.realType = 1;
		
	}

	public Address(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserCode() {
		return userCode;
	}

	public void setUserCode(Long userCode) {
		this.userCode = userCode;
	}

	public Integer getvAreaCode() {
		return vAreaCode;
	}

	public void setvAreaCode(Integer vAreaCode) {
		this.vAreaCode = vAreaCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode == null ? null : zipCode.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell == null ? null : cell.trim();
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone == null ? null : telephone.trim();
	}

	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail == null ? null : addressDetail
				.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
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

	@Override
	public String toString() {
		return new StringBuilder("Address [id=").append(id)
				.append(", userCode=").append(userCode).append(", vAreaCode=")
				.append(vAreaCode).append(", zipCode=").append(zipCode)
				.append(", name=").append(name).append(", cell=").append(cell)
				.append(", telephone=").append(telephone)
				.append(", addressDetail=").append(addressDetail)
				.append(", sort=").append(sort).append(", status=")
				.append(status).append(", isDefault=").append(isDefault)
				.append(", realType=").append(realType).append(", createTime=")
				.append(createTime).append(", updateTime=").append(updateTime)
				.append("]").toString();
	}

}