package com.jzwgj.product.template.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.product.template.producer.TemplateProducer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class TemplateProducerTest {

	@Autowired
	private TemplateProducer templateProducer;
	
	@Test
	public void test() {
		templateProducer.initTemplate("HZJZ00091412102XnxgR", Constants.SHOP_GW_TYPE);
	}

}
