package com.zjlp.face.web.server.product.material.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.material.dao.AricleColumnDao;
import com.zjlp.face.web.server.product.material.domain.AricleColumn;
import com.zjlp.face.web.server.product.material.domain.dto.AricleColumnDto;
import com.zjlp.face.web.server.product.material.service.AricleColumnService;

@Service
public class AricleColumnServiceImpl implements AricleColumnService{

	
	@Autowired
	private AricleColumnDao aricleColumnDao;

	@Override
	public void addAricleColumn(AricleColumn aricleColumn) {
		
		Date date = new Date();
		aricleColumn.setCreateTime(date);
		aricleColumn.setUpdateTime(date);
		aricleColumn.setStatus(1);
		aricleColumnDao.addAricleColumn(aricleColumn);
	}

	@Override
	public void editAricleColumn(AricleColumn aricleColumn) {
		Date date = new Date();
		aricleColumn.setUpdateTime(date);
		aricleColumnDao.editAricleColumn(aricleColumn);
	}

	@Override
	public void delAricleColumn(Long id) {
		// TODO Auto-generated method stub
		aricleColumnDao.deleteAricleColumnById(id);
	}

	@Override
	public List<AricleColumn> findAricleColumnByShopNo(String shopNo) {
		// TODO Auto-generated method stub
		return aricleColumnDao.findAricleColumnByShopNo(shopNo);
	}

	@Override
	public Pagination<AricleColumnDto> findAricleColumnPageList(
			AricleColumnDto acDto, Pagination<AricleColumnDto> pagination) {
		Integer totalRow = aricleColumnDao.getCount(acDto);
		List<AricleColumnDto> datas = aricleColumnDao.findAricleColumnList(acDto, pagination.getStart(), pagination.getPageSize());
		pagination.setTotalRow(totalRow);
		pagination.setDatas(datas);
		return pagination;
	}

	@Override
	public AricleColumn getAricleColumnById(Long id) {
		return aricleColumnDao.getAricleColumnById(id);
	}

	@Override
	public List<AricleColumnDto> findCategoryAndSortByColumnId(Long columnId) {
		return aricleColumnDao.findCategoryAndSortByColumnId(columnId);
	}

	

}
