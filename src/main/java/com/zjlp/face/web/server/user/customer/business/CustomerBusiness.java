package com.zjlp.face.web.server.user.customer.business;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.AppCustomerException;
import com.zjlp.face.web.server.user.customer.domain.AppCustomer;
import com.zjlp.face.web.server.user.customer.domain.AppGroup;
import com.zjlp.face.web.server.user.customer.domain.dto.CustomerDetailDto;
import com.zjlp.face.web.server.user.customer.domain.vo.AppCustomerVo;
import com.zjlp.face.web.server.user.customer.domain.vo.MyCustomerVo;


/**
 * 好友、客户分组、标签
 * 
 * @author Baojiang Yang
 * @date 2015年8月10日 下午5:57:36
 *
 */
public interface CustomerBusiness {

	/**
	 * @Description: 获取我的客户 以及交易信息
	 * @return 客户以及交易信息列表
	 * @param shopType 查找店铺类型，1总店客户，2分店客户
	 * @date 2015年4月17日 上午10:07:41
	 * @author ybj
	 */
	Pagination<MyCustomerVo> getMyCustomerByUserId(Integer shopType, Long userId, String customerName, String orderBy, Pagination<MyCustomerVo> pagination) throws AppCustomerException;
	/**
	 * @Description: 获取我的客户 详情
	 * @return 客户详情
	 * @date 2015年4月17日 上午10:07:41
	 * @author ybj
	 */
	CustomerDetailDto getCustomerDetailByIds(Long userId, Long customerId) throws AppCustomerException;

	/**
	 * @Description: 获取我的客户分组列表
	 * @return 分组列表
	 * @date 2015年4月17日 上午10:07:41
	 * @author ybj
	 */
	List<AppGroup> getMyCustomerGroupList(Long userId, Integer type) throws AppCustomerException;

	/**
	 * 查找属于我的客户
	 * 
	 * @param appCustomer
	 * @return
	 * @throws AppCustomerException
	 */
	AppCustomer getMyAppCustomer(AppCustomer appCustomer) throws AppCustomerException;

	/**
	 * 验证我的客户是否存在
	 * 
	 * @param appCustomer
	 * @return
	 * @throws AppCustomerException
	 */
	AppCustomer getMyAppCustomer(Long id, Long userId, Integer type) throws AppCustomerException;

	/**
	 * 通过分组主键获取分组下的客户信息
	 * 
	 * @param userId
	 * @return
	 */
	List<AppCustomer> getCurrentGroupCustomer(AppCustomer appcustomer) throws AppCustomerException;

	/**
	 * 一个客户新增到多个（或者一个）分组，新分组可选
	 * 
	 * @param userId
	 * @param customerId
	 * @param goalGroupIds
	 * @param groupName
	 * @throws AppCustomerException
	 */
	Long addCustomerToGroups(Long userId, Long customerId, List<Long> goalGroupIds, String groupName, Integer type) throws AppCustomerException;

	/**
	 * 多个（或者一个）客户分到多一个分组，新分组必选
	 * 
	 * @param userId
	 * @param goalCustomerIds
	 * @param goalGroupId
	 * @param groupName
	 * @throws AppCustomerException
	 */
	Long addCustomersToGroup(Long userId, List<Long> goalCustomerIds, Long goalGroupId, String groupName, Integer type)
			throws AppCustomerException;

	/**
	 * 移除用户分组下客户
	 * @param deletedGroupIds
	 * @throws AppCustomerException
	 */
	void removeCurrentGroupCustomer(List<Long> deletedGroupIds) throws AppCustomerException;

	/**
	 * 在当前分组下新增客户
	 * 
	 * @param deletedGroupIds
	 * @throws AppCustomerException
	 */
	Long addCurrentGroupCustomer(Long userId, List<Long> originalCustomerIds, Long groupId, List<Long> addCustomerIds,
			Integer type) throws AppCustomerException;

	/**
	 * 移除当前分组
	 * 
	 * @param rollBackCustomerIds
	 * @param groupId
	 * @throws AppCustomerException
	 */
	void removeCustomerGroup(List<Long> rollBackCustomerIds, Long groupId, Integer type) throws AppCustomerException;

	/**
	 * 统计供货商下的代理商统计信息
	 * 
	 * @param shopNo
	 * @return
	 */
	Pagination<MyCustomerVo> findStaffCount(String shopNo, String orderBy, Pagination<MyCustomerVo> pagination)
			throws AppCustomerException;

	/**
	 * @Title: updateAppCustomer
	 * @Description: (更新客户信息)
	 * @param appCustomer
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月28日 下午9:13:36
	 */
	void updateAppCustomer(AppCustomer appCustomer) throws AppCustomerException;;

	/**
	 * @Title: updateAppGroup
	 * @Description: (更新分组)
	 * @param appGroup
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 下午3:33:10
	 */
	Long updateAppGroup(AppGroup appGroup) throws AppCustomerException;

	/**
	 * @Title: updateAppGroupSort
	 * @Description: (更新分组权重)
	 * @param sortList
	 * @param userId
	 * @param type
	 * @return
	 * @throws AppCustomerException
	 * @return Long 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月12日 上午9:49:35
	 */
	Long updateAppGroupSort(Object[] sortList, Long userId, Integer type) throws AppCustomerException;

	/**
	 * @Title: addAppGroup
	 * @Description: (简单添加一个分组)
	 * @param appGroup
	 * @return
	 * @throws AppCustomerException
	 * @return Long 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月13日 上午11:58:15
	 */
	Long addAppGroup(AppGroup appGroup) throws AppCustomerException;

	/**
	 * @Title: getUngroups
	 * @Description: (查找未命名分组)
	 * @param userId
	 * @param type
	 * @return
	 * @throws AppCustomerException
	 * @return List<AppGroup> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月27日 下午4:36:04
	 */
	List<AppGroup> getUngroups(Long userId, Integer type) throws AppCustomerException;

	/**
	 * @Title: findUngroupCustomer
	 * @Description: (查找未分组客户)
	 * @param userId
	 * @param type
	 * @return
	 * @throws AppCustomerException
	 * @return List<AppCustomerVo> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月14日 上午8:44:22
	 */
	List<AppCustomerVo> findUngroupCustomer(Long userId, Integer type) throws AppCustomerException;
	
	/**
	* @Title: findShopAndSubOrder
	* @Description: (页统计客户在我的总店、分店、历史分下订单)
	* @param userId
	* @param customerId
	* @param pagination
	* @return
	* @throws AppCustomerException
	* @return Pagination<MyCustomerVo>    返回类型
	* @throws
	* @author Baojiang Yang  
	* @date 2015年8月25日 下午8:19:00 
	*/
	Pagination<MyCustomerVo> findShopAndSubOrder(Long userId, Long customerId, Pagination<MyCustomerVo> pagination) throws AppCustomerException;

	/**
	 * @Title: countAllMyShopOrder
	 * @Description: (统计客户在我的所有分店、历史分下订单数和总金额)
	 * @param userId
	 * @param customerId
	 * @return
	 * @throws AppCustomerException
	 * @return MyCustomerVo 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月26日 上午10:18:23
	 */
	MyCustomerVo countAllMyShopOrder(Long userId, Long customerId) throws AppCustomerException;

	/**
	 * @Title: removeUngroups
	 * @Description: (删除用户下所有未分组)
	 * @param userId
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月27日 下午5:08:38
	 */
	void removeUngroups(Long userId);
}
