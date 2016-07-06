package com.zjlp.face.web.server.user.weixin.bussiness;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.WechatException;
import com.zjlp.face.web.server.user.weixin.domain.MessageContent;
import com.zjlp.face.web.server.user.weixin.domain.MessageSetting;
import com.zjlp.face.web.server.user.weixin.domain.ToolSetting;
import com.zjlp.face.web.server.user.weixin.domain.dto.MessageSettingDto;

/**
 * 微信业务接口
 * @ClassName: WechatBusiness 
 * @Description: (微信业务接口) 
 * @author ah
 * @date 2014年8月5日 下午3:06:52
 */
public interface WechatBusiness {
	
	/**
	 * 根据店铺查询回复消息设置
	 * @Title: getToolSettingByShopNo 
	 * @Description: (根据店铺查询回复消息设置) 
	 * @return
	 * @date 2015年3月23日 下午7:39:18  
	 * @author ah
	 */
	ToolSetting getToolSettingByShopNo(String shopNo);
	
	/**
	 * 编辑回复消息状态
	 * @Title: editToolSetting 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param type
	 * @param status
	 * @param toolSetting
	 * @date 2015年3月23日 下午7:43:41  
	 * @author ah
	 */
	void editToolSetting(Integer type, Integer status, ToolSetting toolSetting);

	/**
	 * 新增消息
	 * @Title: addMessage 
	 * @Description: (新增消息) 
	 * @param weixinItem
	 * @param msDto
	 * @date 2014年8月7日 下午4:07:49  
	 * @author ah
	 */
	void addMessage(String weixinItem, MessageSettingDto msDto) throws WechatException;

	/**
	 * 编辑消息
	 * @Title: editMessage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param weixinItem
	 * @param msDto
	 * @date 2014年8月8日 下午5:30:47  
	 * @author ah
	 */
	void editMessage(String weixinItem, MessageSettingDto msDto);
	
	/**
	 * 查询消息设置分页列表
	 * @Title: findMessageSettingPageList 
	 * @Description: (查询消息设置分页列表) 
	 * @param dto
	 * @param pagination
	 * @return
	 * @date 2014年8月5日 下午3:24:01  
	 * @author ah
	 */
	Pagination<MessageSettingDto> findMessageSettingPageList(MessageSettingDto dto,
			Pagination<MessageSettingDto> pagination) throws WechatException;
	
	/**
	 * 根据消息主体id查询消息内容
	 * @Title: findMessageContentByMessageBodyId 
	 * @Description: (根据消息主体id查询消息内容) 
	 * @param messageBodyId
	 * @return
	 * @date 2014年8月8日 下午3:21:02  
	 * @author ah
	 */
	List<MessageContent> findMessageContentByMessageBodyId(Long messageBodyId);
	
	/**
	 * 根据主键查询消息内容
	 * @Title: getMessageContentById 
	 * @Description: (根据主键查询消息内容) 
	 * @param id
	 * @return
	 * @date 2014年8月6日 下午2:39:03  
	 * @author Administrator
	 */
	MessageContent getMessageContentById(Long id);
	
	/**
	 * 查询消息设置
	 * @Title: getMessageSettingId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2014年8月8日 下午3:57:05  
	 * @author ah
	 */
	MessageSetting getMessageSettingId(Long id);
	
	/**
	 * 逻辑删除消息设置
	 * @Title: removeMessageSetting 
	 * @Description: (逻辑删除消息设置) 
	 * @param id
	 * @throws WechatException
	 * @date 2014年8月5日 下午5:38:46  
	 * @author ah
	 */
	void removeMessageSetting(Long id) throws WechatException;

	/**
	 * 删除消息内容
	 * @Title: deleteMessageContent 
	 * @Description: (删除消息内容) 
	 * @param id
	 * @date 2014年8月11日 下午8:35:01  
	 * @author ah
	 */
	void deleteMessageContent(Long id);

	/**
	 * 根据事件类型查询消息设置
	 * @Title: getMessageSetting 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param eventType
	 * @return
	 * @date 2015年3月27日 下午3:48:47  
	 * @author ah
	 */
	MessageSetting getMessageSetting(MessageSetting messageSetting);

}
