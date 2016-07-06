package com.zjlp.face.web.server.product.good.domain.vo;

import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.server.product.good.domain.GoodImg;

public class GoodImgVo extends GoodImg {

	private static final long serialVersionUID = -1251788321914873116L;
	/** 商品缩略图片 */
	private String zoomUrl;

	public String getZoomUrl() {
		this.zoomUrl = super.getUrl();
		if (null != this.zoomUrl && !"".equals(this.zoomUrl)) {
			String size = PropertiesUtil.getContexrtParam(ImageConstants.GOOD_FILE);
			return ImageConstants.getCloudstZoomPath(this.zoomUrl, size);
		 }
		return null;
	}

	public void setZoomUrl(String zoomUrl) {
		this.zoomUrl = zoomUrl;
	}

}
