package com.zjlp.face.web.server.trade.order.domain.vo;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * 
 * @ClassName: SupplierSalesRankingVo 
 * @Description: 总店用户销售排行 
 * @author cbc
 * @date 2015年8月11日 下午2:41:50
 */
public class SupplierSalesRankingVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3812972546728281371L;
	//分店ID
	private Long subbranchId;
	//用户名
	private String userName;
	//分店userId
	private Long userId;
	//订单数
	private Integer orderCount;
	//成交总额
	private Long payAmount;
	//头像
	private String headUrl;
	//分店级别
	private Long level = 1L;
	//手机
	private String userCell;
	//支出佣金
	private Long commission;
	//用户登陆账号
	private String loginAccount;
	
	
	public String getLoginAccount() {
		return loginAccount;
	}

	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}

	public String getPayAmountStr() {
		DecimalFormat df = new DecimalFormat("##0.00");
		return df.format(this.payAmount/100.0);
	}
	
	public String getCommissionStr() {
		DecimalFormat df = new DecimalFormat("##0.00");
		return df.format(this.commission/100.0);
	}

	
	public Long getCommission() {
		return commission;
	}


	public void setCommission(Long commission) {
		this.commission = commission;
	}


	public Long getSubbranchId() {
		return subbranchId;
	}

	public void setSubbranchId(Long subbranchId) {
		this.subbranchId = subbranchId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public String getUserCell() {
		return userCell;
	}

	public void setUserCell(String userCell) {
		this.userCell = userCell;
	}
	
	
}
