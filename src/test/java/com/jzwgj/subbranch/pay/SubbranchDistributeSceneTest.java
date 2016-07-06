package com.jzwgj.subbranch.pay;

import java.util.Date;

import org.dbunit.basetest.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zjlp.face.web.server.trade.order.dao.SalesOrderDao;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.payment.domain.dto.FeeDto;
import com.zjlp.face.web.server.trade.payment.scene.dis.DistributeScene;

public class SubbranchDistributeSceneTest extends BaseTest {

	@Autowired
	SalesOrderDao salesOrderDao;
	@Autowired
	DistributeScene subbranchDistributeScene;
	
	@Test
	public void test(){
		SalesOrder salesOrder = salesOrderDao.getSalesOrderByOrderNo("S15070714562818943");
		salesOrder.setTransactionSerialNumber("2341341341234");
		FeeDto orderFee = new FeeDto();
		orderFee.setPayFee(0L);
		orderFee.setPlatformFee(0L);
		subbranchDistributeScene.distributeCalculation(salesOrder, orderFee, 1290L, new Date());;
	}
}
