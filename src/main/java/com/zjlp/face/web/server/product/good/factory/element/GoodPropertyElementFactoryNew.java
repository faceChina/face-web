package com.zjlp.face.web.server.product.good.factory.element;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.dao.GoodPropertyDao;
import com.zjlp.face.web.server.product.good.dao.PropValueDao;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.Prop;
import com.zjlp.face.web.server.product.good.domain.PropValue;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;
import com.zjlp.face.web.server.product.good.service.PropService;
import com.zjlp.face.web.util.Logs;
/**
 * 四阶段商品规格属性元素工厂:单属性商品,双属性商品,三属性商品
 * @ClassName: GoodPropFactory 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author talo
 * @date 2015年7月7日 下午3:08:35
 */
@Service("goodPropertyElementFactoryNew")
public class GoodPropertyElementFactoryNew implements ElementFactory<GoodProperty[]> {
	
	private Logger _logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private GoodPropertyDao goodPropertyDao;
	@Autowired
	private PropService propService;
	@Autowired
	private PropValueDao propValueDao;
	
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GoodProperty[] create(GoodParam goodParam) throws GoodException {
		
		try {
			AssertUtil.notNull(goodParam.getId(), "发布商品Property失败，商品主键为空！");
			List<GoodSku> goodSkuList = goodParam.getSkuList();
			AssertUtil.notEmpty(goodSkuList, "发布商品Property失败，goodSkuList为空");
			Map<String, GoodProperty> goodPropertyMap = new HashMap<String, GoodProperty>();
			goodParam.setPropertyImgs(goodPropertyMap);
			for (GoodSku goodSku : goodSkuList) {
				String[] ids = StringUtils.split(goodSku.getSkuProperties(), ";");
				String[] names=StringUtils.split(goodSku.getName(),";");
				Map<String,String> map=new HashMap<String,String>();
				for(int i=0;i<ids.length;i++){
					map.put(ids[i], names[i]);
				}
				Arrays.sort(ids,new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						return Long.valueOf(o1).compareTo(Long.valueOf(o2));
					}
				});
				String sukProperty = ";";
				String propValueName = "";
				Date date = new Date();
				for (int i=0;i<ids.length;i++) {
					GoodProperty goodProperty=goodPropertyMap.get(ids[i]);
					if(goodProperty==null){
						PropValue propValue  = propValueDao.getById(Long.parseLong(ids[i]));
						AssertUtil.notNull(propValue, "操作商品时标准属性值不存在，操作失败！");
						goodProperty=new GoodProperty();
						goodProperty.setPropValueId(propValue.getId());
						goodProperty.setPropValueAlias(map.get(ids[i]));
						goodProperty.setGoodId(goodParam.getId());//设置商品ID
						goodProperty.setCreateTime(date);
						goodProperty.setUpdateTime(date);
						goodProperty.setStatus(Constants.GOOD_STATUS_DEFAULT);
						
						//设置标准属性
						goodProperty.setPropValueCode(propValue.getCode());
						goodProperty.setPropValueName(propValue.getName());
						Prop prop = propService.getPropById(propValue.getPropertyId());
						AssertUtil.notNull(prop, "操作商品时标准属性不存在，操作失败！");
						goodProperty.setPropName(prop.getName());//设置属性名称
						goodProperty.setPropId(prop.getId());//设置属性ID
						goodProperty.setIsColorProp(prop.getIsColorProp());
						goodProperty.setIsEnumProp(prop.getIsEnumProp());
						goodProperty.setIsInputProp(prop.getIsInputProp());
						goodProperty.setIsKeyProp(prop.getIsKeyProp());
						goodProperty.setIsSalesProp(prop.getIsSalesProp());
						goodProperty.setIsStandard(prop.getIsStandard());
						goodProperty.setName(this._getName(goodProperty));
						if(prop.getIsColorProp()){
							goodProperty.setPicturePath(goodSku.getPicturePath());
						}
						goodPropertyDao.add(goodProperty);
					   	AssertUtil.notNull(goodProperty.getId(),"操作商品Property失败，商品ID:"+goodParam.getId());
						goodPropertyMap.put(ids[i], goodProperty); 
					}
					sukProperty += Long.toString(goodProperty.getId())+";";
					propValueName += goodProperty.getPropName()+":"+goodProperty.getPropValueAlias()+";";
				}
				goodSku.setSkuProperties(sukProperty); // 用goodProperty id 替换Properties值
				propValueName = propValueName.substring(0,propValueName.length() - 1);
				goodSku.setSkuPropertiesName(propValueName); //颜色:白色;服装尺寸:M
			}
			return null;
		} catch (Exception e) {
			Logs.print(goodParam);
			throw new GoodException(e.getMessage(),e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GoodProperty[] edit(GoodParam goodParam) throws GoodException{
		try {
			AssertUtil.notNull(goodParam.getId(), "编辑商品Property失败，商品主键为空！");
			List<GoodProperty> oldList = goodPropertyDao.findGoodPropertyListByGoodId(goodParam.getId());
			AssertUtil.notEmpty(oldList, "编辑商品Property失败，oldList为空");
			List<GoodSku> goodSkuList = goodParam.getSkuList();
			AssertUtil.notEmpty(goodSkuList, "发布商品Property失败，goodSkuList为空");
			Map<String, GoodProperty> oldMap = new HashMap<String, GoodProperty>();
			Map<String, GoodProperty> newMap = new HashMap<String, GoodProperty>();
			goodParam.setPropertyImgs(newMap);
			for(GoodProperty gp:oldList){
				oldMap.put(String.valueOf(gp.getPropValueId()), gp);
			}
			for (GoodSku goodSku : goodSkuList) {
				String[] ids = goodSku.getSkuProperties().split(";");
				String[] names=goodSku.getName().split(";");
				Map<String,String> map=new HashMap<String,String>();
				for(int i=0;i<ids.length;i++){
					map.put(ids[i], names[i]);
				}
				Arrays.sort(ids,new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						return Long.valueOf(o1).compareTo(Long.valueOf(o2));
					}
				});
				Date date = new Date();
				String propValueIds=";";
				String propValueName = "";
				for (int i=0;i<ids.length;i++) {
					GoodProperty goodProperty=oldMap.get(ids[i]);
					if(goodProperty==null){
						goodProperty=new GoodProperty();
						PropValue propValue  = propValueDao.getById(Long.parseLong(ids[i]));
						goodProperty.setPropValueId(propValue.getId());
						goodProperty.setPropValueAlias(map.get(ids[i]));
						goodProperty.setGoodId(goodParam.getId());//设置商品ID
						Prop prop = propService.getPropById(propValue.getPropertyId());
						goodProperty.setPropName(prop.getName());//设置属性名称
						goodProperty.setPropId(prop.getId());//设置属性ID
						goodProperty.setIsColorProp(prop.getIsColorProp());
						goodProperty.setIsEnumProp(prop.getIsEnumProp());
						goodProperty.setIsInputProp(prop.getIsInputProp());
						goodProperty.setIsKeyProp(prop.getIsKeyProp());
						goodProperty.setIsSalesProp(prop.getIsSalesProp());
						goodProperty.setIsStandard(prop.getIsStandard());
						goodProperty.setName(this._getName(goodProperty));
						goodProperty.setCreateTime(date);
						goodProperty.setUpdateTime(date);
						goodProperty.setStatus(Constants.GOOD_STATUS_DEFAULT);
						if(prop.getIsColorProp()){
							goodProperty.setPicturePath(goodSku.getPicturePath());
						}
						goodPropertyDao.add(goodProperty);
					}else{
						if((names[i]!=null&&!names[i].equals(goodProperty.getPropValueAlias()))||
								(goodSku.getPicturePath()!=null&&!goodSku.getPicturePath().equals(goodProperty.getPicturePath()))
								||goodSku.getPicturePath()!=goodProperty.getPicturePath()){
							Prop prop = propService.getPropById(goodProperty.getPropId());
							GoodProperty edit=new GoodProperty();
							edit.setId(goodProperty.getId());
							edit.setName(this._getName(goodProperty));
							edit.setPropValueAlias(map.get(ids[i]));
							edit.setIsColorProp(prop.getIsColorProp());
							edit.setIsEnumProp(prop.getIsEnumProp());
							edit.setIsInputProp(prop.getIsInputProp());
							edit.setIsKeyProp(prop.getIsKeyProp());
							edit.setIsSalesProp(prop.getIsSalesProp());
							edit.setIsStandard(prop.getIsStandard());
							edit.setUpdateTime(date);
							if(prop.getIsColorProp()){
								goodProperty.setPicturePath(goodSku.getPicturePath());
							}
							goodPropertyDao.edit(edit);
						}
					}
					propValueIds+=goodProperty.getId()+";";
					propValueName += goodProperty.getPropName()+":"+goodProperty.getPropValueAlias()+";";
					newMap.put(ids[i], goodProperty);
					oldMap.put(ids[i], goodProperty);
					newMap.put(goodProperty.getId().toString(), goodProperty);
				}
				goodSku.setSkuProperties(propValueIds);
				goodSku.setSkuPropertiesName(propValueName.substring(0,propValueName.length() - 1));
			}
			for(String id:oldMap.keySet()){
				if(!newMap.containsKey(id)){
					goodPropertyDao.removeById(oldMap.get(id).getId());
				}
			}
			if (_logger.isDebugEnabled()) {
				_logger.debug("create GoodProperty success! goodId is "+goodParam.getId());
			}
			return null;
		} catch (Exception e) {
			Logs.print(goodParam);
			throw new GoodException(e.getMessage(),e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private String _getName(GoodProperty goodProperty){
		if (StringUtils.isBlank(goodProperty.getPropValueAlias())) {
			goodProperty.setPropValueAlias(goodProperty.getPropValueName());
		}
		StringBuilder name = new StringBuilder();
		name.append(goodProperty.getPropName()).append(":").append(goodProperty.getPropValueAlias());
		return name.toString();
	}

}
