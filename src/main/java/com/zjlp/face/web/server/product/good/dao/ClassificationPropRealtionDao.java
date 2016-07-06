package com.zjlp.face.web.server.product.good.dao;

import com.zjlp.face.web.server.product.good.domain.ClassificationPropRealtion;

public interface ClassificationPropRealtionDao {

	void add(ClassificationPropRealtion classificationPropRealtion);
	
	void edit(ClassificationPropRealtion classificationPropRealtion);
	
	ClassificationPropRealtion getById(Long id);
	
	void delete(Long id);
}
