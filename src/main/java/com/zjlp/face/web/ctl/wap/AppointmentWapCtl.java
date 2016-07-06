package com.zjlp.face.web.ctl.wap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.util.json.JsonUtil;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.ctl.AppointmentCtl;
import com.zjlp.face.web.http.interceptor.Token;
import com.zjlp.face.web.security.cas.userdetails.TgtCookieRedirectToCas;
import com.zjlp.face.web.server.operation.appoint.bussiness.AppointmentBusiness;
import com.zjlp.face.web.server.operation.appoint.domain.Appointment;
import com.zjlp.face.web.server.operation.appoint.domain.DynamicForm;
import com.zjlp.face.web.server.product.good.business.GoodBusiness;
import com.zjlp.face.web.server.product.good.business.ShopTypeBusiness;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.vo.GoodSkuVo;
import com.zjlp.face.web.server.product.good.domain.vo.ShopTypeVo;
import com.zjlp.face.web.server.trade.order.bussiness.SalesOrderBusiness;
import com.zjlp.face.web.server.trade.order.domain.dto.SalesOrderVo;
import com.zjlp.face.web.server.trade.payment.util.WXPayUtil;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.WeixinUtil;

@Controller
@RequestMapping("/wap/{shopNo}/any")
public class AppointmentWapCtl extends WapCtl {
	private Log log = LogFactory.getLog(getClass());
	@Autowired
	private AppointmentBusiness appointmentBusiness;
	@Autowired
	private ShopTypeBusiness shopTypeBusiness;
	@Autowired
	private GoodBusiness goodBusiness;
	@Autowired
	private SalesOrderBusiness salesOrderBusiness;
	@Autowired
	private UserBusiness userBusiness;
	@Autowired(required=false)
	private TgtCookieRedirectToCas tgtCookieRedirectToCas;
	

	@RequestMapping(value = "/appoint/{id}")
	public String appointment(@PathVariable Long id, HttpServletRequest httpRequest,HttpServletResponse response,
			HttpSession session, Long shopTypeId, String json, Model model) throws Exception {
		log.info(id+","+json);
		String shopNo = getShopNo();
		String openId = (String) session.getAttribute("anonymousOpenId");
		String appId = PropertiesUtil.getContexrtParam("WECAHT_APP_ID");
		Assert.hasLength(appId, "WECAHT_APP_ID未配置");
		Long userId = getUserId();
		log.info("userId:"+userId);
		log.info("openId:"+openId);
		model.addAttribute("openId", openId);
		String wechatLoginSwitch = PropertiesUtil.getContexrtParam("WECHAT_LOGIN_SWITCH");
		if (null == userId) {
			Cookie tgtCookie = tgtCookieRedirectToCas.getTgtCookie(httpRequest);
			// 用户未登录且从微信浏览器访问时，去获取openid
			if(WeixinUtil.isWechatBrowser(httpRequest)&&!"1".equals(wechatLoginSwitch)) {
				if(!StringUtils.isEmpty(openId)) {
					userBusiness.registerAnonymousUser(openId);
				} else {
					String wgjurl = PropertiesUtil.getContexrtParam("WGJ_URL");
					Assert.hasLength(wgjurl, "WGJ_URL未配置");
					StringBuffer setOpendIdUrl = new StringBuffer();
					setOpendIdUrl = setOpendIdUrl.append(wgjurl).append("/wechat/getAnonymousOpenId").append(".htm");
					return super.getRedirectUrl(WXPayUtil.getoauth2Url(appId, setOpendIdUrl.toString(), "/wap/"+shopNo+"/any/appoint/"+id+Constants.URL_SUFIX));
				}
			// 非微信浏览器访问时，需登录
			}else {
				//TODO(补丁程序) APP访问获取Cookie重定向至CAS
				boolean isRedirectSuccess =  tgtCookieRedirectToCas.redirectToCas(httpRequest, response, tgtCookie);
				if (isRedirectSuccess) {
					return null;
				}
				//其他情况
				return super.getRedirectUrl("/wap/" + shopNo + "/login");
			}
		}

		Appointment appointment = appointmentBusiness.getAppointmentById(id);
		AssertUtil.isTrue(null != appointment && shopNo.equals(appointment.getShopNo()), "操作异常","操作异常");
		List<DynamicForm> dynamicFormList = appointmentBusiness.findDynamicFormByAppointmentId(id);
		model.addAttribute("appointment", appointment);
		model.addAttribute("dynamicFormList", dynamicFormList);
		model.addAttribute("id", id);
		if(AppointmentCtl.APPOINTMENT_GOOD_TYPE == appointment.getType()){
			ShopTypeVo shopTypeVo = new ShopTypeVo();
			shopTypeVo.setShopNo(shopNo);
			shopTypeVo.setServiceId(Constants.SERVICE_ID_APPOINTMENT);
			shopTypeVo.setAppointmentId(id);
			List<ShopTypeVo> shopTypeList=shopTypeBusiness.findAppointShopType(shopTypeVo);
			Set<Long> set=new HashSet<Long>();
			for(Iterator<ShopTypeVo> ite=shopTypeList.iterator();ite.hasNext();){
				ShopTypeVo vo=ite.next();
				if(set.contains(vo.getId())){
					ite.remove();
				}else{
					set.add(vo.getId());
				}
			}
			List<GoodSkuVo> goodSkuList = new ArrayList<GoodSkuVo>();
			if(null != shopTypeId){
				goodSkuList = goodBusiness.findGoodSkuByAppointmentIdAndShopTypeId(id, shopTypeId);
			}else if(shopTypeList.size() > 0){
				shopTypeId = shopTypeList.get(0).getId();
				for(ShopTypeVo vo:shopTypeList){
					goodSkuList = goodBusiness.findGoodSkuByAppointmentIdAndShopTypeId(id, vo.getId());
					if(goodSkuList.size() > 0){
						shopTypeId = vo.getId();
						break;
					}
				}
			}

			int num = 0;
			int count=0;
			if(json != null && json.length() > 2){
				List<GoodSkuVo> selectedList = JsonUtil.toArrayBean(json, GoodSkuVo.class);
				Map<Long, GoodSkuVo> map = new HashMap<Long, GoodSkuVo>();
				Long total = 0l;
				for(GoodSkuVo goodSkuVo:selectedList){
					map.put(goodSkuVo.getId(), goodSkuVo);
					GoodSku goodSku = goodBusiness.getGoodSkuById(goodSkuVo.getId());
					if(null == goodSku){
						selectedList.remove(goodSkuVo);
					}else{
						Long quantity = goodSkuVo.getQuantity() < goodSku.getStock() ? goodSkuVo.getQuantity() : goodSku.getStock();
						goodSkuVo.setQuantity(quantity);
						total += CalculateUtils.get(goodSku.getSalesPrice(), quantity);
					}
				}
				Long sub=0l;
				for(GoodSkuVo goodSkuVo:goodSkuList){
					if(map.containsKey(goodSkuVo.getId())){
						goodSkuVo.setQuantity(map.get(goodSkuVo.getId()).getQuantity());
						count++;
						sub+=CalculateUtils.get(goodSkuVo.getSalesPrice(), goodSkuVo.getQuantity());
					}
				}
				json = JsonUtil.fromCollection(selectedList, false, new String[]{"id", "quantity"});
				model.addAttribute("json", json);
				num = selectedList.size();
				model.addAttribute("otherTotal", total-sub);
			}
			model.addAttribute("shopTypeId", shopTypeId);
			model.addAttribute("num", num);
			model.addAttribute("otherNum",num-count);
			model.addAttribute("goodSkuList", goodSkuList);
			model.addAttribute("shopTypeList", shopTypeList);
			if(appointment.getTemplateId() == 2){
				return "/wap/operation/appoint/index2";
			}
			return "/wap/operation/appoint/index";
		}else{
			User user=null;
			if(null == userId){
				user = userBusiness.getUserByOpenId(openId);
			}else{
				user=userBusiness.getUserById(userId);
			}
			model.addAttribute("user", user);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = sdf.format(date);
			model.addAttribute("dateString", dateString);
			return "/wap/operation/appoint/index3";
		}
	}

	@RequestMapping(value = "/appoint/good/{id}")
	@Token(save = true)
	public String appointment(@PathVariable Long id, @RequestParam(required = true) String json, String openId,Model model) throws Exception {
		log.info(id+","+json+","+openId);
		Appointment appointment = appointmentBusiness.getAppointmentById(id);
		AssertUtil.isTrue(null != appointment && getShopNo().equals(appointment.getShopNo()), "操作异常");
		AssertUtil.isTrue(AppointmentCtl.APPOINTMENT_GOOD_TYPE == appointment.getType(), "预约项目不存在");
		model.addAttribute("id", id);
		List<DynamicForm> dynamicFormList = appointmentBusiness.findDynamicFormByAppointmentId(id);
		model.addAttribute("dynamicFormList", dynamicFormList);
		model.addAttribute("json", json);
		Long num = 0l;
		if(json != null && json.length() > 2){
			List<GoodSkuVo> selectedList = JsonUtil.toArrayBean(json, GoodSkuVo.class);
			Map<Long, GoodSkuVo> map = new HashMap<Long, GoodSkuVo>();
			Long total = 0l;
			for(GoodSkuVo goodSkuVo:selectedList){
				map.put(goodSkuVo.getId(), goodSkuVo);
				GoodSku goodSku = goodBusiness.getGoodSkuById(goodSkuVo.getId());
				Long quantity = goodSkuVo.getQuantity() < goodSku.getStock() ? goodSkuVo.getQuantity() : goodSku.getStock();
				goodSkuVo.setName(goodSku.getName());
				goodSkuVo.setQuantity(quantity);
				goodSkuVo.setSalesPrice(goodSku.getSalesPrice());
				goodSkuVo.setPicturePath(goodSku.getPicturePath());
				total += CalculateUtils.get(goodSku.getSalesPrice(), quantity);
				num = num + quantity;
			}
			model.addAttribute("selectedList", selectedList);
			model.addAttribute("total", total);
		}
		model.addAttribute("num", num);
		model.addAttribute("openId", openId);
		Long userId = getUserId();
		User user=null;
		if(null == userId){
			user = userBusiness.getUserByOpenId(openId);
		}else{
			user=userBusiness.getUserById(userId);
		}
		model.addAttribute("user", user);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = sdf.format(date);
		model.addAttribute("dateString", dateString);
		return "/wap/operation/appoint/reserve-appointment";
	}

	@RequestMapping(value = "/appoint/good/{id}", method = RequestMethod.POST)
	@Token(remove = true)
	public String appointment(@PathVariable Long id, HttpSession session, @RequestParam(required = true) String json, String openId,SalesOrderVo salesOrderVo, Model model, HttpServletRequest request) throws Exception {
		log.info("预约订单开始,"+id+","+json+","+openId);
		String shopNo = getShopNo();
		Appointment appointment = appointmentBusiness.getAppointmentById(id);
		AssertUtil.notNull(appointment, "预约项目不存在");
		AssertUtil.isTrue(AppointmentCtl.APPOINTMENT_GOOD_TYPE == appointment.getType(), "预约项目不存在");
		Long userId = getUserId();
		log.info("userId:"+userId);
		User user=null;
		if(null == userId){
			log.info("openId:"+openId);
			if(StringUtils.isEmpty(openId)){
				model.addAttribute("retUrl", "/wap/" + shopNo + "/any/appoint/" + id + ".htm?json=" + json);
				return getRedirectUrl("/wap/" + shopNo + "/login");
			}
			user = userBusiness.getUserByOpenId(openId);
			if(null == user){
				model.addAttribute("retUrl", "/wap/" + shopNo + "/any/appoint/" + id + ".htm?json=" + json);
				return getRedirectUrl("/wap/" + shopNo + "/login");
			}
			userId=user.getId();
		}else{
			user=userBusiness.getUserById(userId);
		}
		salesOrderVo.setUserId(userId);
		salesOrderVo.setShopNo(shopNo);
		List<GoodSkuVo> selectedList = JsonUtil.toArrayBean(json, GoodSkuVo.class);
		salesOrderBusiness.addAppointOrder(id, salesOrderVo, selectedList);
		model.addAttribute("user", user);
		return "/wap/operation/appoint/success";
	}

	@RequestMapping(value = "/appoint/serve/{id}", method = RequestMethod.POST)
	public String appointment(@PathVariable Long id, HttpSession session, SalesOrderVo salesOrderVo, HttpServletRequest request, Model model) throws Exception {
		String shopNo = getShopNo();
		Long userId = getUserId();
		User user=null;
		if(null == userId){
			String openId = (String) session.getAttribute("anonymousOpenId");
			if(StringUtils.isEmpty(openId)){
				model.addAttribute("retUrl", "/wap/" + shopNo + "/any/appoint/" + id);
				return getRedirectUrl("/wap/" + shopNo + "/login");
			}
			user = userBusiness.getUserByOpenId(openId);
			if(null == user){
				model.addAttribute("retUrl", "/wap/" + shopNo + "/any/appoint/" + id);
				return getRedirectUrl("/wap/" + shopNo + "/login");
			}
			userId=user.getId();
		}else{
			user=userBusiness.getUserById(userId);
		}
		Appointment appointment = appointmentBusiness.getAppointmentById(id);
		AssertUtil.notNull(appointment, "预约项目不存在");
		AssertUtil.isTrue(AppointmentCtl.APPOINTMENT_SERVE_TYPE == appointment.getType(), "预约项目不存在");
		salesOrderVo.setUserId(userId);
		salesOrderVo.setShopNo(shopNo);
		salesOrderBusiness.addAppointOrder(id, salesOrderVo, null);
		model.addAttribute("user", user);
		return "/wap/operation/appoint/success";
	}

	@RequestMapping(value = "/appoint/infodetails/{skuId}")
	public String goodDetail(@PathVariable Long skuId, Model model) throws Exception {
		GoodSku goodSku = goodBusiness.getGoodSkuById(skuId);
		if(null != goodSku){
			Good good = goodBusiness.getGoodById(goodSku.getGoodId());
			model.addAttribute("good", good);
		}
		model.addAttribute("goodSku", goodSku);
		return "/wap/operation/appoint/reserve-infodetails";
	}

	@RequestMapping(value = "/appoint/findGood/{id}")
	public String findGood(@PathVariable Long id, Long shopTypeId, String json, Model model) throws Exception {
		List<GoodSkuVo> goodSkuList = goodBusiness.findGoodSkuByAppointmentIdAndShopTypeId(id, shopTypeId);
		model.addAttribute("goodSkuList", goodSkuList);
		int num = 0;
		long total = 0l;
		Integer currentTypeNum = 0;
		if(json != null && json.length() > 2){
			List<GoodSkuVo> selectedList = JsonUtil.toArrayBean(json, GoodSkuVo.class);
			Map<Long, GoodSkuVo> map = new HashMap<Long, GoodSkuVo>();
			for(GoodSkuVo goodSkuVo:selectedList){
				map.put(goodSkuVo.getId(), goodSkuVo);
				GoodSku goodSku = goodBusiness.getGoodSkuById(goodSkuVo.getId());
				if(null == goodSku){
					selectedList.remove(goodSkuVo);
				}else{
					Long quantity = goodSkuVo.getQuantity() < goodSku.getStock() ? goodSkuVo.getQuantity() : goodSku.getStock();
					goodSkuVo.setQuantity(quantity);
					total += CalculateUtils.get(goodSku.getSalesPrice(), quantity);
					num += goodSkuVo.getQuantity();
				}
			}

			for(GoodSkuVo goodSkuVo:goodSkuList){
				if(map.containsKey(goodSkuVo.getId())){
					goodSkuVo.setQuantity(map.get(goodSkuVo.getId()).getQuantity());
					currentTypeNum ++;
				}
			}
			json = JsonUtil.fromCollection(selectedList, false, new String[]{"id", "quantity"});
			num = selectedList.size();
		}
		model.addAttribute("json", json);
		model.addAttribute("num", num-currentTypeNum);
		model.addAttribute("currentTypeNum", currentTypeNum == 0 ? null : currentTypeNum);
		model.addAttribute("total", total);
		return "/wap/operation/appoint/goodSkuAppend";
	}
}
