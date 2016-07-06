package com.zjlp.face.web.server.user.shop.bussiness.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.calcu.CalculateUtils;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.LogisticsException;
import com.zjlp.face.web.server.user.shop.bussiness.LogisticsBusiness;
import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateDto;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateItemDto;
import com.zjlp.face.web.server.user.shop.domain.dto.PickUpStoreDto;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDistributionDto;
import com.zjlp.face.web.server.user.shop.domain.vo.DeliveryTemplateVo;
import com.zjlp.face.web.server.user.shop.domain.vo.PickUpStoreVo;
import com.zjlp.face.web.server.user.shop.service.LogisticsService;
import com.zjlp.face.web.server.user.user.domain.dto.VaearTree;
import com.zjlp.face.web.server.user.user.producer.AddressProducer;

@Repository("logisticsBusiness")
public class LogisticsBusinessImpl implements LogisticsBusiness {

	@Autowired
	private LogisticsService logisticsService;
	@Autowired
	private AddressProducer addressProducer;
	
	@Override
	public DeliveryTemplateDto getDeliveryTemplateByShopNo(String shopNo)
			throws LogisticsException {
		try {
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			List<DeliveryTemplateDto> dtoList = logisticsService.findDeliveryTemplateList(shopNo);
			if (null == dtoList || dtoList.isEmpty()) {
				return null;
			}
			return dtoList.get(0);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public DeliveryTemplateVo getDeliveryTemplateVoByShopNo(String shopNo)
			throws LogisticsException {
		try {
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			DeliveryTemplateDto dto = this.getDeliveryTemplateByShopNo(shopNo);
			if (null == dto) {
				return null;
			}
			VaearTree vaearTree = addressProducer.getVaearTree();
			return new DeliveryTemplateVo(dto, vaearTree);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public void addDispatchTemplate(DeliveryTemplateDto templateDto,
			String shopNo) throws LogisticsException {
		try {
			AssertUtil.notNull(templateDto, "Param[templateDto] can not be null.");
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			templateDto.setShopNo(shopNo);
			templateDto.setAssumer(1);
			templateDto.setValuation(1);
			List<DeliveryTemplateItemDto> itemList = templateDto.getItemList();
			if (null != itemList && !itemList.isEmpty()) {
				DeliveryTemplateItemDto dto = null;
				for (int i = itemList.size() - 1; i >= 0; i--) {
					dto = itemList.get(i);
					if (null == dto.getId() && null == dto.getPostageTemplateId() && null == dto.getDestination()
							&& StringUtils.isBlank(dto.getStartPostageYuan())  && null == dto.getStartStandard()
							&& StringUtils.isBlank(dto.getAddPostageYuan()) && null == dto.getAddStandard()) {
						itemList.remove(i);
						continue;
					}
					dto.setStartPostage(CalculateUtils.converYuantoPenny(dto.getStartPostageYuan()));
					dto.setAddPostage(CalculateUtils.converYuantoPenny(dto.getAddPostageYuan()));
				}
			}
			logisticsService.addDelivery(templateDto,
					DeliveryTemplateItemDto.cover(templateDto.getItemList()));
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public void editDispatchTemplate(DeliveryTemplateDto templateDto,
			String shopNo) throws LogisticsException {
		try {
			AssertUtil.notNull(templateDto, "Param[templateDto] can not be null.");
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			List<DeliveryTemplateItemDto> itemList = templateDto.getItemList();
			if (null != itemList && !itemList.isEmpty()) {
				DeliveryTemplateItemDto dto = null;
				for (int i = itemList.size() - 1; i >= 0; i--) {
					dto = itemList.get(i);
					if (null == dto.getId() && null == dto.getPostageTemplateId() && null == dto.getDestination()
							&& StringUtils.isBlank(dto.getStartPostageYuan())  && null == dto.getStartStandard()
							&& StringUtils.isBlank(dto.getAddPostageYuan()) && null == dto.getAddStandard()) {
						itemList.remove(i);
						continue;
					}
					dto.setStartPostage(CalculateUtils.converYuantoPenny(dto.getStartPostageYuan()));
					dto.setAddPostage(CalculateUtils.converYuantoPenny(dto.getAddPostageYuan()));
				}
			}
			logisticsService.editDelivery(templateDto,
					DeliveryTemplateItemDto.cover(templateDto.getItemList()),
					shopNo);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public void delDispatchTemplate(Long id, String shopNo)
			throws LogisticsException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			logisticsService.removeDeliveryTemplate(id, shopNo);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public Pagination<ShopDistributionDto> findShopDistributionPage(
			Pagination<ShopDistributionDto> pagination, String shopNo)
			throws LogisticsException {
		try {
			AssertUtil.notNull(pagination, "Param[pagination] can not be null.");
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			ShopDistributionDto dto = new ShopDistributionDto(shopNo);
			dto.getAide().setStartNum(pagination.getStart());
			dto.getAide().setPageSizeNum(pagination.getPageSize());
			Integer totalRow = logisticsService.getPsCount(dto);
			List<ShopDistributionDto> datas = logisticsService.findPsPage(dto);
			pagination.setTotalRow(totalRow);
			pagination.setDatas(datas);
			return pagination;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public void addOrEditShopDistribution(ShopDistribution shopDistribution,
			String shopNo) throws LogisticsException {
		try {
			AssertUtil.notNull(shopDistribution, "Param[shopDistribution] can not be null.");
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			if (null == shopDistribution.getId()) {
				shopDistribution.setShopNo(shopNo);
				logisticsService.addShopDistribution(shopDistribution);
			} else {
				logisticsService.editShopDistribution(shopDistribution, shopNo);
			}
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public void delShopDistribution(Long id, String shopNo)
			throws LogisticsException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			logisticsService.removeShopDistribution(id, shopNo);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public Pagination<PickUpStoreVo> findPickUpStorePage(
			Pagination<PickUpStoreVo> pagination, String shopNo) throws LogisticsException {
		try {
			AssertUtil.notNull(pagination, "Param[pagination] can not be null.");
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			PickUpStoreDto dto = new PickUpStoreDto(shopNo);
			dto.getAide().setStartNum(pagination.getStart());
			dto.getAide().setPageSizeNum(pagination.getPageSize());
			Integer totalRow = logisticsService.getZtCount(dto);
			List<PickUpStoreDto> lists = logisticsService.findZtPage(dto);
			VaearTree vaearTree = addressProducer.getVaearTree();
			List<PickUpStoreVo> datas = PickUpStoreVo.cover(lists, vaearTree);
			pagination.setTotalRow(totalRow);
			pagination.setDatas(datas);
			return pagination;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public PickUpStore getPickUpStoreById(Long id, String shopNo)
			throws LogisticsException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			return logisticsService.getValidPickUpStoreById(id, shopNo);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public void addOrEditPickUpStore(PickUpStoreDto dto, String shopNo)
			throws LogisticsException {
		try {
			AssertUtil.notNull(dto, "Param[dto] can not be null.");
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			boolean isvalid = addressProducer.isValidAddress(dto.getProvince(), 
					dto.getCity(), dto.getCounty());
			AssertUtil.isTrue(isvalid, "Address[province={}, city={}, country={}] is not valid",
					"", dto.getProvince(), dto.getCity(), dto.getCounty());
			if (null == dto.getId()) {
				dto.setShopNo(shopNo);
				logisticsService.addPickUpStore(dto);
			} else {
				logisticsService.editPickUpStore(dto, shopNo);
			}
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public void delPickUpStore(Long id, String shopNo)
			throws LogisticsException {
		try {
			AssertUtil.notNull(id, "Param[id] can not be null.");
			AssertUtil.notNull(shopNo, "Param[shopNo] can not be null.");
			logisticsService.removePickUpStore(id, shopNo);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public VaearTree getProviceVaear() throws LogisticsException {
		try {
			return addressProducer.getProviceVaear();
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public boolean delDispatchTemplateItem(Long id, String shopNo)
			throws LogisticsException {
		try {
			logisticsService.getValidDeliveryTemplateItem(id, shopNo);
			logisticsService.delDispatchTemplateItem(id);
			return true;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public Integer countShopDistributionByShopNo(String shopNo) {
		return logisticsService.countShopDistributionByShopNo(shopNo);
	}

	@Override
	public Integer countPickUpStoreByShopNo(String shopNo) {
		return logisticsService.countPickUpStoreByShopNo(shopNo);
	}

	@Override
	public List<ShopDistribution> findShopDistributionByShopNo(String shopNo) throws LogisticsException {
		try {
			AssertUtil.hasLength(shopNo, "参数shopNo不能为空");
			return logisticsService.findShopDistributionList(shopNo);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public List<PickUpStore> findPickUpByShopNo(String shopNo) throws LogisticsException {
		try {
			AssertUtil.hasLength(shopNo, "参数shopNo不能为空");
			return logisticsService.findPickUpStoreList(shopNo);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}
	
	
	
}
