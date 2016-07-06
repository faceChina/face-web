package com.zjlp.face.web.server.product.material.bussiness;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.AlbumException;
import com.zjlp.face.web.server.product.material.domain.Album;
import com.zjlp.face.web.server.product.material.domain.dto.AlbumDto;

public interface AlbumBusiness {

	
	
	Pagination<AlbumDto> findAlbumPageList(AlbumDto albumDto,Pagination<AlbumDto> pagination) throws AlbumException;
	
	Pagination<AlbumDto> findWapAlbumPageList(AlbumDto albumDto,Pagination<AlbumDto> pagination) throws AlbumException;
	
	void addAlbum(Album album,Long userId) throws AlbumException;
	
	String delAlbum(Album album,Long userId) throws AlbumException;
	
	void editAlbum(Album album,Long userId,String shopNo) throws AlbumException;
	
	List<Album> findAlbumByShopNo(String shopNo) throws AlbumException;
	
	List<Album> findAlbumByName(Album album) throws AlbumException;
	
	Album getById(Long id) throws AlbumException;
	
	List<AlbumDto> findAlbumByIdList(String idsStr) throws AlbumException;
}
