package com.zjlp.face.web.mapper;

import java.util.HashMap;
import java.util.List;

import com.zjlp.face.web.server.product.material.domain.AricleColumn;
import com.zjlp.face.web.server.product.material.domain.dto.AricleColumnDto;

public interface AricleColumnMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AricleColumn record);

    int insertSelective(AricleColumn record);

    AricleColumn selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AricleColumn record);

    int updateByPrimaryKey(AricleColumn record);
    
	List<AricleColumn> selectListByShopNo(String shopNo);

	Integer getCount(AricleColumnDto aricleColumnDto);

	List<AricleColumnDto> selectPageList(HashMap<String, Object> paramMap);
	
	
	List<AricleColumnDto> findCategoryAndSortByColumnId(Long column);
	
    
    
}