package com.zjlp.face.web.server.product.material.bussiness;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.AlbumException;
import com.zjlp.face.web.server.product.material.domain.Photo;
import com.zjlp.face.web.server.product.material.domain.dto.PhotoDto;

public interface PhotoBusiness {

	Pagination<PhotoDto> findPhotoList(PhotoDto photoDto,Pagination<PhotoDto> pagination) throws AlbumException;
	
	void addPhoto(Photo photo,Long userId) throws AlbumException;
	
	void editPhotoName(Photo photo,String shopNo) throws AlbumException;
	
	void delPhoto(String ids,String shopNo,Long userId) throws AlbumException;
	
	void movePhoto(String ids,Long albumId,String shopNo) throws AlbumException;
	
}
