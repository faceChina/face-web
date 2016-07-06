package com.zjlp.face.web.server.product.material.dao;

import java.util.List;

import com.zjlp.face.web.component.daosupport.GenericDao;
import com.zjlp.face.web.component.daosupport.Param;
import com.zjlp.face.web.server.product.material.domain.Album;
import com.zjlp.face.web.server.product.material.domain.dto.AlbumDto;

public interface AlbumDao extends GenericDao{
	
	void insertSelective(@Param Album album);
	
	void updateByPrimaryKeySelective(@Param Album album);
	
	void deleteByPrimaryKey(@Param("id") Long id);
	
	
	Integer getCount(@Param AlbumDto albumDto);

	List<AlbumDto> findPageList(@Param
			AlbumDto albumDto,@Param("start") int start,@Param("pageSize") int pageSize);
	
	
	List<Album> findDefaultAlbumByShopNo(@Param("shopNo") String shopNo);
	
	Album selectByPrimaryKey(@Param("id") Long id);
	
	int getCountPhoto(@Param("id") Long id);
	
	
	List<Album> findAlbumByShopNo(@Param("shopNo") String shopNo);
	
	List<Album> findAlbumByName(@Param Album album);
	
	List<AlbumDto> findByIdList(@Param("ids") List<Long> ids);
	
	Integer getWapCount(@Param AlbumDto albumDto);
	
	List<AlbumDto> findWapPageList(@Param
			AlbumDto albumDto,@Param("start") int start,@Param("pageSize") int pageSize);

	Integer getCount(@Param("shopNo") String shopNo);
}
