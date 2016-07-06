package com.zjlp.face.web.server.product.template.dao;

import java.util.List;

import com.zjlp.face.web.server.product.template.domain.Modular;

public interface ModularDao {

	void add(Modular modular);
	
	void edit(Modular modular);
	
	Modular getById(Long id);
	
	void delete(Long id);
	
	List<Modular> findModularByOwTemplateId(Long owTemplateId);
	
	void updateModularSort(Modular modular);
	
	Integer getModularMaxSort(Long owTemplateId);
	
	void deleteModularByOwTemplateId(Long owTemplateId);
	
	void editModular(Modular modular);
}
