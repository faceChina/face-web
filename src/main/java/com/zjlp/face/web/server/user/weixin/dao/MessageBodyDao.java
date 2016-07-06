package com.zjlp.face.web.server.user.weixin.dao;

import com.zjlp.face.web.server.user.weixin.domain.MessageBody;

/**
 * 消息主体持久层
 * @ClassName: MessageBodyDao 
 * @Description: (消息主体持久层) 
 * @author ah
 * @date 2014年8月7日 下午5:28:35
 */
public interface MessageBodyDao {

	/**
	 * 新增消息主体
	 * @Title: add
	 * @Description: (新增消息主体) 
	 * @param messageBody
	 * @date 2014年8月7日 下午5:30:40  
	 * @author ah
	 */
	void add(MessageBody messageBody);

	/**
	 * 编辑消息主体
	 * @Title: edit 
	 * @Description: (编辑消息主体) 
	 * @param messageBody
	 * @date 2014年8月8日 下午7:13:19  
	 * @author ah
	 */
	void edit(MessageBody messageBody);

	/**
	 * 根据主键查询消息主体
	 * @Title: getMessageBodyById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年3月27日 下午9:23:46  
	 * @author ah
	 */
	MessageBody getMessageBodyById(Long id);

}
