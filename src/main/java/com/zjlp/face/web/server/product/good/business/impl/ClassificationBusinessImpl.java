package com.zjlp.face.web.server.product.good.business.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.ClassificationException;
import com.zjlp.face.web.server.product.good.business.ClassificationBusiness;
import com.zjlp.face.web.server.product.good.domain.Classification;
import com.zjlp.face.web.server.product.good.domain.GoodProperty;
import com.zjlp.face.web.server.product.good.domain.Prop;
import com.zjlp.face.web.server.product.good.domain.PropValue;
import com.zjlp.face.web.server.product.good.domain.vo.ClassificationVo;
import com.zjlp.face.web.server.product.good.service.ClassificationService;
import com.zjlp.face.web.server.product.good.service.GoodPropertyService;
import com.zjlp.face.web.server.product.good.service.PropService;
@Service
public class ClassificationBusinessImpl implements ClassificationBusiness {

    private Log _logger = LogFactory.getLog(ClassificationBusinessImpl.class);
	
	@Autowired
	private ClassificationService classificationService;
	
	@Autowired
	private PropService propService;
	
	@Autowired
	private GoodPropertyService goodPropertyService;
	
	
	@Override
	public Classification getClassificationById(Long id) throws ClassificationException {
		try {
			AssertUtil.notNull(id, "id不能为空！");
			return classificationService.getClassificationById(id);
		} catch (Exception e) {
			throw new ClassificationException(e.getMessage(),e);
		}
	}

	@Override
	public List<Classification> findClassificationByPid(Long pid, Integer level) throws ClassificationException {
		try {
			AssertUtil.notNull(pid, "pid不能为空！");
			AssertUtil.notNull(pid, "level不能为空！");
			return classificationService.findClassificationByPid(pid, level);
		} catch (Exception e) {
			throw new ClassificationException(e.getMessage(),e);
		}
	}

	@Override
	public List<PropValue> findPropValuesByPropId(Long propId)
			throws ClassificationException {
		try {
			AssertUtil.notNull(propId, "属性ID不能为空！");
			return propService.findPropValuesByPropId(propId);
		} catch (Exception e) {
			throw new ClassificationException(e.getMessage(),e);
		}
	}

	@Override
	public Boolean hasSalesProp(Long classificationId)
			throws ClassificationException {
		try {
			AssertUtil.notNull(classificationId, "类目ID不能为空！");
			return propService.hasSalesProp(classificationId);
		} catch (Exception e) {
			throw new ClassificationException(e.getMessage(),e);
		}
	}

	@Override
	public Boolean hasProtocolClassification(Long classificationId)
			throws ClassificationException {
		try {
			AssertUtil.notNull(classificationId, "类目ID不能为空！");
			return classificationService.hasProtocolClassification(classificationId);
		} catch (Exception e) {
			throw new ClassificationException(e.getMessage(),e);
		}
	}

	@Override
	public ClassificationVo getLatestClassification(String shopNo)
			throws ClassificationException {
		try {
			AssertUtil.hasLength(shopNo, "店铺号不能不能为空！");
			return classificationService.getLatestClassification(shopNo);
		} catch (Exception e) {
			throw new ClassificationException(e.getMessage(),e);
		}
	}

	@Override
	public String getClassificationName(Classification child,String name)throws ClassificationException {
		AssertUtil.notNull(child, "类目不能为空！");
		
		if(StringUtils.isBlank(name)){
			name = child.getName();
		}else{
			name = child.getName() + ">" + name;
		}
		
		if(!child.getParentId().equals(0L)){
			Classification parent = this.getClassificationById(child.getParentId());
			return getClassificationName(parent,name);
		}
		
		return name;
	}

	@Override
	public List<Prop> findPropsByClassificationId(Long classificationId)
			throws ClassificationException {
		try {
			AssertUtil.notNull(classificationId, "类目ID不能为空！");
			return propService.findPropsByClassificationId(classificationId);
		} catch (Exception e) {
			_logger.error(e.getMessage(),e);
			throw new ClassificationException(e.getMessage(),e);
		}
	}
	
	private List<Prop> findPropsByClassificationIdToSort(Long classificationId){
		List<Prop> propList = this.findPropsByClassificationId(classificationId);
		
		if (null == propList || propList.isEmpty()) {
			return null;
		}
		
		Collections.sort(propList, new Comparator<Prop>() {
			@Override
			public int compare(Prop prop1, Prop prop2) {
//				if(prop1.getSort()<prop2.getSort()){
//					if(!prop2.getIsColorProp()){
//						return -1;
//					}else{
//						return 1;
//					}
//				}else{
//					if(!prop1.getIsColorProp()){
//						return 1;
//					}else{
//						return -1;
//					}
//				}
				
				if(prop1.getIsColorProp()){
					return -1;
				}else{
					return 1;
				}
			}
		});
		
		return propList;
	}

	@Override
	public Map<Prop, List<PropValue>> findAllPropByClassificationId(
			Long classificationId) throws ClassificationException {
		try {
			AssertUtil.notNull(classificationId, "类目ID不能为空！");
//			Map<Prop,List<PropValue>> map = new TreeMap<Prop, List<PropValue>>(new Comparator<Prop>() {
//				@Override
//				public int compare(Prop prop1, Prop prop2) {
//					if(prop1.getSort()<prop2.getSort()){
//						if(!prop2.getIsColorProp()){
//							return -1;
//						}else{
//							return 1;
//						}
//					}else{
//						if(!prop1.getIsColorProp()){
//							return 1;
//						}else{
//							return -1;
//						}
//					}
//				}
//			});
//			List<Prop> propList = this.findPropsByClassificationId(classificationId);
			
			Map<Prop,List<PropValue>> map = new LinkedHashMap<Prop, List<PropValue>>();
			List<Prop> propList = this.findPropsByClassificationIdToSort(classificationId);
//			if (null == propList || propList.isEmpty()) {
//				return null;
//			}
//			Collections.sort(propList);
			for (Prop prop : propList) {
				 List<PropValue>  propValueList = this.findPropValuesByPropId(prop.getId());
				 map.put(prop, propValueList);
			}
			return map;
		} catch (Exception e) {
			_logger.error(e.getMessage(),e);
			throw new ClassificationException(e.getMessage(),e);
		}
	}
	
	@Override
	public Map<String, List<GoodProperty>> findAllPropNameByClassificationId(Long classificationId) throws ClassificationException {
		try {
			AssertUtil.notNull(classificationId, "类目ID不能为空！");
			Map<String, List<GoodProperty>> map = new LinkedHashMap<String, List<GoodProperty>>();
//			List<Prop> propList = this.findPropsByClassificationId(classificationId);
			List<Prop> propList = this.findPropsByClassificationIdToSort(classificationId);
			if (null == propList || propList.isEmpty()) {
				return null;
			}
			
//			Collections.sort(propList, new Comparator<Prop>() {
//				@Override
//				public int compare(Prop prop1, Prop prop2) {
//					if(prop1.getSort()<prop2.getSort()){
//						if(!prop2.getIsColorProp()){
//							return -1;
//						}else{
//							return 1;
//						}
//					}else{
//						if(!prop1.getIsColorProp()){
//							return 1;
//						}else{
//							return -1;
//						}
//					}
//				}
//			});
			
			for (Prop prop : propList) {
				map.put(prop.getName(), new ArrayList<GoodProperty>());
			}
			
			return map;
		} catch (Exception e) {
			_logger.error(e.getMessage(),e);
			throw new ClassificationException(e.getMessage(),e);
		}
	}
}
