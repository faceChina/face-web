package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.product.material.domain.FancyMessage;

public interface FancyMessageMapper {
	
    int deleteByPrimaryKey(Long id);

    int insert(FancyMessage record);

    int insertSelective(FancyMessage record);

    FancyMessage selectByPrimaryKey(Long id);
    
    int updateByPrimaryKeySelective(FancyMessage record);

    int updateByPrimaryKey(FancyMessage record);

	int getCount(FancyMessage fancyMessage);

	List<FancyMessage> findFancyMessagePageList(Map<String, Object> map);
}