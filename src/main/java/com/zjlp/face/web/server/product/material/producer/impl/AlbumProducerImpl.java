package com.zjlp.face.web.server.product.material.producer.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.exception.ext.AlbumException;
import com.zjlp.face.web.server.product.material.domain.Album;
import com.zjlp.face.web.server.product.material.producer.AlbumProducer;
import com.zjlp.face.web.server.product.material.service.AlbumService;

@Service
public class AlbumProducerImpl implements AlbumProducer{

	@Autowired
	private AlbumService albumService;
	
	
	@Override
	public void createDefaultAlbumByShopNo(String shopNo) throws AlbumException {
		try {
			Album album = new Album();
			Date date = new Date();
			album.setIsDefault(true);
			album.setName("默认");
			album.setShopNo(shopNo);
			album.setCreateTime(date);
			album.setUpdateTime(date);
			albumService.addAlbum(album);
			
		} catch (Exception e) {
			throw new AlbumException(e);
		} 
		
	}

	/**
	 * 根据店铺编码获取相册数量
	 */
	@Override
	public Integer getAlbumCountByShopNo(String shopNo) throws AlbumException {
		try {
			Album album = new Album();
			album.setShopNo(shopNo);
			return albumService.getAlbumCountByShopNo(shopNo);
		} catch (Exception e) {
			throw new AlbumException(e);
		} 
	}
}
