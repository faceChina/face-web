package com.zjlp.face.web.component.daosupport;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface GenericDao {

	/**
	 * 新增
	 * @Title: excuteInsert 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param method
	 * @param parameterMap
	 * @return
	 * @throws DataAccessException
	 * @date 2015年1月6日 下午8:21:07  
	 * @author Administrator
	 */
    public Object excuteInsert(Method method, Map<String, Object> parameterMap) throws DataAccessException;
    
    /**
     * 调用修改方法
     * @Title: excuteUpdate 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param method
     * @param parameterMap
     * @return
     * @throws DataAccessException
     * @date 2015年1月6日 下午8:21:13  
     * @author Administrator
     */
    public int excuteUpdate(Method method, Map<String, Object> parameterMap) throws DataAccessException;
    
    /**
     * 调用删除方法
     * @Title: excuteDelete 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param method
     * @param parameterMap
     * @return
     * @throws DataAccessException
     * @date 2015年1月6日 下午8:21:21  
     * @author Administrator
     */
    public int excuteDelete(Method method, Map<String, Object> parameterMap) throws DataAccessException;
    
    /**
     * 单个查询
     * @Title: excuteGet 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param method
     * @param parameterMap
     * @return
     * @throws DataAccessException
     * @date 2015年1月6日 下午8:23:02  
     * @author Administrator
     */
    public Object excuteGet(Method method, Map<String, Object> parameterMap) throws DataAccessException;
    
    /**
     * 调用查询多个 
     * @Title: excuteQuery 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param method
     * @param parameterMap
     * @return
     * @throws DataAccessException
     * @date 2015年1月6日 下午8:21:33  
     * @author Administrator
     */
    public List<Object> excuteQuery(Method method, Map<String, Object> parameterMap) throws DataAccessException;
    

}