package com.jzwgj.component.daosupport.dao;

import com.jzwgj.component.daosupport.domain.User;
import com.zjlp.face.web.component.daosupport.GenericDao;
import com.zjlp.face.web.component.daosupport.Param;

public interface UserDao extends GenericDao {

	void insert(@Param User user);
	
	User selectByPrimaryKey(@Param("id") Long id);
}
