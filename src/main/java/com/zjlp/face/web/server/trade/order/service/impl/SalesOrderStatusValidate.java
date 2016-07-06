package com.zjlp.face.web.server.trade.order.service.impl;

import com.zjlp.face.web.constants.Constants;

public class SalesOrderStatusValidate {

	/**
	 * 用于修改状态是验证，第一维为修改后状态，第二位为修改前状态
	 */
	private static final Object[][] validateStatus = { 
		{ Constants.STATUS_PAY, new Integer[] { Constants.STATUS_WAIT } },
		{ Constants.STATUS_SEND, new Integer[] { Constants.STATUS_PAY } },
		{ Constants.STATUS_RECEIVE, new Integer[] { Constants.STATUS_SEND } },
		{ Constants.STATUS_COMPILE, new Integer[] { Constants.STATUS_RECEIVE } },
		{ Constants.STATUS_CANCEL, new Integer[] { Constants.STATUS_WAIT } },
		{ Constants.STATUS_CLOSE, new Integer[] { Constants.STATUS_WAIT } },
		{ Constants.STATUS_DELETE, new Integer[] { Constants.STATUS_CANCEL, Constants.STATUS_CLOSE } } ,
		{ Constants.BOOKORDER_STATUS_CONFIRM, new Integer[] { Constants.BOOKORDER_STATUS_WAIT } },
		{ Constants.BOOKORDER_STATUS_REFUSE, new Integer[] { Constants.BOOKORDER_STATUS_WAIT } },
		{ Constants.BOOKORDER_STATUS_CANCEL, new Integer[] { Constants.BOOKORDER_STATUS_WAIT } }
	};
	
	public boolean validate(Integer newStatus, Integer oldStatus){
		if (newStatus == null) {
			return false;
		}
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
}
