package com.zjlp.face.web.server.operation.marketing.producer.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.MarketingException;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivity;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingTool;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketOrderDetailDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDetailDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingToolDto;
import com.zjlp.face.web.server.operation.marketing.domain.vo.MarketingActivityDetailVo;
import com.zjlp.face.web.server.operation.marketing.producer.MarketingProducer;
import com.zjlp.face.web.server.operation.marketing.service.MarketingService;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberRule;
import com.zjlp.face.web.server.operation.member.producer.IntegralProducer;
import com.zjlp.face.web.server.operation.member.producer.MemberProducer;
import com.zjlp.face.web.server.trade.cart.domain.vo.SellerVo;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;

@Repository("marketingProducer")
public class MarketingProducerImpl implements MarketingProducer {

	private Logger _logger = Logger.getLogger(this.getClass());
	@Autowired
	private MarketingService marketingService;
	@Autowired
	private ShopProducer shopProducer;
	@Autowired
	private MemberProducer memberProducer;
	@Autowired
	private IntegralProducer integralProducer;
	
	

	
	@Override
	@Transactional
	public boolean initMarketingTool(Long sellerId) throws MarketingException {
		try {
			AssertUtil.notNull(sellerId, "param[sellerId] can't be null");
			//会员初始化
			MarketingTool memberTool = MarketingToolDto.initMemberRuleTool(sellerId);
			Long id = marketingService.addTool(memberTool);
			List<MarketingActivityDetail> memberRules = MarketingActivityDetailDto.initMemberRule(id);
			marketingService.addActivityDetail(memberRules);
			//会员充值营销工具  默认未开启
			MarketingTool czTool = MarketingToolDto.initTool(sellerId, 
					MarketingToolDto.CJ_TYPE_CZ, MarketingToolDto.PD_TYPE_HY, MarketingToolDto.STANDARD_TYPE_NO);
			marketingService.addTool(czTool);
			//积分低价营销工具  默认未开启
			MarketingTool jfdjTool = MarketingToolDto.initTool(sellerId, 
					MarketingToolDto.CJ_TYPE_DG, MarketingToolDto.PD_TYPE_JF, MarketingToolDto.STANDARD_TYPE_YES);
			marketingService.addTool(jfdjTool);
			//签到送积分  默认未开启
			MarketingTool jfqdTool = MarketingToolDto.initTool(sellerId, 
					MarketingToolDto.CJ_TYPE_QD, MarketingToolDto.PD_TYPE_JF, MarketingToolDto.STANDARD_TYPE_YES);
			marketingService.addTool(jfqdTool);
			//消费送积分营销工具   默认未开启
			MarketingTool jfxfTool = MarketingToolDto.initTool(sellerId, 
					MarketingToolDto.CJ_TYPE_ZF, MarketingToolDto.PD_TYPE_JF, MarketingToolDto.STANDARD_TYPE_YES);
			marketingService.addTool(jfxfTool);
			return true;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}
	
	@Override
	public MarketingActivityDetail getMarketingActivityDetail(Long sellerId,
			Integer marketingType, Integer productType, Integer condition) {
		try {
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			AssertUtil.notNull(marketingType, "Param[marketingType] can't be null.");
			AssertUtil.notNull(productType, "Param[productType] can't be null.");
			AssertUtil.notNull(condition, "Param[condition] can't be null.");
			AssertUtil.notTrue(0L > condition , "参数[condition] 超出范围");
				AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
				AssertUtil.notNull(marketingType, "Param[marketingType] can't be null.");
				AssertUtil.notNull(productType, "Param[productType] can't be null.");
				MarketingTool tool = marketingService.getToolByType(String.valueOf(sellerId),
						MarketingToolDto.REMOTE_TYPE_SELLER, marketingType, productType);
				if (null == tool || !Constants.VALID.equals(tool.getStatus())) {
					_logger.info("营销工具出于关闭状态，不能使用");
					return null;
				}
			List<MarketingActivityDetail> details =  marketingService.findListByToolId(tool.getId());
			if (null == details || details.isEmpty()) {
				//没有活动明细
				return null;
			}
			MarketingActivityDetail selectedDetail = null;
			boolean isActity =1==tool.getStandardType().intValue()?true:false;
			if (isActity&& 1 == details.size()) {
				//是标准活动
				selectedDetail = details.get(0);
			}else{
				//有活动
				for (MarketingActivityDetail detail : details) {
					if(null == detail.getPremiseVal() || 0 > detail.getPremiseVal().intValue()) continue;
					if (condition >= detail.getPremiseVal().intValue()) {
						if (null == selectedDetail) {
							selectedDetail = detail;
						}else{
							Integer selectedPremiseVal = selectedDetail.getPremiseVal();
							if (selectedPremiseVal.intValue() < detail.getPremiseVal().intValue()) {
								selectedDetail = detail;
							}
						}
					}
				}
			}
			return selectedDetail;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public MarketingActivityDetail getMarketingActivityDetail(String shopNo,
			Integer marketingType, Integer productType, Integer condition) {
		try {
			AssertUtil.hasLength(shopNo, "参数[shopNo] 不能为空.");
			AssertUtil.notNull(marketingType, "参数[marketingType] 不能为空.");
			AssertUtil.notNull(productType, "参数[productType] 不能为空.");
			AssertUtil.notNull(condition, "参数[condition] 不能为空.");
			AssertUtil.notTrue(0L > condition , "参数[condition] 超出范围");
			//查询店铺所属z	
			Long sellerId  = shopProducer.getShopUserIdByNo(shopNo);
			return this.getMarketingActivityDetail(sellerId, marketingType, productType, condition);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}
	
	@Override
	public List<MarketingActivityDetail> findDetail(Long sellerId,
			Integer marketingType, Integer productType)
			throws MarketingException {
		try {
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			AssertUtil.notNull(marketingType, "Param[marketingType] can't be null.");
			AssertUtil.notNull(productType, "Param[productType] can't be null.");
			MarketingTool tool = marketingService.getToolByType(String.valueOf(sellerId),
					MarketingToolDto.REMOTE_TYPE_SELLER, marketingType, productType);
			if (null == tool || !Constants.VALID.equals(tool.getStatus())) {
				_logger.info("营销工具出于关闭状态，不能使用");
				return null;
			}
			return marketingService.findListByToolId(tool.getId());
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}
	
	@Override
	public List<MarketingActivityDetail> findDetail(String shopNo,
			Integer marketingType, Integer productType)
			throws MarketingException {
		try {
			AssertUtil.notNull(shopNo, "Param[shopNo] can't be null.");
			AssertUtil.notNull(marketingType, "Param[marketingType] can't be null.");
			AssertUtil.notNull(productType, "Param[productType] can't be null.");
			Shop shop = shopProducer.getShopByNo(shopNo);
			return findDetail(shop.getUserId(), marketingType, productType);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public MarketingTool getMarketingTool(Long sellerId, Integer mkType,
			Integer pdType) throws MarketingException {
		try {
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			AssertUtil.notNull(mkType, "Param[mkType] can't be null.");
			AssertUtil.notNull(pdType, "Param[pdType] can't be null.");
			return marketingService.getToolByType(String.valueOf(sellerId),
					MarketingToolDto.REMOTE_TYPE_SELLER, mkType, pdType);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public MarketingTool getMarketingTool(String shopNo, Integer mkType,
			Integer pdType) throws MarketingException {
		try {
			AssertUtil.notNull(shopNo, "Param[shopNo] can't be null.");
			AssertUtil.notNull(mkType, "Param[mkType] can't be null.");
			AssertUtil.notNull(pdType, "Param[pdType] can't be null.");
			Long sellerId  = shopProducer.getShopUserIdByNo(shopNo);
			return getMarketingTool(sellerId, mkType, pdType);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public List<MarketingActivity> findMarketingActivityByToolId(Long toolId)
			throws MarketingException {
		try {
			AssertUtil.notNull(toolId, "Param[toolId] can't be null.");
			return marketingService.findActivityListByToolId(toolId);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public MarketingActivity getValidMarketingActivityByToolId(Long toolId)
			throws MarketingException {
		try {
			AssertUtil.notNull(toolId, "Param[toolId] can't be null.");
			MarketingTool tool = marketingService.getToolById(toolId);
			AssertUtil.notNull(tool, "");
			MarketingActivity activity = marketingService.getValidActivityByToolId(toolId);
			return activity;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public Long addDetail(MarketingActivityDetail detail)
			throws MarketingException {
		try {
			AssertUtil.notNull(detail, "Param[detail] can't be null.");
			return marketingService.addActivityDetail(detail);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public boolean editDetail(MarketingActivityDetail detail)
			throws MarketingException {
		try {
			AssertUtil.notNull(detail, "Param[detail] can't be null.");
			AssertUtil.notNull(detail.getId(), "Param[detail.id] can't be null.");
			return marketingService.editDetail(detail);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public MemberRule getMemberRule(String shopNo, Long amount)
			throws MarketingException {
		try {
			AssertUtil.notNull(shopNo, "Param[shopNo] can't be null.");
			AssertUtil.notNull(amount, "Param[amount] can't be null.");
			List<MemberRule> rules = this.findMemberRuleList(shopNo);
			return MemberRule.getMemberRuleByAmount(rules, amount);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public MemberRule getMemberRule(long sellerId, Long amount)
			throws MarketingException {
		try {
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			AssertUtil.notNull(amount, "Param[amount] can't be null.");
			List<MemberRule> rules = this.findMemberRuleList(sellerId);
			return MemberRule.getMemberRuleByAmount(rules, amount);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}
	
	@Override
	public List<MemberRule> findMemberRuleList(String shopNo)
			throws MarketingException {
		try {
			Shop shop = shopProducer.getShopByNo(shopNo);
			return this.findMemberRuleList(shop.getUserId());
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public List<MemberRule> findMemberRuleList(Long userId)
			throws MarketingException {
		try {
			AssertUtil.notNull(userId, "Param[userId] can't be null.");
			List<MarketingActivityDetail> details = this.findDetail(userId, 
					MarketingToolDto.CJ_TYPE_DG, MarketingToolDto.PD_TYPE_HY);
			AssertUtil.notEmpty(details, "MemberRules[userId={}] is not exists.", userId);
			List<MemberRule> rules = MemberRule.converToRule(details);
			Collections.sort(rules, MemberRule.memberComparator);
			return rules;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public Long getRechargeGiftAmount(Long sellerId, Long chargePrice) {
		try {
			AssertUtil.notNull(sellerId, "参数[sellerId] 不能为空");
			AssertUtil.notNull(chargePrice, "参数[chargePrice] 不能为空");
			AssertUtil.notTrue(0L > chargePrice , "参数[chargePrice] 超出范围");
			Integer giftAmount = this._getMarketingActivityDetailResultVal(sellerId, 
					MarketingToolDto.CJ_TYPE_CZ, MarketingToolDto.PD_TYPE_HY, chargePrice.intValue());
			_logger.info("本次消费可赠送会员卡金额"+giftAmount+"分");
			return Long.valueOf(giftAmount);
		} catch (Exception e) {
			throw new MarketingException(e.getMessage(),e);
		}
	}

	@Override
	public void consumptionOfBonusPoints(String shopNo, Long userId,Long bonusPoint) {
		try {
			AssertUtil.hasLength(shopNo,"参数[shoNo] 不能为空");
			AssertUtil.notNull(bonusPoint,"参数[consumPrice] 不能为空");
			AssertUtil.notNull(userId,"参数[userId] 不能为空");
			AssertUtil.notTrue(0L > bonusPoint , "参数[bonusPoint] 超出范围");
			Long sellerId = shopProducer.getShopUserIdByNo(shopNo);
			//累计积分，并赠送积分
			integralProducer.consumerGiftIntegral(sellerId,userId, bonusPoint);
		} catch (Exception e) {
			throw new MarketingException(e.getMessage(),e);
		}
	}
	
	@Override
	public void deductionPoints(String shopNo, Long userId, Long integral) {
		try {
			AssertUtil.hasLength(shopNo,"参数[shoNo] 不能为空");
			AssertUtil.notNull(userId,"参数[userId] 不能为空");
			AssertUtil.notTrue(0L > integral , "参数[integral] 超出范围");
			Long sellerId = shopProducer.getShopUserIdByNo(shopNo);
			MemberCard memberCard = memberProducer.getMemberCardByUserIdAndShopNo(userId, shopNo);
			if(null == memberCard){
				return;
			}
			//积分抵扣在交易成功后
			integralProducer.deductIntegral(sellerId, userId, integral);
		} catch (Exception e) {
			throw new MarketingException(e.getMessage(),e);
		}
	}
	
	@Deprecated
	@Override
	public MarketOrderDetailDto getDeductionPoints(String shopNo, Long userId,Long consumPrice) {
		try {
			AssertUtil.hasLength(shopNo,"参数[shoNo] 不能为空");
			AssertUtil.notNull(consumPrice,"参数[consumPrice] 不能为空");
			AssertUtil.notTrue(0L > consumPrice , "参数[consumPrice] 超出范围");
			MarketOrderDetailDto marketOrderDetailDto  = new MarketOrderDetailDto();
			Long deductionPrice = 0L;//优惠金额
			MemberCard memberCard = memberProducer.getMemberCardByUserIdAndShopNo(userId, shopNo);
			if (null == memberCard ) {
				_logger.info("该用户不是会员！无法享受积分抵扣优惠");
				return  null;
			}
			Long availableIntegral = memberCard.getAvailableIntegral();
			if ( 0 > availableIntegral.longValue()) {
				_logger.info("用户积分可用积分不足，无法抵扣！");
				return  null;
				
			}
			_logger.info("用户在该店铺可用积分: "+availableIntegral);
			MarketingActivityDetail deatil = this.getMarketingActivityDetail(shopNo,MarketingToolDto.CJ_TYPE_DG,
					MarketingToolDto.PD_TYPE_JF,availableIntegral.intValue());
			if (null == deatil) {
				_logger.info("该店铺未设置积分抵扣规则");
				return  null;
			}
	
			if (null == deatil.getPremiseVal() || 0 >  deatil.getPremiseVal().intValue()
				|| null == deatil.getResultVal()  || 0 >  deatil.getResultVal().intValue()
				|| null == deatil.getMaxVal()  || 1 >  deatil.getResultVal().intValue()) {
				_logger.warn("该店铺积分抵扣规则不完整");
				return  null;
			}
			BigDecimal consumPriceDecimal = new BigDecimal(consumPrice);
			BigDecimal conditionDecimal = new BigDecimal(deatil.getPremiseVal());//条件
			BigDecimal resultDecimal = new BigDecimal(deatil.getResultVal());//结果
			BigDecimal maxRateDecimal = new BigDecimal(deatil.getMaxVal());//最大抵扣比例
			_logger.info("当前匹配的积分送规则为："+conditionDecimal.longValue()+"积分抵扣"+resultDecimal.longValue()+"分，最大可抵扣比例"+maxRateDecimal.longValue()+"%");
			/** 计算优惠最大价格     公式： （ 消费总额/最大抵扣比例） */
			Long maxPrice = consumPriceDecimal.multiply(maxRateDecimal.divide(new BigDecimal(100))).longValue();
			_logger.info("本次消费最大可抵扣金额为"+maxPrice+"分");
	
			/** 计算积分抵扣的优惠价格     公式： （ 用户积分数/积分条件）*积分结果 */
			BigDecimal availableIntegralDecimal = new BigDecimal(availableIntegral);
			deductionPrice = availableIntegralDecimal.divide(conditionDecimal).multiply(resultDecimal).longValue();
			if (deductionPrice.longValue() > maxPrice.longValue()) {
				deductionPrice = maxPrice;
			}
			/** 计算已抵扣的积分数     公式： （积分抵扣的优惠价格/积分结果）*积分条件) */
			Long integral = new BigDecimal(deductionPrice).divide(resultDecimal).multiply(conditionDecimal).longValue();
			_logger.info("当前使用"+integral+"积分抵扣的优惠价格为"+deductionPrice+"分");
			/** 积分冻结 */
			integralProducer.frozenIntegral(memberCard.getId(),Long.valueOf(integral));
			marketOrderDetailDto.setDiscountFee(deductionPrice);
			marketOrderDetailDto.setIntegral(integral);
			marketOrderDetailDto.setToolId(deatil.getToolId());
			marketOrderDetailDto.setActivityId(deatil.getActivityId());
			marketOrderDetailDto.setDetailId(deatil.getId());
			marketOrderDetailDto.setMaxVal(deatil.getMaxVal()); //保存最大抵扣率
			return marketOrderDetailDto;
		} catch (RuntimeException e) {
			throw new MarketingException(e.getMessage(),e);
		}
	}
	



	@Override
	public MarketingActivityDetailVo getMarketingActivityDetailVo(
			String shopNo, Long userId) {
		AssertUtil.hasLength(shopNo,"参数[shoNo] 不能为空");
		AssertUtil.notNull(userId,"参数[userId] 不能为空");
		
		MarketingActivityDetailVo detailVo = new MarketingActivityDetailVo();
		//查询管理员信息
		Long sellerId = shopProducer.getShopUserIdByNo(shopNo);
		detailVo.setSellerId(sellerId);
		
		//查询会员信息
		MemberCard memberCard = memberProducer.getMemberCardByUserIdAndShopNo(userId, shopNo);
		if (null == memberCard) {
			return detailVo;
		}
		detailVo.setUserIntegral(memberCard.getAvailableIntegral());
		//查询消费送积分信息
		List<MarketingActivityDetail> bonusPointList = this.findDetail(sellerId, MarketingToolDto.CJ_TYPE_ZF, MarketingToolDto.PD_TYPE_JF);
		if (null != bonusPointList && !bonusPointList.isEmpty()) {
			MarketingActivityDetail mad = bonusPointList.get(0);
			detailVo.setGiftCondition(Long.valueOf(mad.getPremiseVal()));
			detailVo.setGiftResult(Long.valueOf(mad.getResultVal()));
		}

		//查询积分抵价信息
		List<MarketingActivityDetail> deductionPointList = this.findDetail(sellerId, MarketingToolDto.CJ_TYPE_DG, MarketingToolDto.PD_TYPE_JF);
		if (null != deductionPointList && !deductionPointList.isEmpty()) {
			MarketingActivityDetail mad = deductionPointList.get(0);
			detailVo.setDeductionCondition(Long.valueOf(mad.getPremiseVal()));
			detailVo.setDeductionResult(Long.valueOf(mad.getResultVal()));
			detailVo.setDeductionMaxrete(mad.getMaxVal());
		}
		return detailVo;
	}
	
	private Integer _getMarketingActivityDetailResultVal(Long sellerId,
			Integer marketingType, Integer productType, Integer condition){
		MarketingActivityDetail deatil = this.getMarketingActivityDetail(sellerId,marketingType,
				productType,condition);
		if (null == deatil) {
			_logger.info("该店铺未设置营销规则");
			return  0;
		}
		return deatil.getResultVal();
	}

	@Override
	public Integer getActivityCount(MarketingActivityDto dto) {
		try {
			AssertUtil.notNull(dto, "参数【查询信息】为空");
			return marketingService.getActivityCount(dto);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public List<MarketingActivityDto> findActivityList(MarketingActivityDto dto) {
		try {
			AssertUtil.notNull(dto, "参数【查询信息】为空");
			return marketingService.findActivityPage(dto);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public List<MarketingActivityDetailDto> findDetailListByActivityId(Long activityId) {
		try {
			AssertUtil.notNull(activityId, "参数【查询信息】为空");
			return marketingService.findListByActivityId(activityId);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public Long addActivity(MarketingActivity activity) {
		try {
			AssertUtil.notNull(activity, "参数为空");
			return marketingService.addActivity(activity);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public boolean editActivity(MarketingActivity newActivity) {
		try {
			AssertUtil.notNull(newActivity, "参数为空");
			return marketingService.editActivity(newActivity);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public MarketingActivity getValidActivityById(Long activityId) {
		try {
			AssertUtil.notNull(activityId, "参数为空");
			return marketingService.getActivityById(activityId);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public boolean removeActivity(Long activityId) throws MarketingException {
		try {
			AssertUtil.notNull(activityId, "参数为空");
			marketingService.delActivity(activityId);
			marketingService.delActivityDetailByActivityId(activityId);
			return true;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public List<MarketingActivityDetailDto> findNotStandardDetail(
			Long sellerId, Integer cj, Integer pd) throws MarketingException {
		try {
			AssertUtil.notNull(sellerId, "参数[sellerId]为空");
			AssertUtil.notNull(cj, "参数[cj]为空");
			AssertUtil.notNull(pd, "参数[pd]为空");
			MarketingTool tool = this.getMarketingTool(sellerId, cj, pd);
			AssertUtil.notNull(tool, "Tool[sellerId={}, cj={}, pd={}] is not exists.", sellerId, cj, pd);
			MarketingActivity activity = this.getValidMarketingActivityByToolId(tool.getId());
			if (null == activity) {
				_logger.info("can't find a running activity for tool. id="+tool.getId());
				return null;
			}
			return this.findDetailListByActivityId(activity.getId());
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public MarketingActivityDetail getAttendanceRule(String shopNo)
			throws MarketingException {
		try {
			AssertUtil.notNull(shopNo, "参数[shopNo]为空");
			MarketingTool tool = this.getMarketingTool(shopNo, 
					MarketingToolDto.CJ_TYPE_QD, MarketingToolDto.PD_TYPE_JF);
			if (null == tool || !Constants.VALID.equals(tool.getStatus())) {
				_logger.info("签到送积分出于关闭状态，不能使用");
				return null;
			}
			List<MarketingActivityDetail>  list = this.findDetail(shopNo, 
					MarketingToolDto.CJ_TYPE_QD, MarketingToolDto.PD_TYPE_JF);
			if (null == list || list.isEmpty()) {
				_logger.info("没有找到店铺"+shopNo+"的签到规则！");
				return null;
			}
			return list.get(0);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public MarketingTool getMarketingToolById(Long toolId)
			throws MarketingException {
		try {
			AssertUtil.notNull(toolId, "参数[toolId]为空");
			return marketingService.getToolById(toolId);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	@Transactional
	public boolean editMarketingRuleStatus(Long toolId, Integer status)
			throws MarketingException {
		try {
			AssertUtil.notNull(toolId, "参数[toolId]为空");
			AssertUtil.notNull(status, "参数[status]为空");
			AssertUtil.isTrue(Constants.VALID.equals(status) 
					|| Constants.FREEZE.equals(status), "参数【修改的状态】不是有效或是冻结");
			MarketingTool tool = this.getMarketingToolById(toolId);
			if (status.equals(tool.getStatus())) {
				_logger.info("状态已为"+status+", 修改失败。");
			}
			marketingService.editToolStatus(toolId, status);
//			if (MarketingToolDto.STANDARD_TYPE_NO.equals(tool.getStandardType())) {
//				marketingService.editActivityStatusByToolId(toolId, status);
//			}
//			marketingService.editDetailStatusByToolId(toolId, status);
			return true;
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public MarketingActivityDetail getMarketingDetailById(Long id)
			throws MarketingException {
		try {
			AssertUtil.notNull(id, "参数[id]为空");
			return marketingService.getDetailById(id);
		} catch (Exception e) {
			throw new MarketingException(e);
		}
	}

	@Override
	public boolean removeDetailById(Long id, Long sellerId)
			throws MarketingException {
		try {
			MarketingActivityDetail detail = this.getMarketingDetailById(id);
			MarketingTool tool = this.getMarketingToolById(detail.getToolId());
			AssertUtil.isTrue(String.valueOf(sellerId).equals(tool.getRemoteId()), "data unvalid.");
			marketingService.delActivityDetail(id);
			return false;
		} catch (RuntimeException e) {
			throw new MarketingException(e);
		}
	}
	
	@Override
	public Long getMemberActivityDiscount(Long sellerId,Long consumptionAmout) {
		Long rate = 100L;//会员折扣默认百分百
		MarketingActivityDetail deatil = this.getMarketingActivityDetail(sellerId,MarketingToolDto.CJ_TYPE_DG,
				MarketingToolDto.PD_TYPE_HY,consumptionAmout.intValue());
		if (null == deatil) {
			_logger.info("该店铺没有开启会员折扣");
			return rate;
		}
		if (null == deatil.getResultVal() || 0 > deatil.getResultVal().intValue()
			|| 100 < deatil.getResultVal().intValue()) {
			_logger.info("店铺会员折扣率不合法 rate ="+deatil.getResultVal());
			return rate;
		}
		rate = Long.valueOf(deatil.getResultVal());
		_logger.info("会员累计消费："+consumptionAmout+"分,当前会员折扣为"+rate+"%");
		return rate;
	}

	@Override
	public SellerVo buyActivityInfo(String shopNo,Long sellerId, Long userId)
			throws MarketingException {
		AssertUtil.notNull(sellerId, "参数[sellerId]为空");
		AssertUtil.notNull(userId, "参数[userId]为空");
		SellerVo sellerVo= new SellerVo();
		sellerVo.setSellerId(sellerId);
		MemberCard memberCard = memberProducer.getMemberCardByUserIdAndShopNo(userId,shopNo);
		if (null == memberCard) {
			//用户不是会员，不享受任何活动
			_logger.info("用户："+userId+"不是卖家："+sellerId+"会员，不享受任何活动");
			return sellerVo;
		}
		//会员当前可用积分
		Long userIntegral = memberCard.getAvailableIntegral();
		if ( 0L > userIntegral ) {
			userIntegral = 0L;
		}
		sellerVo.setUserIntegral(userIntegral);
		_logger.info("用户当前可用积分 ： "+userIntegral+"个");
		//消费送积分规则
		List<MarketingActivityDetail> details = this.findDetail(sellerId, MarketingToolDto.CJ_TYPE_ZF, MarketingToolDto.PD_TYPE_JF);
		if (null != details && !details.isEmpty()) {
			MarketingActivityDetail consumerGiftDetail = details.get(0);
			sellerVo.setGiftCondition(Long.valueOf(consumerGiftDetail.getPremiseVal()));
			sellerVo.setGiftResult(Long.valueOf(consumerGiftDetail.getResultVal()));
			_logger.info("用户在卖家"+sellerId+"，消费送积分规则：消费"+consumerGiftDetail.getPremiseVal()+"分送"+consumerGiftDetail.getResultVal()+"个积分");
		}
		//积分抵扣
		List<MarketingActivityDetail> details1 = this.findDetail(sellerId, MarketingToolDto.CJ_TYPE_DG, MarketingToolDto.PD_TYPE_JF);
		if (null != details1 && !details1.isEmpty()) {
			MarketingActivityDetail in = details1.get(0);
			sellerVo.setDeductionCondition(Long.valueOf(in.getPremiseVal()));
			sellerVo.setDeductionMaxrete(Long.valueOf(in.getMaxVal()));
			sellerVo.setDeductionResult(Long.valueOf(in.getResultVal()));
			_logger.info("用户在卖家"+sellerId+"，积分抵扣规则："+in.getPremiseVal()+"积分抵扣"+in.getResultVal()+"分"
					+ "最大比例："+in.getMaxVal());
		}
		return sellerVo;
	}

	@Override
	public MarketOrderDetailDto getConsumptionOfBonusPoints(String shopNo,
			Long userId, Long consumPrice) {
		try {
			AssertUtil.hasLength(shopNo,"参数[shoNo] 不能为空");
			AssertUtil.notNull(consumPrice,"参数[consumPrice] 不能为空");
			AssertUtil.notTrue(0L > consumPrice , "参数[consumPrice] 超出范围");
			MarketOrderDetailDto marketOrderDetailDto  = new MarketOrderDetailDto();
			MemberCard memberCard = memberProducer.getMemberCardByUserIdAndShopNo(userId, shopNo);
			if (null == memberCard ) {
				_logger.info("该用户不是会员！无法享受消费送积分优惠！");
				return  null;
			}
			MarketingActivityDetail deatil = this.getMarketingActivityDetail(shopNo,MarketingToolDto.CJ_TYPE_ZF,
					MarketingToolDto.PD_TYPE_JF,consumPrice.intValue());
			if (null == deatil) {
				_logger.info("该店铺未设置消费送积分优惠");
				return  null;
			}
	
			if (null == deatil.getPremiseVal() || 0 >  deatil.getPremiseVal().intValue()
				|| null == deatil.getResultVal()  || 0 >  deatil.getResultVal().intValue()) {
				_logger.warn("该店铺消费送积分规则不完整");
				return  null;
			}
			BigDecimal conditionDecimal = new BigDecimal(deatil.getPremiseVal());//条件
			BigDecimal resultDecimal = new BigDecimal(deatil.getResultVal());//结果
			_logger.info("当前匹配的消费送积分规则为消费 ："+conditionDecimal.longValue()+"金额（分）赠送"+resultDecimal.longValue()+"个积分");
			/** 计算 */
			BigDecimal consumPricedeDecimal = new BigDecimal(consumPrice);
			Long giftIntegral = consumPricedeDecimal.divide(conditionDecimal).multiply(resultDecimal).longValue();
			_logger.info("当前消费金额为："+consumPrice+"分，赠送积分"+giftIntegral+"个");
			marketOrderDetailDto.setGiftIntegral(giftIntegral);
			marketOrderDetailDto.setToolId(deatil.getToolId());
			marketOrderDetailDto.setActivityId(deatil.getActivityId());
			marketOrderDetailDto.setDetailId(deatil.getId());
			return marketOrderDetailDto;
		} catch (RuntimeException e) {
			throw new MarketingException(e.getMessage(),e);
		}
	}
	



	public static void main(String[] args) {
		
		BigDecimal availableIntegralDecimal = new BigDecimal(500);//用户500分
		BigDecimal consumPriceDecimal = new BigDecimal(700);//消费金额
		BigDecimal conditionDecimal = new BigDecimal(100);//条件 100积分
		BigDecimal resultDecimal = new BigDecimal(100);//结果 100分=1元
		BigDecimal maxRateDecimal = new BigDecimal(50);//百分之50
		
		Long maxPrice = consumPriceDecimal.multiply(maxRateDecimal.divide(new BigDecimal(100))).longValue();
		System.out.println("本次消费最大可抵扣金额为"+maxPrice+"分");
		
		/** 计算 （ 用户积分数/积分条件）*积分结果  */
		Long deductionPrice = availableIntegralDecimal.divide(conditionDecimal).multiply(resultDecimal).longValue();
		System.out.println("1= " +availableIntegralDecimal.divide(conditionDecimal));
		System.out.println("deductionPrice= " +deductionPrice);
		if (deductionPrice.longValue() > maxPrice.longValue()) {
			deductionPrice = maxPrice;
		}
		Long integral = new BigDecimal(deductionPrice).divide(resultDecimal).multiply(conditionDecimal).longValue();
		System.out.println("当前使用"+integral+"个积分抵扣的优惠价格为"+deductionPrice+"分");
		
		
		BigDecimal conditionDecimal1 = new BigDecimal(10000);//条件100元
		BigDecimal resultDecimal1 = new BigDecimal(10);//结果
		/** 计算 */
		BigDecimal consumPricedeDecimal = new BigDecimal(10500);//条件150元
		Long giftIntegral = consumPricedeDecimal.divide(conditionDecimal1).multiply(resultDecimal1).longValue();
		System.out.println(consumPricedeDecimal.divide(conditionDecimal1).toString()+" "+giftIntegral);
	}
	
}
