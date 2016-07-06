package com.jzwgj.product.good.produer;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zjlp.face.web.server.product.phone.domain.PhoneDevice;
import com.zjlp.face.web.server.product.phone.producer.PhoneDeviceProducer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
@TransactionConfiguration(defaultRollback = true, transactionManager = "jzTransactionManager")
@ActiveProfiles("dev")
public class PhoneDeviceProducerTest {

	private Logger _logger = Logger.getLogger(getClass());

	private static final String PUSHID = "fgkkJtwqsxP1407+19qhF2ipzUW+GdH7XNYLSEwIHPD1ZqiGg6li6yBJEE+NO43LirJpte6sdxIbOuNXNdMO9YTp+vdpASK4Ak5aAYwEReE=";

	@Autowired
	private PhoneDeviceProducer phoneDeviceProducer;

//	@Test
	public void testFindPhoneDevice() {
		PhoneDevice device = phoneDeviceProducer.getPhoneDevice(221L);
		_logger.info("Result:" + device);
	}

//	@Test
	public void testAddPhoneDevice() {
		Long id = phoneDeviceProducer.addPhoneDevice(getAddDevice());
		_logger.info("Result:" + id);
	}

	public PhoneDevice getAddDevice() {
		PhoneDevice phoneDevice = new PhoneDevice();
		phoneDevice.setId(223L);
		phoneDevice.setUserId(111L);
		phoneDevice.setPushUserId(PUSHID);
		phoneDevice.setStatus(2);
		phoneDevice.setDeviceType(0);
		phoneDevice.setType(2);
		phoneDevice.setCreateTime(new Date());
		return phoneDevice;
	}
}
