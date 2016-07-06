package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.user.customer.domain.AppGroup;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月10日 下午5:50:59
 *
 */
public interface AppGroupMapper {

	int insert(AppGroup record);

	int deleteByPrimaryKey(Long id);

	int updateByPrimaryKey(AppGroup record);

	AppGroup selectByPrimaryKey(Long id);

	List<AppGroup> selectByUserId(Map<String, Object> map);

	AppGroup selectByUserIdAndGroupName(Map<String, Object> map);

	Integer getMaxGroupSort(Map<String, Object> map);

	List<AppGroup> selectUngroups(Map<String, Object> map);

	int deleteUngroupByUserId(Long userId);

}
