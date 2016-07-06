package com.zjlp.face.web.ctl.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.appvo.AssPagination;
import com.zjlp.face.web.server.product.template.bussiness.TemplateBusiness;
import com.zjlp.face.web.server.trade.order.domain.SalesOrder;
import com.zjlp.face.web.server.user.customer.business.CustomerBusiness;
import com.zjlp.face.web.server.user.customer.domain.AppCustomer;
import com.zjlp.face.web.server.user.customer.domain.AppGroup;
import com.zjlp.face.web.server.user.customer.domain.dto.CustomerDetailDto;
import com.zjlp.face.web.server.user.customer.domain.dto.GroupDetail;
import com.zjlp.face.web.server.user.customer.domain.vo.AppCustomerVo;
import com.zjlp.face.web.server.user.customer.domain.vo.MyCustomerVo;
import com.zjlp.face.web.server.user.shop.bussiness.AssShopBusiness;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.AssConstantsUtil;
import com.zjlp.face.web.util.ConvertUtil;


/**
* 客户管理
* @author Baojiang Yang
* @date 2015年6月25日 上午10:20:32
*
*/ 
@Controller
@RequestMapping({ "/assistant/ass/customer/" })
public class CustomerAppCtl extends BaseCtl {

	private static final String DEFAULT_SORT_CODE = "0";

	private static final String COMMA = ",";

	public static final String ALL_NUMBER = "^[0-9]*$";

	public static final String SEMICOLON = ";";

	public static final String COLON = ":";

	private Logger _logger = Logger.getLogger(getClass());
	
	public static final String[] MYCUSTOMER_JSON_FIELDS = { "curPage", "start", "pageSize",
			"totalRow", "datas.customerId", "datas.picture", "datas.customerName", "datas.tradeNo",
			"datas.totalPriceStr", "datas.latestTime" ,"datas.cell"};

	public static final String[] CUSTOMER_DETAIL_JSON_FIELDS = { "id", "customerId", "customerName",
			"birthday", "cell", "address", "weChat", "tag", "appGroupList.id",
			"appGroupList.groupName", "remark", "customerTradeQuantity", "costomerTradePrice"};

	public static final String[] CUSTOMER_GROUPL_JSON_FIELDS = { "id", "name" };

	public static final String[] GROUPL_MANAGERMENT_JSON_FIELDS = { "id", "name", "type", "sort", "ungrouped", "groupDetails.id",
			"groupDetails.customerId", "groupDetails.picture", "groupDetails.customerName", "groupDetails.remarkName",
			"groupDetails.loginAccount" };

	public static final String[] MYSTAFF_JSON_FIELDS = { "curPage", "start", "pageSize",
			"totalRow", "datas.customerId", "datas.hearderPicture", "datas.customerName", "datas.tradeNo",
			"datas.totalPriceStr", "datas.customerQuantity" };

	public static final String[] MY_CUTSOMER_LIST_FIELDS = { "id", "customerId", "nickOrRemark", "headImgUrl" };

	public static final String[] SHOP_AND_SUBBRANCH_FIELDS = { "curPage", "start", "pageSize", "totalRow", "allOrder.tradeNo",
			"allOrder.totalPriceStr", "datas.role", "datas.subbranchId", "datas.shopNo", "datas.shopName", "datas.shopLogoUrl",
			"datas.totalPriceStr", "datas.tradeNo" };

	@Autowired
	private CustomerBusiness customerBusiness;
	@Autowired
	private TemplateBusiness templateBusiness;
	@Autowired
	private ShopBusiness shopBusiness;
	@Autowired
	private AssShopBusiness assShopBusiness;
	@Autowired
	private UserBusiness userBusiness;

	/** 客户管理：获取用户的客户列表 (总店和分店) **/
	@RequestMapping(value = "customerList")
	@ResponseBody
	public String customerList(@RequestBody JSONObject jsonObj, Pagination<MyCustomerVo> pagination)
			throws Exception {
		try {
			// 检测提交的参数
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String orderBy = jsonObj.optString("orderBy");
			if (StringUtils.isEmpty(orderBy)) {
				orderBy = DEFAULT_SORT_CODE;
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);
			String customerName = null; //客户名称，将用来进行模糊查询
			if (jsonObj.containsKey("customerName")) {
				customerName = jsonObj.optString("customerName");
			}
			//店铺类型 1:总店 2:分店
			Integer shopType = null;
			Object shopTypeObj = jsonObj.get("shopType");
			if (shopTypeObj != null && StringUtils.isNotBlank(shopTypeObj.toString())) {
				shopType = Integer.parseInt(shopTypeObj.toString());
			} else {
				return outFailure(AssConstantsUtil.CustomerCode.NULL_SHOP_TYPE_CODE);
			}
			// 排除一个用户多个店铺或者没有店铺的脏数据
			List<Shop> shopList = this.shopBusiness.findShopListByUserId(super.getUserId());
			if (CollectionUtils.isEmpty(shopList) || shopList.size() > 1) {
				_logger.info("该账号下有多个店铺或者没有店铺，脏数据！");
				return outFailure(AssConstantsUtil.CustomerCode.DIRTY_SHOP_ERROR_CODE);
			}
			// 查询客户
			pagination = customerBusiness.getMyCustomerByUserId(shopType, super.getUserId(), customerName, orderBy, pagination);
			List<MyCustomerVo> myCustomerList = pagination.getDatas();
			// 重新封装Pagination对象
			AssPagination<MyCustomerVo> newpag = new AssPagination<MyCustomerVo>();
			newpag.setCurPage(pagination.getCurPage());
			newpag.setStart(pagination.getEnd());
			newpag.setPageSize(pagination.getPageSize());
			newpag.setTotalRow(pagination.getTotalRow());
			newpag.setDatas(myCustomerList);
			return outSucceed(newpag, true, MYCUSTOMER_JSON_FIELDS);
		} catch (Exception e) {
			_logger.error("查询客户列表失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/** 客户详情：获取用户的客户详情 **/
	@RequestMapping(value = "customerDetails")
	@ResponseBody
	public String customerDetails(@RequestBody JSONObject jsonObj) throws Exception {
		try {
			// 检测提交的参数
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// userId
			Object customerIdobj = jsonObj.get("customerId");
			if (customerIdobj == null) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			Long customerId = Long.parseLong(customerIdobj.toString());
			// 查询客户
			CustomerDetailDto myCustomerDetail= customerBusiness.getCustomerDetailByIds(super.getUserId(), customerId);
			return outSucceed(myCustomerDetail, true, CUSTOMER_DETAIL_JSON_FIELDS);
		} catch (Exception e) {
			_logger.error("查询客户详情失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/** 客户管理：获取用户的分组列表，不含组内详情 **/
	@RequestMapping(value = "customerGroupList")
	@ResponseBody
	public String customerGroupList(@RequestBody JSONObject jsonObj) {
		try {
			// 检测提交的参数
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			/** 类型 , 0：客户 , 1:朋友 , 2：标签 **/
			Object typeObj = jsonObj.get("type");
			Integer type = null;
			if (typeObj != null && StringUtils.isNotBlank(typeObj.toString())) {
				type = Integer.parseInt(typeObj.toString());
			} else {
				return outFailure(AssConstantsUtil.CustomerCode.NULL_TYPE_ERROR_CODE, "");
			}
			Object userIdObj = jsonObj.get("userId");
			Long userId = null;
			if (userIdObj != null && StringUtils.isNotBlank(userIdObj.toString())) {
				userId = Long.parseLong(userIdObj.toString());
			}
			if (userId == null) {
				userId = super.getUserId();
			}
			List<AppGroup> customerGroupList = this.customerBusiness.getMyCustomerGroupList(userId, type);
			return outSucceed(customerGroupList, true, CUSTOMER_GROUPL_JSON_FIELDS);
		} catch (Exception e) {
			_logger.error("客户分组列表查询失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/** 一个客户（或者多个）分配一个（或者多个）分组，包括新分组 **/
	/**
	 * @param salesOrder
	 * @param jsonObj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "groupManagement")
	@ResponseBody
	public String groupManagement(SalesOrder salesOrder, @RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			/** 类型 , 0：客户 , 1:朋友 , 2：标签 **/
			Object typeObj = jsonObj.get("type");
			Integer type = null;
			if (typeObj != null && StringUtils.isNotBlank(typeObj.toString())) {
				type = Integer.parseInt(typeObj.toString());
			} else {
				return outFailure(AssConstantsUtil.CustomerCode.NULL_TYPE_ERROR_CODE, "");
			}
			Object userIdObj = jsonObj.get("userId");
			Long userId = null;
			if (userIdObj != null && StringUtils.isNotBlank(userIdObj.toString())) {
				userId = Long.parseLong(userIdObj.toString());
			}
			if (userId == null) {
				userId = super.getUserId();
			}
			// 客户IDs,可以为空,为空时为增加空分组
			String customersStr = jsonObj.optString("customerList");
			List<Long> goalCustomerIds = new ArrayList<Long>();
			if (StringUtils.isNotEmpty(customersStr)) {
//				return outFailure(AssConstantsUtil.CustomerCode.NO_COSTOMER_ERROR_CODE, "");
//			} else {
				// 检查客户是否存在 ,根据主键查到数据说明存在，并且USER_ID等于当前客用户说明是我的客户
				goalCustomerIds = ConvertUtil.splitString(customersStr, COMMA);
				if (type == 0) {
					for (Long id : goalCustomerIds) {
						AppCustomer existCustomer = this.customerBusiness.getMyAppCustomer(id, userId, type);
						if (existCustomer == null || existCustomer.getId() == null) {
							return outFailure(AssConstantsUtil.CustomerCode.NO_COSTOMER_ERROR_CODE, "");
						}
					}
				}
			}
			// 分组ID,多个分组用','链接,可以为空
			String groupsStr = jsonObj.optString("groupList");// str="1,2,3,4,5";
			List<Long> goalGroupIds = new ArrayList<Long>();
			// 检查分组是否合法(性能较差时可以去除验证)
			if (StringUtils.isNotBlank(groupsStr)) {
				// 存在性和包含性验证
				goalGroupIds = ConvertUtil.splitString(groupsStr, COMMA);
				List<AppGroup> existGroups = this.customerBusiness.getMyCustomerGroupList(userId, type);
				List<Long> existGroupIds = new ArrayList<Long>();
				for (AppGroup current : existGroups) {
					Long id = current.getId();
					if (id != null) {
						existGroupIds.add(id);
					}
				}
				// 如{1,2,3,4}containsAll{1,2,3}为true
				if (!existGroupIds.containsAll(goalGroupIds)) {
					return outFailure(AssConstantsUtil.CustomerCode.NO_CONTAIN_ERROR_CODE, "");
				}
			}
			String groupName = jsonObj.optString("groupName");// 新分组名
			Map<String, Object> dataMap = new HashMap<String, Object>();
			Long code = null;
			/** 客户详情： 一个客户分配到多个(或者一个)分组，新分组可选 **/
			if (goalCustomerIds.size() == 1 && goalGroupIds.size() >= 1) {
				code = this.customerBusiness.addCustomerToGroups(userId, goalCustomerIds.get(0), goalGroupIds, groupName, type);
				// goalCustomerIds.size() >= 1 &&
			} else if (goalGroupIds.size() == 0 && StringUtils.isNotBlank(groupName)) {
				/** 多个客户分配到一个分组,即新建立分组，分组名不为空 **/
				code = this.customerBusiness.addCustomersToGroup(userId, goalCustomerIds, null, groupName, type);
			} else {
				/** 不符合分组条件 **/
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			if (code != null && code < 0) {
				if(code.equals(-2211L)){
					return outFailure(AssConstantsUtil.CustomerCode.EXIST_GROUP_ERROR_CODE, "");
//					dataMap.put("errorCode", AssConstantsUtil.CustomerCode.EXIST_GROUP_ERROR_CODE);
				}else{
					dataMap.put("errorCode", code);
					return outSucceed(dataMap, false, "");
				}
			} else {
				return outSucceedByNoData();
			}
		} catch (Exception e) {
			_logger.error("分配客户到新分组(新分组)失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	
	/** 获取用户的分组列表 包含分组内详细信息 **/
	@RequestMapping(value = "groupListAndDetails")
	@ResponseBody
	public String groupListAndDetails(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			/** 类型 , 0：客户 , 1:朋友 , 2：标签 **/
			Object typeObj = jsonObj.get("type");
			Integer type = null;
			if (typeObj != null && StringUtils.isNotBlank(typeObj.toString())) {
				type = Integer.parseInt(typeObj.toString());
			} else {
				return outFailure(AssConstantsUtil.CustomerCode.NULL_TYPE_ERROR_CODE, "");
			}
			Object userIdObj = jsonObj.get("userId");
			Long userId = null;
			if (userIdObj != null && StringUtils.isNotBlank(userIdObj.toString())) {
				userId = Long.parseLong(userIdObj.toString());
			}
			if (userId == null) {
				userId = super.getUserId();
			}
			List<GroupDetail> groupDeatils = new ArrayList<GroupDetail>();
			List<AppGroup> customerGroupList = this.customerBusiness.getMyCustomerGroupList(userId, type);
			for (AppGroup group : customerGroupList) {
				GroupDetail groupDetail = new GroupDetail();
				// 分组权重不能为空
				if (group.getSort() == null) {
					return outFailure(AssConstantsUtil.CustomerCode.NULL_SORT_ERROR_CODE, "");
				} else {
					groupDetail.setSort(group.getSort());
				}
				// 分组ID
				groupDetail.setId(group.getId());
				// 类型
				groupDetail.setType(group.getType());
				// 分组名称
				groupDetail.setName(group.getName());
				// 未分组表示位
				groupDetail.setUngrouped(group.getUngrouped());
				/** 分组内客户信息 **/
				AppCustomer appCustomer = new AppCustomer(type);
				// 查询条件
				appCustomer.setId(group.getId());// 当前用户的分组ID
				appCustomer.setUserId(userId);// 当前用户ID
				// 组内客户信息
				List<AppCustomer> customerList = new ArrayList<AppCustomer>();
				if (group.getUngrouped() == 1 && type == 0) {
					// "未分组"组内客户信息
					List<AppCustomerVo> ungroupCustomers = this.customerBusiness.findUngroupCustomer(userId, type);
					if (CollectionUtils.isNotEmpty(ungroupCustomers)) {
						for (AppCustomerVo vo : ungroupCustomers) {
							AppCustomer customer = new AppCustomer();
							customer.setId(vo.getId());
							customer.setCustomerId(vo.getCustomerId());
							customer.setPicture(vo.getHeadImgUrl());
							customer.setCustomerName(this._dealString(vo.getCustomerName()));
							customer.setRemarkName(this._dealString(vo.getRemarkName()));
							customer.setLoginAccount(vo.getLoginAccount());
							customerList.add(customer);
						}
					}
				} else {
					// 普通分组组内客户信息
					customerList = this.customerBusiness.getCurrentGroupCustomer(appCustomer);
				}
				groupDetail.setGroupDetails(customerList);
				groupDeatils.add(groupDetail);
		    }
			// 分组按照权重排序
			Collections.sort(groupDeatils, new Comparator<GroupDetail>() {
				@Override
				public int compare(GroupDetail o1, GroupDetail o2) {
					if (null == o1 && null == o2) {
						return 0;
					}
					if (null == o1) {
						return 1;
					}
					if (null == o2) {
						return -1;
					}
					return o1.getSort().compareTo(o2.getSort());
				}
			});
			return outSucceed(groupDeatils, true, GROUPL_MANAGERMENT_JSON_FIELDS);
		} catch (Exception e) {
			_logger.error("客户分组列表查询失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	
	/**
	 * @Title: _dealString
	 * @Description: (处理null字符串，防止转成json时成为"null")
	 * @param str
	 * @return
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月17日 上午11:56:45
	 */
	private String _dealString(String str) {
		return StringUtils.isNotBlank(str) ? str : StringUtils.EMPTY;
	}

	/** 更新分组内客户信息，删除或者新增后的组内客户Idlist以及改组Id **/
	@RequestMapping(value = "updateGroupMemberAndName")
	@ResponseBody
	public String updateGroupMemberAndName(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			/** 类型 , 0：客户 , 1:朋友 , 2：标签 **/
			Object typeObj = jsonObj.get("type");
			Integer type = null;
			if (typeObj != null && StringUtils.isNotBlank(typeObj.toString())) {
				type = Integer.parseInt(typeObj.toString());
			} else {
				return outFailure(AssConstantsUtil.CustomerCode.NULL_TYPE_ERROR_CODE, "");
			}
			Object userIdObj = jsonObj.get("userId");
			Long userId = null;
			if (userIdObj != null && StringUtils.isNotBlank(userIdObj.toString())) {
				userId = Long.parseLong(userIdObj.toString());
			}
			if (userId == null) {
				userId = super.getUserId();
			}
			Object groupIdObj = jsonObj.get("groupId");
			if (groupIdObj == null) {
				return outFailure(AssConstantsUtil.CustomerCode.NULL_GROUP_ID_ERROR_CODE, "");
			}
			// 更新分组ID
			Long groupId = Long.parseLong(groupIdObj.toString());
			/** 更新分组名start **/
			if (jsonObj.containsKey("groupName")) {
				String groupName = jsonObj.optString("groupName");
				if (StringUtils.isBlank(groupName)) {
					return outFailure(AssConstantsUtil.CustomerCode.NULL_GROUP_NAME_CODE, "");
				}
				AppGroup appGroup = new AppGroup(type);
				appGroup.setId(groupId);
				appGroup.setName(groupName);
				appGroup.setUserId(userId);
				Long resultCode = this.customerBusiness.updateAppGroup(appGroup);
				if (resultCode < 0) {
					if(resultCode.equals(-2211L)){
						return outFailure(AssConstantsUtil.CustomerCode.EXIST_GROUP_ERROR_CODE, "");
					}
					return outFailure(AssConstantsUtil.CustomerCode.NULL_GROUP_ERROR_CODE, "");
				}
			}
			/** 更新分组名end **/
			// 组内客户ID,多个分组用','链接，为空则表示删除了组内全部客户，不为空则表示更新以后的组内客户ID,不传表示不更新
			if (jsonObj.containsKey("customerList")) {
				String customersStr = jsonObj.optString("customerList");// str="1,2,3,4,5";
				// 返回分组内信息：分组ID，分组名，以及一个组内客户List
				GroupDetail groupDeatils = new GroupDetail();
				groupDeatils.setId(groupId);
				// 更新后的组内客户IdList
				List<Long> updatedCustomerIds = new ArrayList<Long>();
				if (StringUtils.isNotBlank(customersStr)) {
					updatedCustomerIds = ConvertUtil.splitString(customersStr, COMMA);
					// 查出当前用户下所有分组
					List<AppGroup> customerGroupList = this.customerBusiness.getMyCustomerGroupList(userId, type);
					// 找到正在更新的该分组
					for (AppGroup group : customerGroupList) {
						if (group != null && groupId.equals(group.getId())) {
							/** 分组内客户信息 **/
							AppCustomer appCustomer = new AppCustomer(type);
							// 查询条件 关系表中组ID
							appCustomer.setId(group.getId());
							// 查询条件 分组表中用用户ID
							appCustomer.setUserId(userId);
							// 原有组内客户信息
							List<AppCustomer> originalAppCustomer = this.customerBusiness.getCurrentGroupCustomer(appCustomer);
							List<Long> originalCustomerIds = new ArrayList<Long>();
							// 拿到组内原始数据主键做比较
							for (AppCustomer customer : originalAppCustomer) {
								originalCustomerIds.add(customer.getId());
							}
							// 无变化
							if (originalCustomerIds.size() == updatedCustomerIds.size() && originalCustomerIds.containsAll(updatedCustomerIds)) {
								groupDeatils.setGroupDetails(originalAppCustomer);
								return outSucceed(groupDeatils, true, GROUPL_MANAGERMENT_JSON_FIELDS);
							} else {
								// 更新了客户
								this.customerBusiness.addCurrentGroupCustomer(userId, originalCustomerIds, group.getId(),updatedCustomerIds, type);
								appCustomer = new AppCustomer(type);
								// 查询条件 当前用户
								appCustomer.setUserId(userId);
								// 查询条件 组外键
								appCustomer.setId(group.getId());
								// 组内客户信息
								List<AppCustomer> afterUpdate = this.customerBusiness.getCurrentGroupCustomer(appCustomer);
								groupDeatils.setGroupDetails(afterUpdate);
								return outSucceedByNoData();
							}
						}
					}
				} else {
					// 删除组内所有客户
					this.customerBusiness.addCurrentGroupCustomer(userId ,null, groupId, null, type);
				}
				return outSucceed(groupDeatils, true, GROUPL_MANAGERMENT_JSON_FIELDS);
			} else {
				_logger.info("没有要更新的成员！");
				return outSucceedByNoData();
			}
		} catch (Exception e) {
			_logger.error("更新分组失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: updateGroupName
	 * @Description: (更新组名)
	 * @param jsonObj
	 * @return
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 下午3:29:01
	 */
	@RequestMapping(value = "updateGroupName")
	@ResponseBody
	public String updateGroupName(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			/** 类型 , 0：客户 , 1:朋友 , 2：标签 **/
			Object typeObj = jsonObj.get("type");
			Integer type = null;
			if (typeObj != null && StringUtils.isNotBlank(typeObj.toString())) {
				type = Integer.parseInt(typeObj.toString());
			} else {
				return outFailure(AssConstantsUtil.CustomerCode.NULL_TYPE_ERROR_CODE, "");
			}
			Object userIdObj = jsonObj.get("userId");
			Long userId = null;
			if (userIdObj != null && StringUtils.isNotBlank(userIdObj.toString())) {
				userId = Long.parseLong(userIdObj.toString());
			}
			if (userId == null) {
				userId = super.getUserId();
			}
			Object groupIdObj = jsonObj.get("groupId");
			if (groupIdObj == null) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 更新分组ID
			Long groupId = Long.parseLong(groupIdObj.toString());
			String groupName = jsonObj.optString("groupName");
			if (StringUtils.isBlank(groupName)) {
				return outFailure(AssConstantsUtil.CustomerCode.NULL_GROUP_NAME_CODE, "");
			}
			AppGroup appGroup = new AppGroup(type);
			appGroup.setId(groupId);
			appGroup.setName(groupName);
			Long resultCode = this.customerBusiness.updateAppGroup(appGroup);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if (resultCode < 0) {
				dataMap.put("errorCode", resultCode);
				return outSucceed(dataMap, false, "");
			}
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("更新组名失败失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: updateGroupSort
	 * @Description: (更新分组排序)
	 * @param jsonObj
	 * @return
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月12日 上午8:36:08
	 */
	@RequestMapping(value = "updateGroupSort")
	@ResponseBody
	public String updateGroupSort(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			/** 类型 , 0：客户 , 1:朋友 , 2：标签 **/
			Object typeObj = jsonObj.get("type");
			Integer type = null;
			if (typeObj != null && StringUtils.isNotBlank(typeObj.toString())) {
				type = Integer.parseInt(typeObj.toString());
			} else {
				return outFailure(AssConstantsUtil.CustomerCode.NULL_TYPE_ERROR_CODE, "");
			}
			// 用户ID
			Object userIdObj = jsonObj.get("userId");
			Long userId = null;
			if (userIdObj != null && StringUtils.isNotBlank(userIdObj.toString())) {
				userId = Long.parseLong(userIdObj.toString());
			}
			if (userId == null) {
				userId = super.getUserId();
			}
			// 分组排序权重 groupSortList": [2,1,5,4,3,0]
			JSONArray groupSortList = jsonObj.getJSONArray("groupSortList");
			if (CollectionUtils.isEmpty(groupSortList)) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			} else {
				this.customerBusiness.updateAppGroupSort(groupSortList.toArray(), userId, type);
				return outSucceedByNoData();
			}
		} catch (Exception e) {
			_logger.error("更新组名失败失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: removeGroup
	 * @Description: (删除组)
	 * @param jsonObj
	 * @return
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 下午3:28:04
	 */
	@RequestMapping(value = "removeGroup")
	@ResponseBody
	public String removeGroup(@RequestBody JSONObject jsonObj){
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			/** 类型 , 0：客户 , 1:朋友 , 2：标签 **/
			Object typeObj = jsonObj.get("type");
			Integer type = null;
			if (typeObj != null && StringUtils.isNotBlank(typeObj.toString())) {
				type = Integer.parseInt(typeObj.toString());
			} else {
				return outFailure(AssConstantsUtil.CustomerCode.NULL_TYPE_ERROR_CODE, "");
			}
			Long userId = null;
			if (jsonObj.containsKey("userId")) {
				Object userIdObj = jsonObj.get("userId");
				if (userIdObj != null && StringUtils.isNotBlank(userIdObj.toString())) {
					userId = Long.parseLong(userIdObj.toString());
				}
			}
			if (userId == null) {
				userId = super.getUserId();
			}
			Object groupIdObj = jsonObj.get("groupId");
			if (groupIdObj == null) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 删除分组ID
			Long groupId = Long.parseLong(groupIdObj.toString());
			// 删除失败后回滚
			String customerIdsStr = jsonObj.optString("customerList");
			List<Long> rollBackCustomerIds = new ArrayList<Long>();
			if (StringUtils.isNotBlank(customerIdsStr)) {
				rollBackCustomerIds = ConvertUtil.splitString(customerIdsStr, COMMA);
			}
			if (this._checkPersion(userId, type, groupId)) {// 权限检查，只能删除自己的分组
				// 删除分组
				this.customerBusiness.removeCustomerGroup(rollBackCustomerIds, groupId, type);
				return outSucceedByNoData();
			} else {
				return outFailure(AssConstantsUtil.CustomerCode.NO_PERSION_ERROR_CODE, "");
			}
		} catch (Exception e) {
			_logger.error("删除分组失败！", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: _checkPersion
	 * @Description: (检查要删除的分组是否是我的分组)
	 * @param userId
	 * @param type
	 * @param groupId
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月11日 下午5:35:42
	 */
	private boolean _checkPersion(Long userId, Integer type, Long groupId) {
		List<AppGroup> customerGroupList = this.customerBusiness.getMyCustomerGroupList(userId, type);
		if (CollectionUtils.isNotEmpty(customerGroupList)) {
			for (AppGroup current : customerGroupList) {
				if (current.getId().longValue() == groupId.longValue()) {
					return true;
				}
			}
		}
		return false;
	}


	/** 员工管理：获取用户的客户列表 **/
	@RequestMapping(value = "staffManagement")
	@ResponseBody
	public String staffDataManagement(@RequestBody JSONObject jsonObj, Pagination<MyCustomerVo> pagination) throws Exception {
		try {
			// 检测提交的参数
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			String orderBy = jsonObj.optString("orderBy");
			if (StringUtils.isEmpty(orderBy)) {
				orderBy = DEFAULT_SORT_CODE;
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);
			// 店铺ID
			String shopNo = jsonObj.optString("shopNo");
			pagination = customerBusiness.findStaffCount(shopNo, orderBy, pagination);
			if (pagination != null) {
				List<MyCustomerVo> results = new ArrayList<MyCustomerVo>();
				for (MyCustomerVo current : pagination.getDatas()) {
					// 计算总价
					current.setTotalPriceStr(CalculateUtils.converFenToYuan(current.getPayPrice() + current.getPostFree()));
					// 获取员工头像
					User staff = this.userBusiness.getUserById(current.getCustomerId());
					current.setHearderPicture(staff != null ? staff.getHeadimgurl() : null);
					results.add(current);
				}
				pagination.getDatas().clear();
				pagination.setDatas(results);
			}
			
			// 重新封装Pagination对象
			AssPagination<MyCustomerVo> newpag = new AssPagination<MyCustomerVo>();
			if (pagination != null) {
				newpag.setCurPage(pagination.getCurPage());
				newpag.setStart(pagination.getEnd());
				newpag.setPageSize(pagination.getPageSize());
				newpag.setTotalRow(pagination.getTotalRow());
				newpag.setDatas(pagination.getDatas());
			}
			return outSucceed(newpag, true, MYSTAFF_JSON_FIELDS);
		} catch (Exception e) {
			_logger.error("查询员工失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: updateCustomerRemark
	 * @Description: (修改客户两种备注)
	 * @param jsonObj
	 * @return
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月29日 上午8:43:20
	 */
	@RequestMapping(value = "updateCustomerRemark")
	@ResponseBody
	public String updateCustomerRemark(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 客户ID,此ID不是userId
			Long customerId = null;
			Object customerIdObj = jsonObj.get("id");
			if (customerIdObj != null && StringUtils.isNotBlank(customerIdObj.toString())) {
				customerId = Long.parseLong(customerIdObj.toString());
			} else {
				return outFailure(AssConstantsUtil.CustomerCode.NULL_COSTOMER_ID_CODE, "");
			}
			/** 兼容老用户不加type校验 **/
//			Object typeObj = jsonObj.get("type");
//			Integer type = null;
//			if (typeObj != null && StringUtils.isNotBlank(typeObj.toString())) {
//				type = Integer.parseInt(typeObj.toString());
//			} else {
//				return outFailure(AssConstantsUtil.CustomerCode.NULL_TYPE_ERROR_CODE, "");
//			}
			/** 购物喜好备注 **/
			String remark = null;
			if (jsonObj.containsKey("remark")) {
				Object remarkObj = jsonObj.get("remark");
				if (remarkObj != null && StringUtils.isNotBlank(remarkObj.toString())) {
					remark = remarkObj.toString();
				}
			}
			/** 客户列表备注名 **/
			String remarkName = null;
			if (jsonObj.containsKey("remarkName")) {
				Object remarkNameObj = jsonObj.get("remarkName");
				if (remarkNameObj != null && StringUtils.isNotBlank(remarkNameObj.toString())) {
					remarkName = remarkNameObj.toString();
				}
			}
			AppCustomer appCustomer = new AppCustomer();
			appCustomer.setId(customerId);
//			appCustomer.setType(type);
			appCustomer.setRemark(remark);
			appCustomer.setRemarkName(remarkName);
			this.customerBusiness.updateAppCustomer(appCustomer);
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("更新客户标签信息失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

	/**
	 * @Title: shopAndSubList
	 * @Description: (页统计客户在我的总店、分店、历史分下订单)
	 * @param jsonObj
	 * @param pagination
	 * @return
	 * @throws Exception
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月25日 下午8:34:00
	 */
	@RequestMapping(value = "shopAndSubList")
	@ResponseBody
	public String shopAndSubList(@RequestBody JSONObject jsonObj, Pagination<MyCustomerVo> pagination) throws Exception {
		try {
			// 检测提交的参数
			if (null == jsonObj || jsonObj.isEmpty()) {
				return outFailure(AssConstantsUtil.System.TYPE_ERROR_CODE, "");
			}
			// 分页信息
			pagination = super.initPagination(jsonObj);
			Object userIdObj = jsonObj.get("userId");
			Long userId = null;
			if (userIdObj != null && StringUtils.isNotBlank(userIdObj.toString())) {
				userId = Long.parseLong(userIdObj.toString());
			}
			if (userId == null) {
				userId = super.getUserId();
			}
			Object customerIdObj = jsonObj.get("customerId");
			Long customerId = null;
			if (customerIdObj != null && StringUtils.isNotBlank(customerIdObj.toString())) {
				customerId = Long.parseLong(customerIdObj.toString());
			} else {
				return outFailure(AssConstantsUtil.CustomerCode.NULL_COSTOMER_ID_CODE);
			}
			// 排除一个用户多个店铺或者没有店铺的脏数据
			List<Shop> shopList = this.shopBusiness.findShopListByUserId(userId);
			if (CollectionUtils.isEmpty(shopList) || shopList.size() > 1) {
				_logger.info("该账号下有多个店铺或者没有店铺，脏数据！");
				return outFailure(AssConstantsUtil.CustomerCode.DIRTY_SHOP_ERROR_CODE);
			}
			// 查询客户购买过的店铺
			Pagination<MyCustomerVo> pag = new Pagination<MyCustomerVo>();
			pag = this.customerBusiness.findShopAndSubOrder(userId, customerId, pagination);
			// 如果是第一页,统计所有店铺，第一页以后不再请求
			MyCustomerVo allOrder = new MyCustomerVo();
			Integer start = jsonObj.optInt("start");
			if (start.intValue() == 0) {
				allOrder = this.customerBusiness.countAllMyShopOrder(userId, customerId);
			}
			// Pagination对象放进Map
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("curPage", pag.getCurPage());
			dataMap.put("start", pag.getEnd());
			dataMap.put("pageSize", pag.getPageSize());
			dataMap.put("totalRow", pag.getTotalRow());
			dataMap.put("datas", pag.getDatas());
			if (start.intValue() == 0) {
				dataMap.put("allOrder", allOrder);
			}
			return outSucceed(dataMap, true, SHOP_AND_SUBBRANCH_FIELDS);
		} catch (Exception e) {
			_logger.error("查询客户购买店铺列表失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}

}
