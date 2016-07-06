package com.zjlp.face.web.server.user.bankcard.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.BankCardException;
import com.zjlp.face.web.server.user.bankcard.dao.BankCardDao;
import com.zjlp.face.web.server.user.bankcard.domain.BankCard;
import com.zjlp.face.web.server.user.bankcard.service.BankCardService;

@Service
public class BankCardServiceImpl implements BankCardService {

	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private BankCardDao bankCardDao;
	
	@Override
	public Long addBankCard(BankCard bankCard) throws BankCardException {
		try {
			AssertUtil.notNull(bankCard, "Param[bankCard] can not be null.");
			StringBuilder sb = new StringBuilder();
			log.info(sb.append("[addBankCard] Add bank card begin.")
					.append(String.valueOf(bankCard)));
			//验证
			this.check(bankCard);
			//相同银行卡过滤
			this.filter(bankCard);
			//创建时间
			Date date = new Date();
			bankCard.setUpdateTime(date);
			bankCard.setCreateTime(date);
			Long id = bankCardDao.addBankCard(bankCard);
			log.info(sb.delete(0, sb.length()).append("[addBankCard] Add bank card end, id=")
					.append(id).append("]"));
			return id;
		} catch (Exception e) {
			log.error("[addBankCard] Add bank card faild.", e);
			throw new BankCardException(e);
		}
	}

	@Override
	public BankCard getValidBankCardById(Long id) throws BankCardException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			BankCard bankCard = bankCardDao.getBankCardById(id);
			AssertUtil.notNull(bankCard, "BankCard[id={}] is not exists.", id);
			return bankCard;
		} catch (Exception e) {
			log.error("[getValidBankCardById] Get bank card faild.", e);
			throw new BankCardException(e);
		}
	}


	@Override
	public boolean invalidateBankCard(Long id) throws BankCardException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			StringBuilder sb = new StringBuilder();
			log.info(sb.append("[invalidateBankCard] Remove bankcard[id=").append(id).append("] begin"));
			bankCardDao.removeBankCardById(id);
			log.info(sb.append("[invalidateBankCard] Remove bankcard successful."));
			return true;
		} catch (Exception e) {
			log.error("[invalidateBankCard] Invalidate bank card faild.", e);
			throw new BankCardException(e);
		}
	}

	@Override
	public List<BankCard> findPayCardList(Long userId) throws BankCardException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			List<BankCard> cardList = this.findValidBankCardList(String.valueOf(userId), 
					BankCard.REMOTE_TYPE_USER, null, BankCard.USERFOR_PAY);
			return cardList;
		} catch (Exception e) {
			log.error("[findPayCardList] Find bank card list for pay faild.", e);
			throw new BankCardException(e);
		}
	}

	@Override
	public List<BankCard> findSettleCardList(Long userId)
			throws BankCardException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			List<BankCard> cardList = this.findValidBankCardList(String.valueOf(userId), 
					BankCard.REMOTE_TYPE_USER, null, BankCard.USERFOR_SETTLE);
			return cardList;
		} catch (Exception e) {
			log.error("[findSettleCardList] Find bank card list for settle faild.", e);
			throw new BankCardException(e);
		}
	}

	@Override
	public BankCard getBankCardByPk(Long id) throws BankCardException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			BankCard card = bankCardDao.getBankCardByPk(id);
			return card;
		} catch (Exception e) {
			log.error("[getBankCardByPk] Get bank card faild.", e);
			throw new BankCardException(e);
		}
	}
	
	@Override
	public BankCard getDefaultBankCardByUserId(Long userId){
		return bankCardDao.getDefaultBankCardByUserId(userId);
	}
	
	@Override
	public BankCard getDefaultBankCard(String remoteId, Integer remoteType,
			Integer type) throws BankCardException {
		try {
			AssertUtil.notNull(remoteId, "Param[remoteId] can not be null.");
			AssertUtil.notNull(remoteType, "Param[remoteType] can not be null.");
			AssertUtil.notNull(type, "Param[type] can not be null.");
			BankCard card = new BankCard(remoteId, remoteType);
			card.setType(type);
			card.setIsDefault(Constants.ISDEFAULT);
			card.setStatus(Constants.VALID);
			List<BankCard> list = bankCardDao.getCardForDfType(card);
			if (null == list || list.isEmpty()) {
				return null;
			}
			AssertUtil.isTrue(1 <= list.size(), 
					"Default card[remoteType={},remoteId={},type={}]'s size is more than one."
					,remoteType, remoteId, type);
			return list.get(0);
		} catch (Exception e) {
			log.error("[getDefaultBankCard] Get default bank card faild.", e);
			throw new BankCardException(e);
		}
	}
	
	@Override
	public BankCard getDefaultBankCard(Long userId, Integer type)
			throws BankCardException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			AssertUtil.notNull(type, "Param[type] can not be null.");
			return this.getDefaultBankCard(String.valueOf(userId), BankCard.REMOTE_TYPE_USER, type);
		} catch (Exception e) {
			log.error("[getDefaultBankCard] Get default bank card faild.", e);
			throw new BankCardException(e);
		}
	}

	@Override
	@Transactional
	public boolean setDefaultBankCard(Long id, Long userId) throws BankCardException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			AssertUtil.notNull(userId, "Param[userId] can not be null.");
			BankCard defaultCard = this.getDefaultBankCardByUserId(userId);
			if (null != defaultCard) {
				this.setCardDfType(defaultCard.getId(), Constants.NOTDEFAULT);
			}
			this.setCardDfType(id, Constants.ISDEFAULT);
			return true;
		} catch (Exception e) {
			log.error("[setDefaultBankCard] Set default bank card faild.", e);
			throw new BankCardException(e);
		}
	}
	
	/**
	 * 设置银行卡的默认类型
	 * 
	 * @Title: setCardDfType
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            银行卡id
	 * 
	 * @param isDefault
	 *            默认类型
	 * @date 2015年3月13日 上午10:05:36
	 * @author lys
	 */
	private void setCardDfType(Long id, Integer isDefault) {
		BankCard bankCard = new BankCard(id);
		bankCard.setIsDefault(isDefault);
		bankCard.setUpdateTime(new Date());
		bankCardDao.setCardDfTypeById(bankCard);
	}

	/**
	 * 查找银行卡列表
	 * 
	 * @Title: findValidBankCardList
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param remoteId
	 *            外键id
	 * @param remoteType
	 *            外键类型
	 * @param bankType
	 *            银行卡类型
	 * @param type
	 *            银行卡使用类型
	 * @return
	 * @date 2015年3月13日 上午9:17:28
	 * @author lys
	 */
	private List<BankCard> findValidBankCardList(String remoteId, Integer remoteType, 
			Integer bankType, Integer type) {
		try {
			AssertUtil.notNull(remoteId, "Param[remoteId] can not be null.");
			AssertUtil.notNull(remoteType, "Param[remoteType] can not be null.");
			BankCard bankCard = new BankCard(remoteId, remoteType);
			bankCard.setBankType(bankType);
			bankCard.setType(type);
			bankCard.setStatus(Constants.VALID);
			List<BankCard> cardList = bankCardDao.findBankCardList(bankCard);
			return cardList;
		} catch (RuntimeException e) {
			log.error("[findValidBankCardList] Find bank card list faild.", e);
			throw new BankCardException(e);
		}
	}
	
	/**
	 * 数据一致性验证
	 * 
	 * @Title: check
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param bankCard
	 *            银行卡
	 * @date 2015年3月13日 上午9:26:15
	 * @author lys
	 */
	private void check(BankCard bankCard) {
		AssertUtil.notNull(bankCard, "Bankcard can not be null.");
		AssertUtil.notNull(bankCard.getRemoteId(), "[CHECK ERROR] BankCard.remoteId is null.");
		AssertUtil.notNull(bankCard.getRemoteType(), "[CHECK ERROR] BankCard.remoteType is null.");
		AssertUtil.notNull(bankCard.getBankCard(), "[CHECK ERROR] BankCard.bankCard is null.");
		AssertUtil.notNull(bankCard.getBankName(), "[CHECK ERROR] BankCard.bankName is null.");
		AssertUtil.notNull(bankCard.getName(), "[CHECK ERROR] BankCard.name is null.");
		AssertUtil.notNull(bankCard.getBankType(), "[CHECK ERROR] BankCard.bankType is null.");
		AssertUtil.notNull(bankCard.getType(), "[CHECK ERROR] BankCard.type is null.");
		AssertUtil.notNull(bankCard.getStatus(), "[CHECK ERROR] BankCard.status is null.");
		AssertUtil.notNull(bankCard.getIdentity(), "[CHECK ERROR] BankCard.identity is null.");
		//AssertUtil.notNull(bankCard.getPayType(), "[CHECK ERROR] BankCard.payType is null.");
		AssertUtil.notNull(bankCard.getIsDefault(), "[CHECK ERROR] BankCard.isDefault is null.");
		if (BankCard.TYPE_CREDIT.equals(bankCard.getBankType())) {
			AssertUtil.notNull(bankCard.getEndTime(), "[CHECK ERROR] BankCard.endTime is null.");
			AssertUtil.notNull(bankCard.getCvv(), "[CHECK ERROR] BankCard.cvv is null.");
		}
		if (BankCard.USERFOR_PAY.equals(bankCard.getBankType())) {
			AssertUtil.notNull(bankCard.getCell(), "[CHECK ERROR] BankCard.cell is null.");
		}
	}

	/**
	 * 过滤相同的银行卡
	 * 
	 * @Title: filter
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param bankCard
	 *            过滤对象
	 * @date 2015年3月13日 上午10:02:22
	 * @author lys
	 */
	private void filter(BankCard bankCard) {
		BankCard card = this.getIdenticalValidCard(bankCard.getRemoteId(),
				bankCard.getRemoteType(), bankCard.getBankCard(),
				bankCard.getBankCode(), bankCard.getBankType(),
				bankCard.getType());
		AssertUtil.isNull(card, "BankCard[remoteType={},remoteId={},cardNo={},bankCode={},bankType={},type={}] is already exists.",
				"该卡已存在", bankCard.getRemoteType(), bankCard.getRemoteId(), bankCard.getBankCard()
				,bankCard.getBankCode(), bankCard.getBankType(), bankCard.getType());
	}

	@Override
	public boolean editCardStatus(Long id, Integer status)
			throws BankCardException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			AssertUtil.notNull(status, "Param[status] can not be null.");
			StringBuilder sb = new StringBuilder();
			log.info(sb.append("[editCardStatus] Edit status begin, Param[id=").append(id)
					.append(", status=").append(status).append("]"));
			BankCard bankCard = new BankCard(id);
			bankCard.setStatus(status);
			bankCard.setUpdateTime(new Date());
			bankCardDao.editCardStatusById(bankCard);
			log.info(sb.append("[editCardStatus] Edit status successful."));
			return true;
		} catch (Exception e) {
			log.error("[editCardStatus] Edit bankcard' status faild.", e);
			throw new BankCardException(e);
		}
	}

	@Override
	public boolean setCardNoAgree(Long id, String noAgree)
			throws BankCardException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			AssertUtil.notNull(noAgree, "Param[noAgree] can not be null.");
			StringBuilder sb = new StringBuilder();
			log.info(sb.append("[setCardNoAgree] Edit status noAgree, Param[id=").append(id)
					.append(", noAgree=").append(noAgree).append("]"));
			BankCard bankCard = new BankCard(id);
			bankCard.setNoAgree(noAgree);
			bankCard.setUpdateTime(new Date());
			bankCardDao.editCardNoAgreeById(bankCard);
			log.info(sb.append("[setCardNoAgree] Edit noAgree successful."));
			return true;
		} catch (Exception e) {
			log.error("[setCardNoAgree] Edit bankcard' noAgree faild.", e);
			throw new BankCardException(e);
		}
	}

	@Override
	public BankCard getIdenticalValidCard(String remoteId, Integer remoteType,
			String cardNo, String bankCode, Integer bankType, Integer type)
			throws BankCardException {
		try {
			AssertUtil.notNull(remoteId, "Param[remoteId] can not be null.");
			AssertUtil.notNull(remoteType, "Param[remoteType] can not be null.");
			AssertUtil.notNull(cardNo, "Param[cardNo] can not be null.");
			AssertUtil.notNull(type, "Param[type] can not be null.");
			BankCard bankCard = new BankCard(remoteId, remoteType);
			bankCard.setBankCard(cardNo);
			bankCard.setBankCode(bankCode);
			bankCard.setBankType(bankType);
			bankCard.setType(type);
			List<BankCard> cardList = bankCardDao.findIdenticalValidCard(bankCard);
			if (null == cardList || cardList.isEmpty() ) {
				return null;
			}
			AssertUtil.isTrue(cardList.size() == 1, 
					"BankCard[remoteType={},remoteId={},cardNo={},bankCode={},bankType={},type={}] is not identical(size={}).",
					remoteType, remoteId, cardNo, bankCode, bankType, type, cardList.size());
			return cardList.get(0);
		} catch (Exception e) {
			log.error("[getIdenticalValidCard] Find identical & valid bank card faild.", e);
			throw new BankCardException(e);
		}
	}

	@Override
	public BankCard getIdenticalValidCard(String remoteId, Integer remoteType,
			String cardNo, Integer type) throws BankCardException {
		//一般情况下，固定的卡号对应唯一的银行卡类型和银行卡编号
		return this.getIdenticalValidCard(remoteId, remoteType, cardNo, null, null, type);
	}

	@Override
	public int getCardNumber(String remoteId, Integer remoteType, Integer type) {
		try {
			BankCard card = new BankCard(remoteId, remoteType);
			card.setType(type);
			return bankCardDao.getCardNumber(card);
		} catch (Exception e) {
			throw new BankCardException(e);
		}
	}

	@Override
	public void editBindId(Long cardId, String bindId) {
		AssertUtil.notNull(cardId, "Param[cardId] can not be null.");
		AssertUtil.notNull(bindId, "Param[bindId] can not be null.");
		BankCard card = new BankCard(cardId);
		card.setBindId(bindId);
		card.setUpdateTime(new Date());
		bankCardDao.edit(card);
	}

	@Override
	public BankCard getBankCardByCardNoAndUserId(String bankCard, Long userId) {
		BankCard query=new BankCard();
		query.setBankCard(bankCard);
		query.setRemoteId(String.valueOf(userId));
		query.setRemoteType(1);
		return bankCardDao.getBankCard(query);
	}

	@Override
	public void editBankCard(BankCard bankCard) {
		bankCardDao.edit(bankCard);
	}

	@Override
	public List<BankCard> findBankCardListByUserId(Long userId) {
		return bankCardDao.findBankCardListByUserId(userId);
	}
	
	
}
