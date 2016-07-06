package com.jzwgj.trade.order.dao;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.web.server.trade.order.dao.PurchaseOrderDao;
import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class PurchaseOrderDaoTest {
	
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;
	
	private PurchaseOrder purchaseOrder;
	
	@Before
	public void init(){
		purchaseOrder = new PurchaseOrder();
		purchaseOrder.setOrderNo("S15031619125567004");
		purchaseOrder.setCreateTime(new Date());
		purchaseOrder.setCooperationWay(1);
//		purchaseOrder.setPurchaserNo("123123");
//		purchaseOrder.setPurchaserNick("123L");
		purchaseOrder.setTotalProfitPrice(123L);
		purchaseOrder.setSupplierNick("123L");
		purchaseOrder.setSupplierNo("123L");
		purchaseOrder.setTotalPurchasePrice(321L);
		purchaseOrder.setTotalPurchaseQuantity(132L);
		purchaseOrder.setTotalSalesPrice(123L);
		purchaseOrder.setPicturePath("123123");
		purchaseOrder.setUpdateTime(new Date());
	}
	@Test
	public void test(){
		try {
			purchaseOrderDao.insert(purchaseOrder);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
