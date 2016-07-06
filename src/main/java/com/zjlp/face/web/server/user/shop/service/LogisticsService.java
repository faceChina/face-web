package com.zjlp.face.web.server.user.shop.service;

import java.util.List;

import com.zjlp.face.web.exception.ext.LogisticsException;
import com.zjlp.face.web.server.user.shop.domain.DeliveryTemplate;
import com.zjlp.face.web.server.user.shop.domain.DeliveryTemplateItem;
import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateDto;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateItemDto;
import com.zjlp.face.web.server.user.shop.domain.dto.PickUpStoreDto;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDistributionDto;

/**
 * 店铺配送基础服务
 * 
 * @ClassName: LogisticsService
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015年3月27日 上午10:14:18
 */
public interface LogisticsService {

	/**
	 * 增加快递模板
	 * 
	 * @Title: addDelivery
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param template
	 *            快递模板
	 * @param itemList
	 *            模板子项
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 上午10:19:42
	 * @author lys
	 */
	Long addDelivery(DeliveryTemplate template,
			List<DeliveryTemplateItem> itemList) throws LogisticsException;

	/**
	 * 增加门店自提信息
	 * 
	 * @Title: addPickUpStore
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param pickUpStore
	 *            门店自提点
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 上午10:20:56
	 * @author lys
	 */
	Long addPickUpStore(PickUpStore pickUpStore) throws LogisticsException;

	/**
	 * 增加店铺配送范围
	 * 
	 * @Title: addShopDistribution
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param distribution
	 *            店铺配送范围
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 上午10:21:59
	 * @author lys
	 */
	Long addShopDistribution(ShopDistribution distribution)
			throws LogisticsException;

	/**
	 * 修改快递信息
	 * 
	 * @Title: editDelivery
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param template
	 *            模板
	 * @param itemList
	 *            子项
	 * @param shopNo
	 *            店铺编号（数据权限验证）
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 上午10:30:22
	 * @author lys
	 */
	Boolean editDelivery(DeliveryTemplate template,
			List<DeliveryTemplateItem> itemList, String shopNo)
			throws LogisticsException;

	/**
	 * 修改自提点
	 * 
	 * @Title: editPickUpStore
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param pickUpStore
	 *            自提点
	 * @param shopNo
	 *            店铺编号（数据权限验证）
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 上午10:30:51
	 * @author lys
	 */
	Boolean editPickUpStore(PickUpStore pickUpStore, String shopNo)
			throws LogisticsException;

	/**
	 * 修改店铺配送
	 * 
	 * @Title: editShopDistribution
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param distribution
	 *            店铺配送
	 * @param shopNo
	 *            店铺编号（数据权限验证）
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 上午10:31:17
	 * @author lys
	 */
	Boolean editShopDistribution(ShopDistribution distribution, String shopNo)
			throws LogisticsException;

	/**
	 * 查询快递信息
	 * 
	 * @Title: findDeliveryTemplateList
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shopNo
	 *            店铺编号
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 上午10:31:46
	 * @author lys
	 */
	List<DeliveryTemplateDto> findDeliveryTemplateList(String shopNo)
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

	/**
	 * 删除快递模板
	 * 
	 * @Title: removeDeliveryTemplate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            模板id
	 * @param shopNo
	 *            店铺编号
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 上午10:44:47
	 * @author lys
	 */
	Boolean removeDeliveryTemplate(Long id, String shopNo)
			throws LogisticsException;

	/**
	 * 删除店铺配送
	 * 
	 * @Title: removeShopDistribution
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            模板id
	 * @param shopNo
	 *            店铺编号
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 上午10:45:37
	 * @author lys
	 */
	Boolean removeShopDistribution(Long id, String shopNo)
			throws LogisticsException;

	/**
	 * 删除店铺自提
	 * 
	 * @Title: removePickUpStore
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 * @param shopNo
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 上午10:46:23
	 * @author lys
	 */
	Boolean removePickUpStore(Long id, String shopNo) throws LogisticsException;

	/**
	 * 有效店铺配送信息获取
	 * 
	 * @Title: getValidShopDistributionById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            主键
	 * @param shopNo
	 *            店铺编号
	 * @return
	 * @throws LogisticsException
	 *             为空抛出异常
	 * @date 2015年3月27日 上午11:32:39
	 * @author lys
	 */
	ShopDistribution getValidShopDistributionById(Long id, String shopNo)
			throws LogisticsException;

	/**
	 * 有效自提点获取
	 * 
	 * @Title: getValidPickUpStoreById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            主键
	 * @param shopNo
	 *            店铺编号
	 * @return
	 * @throws LogisticsException
	 *             为空抛出异常
	 * @date 2015年3月27日 上午11:33:17
	 * @author lys
	 */
	PickUpStore getValidPickUpStoreById(Long id, String shopNo)
			throws LogisticsException;

	/**
	 * 根据主键查询快递子项
	 * 
	 * @Title: getDeliveryTemplateItemById
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            主键
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 上午11:51:33
	 * @author lys
	 */
	DeliveryTemplateItem getValidDeliveryTemplateItemById(Long id)
			throws LogisticsException;

	/**
	 * 根据快递模板id查询快递子项列表
	 * 
	 * @Title: findItemListByTemplateId
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param templateId
	 *            模板id
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 上午11:51:56
	 * @author lys
	 */
	List<DeliveryTemplateItemDto> findItemListByTemplateId(Long templateId)
			throws LogisticsException;

	/**
	 * 查询有效的快递模板
	 * 
	 * @Title: getValidTemplate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param id
	 *            主键
	 * @param shopNo
	 *            店铺编号
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 下午12:48:49
	 * @author lys
	 */
	DeliveryTemplate getValidTemplate(Long id, String shopNo)
			throws LogisticsException;

	/**
	 * 店铺配送个数查询
	 * @Title: getPsCount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param dto
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 下午8:09:56  
	 * @author lys
	 */
	Integer getPsCount(ShopDistributionDto dto) throws LogisticsException;

	/**
	 * 店铺配送记录分页查询
	 * @Title: findPsPage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param dto
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月27日 下午8:10:18  
	 * @author lys
	 */
	List<ShopDistributionDto> findPsPage(ShopDistributionDto dto) throws LogisticsException;

	/**
	 * 自提点个数查询
	 * @Title: getZtCount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param dto
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月28日 上午9:48:06  
	 * @author lys
	 */
	Integer getZtCount(PickUpStoreDto dto) throws LogisticsException;

	/**
	 * 自提点分页查询
	 * @Title: findZtPage 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param dto
	 * @return
	 * @throws LogisticsException
	 * @date 2015年3月28日 上午9:48:20  
	 * @author lys
	 */
	List<PickUpStoreDto> findZtPage(PickUpStoreDto dto) throws LogisticsException;

	/**
	 * 获取有效快递子项
	 * @Title: getValidDeliveryTemplateItem 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @param shopNo
	 * @throws LogisticsException
	 * @date 2015年3月30日 上午10:04:19  
	 * @author lys
	 * @return 
	 */
	DeliveryTemplateItem getValidDeliveryTemplateItem(Long id, String shopNo) throws LogisticsException;

	/**
	 * 删除快递子项
	 * @Title: delDispatchTemplateItem 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @throws LogisticsException
	 * @date 2015年3月30日 上午10:09:07  
	 * @author lys
	 */
	boolean delDispatchTemplateItem(Long id) throws LogisticsException;

	Integer countShopDistributionByShopNo(String shopNo);

	Integer countPickUpStoreByShopNo(String shopNo);
}
