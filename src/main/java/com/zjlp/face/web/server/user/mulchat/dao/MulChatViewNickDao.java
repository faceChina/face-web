package com.zjlp.face.web.server.user.mulchat.dao;

import java.util.List;

import com.zjlp.face.web.server.user.mulchat.domain.MulChatViewNick;

/**
 * @author Baojiang Yang
 *
 */
public interface MulChatViewNickDao {

	String insert(MulChatViewNick mulChatViewNick);

	void delete(MulChatViewNick mulChatViewNick);

	MulChatViewNick select(MulChatViewNick mulChatViewNick);

	String update(MulChatViewNick mulChatViewNick);

	List<MulChatViewNick> findGroupNickList(String groupId);

}
