package com.zjlp.face.web.server.social.businesscircle.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.social.businesscircle.domain.AppCircleMsg;
import com.zjlp.face.web.server.social.businesscircle.domain.vo.AppCircleMsgVo;

public interface AppCircleMsgService {
	
	Long add(AppCircleMsg appCircle);
	
	void delete(Long id);
	
	AppCircleMsgVo getAppCircleById(Long id);
	
	Pagination<AppCircleMsgVo> findMyAppCircleMsgVo(Long userId,Pagination<AppCircleMsgVo> pagination);
	
	List<AppCircleMsgVo> queryAppCircleMsgVo(Long userId);
	
	Pagination<AppCircleMsgVo> queryAppCircleMsgVo(String username,Long upMsgId,Long downMsgId,Pagination<AppCircleMsgVo> pagination);
	
	void edit(AppCircleMsg appCircle);

}
