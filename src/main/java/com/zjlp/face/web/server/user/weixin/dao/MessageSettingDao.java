package com.zjlp.face.web.server.user.weixin.dao;

import java.util.List;

import com.zjlp.face.web.server.user.weixin.domain.MessageSetting;
import com.zjlp.face.web.server.user.weixin.domain.dto.MessageSettingDto;

/**
 * 消息设置持久层
 * @ClassName: MessageSettingDao 
 * @Description: (消息设置持久层) 
 * @author ah
 * @date 2014年8月5日 下午6:59:29
 */
public interface MessageSettingDao {
	
	/**
	 * 新增消息主体
	 * @Title: add
	 * @Description: (新增消息主体) 
	 * @param messageSetting
	 * @date 2014年8月7日 下午5:31:24  
	 * @author Administrator
	 */
	void add(MessageSetting messageSetting);

	/**
	 * 编辑消息设置
	 * @Title: edit
	 * @Description: (编辑消息设置) 
	 * @param messageSetting
	 * @date 2014年8月5日 下午7:02:25  
	 * @author ah
	 */
	void edit(MessageSetting messageSetting);

	/**
	 * 查询消息设置分页列表
	 * @Title: findMessageSettingPageList 
	 * @Description: (查询消息设置分页列表) 
	 * @param dto
	 * @param start
	 * @param pageSize
	 * @return
	 * @date 2014年8月5日 下午7:01:17  
	 * @author ah
	 */
	List<MessageSettingDto> findMessageSettingPageList(MessageSettingDto dto,
			int start, int pageSize);

	/**
	 * 查询消息设置总数
	 * @Title: getCount 
	 * @Description: (查询消息设置总数) 
	 * @param dto
	 * @return
	 * @date 2014年8月5日 下午7:00:09  
	 * @author ah
	 */
	Integer getCount(MessageSettingDto dto);

	/**
	 * 根据主键查询消息设置
	 * @Title: getById 
	 * @Description: (根据主键查询消息设置) 
	 * @param id
	 * @return
	 * @date 2014年8月8日 下午4:02:10  
	 * @author Administrator
	 */
	MessageSetting getById(Long id);

	/**
	 * 逻辑删除消息设置
	 * @Title: removeMessageSetting 
	 * @Description: (逻辑删除消息设置) 
	 * @param messageSetting
	 * @date 2014年8月13日 下午2:08:18  
	 * @author ah
	 */
	void removeMessageSetting(MessageSetting messageSetting);

	/**
	 * 查询回复消息设置
	 * @Title: getMessageSetting 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param messageSetting
	 * @return
	 * @date 2015年3月27日 下午3:54:42  
	 * @author ah
	 */
	MessageSetting getMessageSetting(MessageSetting messageSetting);

	/**
	 * 编辑消息设置状态
	 * @Title: editMessageSettingStatus 
	 * @Description: (编辑消息设置状态) 
	 * @param meg
	 * @return
	 * @date 2015年3月30日 上午11:58:35  
	 * @author ah
	 */ 
	void editMessageSettingStatus(MessageSetting meg);

}
