package com.zjlp.face.web.server.product.template.dao;

import com.zjlp.face.web.server.product.template.domain.TemplateDetail;

public interface TemplateDetailDao {

	void add(TemplateDetail templateDetail);
	
	void edit(TemplateDetail templateDetail);
	
	TemplateDetail getById(Long id);
	
	void delete(Long id);

	TemplateDetail getByTemplateId(Long tempalteId);
}
