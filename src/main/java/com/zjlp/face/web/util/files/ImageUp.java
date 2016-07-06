package com.zjlp.face.web.util.files;

import java.util.List;

import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.web.constants.ImageConstants;

public abstract class ImageUp extends ImageUpProducer {

	private boolean allEdit = true;
	public ImageUp() {}
	public ImageUp(String code, Long userId,
			String tableName, String tableId, String zoomSizes) {
		super(code, userId, tableName, tableId, zoomSizes);
	}
	public ImageUp(String code, Long userId, String shopNo,
			String tableName, String tableId, String zoomSizes) {
		super(code, userId, shopNo, tableName, tableId, zoomSizes);
	}
	
	public ImageUp(String code, Long userId, String shopNo,
			String tableName, String tableId, String zoomSizes, Integer fileLabel) {
		super(code, userId, shopNo, tableName, tableId, zoomSizes, fileLabel);
	}
	public void excute() {
		List<FileBizParamDto> params = super.uploadToTFS();
		if (null == params || params.isEmpty()) {
			return;
		}
		for (FileBizParamDto fileBizParamDto : params) {
			if (null == fileBizParamDto) continue;
			String code = fileBizParamDto.getCode();
			String imgData = fileBizParamDto.getImgData();
			if (ImageConstants.UBB_FILE.equals(code)) {
				this.afterUBB(imgData);
				continue;
			}
			//其他
			this.afterTypeImage(code, imgData);
		}
		//统一保存
		if (allEdit) {
			editAll();
		}
	}
	public boolean isAllEdit() {
		return allEdit;
	}
	public void setAllEdit(boolean allEdit) {
		this.allEdit = allEdit;
	}
	protected void afterUBB(String imgData){}
	
	protected void afterTypeImage(String code, String imgData){}
	
	protected void editAll(){}
	
}
	
