package com.zjlp.face.web.server.product.material.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.material.dao.PhotoDao;
import com.zjlp.face.web.server.product.material.domain.Photo;
import com.zjlp.face.web.server.product.material.domain.dto.PhotoDto;
import com.zjlp.face.web.server.product.material.service.PhotoService;

@Service
public class PhotoServiceImpl implements PhotoService{

	@Autowired
	private PhotoDao photoDao;
	
	@Override
	public void movePhoto(Long oldId, Long newId) {
		photoDao.editMovePhoto(oldId, newId);
	}

	@Override
	public void editPhoto(Photo photo) {
		photoDao.updateByPrimaryKeySelective(photo);
	}

	@Override
	public void delPhoto(Long id) {
		photoDao.deleteByPrimaryKey(id);
	}

	@Override
	public void moveOnePhoto(Photo photo) {
		photoDao.editPhoto(photo);
	}

	@Override
	public Pagination<PhotoDto> findPhotoPageList(PhotoDto photoDto,
			Pagination<PhotoDto> pagination) {
		Integer totalRow = photoDao.getCount(photoDto);
		List<PhotoDto> datas = photoDao.findPhotoList(photoDto, pagination.getStart(), pagination.getPageSize());
		pagination.setTotalRow(totalRow);
		pagination.setDatas(datas);
		return pagination;
	}

	@Override
	public void addPhoto(Photo photo) {
		Date date = new Date();
		photo.setCreateTime(date);
		photo.setUpdateTime(date);
		photoDao.insertSelective(photo);
	}

	@Override
	public Integer getMaxPhotoMax(String shopNo) {
		return photoDao.getMaxPhotoSort(shopNo);
	}

	@Override
	public Photo getById(Long id) {
		return photoDao.selectByPrimaryKey(id);
	}

}
