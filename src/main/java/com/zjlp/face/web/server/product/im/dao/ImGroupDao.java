package com.zjlp.face.web.server.product.im.dao;

import java.util.List;

import com.zjlp.face.web.server.product.im.domain.ImGroup;

public interface ImGroupDao {

	void add(ImGroup imGroup);
	
	void edit(ImGroup imGroup);
	
	void deleteById(Long id);
	
	void deleteByUserId(Long userId);
	
	ImGroup getById(Long id);
	
	List<ImGroup> findList(ImGroup imGroup);
}
