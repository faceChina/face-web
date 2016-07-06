package com.zjlp.face.web.component.base;



public interface BaseDao<PK,T> {
	
	void insert(T obj) throws Exception ;

	void insertSelective(T obj) throws Exception ;
	
	void updateByPrimaryKeySelective(T obj) throws Exception ;

	void updateByPrimaryKey(T obj) throws Exception ;

	void deleteByPrimaryKey(PK id) throws Exception ;

	T selectByPrimaryKey(PK id) throws Exception ;
	

}
