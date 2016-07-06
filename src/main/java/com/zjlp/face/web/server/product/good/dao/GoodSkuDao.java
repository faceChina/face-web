package com.zjlp.face.web.server.product.good.dao;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.GoodSku;
import com.zjlp.face.web.server.product.good.domain.vo.GoodSkuVo;
/**
 * 商品SKU持久层接口
 * @ClassName: GoodSkuDao 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author Administrator
 * @date 2015年3月11日 下午1:49:40
 */
public interface GoodSkuDao {
	
	void add(GoodSku goodSku);
	
	void edit(GoodSku goodSku);
	
	GoodSku getById(Long id);
	
	void delete(Long id);
	
	List<GoodSku> findGoodSkusByGoodId(Long goodId);

	GoodSku selectGoodskuByGoodIdAndPrprerty(Long goodId, String skuProperties);

	void removeByGoodId(Long goodId);
	
	void removeIdById(Long id);

	List<GoodSku> selectGoodskuByGoodIdAndPrprerty(GoodSku goodSku);

	List<GoodSku> findAllGoodSkuByGoodId(Long goodId);

	List<GoodSkuVo> findGoodSkuByAppointmentIdAndShopTypeId(Long appointmentId, Long shopTypeId);

	void editGoodSku(GoodSku editSku);
}
