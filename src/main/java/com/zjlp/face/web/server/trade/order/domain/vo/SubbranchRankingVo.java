package com.zjlp.face.web.server.trade.order.domain.vo;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 
 * @ClassName: SubbranchRankingVo 
 * @Description:  佣金排行榜
 * @author talo
 * @date 2015年8月11日 下午2:41:50
 */
public class SubbranchRankingVo implements Serializable {
	private static final long serialVersionUID = 4733096123791716785L;
	
	//我的佣金
	private Long commissionLong;
	private String commission;
	//我的排名
	private Integer ranking;
	//列表
	private List<SubbranchRankingListVo> datas;
	
	public Long getCommissionLong() {
		return commissionLong;
	}
	public void setCommissionLong(Long commissionLong) {
		this.commissionLong = commissionLong;
	}
	public String getCommission() {
		if (null != commissionLong) {
			DecimalFormat df = new DecimalFormat("##0.00");
			return df.format(this.commissionLong/100.0);
		} else {
			return null;
		}
		
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	public List<SubbranchRankingListVo> getDatas() {
		return datas;
	}
	public void setDatas(List<SubbranchRankingListVo> datas) {
		this.datas = datas;
	}
	
	
	
}
