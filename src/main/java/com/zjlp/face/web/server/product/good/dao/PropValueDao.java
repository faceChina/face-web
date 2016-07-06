package com.zjlp.face.web.server.product.good.dao;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.PropValue;

public interface PropValueDao {
	
	void add(PropValue propValue);
	
	void edit(PropValue propValue);
	
	PropValue getById(Long id);
	
	void delete(Long id);

	List<PropValue> findPropValuesByPropId(Long propId);

}
