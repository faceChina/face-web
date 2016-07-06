package com.zjlp.face.web.server.user.weixin.service;

import com.zjlp.face.web.server.user.weixin.domain.TemplateMessage;
import com.zjlp.face.web.server.user.weixin.domain.TemplateMessageSetting;

/**
 * 模板消息基础接口
 * @ClassName: TemplateMessageService 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author ah
 * @date 2015年5月16日 上午10:22:20
 */
public interface TemplateMessageService {

	/**
	 * 根据店铺编号跟类型查询模板消息
	 * @Title: getTemplateMessageByShopNoAndType 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param templateMessage
	 * @return
	 * @date 2015年5月16日 上午10:23:52  
	 * @author ah
	 */
	TemplateMessage getTemplateMessage(TemplateMessage templateMessage);
	
	/**
	 * 新增模板消息
	 * @Title: addTemplateMessage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param templateMessage
	 * @date 2015年5月16日 上午10:25:23  
	 * @author ah
	 */
	void addTemplateMessage(TemplateMessage templateMessage);
	
	/**
	 * 编辑模板消息
	 * @Title: editTemplateMessage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param templateMessage
	 * @date 2015年5月16日 上午10:30:34  
	 * @author ah
	 */
	void editTemplateMessage(TemplateMessage templateMessage);
	
	/**
	 * 新增模板消息设置
	 * @Title: addTemplateMessageSetting 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param templateMessageSetting
	 * @date 2015年5月16日 上午10:26:12  
	 * @author ah
	 */
	void addTemplateMessageSetting(TemplateMessageSetting templateMessageSetting);
	
	/**
	 * 编辑模板消息设置
	 * @Title: editTemplateMessageSetting 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param templateMessageSetting
	 * @date 2015年5月16日 上午10:31:14  
	 * @author ah
	 */
	void editTemplateMessageSetting(TemplateMessageSetting templateMessageSetting);
	
	/**
	 * 根据店铺编号查询模板消息设置
	 * @Title: getTemplateMessageSettingByShopNo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @return
	 * @date 2015年5月16日 上午10:28:59  
	 * @author ah
	 */
	TemplateMessageSetting getTemplateMessageSettingByShopNo(String shopNo);
	
	/**
	 * 根据店铺编号查询模板消息个数
	 * @Title: getTemplateMessageCountByShopNo 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @return
	 * @date 2015年5月16日 下午2:30:41  
	 * @author ah
	 */
	Integer getTemplateMessageCountByShopNo(String shopNo);
	
}
