package com.zjlp.face.web.server.product.good.factory;

import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;

public interface GoodFactory {
	/**
	 * 创建商品
	 * @Title: addGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodParam 参数对象
	 * @throws Exception
	 * @date 2015年3月11日 下午3:39:04  
	 * @author dzq
	 */
	void addGood(GoodParam goodParam) throws GoodException;
	
	/**
	 * 编辑商品
	 * @Title: editGood 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param GoodParam 参数对象
	 * @throws Exception
	 * @date 2015年3月11日 下午3:40:13  
	 * @author dzq
	 */
	void editGood(GoodParam goodParam) throws GoodException;
	
}
