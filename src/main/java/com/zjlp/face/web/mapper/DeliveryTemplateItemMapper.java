package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.DeliveryTemplateItem;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateItemDto;

public interface DeliveryTemplateItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeliveryTemplateItem record);

    int insertSelective(DeliveryTemplateItem record);

    DeliveryTemplateItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeliveryTemplateItem record);

    int updateByPrimaryKey(DeliveryTemplateItem record);

	List<DeliveryTemplateItemDto> selectByTemplateId(Long templateId);

	void deleteByTemplateId(Long templateId);
}