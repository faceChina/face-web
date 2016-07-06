package org.dbunit.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dbunit.tools.writer.FileWrite;
import org.springframework.util.Assert;

/**
 * 提供两个功能
 * 
 * 1.生成dbunit所需要的导入数据格式
 * 
 * 2.根据数据库中的数据生成一份text的数据报表（可能需要优化生成一份excel的报表）
 * 
 * @ClassName: DataFileCreator 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年7月10日 下午2:19:58
 */
public class DataFileCreator {
	
	private Logger log = Logger.getLogger(DataFileCreator.class);
    private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static final String space = " ";
    private static final String empty_value="''";
    private static final String equal = "=";
    private static final String left = "<";
    private static final String right = "/>";
    private Connection conn = null;
    private List<String> tableNames = new ArrayList<String>();
    private Map<String, List<String>> map = new HashMap<String, List<String>>();
    private Map<String, List<String>> rowsMap = new HashMap<String, List<String>>();
    
    public DataFileCreator(String url, String user, String passwd){
    	try {
			Class.forName(DRIVER_CLASS).newInstance();
			conn = DriverManager.getConnection(url, user, passwd);
			if (!conn.isClosed()) {
				log.info("Succeeded connecting to MySQL!");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    public DataFileCreator(String url, String user, String passwd, String...tableNames){
    	this(url, user, passwd);
    	this.withTableNames(tableNames);
    }
     
    private List<String> getTableColums(String tableName) {
    	try {
			String sql = "SHOW FIELDS FROM " + tableName + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<String> columnList = new ArrayList<String>();
			String columnName = null;
			while (rs.next()) {
				columnName = rs.getString(1);
			    columnList.add(columnName);
			}
			return columnList;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    private void initColumns(List<String> tableNames) {
    	Assert.notEmpty(tableNames, "please input at least 1 table.");
    	for (String tableName : tableNames) {
    		map.put(tableName, this.getTableColums(tableName));
		}
    }
    
    private void initRows(List<String> tableNames, Map<String, List<String>> map) {
    	Assert.notEmpty(tableNames, "please input at least 1 table.");
    	for (String tableName : tableNames) {
    		rowsMap.put(tableName, this.getTableRowDatas(tableName, map.get(tableName)));
		}
    }
    
    public DataFileCreator withTableNames(String...tableNames) {
    	if (null != tableNames) {
    		for (String tableName : tableNames) {
    			this.tableNames.add(tableName);
			}
    	}
    	return this;
    }
    
    /**
     * 功能1
     * @Title: create 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param filePath
     * @date 2015年7月10日 下午2:22:24  
     * @author lys
     */
    public void create(String filePath) {
    	try {
			this.initColumns(tableNames);
			FileWrite fileWrite = new FileWrite(filePath);
			fileWrite.append("<?xml version='1.0' encoding='UTF-8'?>\n<dataset>");
			Iterator<Entry<String, List<String>>> iterator = map.entrySet().iterator();
			String tableName = null;
			List<String> columnNames = null;
			Entry<String, List<String>> entry = null;
			while(iterator.hasNext()) {
				entry = iterator.next();
				tableName = entry.getKey();
				columnNames = entry.getValue();
				fileWrite.next(space).append(space).append(left).append(tableName.toUpperCase());
				for (String column : columnNames) {
					fileWrite.append(space).append(column.toUpperCase()).append(equal).append(empty_value);
				}
				fileWrite.append(space).append(right);
				fileWrite.next(space);
			}
			fileWrite.next("</dataset>");
			fileWrite.write();
		} catch (RuntimeException e) {
			log.info(e.getMessage());
		} finally {
			this.closeMySQLConn();
		}
    }
    
    private void closeMySQLConn() {
        if(conn != null){
            try {
                conn.close();
                log.info("Database connection terminated!");
            } catch (SQLException e) {
                log.info("close conn failed.", e);
            }
        }
    }
    
    private List<String> getTableRowDatas(String tableName, List<String> columns) {
    	try {
			String sql = "SELECT * FROM " + tableName + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<String> tableRows = new ArrayList<String>();
			Row row = new Row();
			while (rs.next()) {
				for (String columnName : columns) {
					row.append(rs.getString(columnName));
				}
				tableRows.add(row.flush());
			}
			return tableRows;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    private String getColumnString(List<String> columnList) {
    	Row row = new Row();
		for (String column : columnList) {
			row.append(column);
		}
		return row.flush();
    }
    
    /**
     * 功能2
     * @Title: createTableValFile 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param filePath
     * @date 2015年7月10日 下午2:22:06  
     * @author lys
     */
    public void createTableValFile(String filePath) {
    	try {
			this.initColumns(tableNames);
			this.initRows(tableNames, map);
			FileWrite fileWrite = new FileWrite(filePath);
			Iterator<Entry<String, List<String>>> iterator = rowsMap.entrySet().iterator();
			String tableName = null;
			List<String> rowsData = null;
			Entry<String, List<String>> entry = null;
			while(iterator.hasNext()) {
				entry = iterator.next();
				tableName = entry.getKey();
				rowsData = entry.getValue();
				fileWrite.next(tableName);
				//列名
				fileWrite.next(this.getColumnString(map.get(tableName)));
				//值
				for (String rowValue : rowsData) {
					fileWrite.next(rowValue);
				}
			}
			fileWrite.write();
		} catch (RuntimeException e) {
			log.info(e.getMessage());
		} finally {
			this.closeMySQLConn();
		}
    }
    
    static class Row {
    	private static final String null_value = " ";
    	private static final Integer count = 40;
    	private StringBuilder rowData = new StringBuilder();
    	public Row append(String unitData) {
    		if (null != unitData && unitData.length() > 35){
    			rowData.append(unitData.substring(0, 35));
    		} else {
    			rowData.append(unitData);
    		}
    		Integer subCount = count - (null == unitData ? 0 : unitData.length());
    		if (subCount > 0) {
    			for(int i = 0; i < subCount; i++) {
    				rowData.append(null_value);
    			}
    		}
    		rowData.append(",");
    		return this;
    	}
    	public String flush() {
    		String data = rowData.toString();
    		rowData.delete(0, rowData.length());
    		return data;
    	}
    }
    
    public static void main(String[] args) {
		DataFileCreator creator = new DataFileCreator(
				"jdbc:mysql://localhost:3306/test", "root", "root");
		creator.withTableNames("user");
		creator.createTableValFile("D:/test.xml");
	}
     
}