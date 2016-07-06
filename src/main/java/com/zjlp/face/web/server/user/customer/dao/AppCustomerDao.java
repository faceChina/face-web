package com.zjlp.face.web.server.user.customer.dao;

import java.util.List;

import com.zjlp.face.web.server.user.customer.domain.AppCustomer;
import com.zjlp.face.web.server.user.customer.domain.vo.MyCustomerVo;

/**
* 
* @author Baojiang Yang
* @date 2015年8月11日 下午5:00:22
*
*/ 
public interface AppCustomerDao {

	Long add(AppCustomer appCustomer);

	void removeById(Long id);

	void edit(AppCustomer appCustomer);

	List<AppCustomer> getByUserId(Long userId);

	AppCustomer getByUserIdAndCustomerId(Long userId, Long customerId);

	AppCustomer getByIdAndUserId(Long id, Long userId, Integer type);

	Integer getPageCount(Integer shoptype, Long userId, String customerName);

	List<MyCustomerVo> getMyCustomerById(Integer shoptype, Long userId, String orderBy, int start, int pageSize, String customerName);

	List<AppCustomer> fingCurrentGroupCustomer(AppCustomer appCustomer);

	List<MyCustomerVo> findStaffCount(String shopNo, String orderBy, int start, int pageSize);

	Integer getStaffTotalRows(String shopNo);

	List<AppCustomer> selectUngroupCustomer(Long userId, Integer type);

	AppCustomer findAppCustomerById(Long id);
	
	/**
	* @Title: countMySubbranchOrder
	* @Description: (分页统计客户在我的分店、历史分下订单-统计)
	* @param customerId
	* @param userId
	* @return
	* @return Integer    返回类型
	* @throws
	* @author Baojiang Yang  
	* @date 2015年8月25日 下午8:06:31 
	*/
	Integer countMySubbranchOrder(Long userId, Long customerId);

	/**
	 * @Title: selectMySubbranchOrder
	 * @Description: (分页统计客户在我的分店、历史分下订单-分页)
	 * @param userId
	 * @param customerId
	 * @param start
	 * @param pageSize
	 * @return
	 * @return List<MyCustomerVo> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月25日 下午8:08:18
	 */
	List<MyCustomerVo> selectMySubbranchOrder(Long userId, Long customerId, int start, int pageSize);
	
	/**
	 * @Title: selectMyShopOrder
	 * @Description: (统计客户在我的总店下订单)
	 * @param userId
	 * @param customerId
	 * @return
	 * @return MyCustomerVo 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月25日 下午8:08:46
	 */
	MyCustomerVo selectMyShopOrder(Long userId, Long customerId);

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
