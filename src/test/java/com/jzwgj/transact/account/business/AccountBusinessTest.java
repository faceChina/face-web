package com.jzwgj.transact.account.business;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.account.service.AccountService;
import com.zjlp.face.util.encryption.DigestUtils;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.trade.account.producer.AccountProducer;
import com.zjlp.face.web.server.user.user.dao.UserDao;
import com.zjlp.face.web.server.user.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
// @TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,
// TransactionalTestExecutionListener.class })
@ActiveProfiles("dev")
//@Transactional
public class AccountBusinessTest {

	@Autowired
	private AccountBusiness accountBusiness;
	@Autowired(required=false)
	private AccountService accountService;
	@Autowired(required=false)
	private UserDao userDao;
	@Autowired
	private AccountProducer accountProducer;

//	@Test
	public void completInformationTest() {

		User user = new User();
		user.setToken("TOKEN1234123");
		user.setLoginAccount("18655015835");
		user.setCell("18655015835");
		user.setStatus(Constants.VALID);
		user.setPasswd("123456");
		user.setRealType(1);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		Long userId = userDao.add(user);
		User u1 = userDao.getById(userId);
		Assert.assertNotNull(u1);
		System.out.println(u1);
		Long accountId = accountService.addAccount(userId, "18655015835", null, null);
		Account a1 = accountService.getAccountById(accountId);
		Assert.assertNotNull(a1);
		System.out.println(a1);

		accountBusiness.completInformation(userId, "123456", "123456", "林亦生",
				"362323198810237511");
		
		u1 = userDao.getById(userId);
		Assert.assertNotNull(u1);
		Assert.assertEquals("林亦生", u1.getContacts());
		Assert.assertEquals("362323198810237511", u1.getIdentity());
		
		a1 = accountService.getAccountById(accountId);
		Assert.assertNotNull(a1);
		Assert.assertEquals(DigestUtils.jzShaEncrypt("123456"), a1.getPaymentCode());
	}
	
	@Test
	public void addAccountTest() {
		accountProducer.addUserAccount(108L, "18655015835", null, null);
	}
}
