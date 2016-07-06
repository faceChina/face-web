package com.zjlp.face.web.server.trade.payment.domain.dto;

import java.io.Serializable;

/**
 * 分配金额对象
* @ClassName: DistributeDto 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author phb 
* @date 2015年3月20日 下午2:46:15 
*
 */
public class DistributeDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6247161786951934967L;
	//卖家金额
	private Long sellerMoney = 0l;
	//支付手续费金额
	private Long payFeeMoney = 0l;
	//平台手续费金额
	private Long platformFeeMoney = 0l;

	public Long getSellerMoney() {
		return sellerMoney;
	}

	public void setSellerMoney(Long sellerMoney) {
		this.sellerMoney = sellerMoney;
	}

	public Long getPayFeeMoney() {
		return payFeeMoney;
	}

	public void setPayFeeMoney(Long payFeeMoney) {
		this.payFeeMoney = payFeeMoney;
	}

	public Long getPlatformFeeMoney() {
		return platformFeeMoney;
	}

	public void setPlatformFeeMoney(Long platformFeeMoney) {
		this.platformFeeMoney = platformFeeMoney;
	}
	
	
}
