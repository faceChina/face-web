package com.zjlp.face.web.http.session;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.zjlp.face.web.security.bean.UserInfo;
import com.zjlp.face.web.security.bean.WgjUser;
/**
 * Session信息管理器
 * Spring Security 中获取登录用户信息类
 * @author dzq
 *
 */
public class UserSessionManager {
	
	/**
	 * 获取登录用户ID
	 */
	public static Long getSessionUserId(){
		UserInfo user = getSessionUserInfo();
		if(null == user){
			return null;
		}
		return user.getUserId();
	}
	/**
	 * 获取登录用户对象
	 */
	public static UserInfo getSessionUserInfo(){
		if(null == getLoginUser()){
			return null;
		}
		return getLoginUser().getUserInfo();
	}
	
	/**
	 * 获取登录Spring Security用户对象
	 */
	public static WgjUser getLoginUser() {  
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  
	    if (authentication != null) {  
	        Object principal = authentication.getPrincipal();  
	        if (principal instanceof WgjUser) {  
	            return (WgjUser) principal;  
	        } else {  
	            return null;  
	        }  
	    } else {  
	        return null;  
	    }  
	} 
}
