package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.user.mulchat.domain.MulChatViewNick;

/**
 * @author Baojiang Yang
 *
 */
public interface MulChatViewNickMapper {
	
	int insert(MulChatViewNick mulChatViewNick);
	
	void delete(Map<String, Object> map);

	MulChatViewNick select(Map<String, Object> map);

	int updateNickName(MulChatViewNick mulChatViewNick);

	List<MulChatViewNick> findGroupNickList(String groupId);

}
