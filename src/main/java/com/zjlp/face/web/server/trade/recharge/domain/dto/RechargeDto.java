package com.zjlp.face.web.server.trade.recharge.domain.dto;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.trade.recharge.domain.Recharge;

public class RechargeDto extends Recharge {

	private static final long serialVersionUID = 7038938523782749228L;

	private static final Object[][] validateStatus = { 
		{ Constants.RECHARGE_STATUS_PAYING, new Integer[] { Constants.RECHARGE_STATUS_WAIT } },
		{ Constants.RECHARGE_STATUS_COMPILE, new Integer[] { Constants.RECHARGE_STATUS_PAYING } }
	};
	
	public static boolean validate(Integer newStatus, Integer oldStatus){
		for(Object[] val : validateStatus){
			Integer status = (Integer) val[0];
			if(status.intValue() == newStatus){
				Integer[] arr = (Integer[]) val[1];
				for(Integer states : arr){
					if(states.intValue() == oldStatus){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private static final Object[] validatePayWay = {
		Constants.PAYMENT_BANKCARD, Constants.PAYMENT_WALLET, Constants.PAYMENT_WECHAT, Constants.PAYMENT_MEMBER_CARD
	};
	
	public static boolean validatePayWay(Integer payWay) {
		for(Object val : validatePayWay){
			Integer status = (Integer) val;
			if(status.intValue() == payWay){
				return true;
			}
		}
		return false;
	}
	
}
