package com.zjlp.face.web.server.operation.subbranch.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.OperateDataException;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchVo;
import com.zjlp.face.web.server.trade.order.domain.vo.SubbranchRankingVo;
import com.zjlp.face.web.server.trade.order.domain.vo.SupplierSalesRankingVo;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年6月19日 上午11:11:31
 *
 */
public interface SubbranchService {

	/**
	 * @Title: addSubbranch
	 * @Description: (插入分店关系)
	 * @param subbranch
	 * @return
	 * @return Long 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月19日 下午2:10:40
	 */
	Long addSubbranch(Subbranch subbranch);

	/**
	 * @Title: findByPrimarykey
	 * @Description: (通过主键查找分店关系)
	 * @param id
	 * @return
	 * @return Subbranch 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月19日 下午2:11:35
	 */
	Subbranch findByPrimarykey(Long id);

	/**
	 * @Title: findByUserId
	 * @Description: (通过用户ID查找分店关系)
	 * @param id
	 * @return
	 * @return List<Subbranch> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月19日 下午2:11:52
	 */
	List<Subbranch> findByUserId(Long id);

	/**
	 * @Title: findPageSubbrache
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param subbranchVo
	 * @param Pagination
	 * @return
	 * @return Pagination<SubbranchVo> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月19日 下午4:23:11
	 */
	Pagination<SubbranchVo> findPageSubbrache(SubbranchVo subbranchVo, Pagination<SubbranchVo> Pagination);

	/**
	 * @Title: updateByPrimaryKey
	 * @Description: (更新分店关系)
	 * @param subbranch
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月19日 下午2:12:10
	 */
	void updateByPrimaryKey(Subbranch subbranch);

	/**
	 * @Title: removeByPrimarykey
	 * @Description: (删除分店关系)
	 * @param id
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月19日 下午2:12:20
	 */
	void removeByPrimarykey(Long id);

	/**
	 * @Title: findMySubbrachs
	 * @Description: (查找我的分店(在我的分店结果中筛选))
	 * @param subbranchVo
	 * @return
	 * @return List<SubbranchVo> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月25日 下午2:34:43
	 */
	List<SubbranchVo> findMySubbrachs(Long userId, String keyword, Integer role);

	/**
	 * @Title: checkExistAsSub
	 * @Description: (检查是否已经是分店)
	 * @param type
	 * @param thisUserId
	 * @param preUserId
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月26日 上午11:47:06
	 */
	boolean checkExistAsSub(int type, Long thisUserId, Long preUserId);

	/**
	 * 
	 * @Title: findByUserCell 
	 * @Description: 通过分店手机号查询分店关系信息
	 * @param userCell
	 * @return
	 * @date 2015年7月6日 下午5:51:32  
	 * @author cbc
	 */
	Subbranch findByUserCell(String userCell);
	
	/**
	 * 查找我的历史分店信息
	* @Title: findMyHistorySubbrachs
	* @Description: 查找我的历史分店信息
	* @param userId
	* @return
	* @return List<Subbranch>    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月13日 上午11:38:42
	 */
	Pagination<Subbranch> findMyHistorySubbrachs(SubbranchVo subbranchVo,Pagination<Subbranch> pagination);
	
	/**
	 * 检查是否有历史分店
	* @Title: checkExistAsHistorySub
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param userId
	* @return
	* @return boolean    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月14日 上午10:45:37
	 */
	boolean checkExistAsHistorySub(Long userId);
	
	/**
	 * 链式修改状态
	 * 
	 * <p>
	 * 
	 * 注：P->bf1->bf2->bf3->...
	 * 
	 * @Title: unifyStatus 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param subbranchId 分店id
	 * @param status 修改的状态
	 * @return
	 * @date 2015年7月9日 下午2:34:56  
	 * @author lys
	 */
	boolean unifyStatus(Long subbranchId, Integer status);
	
	/**
	 * 链式关系查出
	 * 
	 * 注：P->bf1->bf2->bf3->...
	 * 
	 * @Title: findSubbranchList 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param subbranchId 顶级关系id
	 * @return
	 * @date 2015年7月13日 下午2:01:07  
	 * @author lys
	 */
	List<Subbranch> findSubbranchList(Long subbranchId);

	/**
	 * 
	 * @Title: findHistorySubbranchByUserIdPage 
	 * @Description: 分页查询历史分店
	 * @param userId
	 * @param pagination
	 * @return
	 * @date 2015年7月13日 下午2:45:06  
	 * @author cbc
	 */
	Pagination<Subbranch> findHistorySubbranchByUserIdPage(Long userId,
			Pagination<Subbranch> pagination);

	/**
	 * 
	 * @Title: countHistorySubbranch 
	 * @Description: 查询历史分店的数量
	 * @param userId
	 * @return
	 * @date 2015年7月13日 下午3:04:19  
	 * @author cbc
	 */
	Integer countHistorySubbranch(Long userId);

	/**
	 * 
	 * @Title: findAllHistorySubbranch 
	 * @Description: 查询用户的所有历史分店
	 * @param userId
	 * @return
	 * @date 2015年7月13日 下午4:58:34  
	 * @author cbc
	 */
	List<Subbranch> findAllHistorySubbranch(Long userId);

	/**
	 * 
	 * @Title: findSubbranchByIdWithNoStatus 
	 * @Description: 通过ID查询subbranch(不带状态，既历史店铺，非历史店铺都查出来) 
	 * @param subId
	 * @return
	 * @date 2015年7月14日 下午4:56:12  
	 * @author cbc
	 */
	Subbranch findSubbranchByIdWithNoStatus(Long subId);

	/**
	 * @Title: findUncompleteSubByUserCell
	 * @Description: (查找待完善的分店，通过微信分享成为的分店)
	 * @param userCell
	 * @return
	 * @return Subbranch 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年7月16日 下午5:22:37
	 */
	Subbranch findUncompleteSubByUserCell(String userCell);

	/**
	 * 
	 * @Title: findByPrimarykeyWhenStatusIsNormal 
	 * @Description: 通过id查询subbranch,状态为有效
	 * @param subbrachId
	 * @return
	 * @date 2015年7月28日 下午5:31:25  
	 * @author cbc
	 */
	Subbranch findByPrimarykeyWhenStatusIsNormal(Long subbrachId);

	/**
	 * 查询总店销售排行榜<br>
	 * @Title: getSupplierSalesRaning 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @param orderBy 1为成交金额排序， 2为订单数排序
	 * @param time 1为昨日，2为7日，3为30日
	 * @return
	 * @date 2015年8月11日 下午3:04:13  
	 * @author cbc
	 */
	Pagination<SupplierSalesRankingVo> getSupplierSalesRaning(Long userId,
			Integer orderBy, Integer time, Pagination<SupplierSalesRankingVo> pagination);
	
	/**
	 * 分店佣金排行榜<br>
	 * @Title: getSubbranchRaning 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param supplierNo
	 * @param subbranchId
	 * @param days
	 * @return 
	 * @throws OperateDataException
	 * @date  2015年8月11日 下午4:49:42  
	 * @author talo
	 */
	SubbranchRankingVo getSubbranchRaning(Long subbranchId, Integer days);

	/**
	 * 
	 * @Title: getSupplierSalesRankingDetail 
	 * @Description: 查询总店排行榜详情
	 * @param subbranchId
	 * @param time 1为昨日，2为7日，3为30日
	 * @return
	 * @date 2015年8月12日 上午9:36:12  
	 * @author cbc
	 */
	SupplierSalesRankingVo getSupplierSalesRankingDetail(Long subbranchId,
			Integer time);

	/**
	 * 角色：总店<br>
	 * 查询正常状态下的下级分店<br>
	 * 查询关键词：姓名(模糊查询)，电话(模糊查询)，是否有发货权限<br>
	 * @Title: findSubbranchWithNamePhoneDelievery 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param pagination
	 * @param subbranchVo
	 * @return
	 * @date 2015年8月14日 上午11:08:51  
	 * @author cbc
	 */
	Pagination<SubbranchVo> findSubbranchWithNamePhoneDelievery(
			Pagination<SubbranchVo> pagination, SubbranchVo subbranchVo);

	/**
	 * 
	 * @Title: getActiveSubbranchByUserId 
	 * @Description: 查找用户有效状态的分店
	 * @param userId
	 * @return
	 * @date 2015年8月25日 上午10:02:52  
	 * @author cbc
	 */
	SubbranchVo getActiveSubbranchByUserId(Long userId);
}
