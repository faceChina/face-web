package com.zjlp.face.web.server.trade.order.bussiness.salesorder.supportprice;

import java.math.BigDecimal;
import java.util.Map;


/**
 * 抵价规则
 * @ClassName: SupportRule 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年9月22日 上午11:25:05
 */
public interface SupportRule {
	
	/**
	 * 设置抵价总价格
	 * @Title: withSupportPrice 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param supportPrice 抵价总价格
	 * @date 2015年9月23日 下午2:27:01  
	 * @author lys
	 */
	void withSupportPrice(Long supportPrice);
	
	/**
	 * 追加键值对
	 * @Title: addPriceById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id 主键
	 * @param price 价格
	 * @date 2015年9月23日 下午2:12:27  
	 * @author lys
	 */
	void addPriceById(Long id, Long price);

	/**
	 * 抵价分配计算支持算法
	 * @Title: excute 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return 分配计算结果
	 * @throws RuntimeException
	 * @date 2015年9月22日 上午11:36:41  
	 * @author lys
	 */
	void excute() throws RuntimeException;
	
	/**
	 * 抵价完成后的结果集合
	 * @Title: getPriceMap 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月23日 下午2:38:19  
	 * @author lys
	 */
	Map<Long, BigDecimal> getPriceMap();
	
	/**
	 * 抵价多少的集合
	 * @Title: getSupportMap 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月23日 下午2:38:50  
	 * @author lys
	 */
	Map<Long, BigDecimal> getSupportMap();
}
