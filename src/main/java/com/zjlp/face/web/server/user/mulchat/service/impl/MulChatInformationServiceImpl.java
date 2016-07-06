package com.zjlp.face.web.server.user.mulchat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.user.mulchat.dao.MulChatInformationDao;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatInformation;
import com.zjlp.face.web.server.user.mulchat.service.MulChatInformationService;

/**
 * @author Baojiang Yang
 *
 */
@Service
public class MulChatInformationServiceImpl implements MulChatInformationService {

	@Autowired
	private MulChatInformationDao mulChatInformationDao;

	@Override
	public String insert(MulChatInformation mulChatInformation) {
		return this.mulChatInformationDao.insert(mulChatInformation);
	}

	@Override
	public void deleteByPrimarykey(String id) {
		this.mulChatInformationDao.deleteByPrimarykey(id);
	}

	@Override
	public MulChatInformation selectByPrimarykey(String id) {
		return this.mulChatInformationDao.selectByPrimarykey(id);
	}

	@Override
	public String updateByPrimaryKey(MulChatInformation mulChatInformation) {
		return this.mulChatInformationDao.updateByPrimaryKey(mulChatInformation);
	}

}
