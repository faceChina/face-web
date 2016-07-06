package com.zjlp.face.web.mapper;

import java.util.HashMap;
import java.util.List;

import com.zjlp.face.web.server.product.material.domain.PhotoAlbum;
import com.zjlp.face.web.server.product.material.domain.dto.PhotoAlbumDto;

public interface PhotoAlbumMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PhotoAlbum record);

    int insertSelective(PhotoAlbum record);

    PhotoAlbum selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PhotoAlbum record);

    int updateByPrimaryKey(PhotoAlbum record);
    
    
    
	Integer getCount(PhotoAlbum record);

	List<PhotoAlbum> selectPageList(HashMap<String, Object> paramMap);
	
	PhotoAlbumDto selectPhotoAlbumDtoByPrimaryKey(Long id);
}