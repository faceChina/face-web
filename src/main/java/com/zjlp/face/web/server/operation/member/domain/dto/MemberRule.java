package com.zjlp.face.web.server.operation.member.domain.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDetailDto;

/**
 * 会员列表
 * 
 * @ClassName: Member
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年4月9日 下午6:01:11
 */
public class MemberRule implements Comparable<MemberRule> {
	
	@Override
	public int compareTo(MemberRule o) {
		if (null == o) {
			return 0;
		}
		return this.consumptionAmout-o.getConsumptionAmout();
	}
	// 主键
	private Long id;
	// 管理账户
	private Long sellerId;
	// 姓名
	private String name;
	// 消费额
	private Integer consumptionAmout;
	// 折扣
	private Integer discount;
	//消费额
	private String amountString;
	
	public MemberRule(){
	}
	
	public static MarketingActivityDetail cover(MemberRule memberRule, Long toolId) {
		if (null == memberRule)
			return null;
		MarketingActivityDetail activityDetail = new MarketingActivityDetail();
		activityDetail.setId(memberRule.getId());
		activityDetail.setName(memberRule.getName());
		activityDetail.setPremiseVal(memberRule.getConsumptionAmout());
		activityDetail.setResultVal(memberRule.getDiscount());
		activityDetail.setToolId(toolId);
		activityDetail.setStatus(Constants.VALID);
		activityDetail.setType(MarketingActivityDetailDto.TYPE_ZK);
		activityDetail.setToUnit(MarketingActivityDetailDto.UNIT_AMOUNT);
		return activityDetail;
	}
	public static MemberRule converToRule(MarketingActivityDetail detail) {
		if (null == detail) return null;
		MemberRule rule = new MemberRule();
		rule.setId(detail.getId());
		rule.setConsumptionAmout(detail.getPremiseVal());
		rule.setName(detail.getName());
		rule.setDiscount(detail.getResultVal());
		return rule;
	}
	public static List<MemberRule> converToRule(List<MarketingActivityDetail> details) {
		if (null == details || details.isEmpty()) {
			return null;
		}
		List<MemberRule> rules = new ArrayList<MemberRule>();
		for (MarketingActivityDetail detail : details) {
			if (null == detail) continue;
			rules.add(converToRule(detail));
		}
		return rules;
	}
	public static void checkInput(MemberRule memberRule) {
		AssertUtil.notNull(memberRule.consumptionAmout, "consumptionAmout can't be null");
		AssertUtil.isTrue(memberRule.consumptionAmout >= 0, "discount can't be null");
		AssertUtil.notNull(memberRule.discount, "discount can't be null");
		AssertUtil.isTrue(memberRule.discount > 0 || memberRule.discount <= 100, "discount can't be null");
		AssertUtil.notNull(memberRule.name, "name can't be null");
	}
	public static Comparator<MemberRule> memberComparator = new Comparator<MemberRule>() {
		@Override
		public int compare(MemberRule o1, MemberRule o2) {
			if (null == o1 && null == o2) {
				return 0;
			} else if (null == o1) {
				return 1;
			} else if (null == o2) {
				return -1;
			}
			if(null == o1.getConsumptionAmout() && null == o2.getConsumptionAmout()){
				return 0;
			} else if (null == o1.getConsumptionAmout()) {
				return -1;
			} else if (null == o2.getConsumptionAmout()) {
				return 1;
			}
			return o1.getConsumptionAmout().compareTo(o2.getConsumptionAmout());
		}
	};
	
	/**
	 * 会员规则是否合理
	 * 1.会员规则中的adminId  name  consumptionAmout  discount不能为空
	 * 2.按照消费金额排序，排序后的折扣应该为反排序
	 * @Title: isValidMemberList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberList
	 * @return 符合规则：true  其他：false
	 * @date 2015年4月9日 下午6:11:16  
	 * @author lys
	 */
	public static boolean isValidRules(List<MemberRule> memberList) {
		if(null == memberList || memberList.isEmpty()){
			return false;
		}
		checkInput(memberList.get(0));
		//只有一条规则
		int size = memberList.size();
		if(1 == size){
			return true;
		}
		//排序
		Collections.sort(memberList, memberComparator);
		//多条规则
		MemberRule pre = memberList.get(0);
		MemberRule fix = null;
		for(int i = 0; i < size; i++){
			if(i == (size - 1)){
				break;
			}
			fix = memberList.get(i + 1);
			//必须字段
			checkInput(fix);
			//消费金额
			if (pre.getConsumptionAmout().compareTo(fix.getConsumptionAmout()) >= 0) {
				return false;
			}
			//名称不一致
			if (pre.getName().trim().equals(fix.getName().trim())) {
				return false;
			}
			//折扣倒序
			if (pre.getDiscount().compareTo(fix.getDiscount()) < 0) {
				return false;
			}
			pre = fix;
		}
		return true;
	}
	/**
	 * 对比数据库的数据与页面的数据
	 * @Title: mergerList 
	 * @Description: 验证页面提交数据合法性
	 * @param pageList
	 * @param databaseList
	 * @return
	 * @date 2014年11月11日 下午3:49:09  
	 * @author Administrator
	 */
	public static boolean mergerList(List<MemberRule> pageList, List<MemberRule> databaseList) {
		if (CollectionUtils.isEmpty(pageList) && CollectionUtils.isEmpty(databaseList)) {
			return true;
		}
		List<Long> idList = new ArrayList<Long>();
		for (MemberRule mb1 : databaseList) {
			idList.add(mb1.getId());
		}
		for (MemberRule mb2 : pageList) {
			if (idList.contains(mb2.getId()) || null == mb2.getId()) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
	public static MemberRule getMemberRuleByAmount(List<MemberRule> ruleList, Long amount) {
		if (null == ruleList || ruleList.isEmpty() || null == amount) {
			return null;
		}
		Collections.sort(ruleList);
		MemberRule selectRule = null;
		for (MemberRule memberRule : ruleList) {
			if (null == memberRule) continue;
			if (amount.compareTo(Long.valueOf(memberRule.getConsumptionAmout())) >= 0) {
				selectRule = memberRule;
			}
		}
		return selectRule;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getConsumptionAmout() {
		return consumptionAmout;
	}
	public void setConsumptionAmout(Integer consumptionAmout) {
		if (null != consumptionAmout) {
			amountString = CalculateUtils.converFenToYuan(consumptionAmout.longValue());
		}
		this.consumptionAmout = consumptionAmout;
	}
	public Integer getDiscount() {
			return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public String getAmountString() {
		return amountString;
	}
	public void setAmountString(String amountString) {
		if (null != amountString) {
			consumptionAmout = CalculateUtils.converYuantoPenny(amountString).intValue();
		}
		this.amountString = amountString;
	}
	@Override
	public String toString() {
		return new StringBuilder("MemberRule [id=").append(id).append(", sellerId=")
				.append(sellerId).append(", name=").append(name)
				.append(", consumptionAmout=").append(consumptionAmout)
				.append(", discount=").append(discount)
				.append("]").toString();
	}

}