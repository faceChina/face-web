package com.zjlp.face.web.server.product.material.service;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.AlbumPhotoAlbumRelation;

public interface AlbumPhotoAlbumRelationService {

	
	void addAlbumPhotoAlbumRelation(AlbumPhotoAlbumRelation albumPhotoAlbumRelation);
	
	
	List<AlbumPhotoAlbumRelation>  findAlbumPhotoAlbumRelationByPhotoAlbumId(Long photoAlbumId);
	
	
	Integer getMaxSortByPhotoAlbumId(Long photoAlbumId);
	
	
	void delAlbumPhotoAlbumRelation(Long id);
	
	void delAlbumPhotoAlbumRelationByPhotoAlbumId(Long photoAlbumId);

	void delAlbumPhotoAlbumRelationByAlbumId(Long albumId);
	
	
}
