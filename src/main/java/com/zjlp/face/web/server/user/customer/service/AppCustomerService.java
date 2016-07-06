package com.zjlp.face.web.server.user.customer.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.AppCustomerException;
import com.zjlp.face.web.server.user.customer.domain.AppCustomer;
import com.zjlp.face.web.server.user.customer.domain.vo.MyCustomerVo;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月11日 上午8:57:37
 *
 */
public interface AppCustomerService {

	/**
	 * @Title: addAppCustomer
	 * @Description: (添加一个客户)
	 * @param appCustomer
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 上午8:57:51
	 */
	void addAppCustomer(AppCustomer appCustomer);

	/**
	 * @Title: editAppCustomer
	 * @Description: (编辑一个客户)
	 * @param appCustomer
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 上午8:58:12
	 */
	void editAppCustomer(AppCustomer appCustomer);

	/**
	 * @Title: findAppCustomerList
	 * @Description: (根据用户ID查询客户列表)
	 * @param userId
	 * @return
	 * @throws AppCustomerException
	 * @return List<AppCustomer> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 上午8:58:24
	 */
	List<AppCustomer> findAppCustomerList(Long userId) throws AppCustomerException;

	/**
	 * @Title: findAppCustomer
	 * @Description: (根据客户ID和用户ID查询客户)
	 * @param userId
	 * @param customerId
	 * @return
	 * @throws AppCustomerException
	 * @return AppCustomer 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 上午8:58:33
	 */
	AppCustomer findAppCustomer(Long userId, Long customerId) throws AppCustomerException;

	/**
	 * @Title: findMyAppCustomer
	 * @Description: (根据ID和用户ID查询客户)
	 * @param id
	 * @param userId
	 * @param type
	 * @return
	 * @throws AppCustomerException
	 * @return AppCustomer 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 上午8:58:46
	 */
	AppCustomer findMyAppCustomer(Long id, Long userId, Integer type) throws AppCustomerException;

	/**
	 * @Title: removeAppCustomer
	 * @Description: (删除客户)
	 * @param appCustomerId
	 * @throws AppCustomerException
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 上午8:58:56
	 */
	void removeAppCustomer(Long appCustomerId) throws AppCustomerException;

	/**
	 * @Title: getMyCustomerById
	 * @Description: (获取我的客户以及相关订单)
	 * @param shopType
	 * @param userId
	 * @param orderBy
	 * @param pagination
	 * @return
	 * @throws Exception
	 * @return Pagination<MyCustomerVo> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 上午8:59:06
	 */
	Pagination<MyCustomerVo> getMyCustomerById(Integer shoptype,Long userId, String orderBy, String customerName, Pagination<MyCustomerVo> pagination)
			throws Exception;

	/**
	 * @Title: findCurrentGroupCustomer
	 * @Description: (获取当前分组下的客户列表)
	 * @param appcustomer
	 * @return
	 * @return List<AppCustomer> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 上午8:59:16
	 */
	List<AppCustomer> findCurrentGroupCustomer(AppCustomer appcustomer);

	/**
	 * @Title: findStaffCount
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 * @param orderBy
	 * @param pagination
	 * @return
	 * @throws Exception
	 * @return Pagination<MyCustomerVo> 返回类型
	 * @throws
	 * @date 2015年8月11日 上午8:59:28
	 */
	Pagination<MyCustomerVo> findStaffCount(String shopNo, String orderBy, Pagination<MyCustomerVo> pagination) throws Exception;

	/**
	 * @Title: findUngroupCustomer
	 * @Description: (查找未分组客户客户)
	 * @param userId
	 * @param type
	 * @return
	 * @return List<AppCustomer> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月14日 上午9:20:14
	 */
	List<AppCustomer> findUngroupCustomer(Long userId, Integer type);

	/**
	 * @Title: findAppCustomerById
	 * @Description: (通过主键查找客户)
	 * @param id
	 * @return
	 * @return List<AppCustomer> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月20日 上午11:59:26
	 */
	AppCustomer findAppCustomerById(Long id);
	
	/**
	 * @Title: findMySubbranchOrder
	 * @Description: (分页统计客户在我的分店、历史分下订单)
	 * @param userId
	 * @param customerId
	 * @param pagination
	 * @return
	 * @throws Exception
	 * @return Pagination<MyCustomerVo> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月25日 下午8:12:22
	 */
	Pagination<MyCustomerVo> findMySubbranchOrder(Long userId, Long customerId, Pagination<MyCustomerVo> pagination) throws Exception;

	/**
	 * @Title: findMyShopOrder
	 * @Description: (统计客户在我的总店下订单)
	 * @param userId
	 * @param customerId
	 * @return
	 * @return MyCustomerVo 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月25日 下午8:08:46
	 */
	MyCustomerVo findMyShopOrder(Long userId, Long customerId);

	/**
	 * @Title: countAllMyShopOrder
	 * @Description: (统计客户在我的所有分店、历史分下订单数和总金额)
	 * @param userId
	 * @param customerId
	 * @return
	 * @return MyCustomerVo 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月26日 上午10:14:19
	 */
	MyCustomerVo countAllMyShopOrder(Long userId, Long customerId);
}
