//package com.jzwgj.subbranch;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.dbunit.basetest.BaseTest;
//import org.dbunit.tools.DataFileCreator;
//import org.dbunit.tools.writer.FileWrite;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.Assert;
//
//import com.github.springtestdbunit.annotation.DatabaseSetup;
//import com.zjlp.face.util.page.Pagination;
//import com.zjlp.face.web.exception.errorcode.CErrMsg;
//import com.zjlp.face.web.exception.ext.OrderException;
//import com.zjlp.face.web.server.trade.cart.domain.BuyItem;
//import com.zjlp.face.web.server.trade.cart.domain.OrderBuy;
//import com.zjlp.face.web.server.trade.cart.domain.OrderBuyVo;
//import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
//import com.zjlp.face.web.server.trade.order.bussiness.SubBranchSaleOrderBusiness;
//import com.zjlp.face.web.server.trade.order.dao.PurchaseOrderDao;
//import com.zjlp.face.web.server.trade.order.dao.PurchaseOrderItemDao;
//import com.zjlp.face.web.server.trade.order.dao.SalesOrderDao;
//import com.zjlp.face.web.server.trade.order.domain.PurchaseOrder;
//import com.zjlp.face.web.server.trade.order.domain.PurchaseOrderItem;
//import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
//import com.zjlp.face.web.server.trade.order.domain.vo.SubBranchSalesOrderVo;
//
//public class SubbranchOrderTest extends BaseTest {
//
//	private static final String filePath = "D:/";
//	@Autowired
//	private SalesOrderDao salesOrderDao;
//	@Autowired
//	private PurchaseOrderDao purchaseOrderDao;
//	@Autowired
//	private PurchaseOrderItemDao purchaseOrderItemDao;
//	@Autowired
//	private SalesOrderBusiness salesOrderBusiness;
//	@Autowired
//	private SubBranchSaleOrderBusiness subBranchSaleOrderBusiness;
//	
//	private void create(String base) {
//		FileWrite fileWrite = new FileWrite(filePath
//				+ base);
//		List<SalesOrder> lsit = salesOrderDao.selectAll();
//		Assert.notEmpty(lsit);
//		for (SalesOrder data : lsit) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//		List<PurchaseOrder> poList = purchaseOrderDao.selectAll();
//		for (PurchaseOrder data : poList) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//		List<PurchaseOrderItem> item = purchaseOrderItemDao.selectAll();
//		for (PurchaseOrderItem data : item) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//		fileWrite.write();
//	}
//
//	@DatabaseSetup("test-one-leve.xml")
////	@Test
//	public void test_one_level() {
//		// 订单生成
//		OrderBuyVo vo = this.getOrderBuyVo();
//		List<String> orderNoList = salesOrderBusiness.add(vo);
//		Assert.notEmpty(orderNoList);
//		this.create("one-level-base.txt");
//		// 订单查询
//		FileWrite fileWrite = new FileWrite(filePath
//				+ "test-result-one-level.txt");
//		
//		fileWrite.append("//供应商自己的订单");
//		SalesOrder salesOrder = new SalesOrder();
//		salesOrder.setShopNo("HZJZ00091412102XnxgR");
//		Pagination<SubBranchSalesOrderVo> pagination = new Pagination<SubBranchSalesOrderVo>();
//		pagination = subBranchSaleOrderBusiness.findAllMySalesOrderByShopNo(
//				salesOrder, null, pagination);
//		Assert.notEmpty(pagination.getDatas());
//		for (SubBranchSalesOrderVo data : pagination.getDatas()) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//
//		fileWrite.next("//供应商的分店订单");
//		pagination = new Pagination<SubBranchSalesOrderVo>();
//		pagination = subBranchSaleOrderBusiness
//				.findSubBranchOrderPageByShopNoAndStatusForSub(
//						"HZJZ00091412102XnxgR", null, null, pagination);
//		Assert.notEmpty(pagination.getDatas());
//		for (SubBranchSalesOrderVo data : pagination.getDatas()) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//
//		fileWrite.next("//一级分店订单");
//		pagination = new Pagination<SubBranchSalesOrderVo>();
//		pagination = subBranchSaleOrderBusiness
//				.findSubBranchOrderPageByPhoneAndStatusForOwn("13666666666",
//						null, null, pagination);
//		Assert.notEmpty(pagination.getDatas());
//		for (SubBranchSalesOrderVo data : pagination.getDatas()) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//		fileWrite.write();
//	}
//
//	@DatabaseSetup("test-two-leve.xml")
////	@Test
//	public void test_two_level() {
//		// 订单生成
//		OrderBuyVo vo = this.getOrderBuyVo();
//		List<String> orderNoList = salesOrderBusiness.add(vo);
//		Assert.notEmpty(orderNoList);
//		
//		create("two-level-base.txt");
//		
//		// 订单查询
//		FileWrite fileWrite = new FileWrite(filePath
//				+ "test-result-two-level.txt");
//		fileWrite.append("//供应商自己的订单");
//		SalesOrder salesOrder = new SalesOrder();
//		salesOrder.setShopNo("HZJZ00091412102XnxgR");
//		Pagination<SubBranchSalesOrderVo> pagination = new Pagination<SubBranchSalesOrderVo>();
//		pagination = subBranchSaleOrderBusiness.findAllMySalesOrderByShopNo(
//				salesOrder, null, pagination);
//		Assert.notEmpty(pagination.getDatas());
//		for (SubBranchSalesOrderVo data : pagination.getDatas()) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//
//		fileWrite.next("//供应商的分店订单");
//		pagination = new Pagination<SubBranchSalesOrderVo>();
//		pagination = subBranchSaleOrderBusiness
//				.findSubBranchOrderPageByShopNoAndStatusForSub(
//						"HZJZ00091412102XnxgR", null, null, pagination);
//		Assert.notEmpty(pagination.getDatas(), "供应商的分店订单");
//		for (SubBranchSalesOrderVo data : pagination.getDatas()) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//
//		fileWrite.next("//一级分店订单");
//		pagination = new Pagination<SubBranchSalesOrderVo>();
//		pagination = subBranchSaleOrderBusiness.findSubBranchOrderPageByPhoneAndStatusForOwn("13666666666",
//						null, null, pagination);
////		Assert.notEmpty(pagination.getDatas(), "一级分店订单");
//		for (SubBranchSalesOrderVo data : pagination.getDatas()) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//		
//		fileWrite.next("//一级分店下级订单");
//		pagination = new Pagination<SubBranchSalesOrderVo>();
//		pagination = subBranchSaleOrderBusiness.findSubBranchOrderPageByPhoneAndStatusForSub("13666666666",
//						null, null, pagination);
//		Assert.notEmpty(pagination.getDatas(), "一级分店下级订单");
//		for (SubBranchSalesOrderVo data : pagination.getDatas()) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//		
//		fileWrite.next("//二级分店订单");
//		pagination = new Pagination<SubBranchSalesOrderVo>();
//		pagination = subBranchSaleOrderBusiness.findSubBranchOrderPageByPhoneAndStatusForOwn("13388888888",
//						null, null, pagination);
//		Assert.notEmpty(pagination.getDatas(), "二级分店下级订单");
//		for (SubBranchSalesOrderVo data : pagination.getDatas()) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//		fileWrite.write();
//	}
//	
//	
//	@DatabaseSetup("test-three-leve.xml")
////	@Test
//	public void test_three_level() {
//		// 订单生成
//		OrderBuyVo vo = this.getOrderBuyVo();
//		List<String> orderNoList = salesOrderBusiness.add(vo);
//		Assert.notEmpty(orderNoList);
//		
//		create("three-level-base.txt");
//		
//		// 订单查询
//		FileWrite fileWrite = new FileWrite(filePath
//				+ "test-result-three-level.txt");
//		fileWrite.append("//供应商自己的订单");
//		SalesOrder salesOrder = new SalesOrder();
//		salesOrder.setShopNo("HZJZ00091412102XnxgR");
//		Pagination<SubBranchSalesOrderVo> pagination = new Pagination<SubBranchSalesOrderVo>();
//		pagination = subBranchSaleOrderBusiness.findAllMySalesOrderByShopNo(
//				salesOrder, null, pagination);
//		Assert.notEmpty(pagination.getDatas());
//		for (SubBranchSalesOrderVo data : pagination.getDatas()) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//
//		fileWrite.next("//供应商的分店订单");
//		pagination = new Pagination<SubBranchSalesOrderVo>();
//		pagination.setStart(0);
//		pagination.setPageSize(20);
//		pagination = subBranchSaleOrderBusiness
//				.findSubBranchOrderPageByShopNoAndStatusForSub(
//						"HZJZ00091412102XnxgR", null, null, pagination);
//		Assert.notEmpty(pagination.getDatas(), "供应商的分店订单");
//		for (SubBranchSalesOrderVo data : pagination.getDatas()) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//
//		fileWrite.next("//一级分店订单");
//		pagination = new Pagination<SubBranchSalesOrderVo>();
//		pagination.setStart(0);
//		pagination.setPageSize(20);
//		pagination = subBranchSaleOrderBusiness.findSubBranchOrderPageByPhoneAndStatusForOwn("13666666666",
//						null, null, pagination);
////		Assert.notEmpty(pagination.getDatas(), "一级分店订单");
//		for (SubBranchSalesOrderVo data : pagination.getDatas()) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//		
//		fileWrite.next("//一级分店下级订单");
//		pagination = new Pagination<SubBranchSalesOrderVo>();
//		pagination.setStart(0);
//		pagination.setPageSize(20);
//		pagination = subBranchSaleOrderBusiness.findSubBranchOrderPageByPhoneAndStatusForSub("13666666666",
//						null, null, pagination);
//		Assert.notEmpty(pagination.getDatas(), "一级分店下级订单");
//		for (SubBranchSalesOrderVo data : pagination.getDatas()) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//		
//		fileWrite.next("//二级分店订单");
//		pagination = new Pagination<SubBranchSalesOrderVo>();
//		pagination.setStart(0);
//		pagination.setPageSize(20);
//		pagination = subBranchSaleOrderBusiness.findSubBranchOrderPageByPhoneAndStatusForOwn("13388888888",
//						null, null, pagination);
////		Assert.notEmpty(pagination.getDatas(), "二级分店下级订单");
//		for (SubBranchSalesOrderVo data : pagination.getDatas()) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//		
//		fileWrite.next("//二级分店下级订单");
//		pagination = new Pagination<SubBranchSalesOrderVo>();
//		pagination.setStart(0);
//		pagination.setPageSize(20);
//		pagination = subBranchSaleOrderBusiness.findSubBranchOrderPageByPhoneAndStatusForSub("13388888888",
//						null, null, pagination);
//		Assert.notEmpty(pagination.getDatas(), "一级分店下级订单");
//		for (SubBranchSalesOrderVo data : pagination.getDatas()) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//		
//		fileWrite.next("//三级分店订单");
//		pagination = new Pagination<SubBranchSalesOrderVo>();
//		pagination.setStart(0);
//		pagination.setPageSize(20);
//		pagination = subBranchSaleOrderBusiness.findSubBranchOrderPageByPhoneAndStatusForOwn("13588313856",
//						null, null, pagination);
//		Assert.notEmpty(pagination.getDatas(), "二级分店下级订单");
//		for (SubBranchSalesOrderVo data : pagination.getDatas()) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//		fileWrite.write();
//	}
//
//	private OrderBuyVo getOrderBuyVo() {
//		List<BuyItem> buyItems = new ArrayList<BuyItem>();
//		BuyItem buyItemRed = new BuyItem();
//		buyItemRed.setId(1L);
//		buyItemRed.setQuantity(2L);
//		buyItems.add(buyItemRed);
//
//		List<OrderBuy> orderBuys = new ArrayList<OrderBuy>();
//		OrderBuy orderBuy = new OrderBuy();
//		orderBuy.setShopNo("HZJZ00091412102XnxgR");
//		orderBuy.setBuyShopNo("HZJZ00091412102XnxgR");
//		orderBuy.setDeliveryWay(1);
//		orderBuy.setBuyerMessage("你买或者不买，我就在这里，不悲不喜");
//		orderBuy.setBuyItemList(buyItems);
//		orderBuys.add(orderBuy);
//
//		OrderBuyVo orderBuyVo = new OrderBuyVo();
//		orderBuyVo.setAddressId(1L);
//		orderBuyVo.setBuyType(2);
//		orderBuyVo.setOrderBuyList(orderBuys);
//		orderBuyVo.setUserId(116L);
//		return orderBuyVo;
//	}
//
//	public static void main(String[] args) {
//		DataFileCreator creator = new DataFileCreator(
//				"jdbc:mysql://localhost:3306/test", "root", "root");
//		creator.withTableNames("cart");
//		creator.create("D:/test.xml");
//	}
//	
////	@Test
//	public void test_1() {
//		// 订单查询
//		FileWrite fileWrite = new FileWrite(filePath
//				+ "aaa.txt");
//		fileWrite.append("//供应商自己的订单");
//		SalesOrder salesOrder = new SalesOrder();
//		salesOrder.setShopNo("HZJZ1507071004xY56t1");
//		Pagination<SubBranchSalesOrderVo> pagination = new Pagination<SubBranchSalesOrderVo>();
//		pagination = subBranchSaleOrderBusiness.findAllMySalesOrderByShopNo(
//				salesOrder, null, pagination);
//		Assert.notEmpty(pagination.getDatas());
//		for (SubBranchSalesOrderVo data : pagination.getDatas()) {
//			System.out.println(String.valueOf(data));
//			fileWrite.next(String.valueOf(data));
//		}
//		fileWrite.write();
//	}
//	
////	@Test
//	public void test_2() {
//		// 订单查询
//		FileWrite fileWrite = new FileWrite(filePath
//				+ "bbb.txt");
//		fileWrite.append("//供应商自己的订单");
//		SubBranchSalesOrderVo order = subBranchSaleOrderBusiness.getSupplierSubBranchSalesOrderByOrderNo("S15070714562818943");
//		fileWrite.next(String.valueOf(order));
//		fileWrite.write();
//	}
//	
////	@Test
//	public void test_3() {
//		// 订单查询
//		FileWrite fileWrite = new FileWrite(filePath
//				+ "ccc.txt");
//		fileWrite.append("//供应商自己的订单");
//		Pagination<SubBranchSalesOrderVo> pagain = subBranchSaleOrderBusiness.findSubBranchOrderPageByPhoneAndStatusForSub("13700002222",
//				null, null, new Pagination<SubBranchSalesOrderVo>());
//		for (SubBranchSalesOrderVo order : pagain.getDatas()) {
//			fileWrite.next(String.valueOf(order));
//		}
//		fileWrite.write();
//	}
//	
////	@Test
//	public void test_5() {
//		// 订单查询
//		FileWrite fileWrite = new FileWrite(filePath
//				+ "ddd.txt");
//		SubBranchSalesOrderVo order = subBranchSaleOrderBusiness.getSubBranchSalesOrderWithPurchaseOrderItemsByPrimaryKey("S15070714171933965", 609L);
//		fileWrite.next(String.valueOf(order));
//		fileWrite.write();
//	}
//	
////	@Test
//	public void test_6(){
//		Integer count = subBranchSaleOrderBusiness.getSubbranchOrderTDPCount("18655015835");
//		FileWrite fileWrite = new FileWrite(filePath
//				+ "eee.txt");
//		fileWrite.next(String.valueOf(count));
//		fileWrite.write();
//	}
//	
//	@Test
//	public void test_7(){
//		try {
//			subBranchSaleOrderBusiness.getSubbranchOrderTDPCount("");
//		} catch (OrderException e) {
//			org.junit.Assert.assertEquals(CErrMsg.NULL_PARAM.getErrCd(), e.getMessage());
//			org.junit.Assert.assertEquals(CErrMsg.NULL_PARAM, e.getExternalMsg());
//		}
//	}
//}
