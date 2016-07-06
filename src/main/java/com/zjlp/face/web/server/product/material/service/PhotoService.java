package com.zjlp.face.web.server.product.material.service;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.material.domain.Photo;
import com.zjlp.face.web.server.product.material.domain.dto.PhotoDto;

public interface PhotoService {
	
	
	void movePhoto(Long oldId,Long newId);
	
	void moveOnePhoto(Photo photo);
	
	void editPhoto(Photo photo);
	
	void delPhoto(Long id);
	
	void addPhoto(Photo photo);
	
	
	Integer getMaxPhotoMax(String shopNo);
	
	
	Pagination<PhotoDto> findPhotoPageList(PhotoDto photoDto,Pagination<PhotoDto> pagination);

	Photo getById(Long id);
}
