package com.zjlp.face.web.server.product.material.bussiness;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.NewsException;
import com.zjlp.face.web.server.product.material.domain.AricleColumn;
import com.zjlp.face.web.server.product.material.domain.dto.AricleColumnDto;

public interface AricleColumnBusiness {
	
	
	void addAricleColumn(AricleColumnDto aricleColumnDto,Long userId) throws NewsException;
	
	
	void editAricleColumn(AricleColumnDto aricleColumnDto,Long userId) throws NewsException;
	
	
	AricleColumn getAricleColumn(Long id) throws NewsException;

	
	String delAricleColumn(Long id,Long userId) throws NewsException;
	
	
	List<AricleColumn> findAricleColumnByShopNo(String shopNo) throws NewsException;
	
	
	Pagination<AricleColumnDto> findAricleColumnPageList(AricleColumnDto aricleColumnDto, 
			Pagination<AricleColumnDto> pagination) throws NewsException;
	
	
	List<AricleColumnDto> findNewsAndSortByCategoryId(Long columnId) throws NewsException;
	
	
	String soertCategory(Long subId,Long tarId) throws NewsException;
	
	
	String updateColumnAndCategory(AricleColumnDto aricleColumnDto) throws NewsException;
	
}
