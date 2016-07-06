package com.zjlp.face.web.http.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.zjlp.face.util.file.PropertiesUtil;

@Component
public class ImagePathListener implements ServletContextListener {

	private Logger log = Logger.getLogger(ImagePathListener.class);
	private static final String ROOT_PICURL_NAME = "picUrl";
	private static final String ROOT_PICURL = "ROOT_PICURL";
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		//获得全局变量
        ServletContext servletContext = servletContextEvent.getServletContext();
        //设置全局变量属性
        log.debug("图片基础路径为空，开始加载。");
		String picUrl = PropertiesUtil.getContexrtParam(ROOT_PICURL);
		log.debug("读取配置文件，路径为："+picUrl);
        servletContext.setAttribute(ROOT_PICURL_NAME, picUrl);
        log.debug("图片基础路径加载完成。");
	}

	
}
