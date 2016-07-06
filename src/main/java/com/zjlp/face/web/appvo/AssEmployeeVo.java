package com.zjlp.face.web.appvo;

import java.io.Serializable;
import java.text.DecimalFormat;

public class AssEmployeeVo implements Serializable{

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -6489713975660732120L;
	
	// 订单个数
	private Long count;
	// 全部金额
	private Long amount;
	
	private String amountStr;
	// 员工名称
	private String name;
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		if (null != amount){
			this.amount = amount;
			DecimalFormat df = new DecimalFormat("##0.00");
			this.amountStr = df.format(amount/100.0);
		}else{
			this.amount = 0L;
			this.amountStr = "0.00";
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAmountStr() {
		return amountStr;
	}

}
