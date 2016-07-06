package com.jzwgj.transact.account.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.account.exception.AccountException;
import com.zjlp.face.web.server.trade.account.producer.AccountProducer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
// @TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,
// TransactionalTestExecutionListener.class })
@ActiveProfiles("dev")
//@Transactional
public class AccountProducerTest {

	@Autowired
	private AccountProducer accountProducer;
	
//	@Test
	public void addShopAccount() {
		accountProducer.addShopAccount("HZJZ0001140724232mHd", "12000114735", "null&1407304vA51", null, null);
	}
	
	@Test
	public void addUserAccount() {
		try {
			accountProducer.addUserAccount(1L, "12000114735", null, null);
		} catch (AccountException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getExternalMsg());
		}
	}
}
