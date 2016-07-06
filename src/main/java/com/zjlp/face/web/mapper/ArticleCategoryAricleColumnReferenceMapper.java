package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.ArticleCategoryAricleColumnReference;

public interface ArticleCategoryAricleColumnReferenceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ArticleCategoryAricleColumnReference record);

    int insertSelective(ArticleCategoryAricleColumnReference record);

    ArticleCategoryAricleColumnReference selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleCategoryAricleColumnReference record);

    int updateByPrimaryKey(ArticleCategoryAricleColumnReference record);
    
    int deleteByAricleColumnId(Long aricleColumnId);
    
    
    List<ArticleCategoryAricleColumnReference> findByAricleColumnId(Long aricleColumnId);
}