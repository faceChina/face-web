package com.zjlp.face.web.server.product.material.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.NewsMapper;
import com.zjlp.face.web.server.product.material.dao.NewsDao;
import com.zjlp.face.web.server.product.material.domain.News;
import com.zjlp.face.web.server.product.material.domain.dto.NewsDto;
@Repository("newsDao")
public class NewsDaoImpl implements NewsDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void addNews(News news) {
		sqlSession.getMapper(NewsMapper.class).insertSelective(news);

	}

	@Override
	public void editNews(News news) {
		sqlSession.getMapper(NewsMapper.class).updateByPrimaryKeySelective(news);
	}

	@Override
	public void deleteNews(Long id) {
		sqlSession.getMapper(NewsMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public Integer getCount(NewsDto dto) {
		return sqlSession.getMapper(NewsMapper.class).getCount(dto);
	}

	@Override
	public List<NewsDto> findNewsPageList(NewsDto dto, int start, int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dto", dto);
		map.put("start", start);
		map.put("pageSize", pageSize);
		return sqlSession.getMapper(NewsMapper.class).selectList(map);
	}

	@Override
	@Deprecated //v.5.0
	public Integer getMaxSortByShopNo(String shopNo) {
		return sqlSession.getMapper(NewsMapper.class).getMaxSortByShopNo(shopNo);
	}

	@Override
	public News getNewsById(Long id) {
		return sqlSession.getMapper(NewsMapper.class).selectByPrimaryKey(id);
	}
	
	@Override
	public NewsDto getNewsDtoById(Long id) {
		return sqlSession.getMapper(NewsMapper.class).selectNewsDtoById(id);
	}
	
	@Override
	public List<News> findNewsByShopNo(String shopNo) {
		return sqlSession.getMapper(NewsMapper.class).selectByShopNo(shopNo);
	}

	@Override
	public Integer getCountForModular(NewsDto dto) {
		return sqlSession.getMapper(NewsMapper.class).getCountForModular(dto);
	}

	@Override
	public List<NewsDto> findNewsPageListForModular(NewsDto dto, int start,
			int pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dto", dto);
		map.put("start", start);
		map.put("pageSize", pageSize);
		return sqlSession.getMapper(NewsMapper.class).selectListForModular(map);
	}

	@Override
	public List<News> findNewsListByArticleCategoryId(Long articleCategoryId) {
		return sqlSession.getMapper(NewsMapper.class).selectByArticleCategoryId(articleCategoryId);
	}

	@Override
	public List<News> findNewsList(News news) {
		return sqlSession.getMapper(NewsMapper.class).selectNewsList(news);
	}

	@Override
	public void editNewsById(News news) {
		sqlSession.getMapper(NewsMapper.class).updateByPrimaryKeySelective(news);
	}

	@Override
	public List<NewsDto> findNewsAndSortByCategoryId(Long categoryId) {
		return sqlSession.getMapper(NewsMapper.class).findNewsAndSortByCategoryId(categoryId);
	}

}
