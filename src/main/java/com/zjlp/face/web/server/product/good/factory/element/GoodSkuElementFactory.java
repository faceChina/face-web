package com.zjlp.face.web.server.product.good.factory.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

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
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.dao.GoodDao;
import com.zjlp.face.web.server.product.good.dao.GoodPropertyDao;
import com.zjlp.face.web.server.product.good.dao.GoodSkuDao;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;

/**
 * SKU元素工厂
 * @ClassName: GoodSkuElementFactory 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author dzq
 * @date 2015年3月11日 下午3:34:08
 */
@Service("goodSkuElementFactory")
public class GoodSkuElementFactory implements ElementFactory<List<GoodSku>> {
	
	private static final String GOOD_VERSION="V_0.0.1"; 
	
	private Logger _logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private GoodSkuDao goodSkuDao;
	@Autowired
	private GoodDao goodDao;
	@Autowired
	private GoodPropertyDao goodPropertyDao;
	@Autowired
	private ImageService imageService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<GoodSku> create(GoodParam goodParam) throws GoodException {
		try {
			AssertUtil.notNull(goodParam.getId(), "发布商品SKU失败，商品主键为空！");
			AssertUtil.notNull(goodParam.getName(), "发布商品SKU失败，商品名称为空！");
			AssertUtil.notNull(goodParam.getMarketPrice(), "发布商品SKU失败，商品市场价格为空！");
			List<GoodSku> list = new ArrayList<GoodSku>();
			List<GoodSku> skuList = null;
			if (null !=  goodParam.getSkuList() && ! goodParam.getSkuList().isEmpty()) {
				//颜色尺寸类型Json格式
				skuList = goodParam.getSkuList();
				if(Constants.LEIMU_QITA.equals(goodParam.getGood().getClassificationId())) {
					//自定义非关键属性
					skuList = new ArrayList<GoodSku>(1);
					GoodSku goodSku = goodParam.getGoodSku();
					goodSku.setGoodId(goodParam.getId());
					goodSku.setServiceId(goodParam.getServiceId());
					goodSku.setMarketPrice(goodParam.getMarketPrice());
					goodSku.setSalesPrice(goodParam.getSalesPrice());
					goodSku.setStock(goodParam.getInventory());
					Map<String,GoodProperty> map=goodParam.getGoodPropertyMap();
					StringBuilder pvIds=new StringBuilder();
					StringBuilder pvNames=new StringBuilder();
					if(null!=map){
						for(GoodProperty gp:map.values()){
							pvIds.append(";"+gp.getId());
							pvNames.append(gp.getPropName()+":"+gp.getPropValueName()+";");
						}
						if(pvIds.length()>0)pvIds.append(";");
						if(pvNames.length()>0)pvNames.deleteCharAt(pvNames.length()-1);
					}
					goodSku.setSkuProperties(pvIds.toString());
					goodSku.setSkuPropertiesName(pvNames.toString());
					goodSku.setPreferentialPolicy(goodParam.getPreferentialPolicy());
					goodSku.setVersion(GOOD_VERSION);
					goodSku.setStatus(Constants.GOOD_STATUS_DEFAULT);
					goodSku.setCreateTime(goodParam.getDate());
					goodSku.setUpdateTime(goodParam.getDate());
					goodSkuDao.add(goodSku);
				   	AssertUtil.notNull(goodSku.getId(),"新增商品分类关联失败，商品ID:"+goodParam.getId());
				}else{
					for (GoodSku goodSku : skuList) {
						this._addGoodSku(goodParam, goodSku);
						list.add(goodSku);
					}
				}
			}
			
			if (_logger.isDebugEnabled()) {
				_logger.debug("create GOOD_SKU success! goodId is "+goodParam.getId());
			}
			goodParam.setSkuList(skuList);
			return list;
		} catch (RuntimeException e) {
			throw new GoodException(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<GoodSku> edit(GoodParam goodParam) throws GoodException{
		try {
			//参数验证
			AssertUtil.notNull(goodParam.getId(), "修改商品SKU失败，商品主键为空！");
			AssertUtil.notNull(goodParam.getClassificationId(), "修改商品SKU失败，商品主键为空！");
			//参数准备
			Long goodId = goodParam.getId();
			Good good=goodDao.getById(goodId);
			String version = String.valueOf(System.currentTimeMillis());
			Map<Long, GoodSku> compareMap = new HashMap<Long, GoodSku>();
			List<GoodSku> newList = goodParam.getSkuList();
			AssertUtil.notNull(newList, "修改商品SKU失败，商品newList为空！");
			List<GoodSku> oldList = goodSkuDao.findGoodSkusByGoodId(goodId);
			AssertUtil.notNull(oldList, "修改商品SKU失败，商品oldList为空！");
			//对象对比
			for(GoodSku goodSku : newList){
				if (null == goodParam.getPreferentialPolicy()) {
					goodParam.setPreferentialPolicy(0);
				}
				goodSku.setPreferentialPolicy(goodParam.getPreferentialPolicy());
				goodSku.setName(goodParam.getName());
				if(null == goodSku.getId()){
					if(Constants.LEIMU_QITA.equals(goodParam.getGood().getClassificationId())){
						GoodSku gs = goodParam.getGoodSku();
						gs.setGoodId(goodParam.getId());
						gs.setServiceId(goodParam.getServiceId());
						gs.setMarketPrice(goodParam.getMarketPrice());
						gs.setSalesPrice(goodParam.getSalesPrice());
						gs.setStock(goodParam.getInventory());
						Map<String,GoodProperty> map=goodParam.getGoodPropertyMap();
						StringBuilder pvIds=new StringBuilder();
						StringBuilder pvNames=new StringBuilder();
						if(null!=map){
							for(GoodProperty gp:map.values()){
								pvIds.append(";"+gp.getId());
								pvNames.append(gp.getPropName()+":"+gp.getPropValueName()+";");
							}
							if(pvIds.length()>0)pvIds.append(";");
							if(pvNames.length()>0)pvNames.deleteCharAt(pvNames.length()-1);
						}
						gs.setSkuProperties(pvIds.toString());
						gs.setSkuPropertiesName(pvNames.toString());
						gs.setPreferentialPolicy(goodParam.getPreferentialPolicy());
						gs.setVersion(GOOD_VERSION);
						gs.setStatus(Constants.GOOD_STATUS_DEFAULT);
						gs.setCreateTime(goodParam.getDate());
						gs.setUpdateTime(goodParam.getDate());
						goodSkuDao.add(goodSku);
					}else{
						// 新数据新增
						this._addGoodSku(goodParam, goodSku);
					}
				}else{
					if(Constants.LEIMU_QITA.equals(goodParam.getGood().getClassificationId())){
						GoodSku gs = goodParam.getGoodSku();
						gs.setGoodId(goodParam.getId());
						gs.setServiceId(goodParam.getServiceId());
						gs.setMarketPrice(goodParam.getMarketPrice());
						gs.setSalesPrice(goodParam.getSalesPrice());
						gs.setStock(goodParam.getInventory());
						Map<String,GoodProperty> map=goodParam.getGoodPropertyMap();
						StringBuilder pvIds=new StringBuilder();
						StringBuilder pvNames=new StringBuilder();
						if(null!=map){
							for(GoodProperty gp:map.values()){
								pvIds.append(";"+gp.getId());
								pvNames.append(gp.getPropName()+":"+gp.getPropValueName()+";");
							}
							if(pvIds.length()>0)pvIds.append(";");
							if(pvNames.length()>0)pvNames.deleteCharAt(pvNames.length()-1);
						}
						gs.setSkuProperties(pvIds.toString());
						gs.setSkuPropertiesName(pvNames.toString());
						gs.setPreferentialPolicy(goodParam.getPreferentialPolicy());
						gs.setVersion(GOOD_VERSION);
						gs.setStatus(Constants.GOOD_STATUS_DEFAULT);
						gs.setCreateTime(goodParam.getDate());
						gs.setUpdateTime(goodParam.getDate());
						//goodSkuDao.add(goodSku);
						compareMap.put(gs.getId(), gs);
					}else{
						// 已存在数据放入Map
						compareMap.put(goodSku.getId(), goodSku);
					}
				}
			}
			for(GoodSku goodSku : oldList){
				// 主键存在则修改，主键不存在则删除
				if(compareMap.containsKey(goodSku.getId())){
					// 主键存在，判断对象是否不等
//					if(!goodSku.equals(compareMap.get(goodSku.getId()))){
						// 参数不相同则修改商品版本
						GoodSku editSku = compareMap.get(goodSku.getId());
						//设置有销售属性的SKU
						this._setColorAndSize(goodParam.getId(), editSku);
						/** 当细项下架,商品上架,细项库存大于0时,把细项上架*/
						if(Constants.GOOD_STATUS_UNDERCARRIAGE .equals(goodSku.getStatus())
								&&Constants.GOOD_STATUS_DEFAULT.equals(good.getStatus())
								&&editSku.getStock()>0){
							editSku.setStatus(Constants.GOOD_STATUS_DEFAULT);
						}
						editSku.setVersion(version);
						editSku.setUpdateTime(goodParam.getDate());
						goodSkuDao.editGoodSku(editSku);
//					}
				}else{
					goodSkuDao.removeIdById(goodSku.getId());
				}
			}
			if (_logger.isDebugEnabled()) {
				_logger.debug("edit GOOD_SKU success! goodId is "+goodParam.getId());
			}
			return null;
		} catch (RuntimeException e) {
			throw new GoodException(e);
		}
	}
	@SuppressWarnings("unused")
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
	 * 新增Sku
	 * @Title: _addGoodSku 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId	
	 * @param goodSku
	 * @date 2015年3月25日 下午11:57:47  
	 * @author Administrator
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private void _addGoodSku(GoodParam goodParam,GoodSku goodSku){
		AssertUtil.isTrue(null!=goodSku.getStock()&&0<goodSku.getStock(), "发布商品SKU失败，商品库存: "+goodSku.getStock());
		AssertUtil.isTrue(null!=goodSku.getSalesPrice()&&0<goodSku.getSalesPrice(), "发布商品SKU失败，商品销售价格: "+goodSku.getSalesPrice());
		goodSku.setGoodId(goodParam.getId());//设置商品ID
		goodSku.setName(goodParam.getName());//名称
		goodSku.setVersion(GOOD_VERSION);
		goodSku.setServiceId(goodParam.getServiceId());
		goodSku.setMarketPrice(goodParam.getMarketPrice());
		//设置有销售属性的SKU
		// TODO
		this._setColorAndSize(goodParam.getId(), goodSku);
		//商品相关优惠
		goodSku.setPreferentialPolicy(goodParam.getPreferentialPolicy());
		goodSku.setStatus(Constants.GOOD_STATUS_DEFAULT);
		goodSku.setCreateTime(goodParam.getDate());
		goodSku.setUpdateTime(goodParam.getDate());
		goodSkuDao.add(goodSku);
	   	AssertUtil.notNull(goodSku.getId(),"新增商品分类关联失败，商品ID:"+goodParam.getId());
	}
	
	/**
	 * 设置有销售属性的Sku
	 * @Title: _setColorAndSize 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodId
	 * @param goodSku
	 * @date 2015年4月1日 下午3:18:27  
	 * @author Administrator
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private void _setColorAndSize(Long goodId,GoodSku goodSku){
		if (StringUtils.isNotBlank(goodSku.getColorId())) {
			// TODO
			GoodProperty colorProperty = goodPropertyDao.getByGoodIdAndPropValueId(goodId, Long.valueOf(goodSku.getColorId()));
			AssertUtil.notNull(colorProperty, "发布商品SKU失败，colorProperty为空！");
			GoodProperty sizeProperty = goodPropertyDao.getByGoodIdAndPropValueId(goodId, Long.valueOf(goodSku.getSizeId()));
			AssertUtil.notNull(sizeProperty, "发布商品SKU失败，sizeProperty为空！");
			StringBuffer skuProperties = new StringBuffer();
			skuProperties.append(colorProperty.getId()).append(";").append(sizeProperty.getId());
			goodSku.setSkuProperties(GoodSku.sortDbProperties(skuProperties.toString()));//前后增加";"排序后设置值
			StringBuffer skuPropertiesName = new StringBuffer();
			skuPropertiesName.append(colorProperty.getName()).append(";").append(sizeProperty.getName());
			goodSku.setSkuPropertiesName(skuPropertiesName.toString());
		}
	}
	
}