package com.zjlp.face.web.component.sms;

import java.util.Date;

import com.zjlp.face.util.date.DateStyle;
import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.web.component.sms.Sms.SwitchType;
import com.zjlp.face.web.constants.Constants;


/**
 * 短信发送
 * 
 * @ClassName: SmsProccessor 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年1月21日 上午9:53:55
 */
public final class SmsProccessor {
	
	private SmsProccessor(){}
	
	/**
	 * 提现完成后短信发送
	 * 
	 * @Title: sendWithdrawMessage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param states
	 * @param phone
	 * @param bankCard
	 * @param bankName
	 * @param price
	 * @date 2015年1月21日 上午9:53:26  
	 * @author lys
	 */
	public static void sendWithdrawMessage(Integer states, String phone, String bankCard, String bankName, String price) {
		
		/**短信提醒*/
		try {
			Date date = new Date();
			if (Constants.WD_STATE_SUCC.equals(states)) {
				//短信提醒
				String dateString = DateUtil.DateToString(date, DateStyle.MM_DD_HH_MM_SS_CN);
				String fourCard = bankCard.substring(bankCard.length() - 4, bankCard.length());
				Sms.getInstance().sendUmsBySwitch(SwitchType.UMS_WITHDRAW_SWITCH, SmsContent.UMS_106, phone, 
						dateString, price+"元", bankName, fourCard);
			} else if (Constants.WD_STATE_FAIL.equals(states)) {
				String dateString = DateUtil.DateToString(date, DateStyle.MM_DD_HH_MM_SS_CN);
				Sms.getInstance().sendUmsBySwitch(SwitchType.UMS_WITHDRAW_SWITCH, SmsContent.UMS_107, phone, 
						dateString, price+"元");
			}
		} catch (Exception e) {
			//忽略
		}
	}
	
}
