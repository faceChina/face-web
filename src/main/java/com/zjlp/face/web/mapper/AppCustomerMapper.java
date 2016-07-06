package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.user.customer.domain.AppCustomer;
import com.zjlp.face.web.server.user.customer.domain.vo.MyCustomerVo;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月10日 下午5:50:30
 *
 */
public interface AppCustomerMapper {

	/**
	 * 新增客户
	 * @param record
	 * @return
	 */
	int insert(AppCustomer record);

	/**
	 * 删除客户关系
	 * @param id
	 * @return
	 */
	void deleteByPrimaryKey(Long id);

	/**
	 * 更细客户信息
	 * @param record
	 * @return
	 */
	void updateByPrimaryKey(AppCustomer record);

	/**
	 * 根据用户ID查找客户
	 * 
	 * @param 用户ID
	 * @return
	 */
	List<AppCustomer> selectByUserId(Long userId);

	/**
	 * 根据用户ID和客户ID查找唯一客户
	 * 
	 * @param 用户ID
	 *            ，客户ID
	 * @return
	 */
	AppCustomer selectByUserIdAndCutsomerId(Map<String, Object> map);

	/**
	 * 根据主键和用户ID查找客户是否存在并且时不时我的客户
	 * 
	 * @param ID
	 *            ，客户ID
	 * @return
	 */
	AppCustomer selectByPrimarykeyAndUserId(Map<String, Object> map);

	/**
	 * 我的客户列表页数
	 * 
	 * @param map
	 * @return
	 */
	Integer selectPageCount(Map<String, Object> map);
	/**
	 * 我的客户列表
	 * @param userId
	 * @return
	 */
	List<MyCustomerVo> selectMyCustomerById(Map<String, Object> map);

	/**
	 * 列当前分组下的客户，返回结果中ID实为关系表主键
	 * 
	 * @param userId
	 * @return
	 */
	List<AppCustomer> selectCurrentGroupCustomers(Map<String, Object> map);

	/**
	 * 统计供货商下的员工代理订单信息
	 * 
	 * @param map
	 * @return
	 */
	List<MyCustomerVo> selectStaffCount(Map<String, Object> map);

	/**
	 * 统计供货商下的员工代理订单信息 数据统计
	 * 
	 * @param map
	 * @return
	 */
	Integer getStaffTotalRows(Map<String, Object> map);

	/**
	 * @Title: selectUngroupCustomer
	 * @Description: (通过类型和userId查找我的客户)
	 * @param map
	 * @return
	 * @return List<AppCustomer> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月14日 上午9:23:13
	 */
	List<AppCustomer> selectUngroupCustomer(Map<String, Object> map);

	/**
	 * @Title: findAppCustomerById
	 * @Description: (通过主键查找客户)
	 * @param id
	 * @return
	 * @return AppCustomer 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月20日 下午12:04:42
	 */
	AppCustomer findAppCustomerById(Long id);

	/**
	 * @Title: countMySubbranchOrder
	 * @Description: (分页统计客户在我的分店、历史分下订单-统计)
	 * @param map
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月25日 下午7:43:33
	 */
	Integer countMySubbranchOrder(Map<String, Object> map);

	/**
	 * @Title: selectMySubbranchOrder
	 * @Description: (分页统计客户在我的分店、历史分下订单-分页)
	 * @param map
	 * @return
	 * @return List<AppCustomer> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月25日 下午7:44:07
	 */
	List<MyCustomerVo> selectMySubbranchOrder(Map<String, Object> map);

	/**
	 * @Title: selectMyShopOrder
	 * @Description: (统计客户在我的总店下订单)
	 * @param map
	 * @return
	 * @return AppCustomer 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月25日 下午7:44:58
	 */
	MyCustomerVo selectMyShopOrder(Map<String, Object> map);

	/**
	 * @Title: countAllMyShopOrder
	 * @Description: (统计客户在我的所有分店、历史分下订单数和总金额)
	 * @param map
	 * @return
	 * @return MyCustomerVo 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月26日 上午10:12:36
	 */
	MyCustomerVo countAllMyShopOrder(Map<String, Object> map);

}
