package com.jzwgj.product.good.service;


import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.web.server.product.good.domain.ShopType;
import com.zjlp.face.web.server.product.good.service.ShopTypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class ShopTypeServiceTest {
	
	@Autowired
	private ShopTypeService shopTypeService;
	
	private ShopType shopType;
	
	@Before
	public void init(){
		shopType = new ShopType();
		shopType.setShopNo("HZJZ0001140724232mHd");
		shopType.setBackgroundColor("红");
		shopType.setFontColor("123");
		shopType.setImgPath("123123");
		shopType.setName("123");
		shopType.setStatus(1);
		shopType.setSort(1L);
		Date date = new Date();
		shopType.setCreateTime(date);
		shopType.setUpdateTime(date);
	}
	
	@Test
	public void add(){
		shopTypeService.addShopType(shopType);
		shopType.setBackgroundColor("绿");
		shopTypeService.editShopType(shopType);
		Assert.assertEquals("12123123", "绿", shopType.getBackgroundColor());
	}

}
