package com.zjlp.face.web.server.user.businesscard.producer.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.BusinessCardException;
import com.zjlp.face.web.server.user.businesscard.domain.CardCase;
import com.zjlp.face.web.server.user.businesscard.producer.BusinessCardProducer;
import com.zjlp.face.web.server.user.businesscard.service.BusinessCardService;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.service.UserService;

@Service("businessCardProducer")
public class BusinessCardProducerImpl implements BusinessCardProducer {

	private Logger log = Logger.getLogger(getClass());
	@Autowired
    private BusinessCardService businessCardService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public CardCase getCardCase(Long userId, Long cardId) throws BusinessCardException {
		return businessCardService.getCardCaseByUserId(userId,cardId);
	}

	@Override
	public boolean editCardPinyinByNickName(Long userId, String nickName)
			throws BusinessCardException {
		AssertUtil.notNull(userId, "Param[userId] can not be null.");
		AssertUtil.notNull(nickName, "Param[nickName] can not be null.");
		StringBuilder sb = new StringBuilder();
		log.info(sb.append("[editCardPinyinByNickName] begin, Param[userId=")
				.append(userId).append(", nickName=").append(nickName));
		User user = new User(userId);
		user.setNickname(nickName);
		user.setUpdateTime(new Date());
		userService.edit(user);
		log.info("[editCell] end.");
		return true;
	}

}
