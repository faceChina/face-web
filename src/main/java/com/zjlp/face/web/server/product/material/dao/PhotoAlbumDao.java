package com.zjlp.face.web.server.product.material.dao;

import java.util.List;

import com.zjlp.face.web.component.daosupport.GenericDao;
import com.zjlp.face.web.component.daosupport.Param;
import com.zjlp.face.web.server.product.material.domain.PhotoAlbum;
import com.zjlp.face.web.server.product.material.domain.dto.PhotoAlbumDto;

public interface PhotoAlbumDao extends GenericDao{

	

	void insertSelective(@Param PhotoAlbum photoAlbum);
	
	void updateByPrimaryKeySelective(@Param PhotoAlbum photoAlbum);
	
	void deleteByPrimaryKey(@Param("id") Long id);
	
	
	Integer getCount(@Param PhotoAlbumDto photoAlbumDto);

	List<PhotoAlbumDto> findPhotoList(@Param
			PhotoAlbumDto photoAlbumDto,@Param("start") int start,@Param("pageSize") int pageSize);
	
	List<PhotoAlbum> findPhotoAlbumByShopNo(@Param("shopNo") String shopNo);
	
	PhotoAlbumDto selectPhotoAlbumDtoByPrimaryKey(@Param("id") Long id);
}
