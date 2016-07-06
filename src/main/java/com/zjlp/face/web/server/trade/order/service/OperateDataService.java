package com.zjlp.face.web.server.trade.order.service;

import com.zjlp.face.web.exception.ext.OperateDataException;

public interface OperateDataService {

	/**
	 * 店铺代理冻结金额
	 * @Title: getAencyFreezeAmount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @return
	 * @throws OperateDataException
	 * @date 2015年9月4日 下午6:44:07  
	 * @author lys
	 */
	Long getAencyFreezeAmount(String shopNo) throws OperateDataException;

}
