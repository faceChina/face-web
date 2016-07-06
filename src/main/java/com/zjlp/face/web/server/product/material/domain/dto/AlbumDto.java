package com.zjlp.face.web.server.product.material.domain.dto;

import com.zjlp.face.web.server.product.material.domain.Album;

public class AlbumDto extends Album{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6848109994824669901L;
	
	
	private Integer countPhoto;

	private Long photoAlbumId;

	public Integer getCountPhoto() {
		return countPhoto;
	}

	public void setCountPhoto(Integer countPhoto) {
		this.countPhoto = countPhoto;
	}

	public Long getPhotoAlbumId() {
		return photoAlbumId;
	}

	public void setPhotoAlbumId(Long photoAlbumId) {
		this.photoAlbumId = photoAlbumId;
	}

}
