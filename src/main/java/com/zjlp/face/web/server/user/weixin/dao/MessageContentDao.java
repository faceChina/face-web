package com.zjlp.face.web.server.user.weixin.dao;

import java.util.List;

import com.zjlp.face.web.server.user.weixin.domain.MessageContent;

/**
 * 消息内容持久层
 * @ClassName: MessageContentDao 
 * @Description: (消息内容持久层) 
 * @author ah
 * @date 2014年8月6日 下午2:45:28
 */
public interface MessageContentDao {

	/**
	 * 新增消息主体
	 * @Title: add
	 * @Description: (新增消息主体) 
	 * @param messageContent
	 * @date 2014年8月7日 下午5:32:26  
	 * @author ah
	 */
	void add(MessageContent messageContent);
	
	/**
	 * 根据主键查询消息内容
	 * @Title: getById 
	 * @Description: (根据主键查询消息内容) 
	 * @param id
	 * @return
	 * @date 2014年8月6日 下午2:48:00  
	 * @author ah
	 */
	MessageContent getById(Long id);

	/**
	 * 根据消息主体id查询消息内容
	 * @Title: findByMessageBodyId 
	 * @Description: (根据消息主体id查询消息内容) 
	 * @param messageBodyId
	 * @return
	 * @date 2014年8月8日 下午3:25:21  
	 * @author ah
	 */
	List<MessageContent> findByMessageBodyId(Long messageBodyId);

	/**
	 * 根据消息主体id删除消息内容
	 * @Title: delMessageContentByMessageBodyId 
	 * @Description: (根据消息主体id删除消息内容) 
	 * @param messageBodyId
	 * @date 2014年8月8日 下午7:20:56  
	 * @author ah
	 */
	void delMessageContentByMessageBodyId(Long messageBodyId);

	/**
	 * 编辑消息内容
	 * @Title: editMessageContent 
	 * @Description: (编辑消息内容) 
	 * @param messageContent
	 * @date 2014年8月11日 下午4:15:27  
	 * @author ah
	 */
	void editMessageContent(MessageContent messageContent);

	/**
	 * 根据主键删除消息内容
	 * @Title: delMessageContentById 
	 * @Description: (根据主键删除消息内容) 
	 * @param id
	 * @date 2014年8月11日 下午8:39:01  
	 * @author ah
	 */
	void delMessageContentById(Long id);

}
