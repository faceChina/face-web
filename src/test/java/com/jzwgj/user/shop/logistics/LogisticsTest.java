package com.jzwgj.user.shop.logistics;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.service.LogisticsService;
import com.zjlp.face.web.server.user.user.business.UserAppBusiness;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
// @TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,
// TransactionalTestExecutionListener.class })
@ActiveProfiles("dev")
//@Transactional
public class LogisticsTest {

	@Autowired
	private LogisticsService logisticsService;
	@Autowired
	private UserAppBusiness userAppBusiness;
	
//	@Test
	public void test() {
		List<PickUpStore> list = logisticsService.findPickUpStoreList("HZJZ0001140875114K2K");
		System.out.println(list.size());
	}
	
//	@Test
	public void test2() {
		List<ShopDistribution> list = logisticsService.findShopDistributionList("HZJZ0001140875114K2K");
		System.out.println(list.size());
	}
	
	@Test
	public void test3() {
		userAppBusiness.activateShopLock("18888888888");
	}
}
