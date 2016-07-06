package com.zjlp.face.web.server.product.good.factory.element;

import com.zjlp.face.web.exception.ext.GoodException;
import com.zjlp.face.web.server.product.good.factory.param.GoodParam;

/**
 * 商品元素工厂接口
 * @ClassName: ElementFactory 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月11日 下午3:04:26 
 * @param <E> 返回值泛型
 * @param <P> 参数泛型
 */
public interface ElementFactory<E> {
	/**
	 * 新增商品元素接口
	 * @Title: create 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodParam
	 * @return
	 * @throws Exception
	 * @date 2015年3月16日 下午6:36:33  
	 * @author dzq
	 */
	E create(GoodParam goodParam) throws GoodException ;
	
	/**
	 * 修改商品元素接口
	 * @Title: edit 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param goodParam
	 * @return
	 * @throws Exception
	 * @date 2015年3月16日 下午6:37:01  
	 * @author Administrator
	 */
	E edit(GoodParam goodParam) throws GoodException ;
	
}
