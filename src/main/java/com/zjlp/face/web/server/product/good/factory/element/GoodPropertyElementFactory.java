package com.zjlp.face.web.server.product.good.factory.element;

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
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.Prop;
import com.zjlp.face.web.server.product.good.domain.PropValue;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;
import com.zjlp.face.web.server.product.good.service.PropService;
/**
 * 商品规格属性元素工厂
 * @ClassName: GoodPropFactory 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月11日 下午3:08:35
 */
@Service("goodPropertyElementFactory")
public class GoodPropertyElementFactory implements ElementFactory<GoodProperty[]> {
	
	private Logger _logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private GoodPropertyDao goodPropertyDao;
	@Autowired
	private PropService propService;
	
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GoodProperty[] create(GoodParam goodParam) throws GoodException {
		try {
			AssertUtil.notNull(goodParam.getId(), "发布商品Property失败，商品主键为空！");
			Map<String, GoodProperty> goodPropertyMap = goodParam.getGoodPropertyMap();
			AssertUtil.notEmpty(goodPropertyMap, "发布商品Property失败，goodPropertyMap为空");
			for (GoodProperty goodProperty : goodPropertyMap.values()) {
				this.add(goodParam.getId(), goodProperty,goodParam.getDate());
			}
			if (_logger.isDebugEnabled()) {
				_logger.debug("create GoodProperty success! goodId is "+goodParam.getId());
			}
			return null;
		} catch (Exception e) {
			throw new GoodException(e.getMessage(),e);
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public GoodProperty[] edit(GoodParam goodParam) throws GoodException{
		try {
			AssertUtil.notNull(goodParam.getId(), "编辑商品Property失败，商品主键为空！");
			Map<String, GoodProperty> goodPropertyMap = goodParam.getGoodPropertyMap();
			AssertUtil.notEmpty(goodPropertyMap, "发布商品Property失败，goodPropertyMap为空");
			List<GoodProperty> oldList = goodPropertyDao.findGoodPropertyListByGoodId(goodParam.getId());
			AssertUtil.notEmpty(oldList, "编辑商品Property失败，oldList为空");
			Map<Long, GoodProperty> containMap = new HashMap<Long, GoodProperty>();
			for (GoodProperty goodProperty : goodPropertyMap.values()) {
				//销售属性验证
				if (goodParam.getHasSalesProp()) {
					if (null ==  goodProperty.getPropValueId()) continue;
				}else {
					//组合属性：非关键属性中的输入属性
					goodProperty.setIsInputProp(true);//输入属性
					goodProperty.setIsKeyProp(false);//非关键属性
					goodProperty.setIsEnumProp(false);//非枚举属性
					goodProperty.setIsStandard(false);//非标准属性
					goodProperty.setIsColorProp(false);//非颜色属性
					goodProperty.setIsSalesProp(false);//非销售属性
				}
				if (null == goodProperty.getId()) {
					//没有ID的新增
					this.add(goodParam.getId(), goodProperty,goodParam.getDate());
				}else {
					//已存在的数据
					containMap.put(goodProperty.getId(), goodProperty);
				}
			}
			for (GoodProperty goodProperty : oldList) {
				// 主键存在则修改，主键不存在则删除
				if (containMap.containsKey(goodProperty.getId())) {
					// 主键存在，判断对象是否不等
					if (!goodProperty.equals(containMap.get(goodProperty.getId()))) {
						GoodProperty pageGoodProperty = containMap.get(goodProperty.getId());
						goodProperty.setPropValueAlias(pageGoodProperty.getPropValueAlias());;//设置商品ID
						goodProperty.setUpdateTime(goodParam.getDate());
						goodProperty.setName(this._getName(goodProperty));
						goodPropertyDao.edit(goodProperty);
					}
				}else {
					//不存在的
					goodPropertyDao.removeById(goodProperty.getId());
				}
			}
			if (_logger.isDebugEnabled()) {
				_logger.debug("create GoodProperty success! goodId is "+goodParam.getId());
			}
			return null;
		} catch (Exception e) {
			throw new GoodException(e.getMessage(),e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	private void add(Long goodId,GoodProperty goodProperty,Date date){
		goodProperty.setGoodId(goodId);//设置商品ID
		goodProperty.setCreateTime(date);
		goodProperty.setUpdateTime(date);
		goodProperty.setStatus(Constants.GOOD_STATUS_DEFAULT);
		if (null != goodProperty.getPropValueId()) {
			//设置标准属性
			PropValue propValue = propService.getPropValueById(goodProperty.getPropValueId());
			AssertUtil.notNull(propValue, "操作商品时标准属性值不存在，操作失败！");
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
			goodPropertyDao.add(goodProperty);
		   	AssertUtil.notNull(goodProperty.getId(),"操作商品Property失败，商品ID:"+goodId);
		   	return ;
		}
		//设置非关键属性
		goodProperty.setName(this._getName(goodProperty));
		goodProperty.setIsInputProp(true);//输入属性
		goodPropertyDao.add(goodProperty);
	   	AssertUtil.notNull(goodProperty.getId(),"操作商品Property失败，商品ID:"+goodId);
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
