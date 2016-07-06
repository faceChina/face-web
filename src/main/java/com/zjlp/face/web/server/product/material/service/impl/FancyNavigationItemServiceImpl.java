package com.zjlp.face.web.server.product.material.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.server.product.material.dao.FancyNavigationItemDao;
import com.zjlp.face.web.server.product.material.domain.FancyNavigationItem;
import com.zjlp.face.web.server.product.material.service.FancyNavigationItemService;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月24日 下午5:28:26
 *
 */
@Service("fancyNavigationItemService")
public class FancyNavigationItemServiceImpl implements FancyNavigationItemService {

	@Autowired
	private FancyNavigationItemDao fancyNavigationItemDao;

	@Override
	public Long add(FancyNavigationItem fancyNavigationItem) {
		return this.fancyNavigationItemDao.add(fancyNavigationItem);
	}

	@Override
	public FancyNavigationItem getById(Long id) {
		return this.fancyNavigationItemDao.getById(id);
	}

	@Override
	public void updateById(FancyNavigationItem fancyNavigationItem) {
		this.fancyNavigationItemDao.updateById(fancyNavigationItem);
	}

	@Override
	public void deleteById(Long id) {
		this.fancyNavigationItemDao.deleteById(id);
	}

	@Override
	public List<FancyNavigationItem> findItemByNavigationId(Long navigationId) {
		return this.fancyNavigationItemDao.selectItemByNavigationId(navigationId);
	}

}
