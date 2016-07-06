package com.zjlp.face.web.server.user.shop.bussiness;

import com.zjlp.face.web.server.user.shop.domain.Notice;

public interface NoticeBussiness {

	/**
	 * 
	 * @Title: addNotice 
	 * @Description: 添加公告（将用户上一条公告的状态变为失效） 
	 * @param notice
	 * @return
	 * @date 2015年9月21日 下午3:48:19  
	 * @author cbc
	 */
	public Long addNotice(Notice notice);
	
	/**
	 * 
	 * @Title: getNoticeByShopNo 
	 * @Description: 根据店铺号查询公告(一个店铺只有一个有效公告) 
	 * @param shopNo
	 * @return
	 * @date 2015年9月21日 下午3:52:22  
	 * @author cbc
	 */
	Notice getNoticeByShopNo(String shopNo);
	
	/**
	 * 
	 * @Title: setNotice 
	 * @Description: app设置公告
	 * @param userId
	 * @param notice
	 * @param isNotice
	 * @return
	 * @date 2015年9月21日 下午5:04:05  
	 * @author cbc
	 */
	public void setNotice(Long userId, Notice notice, Integer isNotice);
	
}
