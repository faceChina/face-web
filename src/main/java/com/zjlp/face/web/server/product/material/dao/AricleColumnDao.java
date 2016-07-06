package com.zjlp.face.web.server.product.material.dao;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.AricleColumn;
import com.zjlp.face.web.server.product.material.domain.dto.AricleColumnDto;

public interface AricleColumnDao {
	

	
	void addAricleColumn(AricleColumn aricleColumn);

	void deleteAricleColumnById(Long id);
	
	void editAricleColumn(AricleColumn aricleColumn);

	List<AricleColumn> findAricleColumnByShopNo(String shopNo);

	Integer getCount(AricleColumnDto aricleColumnDto);

	List<AricleColumnDto> findAricleColumnList(
			AricleColumnDto aricleColumnDto, int start, int pageSize);

	AricleColumn getAricleColumnById(Long id);
	
	List<AricleColumnDto> findCategoryAndSortByColumnId(Long columnId);

}
