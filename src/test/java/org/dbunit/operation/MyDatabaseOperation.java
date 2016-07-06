package org.dbunit.operation;

import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;

public class MyDatabaseOperation extends DatabaseOperation {

    public static final DatabaseOperation MY_INSERT = new MyInsertOperation();
    public static final DatabaseOperation MY_CLEAN_INSERT = new CompositeOperation(
            DELETE_ALL, MY_INSERT);  
	@Override
	public void execute(IDatabaseConnection connection, IDataSet dataSet)
			throws DatabaseUnitException, SQLException {
		throw new RuntimeException("Can't run this method.");
	}

}
