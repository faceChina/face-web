//package com.jzwgj.server.operation.card.service;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("/spring-beans.xml")
//@Transactional
//@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
//public class IPraiseRecodeServiceTest {
//	@Autowired
//	private IPraiseRecodeService iPraiseRecodeService;
//
//	
//	public  IPraiseRecode iPraiseRecode = new IPraiseRecode();
//	
//	@Before
//	public void into(){
//		iPraiseRecode.setLpOpenId("1234");
//		iPraiseRecode.setIcardId(1l);
//
//		
//	}
//	
//	@Test
//	public void Test(){
//		iPraiseRecodeService.add(iPraiseRecode);
//		Long id = iPraiseRecode.getId();
//		IPraiseRecode iPraiseRecode2 = iPraiseRecodeService.get(id);
//		assertEquals(iPraiseRecode2.getLpOpenId(),"1234");
//
//		iPraiseRecode.setLpOpenId("12343");
//		iPraiseRecodeService.edit(iPraiseRecode);
//		iPraiseRecode2 = iPraiseRecodeService.get(id);
//		assertEquals(iPraiseRecode2.getLpOpenId(),"12343");
//	}
//}
