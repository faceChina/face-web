package com.zjlp.face.web.server.product.material.domain;

import java.io.Serializable;

public class AlbumPhotoAlbumRelation implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4451964716336334964L;

	private Long id;

    private Long photoAlbumId;

    private Long albumId;

    private Integer sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPhotoAlbumId() {
        return photoAlbumId;
    }

    public void setPhotoAlbumId(Long photoAlbumId) {
        this.photoAlbumId = photoAlbumId;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}