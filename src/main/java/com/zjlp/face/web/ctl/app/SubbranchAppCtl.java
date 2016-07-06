package com.zjlp.face.web.ctl.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.appvo.AssPagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.SubbranchException;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchGoodRelationBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchGoodRelationVo;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchVo;
import com.zjlp.face.web.server.trade.order.bussiness.OperateDataBusiness;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.trade.order.bussiness.SubBranchSaleOrderBusiness;
import com.zjlp.face.web.server.trade.order.domain.vo.SubbranchRankingVo;
import com.zjlp.face.web.server.trade.order.domain.vo.SupplierSalesRankingVo;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopSubbranchDto;
import com.zjlp.face.web.server.user.shop.producer.ShopProducer;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.AssConstantsUtil;
import com.zjlp.face.web.util.Logs;

/**
 * 总分店
 * 
 * @author Baojiang Yang
 * @date 2015年6月22日 下午3:55:55
 *
 */
@Controller
@RequestMapping({ "/assistant/ass/subbranch/" })
public class SubbranchAppCtl extends BaseCtl {

	private Logger _logger = Logger.getLogger(getClass());

	private static final String[] SEARCH_SHOP_PHONE_FILLTER = { "userId", "userName", "userAccount", "shop.subbrachId",
			"shop.no", "shop.role", "shop.name", "shop.shopLogoUrl", "shop.isHistorySubbranch", "shop.shopName", "shop.isBF" };

	private static final String[] SEARCH_MY_SHOP_FILLTER = { "subbrachId", "no", "role", "paidOrderNo", "paidPrice",
			"liveProfit", "deliverOrder", "shopLogoUrl", "name", "shareLink", "shopName", "permission", "todayPayCommission", "status" };

	private static final String[] FIND_MY_SUBBRANCH_FILLTER = { "curPage", "start", "pageSize", "totalRow", "datas.id",
			"datas.shopName", "datas.userName", "datas.profit", "datas.shopLogo", "datas.subPhone" };

	private static final String[] SUBBRANCH_DEATIL_FILLTER = { "id", "subAccount", "userName", "phoneNo", "shopName", "profit",
			"deliver", "status", "freezeShop", "shopLogo", "supplierShopNo" ,"count" };
	
	private static final String[] HISTORY_SUBBRANCH_FILLTER= { "curPage", "start", "pageSize", "totalRow", "datas.id", "datas.pid", "datas.shopName", "datas.isAbleToRecruit","datas.shopLogo"};

	private static final String[] SHOP_DEATIL_FILLTER = { "no", "name", "shopLogoUrl" };

	private static final String[] SEARCH_MY_SUB_FILLTER = { "id", "shopName", "userName", "profit", "shopLogo", "subPhone", "freezeShop" };
	
	private static final String[] SUPPLIER_RANKING_FILTER = {"curPage", "start", "pageSize", "totalRow", "datas.subbranchId", "datas.userName", "datas.userId", "datas.orderCount", "datas.payAmountStr", "datas.headUrl"};
	
	private static final String[] SUPPLIER_RANKING_DETAIL_FILTER = {"userName", "orderCount", "payAmountStr", "headUrl", "commissionStr", "level", "userCell", "loginAccount"};

	private static final String DEFAULT_ORDER = "CREATE_TIME";
	
	private static final String OPERATION_RIGHT = "OPERATION_RIGHT";
	
	private static final String[] FIND_MY_SUBBRANCHGOODRELATION_FILLTER = { "curPage", "start", "pageSize", "totalRow", "datas.id",
			"datas.subbranchId", "datas.goodId", "datas.rate", "datas.name","datas.picUrl","datas.priceMax","datas.priceMin"};
	
	@Autowired
	private SubBranchSaleOrderBusiness subBranchSaleOrderBusiness;
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	@Autowired
	private SubbranchBusiness subbranchBusiness;
	@Autowired
	private ShopBusiness shopBusiness;
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private ShopProducer shopProducer;
	@Autowired
	private OperateDataBusiness operateDataBusiness;
	@Autowired
	private SubbranchGoodRelationBusiness subbranchGoodRelationBusiness;

	/**
	 * @Title: searchShop
	 * @Description: (根据号码查找店铺)
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月23日 上午8:52:02
	 */
	@RequestMapping(value = "searchShops")
	@ResponseBody
	public String searchShops(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 查找手机号
			String searchPhoneNo = jsonObj.optString("searchPhoneNo");
			if (StringUtils.isEmpty(searchPhoneNo)) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			User user = null;
			try {
				user = userBusiness.getUserByLoginAccount(searchPhoneNo);
			} catch (Exception e) {
				return outFailure(AssConstantsUtil.SubbeanchCode.NULL_PHONE_NO_ERROR_CODE, "");
			}
			if (user == null || user.getId() == null) {
				return outFailure(AssConstantsUtil.SubbeanchCode.NULL_PHONE_NO_ERROR_CODE, "");
			}
			List<ShopSubbranchDto> result = new ArrayList<ShopSubbranchDto>();
			List<ShopSubbranchDto> shop = this.subbranchBusiness.searchShopByPhoneNo(searchPhoneNo, super.getUserId());
			// 处理店铺名为null时转成JSON为"null"
			for (ShopSubbranchDto current : shop) {
				if (StringUtils.isBlank(current.getName())) {
					current.setName(StringUtils.EMPTY);
				}
				if (StringUtils.isBlank(current.getShopName())) {
					current.setShopName(StringUtils.EMPTY);
				}
				result.add(current);
			}
			// 分店排在搜索结果中总店的上边
			Collections.reverse(result);
			Map<String,Object> map = new HashMap<String, Object>();
			if (user != null && user.getId() != null) {
				map.put("userId", user.getId());
				map.put("userName", user.getNickname());
				map.put("userAccount", user.getLoginAccount());
				map.put("userBindPhone", user.getCell());
			}
			map.put("shop", result);
			return outSucceed(map, true, SEARCH_SHOP_PHONE_FILLTER);
		} catch (Exception e) {
			_logger.error("根据号码查找店铺失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: searchMyShops
	 * @Description: (查询登陆用户的店铺)
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月23日 上午9:53:18
	 */
	@RequestMapping(value = "searchMyShops" ,method=RequestMethod.POST)
	@ResponseBody
	public String searchMyShops(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			// 用户ID
			Object userIdObj = jsonObj.get("userId");
			Long userId = userIdObj != null && StringUtils.isNotBlank(userIdObj.toString()) ? Long.parseLong(userIdObj.toString()) : null;
			if (userId == null) {
				userId = getUserId();
			}
			List<ShopSubbranchDto> result = new ArrayList<ShopSubbranchDto>();
			List<ShopSubbranchDto> list = this.subbranchBusiness.searchShopByUserId(userId);
			// list中最多有两条数据，角色为1的是总店，角色为2的是分店
			for (ShopSubbranchDto current : list) {	
				if (current.getRole() == 1) {
					// 计算总店下产生的订单
					Integer paidOrderNo = null;
					Long paidPrice = null;
					Long todayPayCommission = null;
					try {
						paidOrderNo = operateDataBusiness.supplierPayOrderCount(userId);
						paidPrice = operateDataBusiness.supplierRecivePrice(userId);
						todayPayCommission = operateDataBusiness.supplierPayCommission(userId);
					} catch (Exception e) {
						_logger.info("查询总店今日付款订单/金额失败", e);
					}
					current.setPaidOrderNo(paidOrderNo);// 今日付款订单
					current.setPaidPrice(CalculateUtils.converFenToYuan(paidPrice));// 今日付款金额
					current.setTodayPayCommission(CalculateUtils.converFenToYuan(todayPayCommission));//今日支出佣金
					if (StringUtils.isNotBlank(current.getNo())) {
						current.setShareLink(new StringBuilder("/wap/").append(current.getNo()).append("/any/gwscIndex.htm").toString());// 总店分享链接
					}
				} else if (current.getRole() == 2) {
					// 计算分店下产生的订单
					Long subbrachId = current.getSubbrachId();
					Integer paidOrderNo = null;
					Long paidPrice = null;
					try {
						paidOrderNo = operateDataBusiness.countTodayPayOrder(subbrachId);
						paidPrice = operateDataBusiness.countTodayCommission(subbrachId);
					} catch (Exception e) {
						_logger.info("查询分店今日付款订单/解冻金额失败", e);
					}
					current.setPaidOrderNo(paidOrderNo);// 今日付款订单
					current.setLiveProfit(CalculateUtils.converFenToYuan(paidPrice));// 今日解冻佣金
					if (StringUtils.isNotBlank(current.getNo())) {
						current.setShareLink(new StringBuilder("/wap/").append(current.getNo()).append("/any/gwscIndex.htm?subbranchId=").append(current.getSubbrachId()).toString());// 分店分享链接
						current.setNo(null);
					}
				}
				result.add(current);
			}
			return outSucceed(result, true, SEARCH_MY_SHOP_FILLTER);
		} catch (Exception e) {
			_logger.error("查询登陆用户的店铺失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: applyAsSubranch
	 * @Description: (申请成为分店（电话号码为参数）)
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月22日 下午5:36:34
	 */
	@RequestMapping(value = "applyAsSubByPhone")
	@ResponseBody
	public String applyAsSubByPhone(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 查找手机号
			String searchPhoneNo = jsonObj.optString("searchPhoneNo");
			// 官网：1 分店：2
			Object roleObj = jsonObj.get("role");
			// 用户提交昵称
			String submitNickName = jsonObj.optString("submitNickName");
			// 用户提交手机号
			String submitPhoneNo = jsonObj.optString("submitPhoneNo");
			Integer role = roleObj != null && StringUtils.isNotBlank(roleObj.toString()) ?Integer.parseInt(roleObj.toString()) : null;
			if (StringUtils.isEmpty(searchPhoneNo) || role == null) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long id = this.subbranchBusiness.applyAsSubByPhone(getUserId(), searchPhoneNo, role, submitNickName, submitPhoneNo);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if (id != null && id > 0) {
				dataMap.put("subbranchId", id);
			} else {
				dataMap.put("errorCode", id);
			}
			return outSucceed(dataMap, false, "");
		} catch (Exception e) {
			_logger.error("申请分店失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: applyAsSubById
	 * @Description: (申请成为分店（ID为参数）)
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月24日 下午2:53:29
	 */
	@RequestMapping(value = "applyAsSubById")
	@ResponseBody
	public String applyAsSubById(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 总店id
			String shopNo = jsonObj.optString("shopNo");
			// 分店id
			Object subbranchObj = jsonObj.get("subbranchId");
			Long subbranchId = subbranchObj != null && StringUtils.isNotBlank(subbranchObj.toString())? Long.parseLong(subbranchObj.toString()) : null;
			// 用户提交昵称
			String submitNickName = jsonObj.optString("submitNickName");
			// 用户提交手机号
			String submitPhoneNo = jsonObj.optString("submitPhoneNo");
			if ((StringUtils.isEmpty(shopNo) && subbranchId == null) || StringUtils.isNotBlank(shopNo) && subbranchId != null) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Long id = this.subbranchBusiness.applyAsSubById(getUserId(), shopNo, subbranchId, submitNickName, submitPhoneNo);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if (id != null && id > 0) {
				dataMap.put("subbranchId", id);
				Subbranch spuerSub = this.subbranchBusiness.findSubbranchById(id);
				if (spuerSub != null) {
					User superUser = this.userBusiness.getUserById(spuerSub.getSuperiorUserId());
					dataMap.put("superPhone", superUser != null ? superUser.getLoginAccount() : StringUtils.EMPTY);
				}

			} else {
				dataMap.put("errorCode", id);
			}
			return outSucceed(dataMap, false, "");
		} catch (Exception e) {
			_logger.error("申请分店失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: updateShopName
	 * @Description: (更新店铺名称)
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月23日 上午11:22:56
	 */
	@RequestMapping(value = "updateShopName")
	@ResponseBody
	public String updateShopName(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String shopName = jsonObj.optString("shopName");
			if (StringUtils.isEmpty(shopName)) {
				return outFailure(AssConstantsUtil.SubbeanchCode.NULL_SHOP_NAME_ERROR_CODE, "");
			}
			// 官网：1 分店：2
			Object roleObj = jsonObj.get("role");
			Integer role = roleObj != null && StringUtils.isNotBlank(roleObj.toString()) ? Integer.parseInt(roleObj.toString()) : null;
			// 总店ID
			String shopNo = jsonObj.optString("shopNo");
			// 分店ID
			Object subbranchObj = jsonObj.get("subbranchId");
			Long subbranchId = subbranchObj != null && StringUtils.isNotBlank(subbranchObj.toString()) ? Long.parseLong(subbranchObj.toString()) : null;
			if (role == null) {
				return outFailure(AssConstantsUtil.SubbeanchCode.NULL_ROLE_ERROR_CODE, "");
			} else if (role == 1 && StringUtils.isEmpty(shopNo)) {
				return outFailure(AssConstantsUtil.SubbeanchCode.NULL_SHOP_NO_ERROR_CODE, "");
			} else if (role == 2 && subbranchId == null) {
				return outFailure(AssConstantsUtil.SubbeanchCode.NULL_SUB_ID_ERROR_CODE, "");
			}
			if (role == 1) {// 更新总店
				Shop shop = new Shop();
				shop.setNo(shopNo);
				shop.setName(shopName);
				this.shopBusiness.editShop(shop);
				return outSucceedByNoData();
			} else if (role == 2) {// 更新分店
				this.subbranchBusiness.updateSubbranchName(subbranchId, shopName);
				return outSucceedByNoData();
			}
			return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("申请分店失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: shopManagement
	 * @Description: (我的分店列表)
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月23日 上午11:40:03
	 */
	@RequestMapping(value = "subbranchList")
	@ResponseBody
	public String subbranchList(@RequestBody JSONObject jsonObj, Pagination<SubbranchVo> pagination) throws Exception {
		try {
			// 检测提交的参数
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String orderBy = jsonObj.optString("orderBy");
			if (StringUtils.isEmpty(orderBy)) {
				orderBy = DEFAULT_ORDER;
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);
			Object roleObj = jsonObj.get("role");
			Integer role = roleObj != null && StringUtils.isNotBlank(roleObj.toString()) ? Integer.parseInt(roleObj.toString()): null;
			if (null == role) {
				return outFailure(AssConstantsUtil.SubbeanchCode.NULL_ROLE_ERROR_CODE, "");
			}
			// 查询分店
			SubbranchVo subbranchVo = new SubbranchVo();
			subbranchVo.setSuperiorUserId(getUserId());
			subbranchVo.setOrderBy(orderBy);
			subbranchVo.setType(role);
			pagination = this.subbranchBusiness.findPageSubbrach(subbranchVo, pagination);
			// 重新封装Pagination对象
			AssPagination<SubbranchVo> newpag = new AssPagination<SubbranchVo>();
			newpag.setCurPage(pagination.getCurPage());
			newpag.setStart(pagination.getEnd());
			newpag.setPageSize(pagination.getPageSize());
			newpag.setTotalRow(pagination.getTotalRow());
			newpag.setDatas(pagination.getDatas());
			return outSucceed(newpag, true, FIND_MY_SUBBRANCH_FILLTER);
		} catch (Exception e) {
			_logger.error("查询我的分店列表失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: getSubbDetail
	 * @Description: (查找分店详情)
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月23日 上午11:56:04
	 */
	@RequestMapping(value = "getSubDetail")
	@ResponseBody
	public String subbranchDetail(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 分店ID
			Object subbranchObj = jsonObj.get("subbranchId");
			Long subbranchId = subbranchObj != null && StringUtils.isNotBlank(subbranchObj.toString())? Long.parseLong(subbranchObj.toString()) : null;
			if (subbranchId == null) {
				return outFailure(AssConstantsUtil.SubbeanchCode.NULL_SUB_ID_ERROR_CODE, "");
			}
			Subbranch subbranch = this.subbranchBusiness.findSubbranchById(subbranchId);
			SubbranchVo result = new SubbranchVo();
			if (subbranch != null) {
				User user = this.userBusiness.getUserById(subbranch.getUserId());
				if (user != null) {
					result.setId(subbranch.getId());// 分店ID
					result.setSubAccount(user.getLoginAccount());// 分店账号
					result.setUserName(StringUtils.isNotBlank(subbranch.getUserName()) ? subbranch.getUserName() : user.getNickname());// 姓名
					result.setPhoneNo(subbranch.getUserBindingCell());// 分店绑定手机号/联系方式
					result.setShopName(subbranch.getShopName());
					result.setProfit(subbranch.getProfit());// 佣金比例
					result.setDeliver(subbranch.getDeliver());// 发货权限:0,无;1,有
					result.setStatus(subbranch.getStatus());// 状态:1,有效;0,冻结;-1:删除
					result.setFreezeShop(subbranch.getFreezeShop());
					if (StringUtils.isNotBlank(subbranch.getSupplierShopNo())) {
						Shop shop = this.shopBusiness.getShopByNo(subbranch.getSupplierShopNo());
						result.setShopLogo(shop != null ? shop.getShopLogoUrl() : subbranch.getShopLogo());
						result.setSupplierShopNo(subbranch.getSupplierShopNo());
					}
					
					//查询分店已设置佣金比例的商品数量
					Integer count = subbranchGoodRelationBusiness.getTotalCount(subbranchId, subbranch.getSupplierShopNo(), Constants.SUBBRANCH_TYPE_HAVE);
					result.setCount(count);
				}
			}
			Logs.print(result);
			return outSucceed(result, true, SUBBRANCH_DEATIL_FILLTER);
		} catch (Exception e) {
			_logger.error("查找分店详情失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: getShopDetail
	 * @Description: (总店详情)
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月24日 上午11:11:13
	 */
	@RequestMapping(value = "getShopDetail")
	@ResponseBody
	public String getShopDetail(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 分店ID
			String shopNo = jsonObj.optString("shopNo");
			if (StringUtils.isEmpty(shopNo)) {
				return outFailure(AssConstantsUtil.SubbeanchCode.NULL_SUB_ID_ERROR_CODE, "");
			}
			Shop shop = this.shopBusiness.getShopByNo(shopNo);
			return outSucceed(shop, true, SHOP_DEATIL_FILLTER);
		} catch (Exception e) {
			_logger.error("查找分店详情失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	/**
	 * @Title: updateSubbranch
	 * @Description: (更新分店信息)
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月23日 上午11:56:04
	 */
	@RequestMapping(value = "updateSubbranch")
	@ResponseBody
	public String updateSubbranch(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Object userIdObj = jsonObj.get("userId");
			Long userId = null;
			if (userIdObj != null && StringUtils.isNotBlank(userIdObj.toString())) {
				userId = Long.valueOf(userIdObj.toString());
			} else {
				return outFailure(AssConstantsUtil.SubbeanchCode.NULL_SUB_ID_ERROR_CODE, "");
			}
			// 分店ID
			Object subbranchIdObj = jsonObj.get("subbranchId");
			Long subbranchId = subbranchIdObj != null && StringUtils.isNotBlank(subbranchIdObj.toString())? Long.parseLong(subbranchIdObj.toString()) : null;
			if (subbranchId == null) {
				return outFailure(AssConstantsUtil.SubbeanchCode.NULL_USER_ID_ERROR_CODE, "");
			}
			// 佣金百分比
			Object profitObj = jsonObj.get("profit");
			Integer profit = profitObj != null && StringUtils.isNotBlank(profitObj.toString()) ? Integer.parseInt(profitObj.toString()) : null;
			// 发货权限:0,无;1,有
			Object deliveroObj = jsonObj.get("deliver");
			Integer deliver = deliveroObj != null && StringUtils.isNotBlank(deliveroObj.toString()) ? Integer.parseInt(deliveroObj.toString()) : null;
			// 状态:1,有效;0,冻结;-1:删除
			Object statusObj = jsonObj.get("status");
			Integer status = statusObj != null && StringUtils.isNotBlank(statusObj.toString()) ? Integer.parseInt(statusObj.toString()) : null;
			// 冻结店铺
			Object freezeShopObj = jsonObj.get("freezeShop");
			Integer freezeShop = freezeShopObj!=null && StringUtils.isNotBlank(freezeShopObj.toString()) ? Integer.parseInt(freezeShopObj.toString()) : null;
			// 店铺LOGO
			String shopLogo = jsonObj.optString("shopLogo");
			if (profit != null || deliver != null || status != null) {
				Subbranch subbranch = new Subbranch();
				subbranch.setActionUserId(userId);// 当前登陆者才能操作自己的分店
				subbranch.setId(subbranchId);
				if (profit != null) {
					if (profit < 0 || profit > 100) {
						return outFailure(AssConstantsUtil.SubbeanchCode.INVALID_PROFIT_ERROR_CODE, "");
					} else {
						subbranch.setProfit(profit);
					}
				}
				subbranch.setDeliver(deliver);
				subbranch.setStatus(status);
				if (status != null && !Subbranch.isValidStatus(status)) {
					return outFailure(AssConstantsUtil.SubbeanchCode.INVALID_STATUS_ERROR_CODE, "");
				}
				subbranch.setFreezeShop(freezeShop);
				subbranch.setShopLogo(shopLogo);
				Long code = this.subbranchBusiness.updateSubbranchInfo(subbranch);
				if (code > 0) {
					return outSucceedByNoData();
				} else {
					Map<String, Object> dataMap = new HashMap<String, Object>();
					dataMap.put("updateCode", code);
					return outSucceed(dataMap, false, "");
				}
			}
			return outFailure(AssConstantsUtil.SubbeanchCode.NO_CHANGE_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("申请分店失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: searchMySubBykey
	 * @Description: (根据关键字查找分店)
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月25日 下午4:56:36
	 */
	@RequestMapping(value = "searchMySubBykey")
	@ResponseBody
	public String searchMySubBykey(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 查找关键字
			String keyword = jsonObj.optString("keyword");
			if (StringUtils.isBlank(keyword)) {
				return outFailure(AssConstantsUtil.SubbeanchCode.NULL_SUB_ID_ERROR_CODE, "");
			}
			Object roleObj = jsonObj.get("role");
			Integer role = roleObj != null && StringUtils.isNotBlank(roleObj.toString()) ? Integer.parseInt(roleObj.toString()): null;
			if (role == null) {
				return outFailure(AssConstantsUtil.SubbeanchCode.NULL_ROLE_ERROR_CODE, "");
			}
			List<SubbranchVo> result = this.subbranchBusiness.findMySubbrachs(getUserId(), keyword, role);
			return outSucceed(result, true, SEARCH_MY_SUB_FILLTER);
		} catch (Exception e) {
			_logger.error("根据关键字查找分店失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: updateShopLogo
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月25日 下午5:41:50
	 */
	@RequestMapping(value = "updateShopLogo")
	@ResponseBody
	public String updateShopLogo(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String shopNo = jsonObj.optString("shopNo");
			// 店铺LOGO
			String shopLogoUrl = jsonObj.optString("shopLogoUrl");
			if (StringUtils.isBlank(shopLogoUrl) || StringUtils.isBlank(shopNo)) {
				return outFailure(AssConstantsUtil.SubbeanchCode.NULL_SUB_ID_ERROR_CODE, "");
			}
			Shop shop = new Shop();
			shop.setNo(shopNo);
			shop.setUserId(getUserId());
			shop.setShopLogoUrl(shopLogoUrl);
			boolean isOk = this.shopProducer.updateShopInfo(shop);
			if (isOk) {
				return outSucceedByNoData();
			}
			return outFailure(AssConstantsUtil.SubbeanchCode.UPDATE_LOGO_ERROR_CODE, "");
		} catch (Exception e) {
			_logger.error("更新总店logo失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	/**
	 * 解除分店绑定
	 * @Title: unbind 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param jsonObj 解除参数
	 * @return
	 * @date 2015年7月14日 上午10:01:32  
	 * @author lys
	 */
	@RequestMapping(value = "unbind")
	@ResponseBody
	public String unbind(@RequestBody JSONObject jsonObj){
		try {
			//subbranchId
			String subbranchId = jsonObj.optString("subbranchId");
			if (StringUtils.isBlank(subbranchId)) {
				return outFailure(AssConstantsUtil.SubbeanchCode.NO_UNBINDID_ERROR_CODE, "");
			}
			// 解绑
			boolean isOk = subbranchBusiness.unBindSubbranch(super.getUserId(), Long.valueOf(subbranchId));
			//返回成功解除数据
			if (isOk) {
				return outSucceedByNoData();
			}
			//解除分店失败
			return outFailure(AssConstantsUtil.SubbeanchCode.NO_UNBINDID_FAIL_ERROR_CODE, "");
		} catch (Exception e) {
			//没有解除绑定的权限
			if (OPERATION_RIGHT.equals(e.getMessage())) {
				return outFailure(AssConstantsUtil.SubbeanchCode.NO_RIGHT_UNBINDID_ERROR_CODE, "");
			}
			_logger.error("解除分店绑定！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * 
	 * @Title: listHistorySubbranch 
	 * @Description: 查询用户的历史分店
	 * @param jsonObj
	 * @param pagination
	 * @return
	 * @date 2015年7月14日 下午2:14:10  
	 * @author cbc
	 */
	@RequestMapping("historySubbranchs")
	@ResponseBody
	public String listHistorySubbranch(@RequestBody JSONObject jsonObj,  Pagination<Subbranch> pagination) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);
			pagination = subbranchBusiness.findHistorySubbranchByUserIdPage(super.getUserId(), pagination);
			AssPagination<Subbranch> newpag = new AssPagination<Subbranch>();
			newpag.setCurPage(pagination.getCurPage());
			newpag.setStart(pagination.getEnd());
			newpag.setPageSize(pagination.getPageSize());
			newpag.setTotalRow(pagination.getTotalRow());
			newpag.setDatas(pagination.getDatas());
			return outSucceed(newpag, true, HISTORY_SUBBRANCH_FILLTER);
		} catch (Exception e) {
			_logger.error("查询历史分店失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	/**
	 * @Title: getMyGwscUrl
	 * @Description: (获取当前用户的官网首页)
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月14日 上午10:41:15
	 */
	@RequestMapping(value = "getMyGwscUrl")
	@ResponseBody
	public String getMyGwscUrl(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Object userIdObj = jsonObj.get("userId");
			Long userId = userIdObj != null && StringUtils.isNotBlank(userIdObj.toString()) ? Long.parseLong(userIdObj.toString()) : null;
			if (userId == null) {
				userId = getUserId();
			}
			if (userId == null) {
				return outFailure(AssConstantsUtil.SubbeanchCode.NULL_USER_ID_ERROR_CODE, "");
			}
			List<Shop> shopList = this.shopBusiness.findShopListByUserId(userId);
			String gwscUrl = null;
			if (CollectionUtils.isNotEmpty(shopList) && shopList.size() == 1) {
				gwscUrl = new StringBuilder("/wap/").append(shopList.get(0).getNo()).append("/any/index.htm").toString();
			} else {
				return outFailure(AssConstantsUtil.SubbeanchCode.DITY_DATA_ERROR_CODE, "");
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("gwscUrl", gwscUrl);
			return outSucceed(dataMap, false, "");
		} catch (Exception e) {
			_logger.error("查询我的官网首页失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 4.8assistant
	 * "orderBy":1,  //1为成交金额排序， 2为订单数排序
	 * "time":1 //1为昨日，2为7日，3为30日
	 * @Title: getSupplierSalesRaning 
	 * @Description: 总店销售榜单 
	 * @param jsonObj
	 * @param pagination
	 * @return
	 * @date 2015年8月11日 下午5:39:52  
	 * @author cbc
	 */
	@RequestMapping(value="supplierRanking")
	@ResponseBody
	public String getSupplierSalesRaning(@RequestBody JSONObject jsonObj, Pagination<SupplierSalesRankingVo> pagination) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			pagination = super.initPagination(jsonObj);
			Object orderByObj = jsonObj.get("orderBy");
			Integer orderBy = null;
			if (null != orderByObj && !"".equals(orderByObj)) {
				orderBy = Integer.valueOf(orderByObj.toString());
			}
			Object timeObj = jsonObj.get("time");
			Integer time = null;
			if (null != timeObj && !"".equals(timeObj)) {
				time = Integer.valueOf(timeObj.toString());
			}
			Long userId = super.getUserId();
			pagination = operateDataBusiness.getSupplierSalesRaning(userId, orderBy, time, pagination);
			AssPagination<SupplierSalesRankingVo> newpag = new AssPagination<SupplierSalesRankingVo>();
			newpag.setCurPage(pagination.getCurPage());
			newpag.setStart(pagination.getEnd());
			newpag.setPageSize(pagination.getPageSize());
			newpag.setTotalRow(pagination.getTotalRow());
			newpag.setDatas(pagination.getDatas());
			return outSucceed(newpag, true, SUPPLIER_RANKING_FILTER);
		} catch (Exception e) {
			_logger.error("查询总店销售排行失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 
	 * 4.8assistant
	 * @Title: getSupplierSalesRaningDetail 
	 * @Description: 总店销售排行榜详情
	 * @param jsonObj "time":1 //1为昨日，2为7日，3为30日
	 * @return
	 * @date 2015年8月12日 上午9:58:08  
	 * @author cbc
	 */
	@RequestMapping(value="supplierRankingDetail")
	@ResponseBody
	public String getSupplierSalesRaningDetail(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Object subbranchIdObj = jsonObj.get("subbranchId");
			Long subbranchId = null;
			if (null != subbranchIdObj && !"".equals(subbranchIdObj)) {
				subbranchId = Long.valueOf(subbranchIdObj.toString());
			}
			Object timeObj = jsonObj.get("time");
			Integer time = null;
			if (null != timeObj && !"".equals(timeObj)) {
				time = Integer.valueOf(timeObj.toString());
			}
			SupplierSalesRankingVo supplierSalesRankingVo = operateDataBusiness.getSupplierSalesRankingDetail(subbranchId, time);
			return outSucceed(supplierSalesRankingVo, true, SUPPLIER_RANKING_DETAIL_FILTER);
		} catch (Exception e) {
			_logger.error("查询总店销售排行详情失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		} 
	}
	
	
	/**
	 * 4.8assistant
	 * @Title: getSubbranchRaning 
	 * @Description: 分店佣金排行榜
	 * @param jsonObj
	 * @return
	 * @date 2015年8月12日 下午5:39:52  
	 * @author talo
	 */
	@RequestMapping(value="subbranchRanking")
	@ResponseBody
	public String getSubbranchRaning(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Object subbranchIdObj = jsonObj.get("subbranchId");
			Long subbranchId = null;
			if (null != subbranchIdObj && !"".equals(subbranchIdObj)) {
				subbranchId = Long.valueOf(subbranchIdObj.toString());
			}
			Object daysObj = jsonObj.get("days");
			Integer days = null;
			if (null != daysObj && !"".equals(daysObj)) {
				days = Integer.valueOf(daysObj.toString());
			}
			SubbranchRankingVo subbranchRankingVo = operateDataBusiness.getSubbranchRaning(subbranchId, days);
			return outSucceed(subbranchRankingVo, false,"");
		} catch (Exception e) {
			_logger.error("查询分店佣金排行榜失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * 新增分店佣金比例
	 * @param jsonObj
	 * @return
	 */
	@RequestMapping(value="addSubbranchGoodBrokerage")
	@ResponseBody
	public String addSubbranchGoodBrokerage(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Long subbranchId = jsonObj.getLong("subbranchId");
			Long userId = jsonObj.getLong("userId");
			Integer rate = jsonObj.getInt("rate");
			String goodIds = jsonObj.getString("goodIds");
			
			String[] goods = goodIds.split(",");
			Long[] goodIdList = new Long[goods.length];
			for(int i=0;i<goods.length;i++){
				goodIdList[i] = Long.parseLong(goods[i]);
			}
			
			subbranchGoodRelationBusiness.addSubbranchGoodBrokerage(goodIdList, subbranchId, userId, rate);
			return outSucceedByNoData();
		} catch(SubbranchException e){
			_logger.error("新增分店佣金比例出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		} catch (Exception e) {
			_logger.error("新增分店佣金比例出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		}
	}
	
	/**
	 * 查询分店已设置佣金比例的商品数量
	 * @param jsonObj
	 * @return
	 */
	@RequestMapping(value="getHaveTotalCount")
	@ResponseBody
	public String getHaveTotalCount(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Long subbranchId = jsonObj.getLong("subbranchId");
			String shopNo = jsonObj.getString("shopNo");
			
			Integer count = subbranchGoodRelationBusiness.getTotalCount(subbranchId, shopNo, Constants.SUBBRANCH_TYPE_HAVE);
			return outSucceed(count, false,"");
		} catch(SubbranchException e){
			_logger.error("查询分店已设置佣金比例的商品数量出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		} catch (Exception e) {
			_logger.error("查询分店已设置佣金比例的商品数量出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		}
	}
	
	/**
	 * 删除分店商品佣金比例
	 * @param jsonObj
	 * @return
	 */
	@RequestMapping(value="deleteSubbranchGoodRelation")
	@ResponseBody
	public String deleteSubbranchGoodRelation(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Long id = jsonObj.getLong("id");
			
			subbranchGoodRelationBusiness.deleteByPrimaryKey(id);
			return outSucceedByNoData();
		} catch(SubbranchException e){
			_logger.error("删除佣金比例出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		} catch (Exception e) {
			_logger.error("删除佣金比例出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		}
	}
	
	/**
	 * 修改分店商品佣金比例
	 * @param jsonObj
	 * @return
	 */
	@RequestMapping(value="updateSubbranchGoodRelation")
	@ResponseBody
	public String updateSubbranchGoodRelation(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Long id = jsonObj.getLong("id");
			Integer rate = jsonObj.getInt("rate");
			
			subbranchGoodRelationBusiness.update(id, rate);
			return outSucceedByNoData();
		} catch(SubbranchException e){
			_logger.error("修改佣金比例出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		} catch (Exception e) {
			_logger.error("修改佣金比例出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		}
	}
	
	/**
	 * 查询分店商品佣金比例列表
	 * @param jsonObj
	 * @return
	 */
	@RequestMapping(value="findPageSubbranchGood")
	@ResponseBody
	public String findPageSubbranchGood(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Long subbranchId = jsonObj.getLong("subbranchId");
			String shopNo = jsonObj.getString("shopNo");
			Integer type =  jsonObj.getInt("type");
			String name =  jsonObj.getString("name");
			
			int curPage = 1; // 当前页
			int pageSize = 10; // 每页多少行
			int start = 0;// 当前页起始行
			
			try {
				curPage = jsonObj.getInt("curPage");
				pageSize = jsonObj.getInt("pageSize");
				start = jsonObj.getInt("start");	
			} catch (Exception e) {
				_logger.error("分页传参出错,使用默认分页参数");
			}
			
			Pagination<SubbranchGoodRelationVo> pagination = new Pagination<SubbranchGoodRelationVo>();
			pagination.setCurPage(curPage);
			pagination.setPageSize(pageSize);
			pagination.setStart(start);
			
			pagination = subbranchGoodRelationBusiness.findPageSubbranchGood(pagination, subbranchId, shopNo, type,name);
			
			// 重新封装Pagination对象
			AssPagination<SubbranchGoodRelationVo> newpag = new AssPagination<SubbranchGoodRelationVo>();
			newpag.setCurPage(pagination.getCurPage());
			newpag.setStart(pagination.getEnd());
			newpag.setPageSize(pagination.getPageSize());
			newpag.setTotalRow(pagination.getTotalRow());
			newpag.setDatas(pagination.getDatas());
			return outSucceed(newpag, true, FIND_MY_SUBBRANCHGOODRELATION_FILLTER);
		} catch(SubbranchException e){
			_logger.error("查询分店商品佣金比例数据出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		} catch (Exception e) {
			_logger.error("查询分店商品佣金比例数据出错:" + e.getMessage(), e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, e.getMessage());
		}
	}
}
