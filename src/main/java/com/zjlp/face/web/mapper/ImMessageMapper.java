package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.product.im.domain.ImMessage;
import com.zjlp.face.web.server.product.im.domain.dto.ImMessageDto;

public interface ImMessageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImMessage record);

    int insertSelective(ImMessage record);

    ImMessage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImMessage record);

    int updateByPrimaryKeyWithBLOBs(ImMessage record);

    int updateByPrimaryKey(ImMessage record);

	void deleteByUserId(Long userId);
	
	List<ImMessageDto> selectImUserMessage(Map<String,Object> param);
	
	Integer selectImUserMessageCount(ImMessage record);
}