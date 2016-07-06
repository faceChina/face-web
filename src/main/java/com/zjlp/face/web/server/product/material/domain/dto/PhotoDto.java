package com.zjlp.face.web.server.product.material.domain.dto;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.server.product.material.domain.Photo;

public class PhotoDto extends Photo{

	/**
	 * 
	 */
	private static final long serialVersionUID = -967420222072854077L;

	private Long[] ids;
	
	private String zoomPath;

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}
	
	public String getZoomPath() {
		String zoom = PropertiesUtil.getContexrtParam(ImageConstants.ALBUM_FILE);
		AssertUtil.hasLength(zoom, "imgConfig.properties未配置相册缩略大小");
		return ImageConstants.getCloudstZoomPath(getPath(), zoom);
	}
	
	
}
