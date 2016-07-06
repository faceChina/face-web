package com.zjlp.face.web.appvo;

import java.io.Serializable;

import com.zjlp.face.web.util.DataUtils;



public class AssAccountOperationVo implements Serializable{
	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 373454989652356312L;
	private Long id;
	// 创建时间
	private Long createTime;
	//动作
	private String operationTypeStr;
	//金额
	private Long operationAmount;
	//操作金额正负位, false为负，true为正
	private boolean isPositive;
	// 可用余额
	private Long balance;
	// 金额格式化
	private String operationAmountStr;
	// 可用余额格式化
	private String balanceStr;
	//流水号
	private String serialNumber;
	/**收支对象**/
	private String target;
	
    // 支付方式：1.支付宝支付、2.钱包支付、3.微信支付
    private Integer payWayFlag;
    // 备注
    private String remark;
    
    private String payTypeStr;
	
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getOperationTypeStr() {
		return operationTypeStr;
	}
	public void setOperationTypeStr(String operationTypeStr) {
		this.operationTypeStr = operationTypeStr;
	}
	public Long getOperationAmount() {
		return operationAmount;
	}
	public void setOperationAmount(Long operationAmount) {
		this.operationAmount = operationAmount;
		this.operationAmountStr = DataUtils.formatMoney("##0.00", operationAmount);
	}
	public boolean isPositive() {
		return isPositive;
	}
	public void setPositive(boolean isPositive) {
		this.isPositive = isPositive;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public String getOperationAmountStr() {
		return operationAmountStr;
	}
	public String getBalanceStr() {
		return balanceStr;
	}
	public void setBalanceStr(String balanceStr) {
		this.balanceStr = balanceStr;
	}
	public void setOperationAmountStr(String operationAmountStr) {
		this.operationAmountStr = operationAmountStr;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public Integer getPayWayFlag() {
		return payWayFlag;
	}
	public void setPayWayFlag(Integer payWayFlag) {
		this.payWayFlag = payWayFlag;
	}
	public String getRemark() {
		return remark == null ? "":remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getPayTypeStr() {
		
		if (null == payWayFlag) {
			return "";
		}
		switch (payWayFlag) {
		case 1:
			return "支付宝支付";
		case 2:
			return "钱包支付";
		case 3:
			return "微信支付";
		default:
			return "";
		}
	}



	public static final int[] USER_RECIVE = {1, 2, 3};
	
	public static final int[] USER_PAY = {14, 15, 16, 17, 19, 21};

}
