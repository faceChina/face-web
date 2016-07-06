package com.zjlp.face.web.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.zjlp.face.web.security.bean.UserInfo;
import com.zjlp.face.web.security.bean.WgjUser;
import com.zjlp.face.web.server.user.user.business.UserBusiness;

public class MyUserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserBusiness userBussiness;
	
	// 登录验证
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		UserInfo userInfo = userBussiness.getUserInfo(username);
		
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(userInfo.getRoleList());

		// 封装成spring security的user
		WgjUser userdetail = new WgjUser(userInfo.getLoginAccount(),
				userInfo.getPasswd(), true, true, true, true, grantedAuths // 用户的权限
				, userInfo);
		
		return userdetail;
	}



	// 取得用户的权限
	private Set<GrantedAuthority> obtionGrantedAuthorities(List<String> roleIdList) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		for (String roleCode : roleIdList) {
 			authSet.add(new SimpleGrantedAuthority(roleCode));//ROLE_ADMIN
		}
		return authSet;
	}
	

}
