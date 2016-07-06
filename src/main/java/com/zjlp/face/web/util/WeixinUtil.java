package com.zjlp.face.web.util;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.zjlp.face.util.encryption.DigestUtils;
import com.zjlp.face.util.http.HttpClientUtils;
/**
 * 公众平台通用接口工具类 
 *
 * @author liuyq
 * @date 2013-08-09
 */

public class WeixinUtil {

	String token = "";
	
    /**
     * 分享到朋友圈
     * @Title: getSignature
     * @Description: (这里用一句话描述这个方法的作用)
     * @param:@param token
     * @param:@param noncestr
     * @param:@param timestamp
     * @param:@param url
     * @param:@return
     * @return String
     * @author phb
     * @date 2015年2月11日 上午10:38:23
     */
    public String getSignature(String token,String noncestr,String timestamp,String url){
    	String jsapi_ticket = "";
		try {
			String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
			String requestUrl = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", token);  
			String resultString = HttpClientUtils.getInstances().doGet(requestUrl, "UTF-8");
			JSONObject jsonObject = JSONObject.fromObject(resultString);
	        String ticket = jsonObject.getString("ticket");
	        String JSAPI_TICKET="jsapi_ticket=JSAPI_TICKET&noncestr=NONCESTR&timestamp=TIMESTAMP&url=URL";
	        jsapi_ticket=JSAPI_TICKET.replace("JSAPI_TICKET", ticket).replace("NONCESTR", noncestr).replace("TIMESTAMP", timestamp).replace("URL", url);   	
	        jsapi_ticket = DigestUtils.jzShaEncrypt(jsapi_ticket);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsapi_ticket;
    }
    
    public static void main(String[] args) {
    	String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		String resultString = "";
		try {
			resultString = HttpClientUtils.getInstances().doGet(JSAPI_TICKET_URL, "UTF-8");
			System.out.println(resultString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		JSONObject jsonObject = JSONObject.fromObject(resultString);
	}
    
	/**
	 * 微信授权链接
	 * @Title: _getWeChatUrl 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param url
	 * @return
	 * @date 2015年2月6日 下午1:11:18  
	 * @author liujia
	 */
	public String getWeChatUrl(String appid, String urlOrIp ,String url){
		StringBuffer weChatUrl = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
		weChatUrl.append(appid);
		weChatUrl.append("&redirect_uri=");
		weChatUrl.append(urlOrIp);
		weChatUrl.append("%2Fcard%2Fweixin%2Fopenid.htm&response_type=code&scope=snsapi_base&state=");
		weChatUrl.append(WeixinUtil.urlEnodeUTF8(url));
		weChatUrl.append("#wechat_redirect");
		System.out.println("授权地址:"+weChatUrl.toString());
		return weChatUrl.toString();
	}
	
	/**
	 * 微信重定向url判定
	 * @Title: getWeChatUrl 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param appId
	 * @param urlOrIp
	 * @param openId
	 * @param getWeChatUrl
	 * @param returnUrl
	 * @param ext
	 * @return
	 * @date 2015年2月6日 下午3:55:16  
	 * @author liujia
	 */

	public String getWeChatUrl(String appId,String urlOrIp, String openId, String masterId,String getWeChatUrl,
			String returnUrl, String ext) {
		if((null==masterId) || (null == openId)){	
			return "redirect:"+getWeChatUrl(appId, urlOrIp, getWeChatUrl);
		}else{
			
			return returnUrl+masterId+ext;
		}
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
    public static String urlEnodeUTF8(String str){
        String result = str;
        try {
            result = URLEncoder.encode(str,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 判断访问是否来自微信浏览器
     * @Title: isWechatBrowser 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param httpRequest
     * @return
     * @date 2015年5月7日 上午10:07:16  
     * @author ah
     */
    public static boolean isWechatBrowser(HttpServletRequest httpRequest) {
    	String ua = httpRequest.getHeader("user-agent").toLowerCase();
		boolean validation = false;
		if (ua.indexOf("micromessenger") > 0) {
	    	// 是微信浏览器
	        validation = true;
	    }
    	return validation;
    }

	/**
	 * @Title: _getWeChatCodeUrl
	 * @Description: (组装获取codeUrl)
	 * @param appId
	 * @param urlOrIp
	 * @param isBaseInfo true：全部信息 false：基本信息
	 * @param state
	 * @return
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月22日 下午9:57:15
	 */
	public static String getCodeUrl(String appId, String urlOrIp, boolean isBaseInfo, String state) {
		String getCodeRequest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
		getCodeRequest = getCodeRequest.replace("APPID", urlEnodeUTF8(appId));
		getCodeRequest = getCodeRequest.replace("REDIRECT_URI", urlEnodeUTF8(urlOrIp));
		getCodeRequest = getCodeRequest.replace("SCOPE", isBaseInfo ? "snsapi_userinfo" : "snsapi_base");
		getCodeRequest = getCodeRequest.replace("STATE", state);
		return getCodeRequest;
	}

	/**
	 * @Title: _getWeChatAccessToken
	 * @Description: (组装获取AccessTokenUrl)
	 * @param appid
	 * @param secret
	 * @param code
	 * @return
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月22日 下午9:58:39
	 */
	public static String getAccessToken(String appid, String secret, String code) {
		StringBuilder sb = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token");
		sb.append("?appid=").append(appid);
		sb.append("&secret=").append(secret);
		sb.append("&code=").append(code);
		sb.append("&grant_type=authorization_code");
		return sb.toString();
	}

	public static boolean isNotEmptyCode(HttpServletRequest request) {
		if (request != null) {
			// 微信返回
			String code = request.getParameter("code");
			// 请求标志位
			String state = request.getParameter("state");
			return StringUtils.isNotBlank(code) && StringUtils.isNotBlank(state);
		}
		return false;
	}
} 