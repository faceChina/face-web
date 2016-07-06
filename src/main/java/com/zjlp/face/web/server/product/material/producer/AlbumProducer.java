package com.zjlp.face.web.server.product.material.producer;

import com.zjlp.face.web.exception.ext.AlbumException;

public interface AlbumProducer {
	

	
	
	void createDefaultAlbumByShopNo(String shopNo) throws AlbumException;
	
	Integer getAlbumCountByShopNo(String shopNo) throws AlbumException;

}
