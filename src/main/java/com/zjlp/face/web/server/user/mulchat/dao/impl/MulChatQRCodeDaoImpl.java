package com.zjlp.face.web.server.user.mulchat.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.MulChatQRCodeMapper;
import com.zjlp.face.web.server.user.mulchat.dao.MulChatQRCodeDao;
import com.zjlp.face.web.server.user.mulchat.domain.MulChatQRCode;

/**
 * @author Baojiang Yang
 *
 */
@Repository("mulChatQRCodeDao")
public class MulChatQRCodeDaoImpl implements MulChatQRCodeDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long addMulChatQRCode(MulChatQRCode mulChatQRCode) {
		this.sqlSession.getMapper(MulChatQRCodeMapper.class).inser(mulChatQRCode);
		return mulChatQRCode.getId();
	}

	@Override
	public void removeByPrimarykey(String qRCode) {
		this.sqlSession.getMapper(MulChatQRCodeMapper.class).deleteByPrimarykey(qRCode);
	}

	@Override
	public Long updateByPrimaryKey(MulChatQRCode mulChatQRCode) {
		this.sqlSession.getMapper(MulChatQRCodeMapper.class).updateByPrimaryKey(mulChatQRCode);
		return mulChatQRCode.getId();
	}

	@Override
	public MulChatQRCode findByQRCode(String qRCode) {
		return this.sqlSession.getMapper(MulChatQRCodeMapper.class).selectByQRcode(qRCode);
	}

	@Override
	public MulChatQRCode findByPrimarykey(Long id) {
		return this.sqlSession.getMapper(MulChatQRCodeMapper.class).selectByPrimarykey(id);
	}

}
