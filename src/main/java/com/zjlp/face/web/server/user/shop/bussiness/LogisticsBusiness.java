package com.zjlp.face.web.server.user.shop.bussiness;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.LogisticsException;
import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateDto;
import com.zjlp.face.web.server.user.shop.domain.dto.PickUpStoreDto;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDistributionDto;
import com.zjlp.face.web.server.user.shop.domain.vo.DeliveryTemplateVo;
import com.zjlp.face.web.server.user.shop.domain.vo.PickUpStoreVo;
import com.zjlp.face.web.server.user.user.domain.dto.VaearTree;

/**
 * 店铺配送业务层
 * 
 * @ClassName: LogisticsBusiness
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年3月27日 下午1:26:57
 */
public interface LogisticsBusiness {
	
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
	 * 添加快递模板
	 * 
	 * @Title: addDispatchTemplate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param templateDto
	 * @param shopNo
	 * @throws LogisticsException
	 * @date 2015年3月27日 下午3:55:35
	 * @author lys
	 */
	void addDispatchTemplate(DeliveryTemplateDto templateDto, String shopNo)
			throws LogisticsException;

	/**
	 * 编辑快递模板
	 * 
	 * @Title: editDispatchTemplate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param templateDto
	 * @param shopNo
	 * @throws LogisticsException
	 * @date 2015年3月27日 下午3:55:51
	 * @author lys
	 */
	void editDispatchTemplate(DeliveryTemplateDto templateDto, String shopNo)
			throws LogisticsException;

	/**
	 * 删除快递模板
	 * 
	 * @Title: delDispatchTemplate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 * @param shopNo
	 * @throws LogisticsException
	 * @date 2015年3月27日 下午3:56:03
	 * @author lys
	 */
	void delDispatchTemplate(Long id, String shopNo) throws LogisticsException;

	/**
	 * 店铺配送分页查询
	 * 
	 * @Title: findShopDistributionPage
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param pagination
	 * @param shopNo
	 * @return
	 * @date 2015年3月27日 下午8:11:16
	 * @author lys
	 */
	Pagination<ShopDistributionDto> findShopDistributionPage(
			Pagination<ShopDistributionDto> pagination, String shopNo)
			throws LogisticsException;

	/**
	 * 新增或修改店铺配送信息
	 * 
	 * @Title: addOrEditShopDistribution
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopDistribution
	 * @param shopNo
	 * @throws LogisticsException
	 * @date 2015年3月27日 下午8:31:34
	 * @author lys
	 */
	void addOrEditShopDistribution(ShopDistribution shopDistribution,
			String shopNo) throws LogisticsException;

	/**
	 * 删除店铺配送
	 * 
	 * @Title: delShopDistribution
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 * @param shopNo
	 * @throws LogisticsException
	 * @date 2015年3月27日 下午8:58:11
	 * @author lys
	 */
	void delShopDistribution(Long id, String shopNo) throws LogisticsException;

	/**
	 * 店铺自提点分页查询
	 * 
	 * @Title: findPickUpStorePage
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param pagination
	 * @param shopNo
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月28日 上午9:28:18
	 * @author lys
	 */
	Pagination<PickUpStoreVo> findPickUpStorePage(
			Pagination<PickUpStoreVo> pagination, String shopNo)
			throws LogisticsException;

	/**
	 * 查询自提点
	 * @Title: getPickUpStoreById 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @param shopNo
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月28日 上午10:15:46  
	 * @author lys
	 */
	PickUpStore getPickUpStoreById(Long id, String shopNo) throws LogisticsException;

	/**
	 * 自提点修改
	 * @Title: addOrEditPickUpStore 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param dto
	 * @param shopNo 
	 * @throws LogisticsException
	 * @date 2015年3月28日 上午10:19:52  
	 * @author lys
	 */
	void addOrEditPickUpStore(PickUpStoreDto dto, String shopNo) throws LogisticsException;

	/**
	 * 删除自提点
	 * @Title: delPickUpStore 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @param shopNo
	 * @throws LogisticsException
	 * @date 2015年3月28日 上午10:24:24  
	 * @author lys
	 */
	void delPickUpStore(Long id, String shopNo) throws LogisticsException;
	
	/**
	 * 地址信息查询
	 * @Title: getProviceVaear 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月28日 下午3:35:02  
	 * @author lys
	 */
	VaearTree getProviceVaear() throws LogisticsException;

	/**
	 * 删除快递项
	 * @Title: delDispatchTemplateItem 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @param shopNo
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月30日 上午9:58:01  
	 * @author lys
	 */
	boolean delDispatchTemplateItem(Long id, String shopNo) throws LogisticsException;

	Integer countShopDistributionByShopNo(String shopNo);

	Integer countPickUpStoreByShopNo(String shopNo);
	
	/**
	 * 
	 * @Title: findShopDistributionByShopNo 
	 * @Description: 通过店铺号查询店铺配送
	 * @param shopNo
	 * @return
	 * @date 2015年7月28日 上午10:34:04  
	 * @author cbc
	 */
	List<ShopDistribution> findShopDistributionByShopNo(String shopNo);
	
	/**
	 * 
	 * @Title: findPickUpByShopNo 
	 * @Description: 根据店铺号查询自提点
	 * @param shopNo
	 * @return
	 * @date 2015年7月28日 上午10:46:24  
	 * @author cbc
	 */
	List<PickUpStore> findPickUpByShopNo(String shopNo);

}
