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
//@Transactional
@TransactionConfiguration(transactionManager="jzTransactionManager")
public class DefaultSalesOrderTestCase {

	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	/**
	 * 单店购买
	 * 测试订单生成，钱包支付，发货收货
	 * @Title: test
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @return void
	 * @author phb
	 * @date 2015年4月15日 上午10:50:53
	 */
	@Test
	public void test(){
		/**
		 * 购买红色S码1件，白色S码2件
		 */
		List<BuyItem> buyItems = new ArrayList<BuyItem>();
		BuyItem buyItemRed = new BuyItem();
		buyItemRed.setId(672L);
		buyItemRed.setQuantity(1L);
		buyItems.add(buyItemRed);
		BuyItem buyItemWhite = new BuyItem();
		buyItemWhite.setId(673L);
		buyItemWhite.setQuantity(2L);
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
		orderBuyVo.setBuyType(1);
		orderBuyVo.setOrderBuyList(orderBuys);
		orderBuyVo.setUserId(2L);
		/**生成订单*/
		List<String> orderNos = salesOrderBusiness.add(orderBuyVo);
//		super.assertNotNull("订单返回订单好为空",orderNos);
//		super.assertTrue("订单生成数量错误",orderNos.size() == 1);
		
//		for (String orderNo : orderNos) {
//			SalesOrderDto dto = salesOrderBusiness.getSalesOrderDetailByOrderNo(orderNo);
//			super.assertNotNull("没有找到订单和订单详细信息", dto);
//			super.assertTrue("订单商品件数不正确 "+dto.getQuantity().longValue(),dto.getQuantity().longValue() == 2);
//			super.assertTrue("订单总价不正确 "+dto.getTotalPrice().longValue(),dto.getTotalPrice().longValue() == 300);
//			super.assertTrue("订单细项数量不正确 "+dto.getOrderItemList().size(),dto.getOrderItemList().size() == 2);
//			List<OrderItem> orderItemList = dto.getOrderItemList();
//			for (OrderItem orderItem : orderItemList) {
//				if(orderItem.getGoodSkuId().longValue() == 3){
//					super.assertTrue("红色商品数量不正确",orderItem.getQuantity().longValue() == 1);
//					super.assertTrue("红色商品原价不正确",orderItem.getPrice().longValue() == 100);
//					super.assertTrue("红色商品总价不正确",orderItem.getTotalPrice().longValue() == 100);
//				}
//				else if(orderItem.getGoodSkuId().longValue() == 5){
//					super.assertTrue("白色商品数量不正确",orderItem.getQuantity().longValue() == 2);
//					super.assertTrue("白色商品原价不正确",orderItem.getPrice().longValue() == 100);
//					super.assertTrue("白色商品总价不正确",orderItem.getTotalPrice().longValue() == 200);
//				}
//			}
//		}
		
		
	}
}
