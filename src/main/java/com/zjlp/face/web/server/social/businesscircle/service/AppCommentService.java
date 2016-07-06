package com.zjlp.face.web.server.social.businesscircle.service;

import java.util.List;

import com.zjlp.face.web.server.social.businesscircle.domain.AppComment;

public interface AppCommentService {
	
	Long add(AppComment appComment);
	
	int delete(Long id,Long senderId);
	
	void deleteByCircleMsgId(Long circleMsgId);
	
	AppComment getAppComment(Long id);
	
	List<AppComment> queryAppComment(Long circleMsgId);

}
