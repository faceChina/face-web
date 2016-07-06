package com.zjlp.face.web.server.operation.member.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.MemberException;
import com.zjlp.face.web.server.operation.member.dao.ConsumptionAdjustmentRecordDao;
import com.zjlp.face.web.server.operation.member.dao.IntegralRecodeDao;
import com.zjlp.face.web.server.operation.member.dao.MemberAttendanceRecordDao;
import com.zjlp.face.web.server.operation.member.dao.MemberCardDao;
import com.zjlp.face.web.server.operation.member.dao.MemberEnactmentDao;
import com.zjlp.face.web.server.operation.member.dao.MemberWechatRelationDao;
import com.zjlp.face.web.server.operation.member.dao.SendIntegralRecodeDao;
import com.zjlp.face.web.server.operation.member.domain.ConsumptionAdjustmentRecord;
import com.zjlp.face.web.server.operation.member.domain.IntegralRecode;
import com.zjlp.face.web.server.operation.member.domain.MemberAttendanceRecord;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.domain.MemberEnactment;
import com.zjlp.face.web.server.operation.member.domain.MemberWechatRelation;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberAttendanceRecordDto;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberCardDto;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberEnactmentDto;
import com.zjlp.face.web.server.operation.member.domain.dto.SendIntegralRecodeDto;
import com.zjlp.face.web.server.operation.member.service.MemberService;
import com.zjlp.face.web.server.trade.payment.dao.MemberTransactionRecordDao;
import com.zjlp.face.web.server.trade.payment.domain.MemberTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.MemberTransactionRecordDto;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	
	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private MemberEnactmentDao memberEnactmentDao;
	@Autowired
	private MemberCardDao memberCardDao;
	@Autowired
	private ConsumptionAdjustmentRecordDao consumptionAdjustmentRecordDao;
	@Autowired
	private MemberAttendanceRecordDao memberAttendanceRecordDao;
	@Autowired
	private IntegralRecodeDao integralRecodeDao;
	@Autowired
	private MemberTransactionRecordDao memberTransactionRecordDao;
	@Autowired
	private SendIntegralRecodeDao sendIntegralRecodeDao;
	@Deprecated
	@Autowired
	private MemberWechatRelationDao memberWechatRelationDao;
	

	@Override
	@Transactional
	public Long addMemberEnactment(Long settleId) throws MemberException {
		try {
			AssertUtil.notNull(settleId, "Param[settleId] can't be null.");
			MemberEnactment enactment = this.getBySettleId(settleId);
			AssertUtil.isNull(enactment, "MemberEnactment[settleId={}] is already exists.", settleId);
			enactment = MemberEnactmentDto.initMemberEnactment(settleId);
			MemberEnactmentDto.checkInput(enactment);
			Date date = new Date();
			enactment.setUpdateTime(date);
			enactment.setCreateTime(date);
			Long id = memberEnactmentDao.add(enactment);
			log.info(new StringBuilder("Add ").append(enactment.toString()).append(" is end."));
			return id;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public MemberEnactment getEnactmentBySettleId(Long settleId)
			throws MemberException {
		try {
			AssertUtil.notNull(settleId, "Param[settleId] can't be null.");
			MemberEnactment enactment = this.getBySettleId(settleId);
			AssertUtil.notNull(enactment, "MemberEnactment[settleId={}] can't be null.", settleId);
			return enactment;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
	private MemberEnactment getBySettleId(Long settleId) {
		return memberEnactmentDao.getBySellerId(settleId);
	}

	@Override
	public boolean editEnactment(MemberEnactment enactment, Long sellerId)
			throws MemberException {
		try {
			//参数验证
			AssertUtil.notNull(enactment, "Param[enactment] can't be null.");
			AssertUtil.notNull(enactment.getId(), "Param[enactment.id] can't be null.");
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			MemberEnactmentDto.checkInput(enactment);
			//数据权限验证
			MemberEnactment old = memberEnactmentDao.getValidById(enactment.getId());
			AssertUtil.notNull(old, "MemberEnactment[id={}] is not exists.", enactment.getId());
			AssertUtil.isTrue(sellerId.equals(old.getSellerId()), 
					"Expect the value of MemberEnactment[id={}]'s sellerId is {}, but is {}.",
					enactment.getId(), sellerId, old.getSellerId());
			//更新
			enactment.setUpdateTime(new Date());
			memberEnactmentDao.edit(enactment);
			return true;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public Long addMemberCard(MemberCard membershipCard)
			throws MemberException {
		try {
			//参数验证
			AssertUtil.notNull(membershipCard, "Param[membershipCard] can't be null.");
			this.checkMemberCard(membershipCard);
			//过滤重复
//			MemberCardDto dto = this.getMemberCard(membershipCard.getSellerId(), 
//					membershipCard.getUserId());
//			AssertUtil.isNull(dto, "MembershipCard[settleId={}, userId={}] is already exists.");
			//添加
			Date date = new Date();
			membershipCard.setUpdateTime(date);
			membershipCard.setCreateTime(date);
			memberCardDao.add(membershipCard);
			log.info(new StringBuilder("Add ").append(membershipCard.toString()).append(" end."));
			return membershipCard.getId();
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	private void checkMemberCard(MemberCard membershipCard) {
		AssertUtil.notNull(membershipCard.getSellerId(),
				"[MembershipCard check] membershipCard.sellerId can't be null.");
//		AssertUtil.notNull(membershipCard.getUserId(),
//				"[MembershipCard check] membershipCard.userId can't be null.");
//		AssertUtil.notNull(membershipCard.getUserName(),
//				"[MembershipCard check] membershipCard.userName can't be null.");
//		AssertUtil.notNull(membershipCard.getCell(),
//				"[MembershipCard check] membershipCard.phone can't be null.");
		AssertUtil.notNull(membershipCard.getMemberCard(),
				"[MembershipCard check] membershipCard.memberCard can't be null.");
		AssertUtil.notNull(membershipCard.getMemberCardPrefix(),
				"[MembershipCard check] membershipCard.memberCardPrefix can't be null.");
		AssertUtil.notNull(membershipCard.getMemberCardNo(),
				"[MembershipCard check] membershipCard.memberCardNo can't be null.");
		AssertUtil.notNull(membershipCard.getImgPath(),
				"[MembershipCard check] membershipCard.imgPath can't be null.");
		AssertUtil.notNull(membershipCard.getRealConsumptionAccount(),
				"[MembershipCard check] membershipCard.realConsumptionAccount can't be null.");
		AssertUtil.notNull(membershipCard.getConsumptionAmout(),
				"[MembershipCard check] membershipCard.consumptionAmout can't be null.");
		AssertUtil.notNull(membershipCard.getAvailableIntegral(),
				"[MembershipCard check] membershipCard.integral can't be null.");
		AssertUtil.notNull(membershipCard.getStatus(),
				"[MembershipCard check] membershipCard.states can't be null.");
	}

	@Override
	public MemberCardDto getMemberCard(Long adminId, Long userId)
			throws MemberException {
		try {
			AssertUtil.notNull(adminId, "Param[adminId] can't be null.");
			AssertUtil.notNull(userId, "Param[userId] can't be null.");
			MemberCardDto dto = new MemberCardDto(adminId, userId);
			List<MemberCardDto> list = this.findMemberCardList(dto);
			return CollectionUtils.isEmpty(list) ? null : list.get(0);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public MemberCardDto getMemberCard(String shopNo, Long userId)
			throws MemberException {
		try {
			AssertUtil.notNull(shopNo, "Param[shopNo] can't be null.");
			AssertUtil.notNull(userId, "Param[userId] can't be null.");
			MemberCardDto dto = new MemberCardDto(shopNo, userId);
			return memberCardDao.getByShopNoAndUserId(dto);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public List<MemberCardDto> findMemberCardList(
			MemberCardDto memberCardDto) throws MemberException {
		try {
			//参数验证：卖家&买家不能同时为空
			boolean isTure = null != memberCardDto.getSellerId() 
					|| null != memberCardDto.getUserId();
			AssertUtil.isTrue(isTure, "Param[sellerId | userId] can't be null.");
			return memberCardDao.findMemberCardList(memberCardDto);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
	@Override
	public boolean editMemberCard(MemberCard memberCard,
			Long sellerId) throws MemberException {
		try {
			//参数
			AssertUtil.notNull(memberCard, "Param[memberCard] can't be null.");
			AssertUtil.notNull(memberCard.getId(), "Param[memberCard.id] can't be null.");
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			//数据权限
			MemberCard old = this.getMemberCardById(memberCard.getId());
			AssertUtil.isTrue(sellerId.equals(old.getSellerId()), 
					"Expect the value of Member[id={}]'s sellerId is {}, but is {}.",
					memberCard.getId(), sellerId, old.getSellerId());
			//更新
			memberCard.setUpdateTime(new Date());
			memberCardDao.edit(memberCard);
			return true;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public MemberCard getMemberCardById(Long id) throws MemberException {
		try {
			AssertUtil.notNull(id, "Param[id] can't be null.");
			MemberCard validData = memberCardDao.getValidById(id);
			AssertUtil.notNull(validData, "MembershipCard[id={}] is not exists, or be deleted.", id);
			return validData;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public Long addConsumptionAdjustmentRecord(
			ConsumptionAdjustmentRecord adjustmentRecord)
			throws MemberException {
		try {
			//参数验证
			AssertUtil.notNull(adjustmentRecord, "Param[adjustmentRecord] can't be null.");
			this.checkAdjustmentRecrod(adjustmentRecord);
			//添加
			adjustmentRecord.setCreateTime(new Date());
			Long id = consumptionAdjustmentRecordDao.add(adjustmentRecord);
			log.info(new StringBuilder("Add ").append(adjustmentRecord.toString()).append(" end."));
			return id;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	private void checkAdjustmentRecrod(ConsumptionAdjustmentRecord adjustmentRecord) {
		AssertUtil.notNull(adjustmentRecord.getMemberCardId(), 
				"[AdjustmentRecord Check] AdjustmentRecord.memberCardId can't be null.");
		AssertUtil.notNull(adjustmentRecord.getAdjustmentAmount(), 
				"[AdjustmentRecord Check] AdjustmentRecord.adjustmentAmount can't be null.");
		AssertUtil.notNull(adjustmentRecord.getType(), 
				"[AdjustmentRecord Check] AdjustmentRecord.type can't be null.");
		AssertUtil.notNull(adjustmentRecord.getIntegralAmout(), 
				"[AdjustmentRecord Check] AdjustmentRecord.integralAmout can't be null.");
	}

	@Override
	public Long addMemberAttendanceRecord(
			MemberAttendanceRecord attendanceRecord) throws MemberException {
		try {
			//验证
			AssertUtil.notNull(attendanceRecord, "Param[attendanceRecord] can't be null.");
			this.checkAttendanceRecord(attendanceRecord);
			//过滤重复
			MemberAttendanceRecord record = this.getLastRecordByMemberCardId(attendanceRecord.getMemberCardId());
			Date date = new Date();
			if (null != record) {
				AssertUtil.isTrue(!MemberAttendanceRecordDto.isDayRecordOf(record, date), 
						"The attendance record of today is already exists.", "今天已经签到过了哟！");
			}
			//添加
			attendanceRecord.setCreateTime(date);
			Long id = memberAttendanceRecordDao.add(attendanceRecord);
			log.info(new StringBuilder("Add ").append(attendanceRecord.toString()).append(" end."));
			return id;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	private void checkAttendanceRecord(MemberAttendanceRecord attendanceRecord) {
		AssertUtil.notNull(attendanceRecord.getMemberCardId(), 
				"[AttendanceRecord Check] AttendanceRecord.memberCardId can't be null.");
		AssertUtil.notNull(attendanceRecord.getAttendanceNumber(), 
				"[AttendanceRecord Check] AttendanceRecord.attendanceNumber can't be null.");
		AssertUtil.notNull(attendanceRecord.getAttendanceTime(), 
				"[AttendanceRecord Check] AttendanceRecord.attendanceTime can't be null.");
	}

	@Override
	public MemberAttendanceRecord getLastRecordByMemberCardId(Long memberCardId)
			throws MemberException {
		try {
			AssertUtil.notNull(memberCardId, "Param[memberCardId] can't be null.");
			return memberAttendanceRecordDao.getLastRecordByMemberCardId(memberCardId);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean sumMemberCardAmount(Long cardId, Long amount, Long giftAmount)
			throws MemberException {
		try {
			AssertUtil.notNull(cardId, "Param[cardId] can't be null.");
			AssertUtil.notNull(amount, "Param[amount] can't be null.");
			AssertUtil.notNull(amount > 0, "Param[amount] can't less than 0.");
			AssertUtil.notNull(giftAmount, "Param[giftAmount] can't be null.");
			AssertUtil.notNull(giftAmount > 0, "Param[giftAmount] can't less than 0.");
			Date date = new Date();
			MemberCard memberCard = memberCardDao.getMemberCardByIdLock(cardId);
			AssertUtil.notNull(memberCard, "Card[id={}] can't be null.", cardId);
			Long newAmount = 0L;
			Long newGiftAmount = 0L;
			Long newRealAmount = 0L;
			if ( 0L < giftAmount.longValue()) {
				newGiftAmount = CalculateUtils.getSum(memberCard.getGiftAmount(), giftAmount);
				newRealAmount =  CalculateUtils.getSum(memberCard.getRealAmount(), amount);
				newAmount = CalculateUtils.getSum(newRealAmount, newGiftAmount);
//				Long totalAmount = CalculateUtils.getSum(newRealAmount, newGiftAmount);
//				newAmount = CalculateUtils.getSum(memberCard.getGiftAmount(), totalAmount);
			}else {
				newRealAmount =  CalculateUtils.getSum(memberCard.getRealAmount(), amount);
				newAmount = CalculateUtils.getSum(memberCard.getAmount(), amount);
			}
			MemberCard editAmountCard = new MemberCard();
			editAmountCard.setId(memberCard.getId());
			editAmountCard.setAmount(newAmount);
			editAmountCard.setRealAmount(newRealAmount);
			editAmountCard.setGiftAmount(newGiftAmount);
			editAmountCard.setUpdateTime(date);
			memberCardDao.editAmount(editAmountCard);
			return true;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean miniMemberCardAmount(Long cardId, Long amount)
			throws MemberException {
		try {
			AssertUtil.notNull(cardId, "Param[cardId] can't be null.");
			AssertUtil.notNull(amount, "Param[amount] can't be null.");
			AssertUtil.notNull(amount > 0, "Param[amount] can't less than 0.");
			MemberCard memberCard = memberCardDao.getMemberCardByIdLock(cardId);
			AssertUtil.notNull(memberCard, "Card[id={}] can't be null.", cardId);
			AssertUtil.isTrue(memberCard.getAmount() >= amount , "Less card amount to pay!", "会员卡余额不足");
			Long newAmount = 0L;
			Long newGiftAmount = 0L;
			Long newRealAmount = 0L;
			Long gift =CalculateUtils.getSum(memberCard.getGiftAmount(),amount * -1);
			if ( 0 <= gift) {
				//正数
				newGiftAmount = gift;
				newRealAmount = memberCard.getRealAmount();
			}else {
				//负数(赠送金额小于消费额度)
				newGiftAmount = 0L;
				newRealAmount = CalculateUtils.getSum(memberCard.getRealAmount(),gift);
			}
			newAmount = CalculateUtils.getSum(newRealAmount,newGiftAmount);
			Date date = new Date();
			MemberCard editAmountCard = new MemberCard();
			editAmountCard.setId(memberCard.getId());
			editAmountCard.setAmount(newAmount);
			editAmountCard.setRealAmount(newRealAmount);
			editAmountCard.setGiftAmount(newGiftAmount);
			editAmountCard.setUpdateTime(date);
			memberCardDao.editAmount(editAmountCard);
			return true;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
//	private boolean editConsumptionAmout(MemberCard oldCard, Long amount,Date date) {
//		MemberCard memberCard = new MemberCard();
//		memberCard.setId(oldCard.getId());
//		memberCard.setConsumptionAmout(CalculateUtils.getSum(
//				ConstantsMethod.nvlLong(oldCard.getConsumptionAmout()), amount));
//		memberCard.setRealConsumptionAccount(CalculateUtils.getSum(
//				ConstantsMethod.nvlLong(oldCard.getRealConsumptionAccount()), amount));
//		memberCard.setUpdateTime(date);
//		memberCardDao.editConsumptionAccount(memberCard);
//		return true;
//	}


	@Deprecated
	@Override
	public Long findIntegralCountForWap(Long userId, Long sellerId) {
		return this.memberCardDao.findIntegralCountForWap(userId, sellerId);
	}

	@Deprecated
	@Override
	public Long findMemberCardIdByUserIdAndShopId(Long userId, Long sellerId) {
		return this.memberCardDao.findMemberCardIdByUserIdAndShopId(userId, sellerId);
	}
	

	@Override
	public List<IntegralRecode> findIntegralRecordListByMemberCardId(
			Long memberCardId, String beginTime, String endTime) {
		return integralRecodeDao.findIntegralRecordListByMemberCardId(memberCardId, beginTime, endTime);
	}

	@Override
	public List<MemberTransactionRecord> findMemberTransactionRecordListByTime(
			Long memberCardId, String beginTime, String endTime) {
		return memberTransactionRecordDao.findMemberTransactionRecordListByTime(memberCardId, beginTime, endTime);
	}

	@Override
	public MemberCard getMemberCard(MemberCard memberCard) {
		return memberCardDao.getMemberCard(memberCard);
	}

	@Override
	public Integer GetCardNo(Long userId) {
		//查询当前会员卡编号最大值
		Integer cardNo = memberCardDao.getLastMemberCardBySellerId(userId);
		//查询店铺会员卡规则
		MemberEnactment memberEnactment = memberEnactmentDao.getBySellerId(userId);
		if (null == cardNo ) {
			cardNo = memberEnactment.getStartNo();
		}else{
			cardNo++;
		}
		// 当前卡号如大于店铺设定的卡号规则中的最大值时，则不能添加会员
		if (cardNo.intValue() > memberEnactment.getEndNo().intValue()) {
			return null;
		} else {
			// 返回新卡号
			return cardNo;
		}
	}

	@Override
	public Pagination<MemberCardDto> findMemberCardPageList(
			MemberCardDto memberCardDto, Pagination<MemberCardDto> pagination) throws MemberException{
		try {
			AssertUtil.notNull(memberCardDto.getUserId(), "参数用户id为空");
			AssertUtil.notNull(pagination.getStart(), "参数分页起始数为空");
			AssertUtil.notNull(memberCardDto.getUserId(), "参数分页个数为空");
			// 查询会员卡分页列表
			List<MemberCardDto> memberCardDtos = memberCardDao.findMemberCardPageList(memberCardDto,
					pagination.getStart(), pagination.getPageSize());
			Integer totalRow = memberCardDao.getMemberCardCount(memberCardDto);
			pagination.setDatas(memberCardDtos);
			pagination.setTotalRow(totalRow);
			return pagination;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}





	@Override
	public MemberCardDto getMemberCardForWap(MemberCardDto memberCardDto) {
		// 查询会员卡（手机端用）
		return memberCardDao.getMemberCardForWap(memberCardDto);
	}

	@Override
	public void saveInfomation(MemberCardDto memberCardDto) {
		this.memberCardDao.saveInfomation(memberCardDto);
	}

	@Override
	public List<MemberCardDto> findCardPageForSeller(MemberCardDto cardDto) {
		return memberCardDao.findCardPageForSeller(cardDto);
	}

	@Override
	public Integer getCountForSeller(MemberCardDto cardDto) {
		return memberCardDao.getCountForSeller(cardDto);
	}

	@Override
	@Transactional
	public boolean sumMemberCardIntegral(Long cardId, Integer integral) {
		try {
			AssertUtil.notNull(cardId, "param[cardId] can't be null.");
			AssertUtil.notNull(integral, "param[integral] can't be null.");
			AssertUtil.isTrue(integral > 0, "param[integral < 0]");
			MemberCard memberCard = memberCardDao.getMemberCardByIdLock(cardId);
			this.editMemberCardIntegral(memberCard, integral);
			return true;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	@Transactional
	public boolean miniMemberCardIntegral(Long cardId, Integer integral) {
		try {
			AssertUtil.notNull(cardId, "param[cardId] can't be null.");
			AssertUtil.notNull(integral, "param[integral] can't be null.");
			AssertUtil.isTrue(integral > 0, "param[integral < 0]");
			MemberCard memberCard = memberCardDao.getMemberCardByIdLock(cardId);
			AssertUtil.isTrue(integral <= memberCard.getAvailableIntegral(), "Less integral to mini.", "积分不够了");
			this.editMemberCardIntegral(memberCard, integral * -1);
			return true;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
	private void editMemberCardIntegral(MemberCard oldCard, Integer integral) {
		MemberCard card = new MemberCard();
		card.setId(oldCard.getId());
		card.setAvailableIntegral(oldCard.getAvailableIntegral() + integral);
		card.setUpdateTime(new Date());
		memberCardDao.edit(card);
	}

	@Override
	public Long editConsumptionAmount(Long cardId, Long consumptionAmout) {
		try {
			AssertUtil.notNull(cardId, "param[cardId] can't be null.");
			AssertUtil.notNull(consumptionAmout, "param[consumptionAmout] can't be null.");
			AssertUtil.isTrue(consumptionAmout > 0, "param[consumptionAmout <= 0].");
			MemberCard old = memberCardDao.getMemberCardByIdLock(cardId);
			if (old.getConsumptionAmout().equals(consumptionAmout)) {
				log.info("消费额未修改");
				return 0L;
			}
			MemberCard card = new MemberCard();
			card.setUpdateTime(new Date());
			card.setConsumptionAmout(consumptionAmout);
			memberCardDao.edit(card);
			return CalculateUtils.getSum(consumptionAmout, -1 * old.getConsumptionAmout());
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Deprecated
	@Override
	public MemberWechatRelation getMemberWechatRelation(
			MemberWechatRelation memberWechatRelation) throws MemberException{
		try {
			AssertUtil.notNull(memberWechatRelation, "参数会员微信关注关系为空");
			// 查询会员微信关注关系
			return memberWechatRelationDao.getMemberWechatRelation(memberWechatRelation);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Deprecated
	@Override
	public void editMemberWechatRelation(
			MemberWechatRelation memberWechatRelation) {
		try {
			// 编辑会员微信关注关系
			memberWechatRelationDao.edit(memberWechatRelation);
		} catch (Exception e) {
			throw new MemberException(e);
		}
		
	}

	@Override
	@Transactional
	public boolean sumConsumptionAmount(Long cardId, Long amount) {
		try {
			AssertUtil.notNull(cardId, "param[cardId] can't be null.");
			AssertUtil.notNull(amount, "param[amount] can't be null.");
			AssertUtil.isTrue(amount >= 0, "param[amount <= 0]");
			MemberCard card = memberCardDao.getMemberCardByIdLock(cardId);
			Long consumptionAmout = CalculateUtils.getSum(card.getConsumptionAmout(), amount);
			Long realConsumptionAmout = CalculateUtils.getSum(card.getRealConsumptionAccount(), amount);
			card = new MemberCard();
			card.setId(cardId);
			card.setConsumptionAmout(consumptionAmout);
			card.setRealConsumptionAccount(realConsumptionAmout);
			card.setUpdateTime(new Date());
			memberCardDao.edit(card);
			return true;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public MemberCardDto getMemberCardForShow(MemberCardDto memberCardDto) {
		return memberCardDao.getMemberCardForShow(memberCardDto);
	}

	@Override
	public Integer getSendIntegralRecode(SendIntegralRecodeDto sendIntegralRecodeDto)
			throws MemberException {
		try {
			AssertUtil.notNull(sendIntegralRecodeDto, "param[dto] can't be null.");
			AssertUtil.notNull(sendIntegralRecodeDto.getSellerId(), "param[dto.sellerId] can't be null.");
			return sendIntegralRecodeDao.getSendIntegralRecode(sendIntegralRecodeDto);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
	/**
	 * 赠送积分记录列表
	 * @param sendIntegralRecodeDto
	 * @return list
	 */
	@Override
	public List<SendIntegralRecodeDto> querySendIntegralRecode(
			SendIntegralRecodeDto sendIntegralRecodeDto) throws MemberException {
		try {
			AssertUtil.notNull(sendIntegralRecodeDto, "param[dto] can't be null.");
			AssertUtil.notNull(sendIntegralRecodeDto.getSellerId(), "param[dto.sellerId] can't be null.");
			return sendIntegralRecodeDao.querySendIntegralRecode(sendIntegralRecodeDto);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	
}
