package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.product.material.domain.News;
import com.zjlp.face.web.server.product.material.domain.dto.NewsDto;

public interface NewsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKeyWithBLOBs(News record);

    int updateByPrimaryKey(News record);

	Integer getCount(NewsDto dto);

	List<NewsDto> selectList(Map<String, Object> map);

	Integer getMaxSortByShopNo(String shopNo);
	
    List<News> selectByShopNo(String shopNo);

	Integer getCountForModular(NewsDto dto);

	List<NewsDto> selectListForModular(Map<String, Object> map);

	List<News> selectByArticleCategoryId(Long articleCategoryId);

	NewsDto selectNewsDtoById(Long id);

	List<News> selectNewsList(News news);

	void updateNewsById(News news);

	
	List<NewsDto> findNewsAndSortByCategoryId(Long categoryId);
	
}