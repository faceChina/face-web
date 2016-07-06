package com.jzwgj.dbunit.salesorder;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.jzwgj.dbunit.MyDataBaseTestCase;
import com.zjlp.face.account.domain.Account;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.trade.cart.domain.BuyItem;
import com.zjlp.face.web.server.trade.cart.domain.OrderBuy;
import com.zjlp.face.web.server.trade.cart.domain.OrderBuyVo;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderDto;
import com.zjlp.face.web.server.trade.payment.bussiness.PaymentBusiness;
import com.zjlp.face.web.server.trade.payment.domain.WalletTransactionRecord;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = { "classpath:/spring-beans.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true,transactionManager="jzTransactionManager")
public class ProtocolSalesOrderTestCase extends MyDataBaseTestCase{

	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	
	@Autowired
	private PaymentBusiness paymentBussiness;
	
	@Autowired
	private AccountBusiness accountBusiness;
	
	@Test
	public void test(){
		//生成订单
		List<BuyItem> buyItems = new ArrayList<BuyItem>();
		BuyItem buyItem = new BuyItem();
		buyItem.setId(1L);
		buyItem.setQuantity(1L);
		buyItems.add(buyItem);
		
		List<OrderBuy> orderBuys = new ArrayList<OrderBuy>();
		OrderBuy orderBuy = new OrderBuy();
		orderBuy.setShopNo("HZJZ0001140724232mHd");
		orderBuy.setDeliveryWay(1);
		orderBuy.setBuyerMessage("你买或者不买，我就在这里，不悲不喜");
		orderBuy.setBuyItemList(buyItems);
		orderBuys.add(orderBuy);
		
		OrderBuyVo orderBuyVo = new OrderBuyVo();
		orderBuyVo.setAddressId(1L);
		orderBuyVo.setBuyType(1);
		orderBuyVo.setOrderBuyList(orderBuys);
		orderBuyVo.setUserId(2L);
		
		List<String> orderNos = salesOrderBusiness.addSalesOrder(orderBuyVo);
		
		super.assertNotNull("订单返回订单好为空",orderNos);
		super.assertTrue("订单生成数量错误",orderNos.size() == 1);
		
		for (String orderNo : orderNos) {
			SalesOrderDto dto = salesOrderBusiness.getSalesOrderDetailByOrderNo(orderNo);
			super.assertNotNull("没有找到订单和订单详细信息", dto);
			super.assertTrue("订单商品数量不正确",dto.getQuantity().longValue() == 1);
			super.assertTrue("订单总价不正确",dto.getTotalPrice().longValue() == 10);
			super.assertTrue("订单细项数量不正确",dto.getOrderItemList().size() == 1);
			super.assertTrue("订单细项对应商品编号不正确",dto.getOrderItemList().get(0).getGoodId().longValue() == 1);
			super.assertTrue("订单细项对应商品SKU编号不正确",dto.getOrderItemList().get(0).getGoodSkuId().longValue() == 1);
		}
		
		//钱包付款
		WalletTransactionRecord walletTransactionRecord = paymentBussiness.paymentConsumerByWallet(2L, "123456", orderNos);
		super.assertNotNull("钱包交易记录为空",walletTransactionRecord);
		//用户钱包
		Account account = accountBusiness.getAccountByUserId(2L);
		super.assertNotNull("用户钱包不存在",account);
		super.assertTrue("用户钱包余额不正确",account.getWithdrawAmount().longValue() == 10000-10);
		//系统钱包
		Account lpAccount = accountBusiness.getAccountById(1L);
		super.assertNotNull("系统钱包不存在",lpAccount);
		super.assertNotNull("系统钱包余额不正确",lpAccount.getWithdrawAmount() == 10);
	}
}
