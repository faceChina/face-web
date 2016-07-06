package com.zjlp.face.web.server.user.user.domain;


public class AddressDto extends Address {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4220835954719309111L;
	
	public static Address copy(Address address) {
		Address newAddress = new Address();
		newAddress.setUserCode(address.getUserCode()); //用户编码
		newAddress.setvAreaCode(address.getvAreaCode()); // 地区CODE
		newAddress.setZipCode(address.getZipCode()); // 邮编
		newAddress.setName(address.getName());  // 收货人姓名
		newAddress.setCell(address.getCell()); // 手机号
		newAddress.setTelephone(address.getTelephone());  // 电话
		newAddress.setAddressDetail(address.getAddressDetail()); // 详细地址
		newAddress.setSort(address.getSort());  // 排序
		newAddress.setStatus(address.getStatus());  // 状态 -1：删除，1：正常
		newAddress.setIsDefault(address.getIsDefault());  // 是否默认（0 否 1是）
		newAddress.setRealType(address.getRealType());  //0 匿名用户 1 实名用户
		return newAddress;
	}

}
