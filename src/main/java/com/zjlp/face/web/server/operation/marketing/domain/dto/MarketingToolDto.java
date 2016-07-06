package com.zjlp.face.web.server.operation.marketing.domain.dto;

import java.util.List;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivity;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingTool;

public class MarketingToolDto extends MarketingTool {

	private static final long serialVersionUID = 6862411596032202359L;
	//营销类型
	/** 场景： 订购*/
	public static final Integer CJ_TYPE_DG = 1;
	/** 场景： 支付*/
	public static final Integer CJ_TYPE_ZF = 2;
	/** 场景： 签到*/
	public static final Integer CJ_TYPE_QD = 3;
	/** 场景： 充值*/
	public static final Integer CJ_TYPE_CZ = 4;
	//产品范围
	/** 产品范围： 会员*/
	public static final Integer PD_TYPE_HY = 1;
	/** 产品范围： 商品*/
	public static final Integer PD_TYPE_SP = 2;
	/** 产品范围： 钱包*/
	public static final Integer PD_TYPE_QB = 3;
	/** 产品范围： 积分*/
	public static final Integer PD_TYPE_JF = 4;
	//是否标准
	/** 是否标准:否 */
	public static final Integer STANDARD_TYPE_NO = -1;
	/** 是否标准:是 */
	public static final Integer STANDARD_TYPE_YES = 1;
	//用户类型
	public static final Integer REMOTE_TYPE_SELLER = 1;
	
	public static MarketingTool initMemberRuleTool(Long sellerId) {
		return initTool(sellerId, CJ_TYPE_DG, PD_TYPE_HY, STANDARD_TYPE_YES, Constants.VALID);
	}
	public static MarketingTool initTool(Long sellerId, Integer mkType, 
			Integer pdType, Integer standardType) {
		return initTool(sellerId, mkType, pdType, standardType, Constants.FREEZE);
	}
	
	public static MarketingTool initTool(Long sellerId, Integer mkType, 
			Integer pdType, Integer standardType, Integer status) {
		MarketingTool tool = new MarketingTool();
		tool.setRemoteId(String.valueOf(sellerId));
		tool.setRemoteType(1);
		tool.setMarketingType(mkType);
		tool.setProductType(pdType);
		tool.setStatus(status);
		tool.setStandardType(standardType);
		return tool;
	}
	
	//是否要进行比较
	//积分 的订购  支付  签到	
	public static boolean canRepeatDetail(Integer cj, Integer pd) {
		if (PD_TYPE_JF.equals(pd)) {
			if (CJ_TYPE_DG.equals(cj) || CJ_TYPE_ZF.equals(cj) || CJ_TYPE_QD.equals(cj)) {
				return false;
			}
		} else if (PD_TYPE_HY.equals(pd)) {
			if (CJ_TYPE_DG.equals(cj) || CJ_TYPE_CZ.equals(cj)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	public static void checkInput(MarketingTool tool) {
		AssertUtil.notNull(tool.getRemoteId(),
				"[MarketingTool Check] tool's remoteId can't be null.");
		AssertUtil.notNull(tool.getRemoteType(),
				"[MarketingTool Check] tool's remoteTye can't be null.");
		AssertUtil.notNull(tool.getMarketingType(),
				"[MarketingTool Check] tool's marketingType can't be null.");
		AssertUtil.notNull(tool.getProductType(),
				"[MarketingTool Check] tool's productType can't be null.");
		AssertUtil.notNull(tool.getStatus(),
				"[MarketingTool Check] tool's status can't be null.");
		AssertUtil.notNull(tool.getStandardType(),
				"[MarketingTool Check] tool's standardType can't be null.");
	}
	
	private List<MarketingActivity> activityList;
	private List<MarketingActivityDetail> detailList;
	public List<MarketingActivity> getActivityList() {
		return activityList;
	}
	public void setActivityList(List<MarketingActivity> activityList) {
		this.activityList = activityList;
	}
	public List<MarketingActivityDetail> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<MarketingActivityDetail> detailList) {
		this.detailList = detailList;
	}
	
}
