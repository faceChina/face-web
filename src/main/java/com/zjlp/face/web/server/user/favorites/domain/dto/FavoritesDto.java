package com.zjlp.face.web.server.user.favorites.domain.dto;

import com.zjlp.face.web.server.user.favorites.domain.Favorites;

public class FavoritesDto extends Favorites {

	private static final long serialVersionUID = 3147329541212462404L;
	
	private String remotePicUrl;
	
	private Long remotePrice;
	
	private Integer remoteStauts;
	
	private String remoteName;
	
	private String goodShopNo;

	public String getGoodShopNo() {
		return goodShopNo;
	}

	public void setGoodShopNo(String goodShopNo) {
		this.goodShopNo = goodShopNo;
	}

	public String getRemoteName() {
		return remoteName;
	}

	public void setRemoteName(String remoteName) {
		this.remoteName = remoteName;
	}

	public String getRemotePicUrl() {
		return remotePicUrl;
	}

	public void setRemotePicUrl(String remotePicUrl) {
		this.remotePicUrl = remotePicUrl;
	}

	public Long getRemotePrice() {
		return remotePrice;
	}

	public void setRemotePrice(Long remotePrice) {
		this.remotePrice = remotePrice;
	}

	public Integer getRemoteStauts() {
		return remoteStauts;
	}

	public void setRemoteStauts(Integer remoteStauts) {
		this.remoteStauts = remoteStauts;
	}


}
