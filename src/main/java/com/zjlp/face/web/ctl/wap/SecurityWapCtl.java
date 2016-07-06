package com.zjlp.face.web.ctl.wap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjlp.face.account.domain.Account;
import com.zjlp.face.util.constants.ConstantsMethod;
import com.zjlp.face.util.exception.BaseException;
import com.zjlp.face.web.constants.MobilecodeType;
import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.http.interceptor.Token;
import com.zjlp.face.web.server.product.phone.business.PhoneBusiness;
import com.zjlp.face.web.server.trade.account.business.AccountBusiness;
import com.zjlp.face.web.server.user.user.business.UserBusiness;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;
import com.zjlp.face.web.util.WeixinUtil;

@Controller
@RequestMapping("/wap/{shopNo}/buy/account/security/")
public class SecurityWapCtl extends WapCtl {

	@Autowired
	private UserBusiness userBusiness;
	@Autowired
	private AccountBusiness accountBusiness;
	@Autowired
	private PhoneBusiness phoneBusiness;

	/**
	 * 安全设置首页
	 * 
	 * @Title: index
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param model
	 * @return
	 * @date 2015年3月23日 下午5:17:06
	 * @author lys
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(Model model,HttpServletRequest httpRequest) {
		User user = userBusiness.getUserById(getUserId());
		boolean isWechat = super.getIsWechat(user);
		model.addAttribute("isWechat", isWechat);
		if (!isWechat || StringUtils.isNotEmpty(user.getCell())) {
			model.addAttribute("phone",ConstantsMethod.replaceToHide(user.getCell(), 3, 4));
		}
		//判断是否从微信浏览器进来
		boolean isBrowser = WeixinUtil.isWechatBrowser(httpRequest);
		model.addAttribute("isBrowser", isBrowser);
		model.addAttribute("isDefaultLogin", User.REGISTERTYPE_DEFAULT.equals(user.getRegisterSourceType()));
		
		return "/wap/user/security/index";
	}

	// --------------修改登陆密码---------------

	/**
	 * 登陆密码修改
	 * 
	 * @Title: loginCode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param retUrl
	 *            返回url
	 * @param model
	 * @return
	 * @date 2015年3月23日 下午7:13:24
	 * @author lys
	 */
	@RequestMapping(value = "logincode", method = RequestMethod.GET)
	public String loginCode(@RequestParam String retUrl, String errorMsg, Model model) {
		model.addAttribute("retUrl", retUrl);
		model.addAttribute("errMsg", errorMsg);
		return "/wap/user/security/logincode";
	}

	/**
	 * 登陆密码修改
	 * 
	 * @Title: editLoginCode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param retUrl
	 *            返回url
	 * @param passwd
	 *            旧密码
	 * @param newPasswd
	 *            新密码
	 * @param confirmPasswd
	 *            确认密码
	 * @return
	 * @date 2015年3月23日 下午7:19:08
	 * @author lys
	 */
	@RequestMapping(value = "logincode", method = RequestMethod.POST)
	public String editLoginCode(@RequestParam String retUrl,
			@RequestParam String passwd, @RequestParam String newPasswd,
			@RequestParam String confirmPasswd, Model model) {
		try {
			userBusiness.editUserPasswd(super.getUserId(), passwd, newPasswd,
					confirmPasswd);
			return super.getRedirectUrl(retUrl);
		} catch (UserException e) {
			model.addAttribute("retUrl", retUrl);
			model.addAttribute("errorMsg", e.getExternalMsg());
			return super.getRedirectUrl("logincode");
		}
	}

	// --------------手机绑定-----------------
	
	/**
	 * 微信注册用户手机绑定
	 * 
	 * @Title: wechatphone
	 * @Description: (跳转)
	 * @param retUrl
	 *            跳转页
	 * @param model
	 * @return
	 * @date 2015年9月24日 下午3:25:06
	 * @author talo
	 */
	@RequestMapping(value = "wechatphone", method = RequestMethod.GET)
	public String initWechatBindPhone (@RequestParam(required = false) String retUrl, Model model, String errorMsg) {
		User user = userBusiness.getUserById(super.getUserId());
		model.addAttribute("retUrl", retUrl);
		if (StringUtils.isNotBlank(user.getCell())) {
			return "/wap/user/security/phonecode";
		}
		return "/wap/user/security/wechat-bind-phone";
	}
	
	/**
	 * 微信注册用户手机绑定
	 * 
	 * @Title: wechatphone
	 * @Description: (保存)
	 * @param retUrl
	 *            跳转页
	 * @param model
	 * @return
	 * @date 2015年9月24日 下午3:25:06
	 * @author talo
	 */
	@Token(save=true)
	@RequestMapping(value = "wechatbindphone", method = RequestMethod.POST)
	public String wechatBindPhone (@RequestParam(required = false) String retUrl,@RequestParam String phone,@RequestParam String mobilecode, Model model) {
		try {
			phoneBusiness.checkMobilecode(phone, MobilecodeType.mobile_phonecode_0.getScene(), mobilecode);
			userBusiness.editUserCell(super.getUserId(), phone);
			model.addAttribute("errorMsg", "手机绑定成功");
			//手机绑定成功之后，直接跳到支付密码设置页面
			model.addAttribute("retUrl", retUrl);
			User user = userBusiness.getUserById(super.getUserId());
			UserVo vo = new UserVo(user.getContacts(), user.getIdentity());
			model.addAttribute("user", vo);
			return "/wap/user/security/set-payment-code";
		} catch (BaseException e) {
			model.addAttribute("errorMsg", "验证码输入错误");
			model.addAttribute("retUrl", retUrl);
			return "/wap/user/security/wechat-bind-phone";
		}
	}
	
	/**
	 * 手机重新绑定
	 * 
	 * @Title: phoneCode
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param retUrl
	 *            跳转页
	 * @param model
	 * @return
	 * @date 2015年3月23日 下午7:25:06
	 * @author lys
	 */
	@RequestMapping(value = "phonecode", method = RequestMethod.GET)
	public String phoneCode(@RequestParam String retUrl, Model model, String errorMsg) {
		Long userId = super.getUserId();
		User user = userBusiness.getUserById(userId);
		model.addAttribute("phone", ConstantsMethod.replaceToHide(user.getCell(), 3, 3));
		model.addAttribute("retUrl", retUrl);
		model.addAttribute("errMsg", errorMsg);
		return "/wap/user/security/phonecode";
	}

	/**
	 * 旧手机号码验证
	 * @Title: checkPhone 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param retUrl
	 * @param mobilecode
	 * @param model
	 * @return
	 * @date 2015年3月23日 下午7:49:01  
	 * @author lys
	 */
	@Token(save=true)
	@RequestMapping(value = "phonecode", method = RequestMethod.POST)
	public String checkPhone(@RequestParam String retUrl,
			@RequestParam String mobilecode, Model model) {
		try {
			User user = userBusiness.getUserById(super.getUserId());
			model.addAttribute("retUrl", retUrl);
			phoneBusiness.checkMobilecode(user.getCell(), 
					MobilecodeType.mobile_phonecode_0.getScene(), mobilecode);
		} catch (BaseException e) {
			model.addAttribute("errorMsg", "验证码输入错误");
			return super.getRedirectUrl("phonecode");
		}
		return "/wap/user/security/set-phone-code";
	}

	/**
	 * 新手机绑定
	 * @Title: bindPhone 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param retUrl
	 * @param phone
	 * @param mobilecode
	 * @return
	 * @date 2015年3月23日 下午7:49:12  
	 * @author lys
	 */
	@Token(remove=true)
	@RequestMapping(value = "bindPhone", method = RequestMethod.POST)
	public String bindPhone(@RequestParam String retUrl,
			@RequestParam String phone, @RequestParam String mobilecode, Model model) {
		try {
			Long userId = super.getUserId();
			// 验证码验证
			phoneBusiness.checkMobilecode(phone, 
					MobilecodeType.mobile_phonecode_1.getScene(), mobilecode);
			userBusiness.editUserCell(userId, phone);
			return super.getRedirectUrl(retUrl);
		} catch (BaseException e) {
			model.addAttribute("errorMsg", "验证码输入错误");
			model.addAttribute("retUrl", retUrl);
			return super.getRedirectUrl("phonecode");
		}
	}

	// ----------------支付密码&姓名&身份证------------------

	/**
	 * 验证绑定手机
	 * 
	 * @Title: bindPhone
	 * @Description: (设置支付密码的时候)
	 * @param retUrl
	 *            最终跳转页
	 * @param model
	 * @return
	 * @date 2015年3月23日 下午4:30:55
	 * @author lys
	 */
	@RequestMapping(value = "paymentCode", method = RequestMethod.GET)
	public String bindPhone(@RequestParam(required = true) String retUrl, String errorMsg,
			Model model) {
		User user = userBusiness.getUserById(getUserId());
		Account account = accountBusiness.getAccountByUserId(user.getId());
		if (StringUtils.isBlank(account.getPaymentCode())) {
			model.addAttribute("isSetPaymentCode", false);
		} else {
			model.addAttribute("isSetPaymentCode", true);
		}
		if (StringUtils.isNotEmpty(user.getCell())) {
			model.addAttribute("phone",ConstantsMethod.replaceToHide(user.getCell(), 3, 4));
		}else{
			model.addAttribute("retUrl", retUrl);
			return "/wap/user/security/wechat-bind-phone";
		}
		model.addAttribute("retUrl", retUrl);
		model.addAttribute("errorMsg", errorMsg);
		return "/wap/user/security/bind-phone-check";
	}

	/**
	 * 手机验证
	 * 
	 * @Title: checkBind
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param retUrl
	 *            返回页
	 * @param phone
	 *            手机号码
	 * @param model
	 * @return
	 * @date 2015年3月23日 下午4:34:25
	 * @author lys
	 */
	@Token(save=true)
	@RequestMapping(value = "paymentCode", method = RequestMethod.POST)
	public String checkBind(@RequestParam(required = true) String retUrl,
			@RequestParam(required = true) String mobilecode, Model model) {
		try {
			model.addAttribute("retUrl", retUrl);
			User user = userBusiness.getUserById(super.getUserId());
			phoneBusiness.checkMobilecode(user.getCell(), 
					MobilecodeType.mobile_paymentcode_0.getScene(), mobilecode);
			UserVo vo = new UserVo(user.getContacts(), user.getIdentity());
			model.addAttribute("user", vo);
			return "/wap/user/security/set-payment-code";
		} catch (BaseException e) {
			model.addAttribute("errorMsg", "验证码输入错误");
			return super.getRedirectUrl("paymentCode");
		}
	}

	/**
	 * 用户资料补全
	 * 
	 * @Title: set
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param retUrl
	 *            跳转页
	 * @param userName
	 *            用户名
	 * @param identity
	 *            身份证
	 * @param paymentCode
	 *            支付密码
	 * @param confirmCode
	 *            确认支付密码
	 * @return
	 * @date 2015年3月23日 下午4:50:24
	 * @author lys
	 */
	@Token(remove=true)
	@RequestMapping(value = "set", method = RequestMethod.POST)
	public String set(@RequestParam String retUrl, String userName, String identity, 
			@RequestParam String paymentCode,
			@RequestParam String confirmCode, Model model) {
		Long userId = super.getUserId();
		accountBusiness.completInformation(userId, paymentCode,
				confirmCode, userName, identity);
		if (retUrl.indexOf(".htm") != -1) {
			return super.getRedirectUrlNoHtm(retUrl);
		} else {
			return super.getRedirectUrl(retUrl);
		}
		
	}
	
}
