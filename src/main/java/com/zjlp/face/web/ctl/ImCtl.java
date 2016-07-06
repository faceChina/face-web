package com.zjlp.face.web.ctl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.im.business.ImMessageBusiness;
import com.zjlp.face.web.server.product.im.business.ImUserBusiness;
import com.zjlp.face.web.server.product.im.domain.ImUser;
import com.zjlp.face.web.server.product.im.domain.dto.ImFriendsDto;
import com.zjlp.face.web.server.product.im.domain.dto.ImMessageDto;
import com.zjlp.face.web.server.product.im.domain.vo.ImUserVo;
import com.zjlp.face.web.server.product.im.util.ImConstantsUtil;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;

@Controller
@RequestMapping("/im/")
public class ImCtl extends BaseCtl {
	
	private Logger _logger = Logger.getLogger("imInfoLog");
	
	@Autowired
	private ImUserBusiness imUserBusiness;
	
	@Autowired
	private ImMessageBusiness imMessageBusiness;
	
	@Autowired
	private UserBusiness userBusiness;
	
	
	@RequestMapping(value="login")
	public String imLogin(Model model) throws Exception{
		String serverDomin = PropertiesUtil.getContexrtParam("im_serverDomin_pc");
		String clusterdomain = PropertiesUtil.getContexrtParam("im_clusterdomain_pc");
		
		if(StringUtils.isBlank(serverDomin) || StringUtils.isBlank(clusterdomain)
				|| "${im_serverDomin_pc}".equals(serverDomin) || "${im_clusterdomain_pc}".equals(clusterdomain)){
			_logger.error("未配置聊天服务器参数");
			throw new Exception("未配置聊天服务器参数");
		}
		Long  userId = getUserId();
		Assert.notNull(userId);
		// 获取用户信息
		User user = userBusiness.getUserById(userId);
		Assert.notNull(user);
		ImUser imUser = new ImUser();
		imUser.setRemoteId(user.getId().toString());
		imUser.setType(ImConstantsUtil.REMOTE_TYPE_SHOP);
		imUser.setUserName(user.getLoginAccount());
		String userPwd = PropertiesUtil.getContexrtParam("IM_INIT_USER_PWD_SHOP");
		imUser.setUserPwd(userPwd);
		imUser.setNickname(user.getNickname());
		ImUserVo imUserVo = imUserBusiness.login(imUser);
		model.addAttribute("imUserVo", imUserVo);
		model.addAttribute("nickName", user.getNickname());
		model.addAttribute("serverDomin", serverDomin.trim());
		model.addAttribute("clusterdomain", clusterdomain.trim());
		return "/m/im/index";
	}
	
	@RequestMapping(value="confirm")
	@ResponseBody
	public String confirmUser(Long userId){
		try {
			imUserBusiness.editImUserStates(userId,ImConstantsUtil.STATES_USER_NORMAL);
			return super.getReqJson(true, "修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return super.getReqJson(true, "修改失败!");
		}
	}
	
	@RequestMapping(value="friends/list")
	@ResponseBody
	public String friendsList(Long imUserId){
		try {
			List<ImFriendsDto> imFriends = imUserBusiness.findFriendsListByUserId(imUserId);
			return super.getReqJson(true, JSONArray.fromObject(imFriends).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return super.getReqJson(false, "查询好友失败!");
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
	
	@RequestMapping(value="message/list")
	@ResponseBody
	public String findImUserMessage(String receiverStr,ImMessageDto imMessageDto,Pagination<ImMessageDto> pagination,Model model){
		try {
			if(0 == imMessageDto.getCurrentTime()){
				imMessageDto.setCurrentTime(new Date().getTime());
			}
			pagination = imMessageBusiness.findImUserMessage(receiverStr,imMessageDto,pagination);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("pagination", pagination);
			map.put("imMessageDto", imMessageDto);
			return super.getReqJson(true,JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return super.getReqJson(false,"聊天记录保存失败");
		}
	}
}
