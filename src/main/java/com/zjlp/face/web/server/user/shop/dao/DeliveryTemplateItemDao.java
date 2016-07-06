package com.zjlp.face.web.server.user.shop.dao;

import java.util.List;

import com.zjlp.face.web.server.user.shop.domain.DeliveryTemplateItem;
import com.zjlp.face.web.server.user.shop.domain.dto.DeliveryTemplateItemDto;

public interface DeliveryTemplateItemDao {

	Long add(DeliveryTemplateItem item);

	List<DeliveryTemplateItemDto> findList(Long templateId);

	void remove(Long templateId);

	void removeByTemplateId(Long id);

	DeliveryTemplateItem getById(Long id);

}
