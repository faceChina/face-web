package org.dbunit.operation;

import org.apache.log4j.Logger;

public class MyInsertOperation extends InsertOperation {

	private Logger log = Logger.getLogger(MyInsertOperation.class);
	@Override
	protected void handleColumnHasNoValue(String tableName, String columnName) {
		log.info(tableName + "[" + columnName + "]'s value is null.");
	}

}
