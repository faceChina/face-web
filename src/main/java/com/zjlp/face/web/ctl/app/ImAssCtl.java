package com.zjlp.face.web.ctl.app;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.appvo.AssImFriendsVo;
import com.zjlp.face.web.appvo.AssPagination;
import com.zjlp.face.web.appvo.AssShopVo;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.product.im.business.ImMessageBusiness;
import com.zjlp.face.web.server.product.im.business.ImUserBusiness;
import com.zjlp.face.web.server.product.im.domain.dto.ImMessageDto;
import com.zjlp.face.web.server.product.template.bussiness.TemplateBusiness;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.AssConstantsUtil;
import com.zjlp.face.web.util.ConvertUtil;
@Controller
@RequestMapping("/assistant/ass/im/")
public class ImAssCtl extends BaseCtl {
	
	
//	private static final String NET_DOMAIN_URL = PropertiesUtil.getContexrtParam("NET_DOMAIN_URL");

	private static final String[] FRIEND_DETAIL_FILTER = { "user.id", "user.headimgurl","user.sex",
			"user.nickname", "user.loginAccount", "user.areaCode", "user.signature", "user.wechat","user.circlePictureUrl",
			"shops.shopNo", "shops.name", "shops.picturePath", "shops.shopUrl", "shops.subbranchId" };

	private Logger _logger = Logger.getLogger("imInfoLog");
	
	@Autowired
	private ImUserBusiness imUserBusiness;
	@Autowired
	private ImMessageBusiness imMessageBusiness;
	
	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private ShopBusiness shopBusiness;
	@Autowired
	private TemplateBusiness templateBusiness;
	@Autowired
	private SubbranchBusiness subbranchBusiness;
	
	/**
	 * 查找历史聊天记录
	* @Title: findImUserMessage
	* @Description:  查找历史聊天记录
	* @param request
	* @param response
	* @param receiverStr
	* @param imMessageDto
	* @param pagination
	* @return String    返回类型
	* @author wxn  
	* @date 2014年12月30日 下午12:33:00
	 */
	@RequestMapping(value="message/list")
	@ResponseBody
	public String findImUserMessage(@RequestBody JSONObject jsonObj,
			ImMessageDto imMessageDto,Pagination<ImMessageDto> pagination){
		try {
			if(0 == imMessageDto.getCurrentTime()){
				imMessageDto.setCurrentTime(new Date().getTime());
			}
			Long sender  = jsonObj.getLong("sender");
			imMessageDto.setSender(sender);
			String receiverStr = jsonObj.getString("receiverStr");
			// 分页信息
			pagination = super.initPagination(jsonObj);

			pagination = imMessageBusiness.findImUserMessage(receiverStr,imMessageDto,pagination);
			//重新封装Pagination对象
			AssPagination<ImMessageDto> newpag = new AssPagination<ImMessageDto>();
			
			newpag.setCurPage(pagination.getCurPage());
			newpag.setStart(pagination.getEnd());
			newpag.setPageSize(pagination.getPageSize());
			newpag.setTotalRow(pagination.getTotalRow());
			newpag.setDatas(pagination.getDatas());
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("message", newpag);
			dataMap.put("imMessageDto", imMessageDto);
			return outSucceed(dataMap, false, "");
		} catch (Exception e) {
			_logger.error("查询聊天记录失败", e);
			return outFailure(AssConstantsUtil.ImCode.IM_SAVE_ERROR_CODE, "");
		}
	}

	/**
	 * 保存发送的消息并添加联系人
	* @Title: saveImMessage
	* @Description: 保存发送的消息
	* @param request
	* @param response
	* @return void    返回类型
	* @author wxn  
	* @date 2014年12月30日 下午12:31:48
	 */
	@RequestMapping(value="message/save")
	@ResponseBody
	public String saveImMessage(@RequestBody JSONObject jsonObj){
		try {
			Long sender  = jsonObj.getLong("sender");
			String receiver = jsonObj.getString("receiver");
			String message = jsonObj.getString("message");
			imMessageBusiness.sendMessage(sender, receiver, message);
			return outSucceedByNoData();
		} catch (Exception e) {
			_logger.error("查询聊天记录失败", e);
			return outFailure(AssConstantsUtil.ImCode.IM_QUERY_MESSAGE_ERROR_CODE, "");
		}
	}
	
	/**
	 * 解析客户端的HTML格式的信息
	* @Title: analysisHtmlMessage
	* @Description: 解析客户端的HTML格式的信息
	* @param request
	* @param response
	* @return void    返回类型
	* @throws
	* @author wxn  
	* @date 2014年12月30日 下午3:54:58
	 */
	@RequestMapping(value="message/analysis")
	@ResponseBody
	public String analysisHtmlMessage(@RequestBody JSONObject jsonObj){
		
		try {
			String data = jsonObj.getString("html");
			Map<String,Object> map = ConvertUtil.analysisHtml(data);
			return outSucceed(map, false, "");
		} catch (Exception e) {
			_logger.error("解析失败", e);
			return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
		}
	}
	
	/**
	 * =================================================
	 * APP 3.0版本 功能
	 * =================================================
	 */
	@RequestMapping(value="friendinfo")
	@ResponseBody
	public String friendinfo(@RequestBody JSONObject jsonObj){
		try {
			//检测提交的参数
			Object[] array = jsonObj.getJSONArray("userName").toArray();
			List<AssImFriendsVo> list = new ArrayList<AssImFriendsVo>();
			for (Object obj : array) {
				AssImFriendsVo friend = new AssImFriendsVo();
				String  str = (String)obj;
				friend.setUserName(str);
					// 查询用户
					User user = userBusiness.getUserByLoginAccount(str);
					if (null != user){
						friend.setNickName(user.getNickname());
						friend.setHeadImgUrl(user.getHeadimgurl());
						friend.setUserid(user.getId());
						friend.setCirclePictureUrl(user.getCirclePictureUrl());
						friend.setCardUrl(new StringBuilder("/app/card/").append(user.getId()).append(".htm").toString());
					}
				list.add(friend);
			}
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("card", list);
			return outSucceed(data, false, "");
		} catch (Exception e) {
			_logger.error("获取好友信息", e);
			return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
		}
	}
	
	/**
	 *  获取好友基本资料
	* @Title: index
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param req_json
	* @return String    返回类型
	* @author wxn  
	* @date 2015年3月31日 下午3:30:11
	 */
	@RequestMapping(value = "friend/details")
	@ResponseBody
	public String getFriendDetails(@RequestBody JSONObject jsonObj) {
		try {
			if (null == jsonObj) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			String userName = jsonObj.getString("userName");
			Object useIdObj = jsonObj.get("userId");
			Long userId = useIdObj != null && StringUtils.isNotBlank(useIdObj.toString()) ? Long.parseLong(useIdObj.toString()) : null;
			if (StringUtils.isEmpty(userName) && userId == null) {
				return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
			}
			User user = null;
			if (StringUtils.isNotBlank(userName) && userId == null) {
				user = userBusiness.getUserByLoginAccount(userName);
			} else if (StringUtils.isEmpty(userName) && userId != null) {
				user = userBusiness.getUserById(userId);
			}
			if (null == user) {
				return outFailure(AssConstantsUtil.ImCode.IM_NO_FIRENDINFO_ERROR_CODE, "");
			}
			List<Shop> list = shopBusiness.findShopListByUserId(user.getId());
			List<AssShopVo> shoplist = new ArrayList<AssShopVo>();
			for (Shop shop : list) {
				AssShopVo vo = new AssShopVo();
				vo.setShopNo(shop.getNo());
				vo.setName(shop.getName());
//				try {
//					OwTemplateDto owTemplateDto = templateBusiness.getCurrentHomePageOwTemplate(shop.getNo(), shop.getType());
//					vo.setPicturePath(NET_DOMAIN_URL + owTemplateDto.getPath());
//				} catch (Exception e) {
//					_logger.info("脏数据，获取店铺模板图失败！", e);
//				}
				vo.setPicturePath(shop.getShopLogoUrl());// 店铺logo
				String shopUrl = new StringBuilder("/wap/").append(shop.getNo()).append("/any/gwscIndex.htm").toString();// 店铺官网地址
				vo.setShopUrl(shopUrl);
				shoplist.add(vo);
			}
			/** 查找好友的分店 */
			List<Subbranch> subList = this.subbranchBusiness.findSubbranchByUserId(user.getId());
			if (CollectionUtils.isNotEmpty(subList) && subList.size() == 1) {
				AssShopVo vo = new AssShopVo();
				vo.setShopNo(null);// 分店ID为Long
				vo.setName(subList.get(0).getShopName());
				if (StringUtils.isNotBlank(subList.get(0).getSupplierShopNo())) {
					Shop shop = this.shopBusiness.getShopByNo(subList.get(0).getSupplierShopNo());
					vo.setPicturePath(shop != null ? shop.getShopLogoUrl() : null);
				}
				String shopUrl = new StringBuilder("/wap/").append(subList.get(0).getSupplierShopNo())
						.append("/any/gwscIndex.htm?subbranchId=").append(subList.get(0).getId()).toString();// 分店地址
				vo.setShopUrl(shopUrl);
				vo.setSubbranchId(subList.get(0).getId());// 分店ID
				shoplist.add(vo);
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("user", user);
			data.put("shops", shoplist);
			return outSucceed(data, true, FRIEND_DETAIL_FILTER);
		} catch (Exception e) {
			_logger.error("获取好友详情失败", e);
			return outFailure(AssConstantsUtil.System.SERVER_ERROR_CODE, "");
		}
	}
}
