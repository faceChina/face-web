package com.zjlp.face.web.server.operation.member.business.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.jredis.client.AbstractRedisDaoSupport;
import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.constants.ConstantsFiled;
import com.zjlp.face.util.data.TranscodeUtil;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.component.sms.Sms;
import com.zjlp.face.web.component.sms.SmsContent;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.errorcode.CErrMsg;
import com.zjlp.face.web.exception.ext.MemberException;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivity;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingTool;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDetailDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingToolDto;
import com.zjlp.face.web.server.operation.marketing.producer.MarketingProducer;
import com.zjlp.face.web.server.operation.member.business.MemberBusiness;
import com.zjlp.face.web.server.operation.member.domain.DateComparator;
import com.zjlp.face.web.server.operation.member.domain.IntegralRecode;
import com.zjlp.face.web.server.operation.member.domain.MemberAttendanceRecord;
import com.zjlp.face.web.server.operation.member.domain.MemberCard;
import com.zjlp.face.web.server.operation.member.domain.MemberEnactment;
import com.zjlp.face.web.server.operation.member.domain.MemberWechatRelation;
import com.zjlp.face.web.server.operation.member.domain.SendIntegralRecode;
import com.zjlp.face.web.server.operation.member.domain.dto.IntegralCalu;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberAttendanceRecordDto;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberCardDto;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberRule;
import com.zjlp.face.web.server.operation.member.domain.dto.SendIntegralRecodeDto;
import com.zjlp.face.web.server.operation.member.domain.vo.BalanceVo;
import com.zjlp.face.web.server.operation.member.domain.vo.ConsumeVo;
import com.zjlp.face.web.server.operation.member.domain.vo.IntegralVo;
import com.zjlp.face.web.server.operation.member.domain.vo.MemberAttendanceRecordVo;
import com.zjlp.face.web.server.operation.member.producer.IntegralProducer;
import com.zjlp.face.web.server.operation.member.service.IntegralService;
import com.zjlp.face.web.server.operation.member.service.MemberCardService;
import com.zjlp.face.web.server.operation.member.service.MemberService;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.service.SalesOrderService;
import com.zjlp.face.web.server.trade.payment.domain.MemberTransactionRecord;
import com.zjlp.face.web.server.trade.payment.domain.dto.MemberTransactionRecordDto;
import com.zjlp.face.web.server.trade.payment.service.MemberTransactionRecordService;
import com.zjlp.face.web.server.trade.recharge.domain.Recharge;
import com.zjlp.face.web.server.trade.recharge.service.RechargeService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;
import com.zjlp.face.web.server.user.shop.service.ShopService;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.producer.UserProducer;
import com.zjlp.face.web.util.files.ImageUp;
import com.zjlp.face.web.util.redis.RedisFactory;

@Service("memberBusiness")
public class MemberBusinessImpl implements MemberBusiness {

//	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private MemberService memberService;
	@Autowired
	private MarketingProducer marketingProducer;
	@Autowired
	private MemberCardService memberCardService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopProducer shopProducer;
	@Autowired
	private MemberTransactionRecordService memberTransactionRecordService;
	@Autowired
	private IntegralProducer integralProducer;
	@Autowired
	private SalesOrderService salesOrderService;
	@Autowired
	private RechargeService rechargeService;
	@Autowired
	private IntegralService integralService;
	@Autowired
	private UserProducer userProducer;
	
	@Deprecated
	@Override
	@Transactional
	public boolean recharge(Long sellerId, Long userId, Long cardId,
			Long activeId, Long amount) throws MemberException {
		try {
			//参数验证
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			AssertUtil.notNull(userId, "Param[userId] can't be null.");
			AssertUtil.notNull(cardId, "Param[cardId] can't be null.");
			AssertUtil.notNull(activeId, "Param[activeId] can't be null.");
			AssertUtil.notNull(amount, "Param[amount] can't be null.");
			AssertUtil.isTrue(amount > 0, "Param[amount] can't be null.");
			//数据权限验证
			MemberCard memberCard = memberService.getMemberCardById(cardId);
			AssertUtil.isTrue(sellerId.equals(memberCard.getSellerId()),
					"The member card[id={}] is not belong seller[id={}]", "非此卖家卡片", cardId, sellerId);
			AssertUtil.isTrue(userId.equals(memberCard.getUserId()),
					"The member card[id={}] is not belong user[id={}]", "非此买家卡片", cardId, userId);
			//充值活动验证
			
			//充值TODO
			this.recharge(cardId, activeId, amount);
			return true;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
	@Transactional
	private boolean recharge(Long cardId, Long activeId, Long amount) {
		//验证活动
		memberService.sumMemberCardAmount(cardId, amount,0L);
		return true;
	}

	@Override
	@Transactional
	public MemberAttendanceRecordVo registrat(String shopNo, Long userId,
			Long cardId) throws MemberException {
		try {
			AssertUtil.notNull(shopNo, "Param[shopNo] can't be null.");
			AssertUtil.notNull(userId, "Param[userId] can't be null.");
			AssertUtil.notNull(cardId, "Param[cardId] can't be null.");
			//签到规则获取
			MarketingActivityDetail rule = marketingProducer.getAttendanceRule(shopNo);
			AssertUtil.notNull(rule, "No attendance rule to running.");
			MemberCard memberCard = memberService.getMemberCardById(cardId);
			//数据权限
			Shop shop = shopProducer.getShopByNo(shopNo);
			AssertUtil.isTrue(shop.getUserId().equals(memberCard.getSellerId()), "", "用户权限不正确");
			//签到记录
			MemberAttendanceRecord lastRecord = memberService.getLastRecordByMemberCardId(cardId);
			//今日是否已签到
			AssertUtil.isTrue(!MemberAttendanceRecordDto.isTodayRecord(lastRecord), "", "今日已经签到过啦");
			//积分累加
			IntegralCalu integralCalu = new IntegralCalu(rule.getBaseVal(), rule.getStepAccumulate(), rule.getMaxVal());
			Integer currentCount = 1;
			if (MemberAttendanceRecordDto.isPreDayRecord(lastRecord)) {
				currentCount = lastRecord.getAttendanceNumber() + 1;
			} else {
				if(null != lastRecord) {
					lastRecord.setAttendanceNumber(0);
				}
			}
			integralProducer.editIntegral(cardId, Long.valueOf(integralCalu.getCurrentAdd(currentCount)),
					IntegralRecode.WAY_ADD, IntegralRecode.TYPE_SIGNIN);
//			memberService.sumMemberCardIntegral(cardId, integralCalu.getCurrentAdd(currentCount));
			MemberAttendanceRecord record = MemberAttendanceRecordDto.getNextRecord(lastRecord, cardId);
			memberService.addMemberAttendanceRecord(record);
			lastRecord = memberService.getLastRecordByMemberCardId(cardId);
			MemberAttendanceRecordVo vo = new MemberAttendanceRecordVo(integralCalu.getCurrentAdd(currentCount), 
					integralCalu.getNextAdd(currentCount));
			return vo;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
	@Override
	public boolean hasRegistratRule(String shopNo) throws MemberException {
		try {
			AssertUtil.notNull(shopNo, "Param[shopNo] can't be null.");
			MarketingActivityDetail detail = marketingProducer.getAttendanceRule(shopNo);
			return null != detail;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public MemberEnactment getMemberEnactment(Long sellerId)
			throws MemberException {
		try {
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			MemberEnactment enact = memberService.getEnactmentBySettleId(sellerId);
			return enact;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	@Transactional
	public boolean editMemberEnactment(final MemberEnactment enactment, final Long sellerId) {
		try {
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			AssertUtil.notNull(enactment, "Param[enactment] can't be null.");
			AssertUtil.notNull(enactment.getStartNo(), "Param[enactment.startNo] can't be null.");
			AssertUtil.notNull(enactment.getEndNo(), "Param[enactment.endNo] can't be null.");
			AssertUtil.notNull(enactment.getCardCode(), "Param[enactment.cardCode] can't be null.");
			AssertUtil.notNull(enactment.getId(), "Param[enactment.id] can't be null.");
			AssertUtil.isTrue(enactment.getStartNo().compareTo(enactment.getEndNo()) <= 0, "", "开始卡号要小于结束卡号");
			AssertUtil.isTrue(enactment.getCardCode().length() <= 5, "", "会员卡英文前缀最大位数不得超过5位");
			ImageUp imageUp = new ImageUp(ImageConstants.MEMBER_FILE, sellerId, "MEMBER_ENACTMENT", 
					String.valueOf(enactment.getId()), PropertiesUtil.getContexrtParam("memberFile")) {
				@Override
				protected void afterUBB(String imgData) {
					enactment.setMemberContent(imgData);
				}
				@Override
				protected void afterTypeImage(String code, String imgData) {
					if (ImageConstants.MEMBER_FILE.equals(code)) {
						enactment.setImgPath(imgData);
					}
				}
				@Override
				protected void editAll() {
					memberService.editEnactment(enactment, sellerId);
				}
			};
			if (StringUtils.isBlank(enactment.getMemberContent())) {
				enactment.setMemberContent(ConstantsFiled.EMPTY);
			}
			imageUp.addFileBizParam(ImageConstants.UBB_FILE, enactment.getMemberContent());
			//默认图片或是图标图片
			if (enactment.getImgPath().indexOf("/resource/") == -1) {
				imageUp.addFileBizParam(enactment.getImgPath());
			}
			memberService.editEnactment(enactment, sellerId);
			imageUp.excute();
			return true;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public List<MemberRule> findMemberList(Long sellerId) throws MemberException {
		try {
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			return marketingProducer.findMemberRuleList(sellerId);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	@Transactional
	public boolean editRule(List<MemberRule> rules, Long sellerId)
			throws MemberException {
		try {
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			AssertUtil.notEmpty(rules, "param[rules] can't be null.");
			for (int i = rules.size() - 1; i >= 0; i--) {
				MemberRule rule = rules.get(i);
				if (null == rule.getDiscount()) {
					rules.remove(i);
				}
			}
			MemberRule.isValidRules(rules);
			boolean isTrue = MemberRule.mergerList(rules, this.findMemberList(sellerId));
			AssertUtil.isTrue(isTrue, "unvalid data.");
			//保存或是编辑
			MarketingTool tool = marketingProducer.getMarketingTool(sellerId, MarketingToolDto.CJ_TYPE_DG, MarketingToolDto.PD_TYPE_HY);
			for (MemberRule rule : rules) {
				rule.setSellerId(sellerId);
				if(null != rule.getId()){
					marketingProducer.editDetail(MemberRule.cover(rule, null));
				} else {
					MarketingActivityDetail detail = MemberRule.cover(rule, tool.getId());
					marketingProducer.addDetail(detail);
				}
			}
			return true;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public Long findIntegralCountForWap(Long userId, String shopNo) {
		AssertUtil.notNull(userId, "参数userID不能为空");
		AssertUtil.notNull(shopNo, "参数shopNo不能为空");
		MemberCardDto member = memberService.getMemberCard(shopNo, userId);
		return null == member ? null : member.getAvailableIntegral();
	}

	@Override
	public Long findMemberCardIdByUserIdAndShopNo(Long userId, String shopNo) {
		AssertUtil.notNull(userId, "参数userId不能为空");
		AssertUtil.notNull(shopNo, "shopNo参数不能为空");
		MemberCardDto member = memberService.getMemberCard(shopNo, userId);
		return null == member ? null : member.getId();
	}

	@Override
	public List<IntegralVo> findIntegralListForWap(Long memberCardId, String year, String month) {
		//判断年份和月份
		String beginTime = null;
		String endTime = null;
		if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)) {
			int yearInt = Integer.parseInt(year);
			int monthInt = Integer.parseInt(month);
			beginTime = yearInt+"-"+monthInt+"-01";
			if (monthInt != 12) {
				endTime = yearInt+"-"+(monthInt+1)+"-01";
			} else {
				endTime = (yearInt+1)+"-01-01";
			}
		}
		//查询所有的积分记录
		List<IntegralRecode> integralRecodeList = this.memberService.findIntegralRecordListByMemberCardId(memberCardId, beginTime, endTime);
		//将积分记录中的时间，方式，积分封装成VO
		List<IntegralVo> voList = new ArrayList<IntegralVo>();
		if (integralRecodeList != null && integralRecodeList.size() > 0) {
			for (IntegralRecode integralRecode : integralRecodeList) {
				IntegralVo integralVo = new IntegralVo();
				//时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				integralVo.setDate(sdf.format(integralRecode.getCreateTime()));
				//操作类型
				Integer type = integralRecode.getType();
				Integer way = integralRecode.getWay();
				Long integral = integralRecode.getIntegral();
				if (type.intValue() == IntegralRecode.TYPE_DEDUCTION) {
					if (way.intValue() == IntegralRecode.WAY_FROZEN) {
						integralVo.setType("积分抵扣");
						if (null == integral || integral == 0) {
							integralVo.setIntegral(0+"");
						} else {
							integralVo.setIntegral("-"+integral);
						}
					}
					if (way.intValue() == IntegralRecode.WAY_UNFROZEN) {
						integralVo.setType("积分返回");
						if (null == integral || integral == 0) {
							integralVo.setIntegral(0+"");
						} else {
							integralVo.setIntegral("+"+integral);
						}
					}
				} else if (type.intValue() == IntegralRecode.TYPE_CONSUMER) {
					integralVo.setType("消费");
					if (null == integral || integral == 0) {
						integralVo.setIntegral(0+"");
					} else {
						integralVo.setIntegral("+"+integral);
					}
				} else if(type.intValue() == IntegralRecode.TYPE_SIGNIN) {
					integralVo.setType("签到");
					if (null == integral || integral == 0) {
						integralVo.setIntegral(0+"");
					} else {
						integralVo.setIntegral("+"+integral);
					}
				} else if(type.intValue() == IntegralRecode.TYPE_SEND) {
						integralVo.setType("店家赠送");
						if (null == integral || integral == 0) {
							integralVo.setIntegral(0+"");
						} else {
							integralVo.setIntegral("+"+integral);
						}
					}
				voList.add(integralVo);
			}
		}
		return voList;
	}

	@Override
	public MemberCard findMemberCardById(Long memberCardId) {
		AssertUtil.notNull(memberCardId, "参数memberCardID不能为空");
		MemberCard memberCard = this.memberService.getMemberCardById(memberCardId);
		return memberCard;
	}

	@Override
	public List<BalanceVo> findBalanceListByMemberCardId(Long memberCardId,
			String year, String month) {
		AssertUtil.notNull(memberCardId, "参数memberCardID不能为空");
		//判断年份和月份
		String beginTime = null;
		String endTime = null;
		if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)) {
			int yearInt = Integer.parseInt(year);
			int monthInt = Integer.parseInt(month);
			beginTime = yearInt+"-"+monthInt+"-01";
			if (monthInt != 12) {
				endTime = yearInt+"-"+(monthInt+1)+"-01";
			} else {
				endTime = (yearInt+1)+"-01-01";
			}
		}
		
		//查询账单并封装VO
		List<MemberTransactionRecord> recordList = this.memberService.findMemberTransactionRecordListByTime(memberCardId, beginTime, endTime);
		
		List<BalanceVo> voList = new ArrayList<BalanceVo>();
		if (null != recordList && recordList.size()>0) {
			for (MemberTransactionRecord memberTransactionRecord : recordList) {
				BalanceVo vo = new BalanceVo();
				//设置日期
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				vo.setDate(sdf.format(memberTransactionRecord.getTransTime()));
				Double transPrice = (double)(Math.round(memberTransactionRecord.getTransPrice())/100.0);
				DecimalFormat df = new DecimalFormat("0.00");
				String decimalPrice = df.format(transPrice);
				//分装类型
				if (memberTransactionRecord.getType().intValue() == 1) {
					vo.setType("充值");
					vo.setMoney("+"+decimalPrice);
				} else {
					vo.setType("消费");
					vo.setMoney("-"+decimalPrice);
				}
				voList.add(vo);
			}
		}
		return voList;
	}

	@Override
	public MarketingTool getMarketingTool(Long sellerId, Integer mkType,
			Integer pdType) throws MemberException {
		try {
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			AssertUtil.notNull(mkType, "Param[mkType] can't be null.");
			AssertUtil.notNull(pdType, "Param[pdType] can't be null.");
			MarketingTool tool = marketingProducer.getMarketingTool(sellerId, mkType, pdType);
			return tool;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	@Transactional
	public boolean addOrEditStandardActivity(Long sellerId, Integer cj,
			Integer pd, MarketingActivityDetail detail)
			throws MemberException {
		try {
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			AssertUtil.notNull(cj, "Param[cj] can't be null.");
			AssertUtil.notNull(pd, "Param[pd] can't be null.");
			AssertUtil.notNull(detail, "Param[detail] can't be null.");
			MarketingTool tool = this.getMarketingTool(sellerId, cj, pd);
			AssertUtil.notNull(tool, "Tool[] is not exists.");
			AssertUtil.isTrue(MarketingToolDto.STANDARD_TYPE_YES.equals(tool.getStandardType()), 
					"Tool[id={}] is not a standard activity.", tool.getId());
			List<MarketingActivityDetail> list = new ArrayList<MarketingActivityDetail>();
			list.add(detail);
			this.addOrEditActivityDetail(sellerId, tool.getId(), null, null, cj, pd, list);
			return true;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
	@Override
	@Transactional
	public boolean addOrEditNotStandardActivity(Long sellerId, Integer cj,
			Integer pd, MarketingActivityDto activity) throws MemberException {
		try {
			//验证
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			AssertUtil.notNull(cj, "Param[cj] can't be null.");
			AssertUtil.notNull(pd, "Param[pd] can't be null.");
			AssertUtil.notNull(activity, "Param[activity] can't be null.");
			AssertUtil.notEmpty(activity.getDetailList(), "param[activity.detailList] can't be null.");
			MarketingTool tool = this.getMarketingTool(sellerId, cj, pd);
			AssertUtil.notNull(tool, "Tool[] is not exists.");
			AssertUtil.isTrue(sellerId.equals(Long.valueOf(tool.getRemoteId())), "", "亲~不能乱改别人家的东西哟！");
			AssertUtil.isTrue(MarketingToolDto.STANDARD_TYPE_NO.equals(tool.getStandardType()), 
					"Tool[id={}] is a standard activity.", tool.getId());
			//添加或修改活动
			Long activityId = this.addOrEditActivity(sellerId, tool.getId(), activity.getId(), 
					activity.getStartTime(), activity.getEndTime());
			//添加或修改详情
			this.addOrEditActivityDetail(sellerId, tool.getId(), activityId, activity.getName(), cj, pd, 
					activity.getDetailList());
			return true;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	/**
	 * 添加或修改活动
	 * @Title: addOrEditActivity 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家
	 * @param toolId 营销工具id
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 * @date 2015年4月16日 上午10:58:54  
	 * @author lys
	 */
	@Transactional
	private Long addOrEditActivity(Long sellerId, Long toolId, Long activityId, Date startTime,
			Date endTime) {
		//参数验证
		AssertUtil.notNull(startTime, "Param[startTime] can't be null,");
		AssertUtil.notNull(endTime, "Param[endTime] can't be null,");
		AssertUtil.isTrue(endTime.after(startTime), "Param[endTime < startTime]", "亲，活动结束时间要比开始时间大哟！");
		Date now = new Date();
		AssertUtil.isTrue(startTime.after(now), "Param[startTime < now]", "亲，活动时间要大于当前时间哟");
		AssertUtil.isTrue(endTime.after(now), "Param[endTime < now]");
		//时间判断
		List<MarketingActivity> list = marketingProducer.findMarketingActivityByToolId(toolId);
		boolean isvalidTime = MarketingActivityDto.isValidTime(startTime, endTime, list, activityId);
		AssertUtil.isTrue(isvalidTime, "Activity[toolId={}] has already exists from {} to {}.", "亲~时间冲突了哟！", toolId, startTime, endTime);
		//新增
		if (null == activityId) {
			MarketingActivity activity = MarketingActivityDto.initActivity(sellerId, toolId, startTime, endTime);
			Long id = marketingProducer.addActivity(activity);
			return id;
		} else {
			//修改
			MarketingActivity activity = marketingProducer.getValidActivityById(activityId);
			AssertUtil.isTrue(sellerId.equals(Long.valueOf(activity.getRemoteId())), "", "亲~不能乱改别人家的东西哟！");
			MarketingActivity newActivity = new MarketingActivity(activity.getId(), startTime, endTime);
			marketingProducer.editActivity(newActivity);
			return activity.getId();
		}
	}
	
	/**
	 * 添加或修改活动详情
	 * @Title: addOrEditActivityDetail 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家id
	 * @param toolId 营销工具id
	 * @param activityId 活动id
	 * @param name 活动名称
	 * @param cj 场景
	 * @param pd 范围
	 * @param detailList 详情细项
	 * @date 2015年4月16日 上午10:59:36  
	 * @author lys
	 */
	@Transactional
	private void addOrEditActivityDetail(Long sellerId, Long toolId, Long activityId, String name, Integer cj, Integer pd, 
			List<? extends MarketingActivityDetail> detailList) {
		if (null == detailList || detailList.isEmpty() ) return;
		for (MarketingActivityDetail detail : detailList) {
			detail.setToolId(toolId);
			detail.setActivityId(activityId);
			if (null != detail.getId()) {
				MarketingActivityDetail old = marketingProducer.getMarketingDetailById(detail.getId());
				if (MarketingActivityDetailDto.equelEach(old, detail)) {
					continue;
				}
				//旧版本
				marketingProducer.removeDetailById(detail.getId(), sellerId);
				detail.setId(null);
			}
			this.checkRepeat(sellerId, cj, pd);
			MarketingActivityDetailDto.initData(detail, cj, pd, name);
			marketingProducer.addDetail(detail);
		}
	}

	/**
	 * 活动详情重复添加控制
	 * @Title: checkRepeat 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家
	 * @param cj 场景
	 * @param pd 范围
	 * @date 2015年4月15日 下午4:54:20  
	 * @author lys
	 */
	private void checkRepeat(Long sellerId, Integer cj, Integer pd) {
		if (MarketingToolDto.canRepeatDetail(cj, pd)) {
			return;
		}
		List<MarketingActivityDetail> list = marketingProducer.findDetail(sellerId, cj, pd);
		AssertUtil.isEmpty(list, "Activity Detail[cj={}, pd={}] has already exists.", "", cj, pd);
	}

	@Override
	public List<MarketingActivityDetail> findDetail(Long sellerId,
			Integer cjType, Integer pdType) {
		try {
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			AssertUtil.notNull(cjType, "Param[cjType] can't be null.");
			AssertUtil.notNull(pdType, "Param[pdType] can't be null.");
			return marketingProducer.findDetail(sellerId, cjType, pdType);
		} catch (RuntimeException e) {
			throw new MemberException(e);
		}
	}

	@Deprecated
	@Override
	public void generateMemberCard(String openId, Long adminId) throws MemberException{
		try {
			AssertUtil.notNull(openId, "对应公众号的唯一用户标识为空");
			AssertUtil.notNull(adminId, "店铺的用户id为空");
			// 添加会员卡
//			memberCardService.addMemberCard(openId, adminId, null);
			
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
	@Override
	@Transactional
	public MemberCard generateMemberCard(Long sellerId, String shopNo, Long userId) {
		MemberCard member = memberCardService.generateMemberCard(sellerId, userId, shopNo);
		Long integral = this.claimIntegral(userId, sellerId);
		member.setAvailableIntegral(integral);
		return member;
	}

	@Deprecated  //不进行关注领卡
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void bindMemberCard(String openId, User user, Shop shop) throws MemberException{
		try {
			AssertUtil.notNull(user.getId(), "参数买家用户id为空");
			AssertUtil.notNull(shop.getNo(), "参数店铺编号为空");
			// 查询该用户在该店铺的超管下是否有会员卡，有卡则不绑卡，无卡则绑卡
			MemberCardDto dto = new MemberCardDto(shop.getUserId(), user.getId());
			dto = memberService.getMemberCardForWap(dto);
			if(null == dto) {
				// 根据openId跟店铺编号查询会员微信关注关系
				if(StringUtils.isNotBlank(openId)) {
					MemberWechatRelation memberWechatRelation = new MemberWechatRelation();
					memberWechatRelation.setOpenId(openId);
					memberWechatRelation.setShopNo(shop.getNo());
					memberWechatRelation = memberService.getMemberWechatRelation(memberWechatRelation);
					if(null != memberWechatRelation && 
						null == memberWechatRelation.getUserId()) {
						// 设置买家用户id
						memberWechatRelation.setUserId(user.getId());
						// 修改会员微信关注关系
						memberService.editMemberWechatRelation(memberWechatRelation);
						// 会员卡绑定用户id
						MemberCard memberCard = memberService.getMemberCardById(memberWechatRelation.getMemberCardId());
						if(null == memberCard.getUserId()) {
							memberCard.setUserId(user.getId());
							memberService.editMemberCard(memberCard, shop.getUserId());
						}
					} 
				}
			}
		} catch (Exception e) {
			throw new MemberException(e);
		}
		
	}

	@Override
	public MemberCardDto getMemberCardForWap(MemberCardDto memberCardDto) throws MemberException{
		try {
			AssertUtil.notNull(memberCardDto.getUserId(), "参数会员卡用户id为空");
			AssertUtil.notNull(memberCardDto.getSellerId(), "参数会员卡管理人id为空");
			
			// 查询会员卡
			memberCardDto = memberService.getMemberCardForWap(memberCardDto);
			if(null != memberCardDto && StringUtils.isNotBlank(memberCardDto.getCardImgPath())) {
				if(StringUtils.isNotBlank(memberCardDto.getCardNameColor()) && !memberCardDto.getCardNameColor().startsWith("#")){
					memberCardDto.setCardNameColor("#" + memberCardDto.getCardNameColor());
				}
				if(StringUtils.isNotBlank(memberCardDto.getCardNoColor()) && !memberCardDto.getCardNoColor().startsWith("#")){
					memberCardDto.setCardNoColor("#" + memberCardDto.getCardNoColor());
				}
				memberCardDto.setCardImgPath(ImageConstants.getCloudstZoomPath(memberCardDto.getCardImgPath(), PropertiesUtil.getContexrtParam("memberFile")));
				// 设置会员卡等级名称
				MemberRule memberRule = marketingProducer.getMemberRule(memberCardDto.getSellerId(), memberCardDto.getConsumptionAmout());
				if(null != memberRule) {
					memberCardDto.setLevalName(memberRule.getName());
				}
			}
			
			return memberCardDto;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public Pagination<MemberCardDto> findMemberCardDtoByUserId(MemberCardDto memberCardDto,
			Pagination<MemberCardDto> pagination) throws MemberException{
		
		try {
			AssertUtil.notNull(memberCardDto.getUserId(), "参数用户id为空");
			// 查询用户会员卡列表
			pagination = memberService.findMemberCardPageList(memberCardDto, pagination);
			for (MemberCardDto memberCard : pagination.getDatas()) {
				if(StringUtils.isNotBlank(memberCard.getCardNameColor()) && !memberCard.getCardNameColor().startsWith("#")){
					memberCard.setCardNameColor("#" + memberCard.getCardNameColor());
				}
				if(StringUtils.isNotBlank(memberCard.getCardNoColor()) && !memberCard.getCardNoColor().startsWith("#")){
					memberCard.setCardNoColor("#" + memberCard.getCardNoColor());
				}
				memberCard.setCardImgPath(ImageConstants.getCloudstZoomPath(memberCard.getCardImgPath(), PropertiesUtil.getContexrtParam("memberFile")));
				// 设置会员卡等级名称
				MemberRule memberRule = marketingProducer.getMemberRule(memberCard.getSellerId(), memberCard.getConsumptionAmout());
				if(null != memberRule) {
					memberCard.setLevalName(memberRule.getName());
					memberCard.setDiscount(memberRule.getDiscount());
				}
				// 查询该会员卡适用的店铺
				//取消,不与微信绑定
//				List<Shop> shopList = shopService.findShopListByMemberCardId(memberCard.getId());
				
				Shop shop = shopService.getShopByUserId(memberCard.getSellerId());
				List<Shop> shopList = new ArrayList<Shop>();
				if(shop!=null){
					shopList.add(shop);
				}
				
				memberCard.setShopList(shopList);
			}
			
			/**
			 * 排序,最后登录的店铺,会员卡放在最上面
			 */
			final Long userLastLoginShopUserId = this.getRedisUserLastLoginShopUserId(memberCardDto.getUserId());
			
			if(null != userLastLoginShopUserId){
				List<MemberCardDto> list = pagination.getDatas();
				Collections.sort(list,new Comparator<MemberCardDto>(){
					@Override
					public int compare(MemberCardDto m1, MemberCardDto m2) {
						System.out.println(userLastLoginShopUserId+ "_"+m1.getSellerId() + "_" + m2.getSellerId());
						if(userLastLoginShopUserId.equals(m1.getSellerId())){
							return -1;
						}else if(userLastLoginShopUserId.equals(m2.getSellerId())){
							return 1;
						}
						return 0;
					}
				});
			}
			/**
			 * 
			 */
			
			return pagination;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
	/**
	 * 获取用户最后登录的店铺
	 * @param userId
	 * @return
	 */
	private Long getRedisUserLastLoginShopUserId(Long userId){
		if (null != RedisFactory.getWgjStringHelper()  && null != userId) {
			String key = "user_last_login_shop_" + userId;
			return RedisFactory.getWgjStringHelper().getAndSet(key, new AbstractRedisDaoSupport<Long>() {
				@Override
				public Long support() throws RuntimeException {
					return null;
				}
			});
		}
		
		return null;
	}

	@Override
	public void saveInfomation(MemberCardDto memberCardDto) {
		String district = memberCardDto.getDistrict();
		String city = memberCardDto.getCity();
		String province = memberCardDto.getProvince();
		if (StringUtils.isNotBlank(district)) {
			memberCardDto.setvAreaCode(Integer.valueOf(district));
		} else if (StringUtils.isNotBlank(city)) {
			memberCardDto.setvAreaCode(Integer.valueOf(city));
		} else if (StringUtils.isNotBlank(province)) {
			memberCardDto.setvAreaCode(Integer.valueOf(province));
		}
		this.memberService.saveInfomation(memberCardDto);
	}

	@Override
	public Pagination<MarketingActivityDto> findCzPage(
			Pagination<MarketingActivityDto> pagination, Long id)
			throws MemberException {
		try {
			AssertUtil.notNull(pagination, "Param[pagination] can't be null.");
			AssertUtil.notNull(id, "Param[id] can't be null.");
			MarketingActivityDto dto = new MarketingActivityDto();
			dto.setToolId(id);
			dto.getAide().setStartNum(pagination.getStart());
			dto.getAide().setPageSizeNum(pagination.getPageSize());
			Integer totalRow = marketingProducer.getActivityCount(dto);
			List<MarketingActivityDto> datas = marketingProducer.findActivityList(dto);
			pagination.setTotalRow(totalRow);
			pagination.setDatas(datas);
			if (null != datas && !datas.isEmpty()) {
				for (MarketingActivityDto data : datas) {
					if (null == data) continue;
					List<MarketingActivityDetailDto> list = this.
							findDetailByActivityId(data.getId());
					if (null == list || list.isEmpty()) continue;
					this.sortCzDetailList(list);
					data.setDetailList(list);
					data.setName(list.get(0).getName());
				}
			}
			return pagination;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
	private void sortCzDetailList(List<MarketingActivityDetailDto> list) {
		Collections.sort(list, new Comparator<MarketingActivityDetailDto>() {
			@Override
			public int compare(MarketingActivityDetailDto o1,
					MarketingActivityDetailDto o2) {
				if (null == o1 && null == o2) {
					return 0;
				} else if (null == o1) {
					return -1;
				} else if (null == o2) {
					return 1;
				}
				return o1.getPremiseVal().compareTo(o2.getPremiseVal());
			}
		});
	}
	
	private List<MarketingActivityDetailDto> findDetailByActivityId(Long activityId) {
		List<MarketingActivityDetailDto> list = marketingProducer.
				findDetailListByActivityId(activityId);
		return list;
	}

	@Override
	public MarketingActivityDto getMarketingActivityById(Long id, Long sellerId)
			throws MemberException {
		try {
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			AssertUtil.notNull(id, "Param[id] can't be null.");
			//根据id查看对应的活动
			MarketingActivity activity = marketingProducer.getValidActivityById(id);
			//数据权限
			AssertUtil.isTrue(sellerId.toString().equals(activity.getRemoteId()), "", "亲~不能偷看别人的东西哟！");
			MarketingActivityDto dto = TranscodeUtil.transParentToChild(activity, MarketingActivityDto.class);
			//查找详情
			List<MarketingActivityDetailDto> detailList = this.findDetailByActivityId(activity.getId());
			this.sortCzDetailList(detailList);
			dto.setDetailList(detailList);
			return dto;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	@Transactional
	public boolean delNotStantdardActivity(Long activityId, Long sellerId)
			throws MemberException {
		try {
			AssertUtil.notNull(sellerId, "Param[sellerId] can't be null.");
			AssertUtil.notNull(activityId, "Param[activityId] can't be null.");
			//根据id查看对应的活动
			MarketingActivity activity = marketingProducer.getValidActivityById(activityId);
			//数据权限
			AssertUtil.isTrue(sellerId.toString().equals(activity.getRemoteId()), "", "亲~不能偷看别人的东西哟！");
			//标记删除状态
			return marketingProducer.removeActivity(activityId);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public Pagination<MemberCardDto> findMemberCardPageForSeller(
			MemberCardDto cardDto, Pagination<MemberCardDto> pagination)
			throws MemberException {
		try {
			//参数验证
			AssertUtil.notNull(cardDto, "Param[cardDto] can't be null.");
			AssertUtil.notNull(cardDto.getSellerId(), "Param[cardDto.sellerId] can't be null.");
			AssertUtil.notNull(cardDto.getOrderbyCode(), "Param[cardDto.orderbycode] can't be null.");
			AssertUtil.notNull(pagination, "Param[pagination] can't be null.");
			//分页信息
			cardDto.getAide().setStartNum(pagination.getStart());
			cardDto.getAide().setPageSizeNum(pagination.getPageSize());
			Integer totalRow = memberService.getCountForSeller(cardDto);
			List<MemberCardDto> datas = memberService.findCardPageForSeller(cardDto);
			if (datas != null && !datas.isEmpty()) {
				List<MemberRule> ruleList = this.findMemberList(cardDto.getSellerId());
				for (MemberCardDto card : datas) {
					card.setLevalName(MemberRule.getMemberRuleByAmount(ruleList, card.getConsumptionAmout()).getName());
				}
			}
			pagination.setDatas(datas);
			pagination.setTotalRow(totalRow);
			return pagination;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public MemberCard getMemberCardById(Long id, Long sellerId, Long userId) throws MemberException {
		try {
			AssertUtil.notNull(id, "Param[id] can't be null.");
			AssertUtil.isTrue(null != sellerId || null != userId, "Not a valid user for get.");
			MemberCard card = memberService.getMemberCardById(id);
			if (null != sellerId) {
				AssertUtil.isTrue(sellerId.equals(card.getSellerId()), "", "亲，莫要偷看哟！");
			}
			if (null != userId) {
				AssertUtil.isTrue(userId.equals(card.getUserId()), "", "亲，莫要偷看哟！");
			}
			return card;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	@Transactional
	public void editMemberCard(MemberCard card, Long sellerId)
			throws MemberException {
		try {
			AssertUtil.notNull(card, "param[card] can't be null.");
			AssertUtil.notNull(card.getId(), "param[card.id] can't be null.");
			AssertUtil.notNull(sellerId, "param[sellerId] can't be null.");
//			AssertUtil.notNull(card.getConsumptionAmout(), "param[consumptionAmout] can't be null.");
//			AssertUtil.isTrue(card.getConsumptionAmout() >= 0, "param[consumptionAmout < 0]");
//			MemberCard old = memberService.getMemberCardById(card.getId());
			//判断消费是否改变
//			if (!card.getConsumptionAmout().equals(old.getConsumptionAmout())) {
//				//修改消费额
//				Long ajAmount = memberService.editConsumptionAmount(card.getId(), card.getConsumptionAmout());
//				//消费额记录
//				ConsumptionAdjustmentRecord record = ConsumptionAdjustmentRecordDto
//						.init(card.getId(), ajAmount, 0L);
//				memberService.addConsumptionAdjustmentRecord(record);
//				card.setConsumptionAmout(null);
//			}
			if (StringUtils.isBlank(card.getRemark())) {
				card.setRemark(ConstantsFiled.EMPTY);
			}
			if (StringUtils.isBlank(card.getUserName())) {
				card.setUserName(ConstantsFiled.EMPTY);
			}
			card.setConsumptionAmout(null); //消费额不调整
			memberService.editMemberCard(card, sellerId);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public Pagination<MemberTransactionRecordDto> findTransactionPage(
			MemberTransactionRecordDto dto,
			Pagination<MemberTransactionRecordDto> pagination)
			throws MemberException {
		try {
			//参数验证
			AssertUtil.notNull(dto, "param[dto] can't be null.");
			AssertUtil.notNull(dto.getMemberCardId(), "param[dto.cardId] can't be null.");
			AssertUtil.notNull(dto.getType(), "param[dto.type] can't be null.");
			AssertUtil.notNull(dto.getSellerId(), "param[dto.sellerId] can't be null.");
			Integer totalRow = memberTransactionRecordService.getTransactionRecordCount(dto);
			dto.getAide().setStartNum(pagination.getStart());
			dto.getAide().setPageSizeNum(pagination.getPageSize());
			List<MemberTransactionRecordDto> datas = memberTransactionRecordService.findTransactionRecordList(dto);
			pagination.setTotalRow(totalRow);
			pagination.setDatas(datas);
			return pagination;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	@Transactional
	public boolean editMarketingRuleStatus(Long sellerId, Long toolId,
			Integer status) throws MemberException {
		try {
			//参数验证
			AssertUtil.notNull(sellerId, "param[sellerId] can't be null.");
			AssertUtil.notNull(toolId, "param[toolId] can't be null.");
			AssertUtil.notNull(status, "param[status] can't be null.");
			//营销工具获取  //数据权限验证
			MarketingTool tool = marketingProducer.getMarketingToolById(toolId);
			AssertUtil.isTrue(String.valueOf(sellerId).equals(tool.getRemoteId()), "", "不要偷看东西了~");
			//状态修改
			marketingProducer.editMarketingRuleStatus(toolId, status);
			return true;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public boolean delDetailById(Long id, Long sellerId) throws MemberException {
		try {
			AssertUtil.notNull(sellerId, "param[sellerId] can't be null.");
			AssertUtil.notNull(id, "param[id] can't be null.");
			marketingProducer.removeDetailById(id, sellerId);
			return true;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public boolean isTodayAttendance(Long cardId) throws MemberException {
		try {
			//签到记录
			MemberAttendanceRecord lastRecord = memberService.getLastRecordByMemberCardId(cardId);
			return MemberAttendanceRecordDto.isTodayRecord(lastRecord);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	public List<ConsumeVo> findOrderListByUserIdAndSellerIdForWap(Long userId,
			Long sellerId, String year, String month, Long memberCardId) {
		AssertUtil.notNull(userId, "userID参数不能为空");
		AssertUtil.notNull(sellerId, "sellerId参数不能为空");
		//判断年份和月份
		String beginTime = null;
		String endTime = null;
		if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)) {
			int yearInt = Integer.parseInt(year);
			int monthInt = Integer.parseInt(month);
			beginTime = yearInt+"-"+monthInt+"-01";
			if (monthInt != 12) {
				endTime = yearInt+"-"+(monthInt+1)+"-01";
			} else {
				endTime = (yearInt+1)+"-01-01";
			}
		}
		
		//通过userid和shopNo查询消费记录
		List<SalesOrder> salesOrderList = this.salesOrderService.findOrderListByUserIdAndSellerIdForWap(userId, sellerId, beginTime, endTime );
		//通过会员卡ID查询充值记录
		List<Recharge> rechargeList = this.rechargeService.findRechargeListByUserAccountAndAccountType(String.valueOf(memberCardId), beginTime, endTime);
		//分装VO
		List<ConsumeVo> consumeVoList = new ArrayList<ConsumeVo>();
		if (null != salesOrderList && !salesOrderList.isEmpty()) {
			for (SalesOrder salesOrder : salesOrderList) {
				ConsumeVo initConsumeVo = this.initConsumeVo(salesOrder.getPayPrice(), salesOrder.getObtainIntegral(), salesOrder.getPaymentTime());
				consumeVoList.add(initConsumeVo);
			}
		}
		if (rechargeList != null && !rechargeList.isEmpty()) {
			for (Recharge aRecharge : rechargeList) {
				ConsumeVo initConsumeVo = this.initConsumeVo(aRecharge.getDiscountPrice(), aRecharge.getObtainIntegral(), aRecharge.getRechargeTime());
				consumeVoList.add(initConsumeVo);
			}
		}
		//对集合通过时间进行排序
		DateComparator dc = new DateComparator();
		Collections.sort(consumeVoList,dc);
		return consumeVoList;
	}
	
	private ConsumeVo initConsumeVo(Long price, Long integral, Date time) {
		ConsumeVo consumeVo = new ConsumeVo();
		//封装钱
		Double realPrice = (double)(Math.round(price)/100.0);
		DecimalFormat df = new DecimalFormat("0.00");
		String decimalPrice = df.format(realPrice);
		consumeVo.setMoney(decimalPrice);
		//封装积分,获取交易获得的积分
		if (null == integral || integral == 0) {
			consumeVo.setIntegral(0+"");
		} else {
			consumeVo.setIntegral("+"+integral); 
		}
		//封装时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date paymentTime = time;
		if (null != paymentTime) {
			consumeVo.setDate(sdf.format(time));
		} 
		return consumeVo;
	}

	@Override
	public MemberCardDto getMemberCardForShow(MemberCardDto memberCardDto) throws MemberException{
		try {
			AssertUtil.notNull(memberCardDto, "参数会员卡信息为空");
			// 查询会员卡信息
			memberCardDto = memberService.getMemberCardForShow(memberCardDto);
			if(null != memberCardDto && StringUtils.isNotBlank(memberCardDto.getCardImgPath())) {
				if(StringUtils.isNotBlank(memberCardDto.getCardNameColor()) && !memberCardDto.getCardNameColor().startsWith("#")){
					memberCardDto.setCardNameColor("#" + memberCardDto.getCardNameColor());
				}
				if(StringUtils.isNotBlank(memberCardDto.getCardNoColor()) && !memberCardDto.getCardNoColor().startsWith("#")){
					memberCardDto.setCardNoColor("#" + memberCardDto.getCardNoColor());
				}
				memberCardDto.setCardImgPath(ImageConstants.getCloudstZoomPath(memberCardDto.getCardImgPath(), PropertiesUtil.getContexrtParam("memberFile")));
				// 设置会员卡等级名称
				MemberRule memberRule = marketingProducer.getMemberRule(memberCardDto.getSellerId(), memberCardDto.getConsumptionAmout());
				if(null != memberRule) {
					memberCardDto.setLevalName(memberRule.getName());
				}
			}
			return memberCardDto;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}

	@Override
	@Transactional
	public boolean sendIntegral(Long userId, Long sellerId, Long integral)
			throws MemberException {
		try {
			//参数过滤
			AssertUtil.notNull(userId, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.getErrorMesage(), "用户id");
			AssertUtil.notNull(sellerId, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.getErrorMesage(), "卖家id");
			AssertUtil.isTrue(null != integral && integral > 0, CErrMsg.NULL_PARAM.getErrCd(), 
					CErrMsg.NULL_PARAM.getErrorMesage(), "积分个数");
			//用户验证
			User user = userProducer.getUserById(userId);
			AssertUtil.notNull(user, CErrMsg.NULL_RESULT.getErrCd(), CErrMsg.NULL_RESULT.getErrorMesage(), "用户");
			
			//查询最近一条赠送记录
			SendIntegralRecode lastRecord = integralService.getLastRecord(new SendIntegralRecode(sellerId, userId));
			//累计赠送积分
			Long staticsIntegral = CalculateUtils.getSum(integral, null == lastRecord ? 0L : lastRecord.getStatisticsIntegral());
			//赠送记录生成
			SendIntegralRecode record = SendIntegralRecode.newInstance(sellerId, userId, integral, staticsIntegral);
			
			//入库处理
			integralService.addSendIntegralRecode(record);
			//是否已经有会员卡
			MemberCard member = memberCardService.getMemberCard(new MemberCard(sellerId, userId));
			//如果已经有会员卡，则直接自动领取会员卡
			if (null != member) {
				this.claimIntegral(userId, sellerId);
			}
			
			//发送短信
			Sms.getInstance().sendUms(SmsContent.UMS_501, user.getCell(), getUrl(sellerId));
			return true;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
	//拼接url ： 店铺首页
	private String getUrl(Long sellerId) {
		//http://www.o2osl.com/wap/HZJZ1507030952D4uQx1/any/index.htm
		Shop shop = shopProducer.getShopByUserId(sellerId);
		StringBuilder sb = new StringBuilder(PropertiesUtil.getContexrtParam("WGJ_URL"));
		sb.append("/wap/").append(shop.getNo()).append("/any/gwscIndex.htm");
		return sb.toString();
	}

	@Override
	@Transactional
	public Long claimIntegral(Long userId, Long sellerId)
			throws MemberException {
		try {
			//参数验证
			AssertUtil.notNull(userId, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.getErrorMesage(), "用户id");
			AssertUtil.notNull(sellerId, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.getErrorMesage(), "卖家id");
			//获取会员卡
			MemberCard member = memberCardService.getMemberCard(new MemberCard(sellerId, userId));
			AssertUtil.notNull(member, CErrMsg.NULL_RESULT.getErrCd(), CErrMsg.NULL_RESULT.getErrorMesage(), "会员卡");
			//赠送积分
			return this.sendIntegral(member);
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
	@Transactional
	private Long sendIntegral(MemberCard member){
		//统计未领取积分个数
		Long integral = integralService.sumSendIntegral(member.getSellerId(), member.getUserId());
		if (null == integral || integral <= 0) {
			return 0L;
		}
		//用户积分累计
		integralProducer.editIntegral(member.getId(), integral, 
				IntegralRecode.WAY_ADD, IntegralRecode.TYPE_SEND);
		//积分记录状态修改为已领取状态
		integralService.editClaimIntegralStatus(member.getSellerId(), member.getUserId());
		return integral;
	}

	@Override
	public MemberCard isReceiveMemberCard(Long userId, Long sellerId)
			throws MemberException {
		//参数验证
		AssertUtil.notNull(userId, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.getErrorMesage(), "用户id");
		AssertUtil.notNull(sellerId, CErrMsg.NULL_PARAM.getErrCd(), CErrMsg.NULL_PARAM.getErrorMesage(), "卖家id");
		//获取会员卡
		MemberCard member = memberCardService.getMemberCard(new MemberCard(sellerId, userId));
		return member;
	}
	/**
	 * 积分赠送记录列表
	 * @param sendDto 
	 * @param patination
	 */
	@Override
	public Pagination<SendIntegralRecodeDto> queryIntegralRecode(SendIntegralRecodeDto sendDto,
			Pagination<SendIntegralRecodeDto> pagination)
			throws MemberException {
		try{
			AssertUtil.notNull(sendDto, "param[dto] can't be null.");
			AssertUtil.notNull(sendDto.getSellerId(), "param[dto.sellerId] can't be null.");
			Integer totalRow = memberService.getSendIntegralRecode(sendDto);
			sendDto.getAide().setStartNum(pagination.getStart());
			sendDto.getAide().setPageSizeNum(pagination.getPageSize());
			List<SendIntegralRecodeDto> datas = memberService.querySendIntegralRecode(sendDto);
			pagination.setTotalRow(totalRow);
			pagination.setDatas(datas);
			return pagination;
		} catch (Exception e) {
			throw new MemberException(e);
		}
	}
	
}
