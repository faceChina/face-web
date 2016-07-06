package com.zjlp.face.web.component.base;

import java.lang.reflect.Method;

import org.apache.ibatis.session.SqlSession;

public abstract class BaseDaoImpl<PK, T> implements BaseDao<PK, T> {

	public abstract SqlSession getSqlSession();
	
	public abstract Class<?> getMapperClass();
	
	@Override
	public void insert(T obj) throws Exception {
		
		_callMethod("insert",obj);
	}

	@Override
	public void insertSelective(T obj) throws Exception {
		
		_callMethod("insertSelective",obj);
	}

	@Override
	public void updateByPrimaryKeySelective(T obj) throws Exception{
		
		_callMethod("updateByPrimaryKeySelective",obj);
	}

	@Override
	public void updateByPrimaryKey(T obj) throws Exception{
		
		_callMethod("updateByPrimaryKeySelective",obj);
	}

	@Override
	public void deleteByPrimaryKey(PK id) throws Exception{
		
		_callMethod("deleteByPrimaryKey",id);
	}

	@Override
	public T selectByPrimaryKey(PK id) throws Exception{
		
		return _callMethod("selectByPrimaryKey",id);
	}
	
	@SuppressWarnings("unchecked")
	private T _callMethod(String methodName,Object param) throws Exception{
		
		 try {
			 
			Object objectMapper = getSqlSession().getMapper(getMapperClass());
			
			Class<?> parameterTypes = param.getClass();
			
			Method method = objectMapper.getClass().getMethod(methodName, parameterTypes);
			
			return (T) method.invoke(objectMapper, param);
			
		} catch (Exception e) {
			
			throw new Exception("ERROR _callMethod ["+methodName+"];"+e.getMessage(),e);
		}
	}

}
