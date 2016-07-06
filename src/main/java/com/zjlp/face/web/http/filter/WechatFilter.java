package com.zjlp.face.web.http.filter;

import java.io.IOException;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.http.interceptor.TokenInterceptor;
import com.zjlp.face.web.http.session.UserSessionManager;
import com.zjlp.face.web.security.bean.UserInfo;
import com.zjlp.face.web.security.bean.WgjUser;
import com.zjlp.face.web.security.cas.userdetails.TgtCookieRedirectToCas;
import com.zjlp.face.web.util.WeixinUtil;

public class WechatFilter implements Filter {

	protected static final String DEFAULT_CHARACTER_ENCODING = "utf-8";

	protected final Log logger = LogFactory.getLog(getClass());

	public static final String SWITCH = "1";//1 turn on, otherwise turn off

	protected static final String SWICH_TO_FACE = "1";

	protected static final String SWICH_TO_WECAHT = "2";

	@Autowired(required = false)
	private TgtCookieRedirectToCas tgtCookieRedirectToCas;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpresponse = (HttpServletResponse) response;
		httpRequest.setCharacterEncoding(DEFAULT_CHARACTER_ENCODING);
		if (WeixinUtil.isWechatBrowser(httpRequest) && !this._checkIsLogin(httpRequest, httpresponse)) {//
			String wechatLoginSwitch = PropertiesUtil.getContexrtParam("WECHAT_LOGIN_SWITCH");
			if (SWITCH.equals(wechatLoginSwitch)) {
				logger.info("1===========>从QQ浏览器进入,微信操作开始...");
				String code = request.getParameter("code");
				logger.info("2===========>从QQ浏览器进入,code=" + code);
				String state = request.getParameter("state");
				logger.info("3===========>从QQ浏览器进入,state" + state);
				String isWechatSwich = request.getParameter("isWechatSwich");
				logger.info("4==========>isWechatSwich=" + isWechatSwich);
//				if (SWICH_TO_WECAHT.equals(isWechatSwich)) {
//					// 微信切换到刷脸，清除刷脸登陆
//					WgjUser user = UserSessionManager.getLoginUser();
//					if (user != null) {
//						logger.info("===========用户存在...退出登陆开始...");
//					}
//					String host = PropertiesUtil.getContexrtParam("WGJ_URL");
//					logger.info(" 微信切换到刷脸，清除刷脸登陆host = " + host);
//					// httpresponse.sendRedirect(host +
//					// "/j_spring_cas_security_logout");
//					MyWechatSimpleUrlLogoutSuccessHandler myWechatSimpleUrlLogoutSuccessHandler = new MyWechatSimpleUrlLogoutSuccessHandler();
//					myWechatSimpleUrlLogoutSuccessHandler.setCasLogout(true);
//					String casUrl = PropertiesUtil.getContexrtParam("CAS.URL");
//					logger.info("===========casUrl" + casUrl);
//					myWechatSimpleUrlLogoutSuccessHandler.setCasLogoutUrl(casUrl + "/logout");
//					myWechatSimpleUrlLogoutSuccessHandler.setRedirectUrl(httpRequest.getRequestURI());
//					MyWechatLogout myWechatLogout = new MyWechatLogout(myWechatSimpleUrlLogoutSuccessHandler,new SecurityContextLogoutHandler());
//					myWechatLogout.logout(httpRequest, httpresponse, chain);
//					if (user == null) {
//						logger.info("===========用户退出登陆成功！");
//					}
//				}
				// 首次微信进入或者是从微信切到刷脸登陆过滤或者是从刷脸切回微信
				if ((StringUtils.isBlank(code) && StringUtils.isBlank(state) && !SWICH_TO_FACE.equals(isWechatSwich)) /*|| SWICH_TO_WECAHT.equals(isWechatSwich)*/) {
					logger.info("5==========>CODE为空且STATE为空.");
//					if (SWICH_TO_WECAHT.equals(isWechatSwich)) {
//						// APP访问获取Cookie重定向至CAS
//						Cookie tgtCookie = tgtCookieRedirectToCas.getTgtCookie(httpRequest);
//						logger.info("重定向到CAS,tgtCookie=" + tgtCookie.toString());
//						boolean isRedirectSuccess = tgtCookieRedirectToCas.redirectToCas(httpRequest, httpresponse, tgtCookie);
//						if (isRedirectSuccess) {
//							return;
//						}
//					}
					logger.info("5==========>CODE为空且STATE为空.");
					this._wechatAction(request, response, chain);
				}
				logger.info("6==========>从QQ浏览器进入,微信操作结束.");
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void destroy() {

	}

	/**
	 * @Title: _wechatAction
	 * @Description: (判断用户是都登陆,未登录则使用微信重定向)
	 * @param req
	 * @param rsp
	 * @param filterChain
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月22日 上午9:59:23
	 */
	private void _wechatAction(ServletRequest req, ServletResponse rsp, FilterChain filterChain) {
		try {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) rsp;
			// 编码
			request.setCharacterEncoding(DEFAULT_CHARACTER_ENCODING);
			// 判断用户是否登录
			if (this._checkIsLogin(request, response)) {
				logger.info("用户已经登陆,微信无需获取code.");
			} else {
				this._wechatRequst(request, response);// 微信请求code
			}
			return;
		} catch (Exception e) {
			logger.error("微信处理失败", e);
		}
	}

	/**
	 * @Title: _checkIsLogin
	 * @Description: (判断用户是都登陆)
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 * @return boolean 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月22日 下午9:59:26
	 */
	private boolean _checkIsLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		WgjUser wgjUser = UserSessionManager.getLoginUser();
		if (null == wgjUser) {
			return false;
		}
		UserInfo userInfo = wgjUser.getUserInfo();
		if (null == userInfo) {
			userInfo = new UserInfo();
		}
		if ("".equals(userInfo.getLoginAccount()) || null == userInfo.getLoginAccount()) {
			return false;
		}
		logger.info("微信处理,用户已经登陆.");
		return true;
	}

	/**
	 * @Title: _wechatLogin
	 * @Description: (微信重定向)
	 * @param request
	 * @param response
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月22日 下午10:00:05
	 */
	private void _wechatRequst(HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info("微信获取code开始...");
			// 微信公众号appId
			String appId = PropertiesUtil.getContexrtParam("WECAHT_APP_ID");
			AssertUtil.hasLength(appId, "wechatDevInfoConfig未配置字段appIdTest");
			logger.info("AppId=[" + appId + "]");
			// 官网地址
			String host = PropertiesUtil.getContexrtParam("WGJ_URL");
			AssertUtil.hasLength(host, "wechatDevInfoConfig未配置字段host");
			logger.info("Host=[" + host + "]");
			// 请求相对地址
			String suffix = request.getRequestURI();
			AssertUtil.hasLength(suffix, "request.getRequestURI()为空");
			logger.info("Suffix=[" + suffix + "]");
			// 完整URL
			String url = host + suffix;
			// 处理符合微信的url
			String authUrl = WeixinUtil.getCodeUrl(appId, url, false, TokenInterceptor.STATE);
			AssertUtil.hasLength(authUrl, "authUrl为空");
			logger.info("AuthUrl=[" + authUrl + "]");
			// 微信不能使用请求转发获取code
			// 必须重定向链接code
			logger.info("微信获取code重定向链接开始...");
			response.sendRedirect(authUrl);
			logger.info("微信获取code重定向链接结束.");
			return;
		} catch (Exception e) {
			logger.error("微信登陆失败", e);
		}
	}

}
