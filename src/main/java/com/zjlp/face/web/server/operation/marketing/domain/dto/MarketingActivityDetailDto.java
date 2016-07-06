package com.zjlp.face.web.server.operation.marketing.domain.dto;

import java.util.ArrayList;
import java.util.List;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;

public class MarketingActivityDetailDto extends MarketingActivityDetail {

	// 操作类型
	/** 折扣型 */
	public static final Integer TYPE_ZK = 1;
	/** 减价 */
	public static final Integer TYPE_JJ = 2;
	/** 抵扣 */
	public static final Integer TYPE_DK = 3;
	/** 赠送 */
	public static final Integer TYPE_ZS = 4;
	//单位
	/** 单位：金额 */
	public static final Integer UNIT_AMOUNT = 1;
	/** 单位：积分 */
	public static final Integer UNIT_JIFEN = 2;

	private static final long serialVersionUID = 9022803105386314017L;
	private String premiseValAmount;
	private String resultValAmount;
	
	public String getPremiseValAmount() {
		return premiseValAmount;
	}
	public void setPremiseValAmount(String premiseValAmount) {
		if (null == premiseValAmount) return; 
		super.setPremiseVal(CalculateUtils.converYuantoPenny(premiseValAmount).intValue());
		this.premiseValAmount = premiseValAmount;
	}
	public String getResultValAmount() {
		return resultValAmount;
	}
	public void setResultValAmount(String resultValAmount) {
		if (null == resultValAmount) return;
		super.setResultVal(CalculateUtils.converYuantoPenny(resultValAmount).intValue());
		this.resultValAmount = resultValAmount;
	}
	/**
	 * 会员规则初始化
	 * 
	 * @Title: initMemberRule
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param toolId
	 * @return
	 * @date 2015年4月14日 下午3:33:27
	 * @author lys
	 */
	public static List<MarketingActivityDetail> initMemberRule(Long toolId) {
		List<MarketingActivityDetail> list = new ArrayList<MarketingActivityDetail>();
		list.add(initMember(toolId, "普通会员", 0, 100));
		list.add(initMember(toolId, "金卡会员", 20000, 100));
		list.add(initMember(toolId, "白金卡会员", 50000, 100));
		list.add(initMember(toolId, "钻石卡会员", 100000, 100));
		return list;
	}
	
	private static MarketingActivityDetail initMember(Long toolId, String name, 
			Integer premiseVal, Integer resultVal) {
		MarketingActivityDetail detail = new MarketingActivityDetail();
		detail.setToolId(toolId);
		detail.setStatus(Constants.VALID);
		detail.setType(TYPE_ZK);
		detail.setName(name);
		detail.setPremiseVal(premiseVal);
		detail.setResultVal(resultVal);
		detail.setToUnit(UNIT_AMOUNT); //消费额
		return detail;
	}

	public static void checkMemberRuleData(MarketingActivityDetail rule) {
		AssertUtil.notNull(rule.getToolId(),
				"[MemberRule Check] tool's id can't be null.");
		AssertUtil.notNull(rule.getStatus(),
				"[MemberRule Check] tool's status can't be null.");
		AssertUtil.notNull(rule.getType(),
				"[MemberRule Check] tool's type can't be null.");
		AssertUtil.notNull(rule.getName(),
				"[MemberRule Check] tool's name can't be null.");
		AssertUtil.notNull(rule.getPremiseVal(),
				"[MemberRule Check] tool's premiseVal can't be null.");
		AssertUtil.notNull(rule.getResultVal(),
				"[MemberRule Check] tool's resultVal can't be null.");
		AssertUtil.notNull(rule.getToUnit(),
				"[MemberRule Check] tool's toUnit can't be null.");
	}
	
	public static void initData(MarketingActivityDetail detail, Integer cjType, Integer pdType, String name) {
		if (MarketingToolDto.PD_TYPE_JF.equals(pdType)) {
			if (MarketingToolDto.CJ_TYPE_DG.equals(cjType)) {
				detail.setType(TYPE_DK);
				detail.setFromUnit(UNIT_AMOUNT);
				detail.setName("积分抵扣");
			} else if (MarketingToolDto.CJ_TYPE_QD.equals(cjType)) {
				detail.setType(TYPE_ZS);
				detail.setName("签到送积分");
			} else if (MarketingToolDto.CJ_TYPE_ZF.equals(cjType)) {
				detail.setType(TYPE_ZS);
				detail.setName("消费送积分");
			}
			detail.setToUnit(UNIT_JIFEN);
		} else if (MarketingToolDto.PD_TYPE_HY.equals(pdType)) {
			if (MarketingToolDto.CJ_TYPE_CZ.equals(cjType)) {
				detail.setName(name);
				detail.setType(TYPE_ZK);
			}
			detail.setToUnit(UNIT_AMOUNT);
		}
		detail.setStatus(Constants.VALID);
	}
	
	//会员充值规则验证
	public static void checkCzGz(List<MarketingActivityDetailDto> detailList) {
		AssertUtil.notEmpty(detailList, "会员充值规则不能为空");
		MarketingActivityDetailDto pre = detailList.get(0);
		MarketingActivityDetailDto fix = null;
		for (int i = 1; i < detailList.size(); i++) {
			fix = detailList.get(i);
			boolean istrue = pre.getPremiseVal().compareTo(fix.getPremiseVal()) < 0 
					&& pre.getResultVal().compareTo(fix.getResultVal()) < 0;
			AssertUtil.isTrue(istrue, "会员充值规则不正确");
			pre = fix;
		}
	}
	
	public static boolean equelEach(MarketingActivityDetail detail1, MarketingActivityDetail detail2) {
		if (null == detail1 || null == detail2) {
			return false;
		}
		return isEq(detail1.getToolId(), detail2.getToolId()) //营销工具
				&& isEq(detail1.getActivityId(), detail2.getActivityId()) //活动ID
//				&& detail1.getName().equals(detail2.getName()) //名称
//				&& isEq(detail1.getType(), detail2.getType()) //操作类型
				&& isEq(detail1.getMaxVal(), detail2.getMaxVal()) //限制值：最大值
				&& isEq(detail1.getMinVal(), detail2.getMinVal()) //限制值：最小值
				&& isEq(detail1.getPremiseVal(), detail2.getPremiseVal()) //条件值
				&& isEq(detail1.getStep(), detail2.getStep()) //步值（默认值 1）
				&& isEq(detail1.getStepAccumulate(), detail2.getStepAccumulate()) //累加值
				&& isEq(detail1.getResultVal(), detail2.getResultVal()) //结果值
				&& isEq(detail1.getBaseVal(), detail2.getBaseVal()); //基础值
//				&& isEq(detail1.getFromUnit(), detail2.getFromUnit()) 
//				&& isEq(detail1.getToUnit(), detail2.getToUnit());
	}
	public static <T extends Comparable<T>>  boolean isEq(T t1, T t2) {
		if (null == t1 && null == t2) {
			return true;
		}
		if (null == t1 || null == t2) {
			return false;
		}
		return t1.compareTo(t2) == 0;
	}
}
