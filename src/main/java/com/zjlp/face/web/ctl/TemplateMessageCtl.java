package com.zjlp.face.web.ctl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.weixin.bussiness.TemplateMessageBusiness;
import com.zjlp.face.web.server.user.weixin.domain.TemplateMessage;
import com.zjlp.face.web.server.user.weixin.domain.TemplateMessageSetting;

@Controller
@RequestMapping("/u/weixin/templateMessage/")
public class TemplateMessageCtl extends BaseCtl {
	
	@Autowired
	private TemplateMessageBusiness templateMessageBusiness;
	
	private Log log = LogFactory.getLog(getClass());
	
	@RequestMapping("manage")
	public String list(Model model) {
		
		// 根据店铺编号查询模板消息设置
		TemplateMessageSetting templateMessageSetting = templateMessageBusiness.
				gettemplateMessageSetting(super.getShopNo());
		model.addAttribute("templateMessageSetting", templateMessageSetting);
		return "/m/user/weixin/template/message-module";
	}
	
	@RequestMapping("changeStatus")
	@ResponseBody
	public String changeStatus(TemplateMessage templateMessage, Model model) {
		try {
			// 编辑状态
			Shop shop = super.getShop();
			if(null != shop && !Constants.AUTHENTICATE_TYPE_CERTIFIED.equals(
					shop.getAuthenticate())) {
				return super.getReqJson(false, "请绑定已认证的服务号,只有认证后的服务号才可以申请模板消息的使用权限。");
				}
			templateMessage.setShopNo(super.getShopNo());
			templateMessageBusiness.editTemplateMessage(templateMessage);
			return super.getReqJson(true, "修改状态成功");
		} catch (Exception e) {
			log.error("切换状态失败", e);
			return super.getReqJson(false, "切换状态失败");
		}
	}
}
