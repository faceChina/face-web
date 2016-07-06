package com.zjlp.face.web.server.trade.payment.domain.dto;

/**
 * 查询微信支付订单的请求参数
 * @ClassName: reqOrederParam 
 * @Description: (查询微信支付订单的请求参数) 
 * @author ah
 * @date 2015年3月12日 上午11:24:08
 */
public class ReqOrederParam {

	// 公众账号ID
	private String appid;
	// 商户号
	private String mch_id;
	// 微信订单号
	private String transaction_id;
	// 商户订单号
	private String out_trade_no;
	// 随机字符串
	private String nonce_str;
	// 签名
	private String sign;
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
