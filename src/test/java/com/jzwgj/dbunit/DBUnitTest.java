package com.jzwgj.dbunit;

import java.io.InputStream;

import net.sf.json.JSONObject;

import org.dbunit.DatabaseTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.server.user.shop.service.VareaService;
import com.zjlp.face.web.server.user.user.domain.dto.VaearDto;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = { "classpath:/spring-beans.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true,transactionManager="jzTransactionManager")
public class DBUnitTest extends DatabaseTestCase{
	
	@Autowired
	private VareaService vareaService;
	
	ReplacementDataSet createDataSet(InputStream is) throws Exception {  
	    return new ReplacementDataSet(new FlatXmlDataSetBuilder().build(is));  
	}
	
//	@Before
	public void before(){
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void test(){
		try {
			VaearDto dto = vareaService.getVareaById(101010);
			System.out.println(JSONObject.fromObject(dto).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@After
	public void after(){
		try {
			tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
        //The default is commit the record  
		String db_url = PropertiesUtil.getContexrtParam("jz.url");  
		String db_user = PropertiesUtil.getContexrtParam("jz.username");  
		String db_pwd = PropertiesUtil.getContexrtParam("jz.password");  
		String driverClass = "com.mysql.jdbc.Driver";  
  
        IDatabaseConnection iconn = null;  
        IDatabaseTester databaseTester;  
        databaseTester = new JdbcDatabaseTester(driverClass, db_url, db_user, db_pwd);  
        iconn = databaseTester.getConnection();  
        return iconn;  
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = loader.getResourceAsStream("com/jzwgj/dbunit/data.xml");
		IDataSet iDataSet = new FlatXmlDataSetBuilder().setColumnSensing(true).build(inputStream);
		return iDataSet;
	}

	@Override
	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.INSERT;
	}

	@Override
	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.DELETE_ALL;
	}

	@Override
	protected void tearDown() throws Exception {
        final IDatabaseTester databaseTester = getDatabaseTester();
        assertNotNull( "DatabaseTester is not set", databaseTester );
        databaseTester.setTearDownOperation( getTearDownOperation() );
        databaseTester.setDataSet( getDataSet() );
        databaseTester.setOperationListener(getOperationListener());
        databaseTester.onTearDown();
	}
	
}

