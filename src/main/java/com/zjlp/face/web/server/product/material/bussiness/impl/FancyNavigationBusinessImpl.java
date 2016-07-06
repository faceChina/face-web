package com.zjlp.face.web.server.product.material.bussiness.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.exception.ext.FancyNavigationException;
import com.zjlp.face.web.server.product.material.bussiness.FancyNavigationBusiness;
import com.zjlp.face.web.server.product.material.domain.dto.FancyNavigationDto;
import com.zjlp.face.web.server.product.material.service.FancyNavigationItemService;
import com.zjlp.face.web.server.product.material.service.FancyNavigationService;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年8月24日 下午5:42:27
 *
 */
@Service("fancyNavigationBusiness")
public class FancyNavigationBusinessImpl implements FancyNavigationBusiness {

	private Logger _log = Logger.getLogger(getClass());

	@Autowired
	private FancyNavigationService fancyNavigationService;

	@Autowired
	private FancyNavigationItemService fancyNavigationItemService;

	@Override
	public List<FancyNavigationDto> findAllNavigation() throws FancyNavigationException {
		try {
			return this.fancyNavigationService.findAllNavigation();
		} catch (Exception e) {
			_log.error("查找刷脸精选及细项失败", e);
			throw new FancyNavigationException(e);
		}
	}

}
