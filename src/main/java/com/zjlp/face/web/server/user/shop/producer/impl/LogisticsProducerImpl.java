package com.zjlp.face.web.server.user.shop.producer.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.web.exception.ext.LogisticsException;
import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.shop.domain.ShopDistribution;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateDto;
import com.zjlp.face.web.server.user.shop.domain.vo.DeliveryTemplateVo;
import com.zjlp.face.web.server.user.shop.domain.vo.PickUpStoreVo;
import com.zjlp.face.web.server.user.shop.producer.LogisticsProducer;
import com.zjlp.face.web.server.user.shop.service.LogisticsService;
import com.zjlp.face.web.server.user.shop.service.VareaService;
import com.zjlp.face.web.server.user.user.domain.dto.VaearDto;
import com.zjlp.face.web.server.user.user.domain.dto.VaearTree;
import com.zjlp.face.web.server.user.user.producer.AddressProducer;

@Repository("logisticsProducer")
public class LogisticsProducerImpl implements LogisticsProducer {

	@Autowired
	private LogisticsService logisticsService;
	@Autowired
	private AddressProducer addressProducer;
	@Autowired
	private VareaService vareaService;

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
	public List<PickUpStore> findPickUpStoreList(String shopNo)
			throws LogisticsException {
		try {
			return logisticsService.findPickUpStoreList(shopNo);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public List<ShopDistribution> findShopDistributionList(String shopNo)
			throws LogisticsException {
		try {
			return logisticsService.findShopDistributionList(shopNo);
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}

	@Override
	public PickUpStore getPickUpStoreById(Long pickUpId, String shopNo){
		return logisticsService.getValidPickUpStoreById(pickUpId, shopNo);
	}
	
	@Override
	public List<PickUpStoreVo> findPickUpStoreVoList(String shopNo)
			throws LogisticsException {
		try {
			List<PickUpStore> list=logisticsService.findPickUpStoreList(shopNo);
			List<PickUpStoreVo> storeList=new ArrayList<PickUpStoreVo>();
			for(PickUpStore pus:list){
				PickUpStoreVo store=new PickUpStoreVo(pus,null);
				VaearDto varea=vareaService.getVareaById(Integer.valueOf(pus.getCounty()));
				store.setFullAddress(varea.getProvinceName()+varea.getCityName()+varea.getAreaName()+pus.getAddress());
				storeList.add(store);
			}
			return storeList;
		} catch (Exception e) {
			throw new LogisticsException(e);
		}
	}
}
