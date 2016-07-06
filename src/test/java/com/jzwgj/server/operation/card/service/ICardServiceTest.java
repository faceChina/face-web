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
//public class ICardServiceTest {
//	@Autowired
//	private ICardService icardService;
//
//	
//	public  ICard icard = new ICard();
//	
//	@Before
//	public void into(){
//		icard.setNickName("刘佳");
//		icard.setCell("18158137183");
//		icard.setCompany("浙江脸谱");
//		icard.setPosition("java工程师");
//		icard.setStatus(0);
//		icard.setFromWechatNo("daotest");
//		
//		icard.setHeadPicture("头像地址");
//		icard.setCardAddress("名片地址 如：/card/(id).htm");
//		icard.setTwoDimensionalCode("二唯码图片地址");
//		icard.setSumContacts(0l);//人脉总数
//		
//	}
//	
//	@Test
//	public void Test(){
//		icardService.add(icard);
//		Long id = icard.getId();
//		ICard card = icardService.get(id);
//		assertEquals(card.getSumContacts(),new Long(0l));
//
//		icard.setSumContacts(1l);
//		icardService.edit(icard);
//		card = icardService.get(id);
//		assertEquals(card.getSumContacts(),new Long(1l));
//		
///*		ICard  card = icardService.get("oefr_tk_u-JHx9fIl_pwp7icwWfU");
//		System.out.println(card.getNickName());*/
//	}
//}
