package com.zjlp.face.web.server.user.shop.producer;

import java.util.List;

import com.zjlp.face.web.exception.ext.LogisticsException;
import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateDto;
import com.zjlp.face.web.server.user.shop.domain.vo.DeliveryTemplateVo;
import com.zjlp.face.web.server.user.shop.domain.vo.PickUpStoreVo;

/**
 * 配送方式对外支撑
 * @ClassName: LogisticsProducer 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年3月30日 下午3:47:01
 */
public interface LogisticsProducer {

	/**
	 * 查询快递信息
	 * 
	 * @Title: getDeliveryTemplateByShopNo
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 下午3:55:20
	 * @author lys
	 */
	DeliveryTemplateDto getDeliveryTemplateByShopNo(String shopNo) throws LogisticsException;

	/**
	 * 查询快递信息
	 * 
	 * @Title: getDeliveryTemplateByShopNo
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 下午3:55:20
	 * @author lys
	 */
	DeliveryTemplateVo getDeliveryTemplateVoByShopNo(String shopNo)
			throws LogisticsException;
	
	/**
	 * 查询店铺自提点
	 * 
	 * @Title: findPickUpStoreList
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 *            店铺编号
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 上午10:32:08
	 * @author lys
	 */
	List<PickUpStore> findPickUpStoreList(String shopNo)
			throws LogisticsException;

	/**
	 * 查询店铺配送
	 * 
	 * @Title: findShopDistributionList
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 *            店铺编号
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 上午10:32:29
	 * @author lys
	 */
	List<ShopDistribution> findShopDistributionList(String shopNo)
			throws LogisticsException;

	PickUpStore getPickUpStoreById(Long pickUpId, String shopNo);

	List<PickUpStoreVo> findPickUpStoreVoList(String shopNo);
}
