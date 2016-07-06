package com.zjlp.face.web.server.product.material.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.material.domain.Album;
import com.zjlp.face.web.server.product.material.domain.dto.AlbumDto;

public interface AlbumService {

	
	
	Pagination<AlbumDto> findAlbumPageList(AlbumDto albumDto,Pagination<AlbumDto> pagination);
	
	Pagination<AlbumDto> findWapAlbumPageList(AlbumDto albumDto,Pagination<AlbumDto> pagination);
	
	void addAlbum(Album album);
	
	void editAlbum(Album album);
	
	void delAlbum(Long id);
	
	Album getAlbumById(Long id);
	
	Album getDefaultAlbumByShopNo(String shopNo);
	
	int getCountPhoto(Long albumId);
	
	List<Album> findAlbumByShopNo(String shopNo);
	
	List<Album> findAlbumByName(Album album);

	Album getById(Long id);
	
	List<AlbumDto> findAlbumByIds(List<Long> ids);
	
	Integer getAlbumCountByShopNo(String shopNo);
}
