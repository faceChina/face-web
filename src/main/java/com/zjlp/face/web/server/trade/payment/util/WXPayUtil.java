package com.zjlp.face.web.server.trade.payment.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.springframework.util.Assert;

import com.zjlp.face.account.exception.PaymentException;
import com.zjlp.face.util.compare.SortParamUtil;
import com.zjlp.face.util.encryption.md5.MD5Util;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.file.xml.XmlHelper;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.GenerateCode;
import com.zjlp.face.web.exception.ext.WechatException;
import com.zjlp.face.web.server.trade.payment.domain.dto.PrepayOrderResult;
import com.zjlp.face.web.server.trade.payment.domain.dto.ReqOrederParam;
import com.zjlp.face.web.server.trade.payment.domain.dto.WXNotice;
import com.zjlp.face.web.server.trade.payment.domain.dto.WXOrder;
import com.zjlp.face.web.server.trade.payment.domain.dto.WXPrepayOrder;

/**
 * 微信调起支付类
 * 
 * @author Sunlight
 * 
 */
public class WXPayUtil {
	
	// 获取用户基本信息
	private final static String GET_USER_BASE_INFO_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";

	public static String createPackageValue(String appid, String payKey, String prepay_id)  {
		SortedMap<String, String> nativeObj = new TreeMap<String, String>();
		nativeObj.put("appId", appid);
		nativeObj.put("timeStamp", Long.toString(new Date().getTime() / 1000));
		nativeObj.put("nonceStr", GenerateCode.generateWord(32));
		nativeObj.put("package", "prepay_id=" + prepay_id);
		nativeObj.put("signType", "MD5");
		nativeObj.put("paySign", createSign(nativeObj, payKey));
		return JSONObject.fromObject(nativeObj).toString();
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	@SuppressWarnings("rawtypes")
	private static String createSign(SortedMap<String, String> packageParams, String AppKey) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + AppKey);
		String sign = MD5Util.getInstance().encryptionMD5(sb.toString()).toUpperCase();
		return sign;
	}
	
	/**
	 * 获取基本权限URL
	 * @Title: getoauth2Url 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年3月11日 下午1:33:52  
	 * @author ah
	 */
	public static String getoauth2Url(String appId, String path, String url) {
		String userBaseInfoUrl = GET_USER_BASE_INFO_URL.replace("APPID", appId).replace("REDIRECT_URI", urlEnodeUTF8(path)).replace("STATE", urlEnodeUTF8(url));
		return userBaseInfoUrl;
	}
	
	/**
	 * 把网址里的特殊字符转换，如http://转换成http:%3A%2F%2F
	 * @Title: urlEnodeUTF8 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param str
	 * @return
	 * @date 2015年2月3日 下午4:17:46  
	 * @author liujia
	 */
    private static String urlEnodeUTF8(String str){
        String result = str;
        try {
            result = URLEncoder.encode(str,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    private static String generateWord(int length) {
		final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }
    
    /**
	 * 请求微信统一下单接口
	 * @Title: _getWechatOrderPrepayId
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param orderNo
	 * @param goodname
	 * @param openId
	 * @param spbillCreateIp
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年3月16日 下午4:27:07
	 */
	@SuppressWarnings("deprecation")
	public static String getWechatOrderPrepayId(Long totalPrice,String orderNo, String goodname, String openId, String spbillCreateIp,String noticeUrl)throws WechatException {

		try {
			// 异步回调通知URL
			String wgjUrl = PropertiesUtil.getContexrtParam("WGJ_URL");
			Assert.hasLength(wgjUrl, "WGJ_URL未配置");
			String appId = PropertiesUtil.getContexrtParam("WXPAY_APP_ID");
			Assert.hasLength(appId, "WXPAY_APP_ID未配置");
			String mchId = PropertiesUtil.getContexrtParam("WXPAY_MCH_ID");
			Assert.hasLength(mchId, "WXPAY_MCH_ID未配置");
			String unifiedUrl = PropertiesUtil.getContexrtParam("WXPAY_UNIFIED_ORDER");
			Assert.hasLength(unifiedUrl, "WXPAY_UNIFIED_ORDER未配置");
			Assert.hasLength(noticeUrl, "noticeUrl为空");
			
			StringBuffer notifyUrl = new StringBuffer();
			notifyUrl = notifyUrl.append(wgjUrl).append(noticeUrl).append(Constants.URL_SUFIX);
			// 微信预支付订单
			String nonceStr = WXPayUtil.generateWord(32);
			WXPrepayOrder prePay = new WXPrepayOrder();
			prePay.setAppid(appId);
			prePay.setBody(goodname);
			prePay.setNonce_str(nonceStr);
			prePay.setMch_id(mchId);
			prePay.setNotify_url(notifyUrl.toString());
			prePay.setOut_trade_no(orderNo);
			prePay.setSpbill_create_ip(spbillCreateIp);
			prePay.setTotal_fee(totalPrice.toString());
			prePay.setTrade_type("JSAPI");
			prePay.setOpenid(openId);
			// 获取签名
			String sign = _getUnifiedOrderSign(prePay);
			prePay.setSign(sign);
			// 获取预支付订单标识
			String paramxml = XmlHelper.objectToXMLCDATA(prePay);
			paramxml = paramxml.replaceAll("__", "_");
			String prepayId = null;
			HttpPost post = EnhancedHttpUtil.getPost(unifiedUrl, paramxml);
			String result = EnhancedHttpUtil.getString(post);
			PrepayOrderResult prepayOrderResult = XmlHelper.parseXmlCdata(URLDecoder.decode(result), PrepayOrderResult.class);
			
			// 以下字段在return_code为SUCCESS的时候有返回
			if (StringUtils.isNotBlank(prepayOrderResult.getReturn_code())&& prepayOrderResult.getReturn_code().equalsIgnoreCase("SUCCESS")) {
				if (StringUtils.isNotBlank(prepayOrderResult.getResult_code()) && prepayOrderResult.getResult_code().equalsIgnoreCase("SUCCESS")) {
					prepayId = prepayOrderResult.getPrepay_id();
				}
			} else {
				throw new Exception("【微信支付】获取的预支付订单失败：" + prepayOrderResult.getReturn_msg());
			}
			return prepayId;
		} catch (Exception e) {
			AssertUtil.isTrue(false, "微信下单失败","微信支付异常，请稍后重试");
			return null;
		}
	}
	
	/**
	 * 生成签名
	 * @Title: _getUnifiedOrderSign
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param prePay
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年3月16日 下午4:28:33
	 */
	private static String _getUnifiedOrderSign(WXPrepayOrder prePay) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("appid", prePay.getAppid());
		paramMap.put("mch_id", prePay.getMch_id());
		paramMap.put("nonce_str", prePay.getNonce_str());
		paramMap.put("body", prePay.getBody());
		paramMap.put("out_trade_no", prePay.getOut_trade_no());
		paramMap.put("total_fee", prePay.getTotal_fee());
		paramMap.put("spbill_create_ip", prePay.getSpbill_create_ip());
		paramMap.put("trade_type", prePay.getTrade_type());
		paramMap.put("notify_url", prePay.getNotify_url());
		
		if("NATIVE".equals(prePay.getTrade_type())){
			paramMap.put("product_id", prePay.getProduct_id());
		}
		if("JSAPI".equals(prePay.getTrade_type())){
			paramMap.put("openid", prePay.getOpenid());
		}
		// 商户支付秘钥
		String key = PropertiesUtil.getContexrtParam("WXPAY_SN");
		Assert.hasLength(key, "WXPAY_SN未配置");
		// 对参数排序
		String signparam =  SortParamUtil.sortParamForSign(paramMap);
		
		System.out.println(signparam);
		String sign = MD5Util.getInstance().encryptionMD5After(signparam, key).toUpperCase();
		return sign;
	}
	
	/**
	 * 验证微信订单是否支付成功
	 * @Title: _getWechatPayOrder
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param notice
	 * @return
	 * @return boolean
	 * @author phb
	 * @date 2015年3月17日 下午8:10:51
	 */
	public static boolean getWechatPayOrder(WXNotice notice) throws PaymentException{
		Boolean isSucc = false;
		if(!"SUCCESS".equalsIgnoreCase(notice.getResult_code())) {
			return isSucc;
		}
		String nonceStr = WXPayUtil.generateWord(32);
		String appId = PropertiesUtil.getContexrtParam("WXPAY_APP_ID");
		Assert.hasLength(appId, "WXPAY_APP_ID未配置");
		String mchId = PropertiesUtil.getContexrtParam("WXPAY_MCH_ID");
		Assert.hasLength(mchId, "WXPAY_MCH_ID未配置");
		// 查询微信支付订单参数
		ReqOrederParam reqOrederParam = new ReqOrederParam();
		reqOrederParam.setAppid(appId);
		reqOrederParam.setMch_id(mchId);
		reqOrederParam.setTransaction_id(notice.getTransaction_id());
		reqOrederParam.setNonce_str(nonceStr);
		reqOrederParam.setOut_trade_no(notice.getOut_trade_no());
		// 获取查询微信支付订单标识
		String sign = _getOrederParamSign(notice, nonceStr,appId,mchId);
		reqOrederParam.setSign(sign);
		// 查询微信支付订单
		String paramxml = XmlHelper.objectToXMLCDATA(reqOrederParam);
		
		paramxml = paramxml.replaceAll("__", "_");
		try {
			// 查询微信支付订单
			String queryUrl = PropertiesUtil.getContexrtParam("WXPAY_ORDER_QUERY");
			Assert.hasLength(queryUrl);
			HttpPost post = EnhancedHttpUtil.getPost(queryUrl, paramxml);
			String result = EnhancedHttpUtil.getString(post);
			WXOrder wxOrder = XmlHelper.parseXmlCdata(result, WXOrder.class);
			
			AssertUtil.isTrue(StringUtils.isNotBlank(wxOrder.getReturn_code()) && wxOrder.getReturn_code().equalsIgnoreCase("SUCCESS"), "查询订单状态失败");
			AssertUtil.isTrue(StringUtils.isNotBlank(wxOrder.getTrade_state()) && wxOrder.getTrade_state().equalsIgnoreCase("SUCCESS"), "支付状态为：" + wxOrder.getTrade_state());
			AssertUtil.isTrue(Integer.valueOf(notice.getTotal_fee()).intValue() == Integer.valueOf(wxOrder.getTotal_fee()).intValue(), "通知记录与查询到的订单记录交易金额不匹配");
			isSucc = true;
		} catch (Exception e) {
			throw new PaymentException(e);
		}
		return isSucc;
	}
	
	/**
	 * 获取查询微信支付订单标识
	 * @Title: _getOrederParamSign
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param notice
	 * @param nonceStr
	 * @return
	 * @return String
	 * @author phb
	 * @date 2015年3月17日 下午8:06:25
	 */
	private static String _getOrederParamSign(WXNotice notice, String nonceStr,String appId,String mchId) {
		// 商户支付秘钥
		String key = PropertiesUtil.getContexrtParam("WXPAY_SN");
		Assert.hasLength(key, "WXPAY_SN未配置");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("appid", appId);
		paramMap.put("mch_id", mchId);
		paramMap.put("nonce_str", nonceStr);
		paramMap.put("transaction_id", notice.getTransaction_id());
		paramMap.put("out_trade_no", notice.getOut_trade_no());
		// 对参数排序
		String signparam =  SortParamUtil.sortParamForSign(paramMap);
		System.out.println(signparam);
		String sign = MD5Util.getInstance().encryptionMD5After(signparam, key).toUpperCase();
		return sign;
	}
	
	 /**
		 * 请求微信统一下单接口
		 * @Title: _getWechatOrderPrepayId
		 * @Description: (这里用一句话描述这个方法的作用)
		 * @param orderNo
		 * @param goodname
		 * @param openId
		 * @param spbillCreateIp
		 * @param tradeType			微信支付类型	NATIVE:二维码支付;JSAPI:公众号支付
		 * @return
		 * @return String
		 * @author phb
		 * @date 2015年3月16日 下午4:27:07
		 */
	public static String getWechatOrderPrepayId(Long totalPrice,String orderNo, String goodname, String openId, String spbillCreateIp,String noticeUrl,String tradeType)throws WechatException {

		try {
			// 异步回调通知URL
			String wgjUrl = PropertiesUtil.getContexrtParam("WGJ_URL");
			Assert.hasLength(wgjUrl, "WGJ_URL未配置");
			String appId = PropertiesUtil.getContexrtParam("WXPAY_APP_ID");
			Assert.hasLength(appId, "WXPAY_APP_ID未配置");
			String mchId = PropertiesUtil.getContexrtParam("WXPAY_MCH_ID");
			Assert.hasLength(mchId, "WXPAY_MCH_ID未配置");
			String unifiedUrl = PropertiesUtil.getContexrtParam("WXPAY_UNIFIED_ORDER");
			Assert.hasLength(unifiedUrl, "WXPAY_UNIFIED_ORDER未配置");
			Assert.hasLength(noticeUrl, "noticeUrl为空");
			
			StringBuffer notifyUrl = new StringBuffer();
			notifyUrl = notifyUrl.append(wgjUrl).append(noticeUrl).append(Constants.URL_SUFIX);
			// 微信预支付订单
			String nonceStr = WXPayUtil.generateWord(32);
			WXPrepayOrder prePay = new WXPrepayOrder();
			prePay.setAppid(appId);
			prePay.setBody(goodname);
			prePay.setNonce_str(nonceStr);
			prePay.setMch_id(mchId);
			prePay.setNotify_url(notifyUrl.toString());
			prePay.setOut_trade_no(orderNo);
			prePay.setSpbill_create_ip(spbillCreateIp);
			prePay.setTotal_fee(totalPrice.toString());
//			prePay.setTrade_type("JSAPI");
			prePay.setTrade_type(tradeType);
//			prePay.setOpenid(openId);
			
			if("NATIVE".equals(tradeType)){
				prePay.setProduct_id(orderNo);
			}
			if("JSAPI".equals(tradeType)){
				prePay.setOpenid(openId);
			}
			
			// 获取签名
			String sign = _getUnifiedOrderSign(prePay);
			prePay.setSign(sign);
			// 获取预支付订单标识
			String paramxml = XmlHelper.objectToXMLCDATA(prePay);
			paramxml = paramxml.replaceAll("__", "_");
			String prepayId = null;
			//微信支付二维码地址
			String codeUrl = null;
			HttpPost post = EnhancedHttpUtil.getPost(unifiedUrl, paramxml);
			String result = EnhancedHttpUtil.getString(post);
			PrepayOrderResult prepayOrderResult = XmlHelper.parseXmlCdata(URLDecoder.decode(result), PrepayOrderResult.class);
			
			// 以下字段在return_code为SUCCESS的时候有返回
			if (StringUtils.isNotBlank(prepayOrderResult.getReturn_code())&& prepayOrderResult.getReturn_code().equalsIgnoreCase("SUCCESS")) {
				if (StringUtils.isNotBlank(prepayOrderResult.getResult_code()) && prepayOrderResult.getResult_code().equalsIgnoreCase("SUCCESS")) {
					prepayId = prepayOrderResult.getPrepay_id();
					codeUrl = prepayOrderResult.getCode_url();
				}
			} else {
				throw new Exception("【微信支付】获取的预支付订单失败：" + prepayOrderResult.getReturn_msg());
			}
			
			if("JSAPI".equals(tradeType)){
				return prepayId;
			}else if("NATIVE".equals(tradeType)){
				return codeUrl;
			}else{
				AssertUtil.isTrue(false, "微信支付类型未定义","微信支付异常，请稍后重试");
				return null;
			}
		} catch (Exception e) {
			AssertUtil.isTrue(false, "微信下单失败","微信支付异常，请稍后重试");
			return null;
		}
	}
}
