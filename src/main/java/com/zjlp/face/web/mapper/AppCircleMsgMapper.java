package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.social.businesscircle.domain.AppCircleMsg;
import com.zjlp.face.web.server.social.businesscircle.domain.vo.AppCircleMsgVo;

public interface AppCircleMsgMapper {
	
	int insert(AppCircleMsg appCircleMsg);

	int deleteByPrimaryKey(Long id);
	
	AppCircleMsgVo selectByPrimaryKey(Long id);
	
	List<AppCircleMsgVo> selectAppCircleMsgByUserId(Map<String, Object> map);
	
	Integer getCount(Long userId);
	
	Integer getCountByTimes(Map<String, Object> map);
	
	List<AppCircleMsgVo> selectAppCircleMsgPage(Map<String, Object> map);
	
	void update(AppCircleMsg appCircleMsg);

}
