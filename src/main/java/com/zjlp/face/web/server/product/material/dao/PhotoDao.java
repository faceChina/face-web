package com.zjlp.face.web.server.product.material.dao;

import java.util.List;

import com.zjlp.face.web.component.daosupport.GenericDao;
import com.zjlp.face.web.component.daosupport.Param;
import com.zjlp.face.web.server.product.material.domain.Photo;
import com.zjlp.face.web.server.product.material.domain.dto.PhotoDto;

public interface PhotoDao extends GenericDao{

	void insertSelective(@Param Photo photo);
	
	void updateByPrimaryKeySelective(@Param Photo photo);
	
	void deleteByPrimaryKey(@Param("id") Long id);
	
	
	Integer getCount(@Param PhotoDto photoDto);

	List<PhotoDto> findPhotoList(@Param
			PhotoDto photoDto,@Param("start") int start,@Param("pageSize") int pageSize);
	
	Integer getMaxPhotoSort(@Param("shopNo") String shopNo);
	
	List<Photo> findPhotoByShopNo(@Param("shopNo") String shopNo);
	
	List<Photo> findPhotoByAlbumId(@Param("albumId") Long albumId);
	
	void editMovePhoto(@Param("oldId") Long oldId,@Param("newId") Long newId);
	
	void editPhoto(@Param Photo photo);
	
	Photo selectByPrimaryKey(@Param("id")Long id);
}
