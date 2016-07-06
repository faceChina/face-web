package com.jzwgj.trade.order.business;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.trade.order.dao.SalesOrderDao;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;
import com.zjlp.face.web.server.trade.order.domain.vo.OperateData;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class SalesOrderBusinessImplTest {

	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	@Autowired
	private SalesOrderDao salesOrderDao;
	
//	@Test
	public void test() {
		SalesOrder salesOrder = salesOrderBusiness.getSalesOrderByOrderNo("S15071410075259280");
		salesOrderBusiness.validateOrderByShopNo("S15071410075259280", "HZJZ15032515067ygvH1");
		salesOrderBusiness.deliveryOrder(salesOrder, 110l);
	}
	
	@Test
	public void test2(){
		SalesOrderVo salesOrderVo = new SalesOrderVo();
		salesOrderVo.setShopNo("HZJZ00091412102XnxgR");
		salesOrderVo.setPaymentTime(DateUtil.StringToDate("2015-06-08", "yyyy-MM-dd"));
		List<OperateData> list = salesOrderDao.selectSupplierRecivePrices(salesOrderVo);
		for (OperateData operateData : list) {
			System.out.println(operateData);
		}
	}

}
