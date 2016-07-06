package com.jzwgj.component.daosupport.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.jzwgj.component.daosupport.dao.UserDao;
import com.jzwgj.component.daosupport.domain.User;
import com.jzwgj.component.daosupport.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class DaoSupportTest {

//	@Autowired(required=false)
	private UserDao userDao;
//	@Autowired
	private UserService userService;
	
//	@Test
	public void addTest() {
		User user = new User();
		user.setName("lys");
		user.setAge(21);
		Date date = new Date();
		user.setCreateTime(date);
		user.setUpdateTime(date);
		userDao.insert(user);
	}
	
	@Test
	public void addAndEditTest(){
		userService.addAndEdit();
	}
}
