package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.product.template.domain.OwTemplate;

public interface OwTemplateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OwTemplate record);

    int insertSelective(OwTemplate record);

    OwTemplate selectByPrimaryKey(Long id);
    
    List<OwTemplate> selectByShopNo(OwTemplate owTemplate);

    int updateByPrimaryKeySelective(OwTemplate record);

    int updateByPrimaryKey(OwTemplate record);
    
	OwTemplate selectByShopNoAndCode(OwTemplate record);
    
    OwTemplate selectCurrent(OwTemplate owTemplate);
    
    void updateStatus(OwTemplate owTemplate);

	Integer countTemplate(OwTemplate owTemplate);
}