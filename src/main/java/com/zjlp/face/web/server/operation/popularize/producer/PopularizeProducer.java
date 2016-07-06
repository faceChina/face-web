package com.zjlp.face.web.server.operation.popularize.producer;

public interface PopularizeProducer {
	
	/**
	 * 查询店铺折扣
	 * @Title: getShopPopularizeRate 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopNo
	 * @param type
	 * @return
	 * @date 2015年5月13日 下午3:17:24  
	 * @author dzq
	 */
	Integer getShopPopularizeRate(String shopNo,Integer type);
	
	
	
	

}
