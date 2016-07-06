package com.zjlp.face.web.server.user.mulchat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.user.mulchat.dao.MulChatQRCodeDao;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatQRCode;
import com.zjlp.face.web.server.user.mulchat.service.MulChatQRCodeService;

/**
 * @author Baojiang Yang
 *
 */
@Service
public class MulChatQRCodeServiceImpl implements MulChatQRCodeService {

	@Autowired
	private MulChatQRCodeDao mulChatQRCodeDao;

	@Override
	public Long addMulChatQRCode(MulChatQRCode mulChatQRCode) {
		return this.mulChatQRCodeDao.addMulChatQRCode(mulChatQRCode);
	}

	@Override
	public void removeByPrimarykey(String qRCode) {
		this.mulChatQRCodeDao.removeByPrimarykey(qRCode);
	}

	@Override
	public Long updateByPrimaryKey(MulChatQRCode mulChatQRCode) {
		return this.mulChatQRCodeDao.updateByPrimaryKey(mulChatQRCode);
	}

	@Override
	public MulChatQRCode findByQRCode(String qRCode) {
		return this.mulChatQRCodeDao.findByQRCode(qRCode);
	}

	@Override
	public MulChatQRCode findByPrimarykey(Long id) {
		return this.mulChatQRCodeDao.findByPrimarykey(id);
	}

}
