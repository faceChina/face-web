//package com.jzwgj.server.operation.card.service;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.Date;
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
//public class ICardCollectServiceTest {
//	@Autowired
//	private ICardCollectService iCardCollectService;
//
//	
//	public  ICardCollect iCardCollect = new ICardCollect();
//	
//	@Before
//	public void into(){
//		Date date = new Date();
//		iCardCollect.setMyId(1L);
//		iCardCollect.setMyId(1L);;
//		iCardCollect.setCreateTime(date);
//		iCardCollect.setCollectId(1L);
//
//		
//	}
//	
//	@Test
//	public void Test(){
//		iCardCollectService.add(iCardCollect);
//		Long id = iCardCollect.getId();
//		ICardCollect cardCollect = iCardCollectService.get(id);
//		assertEquals(cardCollect.getMyId(),new Long(1l));
//
//		iCardCollect.setMyId(2l);
//		iCardCollectService.edit(iCardCollect);
//		cardCollect = iCardCollectService.get(id);
//		assertEquals(cardCollect.getMyId(),new Long(2l));
//	}
//}
