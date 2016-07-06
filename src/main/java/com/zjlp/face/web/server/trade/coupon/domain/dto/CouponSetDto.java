package com.zjlp.face.web.server.trade.coupon.domain.dto;


import com.zjlp.face.web.server.trade.coupon.domain.CouponSet;

public class CouponSetDto extends CouponSet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5900339701449959571L;
	
	/**商家查询优惠券状态：未开始*/
	public static final Integer NOT_BEGIN = 1;
	/**商家查询优惠券状态：进行中*/
	public static final Integer ON_GOING = 2;
	/**商家查询优惠券状态：已失效*/
	public static final Integer INVALID = 3;
	
	
	/**面值类型1.随机*/
	public static final Integer FACE_VALUE_TYPE_RANDOM = 1;
	/**面值类型2.固定值*/
	public static final Integer FACE_VALUE_TYPE_REGULAR = 2;
	
	/**发行量类型1.张数*/
	public static final Integer CIRCULATION_TYPE_NUMBER = 1;
	/**发行量类型2.总面额*/
	public static final Integer CIRCULATION_TYPE_MONEY = 2;
	
	/**是否可以合并使用1.可以合并*/
	public static final Integer CAN_JOIN = 1;
	/**是否可以合并使用2.不可以合并*/
	public static final Integer CAN_NOT_JOIN = 0;
	
	/**流通类型1.指向性流通*/
	public static final Integer CURRENCY_TYPE_POINT = 1;
	/**流通类型2.任意性流通*/
	public static final Integer CURRENCY_TYPE_ANY = 2;
	
	/**状态:-1已删除*/
	public static final Integer STATUS_DELETE = -1;
	/**状态:0被商家结束*/
	public static final Integer STATUS_END = 0;
	/**状态:1未删除*/
	public static final Integer STATUS_NOT_DELETE = 1;
	
	/**总领取数*/
	private Integer receiveNumber;
	/**未使用数*/
	private Integer usedNumber;
	/**当前登录用户未使用的该优惠券的数量*/
	private Integer userUnuseNumber;
	/**当前登录用户所领取的数量*/
	private Integer userReceiveNumber;
	
	public Integer getReceiveNumber() {
		return receiveNumber;
	}
	public void setReceiveNumber(Integer receiveNumber) {
		this.receiveNumber = receiveNumber;
	}
	public Integer getUsedNumber() {
		return usedNumber;
	}
	public void setUsedNumber(Integer usedNumber) {
		this.usedNumber = usedNumber;
	}
	public Integer getUserUnuseNumber() {
		return userUnuseNumber;
	}
	public void setUserUnuseNumber(Integer userUnuseNumber) {
		this.userUnuseNumber = userUnuseNumber;
	}
	
	public Integer getUserReceiveNumber() {
		return userReceiveNumber;
	}
	public void setUserReceiveNumber(Integer userReceiveNumber) {
		this.userReceiveNumber = userReceiveNumber;
	}
	/**
	 * 是否全部领取
	 * @Title: getIsAllReceive 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月25日 下午5:55:54  
	 * @author cbc
	 */
	public boolean getIsAllReceive() {
		return getReceiveNumber().longValue() >= getCirculation().longValue();
	}
	
	/**
	 * 当前用户是否已经领取到了每人限领数量
	 * @Title: getIsReachLimitNumber 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年10月8日 下午1:50:27  
	 * @author cbc
	 */
	public boolean getIsReachLimitNumber() {
		return getUserReceiveNumber().compareTo(getLimitNumber()) >= 0;
	}
	
	/**
	 * 当前用户是否已经领取该优惠券
	 * @Title: getHasReceive 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月25日 下午5:58:27  
	 * @author cbc
	 */
	public boolean getHasReceive() {
		return getUserUnuseNumber().intValue() > 0;
	}
	/**
	 *  优惠券地址
	 * @Title: getCouponUrl 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月25日 下午6:07:24  
	 * @author cbc
	 */
	public String getCouponUrl() {
		return new StringBuilder("/wap/").append(getShopNo()).append("/any/coupon/").append(getId()).append(".htm").toString();
	}
	
}
