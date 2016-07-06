package com.zjlp.face.web.constants;
/**
 * 订单交易状态类
 * TRADE_WAIT_BUYER_PAY(等待买家付款,原:待付款)
 * TRADE_PAY_PENDING(支付付款确认中 即:支付中) 
 * TRADE_WAIT_SELLER_SEND(等待卖家发货,原:已付款) 
 * TRADE_WAIT_BUYER_CONFIRM(等待买家确认收货,原:已发货)
 * TRADE_FINISHED(交易成功) 
 * TRADE_CANCELED(卖家或买家主动取消交易,原:已取消) 
 * TRADE_DELETED(卖家或买家主动删除交易,原:已删除)
 * TRADE_OUTTIME_COLOSED(交易超时关闭)
 */
public interface TradeStatus {
	
	/** TRADE_WAIT_BUYER_PAY(等待买家付款,原:待付款) */
	public static final String TRADE_WAIT_BUYER_PAY ="TRADE_WAIT_BUYER_PAY";
	
	/** TRADE_PAY_PENDING(支付付款确认中 即:支付中)  */
	public static final String TRADE_PAY_PENDING ="TRADE_PAY_PENDING";
	
	/** TRADE_WAIT_SELLER_SEND(等待卖家发货,原:已付款)  */
	public static final String TRADE_WAIT_SELLER_SEND ="TRADE_WAIT_SELLER_SEND";
	
	/** TRADE_WAIT_BUYER_CONFIRM(等待买家确认收货,原:已发货)  */
	public static final String TRADE_WAIT_BUYER_CONFIRM ="TRADE_WAIT_BUYER_CONFIRM";
	

	
	
	
	
	
}
