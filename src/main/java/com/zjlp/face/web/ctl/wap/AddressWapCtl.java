package com.zjlp.face.web.ctl.wap;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.AddressException;
import com.zjlp.face.web.server.user.user.domain.Address;
import com.zjlp.face.web.server.user.user.domain.vo.AddressVo;
import com.zjlp.face.web.server.user.user.producer.AddressProducer;

@Controller
@RequestMapping("/wap/{shopNo}/buy/address/")
public class AddressWapCtl extends WapCtl {

	@Autowired
	private AddressProducer addressProducer;

	@RequestMapping(value = "index")
	public String index(Model model,Long editAddressId) {
		List<Address> addressList = addressProducer.findAddressByUserId(super
				.getUserId());
		model.addAttribute("list", addressList);
		model.addAttribute("editAddressId", editAddressId);
		return "/wap/user/personal/address/index";
	}

	/**
	 * 编辑页
	 * 
	 * @Title: editPage
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param address
	 * @param model
	 * @return
	 * @date 2015年3月30日 下午6:22:18
	 * @author lys
	 */
	@RequestMapping(value = "editAddress", method = RequestMethod.GET)
	public String editPage(Long id, Model model) {
		if (null != id) {
			AddressVo address = addressProducer.getAddressVoById(id);
			model.addAttribute("address", address);
		}
		return "/wap/user/personal/address/edit";
	}

	/**
	 * 编辑
	 * 
	 * @Title: addOrEdit
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 * @param vAreaCode
	 * @param userName
	 * @param cell
	 * @param addressDetail
	 * @return
	 * @date 2015年3月30日 下午6:35:45
	 * @author lys
	 */
	@RequestMapping(value = "editAddress", method = RequestMethod.POST)
	public String addOrEdit(Model model,Long id, @RequestParam Integer vAreaCode,
			@RequestParam String userName, @RequestParam String cell,
			@RequestParam String addressDetail) {
		addressProducer.addOrEditAddress(id, super.getUserId(), vAreaCode,
				userName, cell, addressDetail);
		model.addAttribute("editAddressId", id);
		return super.getRedirectUrl("index");
	}
	
	/**
	 * 设置默认地址
	 * @Title: defaultAddress 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年3月31日 上午9:26:16  
	 * @author lys
	 */
	@RequestMapping(value="default", method=RequestMethod.POST)
	@ResponseBody
	public String defaultAddress(@RequestParam Long id) {
		try {
			addressProducer.setDefaultAddress(id, getUserId());
			return super.getReqJson(true, null);
		} catch (BaseException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}
	
	/**
	 * 删除收货地址
	 * @Title: delAddress 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年3月31日 上午9:31:58  
	 * @author lys
	 */
	@RequestMapping(value = "del", method = RequestMethod.POST)
	@ResponseBody
	public String delAddress(@RequestParam Long id) {
		try {
			addressProducer.removeAddressById(id);
			return super.getReqJson(true, null);
		} catch (AddressException e) {
			return super.getReqJson(false, e.getExternalMsg());
		}
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(Address address) {
		address.setUserCode(getUserId());
		address.setStatus(Constants.VALID);
		address.setZipCode("000000");
		address.setSort(0);
		address.setRealType(1);
		addressProducer.addAddress(address);
		return JSONObject.fromObject(address).toString();
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public String edit(Address address) {
		addressProducer.editAddress(address);
		return "success";
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public String delete(Long id) {
		addressProducer.removeAddressById(id);
		return id.toString();
	}

	@RequestMapping(value = "setDefault", method = RequestMethod.POST)
	@ResponseBody
	public String setDefault(Long id) {
		addressProducer.setDefaultAddress(id, getUserId());
		return id.toString();
	}
	@RequestMapping(value = "get", method = RequestMethod.POST)
	@ResponseBody
	public String get(Long id) {
		AddressVo address = addressProducer.getAddressVoById(id);
		return JSONObject.fromObject(address).toString();
	}
}
