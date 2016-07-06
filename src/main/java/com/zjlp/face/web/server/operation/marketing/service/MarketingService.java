package com.zjlp.face.web.server.operation.marketing.service;

import java.util.List;

import com.zjlp.face.web.exception.ext.MarketingException;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivity;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingActivityDetail;
import com.zjlp.face.web.server.operation.marketing.domain.MarketingTool;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDetailDto;
import com.zjlp.face.web.server.operation.marketing.domain.dto.MarketingActivityDto;

/**
 * 营销配置基础服务
 * 
 * @ClassName: MaketingService
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年4月13日 下午4:51:14
 */
public interface MarketingService {

	// 添加接口

	/**
	 * 添加营销工具
	 * 
	 * @Title: addTool
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param tool
	 *            营销工具
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月13日 下午4:53:30
	 * @author lys
	 */
	Long addTool(MarketingTool tool) throws MarketingException;

	/**
	 * 添加营销活动配置
	 * 
	 * @Title: addActivity
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param activity
	 *            营销活动配置
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月13日 下午4:55:12
	 * @author lys
	 */
	Long addActivity(MarketingActivity activity) throws MarketingException;

	/**
	 * 添加活动详情
	 * 
	 * @Title: addActivityDetail
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param activityDetail
	 *            活动详情
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月13日 下午4:56:58
	 * @author lys
	 */
	Long addActivityDetail(MarketingActivityDetail activityDetail)
			throws MarketingException;
	
	/**
	 * 添加活动详情列表
	 * @Title: addActivityDetail 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param detailList 活动详情列表
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月14日 下午4:29:35  
	 * @author lys
	 */
	void addActivityDetail(List<MarketingActivityDetail> detailList)
			throws MarketingException;

	// 查询接口
	/**
	 * 查询营销工具
	 * 
	 * @Title: getToolById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            主键
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月13日 下午4:58:59
	 * @author lys
	 */
	MarketingTool getToolById(Long id) throws MarketingException;

	/**
	 * 查询特定的营销工具
	 * 
	 * <p>
	 * 
	 * 1.失效活动则不可查询
	 * 
	 * @Title: getToolByType
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param remoteId
	 *            不能为空：归属id
	 * @param remoteType
	 *            不能为空：归属类型
	 * @param marketingType
	 *            不能为空：营销类型
	 * @param productType
	 *            不能为空：活动主体
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月13日 下午5:06:31
	 * @author lys
	 */
	MarketingTool getToolByType(String remoteId, Integer remoteType,
			Integer marketingType, Integer productType)
			throws MarketingException;
	
	/**
	 * 根据主键查询活动
	 * @Title: getActivityById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 主键
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月13日 下午5:38:57  
	 * @author lys
	 */
	MarketingActivity getActivityById(Long id) throws MarketingException;
	
	/**
	 * 查询正在运行的活动
	 * @Title: getValidActivityByToolId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param toolId 营销活动id
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月13日 下午6:10:49  
	 * @author lys
	 */
	MarketingActivity getValidActivityByToolId(Long toolId) throws MarketingException;
	
	/**
	 * 根据工具id查询活动
	 * @Title: getActivityByToolId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param toolId 营销活动id
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月13日 下午5:41:27  
	 * @author lys
	 */
	List<MarketingActivity> findActivityListByToolId(Long toolId) throws MarketingException;
	
	/**
	 * 查询活动基础配置
	 * @Title: getDetailById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 主键
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月13日 下午5:44:42  
	 * @author lys
	 */
	MarketingActivityDetail getDetailById(Long id) throws MarketingException;
	
	/**
	 * 查询活动基础配置列表
	 * @Title: findListByActivityId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param activityId 活动id
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月13日 下午5:45:06  
	 * @author lys
	 */
	List<MarketingActivityDetailDto> findListByActivityId(Long activityId) throws MarketingException;
	
	/**
	 * 查询活动基础配置列表
	 * @Title: findListByToolId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param toolId 营销工具id
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月13日 下午5:45:29  
	 * @author lys
	 */
	List<MarketingActivityDetail> findListByToolId(Long toolId) throws MarketingException;
	
	//状态修改
	/**
	 * 编辑营销工具的状态<br>
	 * 
	 * 1.关闭->开启<br>
	 * 
	 * 2.开启->关闭<br>
	 * 
	 * @Title: editToolStatus 
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param toolId 营销工具id
	 * @param status 修改状态
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月13日 下午6:25:53  
	 * @author lys
	 */
	boolean editToolStatus(Long toolId, Integer status) throws MarketingException;
	
	/**
	 * 编辑营销活动的状态
	 * 
	 * 1. 注： 已删除的活动的状态将不被修改
	 * 
	 * @Title: editActivityStatusByToolId
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param toolId 营销工具id
	 * @param status 修改状态
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月17日 下午5:29:35  
	 * @author lys
	 */
	boolean editActivityStatusByToolId(Long toolId, Integer status) throws MarketingException;
	
	/**
	 * 编辑活动详情的状态
	 * 
	 * 1. 注： 已删除的活动的状态将不被修改
	 * 
	 * @Title: editDetailStatusByToolId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param toolId 营销工具id
	 * @param status 修改状态
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月17日 下午5:31:28  
	 * @author lys
	 */
	boolean editDetailStatusByToolId(Long toolId, Integer status) throws MarketingException;
	
	/**
	 * 删除活动
	 * @Title: delActivity 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param activityId 活动id
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月14日 上午9:56:49  
	 * @author lys
	 */
	boolean delActivity(Long activityId) throws MarketingException;
	
	/**
	 * 删除活动详情
	 * @Title: delActivityDetail 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 主键
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月14日 上午9:01:49  
	 * @author lys
	 */
	boolean delActivityDetail(Long id) throws MarketingException;

	//内容修改
	/**
	 * 修改活动详情
	 * @Title: editDetail 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param detail 活动详情
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月14日 下午7:53:31  
	 * @author lys
	 */
	boolean editDetail(MarketingActivityDetail detail) throws MarketingException;

	List<MarketingActivityDto> findActivityPage(MarketingActivityDto dto) throws MarketingException;

	Integer getActivityCount(MarketingActivityDto dto) throws MarketingException;

	/**
	 * 编辑活动
	 * @Title: editActivity 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param newActivity
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月16日 下午3:09:18  
	 * @author lys
	 */
	boolean editActivity(MarketingActivity newActivity) throws MarketingException;

	/**
	 * 移除指定非标准活动的活动详情
	 * @Title: delActivityDetailByActivityId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param activityId
	 * @return
	 * @throws MarketingException
	 * @date 2015年4月16日 下午3:08:43  
	 * @author lys
	 */
	boolean delActivityDetailByActivityId(Long activityId) throws MarketingException;
	
}
