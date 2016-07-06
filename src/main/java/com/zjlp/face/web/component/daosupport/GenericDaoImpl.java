package com.zjlp.face.web.component.daosupport;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;


public class GenericDaoImpl implements GenericDao {
	
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	private SqlSession sqlSession;
	
	private Class<?> type;
	private String typeStr;
	
	public GenericDaoImpl() {
	}

	public GenericDaoImpl(Class<?> type) {
		this.type = type;
	}

	public GenericDaoImpl(String type) {
		try {
			Class<?> clazz = Class.forName(type);
			this.type = clazz;
		} catch (ClassNotFoundException e) {
			this.type = null;
			this.typeStr = type;
		}
	}

	@SuppressWarnings("unchecked")
	private <T> T _callMethod(Method method,Object... param) throws DataAccessException{
		
		 try {
			 
			Object objectMapper = getSqlSession().getMapper(type);
			
			return (T) method.invoke(objectMapper, param);
			
		} catch (Exception e) {
			
			throw new RuntimeException("ERROR _callMethod ["+method.getName()+"];"+e.getMessage(),e);
		}
	}
	
	@Override
	public Object excuteInsert(Method method, Map<String, Object> parameterMap)
			throws DataAccessException {
		String statementName = this.getStatementNameFromMethod(method);
		return sqlSession.insert(statementName,parameterMap);
	}

	@Override
	public int excuteUpdate(Method method, Map<String, Object> parameterMap)
			throws DataAccessException {
		String statementName = this.getStatementNameFromMethod(method);
		return sqlSession.update(statementName, parameterMap);
	}

	@Override
	public int excuteDelete(Method method, Map<String, Object> parameterMap)
			throws DataAccessException {
		String statementName = this.getStatementNameFromMethod(method);
		return sqlSession.delete(statementName, parameterMap);
	}

	@Override
	public List<Object> excuteQuery(Method method, Map<String, Object> parameterMap)
			throws DataAccessException {
		String statementName = this.getStatementNameFromMethod(method);
		return sqlSession.selectList(statementName, parameterMap);
	}

	@Override
	public Object excuteGet(Method method, Map<String, Object> parameterMap)
			throws DataAccessException {
//		return _callMethod(method,parameterMap);
		String statementName = this.getStatementNameFromMethod(method);
		return sqlSession.selectOne(statementName, parameterMap);
	}
	

	private String getStatementNameFromMethod(Method method) {
		return this.getTypeName() + "." + method.getName();
	}

	private String getTypeName() {
		if (null != this.type) {
			return this.type.getCanonicalName();
		} else if (null != this.typeStr && this.typeStr.length() > 0) {
			return this.typeStr;
		} else {
			return "";
		}
	}
	
	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
}