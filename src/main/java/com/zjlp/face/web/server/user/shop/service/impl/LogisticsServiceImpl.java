package com.zjlp.face.web.server.user.shop.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.LogisticsException;
import com.zjlp.face.web.server.user.shop.dao.DeliveryTemplateDao;
import com.zjlp.face.web.server.user.shop.dao.DeliveryTemplateItemDao;
import com.zjlp.face.web.server.user.shop.dao.PickUpStoreDao;
import com.zjlp.face.web.server.user.shop.dao.ShopDao;
import com.zjlp.face.web.server.user.shop.dao.ShopDistributionDao;
import com.zjlp.face.web.server.user.shop.domain.DeliveryTemplate;
import com.zjlp.face.web.server.user.shop.domain.DeliveryTemplateItem;
import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateDto;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateItemDto;
import com.zjlp.face.web.server.user.shop.domain.dto.PickUpStoreDto;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDistributionDto;
import com.zjlp.face.web.server.user.shop.domain.vo.ShopVo;
import com.zjlp.face.web.server.user.shop.service.LogisticsService;

@Service("logisticsService")
public class LogisticsServiceImpl implements LogisticsService {

	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private DeliveryTemplateDao deliveryTemplateDao;
	@Autowired
	private DeliveryTemplateItemDao deliveryTemplateItemDao;
	@Autowired
	private PickUpStoreDao pickUpStoreDao;
	@Autowired
	private ShopDistributionDao shopDistributionDao;
	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public Long addDelivery(DeliveryTemplate template,
			List<DeliveryTemplateItem> itemList) throws LogisticsException {
		try {
			AssertUtil.notNull(template, "Param[template] can not be null.");
			AssertUtil.notEmpty(itemList, "Param[itemList] can not be empty.");
			Date date = new Date();
			Long templateId = this.addDeliveryTemplate(template, date);
			this.addDeliveryTemplateItemList(templateId, itemList, date);
			return templateId;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	/**
	 * 快递模板添加
	 * 
	 * @Title: addDeliveryTemplate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param template
	 * @param date
	 * @return
	 * @date 2015年3月27日 上午11:08:17
	 * @author lys
	 */
	@Transactional
	private Long addDeliveryTemplate(DeliveryTemplate template, Date date) {
		AssertUtil.notNull(template.getName(),
				"DeliveryTemplate.name can not be null.");
		AssertUtil.notNull(template.getShopNo(),
				"DeliveryTemplate.name can not be null.");
		template.setCreateTime(date);
		template.setUpdateTime(date);
		Long templateId = deliveryTemplateDao.add(template);
		log.info("ADD DeliveryTemplateItem[id=" + templateId + "]");
		return templateId;
	}

	/**
	 * 快递模板子项添加
	 * 
	 * @Title: addDeliveryTemplateItemList
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param templateId
	 * @param itemList
	 * @param date
	 * @date 2015年3月27日 上午11:08:35
	 * @author lys
	 */
	@Transactional
	private void addDeliveryTemplateItemList(Long templateId,
			List<DeliveryTemplateItem> itemList, Date date) {
		Long id = null;
		StringBuilder sb = new StringBuilder("ADD DeliveryTemplateItemList[");
		for (DeliveryTemplateItem item : itemList) {
			if (null == item)
				continue;
			AssertUtil.notNull(item.getDestination(),
					"TemplateItem.destination can not be null.");
			AssertUtil.notNull(item.getStartStandard(),
					"TemplateItem.startStandard can not be null.");
			AssertUtil.notNull(item.getStartPostage(),
					"TemplateItem.startPostage can not be null.");
			AssertUtil.notNull(item.getAddStandard(),
					"TemplateItem.addStandard can not be null.");
			AssertUtil.notNull(item.getAddPostage(),
					"TemplateItem.addPostage can not be null.");
//			item.setStartPostage(CalculateUtils.converYuantoPenny(item.getStartPostage().toString()));
//			item.setAddPostage(CalculateUtils.converYuantoPenny(item.getAddPostage().toString()));
			item.setPostageTemplateId(templateId);
			item.setCreateTime(date);
			item.setUpdateTime(date);
			id = deliveryTemplateItemDao.add(item);
			sb.append(id).append(", ");
		}
		sb.delete(sb.length() - 1, sb.length()).append("] end.");
		log.info(sb);
	}

	@Override
	public Long addPickUpStore(PickUpStore pickUpStore)
			throws LogisticsException {
		try {
			AssertUtil.notNull(pickUpStore,
					"Param[pickUpStore] can not be null.");
			AssertUtil.notNull(pickUpStore.getShopNo(),
					"Param[pickUpStore.shopNo] can not be null.");
			AssertUtil.notNull(pickUpStore.getName(),
					"Param[pickUpStore.name] can not be null.");
			AssertUtil.notNull(pickUpStore.getProvince(),
					"Param[pickUpStore.province] can not be null.");
			AssertUtil.notNull(pickUpStore.getCity(),
					"Param[pickUpStore.city] can not be null.");
			AssertUtil.notNull(pickUpStore.getCounty(),
					"Param[pickUpStore.county] can not be null.");
			AssertUtil.notNull(pickUpStore.getAddress(),
					"Param[pickUpStore.address] can not be null.");
			AssertUtil.notNull(pickUpStore.getPhone(),
					"Param[pickUpStore.phone] can not be null.");
			Date date = new Date();
			pickUpStore.setCreateTime(date);
			pickUpStore.setUpdateTime(date);
			Long id = pickUpStoreDao.add(pickUpStore);
			return id;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public Long addShopDistribution(ShopDistribution distribution)
			throws LogisticsException {
		try {
			AssertUtil.notNull(distribution,
					"Param[distribution] can not be null.");
			AssertUtil.notNull(distribution.getShopNo(),
					"Param[distribution.shopNo] can not be null.");
			AssertUtil.notNull(distribution.getName(),
					"Param[distribution.name] can not be null.");
			AssertUtil.notNull(distribution.getDistributionRange(),
					"Param[distribution.distributionRange] can not be null.");
			Date date = new Date();
			distribution.setCreateTime(date);
			distribution.setUpdateTime(date);
			Long id = shopDistributionDao.add(distribution);
			return id;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	@Transactional
	public Boolean editDelivery(DeliveryTemplate template,
			List<DeliveryTemplateItem> itemList, String shopNo)
			throws LogisticsException {
		try {
			AssertUtil.notNull(template, "Param[template] can not be null.");
			AssertUtil.notNull(template.getId(),
					"Param[template.id] can not be null.");
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			Date date = new Date();
			this.getValidTemplate(template.getId(), shopNo);
			deliveryTemplateItemDao.remove(template.getId());
			this.addDeliveryTemplateItemList(template.getId(), itemList, date);
			deliveryTemplateDao.editById(template);
			return true;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public Boolean editPickUpStore(PickUpStore pickUpStore, String shopNo)
			throws LogisticsException {
		try {
			AssertUtil.notNull(pickUpStore,
					"Param[pickUpStore] can not be null.");
			AssertUtil.notNull(pickUpStore.getId(),
					"Param[pickUpStore.id] can not be null.");
			this.getValidPickUpStoreById(pickUpStore.getId(), shopNo);
			pickUpStoreDao.editById(pickUpStore);
			return true;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public Boolean editShopDistribution(ShopDistribution distribution,
			String shopNo) throws LogisticsException {
		try {
			AssertUtil.notNull(distribution,
					"Param[distribution] can not be null.");
			AssertUtil.notNull(distribution.getId(),
					"Param[distribution.id] can not be null.");
			this.getValidShopDistributionById(distribution.getId(), shopNo);
			shopDistributionDao.editById(distribution);
			return true;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public List<DeliveryTemplateDto> findDeliveryTemplateList(String shopNo)
			throws LogisticsException {
		try {
			List<DeliveryTemplateDto> dtoList = deliveryTemplateDao
					.findListByShopNo(shopNo);
			if (null == dtoList || dtoList.isEmpty()) {
				return dtoList;
			}
			List<DeliveryTemplateItemDto> itemList = null;
			for (DeliveryTemplateDto dto : dtoList) {
				if (null == dto)
					continue;
				itemList = this.findItemListByTemplateId(dto.getId());
				dto.setItemList(itemList);
			}
			return dtoList;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public List<PickUpStore> findPickUpStoreList(String shopNo)
			throws LogisticsException {
		try {
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			return pickUpStoreDao.findList(shopNo);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public List<ShopDistribution> findShopDistributionList(String shopNo)
			throws LogisticsException {
		try {
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			return shopDistributionDao.findList(shopNo);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	@Transactional
	public Boolean removeDeliveryTemplate(Long id, String shopNo)
			throws LogisticsException {
		try {
			AssertUtil.notNull(id, "param[templateId] can not be null.");
			AssertUtil.hasLength(shopNo, "param[shopNo] can not be null.");
			DeliveryTemplate template = deliveryTemplateDao.getById(id);
			AssertUtil.notNull(template,
					"DeliveryTemplate[id={}] can not be null.", id);
			AssertUtil.isTrue(shopNo.equals(template.getShopNo()),
							"DeliveryTemplate[id={}, shopNo={}] is not belong to shop[no={}]",
							id, template.getShopNo(), shopNo);
			deliveryTemplateItemDao.remove(id);
			deliveryTemplateDao.remove(id);
			log.info("DeliveryTemplate[id=" + id + "] is remove");
			return true;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public Boolean removeShopDistribution(Long id, String shopNo)
			throws LogisticsException {
		try {
			this.getValidShopDistributionById(id, shopNo);
			Shop shop=shopDao.getShopByNo(shopNo);
			ShopVo shopVo=new ShopVo();
			shopVo.setLogisticsTypes(shop.getLogisticsTypes());
			/** 开启店铺配送*/
			if(shopVo.getPsType().equals(ShopVo.PS)){
				ShopDistributionDto dto=new ShopDistributionDto();
				dto.setShopNo(shopNo);
				Integer count=shopDistributionDao.getPsCount(dto);
				AssertUtil.isTrue(count>1, "店铺已开启配送,配送范围至少保留一条","店铺已开启配送,配送范围至少保留一条");
			}
			shopDistributionDao.removeById(id);
			return true;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public Boolean removePickUpStore(Long id, String shopNo)
			throws LogisticsException {
		try {
			this.getValidPickUpStoreById(id, shopNo);
			Shop shop=shopDao.getShopByNo(shopNo);
			ShopVo shopVo=new ShopVo();
			shopVo.setLogisticsTypes(shop.getLogisticsTypes());
			/** 开启自提*/
			if(shopVo.getZtType().equals(ShopVo.ZT)){
				PickUpStoreDto dto=new PickUpStoreDto();
				dto.setShopNo(shopNo);
				Integer count=pickUpStoreDao.getZtCount(dto);
				AssertUtil.isTrue(count>1, "店铺已开启自提,自提点至少保留一条","店铺已开启自提,自提点至少保留一条");
			}
			pickUpStoreDao.removeById(id);
			return true;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public ShopDistribution getValidShopDistributionById(Long id, String shopNo)
			throws LogisticsException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			ShopDistribution distribution = new ShopDistribution(id, shopNo);
			distribution = shopDistributionDao.getByIdAndShopNo(distribution);
			AssertUtil.notNull(distribution,
					"ShopDistribution[id={}, shopNo={}] is not exists.", id,
					shopNo);
			return distribution;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public PickUpStore getValidPickUpStoreById(Long id, String shopNo)
			throws LogisticsException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			PickUpStore store = new PickUpStore(id, shopNo);
			store = pickUpStoreDao.getByIdAndShopNo(store);
			AssertUtil.notNull(store,
					"PickUpStore[id={}, shopNo={}] is not exists.", id, shopNo);
			return store;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public DeliveryTemplateItem getValidDeliveryTemplateItemById(Long id)
			throws LogisticsException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			DeliveryTemplateItem item = deliveryTemplateItemDao.getById(id);
			AssertUtil.notNull(item,
					"DeliveryTemplateItem[id={}] is not exists.");
			return item;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public List<DeliveryTemplateItemDto> findItemListByTemplateId(Long templateId)
			throws LogisticsException {
		try {
			AssertUtil
					.notNull(templateId, "Param[templateId] can not be null.");
			return deliveryTemplateItemDao.findList(templateId);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public DeliveryTemplate getValidTemplate(Long id, String shopNo)
			throws LogisticsException {
		try {
			DeliveryTemplate template = new DeliveryTemplate(id, shopNo);
			template = deliveryTemplateDao.getByIdAndShopNo(template);
			AssertUtil.notNull(template,
					"Param[id={}, shopNo={}] is not exists.", id, shopNo);
			return template;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public Integer getPsCount(ShopDistributionDto dto)
			throws LogisticsException {
		try {
			AssertUtil.notNull(dto, "Param[dto] can not be null.");
			return shopDistributionDao.getPsCount(dto);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public List<ShopDistributionDto> findPsPage(ShopDistributionDto dto)
			throws LogisticsException {
		try {
			AssertUtil.notNull(dto, "Param[dto] can not be null.");
			return shopDistributionDao.findPsPage(dto);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public Integer getZtCount(PickUpStoreDto dto) throws LogisticsException {
		try {
			AssertUtil.notNull(dto, "Param[dto] can not be null.");
			return pickUpStoreDao.getZtCount(dto);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public List<PickUpStoreDto> findZtPage(PickUpStoreDto dto)
			throws LogisticsException {
		try {
			AssertUtil.notNull(dto, "Param[dto] can not be null.");
			return pickUpStoreDao.findZtPage(dto);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public DeliveryTemplateItem getValidDeliveryTemplateItem(Long id,
			String shopNo) throws LogisticsException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			DeliveryTemplateItem item = this.getValidDeliveryTemplateItemById(id);
			this.getValidTemplate(item.getPostageTemplateId(), shopNo);
			return item;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public boolean delDispatchTemplateItem(Long id) throws LogisticsException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			deliveryTemplateItemDao.removeByTemplateId(id);
			return true;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public Integer countShopDistributionByShopNo(String shopNo) {
		ShopDistributionDto dto=new ShopDistributionDto();
		dto.setShopNo(shopNo);
		return shopDistributionDao.getPsCount(dto);
	}

	@Override
	public Integer countPickUpStoreByShopNo(String shopNo) {
		PickUpStoreDto dto=new PickUpStoreDto();
		dto.setShopNo(shopNo);
		return pickUpStoreDao.getZtCount(dto);
	}

}
