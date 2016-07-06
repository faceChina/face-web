package com.zjlp.face.web.server.product.good.factory.element;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.dao.GoodPropertyDao;
import com.zjlp.face.web.server.product.good.dao.GoodSkuDao;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodImg;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;
import com.zjlp.face.web.server.product.good.service.GoodImgService;
import com.zjlp.face.web.server.product.good.service.GoodPropertyService;
import com.zjlp.face.web.server.product.good.service.GoodService;
import com.zjlp.face.web.server.product.good.service.GoodSkuService;

@Service("goodFileElementFactory")
public class GoodFileElementFactory  implements ElementFactory<GoodParam>{
	
	public static final String GOOD_TABLE_NAME="GOOD";
	
	public static final String GOOD_SKU_TABLE_NAME="GOOD_SKU";
	
	public static final String GOOD_PROPERTY_TABLE_NAME="GOOD_PROPERTY";
	
	public static final String IMAGE_SPLIT_TARGET =",";
	
	
	private Logger _logger = Logger.getLogger(this.getClass());

	@Autowired
	private GoodService goodService;
	@Autowired
	private GoodSkuService goodSkuService;
	@Autowired
	private GoodSkuDao goodSkuDao;
	/** 本地保存图片*/
	@Autowired
	private GoodImgService goodImgService;
	/** tfs保存图片*/
	@Autowired
	private ImageService imageService;
	@Autowired
	private GoodPropertyDao goodPropertyDao;
	@Autowired
	private GoodPropertyService goodPropertyService;
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GoodParam create(GoodParam goodParam) throws GoodException {
		try {
			/** 参数验证 */
			this._checkedParam(goodParam);
			/** 初始化 */
			List<FileBizParamDto> bizParamDtos = new ArrayList<FileBizParamDto>();
			
			/** 组装图片信息 */
			// 1.组装商品主图
			Map<Integer, GoodImg> imgMap = this._goodOperate(goodParam, bizParamDtos);
          
         	// 2.组装商品属性图片
	     	Map<String, GoodProperty> propertyMap = this._propertyOperate(goodParam, bizParamDtos);
			
         	// 3.组装ubb图片

         	this._ubbOperate(goodParam, bizParamDtos);
			
         	/** 上传图片信息 */
    		StopWatch clock1 = new StopWatch();
    		clock1.start(); // 计时开始
            List<FileBizParamDto> fbpDto = this._uploadToTFS(bizParamDtos);
            clock1.stop(); // 计时结束
			_logger.info("严重警告！！！该方法_uploadToTFS执行耗费:" + clock1.getTime() + " ms ");

        	StopWatch clock = new StopWatch();
			clock.start(); // 计时开始
         	/** 回调更新图片信息 */
            if (null != fbpDto && !fbpDto.isEmpty()) {
                for (FileBizParamDto fileBizParamDto : fbpDto) {
                	// 1. 参数检查
                	if (StringUtils.isBlank(fileBizParamDto.getCode()) || StringUtils.isBlank(fileBizParamDto.getTableId())
                		|| StringUtils.isBlank(fileBizParamDto.getImgData())) {
                		_logger.warn("返回数据时参数不完整");
						continue;
					}
                	// 2.更新商品图片地址
                	GoodImg firstImg  = null;
                	if (ImageConstants.GOOD_FILE.equals(fileBizParamDto.getCode()) && Long.valueOf(fileBizParamDto.getTableId()).longValue() == goodParam.getId()) {
                		String[] imgPath = fileBizParamDto.getImgData().split(IMAGE_SPLIT_TARGET);
                		int index = 0;
                		for (GoodImg goodImg : imgMap.values()) {
                			goodImg.setUrl(imgPath[index]);
                			goodImg.setGoodId(goodParam.getId());
                			goodImg.setCreateTime(goodParam.getDate());
                			goodImgService.addOrEdit(goodImg);
                			AssertUtil.notNull(goodImg.getId(), "持久化商品主图失败！");
                			index++;
						}
                	 	//更新商品首图
                		firstImg =  imgMap.get(0);
                		AssertUtil.notNull(firstImg, "商品主图为空，操作失败！");
                		Good firstImgGood  = new Good();
                		firstImgGood.setId(goodParam.getId());
                		firstImgGood.setPicUrl(firstImg.getUrl());
                     	goodService.editGood(firstImgGood);
					}
                	// 3.更新商品SKU图片地址
                	if (null!=goodParam.getHasSalesProp()&&goodParam.getHasSalesProp()) {
                		if (ImageConstants.GOOD_PROPERTY_FILE.equals(fileBizParamDto.getCode())
                				&& propertyMap.containsKey(fileBizParamDto.getTableId()) ) {
							GoodProperty goodProperty = propertyMap.get(fileBizParamDto.getTableId());
							goodProperty.setPicturePath(fileBizParamDto.getImgData());
							goodPropertyDao.edit(goodProperty);
							String proerptyIdStr = GoodProperty.getProerptyIdStr(goodProperty.getId());
							GoodSku querySku = new GoodSku();
							querySku.setGoodId(goodParam.getId());
							querySku.setSkuProperties(proerptyIdStr);
							List<GoodSku> goodSkuList = goodSkuDao.selectGoodskuByGoodIdAndPrprerty(querySku);
							AssertUtil.notEmpty(goodSkuList, "找不到相应的Sku信息,goodId :"+ goodParam.getId() +" proerptyIdStr :" +proerptyIdStr);
							for (GoodSku goodSku : goodSkuList) {
								goodSku.setPicturePath(fileBizParamDto.getImgData());
								goodSkuDao.editGoodSku(goodSku);
							}
                		}
					}else {//没有属性图片的情况SKU图片为商品主图
						List<GoodSku> goodSkuList = goodSkuService.findGoodSkusByGoodId(goodParam.getId());
						if (null != goodSkuList && !goodSkuList.isEmpty()) {
							for (GoodSku goodSku : goodSkuList) {
								goodSku.setPicturePath(imgMap.get(0).getUrl());
								goodSkuDao.edit(goodSku);
							}
						}
					}
                	
                	// 4.更新商品UBB图片地址
    				if(ImageConstants.UBB_FILE.equals(fileBizParamDto.getCode()) 
    						&& Long.valueOf(fileBizParamDto.getTableId()).longValue() == goodParam.getId()){
    					Good goodContent = new Good();
    					goodContent.setId(goodParam.getId());
    					goodContent.setGoodContent(fileBizParamDto.getImgData());
    					goodService.editGood(goodContent);
    				}
                }
			}
        	clock.stop(); // 计时结束
			_logger.info("严重警告！！！该回调更新图片执行耗费:" + clock.getTime() + " ms ");
            return null;
        } catch (Exception e) {
        	_logger.error("", e);
			throw new GoodException(e.getMessage(),e);
        }
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GoodParam edit(GoodParam goodParam) throws GoodException {
		/** 参数验证 */
		this._checkedParam(goodParam);
		/** 初始化 */
		Map<Long, GoodImg> compareMap = new LinkedHashMap<Long, GoodImg>();
		/** 图片对比 */
		List<GoodImg> dbImgList =goodImgService.findByGoodIdAndType(goodParam.getId(), 1);
		//迭代参数中传送的图片
		for (GoodImg goodImg : goodParam.getGoodImgs()) {
			if (StringUtils.isBlank(goodImg.getUrl())) continue;
			if (null != goodImg.getId()) {
				//已存在的图片放入Map
				compareMap.put(goodImg.getId(), goodImg);
			}
		}
		//迭代数据库中的图片
		for (GoodImg goodImg : dbImgList) {
			if (!compareMap.containsKey(goodImg.getId())) {
				//删除数据
				goodImgService.deleteById(goodImg.getId());
			}
		}
		/** tfs上传部分 */
		this.create(goodParam);
		return null;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void _checkedParam(GoodParam goodParam){
		AssertUtil.notNull(goodParam.getUserId(), "布商品图片失败，用户Id为空！");
		AssertUtil.notNull(goodParam.getId(), "发布商品图片失败，商品主键为空！");
		AssertUtil.hasLength(goodParam.getShopNo(), "发布商品图片失败，店铺号为空！");
		GoodImg[] goodImgs = goodParam.getGoodImgs();
		AssertUtil.notNull(goodImgs, "发布商品图片失败，getImgData为空");
		AssertUtil.notNull(goodImgs[0], "发布商品图片失败，商品首图不能为空");
	}
	
	/**
	 * 上传图片接收返回值
	 * @Title: _uploadToTFS 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param bizParamDtos
	 * @return
	 * @date 2015年3月31日 下午8:34:25  
	 * @author Administrator
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private List<FileBizParamDto> _uploadToTFS(List<FileBizParamDto> bizParamDtos){
		String resultJson =  imageService.addOrEdit(bizParamDtos);
     	JSONObject jsonObject = JSONObject.fromObject(resultJson);
     	AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败,"+jsonObject.getString("info"));
        String dataJson = jsonObject.getString("data");
        List<FileBizParamDto> fbpDto = JsonUtil.toArrayBean(dataJson,  FileBizParamDto.class);
		return fbpDto;
	}
	
	/**
	 * 商品图片组装操作
	 * @Title: goodOperate 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodParam
	 * @param bizParamDtos
	 * @date 2015年3月31日 下午8:31:48  
	 * @author Administrator
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private Map<Integer, GoodImg> _goodOperate(GoodParam goodParam,List<FileBizParamDto> bizParamDtos){
     	Map<Integer, GoodImg> imgMap = new LinkedHashMap<Integer, GoodImg>();
		FileBizParamDto goodFile = this._getFileBizParam(ImageConstants.GOOD_FILE,goodParam.getShopNo()
      		  ,goodParam.getUserId(),goodParam.getId(),GOOD_TABLE_NAME,ImageConstants.ZOOM_IMG_640_640);
       	StringBuilder imgData = new StringBuilder();
       	for (GoodImg goodImg : goodParam.getGoodImgs()) {
       		if (StringUtils.isBlank(goodImg.getUrl())) continue;
       		goodImg.setType(1);
       		imgData.append(goodImg.getUrl()).append(IMAGE_SPLIT_TARGET);
       		imgMap.put(goodImg.getPosition(), goodImg);
       	}
       	if(imgData.length()>0){
       		imgData.delete(imgData.length()-1, imgData.length());
       	}
       	goodFile.setImgData(imgData.toString());
       	bizParamDtos.add(goodFile);
       	return imgMap;
	}
	
	
	/**
	 * 商品sku图片组装
	 * @Title: sku 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodParam
	 * @param bizParamDtos
	 * @return
	 * @date 2015年3月31日 下午8:23:58  
	 * @author Administrator
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private Map<String, GoodProperty> _propertyOperate(GoodParam goodParam,List<FileBizParamDto> bizParamDtos){
    	Map<String, GoodProperty> propertyMap = new LinkedHashMap<String, GoodProperty>();
     	if (null != goodParam.getPropertyImgs() && !goodParam.getPropertyImgs().isEmpty()) {
     		for (GoodProperty goodProperty : goodParam.getPropertyImgs().values()) {
     			if (StringUtils.isBlank(goodProperty.getPicturePath())||null == goodProperty.getPropValueId()) continue;
     			Long propertyId = goodProperty.getId();
     			if (null != propertyId) {
					//上传文件
	     			FileBizParamDto propertyFile = this._getFileBizParam(ImageConstants.GOOD_PROPERTY_FILE,goodParam.getShopNo()
	            		  ,goodParam.getUserId(),propertyId,GOOD_PROPERTY_TABLE_NAME,ImageConstants.ZOOM_IMG_640_640);
	     			propertyFile.setImgData(goodProperty.getPicturePath());
	     			GoodProperty.setIsNull(goodProperty);
	     			propertyMap.put(String.valueOf(propertyId), goodProperty);
	     			bizParamDtos.add(propertyFile);
				}else {
					GoodProperty dbProperty = goodPropertyService.getGoodPropertiesByGoodIdAndPropValueId(goodParam.getId(),goodProperty.getPropValueId());
	     			AssertUtil.notNull(dbProperty, "找不到相应的商品属性");
					//上传文件
	     			FileBizParamDto propertyFile = this._getFileBizParam(ImageConstants.GOOD_PROPERTY_FILE,goodParam.getShopNo()
	            		  ,goodParam.getUserId(),dbProperty.getId(),GOOD_PROPERTY_TABLE_NAME,ImageConstants.ZOOM_IMG_640_640);
	     			propertyFile.setImgData(goodProperty.getPicturePath());
	     			propertyMap.put(String.valueOf(dbProperty.getId()), dbProperty);
	     			bizParamDtos.add(propertyFile);
				}
     		}
     	}
     	return propertyMap;
	}
	
	/**
	 * 商品ubb图片操作
	 * @Title: _ubbOperate 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodParam
	 * @param bizParamDtos
	 * @date 2015年3月31日 下午8:30:06  
	 * @author Administrator
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private void _ubbOperate(GoodParam goodParam,List<FileBizParamDto> bizParamDtos){
    	FileBizParamDto ubbFile = this._getFileBizParam(ImageConstants.UBB_FILE,goodParam.getShopNo()
        		  ,goodParam.getUserId(),goodParam.getId(),GOOD_TABLE_NAME,null);
       	ubbFile.setImgData(goodParam.getGoodContent());
       	bizParamDtos.add(ubbFile);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	private FileBizParamDto _getFileBizParam(String code,String shopNo,Long userId,Long tableId,String tableName,String ZoomSizes){
		FileBizParamDto goodFile = new FileBizParamDto();
		goodFile.setCode(code);
		goodFile.setUserId(userId);
        goodFile.setShopNo(shopNo);
        goodFile.setTableId(String.valueOf(tableId));
        goodFile.setTableName(tableName);
        goodFile.setZoomSizes(ZoomSizes);
        goodFile.setFileLabel(1);
        return goodFile;
	}
}
