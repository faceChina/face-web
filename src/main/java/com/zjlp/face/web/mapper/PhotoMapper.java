package com.zjlp.face.web.mapper;

import java.util.HashMap;
import java.util.List;

import com.zjlp.face.web.server.product.material.domain.Photo;
import com.zjlp.face.web.server.product.material.domain.dto.PhotoDto;

public interface PhotoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Photo record);

    int insertSelective(Photo record);

    Photo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Photo record);

    int updateByPrimaryKey(Photo record);
    
    List<Photo> findPhotoByShopNo(String shopNo);
	
	List<Photo> findPhotoByAlbumId(Long albumId);
    
    
	Integer getCount(PhotoDto record);

	List<PhotoDto> findPhotoList(HashMap<String, Object> paramMap);
	
	
	int getMaxPhotoMax(String shopNo);
	
	
	int movePhoto(Long oldId,Long newId);
	
	int moveOnePhoto(Long id,Long albumId);
}