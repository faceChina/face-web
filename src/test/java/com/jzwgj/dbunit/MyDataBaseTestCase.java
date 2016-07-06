package com.jzwgj.dbunit;

import java.io.InputStream;

import org.dbunit.DatabaseTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;

import com.zjlp.face.util.file.PropertiesUtil;

public class MyDataBaseTestCase extends DatabaseTestCase {

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		String db_url = PropertiesUtil.getContexrtParam("jz.url");  
		String db_user = PropertiesUtil.getContexrtParam("jz.username");  
		String db_pwd = PropertiesUtil.getContexrtParam("jz.password");  
		String driverClass = "com.mysql.jdbc.Driver";  
  
        IDatabaseTester databaseTester = new JdbcDatabaseTester(driverClass, db_url, db_user, db_pwd);  
        IDatabaseConnection iconn = databaseTester.getConnection();  
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
		return DatabaseOperation.DELETE;
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

	/*@Before
	public void before(){
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	/*@After
	public void after(){
		try {
			tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
}
