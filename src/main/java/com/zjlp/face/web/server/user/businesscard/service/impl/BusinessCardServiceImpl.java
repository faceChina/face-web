package com.zjlp.face.web.server.user.businesscard.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.errorcode.CErrMsg;
import com.zjlp.face.web.server.user.businesscard.dao.BusinessCardDao;
import com.zjlp.face.web.server.user.businesscard.dao.CardCaseDao;
import com.zjlp.face.web.server.user.businesscard.domain.BusinessCard;
import com.zjlp.face.web.server.user.businesscard.domain.CardCase;
import com.zjlp.face.web.server.user.businesscard.domain.dto.BusinessCardVo;
import com.zjlp.face.web.server.user.businesscard.service.BusinessCardService;

@Service("businessCardService")
public class BusinessCardServiceImpl implements BusinessCardService {
	
	@Autowired
	private BusinessCardDao businessCardDao;
	
	@Autowired
	private CardCaseDao cardCaseDao;

	@Override
	public Long addBusinessCard(BusinessCard record) {
		Date date = new Date();
		record.setCreateTime(date);
		record.setUpdateTime(date);
		if (null == record.getStatus()) {
			record.setStatus(Constants.VALID);
		}
		if (null == record.getPhoneVisibility()) {
			record.setPhoneVisibility(BusinessCard.PHONE_PUBLIC);
		}
		return businessCardDao.addBusinessCard(record);
	}

	@Override
	public void updateBusinessCard(BusinessCard businessCard) {
		Date date = new Date();
		businessCard.setUpdateTime(date);
		BusinessCard bc=businessCardDao.getById(businessCard.getId());
		businessCard.setUserId(bc.getUserId());
		businessCardDao.updateBusinessCard(businessCard);
	}

	@Override
	public BusinessCard getBusinessCardByUserId(Long userId) {
		return businessCardDao.getBusinessCardByUserId(userId);
	}

	//收藏名片夹
	@Override
	public Long addCardCase(CardCase cardCase) {
		//数据完整性验证
		AssertUtil.isTrue(CardCase.checkInput(cardCase), "数据验证未通过！");
		if (null == cardCase.getCreateTime()) {
			cardCase.setCreateTime(new Date());
		}
		//数据过滤，，是否存在这条数据
		CardCase casecard = cardCaseDao.selectCardCase(cardCase.getUserId(), cardCase.getCardId());
		AssertUtil.isNull(casecard, CErrMsg.ALREADY_EXISTS.getErrCd(), CErrMsg.ALREADY_EXISTS.cdByMsg(), "名片收藏记录");
		return cardCaseDao.addCardCase(cardCase);
	}

	@Override
	public CardCase getCardCaseByUserId(Long userId, Long cardId) {
		return cardCaseDao.getCardCaseByUserId(userId, cardId);
	}

	@Override
	public BusinessCard getById(Long id) {
		return businessCardDao.getById(id);
	}

	@Override
	public void deleteById(Long id) {
		cardCaseDao.deleteById(id);
	}

	@Override
	public Integer selectCardCaseCount(BusinessCardVo vo) {
		//参数验证
		AssertUtil.notNull(vo, "param[vo] can't be null.");
		AssertUtil.notNull(vo.getUserId(), "param[vo.userId] can't be null.");
		return cardCaseDao.selectCardCaseCount(vo);
	}

	@Override
	public List<BusinessCardVo> queryCardCase(BusinessCardVo vo) {
		//参数验证
		AssertUtil.notNull(vo, "param[vo] can't be null.");
		AssertUtil.notNull(vo.getUserId(), "param[vo.userId] can't be null.");
		return cardCaseDao.queryCardCase(vo);
	}

	@Override
	public List<BusinessCardVo> queryCardCase(Long userId) {
		return cardCaseDao.queryCardCase(userId);
	}

}
