package com.zjlp.face.web.server.product.material.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.material.dao.PhotoAlbumDao;
import com.zjlp.face.web.server.product.material.domain.PhotoAlbum;
import com.zjlp.face.web.server.product.material.domain.dto.PhotoAlbumDto;
import com.zjlp.face.web.server.product.material.service.PhotoAlbumService;

@Service
public class PhotoAlbumServiceImpl implements PhotoAlbumService{

	
	@Autowired
	private PhotoAlbumDao photoAlbumDao;
	
	@Override
	public Pagination<PhotoAlbumDto> findPhotoAlbumPageList(
			PhotoAlbumDto photoAlbumDto, Pagination<PhotoAlbumDto> pagination) {
		Integer totalRow = photoAlbumDao.getCount(photoAlbumDto);
		List<PhotoAlbumDto> datas = photoAlbumDao.findPhotoList(photoAlbumDto, pagination.getStart(), pagination.getPageSize());
		pagination.setTotalRow(totalRow);
		pagination.setDatas(datas);
		return pagination;
	}

	@Override
	public void addPhotoAlbum(PhotoAlbum photoAlbum) {
		Date date = new Date();
		photoAlbum.setCreateTime(date);
		photoAlbum.setUpdateTime(date);
		photoAlbum.setStatus(1);
		photoAlbumDao.insertSelective(photoAlbum);
	}

	@Override
	public void editPhotoAlbum(PhotoAlbum photoAlbum) {
		photoAlbumDao.updateByPrimaryKeySelective(photoAlbum);
	}

	@Override
	public void delPhotoAlbum(Long id) {
		photoAlbumDao.deleteByPrimaryKey(id);
	}

	@Override
	public List<PhotoAlbum> findPhotoAlbumByShopNo(String shopNo) {
		return photoAlbumDao.findPhotoAlbumByShopNo(shopNo);
	}

	@Override
	public PhotoAlbumDto getById(Long id) {
		return photoAlbumDao.selectPhotoAlbumDtoByPrimaryKey(id);
	}


}
