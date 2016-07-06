package org.dbunit.basetest;

import org.dbunit.operation.MyDataSetLoader;
import org.dbunit.operation.MyDatabaseOperationLookup;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	TransactionalTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseOperationLookup=MyDatabaseOperationLookup.class,
dataSetLoader=MyDataSetLoader.class, databaseConnection="dataSource")
@TransactionConfiguration(transactionManager="jzTransactionManager")
@ContextConfiguration("/spring-beans.xml")
@Transactional
public class BaseTest {
	
	//now, has nothing to do.
	
}
