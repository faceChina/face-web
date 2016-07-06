package com.jzwgj.component.daosupport.service;

import com.jzwgj.component.daosupport.domain.User;

public interface UserService {

	void addAndEdit();
	
	User getUserById(Long id);
}
