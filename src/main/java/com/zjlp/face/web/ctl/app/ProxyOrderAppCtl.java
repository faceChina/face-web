package com.zjlp.face.web.ctl.app;
//package com.jzwgj.assistant.ctl;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import net.sf.json.JSONObject;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.jzwgj.assistant.util.AssConstantsUtil;
//import com.jzwgj.assistant.vo.AgencyOrderVo;
//import com.jzwgj.assistant.vo.PurchaseOrderItemVo;
//import com.jzwgj.page.Pagination;
//import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
//import com.zjlp.face.web.server.trade.order.domain.dto.PurchaseOrderItemDto;
//
///**
// * 
//* @ClassName: ProxyOrderCtl
//* @Description: TODO(这里用一句话描述这个类的作用)
//* @author wxn
//* @date 2015年5月26日 下午4:14:48
//*
// */
//@Controller
//@RequestMapping("/ass/proxyOrder")
//public class ProxyOrderCtl extends BaseCtl {
//	
//	public static final String[] LIST_JSON_FIELDS = {"orderStatus","shopNo","curPage","start","pageSize","totalRow","orders.orderNo","orders.status","orders.quantity","orders.totalPriceStr",
//		"orders.totalProfitPriceStr","orders.purchaserNick",
//		"orders.createTimeStamp","orders.goodPicturePath","orders.deliveryWay","orders.orderItemList.goodName",
//		"orders.orderItemList.quantity","orders.orderItemList.skuPicturePath","orders.orderItemList.popularize"};
//	
//	public static final String[] NTORDERS_LIST_JSON_FIELDS = {"orderStatus","shopNo","curPage","start","pageSize","totalRow","orders.orderNo","orders.status","orders.quantity",
//		"orders.totalCommissionStr","orders.purchaserNick",
//		"orders.createTimeStamp","orders.goodPicturePath","orders.deliveryWay","orders.orderItemList.goodName","orders.orderItemList.subProfitPrice",
//		"orders.orderItemList.quantity","orders.orderItemList.skuPicturePath","orders.orderItemList.popularize"};
//	
//	Logger _logger = Logger.getLogger(getClass());
//	
//	@Autowired
//	private SalesOrderBusiness salesOrderBusiness;
//	
//	/**
//	 * 代理订单列表
//	* @Title: list
//	* @Description: TODO(这里用一句话描述这个方法的作用)
//	* @return
//	* @return String    返回类型
//	* @throws
//	* @author wxn  
//	* @date 2015年5月26日 下午4:31:03
//	 */
//	@RequestMapping(value="/list")
//	@ResponseBody
//	public String list(@RequestBody JSONObject jsonObj,Pagination<AgencyOrderDto> pagination){
//		try {
//			if (null == jsonObj || "".equals(jsonObj)) {
//				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
//			}
//			String shopNo = jsonObj.optString("shopNo");
//			if (StringUtils.isEmpty(shopNo)) {
//				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
//			}
//			// 分页信息
//			Object pageSizeObj = jsonObj.get("pageSize");
//			if (null != pageSizeObj && !"".equals(pageSizeObj)) {
//				pagination
//						.setPageSize(Integer.parseInt(pageSizeObj.toString()));
//			}
//			Object curPageObj = jsonObj.get("curPage");
//			if (null != curPageObj && !"".equals(curPageObj)) {
//				pagination.setCurPage(Integer.parseInt(curPageObj.toString()));
//			}
//			Object startobj = jsonObj.get("start");
//			if (null != startobj && !"".equals(startobj)) {
//				pagination.setStart(Integer.parseInt(startobj.toString()));
//			}
//			AgencyOrderDto dto = new AgencyOrderDto();
//			dto.setPurchaserNo(shopNo);
//			Object orderStatus = jsonObj.get("orderStatus");
//			if (null != orderStatus && !"".equals(orderStatus)) {
//				if (null != orderStatus && !"".equals(orderStatus)) {
//					Integer status = Integer.parseInt(orderStatus.toString());
//					if (0 != status.intValue()) {
//						dto.setStatus(status);
//					}
//				}
//			}
//			pagination = salesOrderBusiness.findOrderPageForDistributor(
//					pagination, dto);
//			List<AgencyOrderVo> list = new ArrayList<AgencyOrderVo>();
//			for (AgencyOrderDto order : pagination.getDatas()) {
//				AgencyOrderVo assorder = new AgencyOrderVo(order);
//				//组装商品信息
//				List<PurchaseOrderItemVo> purchaseOrderItemVoList = new ArrayList<PurchaseOrderItemVo>();
//				for (PurchaseOrderItemDto purchaseOrderItemDto : order
//						.getPurchaseOrderItemList()) {
//					PurchaseOrderItemVo item = new PurchaseOrderItemVo(
//							purchaseOrderItemDto);
//					purchaseOrderItemVoList.add(item);
//				}
//				assorder.setOrderItemList(purchaseOrderItemVoList);
//				list.add(assorder);
//			}
//			Map<String, Object> dataMap = new HashMap<String, Object>();
//			dataMap.put("orderStatus", dto.getStatus());
//			dataMap.put("shopNo", shopNo);
//			dataMap.put("curPage", pagination.getCurPage());
//			dataMap.put("start", pagination.getEnd());
//			dataMap.put("pageSize", pagination.getPageSize());
//			dataMap.put("totalRow", pagination.getTotalRow());
//			dataMap.put("orders", list);
//			return outSucceed(dataMap, true, LIST_JSON_FIELDS);
//		} catch (Exception e) {
//			_logger.error("分销商查询代理订单失败", e);
//			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
//		}
//	}
//	
//	
//	//下级订单列表
//	@RequestMapping(value="/ntorders")
//	@ResponseBody
//	public String listNextGradeOrders(@RequestBody JSONObject jsonObj,
//			Pagination<AgencyOrderDto> pagination) {
//		
//		try {
//			if (null == jsonObj || "".equals(jsonObj)) {
//				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
//			}
//			String shopNo = jsonObj.optString("shopNo");
//			if (StringUtils.isEmpty(shopNo)) {
//				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
//			}
//			// 分页信息
//			Object pageSizeObj = jsonObj.get("pageSize");
//			if (null != pageSizeObj && !"".equals(pageSizeObj)) {
//				pagination
//						.setPageSize(Integer.parseInt(pageSizeObj.toString()));
//			}
//			Object curPageObj = jsonObj.get("curPage");
//			if (null != curPageObj && !"".equals(curPageObj)) {
//				pagination.setCurPage(Integer.parseInt(curPageObj.toString()));
//			}
//			Object startobj = jsonObj.get("start");
//			if (null != startobj && !"".equals(startobj)) {
//				pagination.setStart(Integer.parseInt(startobj.toString()));
//			}
//			AgencyOrderDto dto = new AgencyOrderDto();
//			dto.setShopNo(shopNo);
//			Object orderStatus = jsonObj.get("orderStatus");
//			if (null != orderStatus && !"".equals(orderStatus)) {
//				Integer status = Integer.parseInt(orderStatus.toString());
//				if (0 != status.intValue()) {
//					dto.setStatus(status);
//				}
//			}
//			pagination = salesOrderBusiness.findOrderPageForDistributor(
//					pagination, dto);
//			List<AgencyOrderVo> list = new ArrayList<AgencyOrderVo>();
//			for (AgencyOrderDto order : pagination.getDatas()) {
//				AgencyOrderVo assorder = new AgencyOrderVo(order);
//				//组装商品信息
//				List<PurchaseOrderItemVo> purchaseOrderItemVoList = new ArrayList<PurchaseOrderItemVo>();
//				for (PurchaseOrderItemDto purchaseOrderItemDto : order
//						.getPurchaseOrderItemList()) {
//					PurchaseOrderItemVo item = new PurchaseOrderItemVo(
//							purchaseOrderItemDto);
//					purchaseOrderItemVoList.add(item);
//				}
//				assorder.setOrderItemList(purchaseOrderItemVoList);
//				list.add(assorder);
//			}
//			Map<String, Object> dataMap = new HashMap<String, Object>();
//			dataMap.put("orderStatus", dto.getStatus());
//			dataMap.put("shopNo", shopNo);
//			dataMap.put("curPage", pagination.getCurPage());
//			dataMap.put("start", pagination.getEnd());
//			dataMap.put("pageSize", pagination.getPageSize());
//			dataMap.put("totalRow", pagination.getTotalRow());
//			dataMap.put("orders", list);
//			return outSucceed(dataMap, true, NTORDERS_LIST_JSON_FIELDS);
//		} catch (Exception e) {
//			_logger.error("分销商查询下级代理订单失败", e);
//			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
//		}
//	}
//	
// }
