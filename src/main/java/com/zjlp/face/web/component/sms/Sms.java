package com.zjlp.face.web.component.sms;

import org.apache.log4j.Logger;

import com.zjlp.face.ums.service.UmsService;
import com.zjlp.face.util.file.PropertiesUtil;
import com.zjlp.face.web.job.PersistenceJobServiceLocator;

public class Sms {
	
	public static final String SMS_PHONE_REG="^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";

	private Logger _logger = Logger.getLogger(getClass());
	
	private static final String SWITCH_OPEN = "1";
	
	private UmsService umsService;
	
	private static Sms instance = new Sms();
	
	public static synchronized Sms getInstance() {
		return instance;
	}
	
	private Sms() {
		this.umsService = PersistenceJobServiceLocator.getJobService(UmsService.class);
	}
	
	/**
	 * 加入特定开关的短信
	 * @Title: sendUmsBySwitch 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param joinSwitch 加入的开关
	 * @param content 短信类型
	 * @param phone 手机号码
	 * @param args 发送参数
	 * @date 2014年12月6日 下午3:46:30  
	 * @author Administrator
	 */
	public void sendUmsBySwitch(SwitchType joinSwitch, SmsContent content, String phone, Object...args) {
		String isOpen = PropertiesUtil.getContexrtParam(joinSwitch.getType());
		//开关关闭
		if (!SWITCH_OPEN.equals(isOpen)) {
			return;
		}
		this.sendUms(content, phone, args);
	}
	
	/**
	 * 发送短信
	 * @Title: sendUmsBySwitch 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param content 短信类型
	 * @param phone 手机号码
	 * @param args 发送参数
	 * @date 2014年12月6日 下午3:46:30  
	 * @author Administrator
	 */
	public void sendUms(SmsContent content, String phone, Object...args) {
		try {
			_logger.info(buildMessage("[发送短信]手机号码：{}，短信内容：", phone));
			_logger.info(buildMessage(content.getContent(), args));
			boolean isSucc = umsService.send(arrayToString(args), content.getTemplateCode(), phone);
			_logger.info(buildMessage("发送结果：{}", isSucc ? "成功" : "失败"));
		} catch (Exception e) {
			_logger.info("发送结果异常", e);
		}
	}
	
	
	public String sendUmsByJson(SwitchType joinSwitch, SmsContent content, String phone, Object...args) {
		String isOpen = PropertiesUtil.getContexrtParam(joinSwitch.getType());
		//开关关闭
		if (!SWITCH_OPEN.equals(isOpen)) {
			return "";
		}
		return this.sendUmsByJson(content, phone, args);
	}
	
	public String sendUmsByJson(SmsContent content, String phone, Object...args) {
		try {
			_logger.info(buildMessage("[发送短信]手机号码：{}，短信内容：", phone));
			_logger.info(buildMessage(content.getContent(), args));
			String json = umsService.sendByJson(arrayToString(args), content.getTemplateCode(), phone);
			_logger.info(buildMessage("发送结果：{}", json));
			return json;
		} catch (Exception e) {
			_logger.info("发送结果异常", e);
			return "";
		}
	}
	
	/**
	 * 开关类型
	 * @ClassName: SwitchType 
	 * @Description: (这里用一句话描述这个类的作用) 
	 * @author Administrator
	 * @date 2014年12月6日 下午3:53:49
	 */
	public enum SwitchType {
		
		//订单短信开关
		UMS_ORDER_SWITCH("SWITCH_UMS_ORDER"),
		
		//提现短信开关
		UMS_WITHDRAW_SWITCH("SWITCH_UMS_WITHDRAW"),
		
		//提现短信开关
		UMS_PHONECODE_SWITCH("SWITCH_UMS_PHONECODE"),
		
		//推广
		UMS_POPULARIZE_SWITCH("SWITCH_UMS_POPULARIZE"),
		;
		
		private String type;
		
		private SwitchType(String type){
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}
	
	/**
	 * 消息类型转换
	 * @Title: arrayToString 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param ts
	 * @return
	 * @date 2014年12月6日 下午3:54:35  
	 * @author Administrator
	 */
	private static <T> String[] arrayToString(T...ts) {
		if (null == ts || ts.length <= 0) {
			return null;
		}
		String[] arr = new String[ts.length];
		for (int i = 0; i < ts.length; i++) {
			arr[i] = String.valueOf(ts[i]);
		}
		return arr;
	}
	
	/**
	 * 日志组装
	 * @Title: buildMessage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param content
	 * @param args
	 * @return
	 * @date 2014年12月6日 下午3:54:48  
	 * @author Administrator
	 */
	private static String buildMessage(String content, Object...args) {
		if (null == content || args == null || args.length <= 0) {
			return content;
		}
		for (Object obj : args) {
			content = content.replaceFirst("\\{\\}", String.valueOf(obj));
		}
		return content;
	}
	
}
