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
//public class ITopicDetailServiceTest {
//	@Autowired
//	private ITopicDetailService iTopicDetailService;
//
//	
//	public  ITopicDetail iTopicDetail = new ITopicDetail();
//	
//	@Before
//	public void into(){
//		Date date = new Date();
//		iTopicDetail.setTopicId(1L);//与Itopic中的id关联
//		iTopicDetail.setType(1);
//		iTopicDetail.setPicturePath("图片路径");
//		iTopicDetail.setContent("具体内容");
//		iTopicDetail.setCreateTime(date);
//		iTopicDetail.setUpdateTime(date);
//
//		
//	}
//	
//	@Test
//	public void Test(){
//		iTopicDetailService.add(iTopicDetail);
//		Long id = iTopicDetail.getId();
//		ITopicDetail card = iTopicDetailService.get(id);
//		assertEquals(card.getType(),new Integer(1));
//
//		iTopicDetail.setType(2);
//		iTopicDetailService.edit(iTopicDetail);
//		card = iTopicDetailService.get(id);
//		assertEquals(card.getType(),new Integer(2));
//	}
//}
