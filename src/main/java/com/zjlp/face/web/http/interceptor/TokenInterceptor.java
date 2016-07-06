package com.zjlp.face.web.http.interceptor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.http.HttpClientUtils;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.http.filter.WechatFilter;
import com.zjlp.face.web.http.session.UserSessionManager;
import com.zjlp.face.web.security.bean.UserInfo;
import com.zjlp.face.web.security.bean.WgjUser;
import com.zjlp.face.web.security.util.CasLoginHttpUtil;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.WeixinUtil;

public class TokenInterceptor extends HandlerInterceptorAdapter {

	private Logger _log = Logger.getLogger(this.getClass());

	private static final String TOKEN = "validateToken";

	private static final String CHARACTER_ENCODING = "utf-8";

	private static final String OPEN_ID = "openid";

	public static final String STATE = "isFromWechat";
	
    private RequestCache requestCache = new HttpSessionRequestCache();

	@Autowired
	private UserBusiness userBusiness;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			String wechatLoginSwitch = PropertiesUtil.getContexrtParam("WECHAT_LOGIN_SWITCH");
//			WgjUser user = UserSessionManager.getLoginUser();
//			if (user != null) {
//				_log.info("7==========>从session中获取user= " + user.getUsername());
//			} else {
//				_log.info("8==========>用户未登录 ");
//			}
			if (WechatFilter.SWITCH.equals(wechatLoginSwitch)) {
				// 微信返回
				String code = request.getParameter("code");
				_log.info("9=============>拦截器获取CODE=." + code);
				// 请求标志位
				String state = request.getParameter("state");
				_log.info("10=============>拦截器获取STATE." + state);
				if (!this._checkIsLogin(request, response) && StringUtils.isNotBlank(code) && StringUtils.isNotBlank(state)) {
					_log.info("11=============>微信注册用户开始....");
					this._wechatRegister(request, response);
					_log.info("12=============>微信注册用户结束.");
				}
			}
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Token annotation = method.getAnnotation(Token.class);
			if (annotation != null) {
				boolean needSaveSession = annotation.save();
				if (needSaveSession) {
					String uuid = UUID.randomUUID().toString();
					request.getSession(false).setAttribute(TOKEN, uuid);
				}
				boolean needRemoveSession = annotation.remove();
				if (needRemoveSession) {
					if (isRepeatSubmit(request)) {
						request.setAttribute("message", "请不要重复提交");
						request.getRequestDispatcher("/jsp/wap/common/error404.jsp").forward(request, response);
						return false;
					}
					request.getSession(false).removeAttribute(TOKEN);
				}
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}


	/**
	 * @Title: _wechatRegister
	 * @Description: (从微信点击进入为用户注册用户)
	 * @param request
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月23日 下午2:54:40
	 */
	private boolean _wechatRegister(HttpServletRequest request, HttpServletResponse response) {
		AssertUtil.notNull(request, "拦截请求为空！");
		// 微信返回
		String code = request.getParameter("code");
		// 请求标志位
		String state = request.getParameter("state");
		if (StringUtils.isNotBlank(code) && STATE.equals(state)) {
			_log.info("微信注册用户,code=" + code + "state=" + state);
			String appIdTest = PropertiesUtil.getContexrtParam("WECAHT_APP_ID");
			AssertUtil.hasLength(appIdTest, "wechatDevInfoConfig未配置字段appIdTest");
			_log.info("AppId=[" + appIdTest + "]");
			String appSecretTest = PropertiesUtil.getContexrtParam("WECAHT_APP_SECRET");
			AssertUtil.hasLength(appSecretTest, "wechatDevInfoConfig未配置字段appSecretTest");
			_log.info("appSecret=[" + appSecretTest + "]");
			String accessTokenUrl = WeixinUtil.getAccessToken(appIdTest, appSecretTest, code);
			AssertUtil.notNull(accessTokenUrl, "组建accessTokenUrl为空！");
			_log.info("accessTokenUrl=[" + accessTokenUrl + "]");
			String getOpenIdresult;
			try {
				getOpenIdresult = HttpClientUtils.getInstances().doGet(accessTokenUrl, CHARACTER_ENCODING);
				AssertUtil.notNull(getOpenIdresult, "获取getOpenIdresult为空！");
				_log.info("getOpenIdresult=[" + getOpenIdresult + "]");
				JSONObject json = JSONObject.fromObject(getOpenIdresult);
				AssertUtil.notNull(json, "转换OpenId为json出错！");
				_log.info("13======用户微信息为json=[" + json + "]");
				if (json.containsKey(OPEN_ID)) {
					String openId = json.optString(OPEN_ID);
					AssertUtil.notNull(openId, "openId为空");
					User existUser = this.userBusiness.getWechatUserByOpenId(openId);
//					User existUser = this.userBusiness.getUserByOpenId(openId);
					if (existUser == null) {
						this.userBusiness.wechatRegisterUser(openId);
						_log.info("微信注册用户成功,opneId = [" + openId + "]");
					}
					_log.info("14=============>微信自动登陆开始...");
					this._wechatAutoLogin(request, response, openId);
					_log.info("15=============>微信自动登陆结束");
				} else {
					_log.info("获取微信用户信息失败,code可能已经失效!" + json);
				}
			} catch (Exception e) {
				_log.error("微信注册或者登陆失败", e);
			}
		}
		return true;
	}

	/**
	 * @Title: _wechatAutoLogin
	 * @Description: (微信自动登陆)
	 * @param request
	 * @param response
	 * @param openId
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月24日 上午10:33:31
	 */
	private void _wechatAutoLogin(HttpServletRequest request, HttpServletResponse response, String userName) {
		try {
			/** CAS登录URL */
			String casUrl = PropertiesUtil.getContexrtParam("CAS.URL");
			String casLoginUrl = casUrl + "/login";
			/** CAS返回确认URL */
			String casHomeUrl = PropertiesUtil.getContexrtParam("SYSTEM.HOME.URL");
			String service = casHomeUrl + "/j_spring_cas_security_check";
			String jsonInfo = CasLoginHttpUtil.login(casLoginUrl, service, userName, Constants.WECHAT_PASSWORD);
			_log.info("=====>自动登陆返回信息为jsonInfo=" + jsonInfo);
			JSONObject jsonObject = JSONObject.fromObject(jsonInfo);
			if (null != jsonObject && jsonObject.get("success").equals(true)) {
				String backUrl = (String) jsonObject.get("backUrl");
				String forwardUri = backUrl.replace(PropertiesUtil.getContexrtParam("SYSTEM.HOME.URL"), "");
				if (!forwardUri.startsWith("/")) {
					forwardUri = "/" + forwardUri;
				}
				String ticketId = (String) jsonObject.get("ticketId");
				String serviceTicket = backUrl.substring(backUrl.indexOf("ticket") + "ticket".length() + 1, backUrl.length());
				_log.info("ticketId:" + ticketId + ",serviceTicket:" + serviceTicket + ",backUrl = " + backUrl);
				request.setAttribute("ticketId", ticketId);
				request.setAttribute("userOpenId", userName);// 登陆信息放入Session
				String uri = request.getRequestURI();
				_log.info("16=====登陆重定向==uri=" + uri);
				requestCache.saveRequest(request, response);
				request.getRequestDispatcher(forwardUri).forward(request, response);
				return;
			} else {
				_log.error("微信自动登录失败:" + jsonObject.get("errmsg"));
			}
		} catch (Exception e) {
			_log.error("微信自动登陆失败,openId=[" + userName + "]", e);
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
		_log.info("微信处理,用户已经登陆.");
		return true;
	}

	private boolean isRepeatSubmit(HttpServletRequest request) {
		String serverToken = (String) request.getSession(false).getAttribute(TOKEN);
		if (serverToken == null) {
			_log.info("当前是重复提交:" + request.getRequestURL().toString());
			return true;
		}
		String clientToken = (String) request.getParameter(TOKEN);
		if (clientToken == null) {
			_log.info("客户端没有拿到TOKEN:" + request.getRequestURL().toString());
			return true;
		}
		if (!serverToken.equals(clientToken)) {
			return true;
		}
		return false;
	}

}