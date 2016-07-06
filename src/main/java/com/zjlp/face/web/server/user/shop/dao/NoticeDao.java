package com.zjlp.face.web.server.user.shop.dao;

import com.zjlp.face.web.server.user.shop.domain.Notice;

public interface NoticeDao {

	Long addNotice(Notice notice);

	Notice getNoticeByShopNo(String shopNo);

	void unvalidNotice(Notice newNotice);

}
