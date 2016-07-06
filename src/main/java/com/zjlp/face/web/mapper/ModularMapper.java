package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.product.template.domain.Modular;

public interface ModularMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Modular record);

    int insertSelective(Modular record);

    Modular selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Modular record);

    int updateByPrimaryKey(Modular record);

	List<Modular> selectByOwTemplateId(Long owTemplateId);

	void updateModularSort(Modular modular);

	Integer getModularMaxSort(Long owTemplateId);

	void deleteByOwTemplateId(Long owTemplateId);

	void editModular(Modular modular);
}