package com.zjlp.face.web.server.user.weixin.bussiness.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.shop.service.ShopExternalService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.WechatException;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.service.ShopService;
import com.zjlp.face.web.server.user.weixin.bussiness.TemplateMessageBusiness;
import com.zjlp.face.web.server.user.weixin.domain.TemplateMessage;
import com.zjlp.face.web.server.user.weixin.domain.TemplateMessageSetting;
import com.zjlp.face.web.server.user.weixin.service.TemplateMessageService;

@Service
public class TemplateMessageBusinessImpl implements TemplateMessageBusiness{

	@Autowired
	private TemplateMessageService templateMessageService;
	
	@Autowired
	private ShopExternalService shopExternalService;
	
	@Autowired ShopService shopService;
	
	@Override
	public TemplateMessageSetting gettemplateMessageSetting(String shopNo) throws WechatException{
		
		try {
			AssertUtil.notNull(shopNo, "店铺编号为空");
			// 根据店铺查询模板消息设置
			return templateMessageService.getTemplateMessageSettingByShopNo(shopNo);
		} catch (Exception e) {
			throw new WechatException(e);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void editTemplateMessage(TemplateMessage templateMessage) throws WechatException{
		
		try {
			
			// 查询店铺模板消息个数
			Integer count = templateMessageService.getTemplateMessageCountByShopNo(
					templateMessage.getShopNo());
			Shop shop = shopService.getShopByNo(templateMessage.getShopNo());
			String accessToken = shopExternalService.getAccessToken(shop.getAppId(), shop.getAppSecret());
			// 没有模板消息时，先设置行业信息
			if(null == count || 0 == count) {
				// 设置行业信息
				shopExternalService.setIndustry(accessToken);
			}
			// 编辑模板消息设置
			templateMessageService.editTemplateMessageSetting(
					_generateTemplateMessageSetting(templateMessage.getShopNo(), templateMessage));
			// 编辑或新增模板消息
			TemplateMessage newTemplateMessage = new TemplateMessage();
			newTemplateMessage.setShopNo(templateMessage.getShopNo());
			newTemplateMessage.setType(templateMessage.getType());
			newTemplateMessage = templateMessageService.getTemplateMessage(newTemplateMessage);
			if(null == newTemplateMessage) {
				// 获取模板消息id
				String templateId = shopExternalService.getTemplateId(templateMessage.getType(), accessToken);
				templateMessage = _generateTemplateMessage(templateId, templateMessage, shop);
				templateMessageService.addTemplateMessage(templateMessage);
				AssertUtil.notNull(templateMessage.getId(), "新增模板消息失败");
			} else {
				newTemplateMessage.setStatus(templateMessage.getStatus());
				newTemplateMessage.setType(templateMessage.getType());
				templateMessageService.editTemplateMessage(newTemplateMessage);
			}
		} catch (Exception e) {
			throw new WechatException(e);
		}
		
	}

	private TemplateMessage _generateTemplateMessage(String templateId,
			TemplateMessage templateMessage, Shop shop) {
		Date date = new Date();
		templateMessage.setTemplateId(templateId);
		templateMessage.setShopNo(shop.getNo());
		templateMessage.setAppId(shop.getAppId());
		templateMessage.setCreateTime(date);
		templateMessage.setUpdateTime(date);
		return templateMessage;
	}

	// 组装模板消息设置
	private TemplateMessageSetting _generateTemplateMessageSetting(
			String shopNo, TemplateMessage templateMessage) {
		TemplateMessageSetting templateMessageSetting = new TemplateMessageSetting();
		templateMessageSetting.setShopNo(shopNo);
		templateMessageSetting.setUpdateTime(new Date());
		if(null != templateMessage.getType() && 1 == templateMessage.getType()) {
			templateMessageSetting.setMemberCardSwitch(templateMessage.getStatus());
		} else if(null != templateMessage.getType() && 2 == templateMessage.getType()) {
			templateMessageSetting.setOrderStatusSwitch(templateMessage.getStatus());
		} else if(null != templateMessage.getType() && 3 == templateMessage.getType()) {
			templateMessageSetting.setCommissionSwitch(templateMessage.getStatus());
		}
		
		return templateMessageSetting;
	}

}
