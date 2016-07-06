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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.ConstantsLogin;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;

public class SubbranchFilter implements Filter {

	public static final String SUBBRANCH_SESSION="SUBBRANCH_SESSION";
	private static final String SPLIT = "@";
	private SubbranchBusiness subbranchBusiness;
	//店铺前缀
	private static final String SHOP_PRE = "HZJZ";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		//初始化数据
		HttpServletRequest req = (HttpServletRequest) request;
		if (!isIgnoreNoShopPath(req)) {
			this.excute(req);
		}
		//链条继续，不打扰
		filterChain.doFilter(req, (HttpServletResponse) response);
	}

	//是否忽略进行过滤
	private boolean isIgnoreNoShopPath(HttpServletRequest req) {
		String shopNo = this.getPathShopNo(req.getServletPath());
		if (StringUtils.isBlank(shopNo)) {
			return true;
		}
		if (!shopNo.startsWith(SHOP_PRE)) {
			return true;
		}
		return false;
	}

	//执行
	private void excute(HttpServletRequest req) {
		String subbranchIdStr = req.getParameter("subbranchId");
		String shopNo = this.getPathShopNo(req.getServletPath());
		HttpSession session = req.getSession();
		//键值
		String key = getKey(session);
		if (!StringUtils.isBlank(subbranchIdStr)) {
			//清空session
			this.cleanSession(session, key);
			//验证此代理是否有效
			boolean isvalid = this.validate(shopNo, subbranchIdStr);
			if (isvalid) {
				this.setSession(session, shopNo, subbranchIdStr, key);
			}
		} else {
			//取值
			String value = this.getSessionVal(session, key);
			//验证
			if (null != value) {
				String[] array = value.split(SPLIT);
				boolean isvalid = this.validate(array[0], shopNo, array[1]);
				//如果不合法，清空session
				if (!isvalid) {
					this.cleanSession(session, key);
				}
			}
		}
	}

	private boolean validate(String shopNo, String subbranchIdStr) {
		if (null == subbranchIdStr || !StringUtils.isNumeric(subbranchIdStr)) {
			return false;
		}
		Long subbranchId = Long.valueOf(subbranchIdStr);
		Subbranch subbranch = subbranchBusiness.findSubbranchById(subbranchId);
		if (null == subbranch
				|| Constants.UNVALID.equals(subbranch.getStatus())) {
			return false;
		}
		if (!shopNo.equals(subbranch.getSupplierShopNo())) {
			return false;
		}
		return true;
	}
	
	private boolean validate(String sessionShopNo, String shopNo, String subbranchIdStr) {
		if (!shopNo.equals(sessionShopNo)) {
			return false;
		}
		return this.validate(shopNo, subbranchIdStr);
	}
	
	private String getPathShopNo(String path){
		String[] bbb = path.split("/");
		for(int i=0;i<bbb.length;i++){
			if(bbb[i] == ConstantsLogin.WAP_URL_PREFIX_TAG|| bbb[i].equals(ConstantsLogin.WAP_URL_PREFIX_TAG)){
				return bbb[i+1];
			}
		}
		return "";
	}

	private void cleanSession(HttpSession session, String key) {
		session.removeAttribute(key);
	}

	private void setSession(HttpSession session, String shopNo,
			String subbranchIdStr, String key) {
		session.setAttribute(key, 
				new StringBuilder(shopNo).append(SPLIT).append(subbranchIdStr).toString());
	}
	
	private String getSessionVal(HttpSession session, String key) {
		Object obj = session.getAttribute(key);
		return null == obj ? null : (String)obj;
	}
	
	private static String getKey(HttpSession session) {
		return new StringBuilder(SUBBRANCH_SESSION).append(SPLIT)
				.append(session.getId()).toString();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(filterConfig.getServletContext());
		subbranchBusiness = wac.getBean(SubbranchBusiness.class);
	}
	
	public static String getSubbranchId(HttpSession httpSession){
		Object obj = httpSession.getAttribute(getKey(httpSession));
		return null == obj ? null : ((String)obj).split(SPLIT)[1];
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	public static void main(String[] args) {
		System.out.println(StringUtils.isNumeric("1"));
	}
}