package com.zjlp.face.web.server.operation.subbranch.producer;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.OperateDataException;
import com.zjlp.face.web.exception.ext.SubbranchException;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.trade.order.domain.vo.SubbranchRankingVo;
import com.zjlp.face.web.server.trade.order.domain.vo.SupplierSalesRankingVo;

public interface SubbranchProducer {
	Subbranch findSubbranchById(Long id) throws SubbranchException;
	
	List<Subbranch> findSubbranchList(Long id) throws SubbranchException;

	Subbranch getSubbranchByUserId(Long id);

	Pagination<SupplierSalesRankingVo> getSupplierSalesRaning(Long userId,
			Integer orderBy, Integer time, Pagination<SupplierSalesRankingVo> pagination);

	SupplierSalesRankingVo getSupplierSalesRankingDetail(Long subbranchId,
			Integer time);
	
	SubbranchRankingVo getSubbranchRaning(Long subbranchId, Integer days) throws OperateDataException;
}
