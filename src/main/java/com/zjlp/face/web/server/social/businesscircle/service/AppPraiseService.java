package com.zjlp.face.web.server.social.businesscircle.service;

import java.util.List;

import com.zjlp.face.web.server.social.businesscircle.domain.AppPraise;

public interface AppPraiseService {
	
	Long add(AppPraise appPraise);
	
	void deleteByCircleMsgId(Long circleMsgId);
	
	List<AppPraise> queryAppPraise(Long circleMsgId);
	
	AppPraise getAppPraise(Long id);
	
	int cancelPraise(Long id,Long userId);

}
