package com.zjlp.face.web.ctl.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.constants.Bank;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.AppCustomerException;
import com.zjlp.face.web.exception.ext.PhoneDeviceException;
import com.zjlp.face.web.security.bean.WgjUser;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.product.im.business.ImUserBusiness;
import com.zjlp.face.web.server.product.im.domain.ImUser;
import com.zjlp.face.web.server.product.im.domain.vo.ImUserVo;
import com.zjlp.face.web.server.product.phone.business.PhoneBusiness;
import com.zjlp.face.web.server.product.phone.domain.PhoneDevice;
import com.zjlp.face.web.server.product.phone.producer.PhoneDeviceProducer;
import com.zjlp.face.web.server.trade.order.bussiness.OperateDataBusiness;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.trade.order.bussiness.SubBranchSaleOrderBusiness;
import com.zjlp.face.web.server.user.customer.business.CustomerBusiness;
import com.zjlp.face.web.server.user.customer.domain.AppGroup;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopSubbranchDto;
import com.zjlp.face.web.server.user.user.business.UserAppBusiness;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.AssConstantsUtil;
import com.zjlp.face.web.util.ImConstantsUtil;
import com.zjlp.face.web.util.ResultCode;
/**
 * 
* @ClassName: LoginAssistantCtl
* @Description: 登录后首页
* @author wxn
* @date 2014年12月19日 下午1:03:07
*
 */
@Controller
@RequestMapping({ "/assistant/" })
public class LoginAssistantCtl extends BaseCtl {
	
	Logger _logger = Logger.getLogger(getClass());
	
	private static final String UN_GROUP_NAME = "未分组";

	private final Lock lock = new ReentrantLock();

	private static final String[] SEARCH_MY_SHOP_FILLTER = { "headImgUrl", "name", "nickName", "lpNo", "userId", "wechat", "signature","superPhone",
			"areaCode", "sex", "myInvitationCode", "myInvitationCodeUrl", "circlePictureUrl", "hasFreeShop", "addShopResult", "isBeenSubbranch", "isBeenSubErrorCode", "Imdata.clusterDomain",
			"Imdata.imUserId", "Imdata.password", "Imdata.register", "Imdata.serverDomin", "Imdata.serverDominPort",
			"Imdata.userName", "myShopList.subbrachId", "myShopList.no", "myShopList.role", "myShopList.paidOrderNo",
			"myShopList.paidPrice", "myShopList.liveProfit", "myShopList.deliverOrder", "myShopList.shopLogoUrl",
			"myShopList.name", "myShopList.shareLink", "myShopList.shopName", "myShopList.permission","hasHistorySubbranch",
			"castgc","QiniuToken", "favoritesUrl",
			"urlBuyerMyOrder","urlBuyerMyBookOrder","urlBuyerMyVipCard","urlBuyerMyShopCart","urlBuyerMyAddress", "myShopList.todayPayCommission", "myShopList.status","individualQRCUrl"};
	

	@Autowired
	private SalesOrderBusiness  salesOrderBusiness;
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private PhoneBusiness phoneBusiness;
	@Autowired
	private ImUserBusiness imUserBusiness;
	@Autowired
	private ShopBusiness shopBusiness;
	@Autowired
	private SubbranchBusiness subbranchBusiness;
	@Autowired
	private UserAppBusiness userAppBusiness;
	@Autowired
	private SubBranchSaleOrderBusiness subBranchSaleOrderBusiness;
	@Autowired
	private OperateDataBusiness operateDataBusiness;
	@Autowired
	private PhoneDeviceProducer phoneDeviceProducer;
	@Autowired
	private CustomerBusiness customerBusiness;

	@RequestMapping(value="/ass/login")
	@ResponseBody
	public String loginDefeated() {
		return super.outFailure(AssConstantsUtil.UserCode.LOGIN_ERROR_CODE, "");
	}
	
	/**
	 * 
	* @Title: login
	* @Description: (登录后查询首页数据)
	* @param model
	* @param request
	* @param response
	* @return String    返回类型
	* @throws
	* @author wxn  
	* @date 2014年12月11日 下午3:34:32
	 */
	@RequestMapping(value="/ass/index",method = RequestMethod.POST)
	@ResponseBody
	public String index(HttpServletRequest request,HttpServletResponse response) {
			// 查询用户
			User user = userBusiness.getUserById(getUserId());
			String headImgUrl = null == user.getHeadimgurl()?"":user.getHeadimgurl();
			Map<String,Object> dataMap = new HashMap<String,Object>();
			//当日订单成交总额
			dataMap.put("headImgUrl", headImgUrl);
			dataMap.put("name", user.getContacts());
			String nickName = null ==user.getNickname()?"":user.getNickname();
			dataMap.put("nickName",nickName);
			dataMap.put("userId",user.getId());
			dataMap.put("wechat", user.getWechat() == null ?"":user.getWechat());
			dataMap.put("signature", user.getSignature() == null ?"":user.getSignature());
		    dataMap.put("areaCode", user.getAreaCode() == null ? 0 : user.getAreaCode());
		    dataMap.put("sex", user.getSex() == null ? 0 : user.getSex());
		    dataMap.put("circlePictureUrl", StringUtils.isNotBlank(user.getCirclePictureUrl()) ? user.getCirclePictureUrl() : StringUtils.EMPTY);
		    dataMap.put("lpNo", user.getLpNo() == null ? "":user.getLpNo());
		    dataMap.put("myInvitationCode", user.getMyInvitationCode());
		    String wgjurl = PropertiesUtil.getContexrtParam("WGJ_URL");
		    dataMap.put("myInvitationCodeUrl", wgjurl+"/any/code/invite.htm?userId="+user.getId());
		    boolean hasFreeShop = true;
		    Shop shop = null;
		    String favoritesUrl = null;
			try {
				shop = shopBusiness.getShopByUserId(user.getId());
				if (null == shop ) {
					hasFreeShop = false;
				}
				if (shop != null) {
				   favoritesUrl = new StringBuilder("/wap/").append(shop.getNo()).append("/buy/personal/favorites/1/1.htm").toString();
				}
			} catch (Exception e) {
				_logger.error("登陆查询免费店铺失败!",e);
			}
			dataMap.put("favoritesUrl", favoritesUrl);
		    dataMap.put("hasFreeShop",hasFreeShop);
			//超管聊天先屏蔽
			Map<String,Object> ImdataMap = imLogin();
			dataMap.put("Imdata",ImdataMap==null? "":ImdataMap);
			//检查是否有官网，没有添加官网以兼容老用户
		    List<ShopDto> shopList = shopBusiness.findShopList(user.getId(), null, null);
		    if (CollectionUtils.isEmpty(shopList)) {
			   String addShopResult = addShopForOlduser(user.getLoginAccount());
			   dataMap.put("addShopResult", addShopResult);
		    }
			//检查当前用户是否已经成为别人的分店
		    List<Subbranch> subList = this.subbranchBusiness.findSubbranchByUserId(user.getId());
		    boolean isBeenSubbranch = false;
		    if (CollectionUtils.isNotEmpty(subList) && subList.size() == 1) {
		    	isBeenSubbranch = true;
				/** 已经成为分店，查找上级分店号码用于聊天 **/
				User superUser = this.userBusiness.getUserById(subList.get(0).getSuperiorUserId());
				dataMap.put("superPhone", superUser != null ? superUser.getLoginAccount() : StringUtils.EMPTY);
		    }
		    if (CollectionUtils.isNotEmpty(subList) && subList.size() > 1) {
		    	isBeenSubbranch = true;
		    	dataMap.put("isBeenSubErrorCode", "查到当前用户多次成为别人的分店，脏数据！");
		    }
		    dataMap.put("isBeenSubbranch", isBeenSubbranch);
		    //登陆查询我的店铺信息(总店、分店)
		    List<ShopSubbranchDto> myShopList = this._searchMyShops(user.getId());
		    dataMap.put("myShopList", myShopList);
		    //  是否有历史分店
		    boolean hasSubbranch =  subbranchBusiness.checkExistAsHistorySub(user.getId());
		    dataMap.put("hasHistorySubbranch", hasSubbranch);
		    /**单点登录需用字段  start**/
			String ticketId = (String)request.getAttribute("ticketId");
			if (StringUtils.isNotBlank(ticketId)) {
				dataMap.put("castgc", ticketId);
			}
			
		    dataMap.put("QiniuToken", "");
		    //设置个人二维码分享链接
		    dataMap.put("individualQRCUrl", wgjurl+Constants.INDIVIDUAL_QRC_URL.replace(Constants.USER_ID, user.getId().toString()));
		    /**单点登录需用字段  end**/
		    //设置我是卖家页需要用的URL
		    this._setUrl(dataMap, user.getId());
		    // 给每一位用户初始化一个朋友和好友的"未分组"
		    this._initUserGroup(user.getId(), user.getLoginAccount());
			return outSucceed(dataMap,true, SEARCH_MY_SHOP_FILLTER);
	}
	
	/**
	 * @Title: initUserGroup
	 * @Description: (给每一位用户初始化一个朋友和客户的"未分组")
	 * @param id
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月13日 上午11:34:21
	 */
	private void _initUserGroup(Long userId, String loginAccount) {
		// 两个分组，至少查两次
		for (int i = 0; i < 2; i++) {
			List<AppGroup> ungroups = this.customerBusiness.getUngroups(userId, i);
			_logger.info(new StringBuilder("查询用户[").append(userId).append("]未分组type=[").append(i).append("]userId=[")
					.append(userId).append("]").toString());
			// 先查是否已经添加，否则不进锁块
			if (CollectionUtils.isEmpty(ungroups) || ungroups.size() == 0) {
				try {
					AssertUtil.notNull(userId, "用户id不能为空！");
					AssertUtil.notNull(i, "用户type不能为空！");
					this._addUngroup(i, userId);
				} catch (Exception e) {
					_logger.error("用户" + userId + "初始化分组失败！");
					throw new AppCustomerException(e.getMessage(), e);
				}
			} else if (CollectionUtils.isNotEmpty(ungroups) && ungroups.size() > 1) {
				_logger.info("用户" + userId + "未分组脏数据,即将进行数据还原...");
				this._roolBackUngroup(userId);
			}
			// size() == 1符合条件,继续循环
		}
	}

	/**
	 * @Title: _roolBackUngroup
	 * @Description: (对未分组初始化失败重新进行分组)
	 * @param userId
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月27日 下午8:05:04
	 */
	private synchronized void _roolBackUngroup(Long userId) {
		try {
			// 对可能存在的脏数据进行删除,避免登陆失败
			this.customerBusiness.removeUngroups(userId);
			// 重新新增未分组
			for (int j = 0; j < 2; j++) {
				this._addUngroup(j, userId);
			}
		} catch (Exception e) {
			_logger.error("未分组数据还原失败！", e);
		}
	}

	/**
	 * @Title: _addUngroup
	 * @Description: (添加未分组)
	 * @param i
	 * @param userId
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年8月27日 下午4:55:11
	 */
	private void _addUngroup(int i, Long userId) {
		lock.lock();// 上锁
		try {
			AppGroup appGroup = new AppGroup();
			appGroup.setType(i);
			appGroup.setUserId(userId);
			appGroup.setName(UN_GROUP_NAME);
			appGroup.setSort(-1);
			appGroup.setUngrouped(1);// 未分组标志位
			appGroup.setCreateTime(new Date());
			appGroup.setUpdateTime(new Date());
			Long ungroupId = this.customerBusiness.addAppGroup(appGroup);
			_logger.info("用户：" + userId + "成功创建默认未分组" + ungroupId);
		} catch (Exception e) {
			_logger.error("用户" + userId + "初始化分组失败！");
			throw new AppCustomerException(e.getMessage(), e);
		} finally {
			lock.unlock();// 释放锁
		}

	}

	/**
	 * @Title: addShopForOlduser
	 * @Description: (给用户增加一个官网)
	 * @param loginAccount
	 * @return
	 * @return String 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月29日 上午9:39:32
	 */
	private String addShopForOlduser(String loginAccount) {
		JSONObject jsonShop = userAppBusiness.activateShopLock(loginAccount);
		if ("ok".equals(jsonShop.optString(ResultCode.SUCCESS))) {
			try {
				jsonShop.remove(ResultCode.SUCCESS);
			} catch (Exception e) {
			}
			return outSucceed(jsonShop, false, "");
		} else if ("登录账户不能为空".equals(jsonShop.optString(ResultCode.LOGINACCOUNT_NO_NULL))) {
			return outFailure(Integer.parseInt(ResultCode.LOGINACCOUNT_NO_NULL), "");
		} else if (jsonShop.optString(ResultCode.ACTIVATE_GW_SHOP_FAIL).startsWith("生成官网失败:")) {
			return outFailure(Integer.parseInt(ResultCode.ACTIVATE_GW_SHOP_FAIL), "");
		} else {
			return outFailure(Integer.parseInt(ResultCode.ACTIVATE_GW_SHOP_FAIL), "");
		}
	}
	
	/**
	 * 
	* @Title: logout
	* @Description: (退出管家助手)
	* @param model
	* @param request
	* @param response
	* @return String    返回类型
	* @throws
	* @author wxn
	* @date 2014年12月11日 下午3:36:06
	 */
	@RequestMapping(value="/ass/logout")
	@ResponseBody
	public String logout(@RequestBody JSONObject jsonObj,HttpServletRequest request) {
		
		try { 
			if(null != jsonObj){
				 String pushUserId = jsonObj.getString("pushUserId");
				 if (null !=pushUserId && !"".equals(pushUserId)){
				        //退出登录后解除push绑定
					PhoneDevice phoneDevice = new PhoneDevice();
					phoneDevice.setPushUserId(pushUserId);
					phoneDevice.setUserId(super.getUserId());
					@SuppressWarnings("unused")
					int i = phoneDeviceProducer.deleteByPushId(phoneDevice);
				 }
			}
		} catch (Exception e) {
			_logger.error("解除用户pushUserId绑定失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
		
		HttpSession session = request.getSession(false);
		if(null != session){
			session.invalidate();
		}
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(null);
        SecurityContextHolder.clearContext();
        return outSucceedByNoData();
	}
	/**
	 * 保存用户pushUserId
	* @Title: savePushId
	* @Description: (保存用户pushUserId)
	* @param request
	* @param response
	* @return void    返回类型
	* @throws
	* @author wxn  
	* @date 2015年1月6日 下午5:17:29
	 */
	@RequestMapping(value="/ass/savePushId")
	@ResponseBody
	public String savePushId(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj ){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			String pushUserId = jsonObj.getString("pushUserId");
			if (null == pushUserId || "".equals(pushUserId)){
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			Integer deviceType = jsonObj.getInt("deviceType");
			if (null == deviceType) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			String oldPushUserId = jsonObj.getString("oldPushUserId");
			if (null != oldPushUserId && !"".equals(oldPushUserId)){
				// 删除过期pushId
				PhoneDevice phoneDevice = new PhoneDevice();
				phoneDevice.setPushUserId(pushUserId);
				phoneDevice.setUserId(super.getUserId());
				phoneDeviceProducer.deleteByPushId(phoneDevice);

			}
			PhoneDevice phoneDevice = new PhoneDevice();
			phoneDevice.setUserId(getUserId());
			phoneDevice.setType(2);
			phoneDevice.setPushUserId(pushUserId);
			phoneDevice.setStatus(1);
			phoneDevice.setDeviceType(deviceType);
			phoneDevice.setCreateTime(new Date());
			phoneDeviceProducer.addPhoneDevice(phoneDevice);
			return outSucceedByNoData();
		}catch(PhoneDeviceException e){
			_logger.error("保存用户pushUserId失败",e);
			if("该用户已绑定该设备".equals(e.getMessage())){
				return outSucceedByNoData();
			}else{
				return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
			}
			
		}catch (Exception e) {
			_logger.error("保存用户pushUserId失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
	/**
	 * 获取聊天登陆数据及聊天登陆
	* @Title: imLogin
	* @Description: (获取聊天登陆数据及聊天登陆)
	* @param request
	* @param response
	* @return
	* @return Map<String,Object>    返回类型
	* @throws
	* @author wxn  
	* @date 2015年1月13日 上午10:20:27
	 */
	private  Map<String,Object> imLogin(){
		try {
			String serverDomin = PropertiesUtil.getContexrtParam("im_serverDomin_ass");
			String clusterdomain = PropertiesUtil.getContexrtParam("im_clusterdomain_ass");
			String serverDominPort = PropertiesUtil.getContexrtParam("im_serverDomin_port_ass");
			
			if(StringUtils.isBlank(serverDomin) || StringUtils.isBlank(clusterdomain)|| StringUtils.isBlank(serverDominPort)
					|| "${im_serverDomin_ass}".equals(serverDomin) || "${im_clusterdomain_ass}".equals(clusterdomain)
					|| "${im_serverDomin_port_ass}".equals(serverDominPort)){
				_logger.error("未配置聊天服务器参数im_serverDomin_ass,im_clusterdomain_ass,im_serverDomin_port_ass");
					//throw new Exception("未配置聊天服务器参数im_serverDomin_ass,im_clusterdomain_ass");
					return null;
			}
			WgjUser user = getLoginUser();
			Assert.notNull(user);
			ImUser imUser = new ImUser();
			// 超级管理员登陆聊天
				User managedUser = userBusiness.getUserById(getUserId());
				Assert.notNull(managedUser);
				imUser.setRemoteId(managedUser.getId().toString());
				imUser.setType(ImConstantsUtil.REMOTE_TYPE_PERSONAL);
				imUser.setUserName(managedUser.getLoginAccount());
				imUser.setNickname(managedUser.getNickname());
				String userPwd = PropertiesUtil.getContexrtParam("IM_INIT_USER_PWD_WAP");
				imUser.setUserPwd(userPwd);
				// 子账户登陆聊天
			Map<String,Object> dataMap = new HashMap<String,Object>();
			ImUserVo imUserVo = imUserBusiness.login(imUser);
			// 客户端登陆用字段
			dataMap.put("userName", imUserVo.getUserName());
			dataMap.put("password", imUserVo.getPassword());
			dataMap.put("register", imUserVo.getRegister());
			dataMap.put("imUserId", imUserVo.getId());
			dataMap.put("serverDomin", serverDomin);
			dataMap.put("serverDominPort",Integer.valueOf(serverDominPort));
			dataMap.put("clusterDomain",clusterdomain);
			return dataMap;
		} catch (Exception e) {
			_logger.error("聊天登陆失败",e);
			return null;
		}

	}
	
	@RequestMapping(value = "/out/getBankList")
	@ResponseBody
	public String getBankList() {
		try {
			 Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("withdrawCardList",Bank.withdrawCardList);
			 return outSucceed(dataMap, false, "");
		} catch (Exception e) {
			_logger.error("获取银行列表失败",e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE,"");
		}
	}

	/**
	* @Title: _searchMyShops
	* @Description: (查询我的总店和分店信息)
	* @param userId
	* @return
	* @return List<ShopSubbranchDto>    返回类型
	* @throws
	* @author Baojiang Yang  
	* @date 2015年7月13日 下午6:09:10 
	*/
	private List<ShopSubbranchDto> _searchMyShops(Long userId) {
		try {
			AssertUtil.notNull(userId, "param[userId] can't be null.");
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
			return result;
		} catch (Exception e) {
			_logger.error("查询登陆用户的店铺失败！", e);
			return null;
		}
	}
	
	/**
	 * 设置我是卖家URL
	* @Title: setUrl
	* @Description: (设置我是卖家URL)
	* @param dataMap
	* @param userId
	* @return void    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月16日 上午10:42:37
	 */
	private void _setUrl(Map<String,Object> dataMap,Long userId){
		
		List<Shop> shopList = shopBusiness.findShopListByUserId(userId);
		
		if (shopList == null || shopList.size() <= 0){
			dataMap.put("urlBuyerMyOrder", "");
			dataMap.put("urlBuyerMyBookOrder", "");
			dataMap.put("urlBuyerMyVipCard", "");
			dataMap.put("urlBuyerMyShopCart", "");
			dataMap.put("urlBuyerMyAddress", "");
			return;
		}
		
		Shop shop = shopList.get(0);
		//我的订单
		StringBuilder urlBuyerMyOrder = new StringBuilder("/wap/");
		urlBuyerMyOrder.append(shop.getNo()).append("/buy/order/list.htm");
		//我的预约
		StringBuilder urlBuyerMyBookOrder  = new StringBuilder("/wap/");
		urlBuyerMyBookOrder.append(shop.getNo()).append("/buy/order/bookOrder.htm");
		//我的会员卡
		StringBuilder urlBuyerMyVipCard = new StringBuilder("/wap/");
		urlBuyerMyVipCard.append(shop.getNo()).append("/buy/member/cardList.htm?show=true");
		 //我的购物车
		StringBuilder  urlBuyerMyShopCart = new StringBuilder("/wap/");
		urlBuyerMyShopCart.append(shop.getNo()).append("/buy/cart/find.htm?show=false");
		//我的收货地址
		StringBuilder  urlBuyerMyAddress = new StringBuilder("/wap/");
		urlBuyerMyAddress.append(shop.getNo()).append("/buy/address/index.htm");
		
		dataMap.put("urlBuyerMyOrder", urlBuyerMyOrder.toString());
		dataMap.put("urlBuyerMyBookOrder", urlBuyerMyBookOrder.toString());
		dataMap.put("urlBuyerMyVipCard", urlBuyerMyVipCard.toString());
		dataMap.put("urlBuyerMyShopCart", urlBuyerMyShopCart.toString());
		dataMap.put("urlBuyerMyAddress", urlBuyerMyAddress.toString());
		
	}
}
