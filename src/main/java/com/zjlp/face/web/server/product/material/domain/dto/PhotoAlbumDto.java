package com.zjlp.face.web.server.product.material.domain.dto;

import com.zjlp.face.web.server.product.material.domain.PhotoAlbum;

public class PhotoAlbumDto extends PhotoAlbum{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1890861214121221456L;
	
	
	
	private Integer countAlbum;
	
	
	
	private String albumItem;



	public String getAlbumItem() {
		return albumItem;
	}



	public void setAlbumItem(String albumItem) {
		this.albumItem = albumItem;
	}



	public Integer getCountAlbum() {
		return countAlbum;
	}



	public void setCountAlbum(Integer countAlbum) {
		this.countAlbum = countAlbum;
	}

}
