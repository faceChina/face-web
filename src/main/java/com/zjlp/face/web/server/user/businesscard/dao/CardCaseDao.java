package com.zjlp.face.web.server.user.businesscard.dao;

import java.util.List;

import com.zjlp.face.web.server.user.businesscard.domain.BusinessCard;
import com.zjlp.face.web.server.user.businesscard.domain.CardCase;
import com.zjlp.face.web.server.user.businesscard.domain.dto.BusinessCardVo;

public interface CardCaseDao {
	
	//收藏名片夹
		Long addCardCase(CardCase cardCase);
		
	    CardCase getCardCaseByUserId(Long userId, Long cardId);
		
		BusinessCard selectByPrimaryKey(Long id);
		
		void deleteById(Long id);
		
		Integer selectCardCaseCount(BusinessCardVo vo);
		
		List<BusinessCardVo> queryCardCase(
	    		BusinessCardVo dto);
		
		CardCase selectCardCase(Long userId, Long cardid);
		
		List<BusinessCardVo> queryCardCase(Long userId);

}
