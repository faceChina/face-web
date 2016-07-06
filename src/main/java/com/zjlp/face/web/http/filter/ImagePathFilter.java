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

/**
 * 基础图片路径加载
 * @ClassName: ImagePathFilter 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年4月1日 下午2:29:08
 */
public class ImagePathFilter implements Filter {
//	private Logger log = Logger.getLogger(getClass());
//	private static final String ROOT_PICURL_NAME = "picUrl";
//	private static final String ROOT_PICURL = "ROOT_PICURL";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
//		HttpSession session = request.getSession(true);
//		String picUrl = (String) session.getAttribute(ROOT_PICURL_NAME);
//		if (StringUtils.isBlank(picUrl)) {
//			log.info("图片基础路径为空，开始加载。");
//			picUrl = PropertiesUtil.getContexrtParam(ROOT_PICURL);
//			log.info("读取配置文件，路径为："+picUrl);
//			session.setAttribute(ROOT_PICURL_NAME, picUrl);
//			log.info("图片基础路径加载完成。");
//		}
		arg2.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

}
