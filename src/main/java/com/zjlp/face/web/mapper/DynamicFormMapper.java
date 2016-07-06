package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.operation.appoint.domain.DynamicForm;

public interface DynamicFormMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DynamicForm record);

    int insertSelective(DynamicForm record);

    DynamicForm selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DynamicForm record);

    int updateByPrimaryKey(DynamicForm record);

	List<DynamicForm> findDynamicFormList(Map<String, Object> map);
}