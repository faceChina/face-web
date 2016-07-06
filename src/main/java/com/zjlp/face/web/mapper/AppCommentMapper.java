package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.social.businesscircle.domain.AppComment;

public interface AppCommentMapper {
	
	int insert(AppComment appComment);
	
	int deleteByIdAndUserId(Map<String,Object> map);
	
	int deleteByCircleMsgId(Long circleMsgId);
	
	List<AppComment> selectByCircleMsgId(Long circleMsgId);
	
	AppComment selectByPrimaryKey(Long id);

}
