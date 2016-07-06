package com.zjlp.face.web.server.product.template.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjlp.face.jredis.client.AbstractRedisDaoSupport;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.TemplateException;
import com.zjlp.face.web.server.product.template.dao.CarouselDiagramDao;
import com.zjlp.face.web.server.product.template.dao.ModularDao;
import com.zjlp.face.web.server.product.template.dao.OwTemplateDao;
import com.zjlp.face.web.server.product.template.dao.TemplateDetailDao;
import com.zjlp.face.web.server.product.template.domain.CarouselDiagram;
import com.zjlp.face.web.server.product.template.domain.Modular;
import com.zjlp.face.web.server.product.template.domain.OwTemplate;
import com.zjlp.face.web.server.product.template.domain.TemplateDetail;
import com.zjlp.face.web.server.product.template.domain.dto.CarouselDiagramDto;
import com.zjlp.face.web.server.product.template.domain.dto.OwTemplateDto;
import com.zjlp.face.web.server.product.template.service.OwTemplateService;
import com.zjlp.face.web.util.redis.RedisFactory;
import com.zjlp.face.web.util.redis.RedisKey;
import com.zjlp.face.web.util.template.LoadTempalteData;
@Service
public class OwTemplateServiceImpl implements OwTemplateService {

	@Autowired
	private OwTemplateDao owTemplateDao;
	@Autowired
    private ModularDao modularDao;
    @Autowired
    private CarouselDiagramDao carouselDiagramDao;
    @Autowired
    private TemplateDetailDao templateDetailDao;
    
//    private Log _log = LogFactory.getLog(getClass());
	
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "TemplateException" })
	public void initTemplate(String shopNo, Integer shopType) {
    	try{
            AssertUtil.notNull(shopNo, "店铺编号为空");
			AssertUtil.notNull(shopType, "店铺类型为空");
            List<OwTemplateDto> list = _getInitOwTemplate(shopType);
            AssertUtil.notNull(list, "未获取到模板数据");
            
            for(OwTemplateDto owTemplateDto : list){
            	OwTemplate ow = _generateOwTemplate(owTemplateDto);
                ow.setShopNo(shopNo);
                // 新增模板
                _addTemplate(ow);
                // 模板是否有商品细项
            	if(null != owTemplateDto.getIsTemplateDetail() &&
            			owTemplateDto.getIsTemplateDetail() == 1) {
            		owTemplateDto.setId(ow.getId());
            		TemplateDetail templateDetail = this._generateTemplateDetail(owTemplateDto);
            		// 新增模板细项
            		_addTemplateDetail(templateDetail);
            	}
            }
        }catch(Exception e){
            throw new TemplateException(e);
        }
	}
    
    /**
     * 新增模板消息
     * @Title: _addTemplateDetail 
     * @Description: (新增模板消息) 
     * @param templateDetail
     * @date 2015年3月26日 下午8:32:50  
     * @author ah
     */
	private void _addTemplateDetail(TemplateDetail templateDetail) {
		// 新增模板消息
		templateDetailDao.add(templateDetail);
		AssertUtil.notNull(templateDetail.getId(), "新增商品细项失败");
	}

	/**
	 * 组装模板细项
	 * @Title: _generateTemplateDetail 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param owTemplateDto
	 * @return
	 * @date 2015年3月26日 下午8:33:42  
	 * @author ah
	 */
	private TemplateDetail _generateTemplateDetail(OwTemplateDto owTemplateDto) {
		Date date = new Date();
		TemplateDetail templateDetail = new TemplateDetail();
		templateDetail.setOwTemplateId(owTemplateDto.getId());;
		templateDetail.setShopStrokesPath(owTemplateDto.getShopStrokesPath());
		templateDetail.setCreateTime(date);
		templateDetail.setUpdateTime(date);
		return templateDetail;
	}

	@Override
	public void addOwTemplate(OwTemplate owTemplate) {
		Assert.notNull(owTemplate);
		Date date = new Date();
		if(null == owTemplate.getCreateTime()){
			owTemplate.setCreateTime(date);
		}
		if(null == owTemplate.getUpdateTime()){
			owTemplate.setUpdateTime(date);
		}
		owTemplateDao.add(owTemplate);
	}

	@Override
	public void editOwTemplateStatus(Long id, Integer status) {
		Assert.notNull(id,"参数编号为空");
		Assert.notNull(status,"参数状态为空");
		OwTemplate owTemplate = new OwTemplate();
		owTemplate.setId(id);
		owTemplate.setUpdateTime(new Date());
		owTemplateDao.edit(owTemplate);
	}

	@Override
	public List<OwTemplateDto> findWgwHpHotOwTemplate() {
		return _getTemplateList(Constants.WGW_HP_TEMPLATE_CODE,1);
	}

	@Override
	public List<OwTemplateDto> findWgwHpOtherOwTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OwTemplateDto> findWgwScHpHotOwTemplate() {
		return _getTemplateList(Constants.GW_WSC_HP_TEMPLATE_CODE,1);
	}

	@Override
	public List<OwTemplateDto> findWgwScHpOtherOwTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OwTemplateDto> findWscHpHotOwTemplate() {
		// 从缓存中拿数据
		if (null != RedisFactory.getWgjListHelper()) {
			String wscHpHotKey = RedisKey.Template_findWscHpHotOwTemplate_key;
			return RedisFactory.getWgjListHelper().list(wscHpHotKey, new AbstractRedisDaoSupport<List<OwTemplateDto>>() {
				@Override
				public List<OwTemplateDto> support() throws RuntimeException {
					return _getTemplateList(Constants.WSC_HP_TEMPLATE_CODE,1);
				}
			});
		} else {
			return _getTemplateList(Constants.WSC_HP_TEMPLATE_CODE,1);
		}
	}

	@Override
	public List<OwTemplateDto> findWscHpOtherOwTemplate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<OwTemplateDto> findGtOwTemplate() {
		return _getGtTemplateList(Constants.GT_WSC);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackForClassName = { "TemplateException" })
	public void switchTemplate(String shopNo, String code) throws TemplateException{
		AssertUtil.notNull(shopNo, "店铺编号为空");
		AssertUtil.notNull(code, "模板编号为空");
		OwTemplate owTemplate = new OwTemplate();
        owTemplate.setCode(code);
        owTemplate.setShopNo(shopNo);
        OwTemplate isOwTemplate = owTemplateDao.getOwTemplateByShopNoAndCode(owTemplate);
        if(null == isOwTemplate){
            OwTemplateDto owTemplateDto = _getOwTemplateByCode(code);
            if(null == owTemplateDto){
                throw new TemplateException("未读取到对应模板数据");
            }
            isOwTemplate = _generateOwTemplate(owTemplateDto);
            isOwTemplate.setShopNo(shopNo);
            this._addTemplate(isOwTemplate);
            // 模板是否有商品细项
        	if(owTemplateDto.getIsTemplateDetail() == 1) {
        		owTemplateDto.setId(isOwTemplate.getId());
        		TemplateDetail templateDetail = this._generateTemplateDetail(owTemplateDto);
        		// 新增模板细项
        		_addTemplateDetail(templateDetail);
        	}
        }
        // 停用其它模板
        Date date = new Date();
        OwTemplate stop = new OwTemplate();
        stop.setShopNo(shopNo);
        stop.setType(isOwTemplate.getType());
        stop.setStatus(Constants.FREEZE);
        stop.setUpdateTime(date);
        owTemplateDao.editStatus(stop);
        // 启用当前模板
        isOwTemplate.setStatus(Constants.VALID);
        isOwTemplate.setUpdateTime(date);
        owTemplateDao.editStatus(isOwTemplate);
	}
	
	/**
	 * 初始化模板数据
	 * @Title: _generateOwTemplate 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param owTemplateDto
	 * @return
	 * @date 2015年3月26日 下午9:05:00  
	 * @author ah
	 */
	private OwTemplate _generateOwTemplate(OwTemplateDto owTemplateDto) {
		Date date = new Date();
		OwTemplate owTemplate = new OwTemplate();
     	owTemplate.setCode(owTemplateDto.getCode());
     	owTemplate.setName(owTemplateDto.getName());
     	owTemplate.setPath(owTemplateDto.getPath());
     	owTemplate.setType(owTemplateDto.getType());
     	owTemplate.setStatus(Constants.VALID);
     	owTemplate.setUpdateTime(date);
     	owTemplate.setCreateTime(date);
		return owTemplate;
	}

	@Override
	public OwTemplate getCurrentTemplate(String shopNo, Integer shopType) {
		AssertUtil.notNull(shopNo, "店铺编号为空");
		AssertUtil.notNull(shopType, "模板类型为空");
		// 查询当前模板
		OwTemplate owTemplate = new OwTemplate();
        owTemplate.setShopNo(shopNo);
        owTemplate.setType(shopType);
		return owTemplateDao.getCurrent(owTemplate);
	}

	@Override
	public TemplateDetail getTemplateDetailByTemplateId(Long tempalteId) throws TemplateException{
		try {
			AssertUtil.notNull(tempalteId, "模板id为空");
			// 根据模板id查询模板
			return templateDetailDao.getByTemplateId(tempalteId);
		} catch (Exception e) {
			throw new TemplateException(e);
		}
	}

	@Override
	public List<Modular> findBaseModular(String subCode)
			throws TemplateException {
		try{
			AssertUtil.notNull(subCode, "模板编号为空");
			// 获取所有基本模块
            List<Modular> newlist = this.findBaseMudular();
            List<Modular> list = new ArrayList<Modular>();
            for(Modular modular : newlist){
                if(subCode.indexOf(modular.getBaseType()) != -1){
                    Modular m = (Modular) modular.clone();
                    list.add(m);
                }
            }
            return list;
        }catch(CloneNotSupportedException e){
            throw new TemplateException(e);
        }
	}

	@Override
	public List<Modular> findModularByOwTemplateId(Long owTemplateId)
			throws TemplateException {
		try {
			AssertUtil.notNull(owTemplateId, "模板主键为空");
			// 根据模板编号查询模块
			return modularDao.findModularByOwTemplateId(owTemplateId);
		} catch (Exception e) {
			throw new TemplateException(e);
		}
	}

	@Override
	public Long saveModuarl(Modular modular, String shopNo)
			throws TemplateException {
		try{
            AssertUtil.notNull(modular, "参数模块数据为空");
            Date date = new Date();
            modular.setUpdateTime(date);
            modular = _modularHandle(modular, shopNo);
            Long id = null;
            if(null == modular.getId()){
                modular.setCreateTime(date);
                if(null==modular.getSort()){
					//如果sort字段为空，为新模块添加sort
					Integer sort = modularDao.getModularMaxSort(modular.getOwTemplateId());
					modular.setSort(sort + 1);
                }
				modularDao.add(modular);
				AssertUtil.notNull(modular.getId(), "模块主键为空");
				id = modular.getId();
            }else{
                modularDao.editModular(modular);
                id = modular.getId();
            }
            return id;
        }catch(Exception e){
            throw new TemplateException(e);
        }
	}
	
	@Override
	public void editModularImgPath(Modular modular) throws TemplateException{
		Assert.notNull(modular);
		Assert.notNull(modular.getId());
		modularDao.edit(modular);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void resetModular(Long owTemplateId) throws TemplateException {
		try{
            AssertUtil.notNull(owTemplateId, "模板id为空");
            OwTemplate owTemplate = owTemplateDao.getById(owTemplateId);
            AssertUtil.notNull(owTemplate, "没有找到该模板", "模板id："+owTemplateId);
            // 删除该模板下的所有模块
            modularDao.deleteModularByOwTemplateId(owTemplateId);
            // 根据模板初始化模块数据
            _addModular(owTemplate, new Date());
        }catch(Exception e){
            throw new TemplateException(e);
        }
	}

	@Override
	public void deleteModular(Long id) throws TemplateException {
		try {
			AssertUtil.notNull(id, "模块id为空");
			// 根据主键删除模块
			modularDao.delete(id);
		} catch (Exception e) {
			throw new TemplateException(e);
		}
		
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void sortModular(Long subId, Long tarId) throws TemplateException {
		try{
			AssertUtil.notNull(subId, "模块id为空");
			AssertUtil.notNull(tarId, "模块id为空");
            Date date = new Date();
            // 查询模块
            Modular subModular = modularDao.getById(subId);
            Modular tarModular = modularDao.getById(tarId);
            Integer subSort = subModular.getSort();
            Integer tarSort = tarModular.getSort();
            subModular.setSort(tarSort);
            subModular.setUpdateTime(date);
            // 编辑模块顺序
            modularDao.updateModularSort(subModular);
            tarModular.setSort(subSort);
            tarModular.setUpdateTime(date);
            modularDao.updateModularSort(tarModular);
        }catch(Exception e){
            throw new TemplateException(e);
        }
		
	}

	@Override
	public Modular getModularById(Long id) throws TemplateException {
		try {
			AssertUtil.notNull(id, "模块id为空");
			//根据主键查询模块
			return modularDao.getById(id);
		} catch (Exception e) {
			throw new TemplateException(e);
		}
	}
	
	// 获取模板列表
	private List<OwTemplateDto> _getTemplateList(String code,Integer classification) {
		List<OwTemplateDto> owTemplateDtos = this.findAllInitOwTemplate();
		List<OwTemplateDto> templateList = new ArrayList<OwTemplateDto>();
		for (OwTemplateDto owTemplateDto : owTemplateDtos) {
			if(null != owTemplateDto.getCode() 
					&& owTemplateDto.getCode().indexOf(code) != -1
					&& owTemplateDto.getClassification().intValue() == classification.intValue()) {
				templateList.add(owTemplateDto);
			}
		}
		// 排序
		return OwTemplateDto.sortList(templateList);
	}
	
	// 获取商品分类模板列表
	private List<OwTemplateDto> _getGtTemplateList(String code) {
		List<OwTemplateDto> owTemplateDtos = this.findAllInitOwTemplate();
		List<OwTemplateDto> templateList = new ArrayList<OwTemplateDto>();
		for (OwTemplateDto owTemplateDto : owTemplateDtos) {
			if(null != owTemplateDto.getCode() 
					&& owTemplateDto.getCode().indexOf(code) != -1) {
				templateList.add(owTemplateDto);
			}
		}
		// 排序
		return OwTemplateDto.sortList(templateList);
	}
	
	/**
     * 新增模板
     * @Title: _addTemplate
     * @Description: (这里用一句话描述这个方法的作用)
     * @param owTemplate
     * @throws TemplateException
     * @date 2014年7月26日 下午3:07:05
     * @author Administrator
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "TemplateException" })
    private void _addTemplate(OwTemplate owTemplate) throws TemplateException{
        Date date = new Date();
        // 模板
        owTemplate.setCreateTime(date);
        owTemplate.setUpdateTime(date);
        owTemplateDao.add(owTemplate);
        // 查询相同code模板个数
        Integer count=owTemplateDao.countTemplate(owTemplate);
        AssertUtil.isTrue(count==1, "店铺模板重复添加", "店铺："+owTemplate.getShopNo(), "模板："+owTemplate.getCode());

        // 模块
        _addModular(owTemplate, date);

        // 轮播图
        _addCarouselDiagram(owTemplate, date);
    }
    
    /***
     * 根据模板，初始化模块
     * 
     * @Title: _addModular
     * @Description: (这里用一句话描述这个方法的作用)
     * @param owTemplate
     * @param date
     * @throws TemplateException
     * @date 2014年7月28日 下午6:06:10
     * @author Administrator
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "TemplateException" })
    private void _addModular(OwTemplate owTemplate, Date date) throws TemplateException{
        // 模块
        List<Modular> modularList = findModularByCode(owTemplate.getCode());
        // 模块资源地址替换
        _resetModularPath(modularList, owTemplate.getShopNo());
        for(Modular m : modularList){
            Modular modular = new Modular();
            modular.setCode(m.getCode());
            modular.setNameZh(m.getNameZh());
            modular.setNameZhColor(m.getNameZhColor());
            modular.setNameEn(m.getNameEn());
            modular.setNameEnColor(m.getNameEnColor());
			modular.setBackgroundColor(m.getBackgroundColor());
			modular.setTransparency(m.getTransparency());
            modular.setImgPath(m.getImgPath());
            modular.setOwTemplateId(owTemplate.getId());
            modular.setResourcePath(m.getResourcePath());
            modular.setSort(m.getSort());
            modular.setStatus(m.getStatus());
            modular.setType(m.getType());
            modular.setCreateTime(date);
            modular.setUpdateTime(date);
            modularDao.add(modular);
        }
    }
    
    /***
     * 根据模板，初始化轮播图
     * 
     * @Title: _addCarouselDiagram
     * @Description: (这里用一句话描述这个方法的作用)
     * @param owTemplate
     * @param date
     * @throws TemplateException
     * @date 2014年7月28日 下午6:06:23
     * @author Administrator
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = { "TemplateException" })
    private void _addCarouselDiagram(OwTemplate owTemplate, Date date) throws TemplateException{
        List<CarouselDiagram> carouselDiagramList = findCarouselDiagramByCode(owTemplate.getCode());
        for(CarouselDiagram cddto : carouselDiagramList){
            CarouselDiagram cd = new CarouselDiagram();
            cd.setCode(cddto.getCode());
            cd.setImgPath(cddto.getImgPath());
            cd.setOwTemplateId(owTemplate.getId());
            cd.setResourcePath(cddto.getResourcePath());
            cd.setSort(cddto.getSort());
            cd.setStatus(cddto.getStatus());
            cd.setCreateTime(date);
            cd.setUpdateTime(date);
            carouselDiagramDao.add(cd);
        }
    }

    /**
     * 根据模板CODE获取对应模块数据
     * 
     * @Title: _findModularByCode
     * @Description: (这里用一句话描述这个方法的作用)
     * @param code
     * @return
     * @throws TemplateException
     * @date 2014年7月26日 上午11:50:33
     * @author Administrator
     */
    @Override
    public List<Modular> findModularByCode(String code) throws TemplateException{
        try{
        	LoadTempalteData loadTempalteData = new LoadTempalteData();
            List<Modular> list = loadTempalteData.loadTempalteDatas(Modular.class, Constants.TEMPALTE_TYPE);
            List<Modular> newlist = new ArrayList<Modular>();
            for(Modular modular : list){
				if(modular.getCode().equals(code)){
                    Modular m = (Modular) modular.clone();
                    newlist.add(m);
                }
            }
            return newlist;
        }catch(Exception e){
            throw new TemplateException(e);
        }
    }

    /**
     * 根据模板CODE获取对应轮播图数据
     * @Title: _findCarouselDiagramByCode 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param code
     * @return
     * @throws TemplateException
     * @date 2015年3月20日 下午5:06:16  
     * @author ah
     */
    @Override
    public List<CarouselDiagram> findCarouselDiagramByCode(String code) throws TemplateException{
        try{
        	LoadTempalteData loadTempalteData = new LoadTempalteData();
            List<CarouselDiagram> list = loadTempalteData.loadTempalteDatas(CarouselDiagram.class, Constants.TEMPALTE_TYPE);
            List<CarouselDiagram> newlist = new ArrayList<CarouselDiagram>();
            for(CarouselDiagram carouselDiagram : list){
                if(carouselDiagram.getCode().equals(code)){
                    CarouselDiagram cd = (CarouselDiagram) carouselDiagram.clone();
                    newlist.add(cd);
                }
            }
            return newlist;
        }catch(Exception e){
            throw new TemplateException(e);
        }
    }
    
    /**
     * 模块路径批量转换
     * 
     * @param modularlist
     * @param shopNo
     */
    private void _resetModularPath(List<Modular> modularlist, String shopNo){
        for(Modular modular : modularlist){
            modular.setResourcePath(_coverPath(modular.getResourcePath(), shopNo));
        }
    }

    /**
     * 路径url转换
     * 
     * @param path
     * @param shopNo
     * @return
     */
    public static String _coverPath(String path, String shopNo){
        if(StringUtils.isBlank(path)){
            return path;
        }
        if(path.startsWith(Constants.URL_ANY)) {
        	return path+Constants.URL_SUFIX;
        }
        if(path.startsWith(Constants.URL_PREFIX) || path.endsWith(Constants.URL_SUFIX)){
            return path.replace(Constants.URL_SHOPNO, shopNo);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.URL_PREFIX);
        sb.append(path.replace(Constants.URL_SHOPNO, shopNo));
        if (path.indexOf(Constants.URL_SUFIX) == -1) {
        	sb.append(Constants.URL_SUFIX);
		}
        return sb.toString();
    }
    
    /**
     * 根据店铺类型获取默认模板数据
     * 
     * @Title: _getInitOwTemplate
     * @Description: (这里用一句话描述这个方法的作用)
     * @param shopType
     * @return
     * @date 2014年7月26日 上午11:09:58
     * @author Administrator
     * @throws Exception 
     */
    private List<OwTemplateDto> _getInitOwTemplate(Integer shopType) throws Exception{
        try{
            List<OwTemplateDto> otlist = new ArrayList<OwTemplateDto>();
            String[] codes = new String[3];
            switch (shopType) {
                case 1:// 官网
                    codes[0] = Constants.WGW_HP_TEMPLATE_CODE;
                    codes[1] = Constants.GW_WSC_HP_TEMPLATE_CODE;
                    codes[2] = Constants.GT_SC_WGW;
                    break;
                case 2:// 内部商城
                case 3: // 外部商城
                    codes[0] = Constants.WSC_HP_TEMPLATE_CODE;
                    codes[1] = Constants.GT_WSC;
                    break;
                default:
                    throw new TemplateException("参数异常");
            }
            
            LoadTempalteData loadTempalteData = new LoadTempalteData();
            //获取模板列表
            List<OwTemplateDto> list = loadTempalteData.loadTempalteDatas(OwTemplateDto.class, Constants.TEMPALTE_TYPE);
            for(String code : codes){
                if(StringUtils.isBlank(code)){
                    continue;
                }
                for(OwTemplateDto owTemplateDto : list){
                    if(null != owTemplateDto && owTemplateDto.getCode().indexOf(code) != -1 && owTemplateDto.getStatus() == 1){
                        otlist.add(owTemplateDto);
                    }
                }
            }
            return otlist;
        }catch(CloneNotSupportedException e){
            throw new TemplateException(e);
        }
    }

    /**
     * 获取所有模板
     * @Title: findAllInitOwTemplate 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @return
     * @date 2015年3月20日 下午8:26:48  
     * @author ah
     */
    public List<OwTemplateDto> findAllInitOwTemplate() {
		LoadTempalteData loadTempalteData = new LoadTempalteData();
		try {
			return loadTempalteData.loadTempalteDatas(OwTemplateDto.class, Constants.TEMPALTE_TYPE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

    /**
     * 获取模板初始化数据
     * @Title: _getOwTemplateByCode 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @param code
     * @return
     * @throws TemplateException
     * @date 2015年3月21日 下午3:09:50  
     * @author ah
     */
 	private OwTemplateDto _getOwTemplateByCode(String code) throws TemplateException{
         try{
             List<OwTemplateDto> list = this.findAllInitOwTemplate();
             for(OwTemplateDto owTemplateDto : list){
                 if(owTemplateDto.getCode().equals(code)){
                     return owTemplateDto;
                 }
             }
             return null;
         }catch(Exception e){
             throw new TemplateException(e);
         }
     }
 	
    /**
     * 获取所有初始化模块数据
     * @Title: findAllInitMudular 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @return
     * @date 2015年3月21日 上午9:43:08  
     * @author ah
     */
    public List<Modular> findAllInitMudular() {
		//从初始化数据获取模块列表
		LoadTempalteData loadTempalteData = new LoadTempalteData();
		try {
			return loadTempalteData.loadTempalteDatas(Modular.class, Constants.TEMPALTE_TYPE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    /**
     * 获取基础初始化模块数据
     * @Title: findBaseMudular 
     * @Description: (这里用一句话描述这个方法的作用) 
     * @return
     * @date 2015年3月21日 上午9:43:08  
     * @author ah
     */
    public List<Modular> findBaseMudular() {
		//从初始化数据获取模块列表
		LoadTempalteData loadTempalteData = new LoadTempalteData();
		try {
			return loadTempalteData.loadTempalteDatas(Modular.class, Constants.BASE_MODULAR_TYPE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
    
    /**
     * 处理添加修改模块
     * @Title: _modularHandle 
     * @Description: (处理添加修改模块) 
     * @param modular
     * @param shopNo
     * @return
     * @throws TemplateException
     * @date 2015年3月21日 下午2:49:08  
     * @author ah
     */
    private Modular _modularHandle(Modular modular, String shopNo) throws TemplateException{
        try{
            if(StringUtils.isBlank(modular.getCode())){
                return modular;
            }
            // 文章
            if(modular.getCode().indexOf(Constants.MODULAR_NEWSLIST) != -1){
                StringBuffer sb = new StringBuffer(Constants.MODULAR_URL_NEWSLIST).append(modular.getBindIds());
                modular.setResourcePath(sb.toString());
            }
            // 文章分类
            if(modular.getCode().indexOf(Constants.MODULAR_ARTICLECATEGORY) != -1){
                modular.setResourcePath(new StringBuffer(Constants.MODULAR_URL_ARTICLECATEGORY).append(modular.getBindIds()).toString());
            }
            // 文章栏目
            if(modular.getCode().indexOf(Constants.MODULAR_ARICLECOLUMN) != -1){
            	modular.setResourcePath(new StringBuffer(Constants.MODULAR_URL_ARICLECOLUMN).append(modular.getBindIds()).toString());
            }
            // 商品分类列表
			if(modular.getCode().indexOf(Constants.MODULAR_SHOPTYPES) != -1 && StringUtils.isNotBlank(modular.getBindIds())){
				modular.setResourcePath(new StringBuffer(Constants.MODULAR_URL_SHOPTYPE_GWSC).append(modular.getBindIds()).toString());
            }
			// 单个商品分类
			if(modular.getCode().endsWith(Constants.MODULAR_SHOPTYPE) && StringUtils.isNotBlank(modular.getBindIds())){
				StringBuffer sb = new StringBuffer();
				sb.append(Constants.MODULAR_URL_SHOPTYPE).append(modular.getBindIds());
				modular.setResourcePath(sb.toString());
			}
            // 商品
			if(modular.getCode().endsWith(Constants.MODULAR_GOOD) && StringUtils.isNotBlank(modular.getBindIds())){
            	if("0".equals(modular.getBindIds())){
            		//bindIds为0代表链接至全部商品
            		modular.setResourcePath(Constants.MODULAR_URL_SHOPTYPE_GWSC);
            	}else{
            		StringBuffer sb = new StringBuffer(Constants.MODULAR_URL_GOOD).append(modular.getBindIds());
            		modular.setResourcePath(sb.toString());
            	}
            }
			//相册
			if(modular.getCode().indexOf(Constants.MODULAR_ALBUM) != -1){
                modular.setResourcePath(new StringBuffer(Constants.MODULAR_URL_ALBUM).append(modular.getBindIds()).toString());
            }
			//相册专辑
			if(modular.getCode().indexOf(Constants.MODULAR_PHOTOALBUM) != -1){
                modular.setResourcePath(new StringBuffer(Constants.MODULAR_URL_PHOTOALBUM).append(modular.getBindIds()).toString());
            }
			
			// 预约
            if(modular.getCode().indexOf(Constants.MODULAR_APPOINTMENT) != -1) {
            	String url = Constants.MODULAR_URL_APPOINTMENT.replace(Constants.URL_ID, modular.getBindIds());
            	modular.setResourcePath(url);
            }
            
			// 链接url
            if(modular.getCode().indexOf(Constants.LINKURL) != -1){
                modular.setResourcePath(_coverUrlLink(modular.getResourcePath()));
            }else{
                modular.setResourcePath(_coverPath(modular.getResourcePath(), shopNo));
            }

            return modular;
        }catch(Exception e){
            throw new TemplateException(e);
        }
    }
    
    /**
     * 外部输入链接处理
     * 
     * @param urlLink
     * @return
     */
    public static String _coverUrlLink(String urlLink){
        if(StringUtils.isBlank(urlLink)){
            return urlLink;
        }
        if(urlLink.contains(Constants.HTTP) || urlLink.contains(Constants.HTTPS)){
            return urlLink;
        }
        return Constants.HTTP + urlLink;
    }
    
    
    /** ============================轮播图============================= */
    
    @Override
    public Long addCarouselDiagram(CarouselDiagramDto carouselDiagramDto,String shopNo){
    	carouselDiagramDto = _carouselDiagramrHandle(carouselDiagramDto,shopNo);
    	return carouselDiagramDao.add(carouselDiagramDto);
    }
    
    @Override
    public void editCarouselDiagram(CarouselDiagramDto carouselDiagramDto,String shopNo){
    	carouselDiagramDto = _carouselDiagramrHandle(carouselDiagramDto,shopNo);
    	carouselDiagramDao.edit(carouselDiagramDto);
    }

    @Override
    public void deleteCarouselDiagram(Long id) throws TemplateException{
        try{
            if(null == id){
                throw new TemplateException("参数为空");
            }
            carouselDiagramDao.delete(id);
        }catch(Exception e){
            throw new TemplateException(e);
        }
    }

    @Override
    public CarouselDiagram getCarouselDiagramById(Long id) throws TemplateException{
        try{
            if(null == id){
                throw new TemplateException("参数为空");
            }
            return carouselDiagramDao.getById(id);
        }catch(Exception e){
            throw new TemplateException(e);
        }
    }

    @Override
    public List<CarouselDiagramDto> findCarouselDiagramByOwTemplateId(Long owTemplateId) throws TemplateException{
        try{
            if(null == owTemplateId){
                throw new TemplateException("参数为空");
            }
            List<CarouselDiagramDto> carouselDiagramDtos = carouselDiagramDao.findCarouselDiagramByOwTemplateId(owTemplateId);
            return carouselDiagramDtos;
        }catch(Exception e){
            throw new TemplateException(e);
        }
    }

	@Override
	public void editTemplateDetail(TemplateDetail templateDetail) {
		templateDetail.setUpdateTime(new Date());
		templateDetailDao.edit(templateDetail);
	}

    
    private CarouselDiagramDto _carouselDiagramrHandle(CarouselDiagramDto carouselDiagram, String shopNo) throws TemplateException{
        try{
            if(StringUtils.isBlank(carouselDiagram.getCode())){
                return carouselDiagram;
            }
            // 商品分类列表
			if(carouselDiagram.getCode().indexOf(Constants.MODULAR_SHOPTYPES) != -1 && StringUtils.isNotBlank(carouselDiagram.getBindIds())){
				StringBuffer sb = new StringBuffer();
				sb.append(Constants.MODULAR_URL_SHOPTYPE_GWSC).append(carouselDiagram.getBindIds());
				carouselDiagram.setResourcePath(sb.toString());
            }
			// 单个商品分类
			if(carouselDiagram.getCode().endsWith(Constants.MODULAR_SHOPTYPE) && StringUtils.isNotBlank(carouselDiagram.getBindIds())){
				StringBuffer sb = new StringBuffer();
				sb.append(Constants.MODULAR_URL_SHOPTYPE).append(carouselDiagram.getBindIds());
				carouselDiagram.setResourcePath(sb.toString());
			}
			// 预约
            if(carouselDiagram.getCode().indexOf(Constants.MODULAR_APPOINTMENT) != -1) {
            	String url = Constants.MODULAR_URL_APPOINTMENT.replace(Constants.URL_ID, carouselDiagram.getBindIds());
            	carouselDiagram.setResourcePath(url);
            }
            // 商品
			if(carouselDiagram.getCode().endsWith(Constants.MODULAR_GOOD) && StringUtils.isNotBlank(carouselDiagram.getBindIds())){
            	if("0".equals(carouselDiagram.getBindIds())){
            		//bindIds为0代表链接至全部商品
            		carouselDiagram.setResourcePath(Constants.MODULAR_URL_SHOPTYPE_GWSC);
            	}else{
            		StringBuffer sb = new StringBuffer(Constants.MODULAR_URL_GOOD).append(carouselDiagram.getBindIds());
            		carouselDiagram.setResourcePath(sb.toString());
            	}
            }

            return carouselDiagram;
        }catch(Exception e){
            throw new TemplateException(e);
        }
    }

}
