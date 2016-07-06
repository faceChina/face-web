package com.zjlp.face.web.util;
import javax.servlet.http.HttpServletRequest;

public class ShualianUtil {
    
    /**
     * 判断访问是否来自刷脸App
     * @param httpRequest
     * @return
     */
    public static boolean isShualianBrowser(HttpServletRequest httpRequest) {
    	String ua = httpRequest.getHeader("user-agent").toLowerCase();
		boolean validation = false;
		//刷脸浏览器的UserAgent定义规则为 Shualian/{Android or iOS}/{versonName}
		//例如：Mozilla/5.0 (Linux; Android 5.0.1; MX5 Build/LRX22C) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/40.0.2214.114 Mobile Safari/537.36; Shualian/Android/4.3.2
		if(ua.toLowerCase().indexOf("shualian/android")>0 || ua.toLowerCase().indexOf("shualian/ios")>0) {
	    	//是刷脸浏览器
	        validation = true;
	    }
    	return validation;
    }
} 