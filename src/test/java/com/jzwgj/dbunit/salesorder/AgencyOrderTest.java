package com.jzwgj.dbunit.salesorder;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.web.server.trade.cart.domain.BuyItem;
import com.zjlp.face.web.server.trade.cart.domain.OrderBuy;
import com.zjlp.face.web.server.trade.cart.domain.OrderBuyVo;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = { "classpath:/spring-beans.xml" })
@TransactionConfiguration(transactionManager="jzTransactionManager")
public class AgencyOrderTest {
	
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	@Test
	public void test(){
		/**
		 * 购买红色S码1件，白色S码2件
		 */
		List<BuyItem> buyItems = new ArrayList<BuyItem>();
		BuyItem buyItemRed = new BuyItem();
		buyItemRed.setId(316L);
		buyItemRed.setQuantity(2L);
		buyItems.add(buyItemRed);
		BuyItem buyItemWhite = new BuyItem();
		buyItemWhite.setId(318L);
		buyItemWhite.setQuantity(3L);
		buyItems.add(buyItemWhite);
		
		List<OrderBuy> orderBuys = new ArrayList<OrderBuy>();
		OrderBuy orderBuy = new OrderBuy();
		orderBuy.setShopNo("HZJZ0001140724232mHd");
		orderBuy.setBuyShopNo("HZJZ0001140724232mHd");
		orderBuy.setDeliveryWay(1);
		orderBuy.setBuyerMessage("你买或者不买，我就在这里，不悲不喜");
		orderBuy.setBuyItemList(buyItems);
		orderBuys.add(orderBuy);
		
		OrderBuyVo orderBuyVo = new OrderBuyVo();
		orderBuyVo.setAddressId(1L);
		orderBuyVo.setBuyType(2);
		orderBuyVo.setOrderBuyList(orderBuys);
		orderBuyVo.setUserId(2L);
		/**生成订单*/
		List<String> orderNos = salesOrderBusiness.add(orderBuyVo);
	}
}
