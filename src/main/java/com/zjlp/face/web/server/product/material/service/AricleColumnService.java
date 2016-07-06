package com.zjlp.face.web.server.product.material.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.material.domain.AricleColumn;
import com.zjlp.face.web.server.product.material.domain.dto.AricleColumnDto;

public interface AricleColumnService {

	

	void addAricleColumn(AricleColumn aricleColumn);
	
	
	void editAricleColumn(AricleColumn aricleColumn);
	
	
	AricleColumn getAricleColumnById(Long id);

	
	void delAricleColumn(Long id);
	
	
	List<AricleColumn> findAricleColumnByShopNo(String shopNo);
	
	
	
	Pagination<AricleColumnDto> findAricleColumnPageList(AricleColumnDto acDto,Pagination<AricleColumnDto> pagination);
	
	
	List<AricleColumnDto> findCategoryAndSortByColumnId(Long columnId);
}
