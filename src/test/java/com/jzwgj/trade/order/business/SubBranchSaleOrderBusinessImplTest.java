package com.jzwgj.trade.order.business;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zjlp.face.web.server.trade.order.bussiness.SubBranchSaleOrderBusiness;
import com.zjlp.face.web.server.trade.order.domain.vo.SubBranchSalesOrderVo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
public class SubBranchSaleOrderBusinessImplTest {

	@Autowired
	private SubBranchSaleOrderBusiness subBranchSaleOrderBusiness;
	
	
	@Test
	public void getSupplierSubBranchSalesOrderByOrderNo () {
		SubBranchSalesOrderVo salesOrder = subBranchSaleOrderBusiness.getSupplierSubBranchSalesOrderByOrderNo("S15081311401797934");
	}
}
