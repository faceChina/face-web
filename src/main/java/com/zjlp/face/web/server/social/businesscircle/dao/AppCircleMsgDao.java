package com.zjlp.face.web.server.social.businesscircle.dao;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.social.businesscircle.domain.AppCircleMsg;
import com.zjlp.face.web.server.social.businesscircle.domain.vo.AppCircleMsgVo;

public interface AppCircleMsgDao {
	
	Long add(AppCircleMsg appCircle);
	
	void delete(Long id);
	
	AppCircleMsgVo getAppCircleById(Long id);
	
	Pagination<AppCircleMsgVo> findAppCircleMsgVoByUserId(Long userId,Pagination<AppCircleMsgVo> pagination);
	
	Pagination<AppCircleMsgVo> queryAppCircleMsgVo(String username,Long upMsgId,Long downMsgId,Pagination<AppCircleMsgVo> pagination);
	
	void edit(AppCircleMsg appCircle);
}
