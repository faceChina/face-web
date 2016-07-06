package com.zjlp.face.web.server.trade.payment.domain.dto;

import com.zjlp.face.util.page.Aide;
import com.zjlp.face.web.server.trade.payment.domain.MemberTransactionRecord;

public class MemberTransactionRecordDto extends MemberTransactionRecord {

	private static final long serialVersionUID = 873239576811838367L;
	//（1 充值  2 消费）
	/** 类型：充值 */
	public static final Integer TYPE_RECHAGE = 1;
	/** 类型：消费 */
	public static final Integer TYPE_CONSUM = 2;
	//资金流入渠道
	/** 资金流入渠道 ： 微信支付 */
	public static final String CHANNEL_WECHAT = "WECHAT";
	/** 资金流入渠道 ： 钱包支付 */
	public static final String CHANNEL_WALLET = "WALLET";
	/** 资金流入渠道 ： 会员卡消费 */
	public static final String CHANNEL_MEMBER_CARD = "MEMBER_CARD";
	/** 资金流入渠道 ： 银行卡支付*/
	public static final String CHANNEL_BANK_CARD = "BANK_CARD";
	
	//辅助分页信息等
	private Aide aide = new Aide();
	public Aide getAide() {
		return aide;
	}
	public void setAide(Aide aide) {
		this.aide = aide;
	}
	/**
	 * 赠送金额
	 * @Title: getGiftAmount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年4月11日 上午11:39:40  
	 * @author lys
	 */
	public Long getGiftAmount() {
		if (null == super.getAmount() || null == super.getBeforeAmount()
				|| null == super.getAmount()) {
			return null;
		}
		return super.getAmount() - super.getBeforeAmount() - super.getAmount();
	}
	
}
