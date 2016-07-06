package com.jzwgj.security;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class dao {
	
	@Autowired
	private PersistentTokenRepository testTokenRepository;
	
	@Test
	public void testTokenRepository(){
		try {
			PersistentRememberMeToken token = new PersistentRememberMeToken("123", "123", "123", new Date());
			testTokenRepository.createNewToken(token);
			
			testTokenRepository.removeUserTokens("1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
