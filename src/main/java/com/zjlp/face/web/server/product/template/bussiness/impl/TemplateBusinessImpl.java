package com.zjlp.face.web.server.product.template.bussiness.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.TemplateException;
import com.zjlp.face.web.server.product.material.domain.GroupList;
import com.zjlp.face.web.server.product.material.service.GroupListService;
import com.zjlp.face.web.server.product.template.bussiness.TemplateBusiness;
import com.zjlp.face.web.server.product.template.domain.CarouselDiagram;
import com.zjlp.face.web.server.product.template.domain.Modular;
import com.zjlp.face.web.server.product.template.domain.OwTemplate;
import com.zjlp.face.web.server.product.template.domain.TemplateDetail;
import com.zjlp.face.web.server.product.template.domain.dto.CarouselDiagramDto;
import com.zjlp.face.web.server.product.template.domain.dto.OwTemplateDto;
import com.zjlp.face.web.server.product.template.domain.vo.ModularColorVo;
import com.zjlp.face.web.server.product.template.domain.vo.TemplateVo;
import com.zjlp.face.web.server.product.template.service.OwTemplateService;
import com.zjlp.face.web.server.product.template.service.impl.OwTemplateServiceImpl;
import com.zjlp.face.web.util.Logs;

@Service
public class TemplateBusinessImpl implements TemplateBusiness {
	
	@Autowired
	private OwTemplateService owTemplateService;
	@Autowired
    private GroupListService groupListService;
	@Autowired(required=false)
	private ImageService imageService;
	
	/** 模板类型（1：热门、2：行业、3：其他）*/
	public static final Integer HOT_CLASSIFICATION = 1;
	public static final Integer INDUSTRY_CLASSIFICATION = 2;
	public static final Integer OTHER_CLASSIFICATION = 3;
	/** 自定义模块是否有文字颜色 */
	private static final Integer NAME_COLOR_CUSTOM = 1;
	/** 模块类型（1：标准、2：自定义） */
	private static final Integer MODULAR_TYPE_STANDARD = 1;
	private static final Integer MODULAR_TYPE_CUSTOM = 2;

	@Override
	public void initTemplate(String shopNo, Integer shopType) throws TemplateException{
		try{
			
        }catch(Exception e){
            throw new TemplateException(e);
        }
		
	}
	
	@Override
	public List<OwTemplateDto> findOwTemplateBySubCode(String subCode, Integer classification)
			throws TemplateException {
		try {
			AssertUtil.notNull(subCode, "参数subCode为空");
			AssertUtil.notNull(classification, "参数classification为空");
			List<OwTemplateDto> templateDtos = new ArrayList<OwTemplateDto>();
			// 微官网模板列表
			if(Constants.WGW_CODE.equals(subCode)) {
				// 热门
				if(HOT_CLASSIFICATION.equals(classification)) {
					// 微官网首页热门模板列表
					templateDtos = owTemplateService.findWgwHpHotOwTemplate();
				// 行业
				} else {
					
				}
			// 微官网商城模板列表
			} else if(Constants.GW_WSC_CODE.equals(subCode)) {
				// 热门
				if(HOT_CLASSIFICATION.equals(classification)) {
					// 微官网首页热门模板列表
					templateDtos = owTemplateService.findWgwScHpHotOwTemplate();
				// 行业
				} else {
					
				}
			// 微商城模板列表
			} else if(Constants.WSC_CODE.equals(subCode)) {
				// 热门
				if(HOT_CLASSIFICATION.equals(classification)) {
					// 微官网首页热门模板列表
					templateDtos = owTemplateService.findWscHpHotOwTemplate();
				// 行业
				} else {
					
				}
			} else if(Constants.GT_WSC.equals(subCode)) {
				templateDtos = owTemplateService.findGtOwTemplate();
			}
			
			return templateDtos;
		} catch (Exception e) {
			throw new TemplateException(e);
		}
	}

	@Override
	public void switchTemplate(String shopNo, String code)
			throws TemplateException {
		 try{
			 AssertUtil.notNull(shopNo, "店铺编号为空");
	         AssertUtil.notNull(code, "模板CODE为空");
	         // 切换模板
	         owTemplateService.switchTemplate(shopNo, code);
	        }catch(Exception e){
	            throw new TemplateException(e);
	        }
	}

	@Override
	public OwTemplateDto getCurrentHomePageOwTemplate(String shopNo,
			Integer shopType) throws TemplateException {
		 try{
	        AssertUtil.notNull(shopNo, "店铺编号为空");
	        AssertUtil.notNull(shopType, "店铺类型为空");
	        OwTemplateDto owTemplateHp = new OwTemplateDto();
	        //查询热门模板或者行业模板
	    	OwTemplate owTemplate = owTemplateService.getCurrentTemplate(shopNo, shopType);
	    	AssertUtil.notNull(owTemplate.getId(), "当前首页模板为空");
			// 查询模型细项
			TemplateDetail templateDetail = this.getTemplateDetailByTemplateId(owTemplate.getId());
			// 组装模板规则
			if(null != templateDetail) {
				owTemplateHp = new OwTemplateDto(owTemplate.getCode(), templateDetail.getShopStrokesPath());
				owTemplateHp.setTemplateDetail(templateDetail);
			} else {
				owTemplateHp = new OwTemplateDto(owTemplate.getCode(), null);
			}
			owTemplateHp.setId(owTemplate.getId());
	        return owTemplateHp;
        }catch(Exception e){
        	Logs.error(e);
            throw new TemplateException(e);
        }
	}

	@Override
	public OwTemplateDto getCurrentGoodTypePageOwTemplate(String shopNo,
			Integer shopType) throws TemplateException {
		try {
			AssertUtil.notNull(shopNo, "店铺编号为空");
	        AssertUtil.notNull(shopType, "店铺类型为空");
	        OwTemplateDto owTemplateGt = new OwTemplateDto();
			if(1 == shopType || 2 == shopType || 4 == shopType){
	            shopType = 4;
	        } else{
	            throw new TemplateException("shopType参数异常");
	        }
			//查询商品分类页模板
	        OwTemplate owTemplate = owTemplateService.getCurrentTemplate(shopNo, shopType);
	        if(null == owTemplate){
	            throw new TemplateException("未找到当前模板");
	        }
	        owTemplateGt = new OwTemplateDto(owTemplate.getCode());
	        owTemplateGt.setId(owTemplate.getId());
	        return owTemplateGt;
		} catch (Exception e) {
			throw new TemplateException(e);
		}
	}
	
	@Override
	public OwTemplate getCurrentHomePageOwTemplateForFree(String shopNo,
			Integer shopType) throws TemplateException {
		AssertUtil.notNull(shopNo, "店铺编号为空");
        AssertUtil.notNull(shopType, "店铺类型为空");
        //查询热门模板或者行业模板
    	OwTemplate owTemplate = owTemplateService.getCurrentTemplate(shopNo, shopType);
		return owTemplate;
	}

	@Override
	public List<Modular> findBaseModular(String subCode)
			throws TemplateException {
		try{
            AssertUtil.notNull(subCode, "subCode");
            // 查询基本模块
            List<Modular> list = owTemplateService.findBaseModular(subCode);
            AssertUtil.notEmpty(list, "模块基础数据为空");
            return list;
        }catch(Exception e){
            throw new TemplateException(e);
        }
	}

	@Override
	public List<Modular> findModularByOwTemplateId(Long owTemplateId,Integer modularType)
			throws TemplateException {
		try {
			AssertUtil.notNull(owTemplateId, "模板id为空");
			// 根据模板id查询模块列表
			List<Modular> list = owTemplateService.findModularByOwTemplateId(owTemplateId);
			List<Modular> newlist = new ArrayList<Modular>();
            for(Modular modular : list){
                if(StringUtils.isBlank(modular.getCode()) || modular.getType().intValue() != modularType.intValue()){
                    continue;
                }
                // 编辑模块信息
                modular = this._getModular(modular);
                newlist.add(modular);
            }
            return newlist;
		} catch (Exception e) {
			throw new TemplateException(e);
		}
	}

	@SuppressWarnings({ "unchecked", "static-access", "deprecation" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = { "TemplateException" })
	public Long saveModuarl(Modular modular, OwTemplateDto owTemplateDto, String shopNo,Long userId)
			throws TemplateException {
		Assert.notNull(modular);
		Assert.notNull(owTemplateDto);
		Assert.hasLength(shopNo);
		Assert.notNull(userId);
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
		try{
            AssertUtil.notNull(modular, "模块对象为空");
            AssertUtil.isTrue(!(null == modular.getId() && owTemplateDto.getIsChange() == 0) || modular.getType().intValue() == Constants.MODULAR_CUSTOM.intValue(), "模块数量不可增减");
            // 编辑模块信息
            modular = _modularHandle(modular);
            Long id = owTemplateService.saveModuarl(modular, shopNo);
            
            if(null != modular.getImgPath() && null != modular.getType()) {
            	 //上传图片到TFS
            	String zoomSizes = null;
            	if(MODULAR_TYPE_STANDARD.equals(modular.getType())) {
            		zoomSizes = owTemplateDto.getModularImageSize();
            	} else {
            		zoomSizes = owTemplateDto.getCustomModularImageSize();
            	}
                FileBizParamDto dto = new FileBizParamDto();
                dto.setImgData(modular.getImgPath());
                dto.setZoomSizes(zoomSizes);
                dto.setUserId(userId);
                dto.setShopNo(shopNo);
                dto.setTableName("MODULAR");
                dto.setTableId(id.toString());
                dto.setCode(ImageConstants.MODULAR_FILE);
                dto.setFileLabel(1);
                list.add(dto);
                
                String flag = imageService.addOrEdit(list);
                
                JSONObject jsonObject = JSONObject.fromObject(flag);
                AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
                String dataJson = jsonObject.getString("data");
                JSONArray jsonArray = JSONArray.fromObject(dataJson);
                List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);

                if(StringUtils.isNotBlank(fbpDto.get(0).getImgData())){
	                Modular editModular = new Modular();
	                editModular.setId(id);
	                editModular.setImgPath(fbpDto.get(0).getImgData());
	                owTemplateService.editModularImgPath(editModular);
                }
            }
            return id;
        }catch(Exception e){
        	//回滚TFS
        	if(null != list){
        		imageService.remove(list);
        	}
            throw new TemplateException(e);
        }
	}

	@Override
	public void deleteModular(Long id,OwTemplateDto owTemplateHp) throws TemplateException{
		try{
            AssertUtil.notNull(id, "参数id为空");
            Modular modular = owTemplateService.getModularById(id);
            AssertUtil.isTrue(owTemplateHp.getIsChange() == 1 || modular.getType().intValue() == Constants.MODULAR_CUSTOM.intValue(), "当前模版模块不可删");
            // 验证该编号的模块是不是当前模板的模块
            AssertUtil.isTrue(modular.getOwTemplateId().intValue() == owTemplateHp.getId().intValue(), "验证该编号的模块是不是当前模板的模块");
            owTemplateService.deleteModular(id);
        }catch(Exception e){
            throw new TemplateException(e);
        }
	}
	
	@Override
	public void resetModular(Long owTemplateId) throws TemplateException {
		try{
            AssertUtil.notNull(owTemplateId, "模板id为空");
            // 模块重置
            owTemplateService.resetModular(owTemplateId);
        }catch(Exception e){
            throw new TemplateException(e);
        }
	}

	@Override
	public void sortModular(Long subId, Long tarId) throws TemplateException {
		try {
			AssertUtil.notNull(subId, "模块id为空");
			AssertUtil.notNull(tarId, "模块id为空");
			// 模块排序
			owTemplateService.sortModular(subId, tarId);
		} catch (Exception e) {
			throw new TemplateException(e);
		}
		
	}
	
	@Override
	public Modular getModularById(Long modularId) throws TemplateException {
		Modular modular = owTemplateService.getModularById(modularId);
		if(null != modular.getCode()){
			// 编辑模块信息
			modular = this._getModular(modular);
		}
		return modular;
	}
	
	@Override
	public void changeShopStrokes(OwTemplateDto owTemplateDto) throws TemplateException{
		
	}

	@Override
	public List<CarouselDiagramDto> findCarouselDiagramByOwTemplateId(
			Long owTemplateId) throws TemplateException {
		Assert.notNull(owTemplateId, "模版编号为空");
		try{
            List<CarouselDiagramDto> list = owTemplateService.findCarouselDiagramByOwTemplateId(owTemplateId);
            for(CarouselDiagramDto dto : list){
                if(StringUtils.isBlank(dto.getCode())){
                    continue;
                }
                /**单选 单个商品分类*/
        		if(dto.getCode().indexOf(Constants.MODULAR_SHOPTYPE) != -1){
        			if(StringUtils.isNotBlank(dto.getResourcePath())){
        				String[] param = dto.getResourcePath().split("=");
        				if(param.length > 1){
        					String id = dto.getResourcePath().split("=")[1];
        					dto.setBindIds(id);
        				}
        			}
        		}
        		/** 单选 预约 */
        		if(dto.getCode().indexOf(Constants.MODULAR_APPOINTMENT) != -1) {
        			if(StringUtils.isNotBlank(dto.getResourcePath())){
        				String uri = dto.getResourcePath();
        				String idPath = uri.substring(dto.getResourcePath().lastIndexOf("/")+1);
        				String[] param = idPath.split("\\.");
        				if(param.length > 1){
        					String id = param[0];
        					dto.setBindIds(id);
        				}
        			}
        		}
        		/**多选*/
                if(dto.getCode().indexOf(Constants.MODULAR_GOOD) != -1
        				||dto.getCode().indexOf(Constants.MODULAR_SHOPTYPES) != -1){
        			if(StringUtils.isNotBlank(dto.getResourcePath())){
        				String[] param = dto.getResourcePath().split("=");
        				if(param.length > 1){
        					List<GroupList> gls = groupListService.findByGroupId(Long.valueOf(param[1]));
        					StringBuffer sb = new StringBuffer();
        					for(GroupList groupList : gls){
        						sb.append(groupList.getPartId()).append(",");
        					}
        					dto.setBindIds(sb.toString());
        				}
        			}
        		}
                /*
                if(dto.getCode().endsWith(Constants.MODULAR_SHOPTYPE) || dto.getCode().indexOf(Constants.MODULAR_GOOD) != -1){
        			if(StringUtils.isNotBlank(dto.getResourcePath())){
        				String[] resourcePaths = dto.getResourcePath().split("\\?");
        				if(resourcePaths.length > 1){
        					String id = resourcePaths[1].split("=")[1];
        					if(StringUtils.isBlank(id)){
        						throw new TemplateException("解析商品分类或商品对应ID异常");
        					}
        					dto.setBindIds(id);
        				}
        				// resourcePath为/app/shopNo/good/detail/goodId.htm时解析goodId
        				if(StringUtils.isBlank(dto.getBindIds()) && dto.getResourcePath().indexOf("/") != -1 && dto.getResourcePath().indexOf(".", dto.getResourcePath().lastIndexOf("/")) != -1){
        					String str = dto.getResourcePath().substring(dto.getResourcePath().lastIndexOf("/") + 1, dto.getResourcePath().lastIndexOf("."));
        					if(str.matches("^\\d+$")){
        						dto.setBindIds(str);
        					}
        				}
        				//链接至全部商品
        				if(dto.getResourcePath().endsWith("/good/list.htm")){
        					dto.setBindIds("0");
        				}
        			}
        		}
        		if(dto.getCode().endsWith(Constants.MODULAR_SHOPTYPES)){
        			if(StringUtils.isNotBlank(dto.getResourcePath())){
        				String resourcePath = dto.getResourcePath();
        				List<GroupList> gls = groupListService.findByGroupId(Long.valueOf(resourcePath.substring(resourcePath.lastIndexOf("=") + 1)));
        				StringBuffer sb = new StringBuffer();
        				for(GroupList groupList : gls){
        					sb.append(groupList.getPartId()).append(",");
        				}
        				dto.setBindIds(sb.toString());
        			}
        		}*/
    			
    			if(null != dto.getImgPath() && dto.getImgPath().indexOf(Constants.BASE_PIC_URL) != -1){
    				dto.setIsBasePic(1);
    			}
    		}
            return list;
        }catch(Exception e){
            throw new TemplateException(e);
        }
	}

	@Override
	public CarouselDiagram getCarouselDiagramById(Long id)
			throws TemplateException {
		Assert.notNull(id, "模版编号为空");
		try{
            return owTemplateService.getCarouselDiagramById(id);
        }catch(Exception e){
            throw new TemplateException(e);
        }
	}

	@Override
	public Long addOrEditCarouselDiagram(CarouselDiagram carouselDiagram,
			OwTemplateDto owTemplateHp, String shopNo) throws TemplateException {
		return null;
	}

	@SuppressWarnings({ "unchecked", "static-access", "deprecation" })
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "TemplateException" })
	public void addOrEditCarouselDiagram(List<CarouselDiagramDto> list, 
			String shopNo,Long userId,OwTemplateDto owTemplateHp) throws TemplateException {
		List<FileBizParamDto> bizParamDtos = new ArrayList<FileBizParamDto>();
		try{
            AssertUtil.notEmpty(list,  "数据为空");
            AssertUtil.hasLength(shopNo,"shopNo为空");
            //保存或者修改
            for(int i =0;i<list.size();i++){
            	CarouselDiagramDto dto = list.get(i);
            	if(StringUtils.isBlank(dto.getImgPath()))continue;
                Date date = new Date();
                dto.setUpdateTime(date);
                dto = _carouselDiagramHandle(dto, shopNo);
                CarouselDiagram carouselDiagram = (CarouselDiagram)dto;
                Long id = null;
                if(null == carouselDiagram.getId()){
                    carouselDiagram.setCreateTime(date);
                    id = owTemplateService.addCarouselDiagram(dto,shopNo);
                }else{
                	id = carouselDiagram.getId();
                	owTemplateService.editCarouselDiagram(dto,shopNo);
                }
                //上传图片到TFS
                FileBizParamDto bizParamDto = new FileBizParamDto();
                bizParamDto.setImgData(carouselDiagram.getImgPath());
                bizParamDto.setZoomSizes(owTemplateHp.getCarouselImageSize());
                bizParamDto.setUserId(userId);
                bizParamDto.setShopNo(shopNo);
                bizParamDto.setTableName("CAROUSEL_DIAGRAM");
                bizParamDto.setTableId(id.toString());
                bizParamDto.setCode(ImageConstants.MODULAR_FILE);
                bizParamDto.setFileLabel(1);
                bizParamDtos.add(bizParamDto);
            }
            
            String flag = imageService.addOrEdit(bizParamDtos);
            JSONObject jsonObject = JSONObject.fromObject(flag);
            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
            String dataJson = jsonObject.getString("data");
            JSONArray jsonArray = JSONArray.fromObject(dataJson);
            List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
            
            for (FileBizParamDto fileBizParamDto : fbpDto) {
            	if(StringUtils.isNotBlank(fileBizParamDto.getImgData())){
	            	CarouselDiagramDto cd = new CarouselDiagramDto();
	            	cd.setId(Long.valueOf(fileBizParamDto.getTableId()));
	            	cd.setImgPath(fileBizParamDto.getImgData());
	            	owTemplateService.editCarouselDiagram(cd,shopNo);
            	}
			}
        }catch(Exception e){
        	if(null != bizParamDtos){
        		imageService.remove(bizParamDtos);
        	}
            throw new TemplateException(e);
        }
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteCarouselDiagramHp(Long id, OwTemplateDto owTemplateHp,Long userId,String shopNo)
			throws TemplateException {
		try{
            AssertUtil.notNull(id, "轮播图编号为空");
            CarouselDiagram cd = owTemplateService.getCarouselDiagramById(id);
            // 验证当前编号的轮播图是否是当前模板下的轮播图
            AssertUtil.isTrue(cd.getOwTemplateId().intValue() == owTemplateHp.getId().intValue(),  "轮播图编号不匹配");
            owTemplateService.deleteCarouselDiagram(id);
            //删除TFS上的图片
            List<FileBizParamDto> bizParamDtos = new ArrayList<FileBizParamDto>();
            FileBizParamDto bizParamDto = new FileBizParamDto();
            bizParamDto.setUserId(userId);
            bizParamDto.setShopNo(shopNo);
            bizParamDto.setTableName("CAROUSEL_DIAGRAM");
            bizParamDto.setTableId(id.toString());
            bizParamDto.setCode(ImageConstants.MODULAR_FILE);
            bizParamDto.setFileLabel(1);
            bizParamDtos.add(bizParamDto);
            imageService.remove(bizParamDtos);
        }catch(Exception e){
            throw new TemplateException(e);
        }
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteCarouselDiagramGt(Long id, OwTemplateDto owTemplateGt,Long userId,String shopNo)
			throws TemplateException {
		try{
            AssertUtil.notNull(id, "轮播图编号为空");
            CarouselDiagram cd = owTemplateService.getCarouselDiagramById(id);
            // 验证当前编号的轮播图是否是当前模板下的轮播图
            AssertUtil.isTrue(cd.getOwTemplateId().intValue() == owTemplateGt.getId().intValue(), "轮播图编号不匹配");
            owTemplateService.deleteCarouselDiagram(id);
            //删除TFS上的图片
            List<FileBizParamDto> bizParamDtos = new ArrayList<FileBizParamDto>();
            FileBizParamDto bizParamDto = new FileBizParamDto();
            bizParamDto.setUserId(userId);
            bizParamDto.setShopNo(shopNo);
            bizParamDto.setTableName("CAROUSEL_DIAGRAM");
            bizParamDto.setTableId(id.toString());
            bizParamDto.setCode(ImageConstants.MODULAR_FILE);
            bizParamDto.setFileLabel(1);
            bizParamDtos.add(bizParamDto);
            imageService.remove(bizParamDtos);
        }catch(Exception e){
            throw new TemplateException(e);
        }
	}

	/**
	 * 编辑模块信息
	 * @Title: _modularHandle 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param modular
	 * @return
	 * @throws TemplateException
	 * @date 2015年3月21日 下午5:02:07  
	 * @author ah
	 */
	private Modular _modularHandle(Modular modular) throws TemplateException{
        try{
            AssertUtil.notNull(modular, "模块对象为空");
            if(StringUtils.isBlank(modular.getCode())){
                return modular;
            }
            // 背景颜色编辑
			if(null != modular.getBackgroundColor() && !"".equals(modular.getBackgroundColor()) && !modular.getBackgroundColor().startsWith("#")){
				modular.setBackgroundColor("#" + modular.getBackgroundColor());
			}
			// 模块中文名称颜色
			if(null != modular.getNameZhColor() && !"".equals(modular.getNameZhColor()) && !modular.getNameZhColor().startsWith("#")){
				modular.setNameZhColor("#" + modular.getNameZhColor());
			}
			// 模块英文名称颜色
			if(null != modular.getNameEnColor() && !"".equals(modular.getNameEnColor()) && !modular.getNameEnColor().startsWith("#")){
				modular.setNameEnColor("#" + modular.getNameEnColor());
			}
			//商品分类
			if(modular.getCode().indexOf(Constants.MODULAR_SHOPTYPES) != -1){
				String[] ids = modular.getBindIds().split(",");
				if(ids.length == 1){
					modular.setCode(modular.getCode().replace(Constants.MODULAR_SHOPTYPES, Constants.MODULAR_SHOPTYPE));
				}else{
					List<GroupList> grouplists = new ArrayList<GroupList>();
					for(String id : ids){
						GroupList gl = new GroupList();
						gl.setPartId(Long.valueOf(id));
						gl.setAssociationTable("SHOP_TYPE");
						grouplists.add(gl);
					}
					Long groupId = groupListService.addGroupList(grouplists);
					modular.setBindIds(groupId.toString());
				}
			}
			//商品
			if(modular.getCode().indexOf(Constants.MODULAR_GOOD) != -1){
				String[] ids = modular.getBindIds().split(",");
				List<GroupList> grouplists = new ArrayList<GroupList>();
				for(String id : ids){
					GroupList gl = new GroupList();
					gl.setPartId(Long.valueOf(id));
					gl.setAssociationTable("GOOD");
					grouplists.add(gl);
				}
				Long groupId = groupListService.addGroupList(grouplists);
				modular.setBindIds(groupId.toString());
			}
            return modular;
        }catch(Exception e){
            throw new TemplateException(e);
        }
    }

	private Modular _getModular(Modular modular) {
		/**单选 文章，文章分类，文章栏目，相册，相册专辑*/
		if(modular.getCode().indexOf(Constants.MODULAR_NEWSLIST) != -1
				||modular.getCode().indexOf(Constants.MODULAR_ARTICLECATEGORY) != -1
				||modular.getCode().indexOf(Constants.MODULAR_ARICLECOLUMN) != -1
				||modular.getCode().indexOf(Constants.MODULAR_ALBUM) != -1
				||modular.getCode().indexOf(Constants.MODULAR_PHOTOALBUM) != -1
				||modular.getCode().indexOf(Constants.MODULAR_SHOPTYPE) != -1){
			if(StringUtils.isNotBlank(modular.getResourcePath())){
				String[] param = modular.getResourcePath().split("=");
				if(param.length > 1){
					String id = modular.getResourcePath().split("=")[1];
					modular.setBindIds(id);
				}
			}
		}
		/** 单选 预约 */
		if(modular.getCode().indexOf(Constants.MODULAR_APPOINTMENT) != -1) {
			if(StringUtils.isNotBlank(modular.getResourcePath())){
				String uri = modular.getResourcePath();
				String idPath = uri.substring(modular.getResourcePath().lastIndexOf("/")+1);
				String[] param = idPath.split("\\.");
				if(param.length > 1){
					String id = param[0];
					modular.setBindIds(id);
				}
			}
		}
			
		/**多选 商品，商品分类*/
		if(modular.getCode().indexOf(Constants.MODULAR_GOOD) != -1
				||modular.getCode().indexOf(Constants.MODULAR_SHOPTYPES) != -1){
			if(StringUtils.isNotBlank(modular.getResourcePath())){
				String[] param = modular.getResourcePath().split("=");
				if(param.length > 1){
					List<GroupList> gls = groupListService.findByGroupId(Long.valueOf(param[1]));
					StringBuffer sb = new StringBuffer();
					for(GroupList groupList : gls){
						sb.append(groupList.getPartId()).append(",");
					}
					modular.setBindIds(sb.toString());
				}
			}
		}
		
		if(null != modular.getImgPath() && modular.getImgPath().indexOf(Constants.BASE_PIC_URL) != -1){
			modular.setIsBasePic(1);
		}
		return modular;
	}
	
	/**
     * 处理添加修改轮播图
     * 
     * @Title: _carouselDiagramHandle
     * @Description: (这里用一句话描述这个方法的作用)
     * @param caroselDiagram
     * @return
     * @throws TemplateException
     * @date 2014年7月29日 下午5:41:33
     * @author Administrator
     */
    private CarouselDiagramDto _carouselDiagramHandle(CarouselDiagramDto caroselDiagram, String shopNo) throws TemplateException{
        try{
            if(StringUtils.isBlank(caroselDiagram.getCode())){
                return caroselDiagram;
            }
            //商品分类
			if(caroselDiagram.getCode().indexOf(Constants.MODULAR_SHOPTYPES) != -1){
				String[] ids = caroselDiagram.getBindIds().split(",");
				if(ids.length == 1){
					caroselDiagram.setCode(caroselDiagram.getCode().replace(Constants.MODULAR_SHOPTYPES, Constants.MODULAR_SHOPTYPE));
				}else{
					List<GroupList> grouplists = new ArrayList<GroupList>();
					for(String id : ids){
						GroupList gl = new GroupList();
						gl.setPartId(Long.valueOf(id));
						gl.setAssociationTable("SHOP_TYPE");
						grouplists.add(gl);
					}
					Long groupId = groupListService.addGroupList(grouplists);
					caroselDiagram.setBindIds(groupId.toString());
				}
			}
			if(caroselDiagram.getCode().indexOf(Constants.MODULAR_GOOD) != -1){
				String[] ids = caroselDiagram.getBindIds().split(",");
				List<GroupList> grouplists = new ArrayList<GroupList>();
				for(String id : ids){
					GroupList gl = new GroupList();
					gl.setPartId(Long.valueOf(id));
					gl.setAssociationTable("GOOD");
					grouplists.add(gl);
				}
				Long groupId = groupListService.addGroupList(grouplists);
				caroselDiagram.setBindIds(groupId.toString());
			}
			if(caroselDiagram.getCode().indexOf(Constants.MODULAR_APPOINTMENT) != -1){
				caroselDiagram.setBindIds(caroselDiagram.getBindIds());
			}
            return caroselDiagram;
        }catch(Exception e){
            throw new TemplateException(e);
        }
    }


	@Override
	public TemplateVo getTemplateDtoByShop(String shopNo, Integer shopType)
			throws TemplateException {
		try{
            AssertUtil.notNull(shopNo, "店铺编号为空");
            AssertUtil.notNull(shopType, "店铺类型为空");
            TemplateVo dto = new TemplateVo();
            // 首页模板获取
            OwTemplateDto owTemplateHp = this.getCurrentHomePageOwTemplate(shopNo, shopType);
            AssertUtil.notNull(owTemplateHp, "模板信息为空");
            // 获取模板对应的模块
            List<Modular> modularList = this.findAllModularByOwTemplateId(owTemplateHp);
            // 组装模块颜色
			List<ModularColorVo> list = _generateColor(modularList, owTemplateHp);
			Integer nameColorCustom = owTemplateHp.getIsNameZhColorCustom();
			if(null != nameColorCustom && NAME_COLOR_CUSTOM==nameColorCustom) {
				// 标准模块颜色
				List<Modular> standardModularList = this._generateModularList(modularList, MODULAR_TYPE_STANDARD);
				List<ModularColorVo> standardModularColor = _generateColor(standardModularList, owTemplateHp);
				String json = JSONArray.fromObject(standardModularColor).toString();
				dto.setJson(json);				// 自定义模块颜色
				List<Modular> customModularList = this._generateModularList(modularList, MODULAR_TYPE_CUSTOM);
				List<ModularColorVo> customModularCustom = _generateColor(customModularList, owTemplateHp);
				String jsonCustom = JSONArray.fromObject(customModularCustom).toString();
				dto.setJsonCustom(jsonCustom);
			} else {
				String json = JSONArray.fromObject(list).toString();
				dto.setJson(json);
			}
            // 获取模板对应的轮播图
            List<CarouselDiagramDto> carouselList = this.findCarouselDiagramByOwTemplateId(owTemplateHp.getId());
            if(null != carouselList) {
            	for(CarouselDiagram carouselDiagram : carouselList){
            		if(StringUtils.isNotBlank(carouselDiagram.getImgPath())) {
            			carouselDiagram.setImgPath(ImageConstants.getCloudstZoomPath(carouselDiagram.getImgPath(), owTemplateHp.getCarouselImageSize()));
            		}
                    // 链接url
                    if(StringUtils.isNotBlank(carouselDiagram.getCode()) && 
                    		carouselDiagram.getCode().indexOf(Constants.LINKURL) != -1){
                    	carouselDiagram.setResourcePath(OwTemplateServiceImpl._coverUrlLink(carouselDiagram.getResourcePath()));
                    }else{
                    	carouselDiagram.setResourcePath(OwTemplateServiceImpl._coverPath(carouselDiagram.getResourcePath(), shopNo));
                    }
                }
            }
            dto.setOwTemplateHp(owTemplateHp);
            dto.setCarouselList(carouselList);
            dto.setModularList(modularList);
            return dto;
        }catch(Exception e){
        	Logs.error(e);
            throw new TemplateException(e);
        }
	}
	
	private List<Modular> _generateModularList(
			List<Modular> modularList, Integer modularType) {
		List<Modular> modulars = new ArrayList<Modular>();
		for (Modular modular : modularList) {
			if(null != modular.getType() && modularType.equals(modular.getType())) {
				modulars.add(modular);
			}
		}
		return modulars;
	}

	/**
	 * 查询所有模块
	 * @Title: findAllModularByOwTemplateId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param owTemplateId
	 * @param modularType
	 * @return
	 * @throws TemplateException
	 * @date 2015年3月26日 下午2:33:06  
	 * @author ah
	 */
	public List<Modular> findAllModularByOwTemplateId(OwTemplateDto owTemplateHp)
			throws TemplateException {
		try {
			AssertUtil.notNull(owTemplateHp.getId(), "模板id为空");
			// 根据模板id查询模块列表
			List<Modular> list = owTemplateService.findModularByOwTemplateId(owTemplateHp.getId());
			List<Modular> newlist = new ArrayList<Modular>();
            for(Modular modular : list){
                if(StringUtils.isBlank(modular.getCode())){
                    continue;
                }
                modular = this._getModular(modular);
                // 编辑模块信息
                if(null != owTemplateHp && StringUtils.isNotBlank(modular.getImgPath())){
                	if(MODULAR_TYPE_STANDARD.equals(modular.getType())) {
						if ("0".equals(owTemplateHp.getIsRegular().toString())) {
        	                modular.setImgPath(ImageConstants.getCloudstZoomPath(modular.getImgPath(), owTemplateHp.getModularWidths()[modular.getSort() - 1] + "_" + owTemplateHp.getModularHeights()[modular.getSort() - 1]));
        	            }else{
        	                modular.setImgPath(ImageConstants.getCloudstZoomPath(modular.getImgPath(), owTemplateHp.getModularImageSize()));
        	            }
                	} else {
                		modular.setImgPath(ImageConstants.getCloudstZoomPath(modular.getImgPath(), owTemplateHp.getCustomModularImageSize()));
                	}
            	}
                newlist.add(modular);
            }
            return newlist;
		} catch (Exception e) {
			throw new TemplateException(e);
		}
	}
	
	/**
	 * 组装颜色列表
	 * @Title: _generateColor 
	 * @Description: (组装颜色列表) 
	 * @param modularList
	 * @param owTemplateHp
	 * @return
	 * @date 2015年3月26日 下午2:49:43  
	 * @author ah
	 */
	public static List<ModularColorVo> _generateColor(List<Modular> modularList, OwTemplateDto owTemplateHp) {
		List<ModularColorVo> list = new ArrayList<ModularColorVo>();
        for(Modular modular : modularList){
//        	if(null != owTemplateHp && StringUtils.isNotBlank(modular.getImgPath())){
//	            if("0".equals(owTemplateHp.getIsRegular())){
//	                modular.setImgPath(ImageConstants.getCloudstZoomPath(modular.getImgPath(), owTemplateHp.getModularWidths()[modular.getSort() - 1] + "_" + owTemplateHp.getModularHeights()[modular.getSort() - 1]));
//	            }else{
//	                modular.setImgPath(ImageConstants.getCloudstZoomPath(modular.getImgPath(), owTemplateHp.getModularImageSize()));
//	            }
//        	}
			ModularColorVo c = new ModularColorVo();
			String nameZhColor = modular.getNameZhColor();
			if(StringUtils.isNotBlank(nameZhColor) && !nameZhColor.startsWith("#")){
				nameZhColor = "#" + nameZhColor;
			}
			c.setColor(nameZhColor);
			String nameEnColor = modular.getNameEnColor();
			if(StringUtils.isNotBlank(nameEnColor) && !nameEnColor.startsWith("#")){
				nameEnColor = "#" + nameEnColor;
			}
			c.setColoren(nameEnColor);
            String str="#FFFFFF";
            if(null!=modular.getBackgroundColor()){
            	str=modular.getBackgroundColor();
            }
			if(!str.startsWith("#")){
				str = "#" + str;
			}
			c.setRed(new BigInteger(str.substring(1, 3), 16).toString());
			c.setGreen(new BigInteger(str.substring(3, 5), 16).toString());
			c.setBlue(new BigInteger(str.substring(5, 7), 16).toString());
			c.setOpacity(String.valueOf((modular.getTransparency() == null ? 100 : modular.getTransparency()) / 100.0));
			list.add(c);
        }
        
        return list;
	}

	@Override
	public TemplateDetail getTemplateDetailByTemplateId(Long id) throws TemplateException{
		try {
			AssertUtil.notNull(id, "模板id为空");
			// 根据模板id查询模板细项
			TemplateDetail templateDetail = owTemplateService.getTemplateDetailByTemplateId(id);
			if(null != templateDetail) {
				// 编辑店招路径
				templateDetail.setShopStrokesPath(OwTemplateDto._generateShopStrokes(
						templateDetail.getShopStrokesPath()));
				if(StringUtils.isNotBlank(templateDetail.getShopStrokesPath()) && templateDetail.getShopStrokesPath().indexOf(Constants.BASE_PIC_URL) != -1){
					templateDetail.setIsBasePic(1);
				}
				
				String fontColor = templateDetail.getFontColor();
				// 文字颜色
				if(StringUtils.isNotBlank(fontColor) && !fontColor.startsWith("#")){
					fontColor = "#" + fontColor;
					templateDetail.setFontColor(fontColor);
				}
				// 背景颜色
				String backgroundColor = templateDetail.getBackgroundColor();
				if(StringUtils.isNotBlank(backgroundColor) && !backgroundColor.startsWith("#")){
					backgroundColor = "#" + backgroundColor;
					templateDetail.setBackgroundColor(backgroundColor);
				}
			}
			return templateDetail;
		} catch (Exception e) {
			e.printStackTrace();
			throw new TemplateException(e);
		}
	}
    
    

	@SuppressWarnings({ "static-access", "unchecked", "deprecation" })
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void editTemplateDetail(TemplateDetail templateDetail, String shopNo, Long userId) throws TemplateException{
		AssertUtil.notNull(templateDetail.getId(), "参数模板细项为空");
		if(null != templateDetail.getShopStrokesPath()) {
			List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
			// 组装上传图片参数
			FileBizParamDto dto = this._generateFileBizParamDto(templateDetail.getShopStrokesPath(),
					PropertiesUtil.getContexrtParam(ImageConstants.SHOP_STROKES_FILE),userId,shopNo,
					"TEMPLATEDETAIL", templateDetail.getId().toString(), ImageConstants.SHOP_STROKES_FILE, Constants.FILE_LABEL_USER);
            list.add(dto);
            String flag = imageService.addOrEdit(list);
            
            JSONObject jsonObject = JSONObject.fromObject(flag);
            AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
            String dataJson = jsonObject.getString("data");
            JSONArray jsonArray = JSONArray.fromObject(dataJson);
            List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
            templateDetail.setShopStrokesPath(fbpDto.get(0).getImgData());
		}
		owTemplateService.editTemplateDetail(templateDetail);
	}
	
	/**
	 * 组装上传图片参数
	 * @Title: _generateFileBizParamDto 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param path
	 * @param zoomSize
	 * @param userId
	 * @param shopNo
	 * @param tableName
	 * @param id
	 * @return
	 * @date 2015年3月27日 上午11:54:59  
	 * @author ah
	 */
	private FileBizParamDto  _generateFileBizParamDto(String path, String zoomSize,
			Long userId, String shopNo, String tableName, String id, String file, Integer type) {
		FileBizParamDto dto = new FileBizParamDto();
		dto.setImgData(path);
		dto.setZoomSizes(zoomSize);
		dto.setUserId(userId);
		dto.setShopNo(shopNo);
		dto.setTableName(tableName);
		dto.setTableId(id);
		dto.setCode(file);
		dto.setFileLabel(type);
        return dto;
	}

	@Override
	public List<Modular> findModularByCode(String code,Integer modularType) {
		List<Modular> list = owTemplateService.findModularByCode(code);
		List<Modular> dtoList = new ArrayList<Modular>();
		for (Modular modular : list) {
			if(modular.getType().intValue() == modularType.intValue()){
				modular.setIsBasePic(1);
				dtoList.add(modular);
			}
		}
		return dtoList;
	}

	@Override
	public List<CarouselDiagramDto> findCarouselDiagramByCode(String code) {
		List<CarouselDiagram> list = owTemplateService.findCarouselDiagramByCode(code);
		List<CarouselDiagramDto> dtoList = new ArrayList<CarouselDiagramDto>();
		for (CarouselDiagram carouselDiagram : list) {
			CarouselDiagramDto dto = new CarouselDiagramDto();
			dto.setImgPath(carouselDiagram.getImgPath());
			dto.setIsBasePic(1);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public List<ModularColorVo> findModularColorVo(List<Modular> modularList) {
		return _generateColor(modularList, null);
	}

}
