package com.zjlp.face.web.server.product.material.dao;

import java.util.List;
import java.util.Map;

import com.zjlp.face.web.server.product.material.domain.FancyMessage;

public interface FancyMessageDao {
	
	Long save(FancyMessage fancyMessage);
	
	FancyMessage selectByPrimaryKey(Long id);
	
	void update(FancyMessage fancyMessage);

	void deleteById(Long id);

	int getCount(FancyMessage fancyMessage);

	List<FancyMessage> findFancyMessagePageList(Map<String, Object> map);

}
