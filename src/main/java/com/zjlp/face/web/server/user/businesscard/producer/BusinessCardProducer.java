package com.zjlp.face.web.server.user.businesscard.producer;

import com.zjlp.face.web.exception.ext.BusinessCardException;
import com.zjlp.face.web.server.user.businesscard.domain.CardCase;

public interface BusinessCardProducer {
	
	CardCase getCardCase(Long userId, Long cardId) throws BusinessCardException;
	
	boolean editCardPinyinByNickName(Long userId, String nickName) throws BusinessCardException;
}
