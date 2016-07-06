package com.jzwgj.user.customer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.user.customer.business.CustomerBusiness;
import com.zjlp.face.web.server.user.customer.domain.vo.MyCustomerVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class CustomerTest {

	@Autowired
	private CustomerBusiness customerBusiness;
	
	@Test
	public void test(){
		customerBusiness.getMyCustomerByUserId(1, 116L, "林", "1", new Pagination<MyCustomerVo>());
	}
}
