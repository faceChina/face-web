package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.product.material.domain.FancyNavigationItem;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月24日 下午4:50:30
 *
 */
public interface FancyNavigationItemMapper {

	int insert(FancyNavigationItem fancyNavigationItem);

	FancyNavigationItem selectByPrimaryKey(Long id);

	int updateByPrimaryKey(FancyNavigationItem fancyNavigationItem);

	int deleteByPrimaryKey(Long id);
	
	List<FancyNavigationItem> selectItemByNavigationId(Map<String, Object> map);
}
