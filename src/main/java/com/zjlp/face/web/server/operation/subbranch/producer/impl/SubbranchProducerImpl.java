package com.zjlp.face.web.server.operation.subbranch.producer.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.OperateDataException;
import com.zjlp.face.web.exception.ext.SubbranchException;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.producer.SubbranchProducer;
import com.zjlp.face.web.server.operation.subbranch.service.SubbranchService;
import com.zjlp.face.web.server.trade.order.domain.vo.SubbranchRankingVo;
import com.zjlp.face.web.server.trade.order.domain.vo.SupplierSalesRankingVo;

@Component
public class SubbranchProducerImpl implements SubbranchProducer {
	
	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private SubbranchService subbranchService;
	
	@Override
	public Subbranch findSubbranchById(Long id) {
		return subbranchService.findByPrimarykey(id);
	}

	@Override
	public List<Subbranch> findSubbranchList(Long id) throws SubbranchException {
		try {
			List<Subbranch> subbranchs = new ArrayList<Subbranch>();
			while (true) {
				Subbranch subbranch = this.findSubbranchById(id);
				if (null == subbranch) break;
				subbranchs.add(subbranch);
				id = subbranch.getPid();
			}
			Collections.sort(subbranchs, new Comparator<Subbranch>() {
				@Override
				public int compare(Subbranch o1, Subbranch o2) {
					if (null ==o1 && null == o2) {
						return 0;
					}
					if (null == o1) {
						return 1;
					}
					if (null == o2) {
						return -1;
					}
					return o1.getPid().compareTo(o2.getPid());
				}
			});
			return subbranchs;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new SubbranchException(e);
		}
	}

	@Override
	public Subbranch getSubbranchByUserId(Long id) {
		List<Subbranch> list=subbranchService.findByUserId(id);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Pagination<SupplierSalesRankingVo> getSupplierSalesRaning(
			Long userId, Integer orderBy, Integer time, Pagination<SupplierSalesRankingVo> pagination) {
		return subbranchService.getSupplierSalesRaning(userId, orderBy, time, pagination);
	}

	@Override
	public SupplierSalesRankingVo getSupplierSalesRankingDetail(
			Long subbranchId, Integer time) {
		SupplierSalesRankingVo supplierSalesRankingDetail = subbranchService.getSupplierSalesRankingDetail(subbranchId, time);
		return supplierSalesRankingDetail;
	}
	
	public SubbranchRankingVo getSubbranchRaning(Long subbranchId, Integer days) throws OperateDataException {
		return subbranchService.getSubbranchRaning(subbranchId, days);
	}

}
