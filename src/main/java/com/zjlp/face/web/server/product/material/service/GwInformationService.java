package com.zjlp.face.web.server.product.material.service;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.GwInformationException;
import com.zjlp.face.web.server.product.material.domain.GwInformation;


public interface GwInformationService {
	
	/**
	 * 根据ID获取资讯
	 * @param ids
	 * @return
	 * @throws GwInformationException
	 */
	GwInformation getGwInformation(Long id) throws GwInformationException;
	
	/**
	 * 分页查询
	 * @param gwInformation
	 * @param pagination
	 * @return
	 */
	Pagination<GwInformation> findGwInformationPageList(GwInformation gwInformation, Pagination<GwInformation> pagination);
}
