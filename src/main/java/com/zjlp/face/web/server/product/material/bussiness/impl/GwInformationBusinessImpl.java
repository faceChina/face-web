package com.zjlp.face.web.server.product.material.bussiness.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.GwInformationException;
import com.zjlp.face.web.server.product.material.bussiness.GwInformationBusiness;
import com.zjlp.face.web.server.product.material.domain.GwInformation;
import com.zjlp.face.web.server.product.material.service.GwInformationService;

@Service
public class GwInformationBusinessImpl implements GwInformationBusiness{

	Logger _logger = Logger.getLogger(getClass());
	
	@Autowired
	private GwInformationService gwInformationService;
	
	@Override
	public GwInformation getByPrimaryKey(Long id) throws GwInformationException {
		try {
			GwInformation gwInformation = gwInformationService.getGwInformation(id);
			AssertUtil.notNull(gwInformation, "没有该资讯信息");
			return gwInformation;
		} catch (Exception e) {
			_logger.error("查询资讯失败:" + e.getMessage(), e);
			throw new GwInformationException(e.getMessage(),e);
		}
	}

	@Override
	public Pagination<GwInformation> findGwInformationPageList(GwInformation gwInformation, Pagination<GwInformation> pagination) throws GwInformationException {
		try {
			AssertUtil.notNull(gwInformation, "查询条件不能为空");
			AssertUtil.notNull(pagination, "分页对象不能为空");
			return gwInformationService.findGwInformationPageList(gwInformation, pagination);
		} catch (Exception e) {
			_logger.error("分页查询资讯失败:" + e.getMessage(), e);
			throw new GwInformationException(e.getMessage(),e);
		}
	}
}
