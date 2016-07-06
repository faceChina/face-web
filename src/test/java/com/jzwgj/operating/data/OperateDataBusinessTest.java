package com.jzwgj.operating.data;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.web.server.trade.order.bussiness.OperateDataBusiness;
import com.zjlp.face.web.server.trade.order.producer.OperateDataProducer;
import com.zjlp.face.web.server.user.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = false, transactionManager = "jzTransactionManager")
// @TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,
// TransactionalTestExecutionListener.class })
@ActiveProfiles("dev")
public class OperateDataBusinessTest {

	@Autowired
	private OperateDataBusiness operateDataBusiness;
	
	@Autowired
	private OperateDataProducer operateDataProducer; 
	
//	@Test
	public void supplierRecivePriceTest(){
		Long price = operateDataBusiness.supplierRecivePrice(2L);
		System.err.println(price);
	}
	
//	@Test
	public void supplierPayCommissionTest(){
		Long price = operateDataBusiness.supplierPayCommission(2L);
		System.err.println(price);
	}
	
	
//	@Test
	public void supplierPayOrderCountTest(){
		Integer price = operateDataBusiness.supplierPayOrderCount(2L);
		System.err.println(price); //6
	}
	
	
//	@Test
	public void supplierConsumerCountTest(){
		Integer price = operateDataBusiness.supplierConsumerCount(2L);
		System.err.println(price); //2
	}
	
	
//	@Test
	public void supplierRecivePricesTest(){
		Map<String, Long> price = operateDataBusiness.supplierRecivePrices(2L);
		System.err.println(price);  //{2015-07-16=395800, 2015-07-21=51000, 2015-07-15=0, 2015-07-17=0, 2015-07-18=0, 2015-07-19=0, 2015-07-20=0}
	}
	
//	@Test
	public void supplierPayCommissionsTest(){
		Map<String, Long> price = operateDataBusiness.supplierPayCommissions(2L);
		System.err.println(price);  //{2015-07-16=109920, 2015-07-15=0, 2015-07-17=0, 2015-07-18=0, 2015-07-19=0, 2015-07-20=0, 2015-07-21=0}
	}
	
	@Test
	public void test(){
		User user = new User();
		user.setId(116L);
		Long amount = operateDataProducer.getFreezeAmount(user);
		System.out.println("----------------"+amount+"----------------");
	}
}
