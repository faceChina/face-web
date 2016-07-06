package com.zjlp.face.web.mapper;

import java.util.HashMap;
import java.util.List;

import com.zjlp.face.web.server.product.material.domain.Album;
import com.zjlp.face.web.server.product.material.domain.dto.AlbumDto;

public interface AlbumMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Album record);

    int insertSelective(Album record);

    Album selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Album record);

    int updateByPrimaryKey(Album record);
    
	Integer getCount(AlbumDto record);

	List<AlbumDto> findPageList(HashMap<String, Object> paramMap);
	
	List<Album> findDefaultAlbumByShopNo(String shopNo);
	
	int getCountPhoto(Long id);
	
	List<Album> findAlbumByShopNo(String shopNo);
    
	List<AlbumDto> selectByIdList(List<Long> ids);
}