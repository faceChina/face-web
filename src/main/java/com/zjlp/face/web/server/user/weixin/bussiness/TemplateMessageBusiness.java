package com.zjlp.face.web.server.user.weixin.bussiness;

import com.zjlp.face.web.server.user.weixin.domain.TemplateMessage;
import com.zjlp.face.web.server.user.weixin.domain.TemplateMessageSetting;

/**
 * 模板消息业务接口
 * @ClassName: TemplateMessageBusiness 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author ah
 * @date 2015年4月29日 下午8:36:33
 */
public interface TemplateMessageBusiness {

	/**
	 * 根据店铺编号查询模板消息设置
	 * @Title: gettemplateMessageSetting 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @return
	 * @date 2015年5月16日 上午9:49:28  
	 * @author ah
	 */
	TemplateMessageSetting gettemplateMessageSetting(String shopNo);
	
	/**
	 * 编辑模板消息设置
	 * @Title: editTemplateMessage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param templateMessageSetting
	 * @date 2015年5月16日 上午10:12:41  
	 * @author ah
	 */
	void editTemplateMessage(TemplateMessage templateMessage);

}
