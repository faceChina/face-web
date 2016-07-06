package com.zjlp.face.web.server.product.material.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.material.dao.NewsDao;
import com.zjlp.face.web.server.product.material.domain.News;
import com.zjlp.face.web.server.product.material.domain.dto.NewsDto;
import com.zjlp.face.web.server.product.material.service.NewsService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Override
    public void addNews(News news){
        newsDao.addNews(news);
    }

    @Override
    public void editNews(News news){
        newsDao.editNewsById(news);

    }

    @Override
    public void deleteNews(Long id){
        newsDao.deleteNews(id);

    }

    @Override
    public Pagination<NewsDto> findNewsPageList(NewsDto dto, Pagination<NewsDto> pagination){
        Integer total = newsDao.getCount(dto);
        List<NewsDto> datas = newsDao.findNewsPageList(dto, pagination.getStart(), pagination.getPageSize());
        pagination.setTotalRow(total);
        pagination.setDatas(datas);
        return pagination;
    }

    @Override
    @Deprecated //v.5.0
    public Integer getMaxSortByShopNo(String shopNo){
        return newsDao.getMaxSortByShopNo(shopNo);
    }

    @Override
    public News getNewsById(Long id){
        return newsDao.getNewsById(id);
    }
    
    @Override
	public NewsDto getNewsDtoById(Long id) {
    	 return newsDao.getNewsDtoById(id);
	}
    
    @Override
    public List<News> findNewsByShopNo(String shopNo){
        return newsDao.findNewsByShopNo(shopNo);
    }

    @Override
    public Pagination<NewsDto> findNewsPageListForModular(NewsDto dto, Pagination<NewsDto> pagination){
        Integer total = newsDao.getCountForModular(dto);
        List<NewsDto> datas = newsDao.findNewsPageListForModular(dto, pagination.getStart(), pagination.getPageSize());
        pagination.setTotalRow(total);
        pagination.setDatas(datas);
        return pagination;
    }

	@Override
	public List<News> findNewsListByArticleCategoryId(Long articleCategoryId) {
		return newsDao.findNewsListByArticleCategoryId(articleCategoryId);
	}

	@Override
	public List<News> findNewsList(News news) {
		return newsDao.findNewsList(news);
	}

	@Override
	public Pagination<NewsDto> findNewsPageListByCondition(NewsDto dto,
			Pagination<NewsDto> pagination) {
		Integer total = newsDao.getCount(dto);
		pagination.setTotalRow(total);
        List<NewsDto> datas = newsDao.findNewsPageList(dto, pagination.getStart(), pagination.getPageSize());
        pagination.setDatas(datas);
        return pagination;
	}

	@Override
	public List<NewsDto> findNewsAndSortByCategoryId(Long categoryId) {
		return newsDao.findNewsAndSortByCategoryId(categoryId);
	}

}
