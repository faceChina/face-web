package com.zjlp.face.web.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchVo;
import com.zjlp.face.web.server.trade.order.domain.vo.SupplierSalesRankingVo;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年6月19日 上午11:04:16
 *
 */
public interface SubbranchMapper {

	int insert(Subbranch subbranch);

	Subbranch selectByPrimaryKey(Long id);

	List<Subbranch> selectByUserId(Long userId);

	int updateByPrimaryKey(Subbranch subbranch);

	int deleteByPrimarykey(Long id);

	Integer getTotalCount(SubbranchVo subbranchVo);

	List<SubbranchVo> findPageSubbrache(Map<String, Object> map);

	List<SubbranchVo> selectMySubbrachs(Map<String, Object> map);

	Integer countExistAsSub(Map<String, Object> map);

	Subbranch findByUserCell(String userCell);
	
	Integer getHistoryTotalCount(SubbranchVo subbranchVo);
	List<Subbranch> findMyHistorySubbrachs(Map<String, Object> map);
	
	Integer countExistAsHistorySub(Map<String, Object> map);

	List<Subbranch> selectListByPid(Long pid);

	Integer countHistorySubbranch(Long userId);

	List<Subbranch> findHistorySubbranchByUserIdPage(Map<String, Object> map);

	List<Subbranch> findAllHistorySubbranch(Long userId);

	Subbranch findSubbranchByIdWithNoStatus(Long subId);

	Subbranch findUncompleteSubByUserCell(String userCell);

	Subbranch findByPrimarykeyWhenStatusIsNormal(Long subbrachId);

	Integer getSupplierSalesRaningCount(@Param("userId")Long userId, @Param("countDay")Date countDay, @Param("today")Date today);

	List<SupplierSalesRankingVo> getSupplierSalesRaning(@Param("userId")Long userId,
			@Param("orderBy")Integer orderBy, @Param("countDay")Date countDay, @Param("today")Date today, @Param("start")Integer start, @Param("pageSize")Integer pageSize);

	SupplierSalesRankingVo getSupplierSalesRankingDetail(@Param("subbranchId")Long subbranchId,
			@Param("countDay")Date countDay, @Param("today")Date today);
	
	String findShopOnById (Long id);
	
	Integer getSubbranchWithNamePhoneDelieveryCount(SubbranchVo subbranchVo);

	List<SubbranchVo> findSubbranchWithNamePhoneDelievery(Map<String, Object> map);

	SubbranchVo getActiveSubbranchByUserId(Long userId);
}
