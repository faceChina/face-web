package com.zjlp.face.web.server.operation.marketing.producer;

import java.util.List;

import com.zjlp.face.web.exception.ext.MarketingException;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivity;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingTool;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketOrderDetailDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDetailDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDto;
import com.zjlp.face.web.server.operation.marketing.domain.vo.MarketingActivityDetailVo;
import com.zjlp.face.web.server.operation.member.domain.dto.MemberRule;
import com.zjlp.face.web.server.trade.cart.domain.vo.SellerVo;

public interface MarketingProducer {
	
	/**
	 * 营销工具初始化
	 * @Title: initMarketingTool 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家id
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月17日 下午4:09:27  
	 * @author lys
	 */
	boolean initMarketingTool(Long sellerId) throws MarketingException;

	/**
	 * 查询营销活动明细信息
	 * @Title: getMarketingActivityDetail 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家ID
	 * @param marketingType	营销类型
	 * @param productType 操作对象
	 * @param condition 定位条件
	 * @return
	 * @date 2015年4月15日 下午4:34:17  
	 * @author dzq
	 */
	MarketingActivityDetail getMarketingActivityDetail(Long sellerId,Integer marketingType, Integer productType,Integer condition);
	
	/**
	 * 查询营销活动明细信息
	 * @Title: getMarketingActivityDetail 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo 店铺编号
	 * @param marketingType	营销类型
	 * @param productType 操作对象
	 * @param condition 定位条件
	 * @return
	 * @date 2015年4月15日 下午4:34:17  
	 * @author dzq
	 */
	MarketingActivityDetail getMarketingActivityDetail(String shopNo,Integer marketingType, Integer productType,Integer condition);

	/**
	 * 查询对应卖家特定营销活动详情
	 * 
	 * @Title: findDetail
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param sellerId
	 *            卖家
	 * @param marketingType
	 *            场景
	 * @param productType
	 *            范围
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月14日 下午4:48:19
	 * @author lys
	 */
	List<MarketingActivityDetail> findDetail(Long sellerId,
			Integer marketingType, Integer productType)
			throws MarketingException;

	/**
	 * 查询对应卖家特定营销活动详情
	 * 
	 * @Title: findDetail
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 *            店铺号
	 * @param marketingType
	 *            场景
	 * @param productType
	 *            范围
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月15日 下午1:43:41
	 * @author lys
	 */
	List<MarketingActivityDetail> findDetail(String shopNo,
			Integer marketingType, Integer productType)
			throws MarketingException;

	/**
	 * 查询营销工具
	 * 
	 * @Title: getMarketingTool
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param sellerId
	 *            卖家
	 * @param mkType
	 *            营销类型
	 * @param pdType
	 *            营销对象
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月14日 下午8:43:45
	 * @author lys
	 */
	MarketingTool getMarketingTool(Long sellerId, Integer mkType, Integer pdType)
			throws MarketingException;
	
	/**
	 * 查询营销工具
	 * @Title: getMarketingTool 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 *            店铺号
	 * @param mkType
	 *            场景
	 * @param pdType
	 *            范围
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月15日 下午1:45:05  
	 * @author lys
	 */
	MarketingTool getMarketingTool(String shopNo, Integer mkType, Integer pdType)
			throws MarketingException;

	/**
	 * 根据营销工具id查询非标准活动列表
	 * @Title: findMarketingActivityByToolId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param toolId
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月16日 下午4:13:05  
	 * @author lys
	 */
	List<MarketingActivity> findMarketingActivityByToolId(Long toolId)
			throws MarketingException;

	/**
	 * 根据营销工具id查询正在使用的非标准活动
	 * @Title: getValidMarketingActivityByToolId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param toolId
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月16日 下午4:12:10  
	 * @author lys
	 */
	MarketingActivity getValidMarketingActivityByToolId(Long toolId)
			throws MarketingException;
	/**
	 * 添加活动详情
	 * @Title: addDetail 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param detail
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月16日 下午4:12:01  
	 * @author lys
	 */
	Long addDetail(MarketingActivityDetail detail) throws MarketingException;

	/**
	 * 编辑活动详情
	 * @Title: editDetail 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param detail
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月16日 下午4:11:47  
	 * @author lys
	 */
	boolean editDetail(MarketingActivityDetail detail) throws MarketingException;
	
	/**
	 * 根据会员金额获取会员折扣信息
	 * @Title: getMemberRule 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo 店铺号
	 * @param amount 金额
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月15日 下午7:11:10  
	 * @author lys
	 */
	MemberRule getMemberRule(String shopNo, Long amount) throws MarketingException;
	
	/**
	 * 根据会员金额获取会员折扣信息
	 * @Title: getMemberRule 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家id
	 * @param amount 金额
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月15日 下午7:11:10  
	 * @author lys
	 */
	MemberRule getMemberRule(long sellerId, Long amount) throws MarketingException;
	
	/**
	 * 查询店铺的会员规则
	 * @Title: findMemberRuleList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月16日 下午4:11:02  
	 * @author lys
	 */
	List<MemberRule> findMemberRuleList(String shopNo) throws MarketingException;
	
	/**
	 * 查询卖家的会员规则
	 * @Title: findMemberRuleList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月16日 下午4:11:23  
	 * @author lys
	 */
	List<MemberRule> findMemberRuleList(Long sellerId) throws MarketingException;
	


	MarketingActivityDetailVo getMarketingActivityDetailVo(String shopNo,Long userId);
	
	/**
	 * 查询充值赠送金额
	 * @Title: getRechargeGiftAmount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家ID
	 * @param chargePrice 充值金额  单位：分
	 * @return  赠送的金额 单位：分
	 * 		  	 若无赠送金额返回0
	 * 		            若有赠送金额返回大于0的整数  
	 * @date 2015年4月15日 下午4:05:25  
	 * @author dzq
	 */
	Long getRechargeGiftAmount(Long sellerId,Long chargePrice);
	
	/**
	 * 获取当前消费送积分活动信息接口(订单生成时调用)
	 * @Title: getconsumptionOfBonusPoints 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo 店铺号
	 * @param userId 买家ID
	 * @param consumPrice
	 * @param sn
	 * @return
	 * @date 2015年4月21日 下午1:51:34  
	 * @author dzq
	 */
	MarketOrderDetailDto getConsumptionOfBonusPoints(String shopNo,Long userId,Long consumPrice);
	
	/**
	 * 生成订单时使用积分抵扣接口(订单生成时调用)
	 * @Title: geteductionPoints 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo	 店铺号
	 * @param userId	用户ID
	 * @param consumPrice 订单消费金额
	 * @return
	 * @date 2015年4月17日 下午4:42:14  
	 * @author dzq
	 */
	MarketOrderDetailDto getDeductionPoints(String shopNo,Long userId,Long consumPrice);
	
	/**
	 * 消费赠送送积分接口(支付成功后调用)
	 * @Title: consumptionOfBonusPoints 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo 店铺号
	 * @param userId 买家ID
	 * @param bonusPoint 赠送积分数
	 * @date 2015年4月15日 上午9:29:42  
	 * @author dzq
	 */
	void consumptionOfBonusPoints(String shopNo,Long userId,Long bonusPoint);
	
	/**
	 * 积分抵扣接口(交易完成后调用)
	 * @Title: deductionPoints 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo  店铺号
	 * @param userId 买家ID
	 * @param integral 抵扣的积分数
	 * @return
	 * @date 2015年4月15日 下午2:45:21  
	 * @author dzq
	 */
	void deductionPoints(String shopNo,Long userId,Long integral);

	
	/**
	 * 查询非标准活动个数
	 * @Title: getActivityCount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param dto
	 * @return
	 * @date 2015年4月16日 下午4:10:38  
	 * @author lys
	 */
	Integer getActivityCount(MarketingActivityDto dto);

	/**
	 * 查询非标准活动列表
	 * @Title: findActivityList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param dto
	 * @return
	 * @date 2015年4月16日 下午4:10:13  
	 * @author lys
	 */
	List<MarketingActivityDto> findActivityList(MarketingActivityDto dto);

	/**
	 * 根据活动id查询活动详情
	 * @Title: findDetailListByActivityId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年4月16日 下午4:09:47  
	 * @author lys
	 */
	List<MarketingActivityDetailDto> findDetailListByActivityId(Long id);

	/**
	 * 添加非标准活动
	 * @Title: addActivity 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param activity
	 * @return
	 * @date 2015年4月16日 下午4:09:33  
	 * @author lys
	 */
	Long addActivity(MarketingActivity activity);

	/**
	 * 编辑指定活动
	 * @Title: editActivity 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param newActivity
	 * @return
	 * @date 2015年4月16日 下午4:09:22  
	 * @author lys
	 */
	boolean editActivity(MarketingActivity newActivity);

	/**
	 * 查询指定活动
	 * @Title: getValidActivityById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param activityId
	 * @return
	 * @date 2015年4月16日 下午4:09:07  
	 * @author lys
	 */
	MarketingActivity getValidActivityById(Long activityId);

	/**
	 * 逻辑删除活动
	 * @Title: removeActivity 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param activityId
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月16日 下午4:08:51  
	 * @author lys
	 */
	boolean removeActivity(Long activityId) throws MarketingException;
	
	/**
	 * 查询正在使用的非标准活动详情
	 * @Title: findNotStandardDetail 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家
	 * @param cj 场景
	 * @param pd 范围
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月16日 下午4:15:00  
	 * @author lys
	 */
	List<MarketingActivityDetailDto> findNotStandardDetail(Long sellerId,
			Integer cj, Integer pd) throws MarketingException;

	/**
	 * 获取卖家的签到规则
	 * 
	 * <p>
	 * 注：<br>
	 * 1.如果签到营销工具开启时，且有此营销活动详情，则返回活动规则<br>
	 * 2.如果营销工具关闭时，或是活动详情没有设置，返回空<br>
	 * @Title: getAttendanceRule 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月17日 上午9:08:16  
	 * @author lys
	 */
	MarketingActivityDetail getAttendanceRule(String shopNo) throws MarketingException;

	/**
	 * 根据营销id获取营销工具
	 * @Title: getMarketingToolById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param toolId 主键
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月17日 下午5:12:32  
	 * @author lys
	 */
	MarketingTool getMarketingToolById(Long toolId) throws MarketingException;
	
	/**
	 * 修改营销工具的状态
	 * @Title: editMarketingRuleStatus 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param toolId
	 * @param status
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月17日 下午5:44:10  
	 * @author lys
	 */
	boolean editMarketingRuleStatus(Long toolId, Integer status) throws MarketingException;

	/**
	 * 根据营销详情id获取营销详情
	 * @Title: getMarketingDetailById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 主键
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月18日 上午11:30:53  
	 * @author lys
	 */
	MarketingActivityDetail getMarketingDetailById(Long id) throws MarketingException;

	/**
	 * 移除活动详情
	 * @Title: removeDetailById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 主键
	 * @param sellerId 卖家
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月18日 上午11:36:16  
	 * @author lys
	 */
	boolean removeDetailById(Long id, Long sellerId) throws MarketingException;
	
	/**
	 * 查询卖家营销活动信息
	 * @Title: getActivityBySellerId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @return
	 * @date 2015年4月21日 上午9:27:59  
	 * @author dzq
	 */
	SellerVo buyActivityInfo(String shopNo,Long sellerId,Long userId)throws MarketingException;
	
	/**
	 * 查询会员活动
	 * @Title: getMemberActivityDiscount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param sellerId 卖家ID
	 * @param consumptionAmout 消费总额（会员活动的条件值）
	 * @return
	 * @date 2015年4月21日 上午10:52:07  
	 * @author dzq
	 */
	Long getMemberActivityDiscount(Long sellerId,Long consumptionAmout);

}
