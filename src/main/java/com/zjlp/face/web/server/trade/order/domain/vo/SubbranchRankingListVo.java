package com.zjlp.face.web.server.trade.order.domain.vo;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * 
 * @ClassName: SubbranchRankingListVo 
 * @Description:  佣金排行榜
 * @author talo
 * @date 2015年8月11日 下午2:41:50
 */
public class SubbranchRankingListVo implements Serializable {

	private static final long serialVersionUID = -5034149962625076473L;
	
	//分店名称
	private String userName;
	//分店总佣金
	private String totalCommission;
	private Long totalCommissionLong;
	//头像
	private String headUrl;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTotalCommission() {
		if (null != totalCommissionLong) {
			DecimalFormat df = new DecimalFormat("##0.00");
			return df.format(this.totalCommissionLong/100.0);
		}else {
			return null;
		}
		
	}
	public void setTotalCommission(String totalCommission) {
		this.totalCommission = totalCommission;
	}
	public Long getTotalCommissionLong() {
		return totalCommissionLong;
	}
	public void setTotalCommissionLong(Long totalCommissionLong) {
		this.totalCommissionLong = totalCommissionLong;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	
	
}
