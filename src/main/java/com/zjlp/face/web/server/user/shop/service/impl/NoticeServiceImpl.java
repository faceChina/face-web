package com.zjlp.face.web.server.user.shop.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.user.shop.dao.NoticeDao;
import com.zjlp.face.web.server.user.shop.domain.Notice;
import com.zjlp.face.web.server.user.shop.service.NoticeService;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private NoticeDao noticeDao;

	@Override
	public Long addNotice(Notice notice) {
		if (notice.getStatus() == null) {
			notice.setStatus(Constants.VALID);
		}
		if (notice.getType() == null) {
			notice.setType(1);
		}
		Date date = new Date();
		notice.setUpdateTime(date);
		notice.setCreateTime(date);
		return noticeDao.addNotice(notice);
	}

	@Override
	public Notice getNoticeByShopNo(String shopNo) {
		return noticeDao.getNoticeByShopNo(shopNo);
	}

	@Override
	public void unvalidNotice(Notice newNotice) {
		newNotice.setUpdateTime(new Date());
		noticeDao.unvalidNotice(newNotice);
	}

}
