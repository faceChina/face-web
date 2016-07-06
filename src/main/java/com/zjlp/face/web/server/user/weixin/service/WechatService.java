package com.zjlp.face.web.server.user.weixin.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.user.weixin.domain.MessageBody;
import com.zjlp.face.web.server.user.weixin.domain.MessageContent;
import com.zjlp.face.web.server.user.weixin.domain.MessageSetting;
import com.zjlp.face.web.server.user.weixin.domain.ToolSetting;
import com.zjlp.face.web.server.user.weixin.domain.dto.MessageSettingDto;

/**
 * 微信基础服务接口
 * @ClassName: WechatService 
 * @Description: (微信基础服务接口) 
 * @author ah
 * @date 2014年8月5日 下午6:04:17
 */
public interface WechatService {

	/**
	 * 新增消息主体
	 * @Title: addMessageBody 
	 * @Description: (新增消息主体) 
	 * @param messageBody
	 * @date 2014年8月7日 下午5:21:19  
	 * @author ah
	 */
	void addMessageBody(MessageBody messageBody);
	
	/**
	 * 新增消息内容
	 * @Title: addMessageContent 
	 * @Description: (新增消息内容) 
	 * @param messageContent
	 * @date 2014年8月7日 下午5:24:33  
	 * @author ah
	 */
	void addMessageContent(MessageContent messageContent);
	
	/**
	 * 新增消息设置
	 * @Title: addMessageSetting 
	 * @Description: (新增消息设置) 
	 * @param messageSetting
	 * @date 2014年8月7日 下午5:22:24  
	 * @author ah
	 */
	void addMessageSetting(MessageSetting messageSetting);
	
	/**
	 * 编辑消息主体
	 * @Title: editMessageBody 
	 * @Description: (编辑消息主体) 
	 * @param messageBody
	 * @date 2014年8月8日 下午6:33:18  
	 * @author ah
	 */
	void editMessageBody(MessageBody messageBody);
	
	/**
	 * 编辑消息内容
	 * @Title: editMessageContent 
	 * @Description: (编辑消息内容) 
	 * @param messageContent
	 * @date 2014年8月11日 下午4:13:16  
	 * @author ah
	 */
	void editMessageContent(MessageContent messageContent);

	/**
	 * 编辑消息设置
	 * @Title: editMessageSetting 
	 * @Description: (编辑消息设置) 
	 * @param messageSetting
	 * @date 2014年8月5日 下午6:07:54  
	 * @author ah
	 */
	void editMessageSetting(MessageSetting messageSetting);

	/**
	 * 根据消息主体id查询消息内容
	 * @Title: findMessageContentByMessageBodyId 
	 * @Description: (根据消息主体id查询消息内容) 
	 * @param messageBodyId
	 * @return
	 * @date 2014年8月8日 下午3:23:28  
	 * @author ah
	 */
	List<MessageContent> findMessageContentByMessageBodyId(Long messageBodyId);
	/**
	 * 查询消息设置分类列表
	 * @Title: findMessageSettingPageList 
	 * @Description: (查询消息设置分类列表) 
	 * @param dto
	 * @param pagination
	 * @return
	 * @date 2014年8月5日 下午6:05:09  
	 * @author ah
	 */
	Pagination<MessageSettingDto> findMessageSettingPageList(
			MessageSettingDto dto, Pagination<MessageSettingDto> pagination);
	
	/**
	 * 根据主键查询消息内容
	 * @Title: getMessageContentById 
	 * @Description: (根据主键查询消息内容) 
	 * @param id
	 * @return
	 * @date 2014年8月6日 下午2:43:07  
	 * @author ah
	 */
	MessageContent getMessageContentById(Long id);

	/**
	 * 根据主键查询消息设置
	 * @Title: getMessageSettingById 
	 * @Description: (根据主键查询消息设置) 
	 * @param id
	 * @return
	 * @date 2014年8月8日 下午4:00:52  
	 * @author ah
	 */
	MessageSetting getMessageSettingById(Long id);

	/**
	 * 根据消息主体id删除消息内容
	 * @Title: delMessageContentByMessageBodyId 
	 * @Description: (根据消息主体id删除消息内容) 
	 * @param messageBodyId
	 * @date 2014年8月8日 下午7:19:22  
	 * @author ah
	 */
	void delMessageContentByMessageBodyId(Long messageBodyId);

	/**
	 * 根据主键删除消息内容
	 * @Title: delMessageContentById 
	 * @Description: (根据主键删除消息内容) 
	 * @param id
	 * @date 2014年8月11日 下午8:37:53  
	 * @author ah
	 */
	void delMessageContentById(Long id);

	/**
	 * 逻辑删除消息设置
	 * @Title: removeMessageSetting 
	 * @Description: (逻辑删除消息设置) 
	 * @param messageSetting
	 * @date 2014年8月13日 下午2:06:18  
	 * @author ah
	 */
	void removeMessageSetting(MessageSetting messageSetting);

	/**
	 * 根据店铺编号查询消息回复设置
	 * @Title: getToolSettingByShopNo 
	 * @Description: (根据店铺编号查询消息回复设置) 
	 * @param shopNo
	 * @return
	 * @date 2015年3月23日 下午7:49:22  
	 * @author ah
	 */
	ToolSetting getToolSettingByShopNo(String shopNo);

	/**
	 * 编辑消息回复设置
	 * @Title: editToolSetting 
	 * @Description: (编辑消息回复设置) 
	 * @param toolSetting
	 * @date 2015年3月23日 下午7:55:58  
	 * @author ah
	 */
	void editToolSetting(ToolSetting toolSetting);

	/**
	 * 根据事件类型查询回复消息设置
	 * @Title: getMessageSetting 
	 * @Description: (根据事件类型查询回复消息设置) 
	 * @param eventType
	 * @return
	 * @date 2015年3月27日 下午3:52:24  
	 * @author ah
	 */
	MessageSetting getMessageSetting(MessageSetting messageSetting);

	/**
	 * 根据主键查询消息主体
	 * @Title: getMessageBodyById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年3月27日 下午9:22:13  
	 * @author ah
	 */
	MessageBody getMessageBodyById(Long id);

	/**
	 * 编辑消息设置状态
	 * @Title: editMessageSettingStatus 
	 * @Description: (编辑消息设置状态) 
	 * @param meg
	 * @date 2015年3月30日 上午11:56:35  
	 * @author ah
	 */
	void editMessageSettingStatus(MessageSetting meg);

}
