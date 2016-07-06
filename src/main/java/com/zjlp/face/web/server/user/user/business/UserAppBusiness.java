package com.zjlp.face.web.server.user.user.business;

import net.sf.json.JSONObject;

import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.server.user.user.domain.vo.UserVo;

public interface UserAppBusiness {
	
	/**
	 * 验证手机验证码
	 * @param phone			手机号码
	 * @param phoneCode		手机验证码
	 * @return				错误:{错误码:错误信息} @see {@link com.zjlp.face.web.util.ResultCode}<br>
	 * 						正确:{"success":"ok"}
	 */
	JSONObject checkPhoneCode(String phone, String phoneCode);
	
	/**
	 * 注册
	 * @param userVo		用户对象
	 * @return				错误:{错误码:错误信息} @see {@link com.zjlp.face.web.util.ResultCode}<br>
	 * 						正确:{"success":"ok"}
	 */
	JSONObject register(UserVo userVo);
	
	/**
	 * 注册
	 * @param userVo				用户对象
	 * @param isGenarateShop		是否开店
	 * @return				错误:{错误码:错误信息} @see {@link com.zjlp.face.web.util.ResultCode}<br>
	 * 						正确:{"success":"ok"}
	 */
	JSONObject register(UserVo userVo,Boolean isGenarateShop);
	
	/**
	 * 登录
	 * @param userName		用户名
	 * @param password		密码
	 * @return				错误:{错误码:错误信息} @see {@link com.zjlp.face.web.util.ResultCode}<br>
	 * 						正确:{"success":"ok","userInfo",userInfo}
	 */
	JSONObject login(String userName, String password);
	
	/**
	 * 生成官网店铺
	 * @param loginAccount		登录账号
	 * @return					错误:{错误码:错误信息} @see {@link com.zjlp.face.web.util.ResultCode}<br>
	 * 							正确:{"success":"ok"}
	 */
	JSONObject activateShop(String loginAccount);
	
	JSONObject activateShopLock(String loginAccount);
}
