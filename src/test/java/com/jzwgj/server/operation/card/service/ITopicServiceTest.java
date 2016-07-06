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
//public class ITopicServiceTest {
//	@Autowired
//	private ITopicService iTopicService;
//
//	
//	public  ITopic iTopic = new ITopic();
//	
//	@Before
//	public void into(){
//		Date date = new Date();
//		iTopic.setCardId(1L);
//		iTopic.setType(1);
//		iTopic.setName("栏目名称");
//		iTopic.setCode("栏目CODE");
//		iTopic.setLink("栏目链接");
//		iTopic.setSort(0);
//		iTopic.setCreateTime(date);
//		iTopic.setUpdateTime(date);
//		
//		
//		
//
//		
//	}
//	
//	@Test
//	public void Test(){
//		iTopicService.add(iTopic);
//		Long id = iTopic.getId();
//		ITopic topic = iTopicService.get(id);
//		assertEquals(topic.getType(),new Integer(1));
//
//		iTopic.setType(2);
//		iTopicService.edit(iTopic);
//		topic = iTopicService.get(id);
//		assertEquals(topic.getType(),new Integer(2));
//	}
//}
