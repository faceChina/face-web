package com.zjlp.face.web.http.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zjlp.face.web.constants.ConstantsSession;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.user.shop.domain.Shop;

public class SubbranchNameFilter implements Filter {
	
	private SubbranchBusiness subbranchBusiness;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		String subbranchId = SubbranchFilter.getSubbranchId(req.getSession());
		
		//向session中添加分店名称
		//第一步:添加总店名称
		StringBuilder name = new StringBuilder();
		Shop shop = (Shop)req.getSession().getAttribute(ConstantsSession.SESSION_SHOP_KEY);
		if(null != shop){
			name.append(shop.getName());
		}
		if (null!=subbranchId && !"".equals(subbranchId)) {
				//向session中添加分店名称
				//第二步:添加分店名称,若无则添加分店手机号码
				if(org.apache.commons.lang.math.NumberUtils.isNumber(subbranchId)){
					Subbranch subbranch = subbranchBusiness.findSubbranchById(Long.parseLong(subbranchId));
					if(null != subbranch){
						String subbranchName = subbranch.getShopName();
						if(StringUtils.isBlank(subbranchName)){
							name.append("-")
								.append(subbranch.getUserCell());
						}else{
							name.append("-")
								.append(subbranchName);
						}
					}
				}
		}
		//向session中添加分店名称
		//第三步:将总店名称打进session
		req.getSession().setAttribute("subbranchName", name.toString());
		filterChain.doFilter(req, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		subbranchBusiness = wac.getBean(SubbranchBusiness.class);
	}
}