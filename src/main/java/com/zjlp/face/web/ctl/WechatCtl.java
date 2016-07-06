package com.zjlp.face.web.ctl;

import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.user.weixin.bussiness.WechatBusiness;
import com.zjlp.face.web.server.user.weixin.domain.MessageContent;
import com.zjlp.face.web.server.user.weixin.domain.MessageSetting;
import com.zjlp.face.web.server.user.weixin.domain.ToolSetting;
import com.zjlp.face.web.server.user.weixin.domain.dto.MessageSettingDto;
import com.zjlp.face.web.server.user.weixin.domain.vo.MessageContentVo;

/**
 * 微信消息控制层
 * @ClassName: WechatCtl 
 * @Description: (微信消息控制层) 
 * @author ah
 * @date 2015年3月23日 下午5:13:07
 */
@Controller
@RequestMapping({"/u/weixin/"})
public class WechatCtl extends BaseCtl {
	
	@Autowired
	private WechatBusiness wechatBusiness;
	
	/**
	 * 回复设置开关显示
	 * @Title: message 
	 * @Description: (回复设置开关显示) 
	 * @param model
	 * @return
	 * @date 2015年3月23日 下午7:37:26  
	 * @author ah
	 */
	@RequestMapping(value="manage")
	public String message(Model model) {
		// 查询消息回复设置
		ToolSetting toolSetting = wechatBusiness.getToolSettingByShopNo(super.getShopNo());
		model.addAttribute("toolSetting", toolSetting);
		return "/m/user/weixin/message";
	}

	/**
	 * 编辑消息回复设置
	 * @Title: changeStatus 
	 * @Description: (编辑消息回复设置) 
	 * @param type
	 * @param status
	 * @param toolSetting
	 * @return
	 * @date 2015年3月24日 上午10:56:21  
	 * @author ah
	 */
	@RequestMapping(value="changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public String changeStatus(Integer type, Integer status, ToolSetting toolSetting) {
		if(1 != status && 0 != status){
            return getReqJson(false, "设置回复状态无效");
        }
        try {
        	toolSetting.setShopNo(super.getShopNo());
        	// 编辑消息回复设置
            wechatBusiness.editToolSetting(type, status, toolSetting);
            return getReqJson(true, null);
		} catch (Exception e) {
			return getReqJson(false, "修改回复状态失败");
		}
	}
	
	/**
	 * 跳转到编辑页面
	 * @Title: editMessage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param messageSetting
	 * @param model
	 * @return
	 * @date 2015年3月24日 下午3:44:29  
	 * @author ah
	 */
	@RequestMapping(value="edit")
	public String editMessage(MessageSetting messageSetting, Model model) {
		// 设置店铺编号
		messageSetting.setShopNo(super.getShopNo());
		messageSetting.setRecoveryMode(null);
		// 查询消息设置
		MessageSetting message = wechatBusiness.getMessageSetting(messageSetting);
		if(null == message) {
			// 默认为多图文
			messageSetting.setRecoveryMode(Constants.MODE_MULTI);
			model.addAttribute("messageSetting", messageSetting);
			return super.getForwardUrl("/u/weixin/messagePage");
		} else {
			model.addAttribute("messageSetting", message);
			// 关键词回复时，跳转到关键词消息回复列表
			if(Constants.ENVNT_TYPE_KEYWORD_RECOVERY.equals(messageSetting.getEventType())) {
				return super.getRedirectUrl("/u/weixin/messageReply");
			} else {
				return super.getForwardUrl("/u/weixin/editMessage");
			}
		}
	}
	
	/**
	 * 关键词回复消息列表
	 * @Title: listMessageSetting 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param dto
	 * @param pagination
	 * @param model
	 * @return
	 * @date 2015年3月24日 下午3:45:29  
	 * @author ah
	 */
	@RequestMapping(value="messageReply")
	public String listMessageSetting(MessageSettingDto dto, Pagination<MessageSettingDto> pagination,
			Model model) {
		//设置店铺编号
		dto.setShopNo(getShopNo());
		//设置回复事件
		dto.setEventType(Constants.ENVNT_TYPE_KEYWORD_RECOVERY);
		//查询消息设置分页列表
		pagination = wechatBusiness.findMessageSettingPageList(dto, pagination);
		model.addAttribute("keyWord", dto.getKeyWord());
		model.addAttribute("pagination", pagination);
		return "/m/user/weixin/message-reply";
	}
	
	/**
	 * 跳转到新增页面
	 * @Title: messagePage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param messageSetting
	 * @param model
	 * @return
	 * @date 2015年3月24日 上午11:23:54  
	 * @author ah
	 */
	@RequestMapping(value="messagePage")
	public String messagePage(MessageSetting messageSetting, Model model) {
		
		model.addAttribute("messageSetting", messageSetting);
		if(Constants.MODE_SINGLE.equals(messageSetting.getRecoveryMode())) {
			//单图文
			return "/m/user/weixin/message-set1";
		} else if(Constants.MODE_MULTI.equals(messageSetting.getRecoveryMode())) {
			//多图文
			return "/m/user/weixin/message-set2";
		} else {
			//文本
			return "/m/user/weixin/message-set3";
		}
	}
	
	/**
	 * 新增回复消息
	 * @Title: addMessage 
	 * @Description: (新增回复消息) 
	 * @param reurl
	 * @param weixinItem
	 * @param msDto
	 * @param model
	 * @return
	 * @date 2015年3月24日 下午3:34:16  
	 * @author ah
	 */
	@RequestMapping(value="addMessage")
	public String addMessage(String weixinItem, MessageSettingDto msDto,Model model) {
		msDto.setShopNo(super.getShopNo());
		msDto.setUserId(super.getUserId());
		wechatBusiness.addMessage(weixinItem, msDto);
		// 关键词回复时，跳转到关键词消息回复列表
		if(Constants.ENVNT_TYPE_KEYWORD_RECOVERY.equals(msDto.getEventType())) {
			return super.getRedirectUrl("/u/weixin/messageReply");
		} else {
			return super.getRedirectUrl("/u/weixin/manage");
		}
	}
	
	/**
	 * 
	 * @Title: edit 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param messageSetting
	 * @param model
	 * @return
	 * @date 2015年3月24日 下午4:36:15  
	 * @author ah
	 */
	@RequestMapping(value="editMessage", method = RequestMethod.GET)
	public String edit(MessageSetting messageSetting, Model model) {
		//查询消息设置
		if(null == messageSetting.getId()) {
			messageSetting.setShopNo(super.getShopNo());
			messageSetting.setRecoveryMode(null);
			messageSetting = wechatBusiness.getMessageSetting(messageSetting);
		} else {
			messageSetting = wechatBusiness.getMessageSettingId(messageSetting.getId());
		}
		//查询消息内容
		List<MessageContent> mContents = wechatBusiness.findMessageContentByMessageBodyId(
				messageSetting.getMessageBodyId());
		List<MessageContentVo> mVos = MessageContentVo._generateVo(mContents);
		JSONArray jsonArray = JSONArray.fromObject(mVos);
		String weixinItem = jsonArray.toString();
		model.addAttribute("messageSetting",messageSetting);
		model.addAttribute("weixinItem", weixinItem);
		model.addAttribute("mContents", mContents);
		
		if(Constants.MODE_SINGLE.equals(messageSetting.getRecoveryMode())) {
			//单图文
			return "/m/user/weixin/message-update1";
		} else if(Constants.MODE_MULTI.equals(messageSetting.getRecoveryMode())) {
			//多图文
			return "/m/user/weixin/message-update2";
		} else {
			//文本
			return "/m/user/weixin/message-update3";
		}
	}
	
	/**
	 * 保存编辑消息回复内容
	 * @Title: editMessage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param reurl
	 * @param weixinItem
	 * @param msDto
	 * @param model
	 * @return
	 * @date 2015年3月24日 下午4:38:04  
	 * @author ah
	 */
	@RequestMapping(value="editMessage",method=RequestMethod.POST)
	public String editMessage(String weixinItem, MessageSettingDto msDto,Model model) {
		msDto.setShopNo(super.getShopNo());
		msDto.setUserId(super.getUserId());
		wechatBusiness.editMessage(weixinItem, msDto);
		// 关键词回复时，跳转到关键词消息回复列表
		if(Constants.ENVNT_TYPE_KEYWORD_RECOVERY.equals(msDto.getEventType())) {
			return super.getRedirectUrl("/u/weixin/messageReply");
		} else {
			return super.getRedirectUrl("/u/weixin/manage");
		}
	}
	
	/**
	 * 删除消息回复设置
	 * @Title: deleteMessage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年3月24日 下午4:37:48  
	 * @author ah
	 */
	@RequestMapping(value="deleteMessage", method=RequestMethod.POST)
	@ResponseBody
	public String deleteMessage(Long id) {
		String result = null;
		try {
			//删除消息
			wechatBusiness.deleteMessageContent(id);
			result = super.getReqJson(true, null);
		} catch (Exception e) {
			result = super.getReqJson(true, "删除回复消息失败");
		}
		return result;
	}
	
	/**
	 * 选择回复模式
	 * @Title: chooseReplyMode 
	 * @Description: (选择回复模式) 
	 * @param messageSetting
	 * @param newRecoveryMode
	 * @param model
	 * @return
	 * @date 2015年3月24日 下午6:32:54  
	 * @author ah
	 */
	@RequestMapping(value="chooseReplyMode")
	public String chooseReplyMode(MessageSetting messageSetting, Model model) {
		// 比较选择的回复模式是否一致
		messageSetting.setShopNo(super.getShopNo());
		MessageSetting setting = null;
		if(!Constants.ENVNT_TYPE_KEYWORD_RECOVERY.equals(
				messageSetting.getEventType())) {
			setting = wechatBusiness.getMessageSetting(messageSetting);
		}
		if(null != setting) {
			return super.getForwardUrl("/u/weixin/editMessage");
		} else {
			return super.getForwardUrl("/u/weixin/messagePage");
		}
	}
	
	/**
	 * 逻辑删除消息设置
	 * @Title: removeMessageSetting 
	 * @Description: (逻辑删除消息设置) 
	 * @param messageSetting
	 * @param model
	 * @return
	 * @date 2014年8月5日 下午8:29:58  
	 * @author ah
	 */
	@RequestMapping(value="remove")
	public String removeMessageSetting(MessageSetting messageSetting, Model model) {
		//逻辑删除消息设置
		wechatBusiness.removeMessageSetting(messageSetting.getId());
		return super.getRedirectUrl("/u/weixin/messageReply");
	}
}
