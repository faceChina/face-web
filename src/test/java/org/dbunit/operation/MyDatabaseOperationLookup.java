package org.dbunit.operation;

import java.util.HashMap;
import java.util.Map;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.operation.DatabaseOperationLookup;

public class MyDatabaseOperationLookup implements DatabaseOperationLookup {

	private static Map<DatabaseOperation, org.dbunit.operation.DatabaseOperation> OPERATION_LOOKUP;
	static {
		OPERATION_LOOKUP = new HashMap<DatabaseOperation, org.dbunit.operation.DatabaseOperation>();
		OPERATION_LOOKUP.put(DatabaseOperation.UPDATE, org.dbunit.operation.MyDatabaseOperation.UPDATE);
		OPERATION_LOOKUP.put(DatabaseOperation.INSERT, org.dbunit.operation.MyDatabaseOperation.MY_INSERT);
		OPERATION_LOOKUP.put(DatabaseOperation.REFRESH, org.dbunit.operation.MyDatabaseOperation.REFRESH);
		OPERATION_LOOKUP.put(DatabaseOperation.DELETE, org.dbunit.operation.MyDatabaseOperation.DELETE);
		OPERATION_LOOKUP.put(DatabaseOperation.DELETE_ALL, org.dbunit.operation.MyDatabaseOperation.DELETE_ALL);
		OPERATION_LOOKUP.put(DatabaseOperation.TRUNCATE_TABLE, org.dbunit.operation.MyDatabaseOperation.TRUNCATE_TABLE);
		OPERATION_LOOKUP.put(DatabaseOperation.CLEAN_INSERT, org.dbunit.operation.MyDatabaseOperation.MY_INSERT);
	}
	
	@Override
	public org.dbunit.operation.DatabaseOperation get(com.github.springtestdbunit.annotation.DatabaseOperation operation) {
		return OPERATION_LOOKUP.get(operation);
	}

}
