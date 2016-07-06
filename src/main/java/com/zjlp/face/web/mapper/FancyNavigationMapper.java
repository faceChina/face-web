package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.product.material.domain.FancyNavigation;
import com.zjlp.face.web.server.product.material.domain.dto.FancyNavigationDto;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月24日 下午4:45:37
 *
 */
public interface FancyNavigationMapper {

	int insert(FancyNavigation fancyNavigation);

	FancyNavigation selectByPrimaryKey(Long id);

	int updateByPrimaryKey(FancyNavigation fancyNavigation);

	int deleteByPrimaryKey(Long id);

	List<FancyNavigationDto> selectAllNavigation(Map<String, Object> map);

}
