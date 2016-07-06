package com.zjlp.face.web.util.files;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.web.job.PersistenceJobServiceLocator;

public class ImageUpProducer {
	//1.图片上传  2.图片备份
	public static final Integer FILELABLE_UPLOAD = 1;
	public static final Integer FILELABLE_BACKUP = 2;
	//图片源头
	private String code;
	//店铺号
	private String shopNo;
	//用户ID
	private Long userId;
	//表名
	private String tableName;
	//字段id
	private String tableId;
	//图片大小
	private String zoomSizes;
	//1.图片上传  2.图片备份
	private Integer fileLable = FILELABLE_UPLOAD;

	public ImageUpProducer() {
	}

	public ImageUpProducer(String code, Long userId, String shopNo,
			String tableName, String tableId, String zoomSizes, Integer fileLable) {
		this.code = code;
		this.shopNo = shopNo;
		this.userId = userId;
		this.tableName = tableName;
		this.tableId = tableId;
		this.zoomSizes = zoomSizes;
		this.fileLable = fileLable;
	}
	
	public ImageUpProducer(String code, Long userId, String shopNo,
			String tableName, String tableId, String zoomSizes) {
		this.code = code;
		this.shopNo = shopNo;
		this.userId = userId;
		this.tableName = tableName;
		this.tableId = tableId;
		this.zoomSizes = zoomSizes;
		this.fileLable = FILELABLE_UPLOAD;
	}

	public ImageUpProducer(String code, Long userId, String tableName,
			String tableId, String zoomSizes, Integer fileLable) {
		this(code, userId, null, tableName, tableId, zoomSizes, fileLable);
	}
	
	public ImageUpProducer(String code, Long userId, String tableName,
			String tableId, String zoomSizes) {
		this(code, userId, null, tableName, tableId, zoomSizes, FILELABLE_UPLOAD);
	}
	public String getCode() {
		return code;
	}
	public String getShopNo() {
		return shopNo;
	}
	public Long getUserId() {
		return userId;
	}
	public String getTableName() {
		return tableName;
	}
	public String getTableId() {
		return tableId;
	}
	public String getZoomSizes() {
		return zoomSizes;
	}
	public List<FileBizParamDto> getBizParamDtos() {
		return bizParamDtos;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	public void setZoomSizes(String zoomSizes) {
		this.zoomSizes = zoomSizes;
	}
	
	private final List<FileBizParamDto> bizParamDtos = new ArrayList<FileBizParamDto>();

	public FileBizParamDto addFileBizParam(String code, String shopNo,
			Long userId, String tableId, String tableName, String zoomSizes,
			String imgData) {
		FileBizParamDto goodFile = new FileBizParamDto();
		goodFile.setCode(code);
		goodFile.setUserId(userId);
		goodFile.setShopNo(shopNo);
		goodFile.setTableId(tableId);
		goodFile.setTableName(tableName);
		goodFile.setZoomSizes(zoomSizes);
		goodFile.setFileLabel(fileLable);
		goodFile.setImgData(imgData);
		bizParamDtos.add(goodFile);
		return goodFile;
	}

	public FileBizParamDto addFileBizParam(String imgData) {
		return this.addFileBizParam(this.code, this.shopNo, this.userId,
				this.tableId, this.tableName, this.zoomSizes, imgData);
	}
	
	public FileBizParamDto addFileBizParam(String code, String imgData) {
		return this.addFileBizParam(code, this.shopNo, this.userId,
				this.tableId, this.tableName, this.zoomSizes, imgData);
	} 

	public List<FileBizParamDto> uploadToTFS() {
		AssertUtil.notEmpty(this.bizParamDtos, "No image data to upload.");
		String resultJson = PersistenceJobServiceLocator.getJobService(
				ImageService.class).addOrEdit(bizParamDtos);
		JSONObject jsonObject = JSONObject.fromObject(resultJson);
		AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")),
				"上传图片失败," + jsonObject.getString("info"));
		String dataJson = jsonObject.getString("data");
		List<FileBizParamDto> fbpDto = JsonUtil.toArrayBean(dataJson,
				FileBizParamDto.class);
		return fbpDto;
	}
}
