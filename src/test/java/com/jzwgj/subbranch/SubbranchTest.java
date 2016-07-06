package com.jzwgj.subbranch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
public class SubbranchTest {

	@Autowired
	private SubbranchBusiness subbranchBusiness;
	
	@Test
	public void unbindTest() {
		subbranchBusiness.unBindSubbranch(113L, 1094L);
	}
}
