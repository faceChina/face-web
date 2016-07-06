package com.zjlp.face.web.server.user.mulchat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.user.mulchat.dao.MulChatViewNickDao;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatViewNick;
import com.zjlp.face.web.server.user.mulchat.service.MulChatViewNickService;

/**
 * @author Baojiang Yang
 *
 */
@Service
public class MulChatViewNickServiceImpl implements MulChatViewNickService {

	@Autowired
	private MulChatViewNickDao mulChatViewNickDao;

	@Override
	public String insert(MulChatViewNick mulChatViewNick) {
		return this.mulChatViewNickDao.insert(mulChatViewNick);
	}

	@Override
	public String update(MulChatViewNick mulChatViewNick) {
		return this.mulChatViewNickDao.update(mulChatViewNick);
	}

	@Override
	public void delete(MulChatViewNick mulChatViewNick) {
		this.mulChatViewNickDao.delete(mulChatViewNick);

	}

	@Override
	public MulChatViewNick select(MulChatViewNick mulChatViewNick) {
		return this.mulChatViewNickDao.select(mulChatViewNick);
	}

	@Override
	public List<MulChatViewNick> findGroupNickList(String groupId) {
		return this.mulChatViewNickDao.findGroupNickList(groupId);
	}

}
