package com.zjlp.face.web.server.user.mulchat.business;

import java.util.List;

import com.zjlp.face.web.exception.ext.MulChatViewNickException;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatViewNick;

/**
 * @author Baojiang Yang
 *
 */
public interface MulChatViewNickBusiness {

	/**
	 * 插入我在群组内显示昵称
	 * 
	 * @param mulChatViewNick
	 * @return
	 * @throws MulChatViewNickException
	 */
	String insert(MulChatViewNick mulChatViewNick) throws MulChatViewNickException;

	/**
	 * 删除我在群组内显示昵称
	 * 
	 * @param mulChatViewNick
	 * @throws MulChatViewNickException
	 */
	void delete(MulChatViewNick mulChatViewNick) throws MulChatViewNickException;

	/**
	 * 查找我在群组内显示昵称
	 * 
	 * @param mulChatViewNick
	 * @return
	 * @throws MulChatViewNickException
	 */
	MulChatViewNick select(MulChatViewNick mulChatViewNick) throws MulChatViewNickException;

	/**
	 * 更新我在群组内显示昵称
	 * 
	 * @param mulChatViewNick
	 * @return
	 * @throws MulChatViewNickException
	 */
	String update(MulChatViewNick mulChatViewNick) throws MulChatViewNickException;

	/**
	 * 查找群组内显示昵称列表
	 * 
	 * @param groupId
	 * @return
	 */
	List<MulChatViewNick> findGroupNickList(String groupId) throws MulChatViewNickException;
}
