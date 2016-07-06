package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.product.material.domain.AlbumPhotoAlbumRelation;

public interface AlbumPhotoAlbumRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AlbumPhotoAlbumRelation record);

    int insertSelective(AlbumPhotoAlbumRelation record);

    AlbumPhotoAlbumRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AlbumPhotoAlbumRelation record);

    int updateByPrimaryKey(AlbumPhotoAlbumRelation record);
    
	List<AlbumPhotoAlbumRelation> findAlbumPhotoAlbumRelationByPhotoAlbumId(Long photoAlbumId);
	
	int delAlbumPhotoAlbumRelationByPhotoAlbumId(Long photoAlbumId);
	
	int delAlbumPhotoAlbumRelationByAlbumId(Long albumId);

}