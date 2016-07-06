package com.zjlp.face.web.http.cookies;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Cookie 操作类
 * @ClassName: Cookies 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年5月15日 下午9:00:35
 */
public class Cookies {
	
	
	/**
	 * 创建一个Cookies
	 * @Title: newInstants 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param res HttpServletResponse
	 * @param name 名称
	 * @param value 值
	 * @param path  作用域：默认为WebApp全局
	 * @param maxAge 生命周期时间
	 * @return
	 * @date 2015年5月15日 下午8:50:40  
	 * @author dzq
	 */
	public static Cookie newInstants(HttpServletResponse res,String name,String value){
		return newInstants(res, name, value, "/", Integer.MAX_VALUE);
	}
	public static Cookie newInstants(HttpServletResponse res,String name,String value,int maxAge){
		return newInstants(res, name, value, "/", maxAge);
	}
	public static Cookie newInstants(HttpServletResponse res,String name,String value,String path,int maxAge){
		 Cookie cookie = new Cookie(name,value);
	     cookie.setMaxAge(maxAge);
	     cookie.setPath(path);
	     res.addCookie(cookie);
	     return cookie;
	}
	
    /**
     * 添加一个cookie
     * @param res
     * @param name
     * @param value
     * @param maxAge
     */
    public static Cookie add(Cookie cookie,HttpServletResponse res,String name,String value,int maxAge) {
//    	if (null == cookie) {
    		cookie = newInstants(res, name, value, maxAge);
//		}else{
//		     cookie.setMaxAge(maxAge);
//		     cookie.setPath("/");
//		     res.addCookie(cookie);
//		}
    	return cookie;
    }
	
	
    /**
     * 添加一个cookie
     * @param res
     * @param name
     * @param value
     * @param maxAge
     */
    public static void addNew(HttpServletResponse res,String name,String value,int maxAge) {
        Cookie cookie = new Cookie(name,value);
        if(maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        cookie.setPath("/");
        res.addCookie(cookie);
    }
    public static void addNew(HttpServletResponse res,String name,String value) {
    	addNew(res,name,value,3600*7);
    }
    /**
     * 获取cookie的值
     * @param req
     * @param name
     * @return
     */
    public static String getName(HttpServletRequest req,String name) {
        Cookie cookie = get(req,name);
        String cookieVal = (null == cookie) ? null : cookie.getValue();
        return cookieVal;
    }
    public static Cookie get(HttpServletRequest req,String name) {
        Map<String,Cookie> cookieMap = _readCookieMap(req);
        if(cookieMap.containsKey(name)) {
            return (Cookie)cookieMap.get(name);
        } else {
            return null;
        }
    }
    /**
     * 清除cookie
     * @param req
     * @param res
     * @param name
     */
    public static void remove(HttpServletRequest req,HttpServletResponse res,String name) {
        String cookieName = getName(req,name);
        if(null != cookieName) {
            Cookie cookie = new Cookie(cookieName,null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            res.addCookie(cookie);
        }
    }
    /**
     * 清除所有cookie
     * @param req
     * @param res
     */
    public static void clear(HttpServletRequest req,HttpServletResponse res) {
        Cookie[] cookies = req.getCookies();
        for(int i = 0,len = cookies.length; i < len; i++) {
            Cookie cookie = new Cookie(cookies[i].getName(),null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            res.addCookie(cookie);
        }
    }
    private static Map<String,Cookie> _readCookieMap(HttpServletRequest req) {
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = req.getCookies();
        if(null != cookies) {
            for(Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return cookieMap;
    }
}
