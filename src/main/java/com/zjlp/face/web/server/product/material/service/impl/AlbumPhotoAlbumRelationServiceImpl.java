package com.zjlp.face.web.server.product.material.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.product.material.dao.AlbumPhotoAlbumRelationDao;
import com.zjlp.face.web.server.product.material.domain.AlbumPhotoAlbumRelation;
import com.zjlp.face.web.server.product.material.service.AlbumPhotoAlbumRelationService;

@Service
public class AlbumPhotoAlbumRelationServiceImpl implements AlbumPhotoAlbumRelationService{

	@Autowired
	private AlbumPhotoAlbumRelationDao albumPhotoAlbumRelationDao;
	
	@Override
	public List<AlbumPhotoAlbumRelation> findAlbumPhotoAlbumRelationByPhotoAlbumId(Long photoAlbumId) {
		return albumPhotoAlbumRelationDao.findAlbumPhotoAlbumRelationByPhotoAlbumId(photoAlbumId);
	}

	@Override
	public void addAlbumPhotoAlbumRelation(
			AlbumPhotoAlbumRelation albumPhotoAlbumRelation) {
		albumPhotoAlbumRelationDao.insertSelective(albumPhotoAlbumRelation);
	}

	@Override
	public Integer getMaxSortByPhotoAlbumId(Long photoAlbumId) {
		return albumPhotoAlbumRelationDao.getMaxSortByPhotoAlbumId(photoAlbumId);
	}

	@Override
	public void delAlbumPhotoAlbumRelation(Long id) {
		albumPhotoAlbumRelationDao.deleteByPrimaryKey(id);	
	}

	@Override
	public void delAlbumPhotoAlbumRelationByPhotoAlbumId(Long photoAlbumId) {
		albumPhotoAlbumRelationDao.delAlbumPhotoAlbumRelationByPhotoAlbumId(photoAlbumId);
	}

	@Override
	public void delAlbumPhotoAlbumRelationByAlbumId(Long albumId) {
		albumPhotoAlbumRelationDao.delAlbumPhotoAlbumRelationByAlbumId(albumId);
	}

}
