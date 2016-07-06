package com.zjlp.face.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.zjlp.face.web.server.user.security.dao.PermissionDao;
import com.zjlp.face.web.server.user.security.domain.Permission;

//1 加载资源与权限的对应关系
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	private Logger _log = Logger.getLogger(this.getClass());
	
	// 加载所有资源与权限的关系
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	
	// 由spring调用
	public MySecurityMetadataSource(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
		loadResourceDefine();
	}
	@Autowired
	private PermissionDao permissionDao;
	
	public PermissionDao getPermissionDao() {
		return permissionDao;
	}

	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}

	// 返回所请求资源所需要的权限
	public Collection<ConfigAttribute> getAttributes(Object object)	throws IllegalArgumentException {
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		if (_log.isDebugEnabled()) {
			_log.debug("requestUrl is " + requestUrl);
		}
		if (resourceMap == null) {
			loadResourceDefine();
		}
		Set<String> urlRegularSet= resourceMap.keySet();
		Collection<ConfigAttribute> result = new ArrayList<ConfigAttribute>();
		for(String urlRegular : urlRegularSet){
			Pattern p = Pattern.compile(urlRegular);
			Matcher m = p.matcher(requestUrl);
			if(m.matches()){
				result.addAll(resourceMap.get(urlRegular));
			} 
		}
		return result;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	private void loadResourceDefine() {
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<Permission> resources = permissionDao.findResource();
			for (Permission resource : resources) {
			//	_log.info("加载访问URL资源路径:" + resource.getResourceUrl());
				//Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				Collection<ConfigAttribute> configAttributes = resourceMap.get(resource.getUrl());
				if(configAttributes==null){
					configAttributes = new ArrayList<ConfigAttribute>();
				}		
				// TODO:通过资源名称来表示具体的权限 注意：必须"ROLE_"开头
				// 关联代码：applicationContext-security.xml
				// 关联代码：com.huaxin.security.MyUserDetailServiceImpl#obtionGrantedAuthorities
				
				ConfigAttribute configAttribute = new SecurityConfig(resource.getCode());
				configAttributes.add(configAttribute);
				resourceMap.put(resource.getUrl(), configAttributes);
			}
/*			for(String key : resourceMap.keySet()){
				Collection<ConfigAttribute> configAttributes = resourceMap.get(key);
				for(ConfigAttribute configAttribute : configAttributes){
					_log.info("当前资源" + configAttribute.getAttribute());
				}
			}*/
		}
	}

}
