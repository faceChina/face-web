package com.zjlp.face.web.server.social.businesscircle.dao;

import java.util.List;

import com.zjlp.face.web.server.social.businesscircle.domain.AppComment;

public interface AppCommentDao {
	
	Long add(AppComment appComment);
	
	int delete(Long id,Long userId);
	
	void deleteByCircleMsgId(Long circleMsgId);
	
	List<AppComment> queryAppComment(Long circleMsgId);
	
	AppComment getAppComment(Long id);

}
