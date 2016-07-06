package com.zjlp.face.web.server.trade.order.bussiness;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.OrderException;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.vo.SubBranchSalesOrderVo;

public interface SubBranchSaleOrderBusiness {
	
	/*******************************
	 * @Title findSubBranchOrderPageByShopNoAndStatusForOwn
	 * @Description (根据店铺编号分页列出本店铺订单)
	 * @param shopNo
	 * @param status (订单状态 ，可null当值为null时表示所有订单)
	 * @param orderBy (排序参数，可null 当值为null 默认ORDER BY UPDATE_TIME)
	 * @param pagination
	 * @return
	 * @throws OrderException
	 * @Return Pagination<SubBranchSalesOrderVo>
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	Pagination<SubBranchSalesOrderVo> findSubBranchOrderPageByShopNoAndStatusForOwn(String shopNo,Integer status,String orderBy,Pagination<SubBranchSalesOrderVo> pagination) throws OrderException;
	/*******************************
	 * @Title findSubBranchOrderPageByPhoneAndStatusForOwn
	 * @Description (根据用户手机分页列出本店铺订单)
	 * @param shopNo
	 * @param status (订单状态 ，可null当值为null时表示所有订单)
	 * @param orderBy (排序参数，可null当值为null 默认ORDER BY UPDATE_TIME)
	 * @param pagination
	 * @return
	 * @throws OrderException
	 * @Return Pagination<SubBranchSalesOrderVo>
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	Pagination<SubBranchSalesOrderVo> findSubBranchOrderPageBySubbranchIdAndStatusForOwn(Long subbranchId,Integer status,String orderBy,Pagination<SubBranchSalesOrderVo> pagination,Long userId,String isShopRequest) throws OrderException;
	
	Pagination<SubBranchSalesOrderVo> findSubBranchOrderPageBySubbranchIdAndStatusForOwn(Long subbranchId,Integer status,String orderBy,String searchKey,Pagination<SubBranchSalesOrderVo> pagination,Long userId,String isShopRequest) throws OrderException;
	
	/*******************************
	 * @Title findSubBranchOrderPageByShopNoAndStatusForSub
	 * @Description (根据店铺编号分页列出分店铺订单)
	 * @param shopNo
	 * @param status (订单状态 ，可null当值为null时表示所有订单)
	 * @param orderBy (排序参数，可null 当值为null 默认ORDER BY UPDATE_TIME)
	 * @param pagination
	 * @return
	 * @throws OrderException
	 * @Return Pagination<SubBranchSalesOrderVo>
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	Pagination<SubBranchSalesOrderVo> findSubBranchOrderPageByShopNoAndStatusForSub(String shopNo,Integer status,String orderBy,Pagination<SubBranchSalesOrderVo> pagination) throws OrderException;
	/*******************************
	 * @Title findSubBranchOrderPageByPhoneAndStatusForSub
	 * @Description (根据用户手机分页列出分店铺订单)
	 * @param phone
	 * @param status (订单状态 ，可null当值为null时表示所有订单)
	 * @param orderBy (排序参数，可null 当值为null 默认ORDER BY UPDATE_TIME)
	 * @param pagination
	 * @return
	 * @throws OrderException
	 * @Return Pagination<SubBranchSalesOrderVo>
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	Pagination<SubBranchSalesOrderVo> findSubBranchOrderPageBySubbranchIdAndStatusForSub(Long subbranchId,Integer status,String orderBy,Pagination<SubBranchSalesOrderVo> pagination) throws OrderException;
	Pagination<SubBranchSalesOrderVo> findSubBranchOrderPageBySubbranchIdAndStatusForSub(Long subbranchId,Integer status,String orderBy,String searchKey,Pagination<SubBranchSalesOrderVo> pagination) throws OrderException;
	/*******************************
	 * @Title deliveryOrder
	 * @Description (发货并判断对应店铺编号是否有权限)
	 * @param salesOrder
	 * @param shopNo (当店铺无权发货时会抛出 Exception)
	 * @param userId
	 * @throws OrderException
	 * @Return void
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	void deliveryOrder(SalesOrder salesOrder,Long userId) throws OrderException;
	/*******************************
	 * @Title cancleOrder
	 * @Description (取消订单)
	 * @param orderNo
	 * @param refuseReason
	 * @param shopNo
	 * @param userId
	 * @throws OrderException
	 * @Return void
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	void cancleOrder(String orderNo, String refuseReason,Long userId) throws OrderException;
	/*******************************
	 * @Title modifyOrderPrice
	 * @Description (价格修改，让利价格不高于佣金)
	 * @param orderNo
	 * @param benefitPrice
	 * @param phone
	 * @param userId
	 * @return
	 * @throws OrderException
	 * @Return Boolean
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	void modifyOrderPrice(String orderNo,Long benefitPrice,Long userId)throws OrderException;
	/*******************************
	 * @Title getSubBranchSalesOrderWithPurchaseOrderItemsByPrimaryKey
	 * @Description (根据主键获取订单及采购单明细列表)
	 * @param orderNo
	 * @return
	 * @throws OrderException
	 * @Return SubBranchSalesOrderVo
	 * @date 2015年6月25日
	 * @author Xilei Huang
	 *******************************/
	SubBranchSalesOrderVo getSubBranchSalesOrderWithPurchaseOrderItemsByPrimaryKey(String orderNo,Long userId) throws OrderException;
	
	
	/**
	 * 查询下级分店的订单详情
	 * 
	 * @Title getSubOrderDetail
	 * @Description (查询下级分店的订单详情)
	 * @param orderNo 订单号
	 * @param userId 用户id
	 * @return
	 * @throws OrderException
	 * @Return SubBranchSalesOrderVo
	 * @date 2015年6月25日
	 * @author lys
	 **/
	@Deprecated
	SubBranchSalesOrderVo getSubOrderDetail(String orderNo, Long userId) throws OrderException;
	
	/*******************************
	 * @Title countSubBranchSalesOrderCountForProducer
	 * @Description (统计总店下今日付款订单数)
	 * @param shopNo
	 * @return
	 * @Return Integer
	 * @date 2015年6月26日
	 * @author Xilei Huang
	 *******************************/
	Integer countSubBranchSalesOrderCountForProducer(String shopNo);
	/*******************************
	 * @Title countSubBranchSalesOrderCountForDistributor
	 * @Description (统计分店下今日付款订单数)
	 * @param shopNo 手机号
	 * @return
	 * @Return Integer
	 * @date 2015年6月26日
	 * @author Xilei Huang
	 *******************************/
	Integer countSubBranchSalesOrderCountForDistributor(String shopNo);
	/*******************************
	 * @Title getSubBranchSalesOrderTotalPrice
	 * @Description (统计总店下今日付款订单金额)
	 * @param shopNo 
	 * @return
	 * @Return Long
	 * @date 2015年6月26日
	 * @author Xilei Huang
	 *******************************/
	Long getSubBranchSalesOrderTotalPrice(String shopNo);
	/*******************************
	 * @Title getSubBranchSalesOrderUnFreezeTotalProfitPrice
	 * @Description (统计分店下今日解冻佣金)
	 * @param userId 用户id
	 * @return
	 * @Return Long
	 * @date 2015年6月26日
	 * @author Xilei Huang
	 *******************************/
	Long getSubBranchSalesOrderUnFreezeTotalProfitPrice(Long userId);
	
	/*******************************
	 * @Title findAllMySalesOrderByShopNo
	 * @Description (查找本店所有订单 包括非分销单)
	 * @param salesOrder
	 * @param orderBy
	 * @param pagination
	 * @return
	 * @throws OrderException
	 * @Return Pagination<SubBranchSalesOrderVo>
	 * @date 2015年7月2日
	 * @author Xilei Huang
	 * @param isShopRequest 
	 *******************************/
	Pagination<SubBranchSalesOrderVo> findAllMySalesOrderByShopNo(SalesOrder salesOrder,String orderBy,Pagination<SubBranchSalesOrderVo> pagination,Long userId, String isShopRequest) throws OrderException;
	/*******************************
	 * @Title getSupplierSubBranchSalesOrderByOrderNo
	 * @Description (获取总店供应单详情)
	 * @param orderNo
	 * @return
	 * @throws OrderException
	 * @Return SubBranchSalesOrderVo
	 * @date 2015年7月2日
	 * @author Xilei Huang
	 *******************************/
	SubBranchSalesOrderVo getSupplierSubBranchSalesOrderByOrderNo(String orderNo) throws OrderException;
	
	/**
	 * 统计分店订单个数（自卖的 + 下级分店产生）
	 * 
	 * @Title: getSubbranchOrderCount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param subbrachId 关系列表
	 * @return
	 * @throws OrderException
	 * @date 2015年7月9日 上午10:44:20  
	 * @author lys
	 */
	Integer getSubbranchOrderTDPCount(Long subbrachId) throws OrderException;
	
	/**
	 * 统计分店订单个数（自卖的 + 下级分店产生）
	 * 
	 * @Title: getSubbranchOrderCount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param cell 手机号码
	 * @return
	 * @throws OrderException
	 * @date 2015年7月9日 上午10:45:51  
	 * @author lys
	 */
	Integer getSubbranchOrderTDPCount(String cell) throws OrderException;
	/**
	 * 
	* @Title: findHistoryOrderForOwn
	* @Description: (查询历史分店的店铺订单)
	* @param subBranchId
	* @param status
	* @param orderBy
	* @param pagination
	* @return
	* @throws OrderException
	* @return Pagination<SubBranchSalesOrderVo>    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月14日 上午11:07:39
	 */
	Pagination<SubBranchSalesOrderVo> findHistoryOrderForOwn(Long subBranchId,Integer status,Long customerId,String orderBy,Pagination<SubBranchSalesOrderVo> pagination, String isShopRequest) throws OrderException;
	
	/**
	 * 
	* @Title: findHistoryOrderForSub
	* @Description: (查询历史分店的分店订单)
	* @param subBranchId
	* @param status
	* @param orderBy
	* @param pagination
	* @return
	* @throws OrderException
	* @return Pagination<SubBranchSalesOrderVo>    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月14日 上午11:08:20
	 */
	Pagination<SubBranchSalesOrderVo> findHistoryOrderForSub(Long subBranchId,Integer status,String orderBy,Pagination<SubBranchSalesOrderVo> pagination) throws OrderException;

	
	
}
