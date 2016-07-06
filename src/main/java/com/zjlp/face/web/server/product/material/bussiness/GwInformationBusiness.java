package com.zjlp.face.web.server.product.material.bussiness;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.product.material.domain.GwInformation;

public interface GwInformationBusiness {

	GwInformation getByPrimaryKey(Long id);
	
	Pagination<GwInformation> findGwInformationPageList(GwInformation gwInformation, Pagination<GwInformation> pagination);
}
