package com.zjlp.face.web.server.trade.order.bussiness;

import java.util.List;
import java.util.Map;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.OrderException;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.vo.GoodSkuVo;
import com.zjlp.face.web.server.trade.cart.domain.OrderBuyVo;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderDto;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;
import com.zjlp.face.web.server.trade.order.domain.vo.OrdersCountDto;

public interface SalesOrderBusiness {

	/**
	 * @Description: 添加订单
	 * @param orderBuyVo
	 * @date: 2015年3月18日 上午10:07:23
	 * @author: zyl
	 */
	@Deprecated
	List<String> addSalesOrder(OrderBuyVo orderBuyVo);
	
	List<String> add(OrderBuyVo orderBuyVo);

	/**
	 * @Description: 描述
	 * @param orderNo
	 * @return
	 * @date: 2015年3月18日 上午10:07:29
	 * @author: zyl
	 */
	SalesOrder getSalesOrderByOrderNo(String orderNo);

	/**
	 * 修改订单状态接口
	 * 
	 * @param orderNo
	 *            订单序列号
	 * @param userId
	 *            操作人id
	 * @param status
	 *            要修改为的状态
	 * @param memo
	 *            商家拒绝预约订单原因
	 * @author lhh
	 */
	void updateOrderStatus(String orderNo, Long userId, Integer status,
			String memo);

	/**
	 * @Description: 订单关闭
	 * @param orderNo
	 * @date: 2015年3月18日 上午10:07:33
	 * @author: zyl
	 */
	void closeOrder(String orderNo);

	/**
	 * @Description: 自动收货(已收货状态，定时任务可调用)
	 * @param orderNo
	 * @param userId
	 * @date: 2015年10月13日 下午15:17:30
	 * @author: talo
	 */
	void receiveAutoOrder (String orderNo, Long userId);
	
	/**
	 * @Description: 自动收货(交易成功状态，定时任务可调用)
	 * @param orderNo
	 * @param userId
	 * @date: 2015年3月18日 上午10:07:37
	 * @author: zyl
	 */
	void compileAutoOrder(String orderNo,Long userId);

	/**
	 * @Description: 删除订单
	 * @param orderNo
	 * @param userId
	 *            TODO
	 * @date: 2015年3月18日 上午10:07:41
	 * @author: zyl
	 */
	void deleteOrder(String orderNo, Long userId);

	/**
	 * @Description: 取消订单
	 * @param orderNo
	 * @param userId
	 *            TODO
	 * @date: 2015年3月18日 上午10:07:45
	 * @author: zyl
	 */
	void cancleOrder(String orderNo, Long userId);

	/**
	 * @Description: 确认收货(手动收货,已收货状态)
	 * @param orderNo
	 * @param userId
	 *            TODO
	 * @date: 2015年3月18日 上午10:07:50
	 * @author: zyl
	 */
	void receiveOrder(String orderNo, Long userId);

	/**
	 * @Description: 买家查询订单
	 * @param pagination
	 * @return
	 * @date: 2015年3月18日 上午10:07:58
	 * @author: zyl
	 */
	Pagination<SalesOrderDto> findOrderPageForWap(SalesOrderVo salesOrderVo,
			Pagination<SalesOrderDto> pagination);

	/**
	 * @Description: 查询订单及订单细项
	 * @param orderNo
	 * @return
	 * @date: 2015年3月24日 下午7:55:45
	 * @author: zyl
	 */
	SalesOrderDto getSalesOrderDetailByOrderNo(String orderNo);

	/**
	 * @Description: 订单号查询订单
	 * @param orderNoList
	 * @return
	 * @date: 2015年3月25日 下午7:14:48
	 * @author: zyl
	 */
	List<SalesOrder> findSalesOrderListByOrderNoList(List<String> orderNoList);

	/**
	 * @Description: 今日完成订单
	 * @param shopNo
	 * @return
	 * @date: 2015年3月27日 上午11:59:47
	 * @author: zyl
	 */
	Integer countTodayCompile(String shopNo);

	/**
	 * @Description: 今日所有订单
	 * @param shopNo
	 * @return
	 * @date: 2015年3月27日 上午11:59:47
	 * @author: zyl
	 */
	Integer countTodayAll(String shopNo);

	/**
	 * @Description: 待付款
	 * @param shopNo
	 * @return
	 * @date: 2015年3月27日 下午12:00:04
	 * @author: zyl
	 */
	Integer countWait(String shopNo);

	/**
	 * @Description: 已付款
	 * @param shopNo
	 * @return
	 * @date: 2015年3月27日 下午12:00:15
	 * @author: zyl
	 */
	Integer countPay(String shopNo);

	/**
	 * 
	 * @Title: findSalesOrderDetailPage 
	 * @Description: 分页查询订单(如果不传orderCategory则会把普通订单和代理订单都查出来) 
	 * @param pagination
	 * @param salesOrderVo
	 * @return
	 * @date 2015年8月18日 下午3:28:11  
	 * @author cbc
	 */
	Pagination<SalesOrderDto> findSalesOrderDetailPage(
			Pagination<SalesOrderDto> pagination, SalesOrderVo salesOrderVo);

	Pagination<SalesOrderDto> findOrderPage(
			Pagination<SalesOrderDto> pagination, SalesOrderVo salesOrderVo);

	/**
	 * @Description: 验证店铺订单
	 * @param orderNo
	 * @param shopNo
	 * @date: 2015年3月27日 下午7:47:42
	 * @author: zyl
	 */
	void validateOrderByShopNo(String orderNo, String shopNo);

	/**
	 * @Description: 验证用户订单
	 * @param orderNo
	 * @param userId
	 * @date: 2015年3月31日 下午7:35:15
	 * @author: zyl
	 */
	void validateOrderByUserId(String orderNo, Long userId);

	void adjustOrderPrice(String orderNo, Long userId, Long adjustPriceYuan);

	/**
	 * @Description: 付款完成之后,处理订单:1.协议商品处理;2.发送短信通知
	 * @param orderNoList
	 * @date: 2015年3月28日 下午4:48:40
	 * @author: zyl
	 * @throws Exception
	 */
	void dealOrderAfterPay(List<String> orderNoList) throws OrderException;
	
	/**
	 * @Description: 付款完成之后,处理订单:发送短信通知
	 * @param orderList
	 * @date: 2015年9月23日 下午4:18:40
	 * @author: zyl
	 * @throws Exception
	 */
	void dealOrderAfterPayV2(List<SalesOrder> orderList) throws OrderException;

	/**
	 * @Description: 发货
	 * @param salesOrder
	 * @param userId
	 *            TODO
	 * @date: 2015年3月30日 下午4:01:44
	 * @author: zyl
	 */
	void deliveryOrder(SalesOrder salesOrder, Long userId);

	/**
	 * @Description: 计算邮费
	 * @param goods
	 * @param shopNo
	 * @return
	 * @date: 2015年3月31日 上午11:22:14
	 * @author: zyl
	 * @param addressId
	 */
	Long calculatePostFee(List<Good> goods, String shopNo, Long addressId);

	/**
	 * @Description: 昨日收益
	 * @param no
	 * @return
	 * @date: 2015年4月1日 上午10:51:05
	 * @author: zyl
	 */
	Long getYesterdayIncomeByShopNo(String no);

	/**
	 * @Description: 卖家备注
	 * @param orderNo
	 * @param sellerMemo
	 * @date: 2015年4月3日 下午3:30:25
	 * @author: zyl
	 */
	void sellerRemark(String orderNo, String sellerMemo);

	Long calculatePostFee(OrderBuyVo orderBuyVo);

	/**
	 * @Description: 预约订单
	 * @param appointmentId
	 * @param salesOrderVo
	 * @param selectedList
	 * @date: 2015年4月16日 下午3:30:25
	 * @author: zyl
	 * @throws Exception
	 */
	String addAppointOrder(Long appointmentId, SalesOrderVo salesOrderVo,
			List<GoodSkuVo> goodSkuList) throws Exception;

	/**
	 * 确认预约订单
	 * 
	 * @param orderNo
	 */
	void confirmAppointOrder(String orderNo);


	/**
	 * 
	 * @Title: findOrderCountByShopNo
	 * @Description: 查询所有普通订单的数量
	 * @param no
	 * @return
	 * @date 2015年5月6日 下午5:35:09
	 * @author cbc
	 */
	Integer findOrderCountByShopNo(String no);



	/**
	 * 取消订单
	 * 
	 * <p>
	 * 
	 * 1.取消订单<br>
	 * 
	 * 2.取消订单的理由
	 * 
	 * @Title: cancleOrder
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param orderNo
	 *            店铺编号
	 * @param refuseReason
	 *            取消理由
	 * @param userId
	 * @date 2015年5月12日 下午2:32:14
	 * @author lys
	 */
	void cancleOrder(String orderNo, String refuseReason, Long userId)
			throws OrderException;







	/**
	 * 获取普通订单个数
	 * 
	 * @Title: getOrderCounts
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param salesOrderVo
	 *            查询条件
	 * @return
	 * @throws OrderException
	 * @date 2015年5月14日 下午7:46:10
	 * @author lys
	 */
	OrdersCountDto getOrderCounts(SalesOrderVo salesOrderVo)
			throws OrderException;





	/**
	 * 查询店铺的订单个数（根据订单类型进行区分）<br>
	 * 
	 * 1.普通订单<br>
	 * 2.预约订单<br>
	 * 
	 * @Title: getCountsByType
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param dto
	 *            店铺编号
	 * @return
	 * @throws OrderException
	 * @date 2015年5月18日 下午5:57:33
	 * @author lys
	 */
	Integer getCountsByType(SalesOrderDto dto) throws OrderException;
	


	
	Map<String,Integer>  getShopSalesOrderCountInfo(String shopNo);

	/**
	 * 
	 * @Title: countBookOrder 
	 * @Description: 预约订单的数量
	 * @param shopNo
	 * @param status
	 * @return
	 * @date 2015年8月17日 下午2:45:45  
	 * @author cbc
	 */
	Integer countBookOrder(String shopNo, Integer status);

	/**
	 * @Title: findCustomerOrderInMyShop
	 * @Description: (查询客户在我的总店下的订单)
	 * @param shopNo
	 * @param customerId
	 * @param pagination
	 * @return
	 * @return Pagination<SalesOrder> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月26日 下午4:11:56
	 */
	Pagination<SalesOrder> findCustomerOrderInMyShop(String shopNo, Long customerId, Pagination<SalesOrder> pagination);
	
	/**
	 * 新的改价接口
	 * @Title: adjustOrderPriceNew 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param orderNo
	 * @param userId
	 * @param adjustPriceYuan
	 * @date 2015年10月16日 下午6:10:51  
	 * @author cbc
	 */
	void adjustOrderPriceNew(String orderNo, Long userId, Long adjustPriceYuan);
}
