package com.zjlp.face.web.security.bean;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class WgjUser extends org.springframework.security.core.userdetails.User {

	public WgjUser(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, UserInfo userInfo) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		this.userInfo = userInfo;
	}

	private static final long serialVersionUID = 4278418666981276003L;

	private UserInfo userInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
}
