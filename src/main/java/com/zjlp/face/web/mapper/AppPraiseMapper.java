package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.social.businesscircle.domain.AppPraise;

public interface AppPraiseMapper {
	
	int insert(AppPraise appPraise);
	
	int deleteByCircleMsgId(Long circleMsgId);
	
	List<AppPraise> selectByCircleMsgId(Long circleMsgId);
	
	AppPraise selectByPrimaryKey(Long id);
	
	int deleteByPrimaryKey(Long id);
}
