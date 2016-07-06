package com.jzwgj.dbunit;

import static org.junit.Assert.fail;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.zjlp.face.web.server.user.shop.service.VareaService;
import com.zjlp.face.web.server.user.user.domain.dto.VaearDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-beans.xml")
//加入dbunitLIstener
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
public class DBUnitTest2 {

	@Autowired
	private VareaService vareaService;
	
//	@Test
//	@DatabaseSetup(type=DatabaseOperation.CLEAN_INSERT,value="com/jzwgj/dbunit/data.xml")//setup阶段执行clean_insert操作，数据导入到数据库中，在测试之前执行
//    @ExpectedDatabase(assertionMode=DatabaseAssertionMode.NON_STRICT,value="com/jzwgj/dbunit/data.xml")//将测试操作后的数据库数据与期望的xml数据进行比较是否相等，来判定测试是否通过，assertionMode设置比较的模式。跑完test后执行
//    @DatabaseTearDown(type=DatabaseOperation.CLEAN_INSERT,value="com/jzwgj/dbunit/data.xml")//恢复数据库中的数据，测试之后执行。
	public void test() {
		
		VaearDto dto = vareaService.getVareaById(101010);
		System.out.println(JSONObject.fromObject(dto).toString());
		fail("Not yet implemented");
	}

}
