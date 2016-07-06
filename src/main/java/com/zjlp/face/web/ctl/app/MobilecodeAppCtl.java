package com.zjlp.face.web.ctl.app;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.web.constants.MobilecodeType;
import com.zjlp.face.web.server.product.phone.business.PhoneBusiness;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.util.AssConstantsUtil;

@Controller
@RequestMapping("/assistant/any/account/")
public class MobilecodeAppCtl extends BaseCtl {

	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private PhoneBusiness phoneBusiness;
	
	/**
	 * 手机验证码
	* @Title: getPhonecode
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param jsonObj
	* @param type
	* @return String    返回类型
	* @author wxn  
	* @date 2015年4月13日 下午3:05:41
	 */
	@RequestMapping(value="phoneCode/{type}", method=RequestMethod.POST)
	@ResponseBody
	public String getPhonecode(@RequestBody JSONObject jsonObj,@PathVariable Integer type) {
		
		if (null == jsonObj ){
			return outFailure(AssConstantsUtil.System.DATA_ERROR_CODE, "");
		}
		String phone = jsonObj.optString("phone");
		MobilecodeType mobileType = MobilecodeType.getByScene(type);
		if (null == mobileType) {
			return outFailure(AssConstantsUtil.UserCode.PHONE_NO_BINDING_CODE, "");
		} else if (!mobileType.isInput()) {
			User user = userBusiness.getUserById(getUserId());
		    phone = user.getCell();
		}
		if (StringUtils.isBlank(phone)){
			return outFailure(AssConstantsUtil.UserCode.PHONE_NO_BINDING_CODE, "");
		} 
		/**4.8assistant*/
		String json = phoneBusiness.createMobilecodeByJson(phone, type);
		JSONObject jsonObject = JSONObject.fromObject(json);
		if (!"true".equals(jsonObject.get("flag"))) {
			String result = jsonObject.get("result").toString();
			if ("32".equals(result)) {
				return outFailure(AssConstantsUtil.UserCode.SEND_MOBILECODE_MUTIL);
			} if ("-2".equals(result)) {
				return outFailure(AssConstantsUtil.UserCode.SEND_MOBILECODE_MUTIL_2);
			} else {
				return outFailure(AssConstantsUtil.UserCode.SEND_MOBILECODE_FAIL);
			}
		}
		/**4.8assistant*/
		return outSucceedByNoData();
	}
	
}
