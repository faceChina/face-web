package com.zjlp.face.web.ctl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.operation.appoint.bussiness.AppointmentBusiness;
import com.zjlp.face.web.server.operation.appoint.domain.Appointment;
import com.zjlp.face.web.server.operation.appoint.domain.AppointmentSkuRelation;
import com.zjlp.face.web.server.operation.appoint.domain.DynamicForm;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentDto;
import com.zjlp.face.web.server.operation.appoint.domain.dto.AppointmentVo;
import com.zjlp.face.web.server.product.good.business.GoodBusiness;
import com.zjlp.face.web.server.product.good.business.ShopTypeBusiness;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.ShopType;
import com.zjlp.face.web.server.product.good.domain.vo.GoodImgVo;
import com.zjlp.face.web.server.product.good.domain.vo.GoodVo;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;
import com.zjlp.face.web.server.product.good.factory.GoodFactory;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;

@Controller
@RequestMapping("/u/appoint")
public class AppointmentCtl extends BaseCtl {
	public static final int APPOINTMENT_GOOD_TYPE = 1;
	public static final int APPOINTMENT_SERVE_TYPE = 2;
	@Autowired
	private AppointmentBusiness appointmentBusiness;

	@Autowired
	private GoodBusiness goodBusiness;

	@Autowired
	private GoodFactory appointmentGoodFactory;
	@Autowired
	private ShopTypeBusiness shopTypeBusiness;

	@RequestMapping(value = "/list")
	public String list(Model model, Pagination<AppointmentDto> pagination, AppointmentVo appointmentVo) {
		String shopNo = super.getShopNo();
		appointmentVo.setShopNo(shopNo);
		pagination = appointmentBusiness.findAppointmentPage(pagination, appointmentVo);
		model.addAttribute("pagination", pagination);
		return "/m/operation/appoint/appointment";
	}

	@RequestMapping(value = "/appointment-type")
	public String appointmentType(Model model, Pagination<AppointmentDto> pagination, AppointmentVo appointmentVo) {
		return "/m/operation/appoint/appointment-type";
	}

	@RequestMapping(value = "/add")
	public String appointmentNewserve(Model model, @RequestParam(required = true) Integer type) {
		if(APPOINTMENT_GOOD_TYPE == type){
			return "/m/operation/appoint/appointment-newshop";
		}else if(APPOINTMENT_SERVE_TYPE == type){
			return "/m/operation/appoint/appointment-newserve";
		}
		return getRedirectUrl("/u/appoint/list");
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String appointmentAdd(Model model, AppointmentVo appointmentVo) {
		appointmentVo.setShopNo(getShopNo());
		if(null==appointmentVo.getIsShowSellerMeg()){
			appointmentVo.setIsShowSellerMeg(0);
		}
		appointmentVo.setStatus(0);
		if(null == appointmentVo.getMaxVal()){
			appointmentVo.setMaxVal(0);
		}
		if(null == appointmentVo.getTemplateId()){
			appointmentVo.setTemplateId(0);
		}
		Date date = new Date();
		appointmentVo.setCreateTime(date);
		appointmentVo.setUpdateTime(date);
		Long userId = getUserId();
		appointmentBusiness.addAppointment(appointmentVo, userId);
		if(APPOINTMENT_GOOD_TYPE == appointmentVo.getType()){
			return getRedirectUrl("/u/appoint/good-manage/" + appointmentVo.getId());
		}
		return getRedirectUrl("/u/appoint/list");
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String appointmentDeit(Model model, Long id) {
		Appointment appointment = appointmentBusiness.getAppointmentById(id);
		AssertUtil.isTrue(getShopNo().equals(appointment.getShopNo()), "操作异常");
		List<DynamicForm> dynamicFormList = appointmentBusiness.findDynamicFormByAppointmentId(id);
		model.addAttribute("appointment", appointment);
		model.addAttribute("dynamicFormList", dynamicFormList);
		if(APPOINTMENT_GOOD_TYPE == appointment.getType()){
			return "/m/operation/appoint/appointment-newshop";
		}else{
			return "/m/operation/appoint/appointment-newserve";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String appointmentEdit(Model model, AppointmentVo appointmentVo) {
		Date date = new Date();
		appointmentVo.setUpdateTime(date);
		if(null == appointmentVo.getMaxVal()){
			appointmentVo.setMaxVal(0);
		}
		Long userId = getUserId();
		appointmentBusiness.editAppointment(appointmentVo, userId);
		return getRedirectUrl("/u/appoint/list");
	}

	@RequestMapping(value = "/switch", method = RequestMethod.POST)
	@ResponseBody
	public String appointmentSwitch(Model model, Long id, Integer status) {
		appointmentBusiness.switchStatus(id, status);
		return getReqJson(true, "");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String appointmentDelete(Model model, Long id) {
		appointmentBusiness.removeAppointmentById(id);
		return getReqJson(true, "");
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String appointmentSave(Model model, @RequestParam(required = true) Long id, @RequestParam(required = true) String goodIds) {
		List<String> goodIdList = new ArrayList<String>();
		if(null != goodIds && goodIds.length() > 2){
			goodIdList = JsonUtil.toArray(goodIds);
		}
		appointmentBusiness.saveGood(goodIdList, id);
		return getReqJson(true, "");
	}

	@RequestMapping(value = "/good-manage/{id}")
	public String appointmentGoodManage(Model model, @PathVariable Long id, Pagination<GoodVo> pagination, GoodVo goodVo, String goodIds) {
		Appointment appointment = appointmentBusiness.getAppointmentById(id);
		AssertUtil.isTrue(getShopNo().equals(appointment.getShopNo()), "操作异常");
		List<String> goodIdList = new ArrayList<String>();
		if(null == goodIds){
			List<AppointmentSkuRelation> relationList = appointmentBusiness.findAppointmentSkuRelationList(id);
			for(AppointmentSkuRelation relation:relationList){
				goodIdList.add(relation.getGoodId().toString());
			}
		}else if(goodIds.length() > 2){
			goodIdList = JsonUtil.toArray(goodIds);
		}
		String shopNo = super.getShopNo();
		goodVo.setShopNo(shopNo);
		goodVo.setServiceId(Constants.SERVICE_ID_APPOINTMENT);
		pagination = goodBusiness.searchPageGood(goodVo, pagination);
		model.addAttribute("pagination", pagination);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Set<String> set = new HashSet<String>();
		for(String str:goodIdList){
			set.add(str);
		}
		for(GoodVo vo:pagination.getDatas()){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", vo.getId());
			map.put("title", vo.getName());
			map.put("src", vo.getPicUrl());
			map.put("price", vo.getSalesPrice());
			map.put("status", set.contains(vo.getId().toString()));
			list.add(map);
		}
		model.addAttribute("goodList", JsonUtil.fromCollection(list));
		List<Good> goodList = goodBusiness.findGoodByIds(goodIdList);
		List<Map<String, Object>> selectedList = new ArrayList<Map<String, Object>>();
		for(Good g:goodList){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", g.getId());
			map.put("title", g.getName());
			map.put("src", g.getPicUrl());
			String price=g.getSalesPrice().toString();
			map.put("price", new StringBuilder(price).insert(price.length()-2, ".").toString());
			selectedList.add(map);
		}
		model.addAttribute("selectedList", JsonUtil.fromCollection(selectedList));
		ShopTypeVo shopTypeVo = new ShopTypeVo();
		shopTypeVo.setShopNo(shopNo);
		shopTypeVo.setServiceId(Constants.SERVICE_ID_APPOINTMENT);
		List<ShopTypeVo> shopTypeList = shopTypeBusiness.findShopType(shopTypeVo);
		model.addAttribute("id", id);
		model.addAttribute("shopTypeList", shopTypeList);
		return "/m/operation/appoint/appointment-shop";
	}

	@RequestMapping(value = "/good-type-list")
	public String appointmentGoods(Model model, Pagination<ShopTypeVo> pagination, ShopTypeVo shopTypeVo) {
		String shopNo = super.getShopNo();
		shopTypeVo.setShopNo(shopNo);
		shopTypeVo.setServiceId(Constants.SERVICE_ID_APPOINTMENT);
		pagination = shopTypeBusiness.findPageShopType(shopTypeVo, pagination);
		model.addAttribute("pagination", pagination);
		return "/m/operation/appoint/shop-type-list";
	}

	@RequestMapping(value = "/good-type-add")
	public String appointmentGoodsAdd(Model model) {
		return "/m/operation/appoint/shop-type-edit";
	}

	@RequestMapping(value = "/good-type-edit")
	public String appointmentGoodsEdite(Model model, Long id) {
		ShopType shopType = shopTypeBusiness.getShopTypeById(id);
		AssertUtil.isTrue(getShopNo().equals(shopType.getShopNo()), "操作异常");
		model.addAttribute("shopType", shopType);
		return "/m/operation/appoint/shop-type-edit";
	}

	@RequestMapping(value = "/good-add")
	public String goodAdd(Model model) {
		String shopNo = getShopNo();
		ShopTypeVo shopTypeVo = new ShopTypeVo();
		shopTypeVo.setShopNo(shopNo);
		shopTypeVo.setServiceId(Constants.SERVICE_ID_APPOINTMENT);
		List<ShopTypeVo> shopTypeList = shopTypeBusiness.findShopType(shopTypeVo);
		model.addAttribute("shopTypeList", shopTypeList);
		return "/m/operation/appoint/good-edit";
	}

	@RequestMapping(value = "/good-add", method = RequestMethod.POST)
	public String goodAdd(Model model, GoodParam goodParam) {
		goodParam.setUserId(super.getUserId());
		goodParam.setShopNo(super.getShopNo());
		goodParam.setClassificationId(12l);
		goodParam.setMarketPrice(goodParam.getSalesPrice());
		goodParam.setProtocolSku(goodParam);
		appointmentGoodFactory.addGood(goodParam);
		return getRedirectUrl("/u/appoint/good-list");
	}

	@RequestMapping(value = "/good-edit")
	public String goodEdit(Model model, @RequestParam(required = true) Long id,Long shopTypeId,String goodName,Integer curPage) {
		model.addAttribute("shopTypeId",shopTypeId );
    	model.addAttribute("goodName",goodName );
    	model.addAttribute("curPage",curPage );
		String shopNo = getShopNo();
		Good good = goodBusiness.getGoodById(id);
		AssertUtil.isTrue(getShopNo().equals(good.getShopNo()), "操作异常");
		model.addAttribute("good", good);
		// 查询商品图片
		List<GoodImgVo> goodImgs = goodBusiness.findGoodImgByGoodIdAndType(id, 1);
		Map<String, GoodImgVo> goodImgMap = new LinkedHashMap<String, GoodImgVo>();
		for(GoodImgVo goodImgVo:goodImgs){
			goodImgMap.put(String.valueOf(goodImgVo.getPosition()), goodImgVo);
		}
		model.addAttribute("goodImgs", goodImgMap);
		List<GoodSku> skuList = goodBusiness.findGoodSkuByGoodId(id);
		model.addAttribute("skuList", skuList);
		ShopTypeVo shopTypeVo = new ShopTypeVo();
		shopTypeVo.setShopNo(shopNo);
		shopTypeVo.setGoodId(id);
		shopTypeVo.setServiceId(Constants.SERVICE_ID_APPOINTMENT);
		List<ShopTypeVo> shopTypeList = shopTypeBusiness.findShopType(shopTypeVo);
		model.addAttribute("shopTypeList", shopTypeList);
		return "/m/operation/appoint/good-edit";
	}

	@RequestMapping(value = "/good-edit", method = RequestMethod.POST)
	public String goodEdit(Model model, GoodParam goodParam,Long shopTypeId,String goodName,Integer curPage) {
		model.addAttribute("shopTypeId",shopTypeId );
    	model.addAttribute("name",goodName );
    	model.addAttribute("curPage",curPage );
		goodParam.setUserId(super.getUserId());
		goodParam.setShopNo(super.getShopNo());
		goodParam.setClassificationId(12l);
		goodParam.setMarketPrice(goodParam.getSalesPrice());
		GoodSku goodSku = goodParam.getSkuList().get(0);
		goodSku.setName(goodParam.getName());
		goodSku.setStock(goodParam.getInventory());
		goodSku.setSalesPrice(goodParam.getSalesPrice());
		goodSku.setMarketPrice(goodParam.getSalesPrice());
		appointmentGoodFactory.editGood(goodParam);
		return getRedirectUrl("/u/appoint/good-list");
	}

	@RequestMapping(value = "/good-list")
	public String goodList(Model model, Pagination<GoodVo> pagination, GoodVo goodVo) {
		String shopNo = super.getShopNo();
		goodVo.setShopNo(shopNo);
		goodVo.setServiceId(Constants.SERVICE_ID_APPOINTMENT);
		pagination = goodBusiness.searchPageGood(goodVo, pagination);
		model.addAttribute("pagination", pagination);
		ShopTypeVo shopTypeVo = new ShopTypeVo();
		shopTypeVo.setShopNo(shopNo);
		shopTypeVo.setServiceId(Constants.SERVICE_ID_APPOINTMENT);
		List<ShopTypeVo> shopTypeList = shopTypeBusiness.findShopType(shopTypeVo);
		model.addAttribute("shopTypeList", shopTypeList);
		return "/m/operation/appoint/good-list";
	}

	@RequestMapping(value = "/good-delete", method = RequestMethod.POST)
	@ResponseBody
	public String goodDelete(Model model, Long id) {
		goodBusiness.removeGood(getUserId(), id);
		return getReqJson(true, "");
	}

	@RequestMapping(value = "/good-batch-delete", method = RequestMethod.POST)
	@ResponseBody
	public String goodBatchDelete(Model model, String ids) {
		List<String> list = JsonUtil.toArray(ids);
		goodBusiness.removeAllGood(getUserId(), list);
		return getReqJson(true, "");
	}
}
