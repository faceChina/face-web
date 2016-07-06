package com.zjlp.face.web.ctl.wap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.web.constants.MobilecodeType;
import com.zjlp.face.web.server.product.phone.business.PhoneBusiness;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;

@Controller
@RequestMapping("/wap/{shopNo}/buy/mobilecode/")
public class MobilecodeWapCtl extends WapCtl {

	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private PhoneBusiness phoneBusiness;
	
	/**
	 * 发送手机验证码
	 * @Title: getPhonecode 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param type 类型
	 * @param phone 手机号
	 * @return 
	 * @date 2015年3月26日 下午2:14:14  
	 * @author lys
	 */
	@RequestMapping(value="phoneCode", method=RequestMethod.POST)
	@ResponseBody
	public String getPhonecode(@RequestParam Integer type, String phone) {
		MobilecodeType mobileType = MobilecodeType.getByScene(type);
		if (null == mobileType) {
			return "false";
		} else if (!mobileType.isInput()) {
			User user = userBusiness.getUserById(getUserId());
			if (StringUtils.isBlank(phone)) {
				phone = user.getCell();
			}
		}
		if (StringUtils.isBlank(phone)) return "false";
		phoneBusiness.createMobilecode(phone, type);
		return "true";
	}
	
	/**
	 * 测试验证码
	 * @Title: testMobilecode 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param type
	 * @param mobilecode
	 * @param phone
	 * @return
	 * @date 2015年4月6日 上午10:41:04  
	 * @author lys
	 */
	@RequestMapping(value="testMobilecode", method=RequestMethod.POST)
	@ResponseBody
	public String testMobilecode(@RequestParam Integer type, @RequestParam String mobilecode, String phone) {
		MobilecodeType mobileType = MobilecodeType.getByScene(type);
		if (null == mobileType) {
			return super.getReqJson(false, "System error!");
		} else if (!mobileType.isInput()) {
			User user = userBusiness.getUserById(getUserId());
			if (StringUtils.isBlank(phone)) {
				phone = user.getCell();
			}
		}
		if (StringUtils.isBlank(phone)) {
			return super.getReqJson(false, "System error!");
		}
		try {
			phoneBusiness.testMobilecode(phone, type, mobilecode);
		} catch (Exception e) {
			return super.getReqJson(false, "验证码输入错误");
		}
		return super.getReqJson(true, null);
	}
}
