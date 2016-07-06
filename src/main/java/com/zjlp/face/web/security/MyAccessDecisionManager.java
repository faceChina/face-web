package com.zjlp.face.web.security;

import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
/**
 * 自定义访问决策管理器
 * @ClassName: MyAccessDecisionManager 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年5月25日 下午4:23:09
 */
public class MyAccessDecisionManager implements AccessDecisionManager {

	private Logger _log = Logger.getLogger(this.getClass());
	
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes.isEmpty()) {
			return;
		}
		// 所请求的资源拥有的权限
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		while (iterator.hasNext()) {
			ConfigAttribute configAttribute = iterator.next();
			
			// 访问所请求资源所需要的权限
			String needPermission = configAttribute.getAttribute();
			if (_log.isDebugEnabled()) {
				_log.debug("访问所请求资源所需要的权限：" + needPermission);
			}
			// 用户所拥有的权限authentication
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (_log.isDebugEnabled()) {
					_log.debug("当前用户拥有权限：" + ga);
				}
				if (needPermission.equals(ga.getAuthority())) {
					return;
				}
			}
		}
		// 没有权限让我们去捕捉
		throw new AccessDeniedException(" 没有权限访问！");
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

}
