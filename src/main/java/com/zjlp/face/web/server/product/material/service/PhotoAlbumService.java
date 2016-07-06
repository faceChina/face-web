package com.zjlp.face.web.server.product.material.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.material.domain.PhotoAlbum;
import com.zjlp.face.web.server.product.material.domain.dto.PhotoAlbumDto;

public interface PhotoAlbumService {
	
	Pagination<PhotoAlbumDto> findPhotoAlbumPageList(PhotoAlbumDto photoAlbumDto,Pagination<PhotoAlbumDto> pagination);
	
	void addPhotoAlbum(PhotoAlbum photoAlbum);
	
	void editPhotoAlbum(PhotoAlbum photoAlbum);
	
	void delPhotoAlbum(Long id);
	
	List<PhotoAlbum> findPhotoAlbumByShopNo(String shopNo);
	
	PhotoAlbumDto getById(Long id);
	
}
