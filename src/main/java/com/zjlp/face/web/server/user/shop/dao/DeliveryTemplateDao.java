package com.zjlp.face.web.server.user.shop.dao;

import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.DeliveryTemplate;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateDto;

public interface DeliveryTemplateDao {

	Long add(DeliveryTemplate template);

	void remove(Long id);

	DeliveryTemplate getById(Long id);

	List<DeliveryTemplateDto> findListByShopNo(String shopNo);

	DeliveryTemplate getByIdAndShopNo(DeliveryTemplate template);

	void editById(DeliveryTemplate template);

}
