package com.zjlp.face.web.server.product.template.dao;

import java.util.List;

import com.zjlp.face.web.server.product.template.domain.OwTemplate;

public interface OwTemplateDao {

	void add(OwTemplate owTemplate);
	
	void edit(OwTemplate owTemplate);
	
	OwTemplate getById(Long id);
	
	List<OwTemplate> getByShopNo(OwTemplate owTemplate);
	
	void delete(Long id);
	
	OwTemplate getOwTemplateByShopNoAndCode(OwTemplate owTemplate);
    
	OwTemplate getCurrent(OwTemplate owTemplate);
	
	void editStatus(OwTemplate owTemplate);

	Integer countTemplate(OwTemplate owTemplate);

}
