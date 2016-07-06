package com.zjlp.face.web.server.user.businesscard.dao;

import com.zjlp.face.web.server.user.businesscard.domain.BusinessCard;

public interface BusinessCardDao {

	Long addBusinessCard(BusinessCard record);

	void updateBusinessCard(BusinessCard businessCard);

	BusinessCard getBusinessCardByUserId(Long userId);

	BusinessCard getById(Long id);
    
	
}
