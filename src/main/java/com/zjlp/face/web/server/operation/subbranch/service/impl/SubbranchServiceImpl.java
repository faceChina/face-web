package com.zjlp.face.web.server.operation.subbranch.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.date.DateUtil;
import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.errorcode.CErrMsg;
import com.zjlp.face.web.server.operation.subbranch.dao.SubbranchDao;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.domain.SubbranchSuit;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchVo;
import com.zjlp.face.web.server.operation.subbranch.service.SubbranchService;
import com.zjlp.face.web.server.trade.order.dao.PurchaseOrderDao;
import com.zjlp.face.web.server.trade.order.domain.vo.SubbranchRankingListVo;
import com.zjlp.face.web.server.trade.order.domain.vo.SubbranchRankingVo;
import com.zjlp.face.web.server.trade.order.domain.vo.SupplierSalesRankingVo;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年6月19日 上午11:11:34
 *
 */
@Service
public class SubbranchServiceImpl implements SubbranchService {

	@Autowired
	private SubbranchDao subbranchDao;
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;

	@Override
	public Long addSubbranch(Subbranch subbranch) {
		return this.subbranchDao.addSubbranch(subbranch);
	}

	@Override
	public Subbranch findByPrimarykey(Long id) {
		return this.subbranchDao.findByPrimarykey(id);
	}

	@Override
	public Pagination<SubbranchVo> findPageSubbrache(SubbranchVo subbranchVo, Pagination<SubbranchVo> Pagination) {
		return this.subbranchDao.findPageSubbrache(subbranchVo, Pagination);
	}

	@Override
	public List<Subbranch> findByUserId(Long id) {
		return this.subbranchDao.findByUserId(id);
	}

	@Override
	public void updateByPrimaryKey(Subbranch subbranch) {
		if (!Subbranch.isValidStatus(subbranch.getStatus())) {
			subbranch.setStatus(null);
		}
		subbranch.setUpdateTime(new Date());
		this.subbranchDao.updateByPrimaryKey(subbranch);
	}

	@Override
	public void removeByPrimarykey(Long id) {
		this.subbranchDao.removeByPrimarykey(id);
	}

	@Override
	public List<SubbranchVo> findMySubbrachs(Long userId, String keyword, Integer role) {
		return this.subbranchDao.findMySubbrachs(userId, keyword, role);
	}

	@Override
	public boolean checkExistAsSub(int type, Long thisUserId, Long preUserId) {
		return this.subbranchDao.checkExistAsSub(type, thisUserId, preUserId);
	}

	@Override
	public Subbranch findByUserCell(String userCell) {
		return subbranchDao.findByUserCell(userCell);
	}
	
	@Override
	public Pagination<Subbranch> findMyHistorySubbrachs(SubbranchVo subbranchVo,Pagination<Subbranch> pagination) {
		return subbranchDao.findMyHistorySubbrachs(subbranchVo,pagination);
	}
	
	@Override
	public boolean unifyStatus(Long subbranchId, Integer status) {
		List<Subbranch> subbranchList = this.findSubbranchList(subbranchId);
		AssertUtil.notEmpty(subbranchList, CErrMsg.NULL_RESULT.getErrCd(), 
				CErrMsg.NULL_RESULT.toString(), "代理关系");
		Subbranch updateSubbranch = new Subbranch(status, new Date());
		for (Subbranch subbranch : subbranchList) {
			updateSubbranch.setId(subbranch.getId());
			subbranchDao.updateByPrimaryKey(updateSubbranch);
		}
		return true;
	}
	
	@Override
	public List<Subbranch> findSubbranchList(Long subbranchId) {
		Subbranch subbranch = this.findByPrimarykey(subbranchId);
		AssertUtil.notNull(subbranch, CErrMsg.NULL_RESULT.getErrCd(), 
				CErrMsg.NULL_RESULT.toString(), "代理关系");
		//组合关系建立
		SubbranchSuit suit = new SubbranchSuit(subbranch);
		suit = this.getSubbranchSuit(suit, subbranch);
		//数据取出
		return suit.coverToList();
	}
	@Override
	public boolean checkExistAsHistorySub(Long userId) {
		return subbranchDao.checkExistAsHistorySub(userId);
	}

	private SubbranchSuit getSubbranchSuit(SubbranchSuit suit, Subbranch subbranch) {
		suit = (SubbranchSuit) suit.addChild(subbranch);
		List<Subbranch> childs = subbranchDao.findListByPid(subbranch.getId());
		if (null != childs && !childs.isEmpty()) {
			for (Subbranch childData : childs) {
				this.getSubbranchSuit(suit, childData);
			}
		}
		return suit;
	}

	@Override
	public Pagination<Subbranch> findHistorySubbranchByUserIdPage(Long userId,
			Pagination<Subbranch> pagination) {
		return subbranchDao.findHistorySubbranchByUserIdPage(userId, pagination);
	}

	@Override
	public Integer countHistorySubbranch(Long userId) {
		return subbranchDao.countHistorySubbranch(userId);
	}

	@Override
	public List<Subbranch> findAllHistorySubbranch(Long userId) {
		return subbranchDao.findAllHistorySubbranch(userId);
	}

	@Override
	public Subbranch findSubbranchByIdWithNoStatus(Long subId) {
		return subbranchDao.findSubbranchByIdWithNoStatus(subId);
	}

	@Override
	public Subbranch findUncompleteSubByUserCell(String userCell) {
		return subbranchDao.findUncompleteSubByUserCell(userCell);
	}

	@Override
	public Subbranch findByPrimarykeyWhenStatusIsNormal(Long subbrachId) {
		return subbranchDao.findByPrimarykeyWhenStatusIsNormal(subbrachId);
	}

	/**
	 * 
	 * @Title: initCountDay 
	 * @Description: 初始化统计天数
	 * @param time
	 * @return
	 * @date 2015年8月13日 下午4:21:13  
	 * @author cbc
	 */
	private Date initCountDay(Integer time) {
		Date now = new Date();
		Date zeroPoint = DateUtil.getZeroPoint(now);
		Date countDay = null;
		if (time == 1) {
			countDay = DateUtil.addDay(zeroPoint, -1);
		} else if (time == 2) {
			countDay = DateUtil.addDay(zeroPoint, -7);
		} else if (time == 3) {
			countDay = DateUtil.addDay(zeroPoint, -30);
		}
		return countDay;
	}
	
	@Override
	public Pagination<SupplierSalesRankingVo> getSupplierSalesRaning(
			Long userId, Integer orderBy, Integer time, Pagination<SupplierSalesRankingVo> pagination) {
		Date countDay = initCountDay(time);
		Date now = new Date();
		Date today = DateUtil.getZeroPoint(now);
		Integer totalRow = subbranchDao.getSupplierSalesRaningCount(userId, countDay, today);
		List<SupplierSalesRankingVo> datas = subbranchDao.getSupplierSalesRaning(userId, orderBy, countDay, today, pagination.getStart(), pagination.getPageSize());
		pagination.setDatas(datas);
		pagination.setTotalRow(totalRow);
		return pagination;
	}
	
	@Override
	public SubbranchRankingVo getSubbranchRaning(Long subbranchId, Integer days){
		SubbranchRankingVo subbranchRankingVo = new SubbranchRankingVo();
		String shopNo = subbranchDao.getShopOnById(subbranchId);
		Long commissionLong = purchaseOrderDao.getSubbranchMyCommission(shopNo, subbranchId, days);
		subbranchRankingVo.setCommissionLong(commissionLong);
		Integer ranking = purchaseOrderDao.getSubbranchMyRaning(shopNo, subbranchId, days);
		subbranchRankingVo.setRanking(ranking);
		List<SubbranchRankingListVo> datas = purchaseOrderDao.getSubbranchRaningList(shopNo, subbranchId, days);
		subbranchRankingVo.setDatas(datas);
		return subbranchRankingVo;
	}

	@Override
	public SupplierSalesRankingVo getSupplierSalesRankingDetail(
			Long subbranchId, Integer time) {
		Date countDay = initCountDay(time);
		Date now = new Date();
		Date today = DateUtil.getZeroPoint(now);
		return subbranchDao.getSupplierSalesRankingDetail(subbranchId, countDay, today);
	}


	@Override
	public Pagination<SubbranchVo> findSubbranchWithNamePhoneDelievery(
			Pagination<SubbranchVo> pagination, SubbranchVo subbranchVo) {
		return subbranchDao.findSubbranchWithNamePhoneDelievery(pagination, subbranchVo);
	}

	@Override
	public SubbranchVo getActiveSubbranchByUserId(Long userId) {
		return subbranchDao.getActiveSubbranchByUserId(userId);
	}
	
	
}
