package com.zjlp.face.web.server.trade.payment.domain.dto;

import java.io.Serializable;

public class AlipayNotice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3457890510674684852L;
	/**描述*/
	private String body;
	
	/**该交易在支付宝系统中的交易流水号。最长 64 位。*/
	private String trade_no;
	
	/**商品的标题/交易标题/订单标题/订单关键字等。*/
	private String subject;
	
	/**DSA必须大写。、 RSA、 MD5 三个值可选,必须大写。*/
	private String sign_type;
	
	/**对应商户网站的订单系统中的唯一订单号，非支付宝交易号。需保证在商户网站中的唯一性。是请求时对应的参数，原样返回。*/
	private String out_trade_no;
	
	/**交易目前所处的状态。
	 *	成功状态的值只有两个：
	 *		z TRADE_FINISHED （ 普 通即时到账的交易成功状态）
	 *		z TRADE_SUCCESS （开通了高级即时到账或机票分销产品后的交易成功状态）
	 */
	private String trade_status;
	
	/**签名*/
	private String sign;
	
	/**示接口调用是否成功，并不表明业务处理结果。 T*/
	private String is_success;
	
	/**该笔订单的资金总额，单位为RMB-Yuan。取值范围为[0.01 ，100000000.00]，精确到小数点后两位。*/
	private String total_fee;
	
	/**标志调用哪个接口返回的链接*/
	private String service;
	
	/**卖家支付宝账号对应的支付宝唯一用户号。以 2088 开头的纯 16 位数字*/
	private String seller_id;
	
	/**支付宝通知校验 ID，商户可以用这个流水号询问支付宝该条通知的合法性*/
	private String notify_id;
	
	/**通知时间（支付宝时间）。格式为 yyyy-MM-dd HH:mm:ss。*/
	private String notify_time;
	
	/**返回通知类型。*/
	private String notify_type;
	
	/**对应请求时的数，原样返回。payment_type 参数，原样返回。*/
	private String payment_type;
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getIs_success() {
		return is_success;
	}
	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getNotify_id() {
		return notify_id;
	}
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}
	public String getNotify_time() {
		return notify_time;
	}
	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}
	public String getNotify_type() {
		return notify_type;
	}
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
}
