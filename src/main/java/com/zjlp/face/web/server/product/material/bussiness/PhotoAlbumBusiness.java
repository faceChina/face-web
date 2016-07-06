package com.zjlp.face.web.server.product.material.bussiness;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.AlbumException;
import com.zjlp.face.web.server.product.material.domain.PhotoAlbum;
import com.zjlp.face.web.server.product.material.domain.dto.PhotoAlbumDto;

public interface PhotoAlbumBusiness {

	
	
	Pagination<PhotoAlbumDto> findPhotoAlbumPageList(PhotoAlbumDto photoAlbumDto,Pagination<PhotoAlbumDto> pagination) throws AlbumException;
	
	

	void addPhotoAlbum(PhotoAlbumDto photoAlbumDto,Long userId) throws AlbumException;
	
	
	void editPhotoAlbum(PhotoAlbumDto photoAlbumDto,Long userId,String shopNo) throws AlbumException;
	
	
	void changeAlbum(String albumList,PhotoAlbum photoAlbum) throws AlbumException; 
	
	
	
	String delPhotoAlbum(PhotoAlbum photoAlbum,Long usrId) throws AlbumException;
	
	
	List<PhotoAlbum> findPhotoAlbumByShopNo(String shopNo) throws AlbumException;
	
	PhotoAlbumDto getById(Long id) throws AlbumException;
}
