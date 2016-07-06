package com.zjlp.face.web.ctl.wap;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.http.filter.SubbranchFilter;
import com.zjlp.face.web.server.operation.subbranch.business.SubbranchBusiness;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.product.good.business.GoodBusiness;
import com.zjlp.face.web.server.product.good.domain.vo.GoodDetailVo;
import com.zjlp.face.web.server.product.im.business.ImMessageBusiness;
import com.zjlp.face.web.server.product.im.business.ImUserBusiness;
import com.zjlp.face.web.server.product.im.domain.ImUser;
import com.zjlp.face.web.server.product.im.domain.dto.ImMessageDto;
import com.zjlp.face.web.server.product.im.domain.vo.ImUserVo;
import com.zjlp.face.web.server.product.im.util.ImConstantsUtil;
import com.zjlp.face.web.server.user.shop.bussiness.ShopBusiness;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
@Controller
@RequestMapping("/wap/{shopNo}/im/")
public class ImWapCtl extends WapCtl{
	
	private Logger _logger = Logger.getLogger("imInfoLog");
	
	@Autowired
	private ImUserBusiness imUserBusiness;
	
	@Autowired
	private ImMessageBusiness imMessageBusiness;
	
	@Autowired
	private GoodBusiness goodBusiness;
	
	@Autowired
	private UserBusiness userBusiness;
	
	@Autowired
	private ShopBusiness shopBusiness;
	@Autowired
	private SubbranchBusiness subbranchBusiness;
	
	@RequestMapping(value="login")
	public String imLogin(@PathVariable String shopNo,Long goodDetailId, HttpSession session,HttpServletRequest request,Model model) throws Exception{
		if(null != goodDetailId){
			GoodDetailVo goodDetailVo = goodBusiness.getGoodDetailById(Long.valueOf(goodDetailId));
			StringBuffer sendurl = new StringBuffer();
			sendurl.append("/wap/").append(shopNo).append("/any/item/").append(goodDetailId).append(EXT);
			model.addAttribute("sendurl", sendurl);
			model.addAttribute("good", goodDetailVo.getGood());
			session.removeAttribute("goodDetailId");
		}
		Long userId = getUserId();
		
		// 获取session 里是否有分店信息，有 则跟分店店主聊天，没有则跟总店聊天
		// 获取session 里是否有分店信息，有 则跟分店店主聊天，没有则跟总店聊天
		String obj = SubbranchFilter.getSubbranchId(request.getSession());
		String managedAccount = null;
		String headpic=null;
		if (StringUtils.isNotEmpty(obj)) {
			// 获取分店信息
			Long subId = Long.valueOf(obj.toString());
			Subbranch sub = subbranchBusiness.findSubbranchById(subId);
			User subbranchUser = userBusiness.getUserById(sub.getUserId());
			managedAccount = subbranchUser.getLoginAccount();
			headpic = subbranchUser.getHeadimgurl();
		}else {
			// 总店信息
			Shop shop = shopBusiness.getShopByNo(shopNo);
			User managedUser = userBusiness.getUserById(shop.getUserId());
			managedAccount = managedUser.getLoginAccount();
			headpic = managedUser.getHeadimgurl();
		}
		if(StringUtils.isNotBlank(headpic)){
			model.addAttribute("headpic", headpic);
		}
		model.addAttribute("managedAccount", managedAccount);
		
		ImUser imUser =  null;
		String serverDomin = null;
		String clusterdomain = null;
		if (null != userId){
			User user = userBusiness.getUserById(userId);
			if (null != user) {
				model.addAttribute("meheadpic", user.getHeadimgurl());
				serverDomin = PropertiesUtil.getContexrtParam("im_serverDomin_wap");
				clusterdomain = PropertiesUtil.getContexrtParam("im_clusterdomain_wap");
				if(StringUtils.isBlank(serverDomin) || StringUtils.isBlank(clusterdomain)
						|| "${im_serverDomin_wap}".equals(serverDomin) || "${im_clusterdomain_wap}".equals(clusterdomain)){
					_logger.error("未配置聊天服务器参数");
					throw new Exception("未配置聊天服务器参数");
				}
				imUser = new ImUser();
				imUser.setRemoteId(user.getId().toString());
				imUser.setType(ImConstantsUtil.REMOTE_TYPE_PERSONAL);
				imUser.setUserName(user.getLoginAccount());
				imUser.setUserPwd(PropertiesUtil.getContexrtParam("IM_INIT_USER_PWD_WAP"));
				imUser.setNickname(user.getNickname());
				ImUserVo imUserVo = imUserBusiness.login(imUser);
				User u = userBusiness.getUserById(userId);
				if (u != null && User.REGISTERTYPE_WECHAT.equals(u.getRegisterSourceType())) {
					imUserVo.setUserName(imUser.getNickname());
				}
				model.addAttribute("imUserVo", imUserVo);
				model.addAttribute("serverDomin", serverDomin.trim());
				model.addAttribute("clusterdomain", clusterdomain.trim());
				model.addAttribute("currentTime", new Date().getTime());
				return "/wap/im/phone";
			}
		}
		//匿名用户登录
		serverDomin = PropertiesUtil.getContexrtParam("im_serverDomin_anonymous");
		clusterdomain = PropertiesUtil.getContexrtParam("im_clusterdomain_anonymous");
		if(StringUtils.isBlank(serverDomin) || StringUtils.isBlank(clusterdomain)
				|| "${im_serverDomin_anonymous}".equals(serverDomin) || "${im_clusterdomain_anonymous}".equals(clusterdomain)){
			_logger.error("未配置聊天服务器参数");
			throw new Exception("未配置聊天服务器参数");
		}
		imUserBusiness.loginAnonymout(session);
		model.addAttribute("serverDomin", serverDomin.trim());
		model.addAttribute("clusterdomain", clusterdomain.trim());
		return "/wap/im/phoneAnonymous";
	}
	
	/**
	 * 
	 * @Title: cardChat 
	 * @Description: 跟名片主人聊天
	 * @param request
	 * @param model
	 * @param toChatUserId
	 * @param session
	 * @return
	 * @date 2015年9月10日 上午10:38:35  
	 * @author cbc
	 */
	@RequestMapping("cardChat/{toChatUserId}")
	public String cardChat(HttpServletRequest request,Model model, @PathVariable Long toChatUserId, HttpSession session) throws Exception {
		Long userId = getUserId();
		User toCharUser = userBusiness.getUserById(toChatUserId);
		String managedAccount = toCharUser.getLoginAccount();
		String headpic=toCharUser.getHeadimgurl();
		if(StringUtils.isNotBlank(headpic)){
			model.addAttribute("headpic", headpic);
		}
		model.addAttribute("managedAccount", managedAccount);
		ImUser imUser =  null;
		String serverDomin = null;
		String clusterdomain = null;
		if (null != userId){
			User user = userBusiness.getUserById(userId);
			if (null != user) {
				model.addAttribute("meheadpic", user.getHeadimgurl());
				serverDomin = PropertiesUtil.getContexrtParam("im_serverDomin_wap");
				clusterdomain = PropertiesUtil.getContexrtParam("im_clusterdomain_wap");
				if(StringUtils.isBlank(serverDomin) || StringUtils.isBlank(clusterdomain)
						|| "${im_serverDomin_wap}".equals(serverDomin) || "${im_clusterdomain_wap}".equals(clusterdomain)){
					_logger.error("未配置聊天服务器参数");
					throw new Exception("未配置聊天服务器参数");
				}
				imUser = new ImUser();
				imUser.setRemoteId(user.getId().toString());
				imUser.setType(ImConstantsUtil.REMOTE_TYPE_PERSONAL);
				imUser.setUserName(user.getLoginAccount());
				imUser.setUserPwd(PropertiesUtil.getContexrtParam("IM_INIT_USER_PWD_WAP"));
				imUser.setNickname(user.getNickname());
				ImUserVo imUserVo = imUserBusiness.login(imUser);
				model.addAttribute("imUserVo", imUserVo);
				model.addAttribute("serverDomin", serverDomin.trim());
				model.addAttribute("clusterdomain", clusterdomain.trim());
				model.addAttribute("currentTime", new Date().getTime());
				return "/wap/im/phone";
			}
		}
		//匿名用户登录
		serverDomin = PropertiesUtil.getContexrtParam("im_serverDomin_anonymous");
		clusterdomain = PropertiesUtil.getContexrtParam("im_clusterdomain_anonymous");
		if(StringUtils.isBlank(serverDomin) || StringUtils.isBlank(clusterdomain)
				|| "${im_serverDomin_anonymous}".equals(serverDomin) || "${im_clusterdomain_anonymous}".equals(clusterdomain)){
			_logger.error("未配置聊天服务器参数");
			throw new Exception("未配置聊天服务器参数");
		}
		imUserBusiness.loginAnonymout(session);
		model.addAttribute("serverDomin", serverDomin.trim());
		model.addAttribute("clusterdomain", clusterdomain.trim());
		return "/wap/im/phoneAnonymous";
		
	}
	
/*	@RequestMapping(value="anonymous")
	public String anonymous(@PathVariable String shopNo,HttpSession session,Model model) throws Exception{
		
		return "/app/im/phoneAnonymous";
	}*/
	
	public String getHistory(){
		
		return null;
	}
	
	@RequestMapping(value="message/list")
	@ResponseBody
	public String findImUserMessage(String receiverStr,ImMessageDto imMessageDto,Pagination<ImMessageDto> pagination){
		try {
			pagination = imMessageBusiness.findImUserMessage(receiverStr,imMessageDto,pagination);
			return super.getReqJson(true,JSONObject.fromObject(pagination).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return super.getReqJson(false,"聊天记录保存失败");
		}
	}
	
	@RequestMapping(value="message/save")
	@ResponseBody
	public String saveImMessage(Long sender,String receiver,String message){
		try {
			imMessageBusiness.sendMessage(sender, receiver, message);
			return super.getReqJson(true,"聊天记录保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			return super.getReqJson(false,"聊天记录保存失败");
		}
	}
}
