package com.zjlp.face.web.server.user.shop.service;

import com.zjlp.face.web.server.user.shop.domain.Notice;

public interface NoticeService {

	Long addNotice(Notice notice);

	Notice getNoticeByShopNo(String shopNo);

	void unvalidNotice(Notice newNotice);

}
