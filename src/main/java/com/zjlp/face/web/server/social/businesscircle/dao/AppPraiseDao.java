package com.zjlp.face.web.server.social.businesscircle.dao;

import java.util.List;

import com.zjlp.face.web.server.social.businesscircle.domain.AppPraise;

public interface AppPraiseDao {
	
	Long add(AppPraise appPraise);
	
	void deleteByCircleMsgId(Long circleMsgId);
	
	List<AppPraise> queryAppPraise(Long circleMsgId);
	
	AppPraise getAppPraise(Long id);
	
	int delete(Long id);

}
