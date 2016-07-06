package com.zjlp.face.web.server.operation.subbranch.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.mapper.SubbranchMapper;
import com.zjlp.face.web.server.operation.subbranch.dao.SubbranchDao;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchVo;
import com.zjlp.face.web.server.trade.order.domain.vo.SupplierSalesRankingVo;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年6月19日 上午11:09:31
 *
 */
@Repository("SubbranchDao")
public class SubbranchDaoImpl implements SubbranchDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long addSubbranch(Subbranch subbranch) {
		this.sqlSession.getMapper(SubbranchMapper.class).insert(subbranch);
		return subbranch.getId();
	}

	@Override
	public Subbranch findByPrimarykey(Long id) {
		return this.sqlSession.getMapper(SubbranchMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public List<Subbranch> findByUserId(Long userId) {
		return this.sqlSession.getMapper(SubbranchMapper.class).selectByUserId(userId);
	}

	@Override
	public Pagination<SubbranchVo> findPageSubbrache(SubbranchVo subbranchVo, Pagination<SubbranchVo> pagination) {
		Integer count = this.sqlSession.getMapper(SubbranchMapper.class).getTotalCount(subbranchVo);
		pagination.setTotalRow(count);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subbranchVo", subbranchVo);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		pagination.setDatas(this.sqlSession.getMapper(SubbranchMapper.class).findPageSubbrache(map));
		return pagination;
	}

	@Override
	public void updateByPrimaryKey(Subbranch subbranch) {
		this.sqlSession.getMapper(SubbranchMapper.class).updateByPrimaryKey(subbranch);
	}

	@Override
	public void removeByPrimarykey(Long id) {
		this.sqlSession.getMapper(SubbranchMapper.class).deleteByPrimarykey(id);
	}

	@Override
	public List<SubbranchVo> findMySubbrachs(Long userId, String keyword, Integer role) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("superiorUserId", userId);
		map.put("keyword", "%" + keyword + "%");
		map.put("role", role);
		return this.sqlSession.getMapper(SubbranchMapper.class).selectMySubbrachs(map);
	}

	@Override
	public boolean checkExistAsSub(int type, Long thisUserId, Long preUserId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("userId", thisUserId);
		map.put("superiorUserId", preUserId);
		Integer total = this.sqlSession.getMapper(SubbranchMapper.class).countExistAsSub(map);
		return total > 0 ? true : false;
	}

	@Override
	public Subbranch findByUserCell(String userCell) {
		return sqlSession.getMapper(SubbranchMapper.class).findByUserCell(userCell);
	}
	
	@Override
	public Pagination<Subbranch> findMyHistorySubbrachs(SubbranchVo subbranchVo,Pagination<Subbranch> pagination){
		
		Integer count = this.sqlSession.getMapper(SubbranchMapper.class).getHistoryTotalCount(subbranchVo);
		pagination.setTotalRow(count);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("subbranchVo", subbranchVo);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		pagination.setDatas(this.sqlSession.getMapper(SubbranchMapper.class).findMyHistorySubbrachs(map));
		return pagination;
		
	}
	
	@Override
	public List<Subbranch> findListByPid(Long pid) {
		return sqlSession.getMapper(SubbranchMapper.class).selectListByPid(pid);
	}
	@Override
	public boolean checkExistAsHistorySub(Long userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		Integer total = this.sqlSession.getMapper(SubbranchMapper.class).countExistAsHistorySub(map);
		return total > 0 ? true : false;
	}

	@Override
	public Pagination<Subbranch> findHistorySubbranchByUserIdPage(Long userId,
			Pagination<Subbranch> pagination) {
		Integer count = sqlSession.getMapper(SubbranchMapper.class).countHistorySubbranch(userId);
		pagination.setTotalRow(count);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		pagination.setDatas(sqlSession.getMapper(SubbranchMapper.class).findHistorySubbranchByUserIdPage(map));
		return pagination;
	}

	@Override
	public Integer countHistorySubbranch(Long userId) {
		return sqlSession.getMapper(SubbranchMapper.class).countHistorySubbranch(userId);
	}

	@Override
	public List<Subbranch> findAllHistorySubbranch(Long userId) {
		return sqlSession.getMapper(SubbranchMapper.class).findAllHistorySubbranch(userId);
	}

	@Override
	public Subbranch findSubbranchByIdWithNoStatus(Long subId) {
		return sqlSession.getMapper(SubbranchMapper.class).findSubbranchByIdWithNoStatus(subId);
	}

	@Override
	public Subbranch findUncompleteSubByUserCell(String userCell) {
		return sqlSession.getMapper(SubbranchMapper.class).findUncompleteSubByUserCell(userCell);
	}

	@Override
	public Subbranch findByPrimarykeyWhenStatusIsNormal(Long subbrachId) {
		return sqlSession.getMapper(SubbranchMapper.class).findByPrimarykeyWhenStatusIsNormal(subbrachId);
	}

	@Override
	public Integer getSupplierSalesRaningCount(Long userId, Date countDay, Date today) {
		return sqlSession.getMapper(SubbranchMapper.class).getSupplierSalesRaningCount(userId, countDay, today);
	}

	@Override
	public List<SupplierSalesRankingVo> getSupplierSalesRaning(Long userId,
			Integer orderBy, Date countDay, Date today, Integer start, Integer pageSize) {
		return sqlSession.getMapper(SubbranchMapper.class).getSupplierSalesRaning(userId, orderBy, countDay, today,  start, pageSize);
	}
	@Override
	public SupplierSalesRankingVo getSupplierSalesRankingDetail(
			Long subbranchId, Date time, Date today) {
		return sqlSession.getMapper(SubbranchMapper.class).getSupplierSalesRankingDetail(subbranchId, time, today);
	}

	
	@Override
	public String getShopOnById (Long id) {
		return sqlSession.getMapper(SubbranchMapper.class).findShopOnById(id);
	}
	
	@Override
	public Pagination<SubbranchVo> findSubbranchWithNamePhoneDelievery(
			Pagination<SubbranchVo> pagination, SubbranchVo subbranchVo) {
		Integer totalRow = sqlSession.getMapper(SubbranchMapper.class).getSubbranchWithNamePhoneDelieveryCount(subbranchVo);
		pagination.setTotalRow(totalRow);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		map.put("subbranchVo", subbranchVo);
		List<SubbranchVo> datas =sqlSession.getMapper(SubbranchMapper.class).findSubbranchWithNamePhoneDelievery(map);
		pagination.setDatas(datas);
		return pagination;
	}

	@Override
	public SubbranchVo getActiveSubbranchByUserId(Long userId) {
		return sqlSession.getMapper(SubbranchMapper.class).getActiveSubbranchByUserId(userId);
	}
	
	
}
