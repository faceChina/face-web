package com.zjlp.face.web.server.product.material.dao;

import java.util.List;

import com.zjlp.face.web.component.daosupport.GenericDao;
import com.zjlp.face.web.component.daosupport.Param;
import com.zjlp.face.web.server.product.material.domain.AlbumPhotoAlbumRelation;

public interface AlbumPhotoAlbumRelationDao extends GenericDao{

	
	
	void insertSelective(@Param AlbumPhotoAlbumRelation albumPhotoAlbumRelation);
	
	
	void deleteByPrimaryKey(@Param("id") Long id);
	
	
	List<AlbumPhotoAlbumRelation> findAlbumPhotoAlbumRelationByPhotoAlbumId(@Param("photoAlbumId") Long photoAlbumId);
	
	
	int getMaxSortByPhotoAlbumId(@Param("photoAlbumId") Long photoAlbumId);

	
	void delAlbumPhotoAlbumRelationByPhotoAlbumId(@Param("photoAlbumId") Long photoAlbumId);
	
	void delAlbumPhotoAlbumRelationByAlbumId(@Param("albumId") Long albumId);
	

}
