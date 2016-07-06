package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.user.businesscard.domain.BusinessCard;


public interface BusinessCardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusinessCard record);

    int insertSelective(BusinessCard record);

    BusinessCard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusinessCard record);

    int updateByPrimaryKey(BusinessCard record);

	BusinessCard getBusinessCardByUserId(Long userId);

	void updateBusinessCard(BusinessCard businessCard);
}