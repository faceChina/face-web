package com.zjlp.face.web.server.user.shop.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.NoticeMapper;
import com.zjlp.face.web.server.user.shop.dao.NoticeDao;
import com.zjlp.face.web.server.user.shop.domain.Notice;

@Repository
public class NoticeDaoImpl implements NoticeDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long addNotice(Notice notice) {
		sqlSession.getMapper(NoticeMapper.class).insertSelective(notice);
		return notice.getId();
	}

	@Override
	public Notice getNoticeByShopNo(String shopNo) {
		return sqlSession.getMapper(NoticeMapper.class).getNoticeByShopNo(shopNo);
	}

	@Override
	public void unvalidNotice(Notice newNotice) {
		sqlSession.getMapper(NoticeMapper.class).updateByPrimaryKeySelective(newNotice);
	}

}
