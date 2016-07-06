package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.product.template.domain.TemplateDetail;

public interface TemplateDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TemplateDetail record);

    int insertSelective(TemplateDetail record);

    TemplateDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TemplateDetail record);

    int updateByPrimaryKey(TemplateDetail record);

	TemplateDetail selectByTemplateId(Long tempalteId);
}