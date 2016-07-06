package com.zjlp.face.web.mapper;

import com.zjlp.face.web.server.user.mulchat.domain.MulChatInformation;

/**
 * @author Baojiang Yang
 *
 */
public interface MulChatInfomationMapper {
	
	int insert(MulChatInformation mulChatInformation);
	
	int deleteByPrimarykey(String id);
	
	MulChatInformation selectByPrimarykey(String id);

	int updateByPrimaryKey(MulChatInformation mulChatInformation);

}
