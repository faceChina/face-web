package com.zjlp.face.web.http.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.trade.payment.util.WXPayUtil;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.util.WeixinUtil;

/**
 * 微信端加载时获取用户与店铺公众号对应的shopOpenId
 * @ClassName: LoadingFilter 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author ah
 * @date 2015年4月10日 下午3:21:48
 */
public class LoadingFilter implements Filter {
	
	protected static final String DEFAULT_CHARACTER_ENCODING = "utf-8";

	protected final Log logger = LogFactory.getLog(getClass());

	private static final String METHOD_GET = "GET";
	
	private static final String MARK = "?";

	@Override
	public void destroy() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse) response; 
		httpRequest.setCharacterEncoding(DEFAULT_CHARACTER_ENCODING);
		
		// 从微信浏览器访问时，获取openid
		if(WeixinUtil.isWechatBrowser(httpRequest)) {
			String switchOpenId = PropertiesUtil.getContexrtParam("SWITCH_OPENID");
			// 获取openid的开关为1时，则在手机端能自动获取openid
			if("1".equals(switchOpenId)) {
				String uri = httpRequest.getRequestURI();
				// 获取当前店铺信息
				Shop shop = (Shop) httpRequest.getSession().getAttribute("shop");
				// 获取shopOpenId值
				String wgjurl = PropertiesUtil.getContexrtParam("WGJ_URL");
				String shopOpenId = "";
				if(null != shop) {
					shopOpenId = (String) httpRequest.getSession().getAttribute(shop.getNo()+"shopOpenId");
				}
				String method = httpRequest.getMethod();
				// shopOpenId为空时，获取shopOpenId
				if(null != shop && Constants.AUTHENTICATE_TYPE_CERTIFIED.equals(shop.getAuthenticate())) {
					if(StringUtils.isBlank(shopOpenId) && StringUtils.isNotBlank(shop.getAppId())) {
						if(METHOD_GET.equalsIgnoreCase(method)) {
							Map<String , String[]> params = new HashMap<String, String[]>();
							String appId = shop.getAppId();
							Assert.hasLength(wgjurl, "WGJ_URL未配置");
							StringBuffer setOpendIdUrl = new StringBuffer();
							setOpendIdUrl = setOpendIdUrl.append(wgjurl).append("/wechat/getOpenId").append(Constants.URL_SUFIX);
							params.putAll(request.getParameterMap()); 
							String param = null;
							StringBuffer paramBuffer = new StringBuffer();
							String oauth2Url = null; 
							if(params.size() > 0) {
								for (Entry<String, String[]> entry : params.entrySet()) {
									paramBuffer.append(entry.getKey()).append("=").append(entry.getValue()[0]).append("&");
								}
								param = paramBuffer.substring(0,paramBuffer.lastIndexOf("&"));
								oauth2Url = WXPayUtil.getoauth2Url(appId, setOpendIdUrl.toString(), wgjurl+uri+MARK+param);
							} else {
								oauth2Url = WXPayUtil.getoauth2Url(appId, setOpendIdUrl.toString(), wgjurl+uri);
							}
							httpResponse.sendRedirect(oauth2Url);
							return;
						}
					}
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
