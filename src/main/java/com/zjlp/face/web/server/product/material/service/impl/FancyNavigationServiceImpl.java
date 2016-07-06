package com.zjlp.face.web.server.product.material.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.product.material.dao.FancyNavigationDao;
import com.zjlp.face.web.server.product.material.dao.FancyNavigationItemDao;
import com.zjlp.face.web.server.product.material.domain.FancyNavigation;
import com.zjlp.face.web.server.product.material.domain.dto.FancyNavigationDto;
import com.zjlp.face.web.server.product.material.service.FancyNavigationService;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月24日 下午5:14:13
 *
 */
@Service("fancyNavigationService")
public class FancyNavigationServiceImpl implements FancyNavigationService {

	@Autowired
	private FancyNavigationDao fancyNavigationDao;

	@Autowired
	private FancyNavigationItemDao fancyNavigationItemDao;

	@Override
	public Long add(FancyNavigation fancyNavigation) {
		return this.fancyNavigationDao.add(fancyNavigation);
	}

	@Override
	public FancyNavigation getById(Long id) {
		return this.fancyNavigationDao.getById(id);
	}

	@Override
	public void updateById(FancyNavigation fancyNavigation) {
		this.fancyNavigationDao.updateById(fancyNavigation);

	}

	@Override
	public void deleteById(Long id) {
		this.fancyNavigationDao.deleteById(id);
	}

	@Override
	public List<FancyNavigationDto> findAllNavigation() {
		List<FancyNavigationDto> list = this.fancyNavigationDao.findAllNavigation();
		for (FancyNavigationDto dto : list) {
			dto.setSubItems(this.fancyNavigationItemDao.selectItemByNavigationId(dto.getId()));
		}
		return list;
	}

}
