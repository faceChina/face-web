package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.DeliveryTemplate;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateDto;

public interface DeliveryTemplateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeliveryTemplate record);

    int insertSelective(DeliveryTemplate record);

    DeliveryTemplate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeliveryTemplate record);

    int updateByPrimaryKey(DeliveryTemplate record);

	List<DeliveryTemplateDto> selectListByShopNo(String shopNo);

	DeliveryTemplate selectByIdAndShopNo(DeliveryTemplate template);
}