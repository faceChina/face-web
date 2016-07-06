package com.zjlp.face.web.server.user.businesscard.service;

import java.util.List;

import com.zjlp.face.web.server.user.businesscard.domain.BusinessCard;
import com.zjlp.face.web.server.user.businesscard.domain.CardCase;
import com.zjlp.face.web.server.user.businesscard.domain.dto.BusinessCardVo;

public interface BusinessCardService {

	Long addBusinessCard(BusinessCard record);

	void updateBusinessCard(BusinessCard businessCard);

	BusinessCard getBusinessCardByUserId(Long userId);
	
	BusinessCard getById(Long id);

	//收藏名片夹
	Long addCardCase(CardCase cardCase);
	
	CardCase getCardCaseByUserId(Long userId,Long cardId);
	
	void deleteById(Long id);
	
	Integer selectCardCaseCount(BusinessCardVo vo);
	
	List<BusinessCardVo> queryCardCase(
    		BusinessCardVo vo);
	
	List<BusinessCardVo> queryCardCase(Long userId);
}
