package com.zjlp.face.web.server.operation.subbranch.dao;

import java.util.Date;
import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchVo;
import com.zjlp.face.web.server.trade.order.domain.vo.SupplierSalesRankingVo;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年6月19日 下午4:24:39
 *
 */
public interface SubbranchDao {

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
	List<Subbranch> findByUserId(Long userId);

	/**
	 * @Title: findPageSubbrache
	 * @Description: (分页查找我的分店)
	 * @param subbranchVo
	 * @param pagination
	 * @return
	 * @return Pagination<SubbranchVo> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月19日 下午4:24:42
	 */
	Pagination<SubbranchVo> findPageSubbrache(SubbranchVo subbranchVo, Pagination<SubbranchVo> pagination);

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
	 * @date 2015年6月26日 上午11:53:19
	 */
	boolean checkExistAsSub(int type, Long thisUserId, Long preUserId);

	/**
	 * 
	 * @Title: findByUserCell 
	 * @Description: 通过分店手机号查询分店关系信息
	 * @param userCell
	 * @return
	 * @date 2015年7月6日 下午5:53:03  
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
	 * 查找是否有历史分店
	* @Title: checkExistAsHistorySub
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param type
	* @param thisUserId
	* @param preUserId
	* @return
	* @return boolean    返回类型
	* @throws
	* @author wxn  
	* @date 2015年7月14日 上午10:24:55
	 */
	boolean checkExistAsHistorySub(Long userId);
	
	/**
	 * 
	 * @Title: findListByPid 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param id
	 * @return
	 * @date 2015年7月13日 下午2:23:59  
	 * @author lys
	 */
	List<Subbranch> findListByPid(Long id);

	/**
	 * 
	 * @Title: findHistorySubbranchByUserIdPage 
	 * @Description: 分页查询历史分店
	 * @param userId
	 * @param pagination
	 * @return
	 * @date 2015年7月13日 下午2:46:02  
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
	 * @date 2015年7月13日 下午3:05:08  
	 * @author cbc
	 */
	Integer countHistorySubbranch(Long userId);

	/**
	 * 
	 * @Title: findAllHistorySubbranch 
	 * @Description: 查询用户的所有历史店铺
	 * @param userId
	 * @return
	 * @date 2015年7月13日 下午4:59:34  
	 * @author cbc
	 */
	List<Subbranch> findAllHistorySubbranch(Long userId);

	/**
	 * 
	 * @Title: findSubbranchByIdWithNoStatus 
	 * @Description: 根据ID查询subbranch，历史店铺，非历史店铺都会查询出来
	 * @param subId
	 * @return
	 * @date 2015年7月14日 下午4:57:36  
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
	 * @Description: 通过ID查询状态为正常的分店 
	 * @param subbrachId
	 * @return
	 * @date 2015年7月28日 下午5:32:53  
	 * @author cbc
	 */
	Subbranch findByPrimarykeyWhenStatusIsNormal(Long subbrachId);

	/**
	 * 
	 * @Title: getSupplierSalesRaningCount 
	 * @Description: 查询总店排行榜总条数
	 * @param userId
	 * @param countDay
	 * @return
	 * @date 2015年8月11日 下午3:31:12  
	 * @author cbc
	 */
	Integer getSupplierSalesRaningCount(Long userId, Date countDay, Date today);

	/**
	 * 
	 * @Title: getSupplierSalesRaning 
	 * @Description: 查询总店排行榜s
	 * @param userId
	 * @param orderBy
	 * @param countDay
	 * @param start
	 * @param pageSize
	 * @return
	 * @date 2015年8月11日 下午3:36:52  
	 * @author cbc
	 */
	List<SupplierSalesRankingVo> getSupplierSalesRaning(Long userId,
			Integer orderBy, Date countDay, Date today, Integer start, Integer pageSize);
	
	/**
	 * @Description:根据分店ID查询主店号
	 * @param id 分店ID
	 * @return 
	 * @author talo
	 */
	String getShopOnById (Long id);

	/**
	 * 
	 * @Title: getSupplierSalesRankingDetail 
	 * @Description: 查询总店排行榜详情
	 * @param subbranchId
	 * @param time
	 * @return
	 * @date 2015年8月12日 上午9:37:16  
	 * @author cbc
	 */
	SupplierSalesRankingVo getSupplierSalesRankingDetail(Long subbranchId,
			Date time ,Date today);
	
	/**
	 * 角色：总店<br>
	 * 查询正常状态下的下级分店<br>
	 * 查询关键词：姓名(模糊查询)，电话(模糊查询)，是否有发货权限<br>
	 * @Title: findSubbranchWithNamePhoneDelievery 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param pagination
	 * @param subbranchVo
	 * @return
	 * @date 2015年8月14日 上午11:10:14  
	 * @author cbc
	 */
	Pagination<SubbranchVo> findSubbranchWithNamePhoneDelievery(
			Pagination<SubbranchVo> pagination, SubbranchVo subbranchVo);

	SubbranchVo getActiveSubbranchByUserId(Long userId);

}
