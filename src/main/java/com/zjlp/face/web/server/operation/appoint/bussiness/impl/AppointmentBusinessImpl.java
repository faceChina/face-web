package com.zjlp.face.web.server.operation.appoint.bussiness.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.file.dto.FileBizParamDto;
import com.zjlp.face.file.service.ImageService;
import com.zjlp.face.util.constants.ConstantsFiled;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.ImageConstants;
import com.zjlp.face.web.exception.ext.TemplateException;
import com.zjlp.face.web.server.operation.appoint.bussiness.AppointmentBusiness;
import com.zjlp.face.web.server.operation.appoint.domain.Appointment;
import com.zjlp.face.web.server.operation.appoint.domain.AppointmentSkuRelation;
import com.zjlp.face.web.server.operation.appoint.domain.DynamicForm;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentDto;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentVo;
import com.zjlp.face.web.server.operation.appoint.service.AppointmentService;
import com.zjlp.face.web.server.operation.appoint.service.AppointmentSkuRelationService;
import com.zjlp.face.web.server.operation.appoint.service.DynamicFormService;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.producer.GoodProducer;

@Component
public class AppointmentBusinessImpl implements AppointmentBusiness {
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private AppointmentSkuRelationService appointmentSkuRelationService;
	@Autowired
	private GoodProducer goodProduer;
	@Autowired
	private DynamicFormService dynamicFormService;
	@Autowired
	private ImageService imageService;
	
	@Override
	public Pagination<AppointmentDto> findAppointmentPage(Pagination<AppointmentDto> pagination, AppointmentVo appointmentVo) {
		return appointmentService.findAppointmentPage(pagination, appointmentVo);
	}

	@Override
	public void switchStatus(Long id, Integer status) {
		Appointment appointment = new Appointment();
		appointment.setId(id);
		appointment.setStatus(status);
		appointment.setUpdateTime(new Date());
		appointmentService.updateByPrimaryKey(appointment);
	}

	@Override
	public void removeAppointmentById(Long id) {
		appointmentService.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional
	public Long addAppointment(AppointmentVo appointmentVo, Long userId) {
		Long id = appointmentService.insert(appointmentVo);
		List<DynamicForm> dynamicFormList = appointmentVo.getDynamicFormList();
		if(null != dynamicFormList){
			Date date = new Date();
			for(DynamicForm form:dynamicFormList){
				if(null != form && null != form.getLable()){
					form.setRemoteId(id.toString());
					form.setRemoteCode(1);
					form.setCreateTime(date);
					form.setUpdateTime(date);
					dynamicFormService.insert(form);
				}
			}
		}
		List<FileBizParamDto> list = _savePicturePath(appointmentVo, userId);
		if (null != list && !list.isEmpty()) {
			for (FileBizParamDto fileBizParamDto : list) {
				if(ImageConstants.SERVICE_APPOINTMENT_FILE.equals(fileBizParamDto.getCode())){
					Appointment appointment = new Appointment();
					appointment.setId(id);
					appointment.setPicturePath(list.get(0).getImgData());
					appointmentService.updateByPrimaryKey(appointment);
				}
				if (ImageConstants.UBB_FILE.equals(fileBizParamDto.getCode()) 
						&& Long.valueOf(fileBizParamDto.getTableId()).longValue() == id) {
					Appointment appointment = new Appointment();
					appointment.setId(id);
					appointment.setContent(fileBizParamDto.getImgData());
					appointmentService.updateByPrimaryKey(appointment);
				}
			}
		}
		
		return id;
	}

	/**
	 * 
	 * @Title: _savePicturePath 
	 * @Description: 将服务类预约的图片上传至七牛
	 * @param picturePath
	 * @return
	 * @date 2015年8月10日 上午11:39:11  
	 * @author cbc
	 */
	@SuppressWarnings({ "deprecation", "unchecked", "static-access" })
	private List<FileBizParamDto> _savePicturePath(Appointment appointmentVo, Long userId) {
		List<FileBizParamDto> list = new ArrayList<FileBizParamDto>();
        if(null != appointmentVo.getPicturePath()) {
        	 //上传图片到TFS
        	String zoomSizes = PropertiesUtil.getContexrtParam("serviceAppointmentFile");
        	AssertUtil.hasLength(zoomSizes, "imgConfig.properties还未配置serviceAppointmentFile字段");
            FileBizParamDto dto = new FileBizParamDto();
            dto.setImgData(appointmentVo.getPicturePath());
            dto.setZoomSizes(zoomSizes);
            dto.setUserId(userId);
            dto.setShopNo(appointmentVo.getShopNo());
            dto.setTableName("APPOINTMENT");
            dto.setTableId(String.valueOf(appointmentVo.getId()));
            dto.setCode(ImageConstants.SERVICE_APPOINTMENT_FILE);
            dto.setFileLabel(1);
            list.add(dto);
            
        }
        if (null != appointmentVo.getContent()) {
        	FileBizParamDto ubbDto = new FileBizParamDto(null, userId, appointmentVo.getShopNo(), "APPOINTMENT", String.valueOf(appointmentVo.getId()), ImageConstants.UBB_FILE, 1);
            ubbDto.setImgData(appointmentVo.getContent());
            list.add(ubbDto);
		}
        String flag = imageService.addOrEdit(list);
        
        JSONObject jsonObject = JSONObject.fromObject(flag);
        AssertUtil.isTrue("SUCCESS".equals(jsonObject.getString("flag")), "上传图片失败:"+flag);
        String dataJson = jsonObject.getString("data");
        JSONArray jsonArray = JSONArray.fromObject(dataJson);
        List<FileBizParamDto> fbpDto = jsonArray.toList(jsonArray, FileBizParamDto.class);
        return fbpDto;
	}

	@Override
	public Appointment getAppointmentById(Long id) {
		return appointmentService.selectByPrimaryKey(id);
	}

	@Override
	public void editAppointment(AppointmentVo appointmentVo, Long userId) {
		List<DynamicForm> dynamicFormList = appointmentVo.getDynamicFormList();
		List<DynamicForm> old = dynamicFormService.findDynamicFormByRemoteIdAndCode(appointmentVo.getId(), 1);
		if(null == dynamicFormList){
			dynamicFormList = new ArrayList<DynamicForm>();
		}
		Iterator<DynamicForm> ite = dynamicFormList.iterator();
		while(ite.hasNext()){
			if(null == ite.next().getLable()){
				ite.remove();
			}
		}
		int size = old.size() > dynamicFormList.size() ? old.size() : dynamicFormList.size();
		Date date = new Date();
		for(int i = 0;i < size;i++){
			if(i >= dynamicFormList.size()){
				dynamicFormService.delete(old.get(i).getId());
			}else if(i >= old.size()){
				DynamicForm form = dynamicFormList.get(i);
				form.setRemoteId(appointmentVo.getId().toString());
				form.setRemoteCode(1);
				form.setSort(i);
				form.setCreateTime(date);
				form.setUpdateTime(date);
				dynamicFormService.insert(form);
			}else{
				DynamicForm edit = dynamicFormList.get(i);
				edit.setId(old.get(i).getId());
				edit.setUpdateTime(date);
				if(null == edit.getPlaceHolder()){
					edit.setPlaceHolder(ConstantsFiled.EMPTY);
				}
				dynamicFormService.update(edit);
			}
		}
		appointmentService.updateByPrimaryKey(appointmentVo);
		Appointment edit = appointmentService.selectByPrimaryKey(appointmentVo.getId());
		edit.setPrice(appointmentVo.getPrice());
		edit.setInventory(appointmentVo.getInventory());
		edit.setAddress(appointmentVo.getAddress());
		edit.setCell(appointmentVo.getCell());
		edit.setSmsPhone(appointmentVo.getSmsPhone());
		List<FileBizParamDto> list = this._savePicturePath(edit, userId);
		if (null != list && !list.isEmpty()) {
			for (FileBizParamDto fileBizParamDto : list) {
				if(ImageConstants.SERVICE_APPOINTMENT_FILE.equals(fileBizParamDto.getCode())){
					edit.setPicturePath(list.get(0).getImgData());
				}
				if (ImageConstants.UBB_FILE.equals(fileBizParamDto.getCode()) 
						&& Long.valueOf(fileBizParamDto.getTableId()).longValue() == edit.getId()) {
					edit.setContent(fileBizParamDto.getImgData());
				}
			}
		}
		appointmentService.updateAllById(edit);
	}

	@Override
	public Long addAppointmentSKuRelation(Long appointmentId, Long goodId) {
		AppointmentSkuRelation appointmentSkuRelation = new AppointmentSkuRelation();
		List<GoodSku> goodSkuList = goodProduer.findGoodSkusByGoodId(goodId);
		appointmentSkuRelation.setAppointmentId(appointmentId);
		appointmentSkuRelation.setGoodSkuId(goodSkuList.get(0).getId());
		appointmentSkuRelation.setGoodId(goodId);
		appointmentSkuRelation.setCreateTime(new Date());
		return appointmentSkuRelationService.addAppointmentSKuRelation(appointmentSkuRelation);
	}

	@Override
	public List<AppointmentSkuRelation> findAppointmentSkuRelationList(Long appointmentId) {
		return appointmentSkuRelationService.findAppointmentSkuRelationList(appointmentId);
	}

	@Override
	public void deleteAppointmentSkuRelationById(Long id) {
		appointmentSkuRelationService.deleteAppointmentSkuRelationById(id);
	}

	@Override
	public void deleteAppointmentSkuRelationBySkuId(Long skuId) {
		appointmentSkuRelationService.deleteAppointmentSkuRelationBySkuId(skuId);
	}

	@Override
	public void saveGood(List<String> goodIdList, Long id) {
		List<AppointmentSkuRelation> relationList = findAppointmentSkuRelationList(id);
		List<String> old = new ArrayList<String>();
		Map<String, AppointmentSkuRelation> map = new HashMap<String, AppointmentSkuRelation>();
		for(AppointmentSkuRelation appointmentSkuRelation:relationList){
			old.add(appointmentSkuRelation.getGoodId().toString());
			map.put(appointmentSkuRelation.getGoodId().toString(), appointmentSkuRelation);
		}
		List<String> add = new ArrayList<String>(goodIdList);
		add.removeAll(old);
		old.removeAll(goodIdList);
		for(String goodId:add){
			addAppointmentSKuRelation(id, Long.valueOf(goodId));
		}
		for(String goodId:old){
			deleteAppointmentSkuRelationById(map.get(goodId).getId());
		}
	}

	@Override
	public List<AppointmentDto> findAllAppointmentByShopNo(String shopNo) throws TemplateException {
		try{
			AssertUtil.notNull(shopNo, "参数店铺编号为空");
			// 根据店铺编号查询预约列表
			return appointmentService.findAllAppointmentByShopNo(shopNo);
		}catch(Exception e){
			throw new TemplateException(e);
		}
	}

	@Override
	public List<DynamicForm> findDynamicFormByAppointmentId(Long id) {
		return dynamicFormService.findDynamicFormByRemoteIdAndCode(id, 1);
	}

}
