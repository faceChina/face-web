package com.zjlp.face.web.server.operation.subbranch.business;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.SubbranchException;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.domain.vo.SubbranchVo;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopSubbranchDto;

/**
 * 
 * @author Baojiang Yang
 * @date 2015年6月19日 上午11:12:07
 *
 */
public interface SubbranchBusiness {
	/**
	 * @Title: findSubbranchById
	 * @Description: (通过主键查找分店)
	 * @param id
	 * @return
	 * @return Subbranch 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月19日 下午3:49:18
	 */
	Subbranch findSubbranchById(Long id) throws SubbranchException;

	/**
	 * @Title: findSubbranchByUserIdE
	 * @Description: (通过用户ID查找分店)
	 * @param userId
	 * @return
	 * @return List<Subbranch> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月19日 下午3:54:41
	 */
	List<Subbranch> findSubbranchByUserId(Long userId) throws SubbranchException;

	/**
	 * @Title: findPageSubbrache
	 * @Description: (分页查找我的分店)
	 * @param subbranchVo
	 * @param pagination
	 * @return
	 * @throws SubbranchException
	 * @return Pagination<SubbranchVo> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月19日 下午4:15:23
	 */
	Pagination<SubbranchVo> findPageSubbrach(SubbranchVo subbranchVo, Pagination<SubbranchVo> pagination) throws SubbranchException;

	/**
	 * @Title: searchShop
	 * @Description: (根据手机号查找官网和分店)
	 * @param phoneNo
	 * @param userId 当前操作人的userId
	 * @return
	 * @throws SubbranchException
	 * @return List<Shop> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月22日 下午2:11:41
	 */
	List<ShopSubbranchDto> searchShopByPhoneNo(String phoneNo, Long userId) throws SubbranchException;

	/**
	 * @Title: searchShopByUserId
	 * @Description: (根据用户ID查找官网和分店)
	 * @param userId
	 * @return
	 * @throws SubbranchException
	 * @return List<Shop> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月23日 上午9:43:44
	 */
	List<ShopSubbranchDto> searchShopByUserId(Long userId) throws SubbranchException;

	/**
	 * @Title: applyAsSubByPhone
	 * @Description: (申请成为分店（根据电话号码）)
	 * @param userId
	 * @param searchPhoneNo
	 * @param role
	 * @param submitNickName
	 * @param submitPhoneNo
	 * @return
	 * @throws SubbranchException
	 * @return Long 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月23日 上午9:02:09
	 */
	@Deprecated
	Long applyAsSubByPhone(Long userId, String searchPhoneNo, Integer role, String submitNickName, String submitPhoneNo) throws SubbranchException;

	/**
	 * @Title: applyAsSubById
	 * @Description: (申请成为分店（根据总店/分店ID）)
	 * @param userId
	 * @param shopId
	 * @param subId
	 * @param submitNickName
	 * @param submitPhoneNo
	 * @return
	 * @throws SubbranchException
	 * @return Long 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月24日 上午11:43:55
	 */
	Long applyAsSubById(Long userId, String shopId, Long subId, String submitNickName, String submitPhoneNo) throws SubbranchException;
	/**
	 * @Title: freezeSubbranch
	 * @Description: (冻结或者解冻分店)
	 * @param subbranchId
	 * @param status
	 * @throws SubbranchException
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月22日 上午10:12:46
	 */
	void freezeSubbranch(Long subbranchId, Integer status) throws SubbranchException;

	/**
	 * @Title: authorizedDeliver
	 * @Description: (授权发货，授权或者不授权)
	 * @param subbranchId
	 * @param deliver
	 * @throws SubbranchException
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月22日 上午10:19:57
	 */
	void authorizedDeliver(Long subbranchId, Integer deliver) throws SubbranchException;

	/**
	 * @Title: setSubbranchProfit
	 * @Description: (设置分店佣金比例)
	 * @param subbranch
	 * @param profit
	 * @throws SubbranchException
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月22日 上午10:14:36
	 */
	void setSubbranchProfit(Long subbranch, Integer profit) throws SubbranchException;

	/**
	 * @Title: updateSubbranchName
	 * @Description: (更改店铺名称)
	 * @param subbranchId
	 * @param shopName
	 * @throws SubbranchException
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月22日 上午10:52:32
	 */
	void updateSubbranchName(Long subbranchId, String shopName) throws SubbranchException;

	/**
	 * @Title: updateSubbranchInfo
	 * @Description: (更新分店信息)
	 * @param subbranch
	 * @throws SubbranchException
	 * @return void 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年6月23日 下午3:41:22
	 */
	Long updateSubbranchInfo(Subbranch subbranch) throws SubbranchException;

	/**
	 * @Title: findMySubbrachs
	 * @Description: (根据分店名，姓名或者分店账号查找我的分店)
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
	 * @date 2015年6月26日 上午11:44:00
	 */
	boolean checkExistAsSub(int type, Long thisUserId, Long preUserId);
	
	/**
	 * 
	 * @Title: findByUserCell 
	 * @Description: 通过分店手机号查询分店关系
	 * @param userCell
	 * @return
	 * @date 2015年7月6日 下午5:50:06  
	 * @author cbc
	 */
	Subbranch findByUserCell(String userCell);
	
	/**
	 * 供货商解绑（分店的手机号）
	 * 
	 * <p>
	 * 
	 * 注：1.如果该关系不存在，发生异常，如果已解绑，返回false，解绑成功，返回true<br>
	 *    如果分店有链条关系，则依次解除<br>
	 * 
	 * @Title: unBindSubbranch
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            供货商id
	 * @param cell
	 *            分店登陆账号
	 * @return
	 * @throws SubbranchException
	 *             以下发生异常： 该分店与供货商未建立任何关系
	 * @date 2015年7月9日 上午9:35:02
	 * @author lys
	 */
	boolean unBindSubbranch(Long userId, String cell) throws SubbranchException;
	
	/**
	 * 供货商解绑
	 * 
	 * <p>
	 * 
	 * 注：1.如果该关系不存在，发生异常，如果已解绑，返回false，解绑成功，返回true<br>
	 *    如果分店有链条关系，则依次解除<br>
	 * 
	 * @Title: unBind
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 *            供货商id
	 * @param subbranchId
	 *            关系id
	 * @return
	 * @throws SubbranchException
	 *             以下发生异常： 该分店与供货商未建立任何关系
	 * @date 2015年7月9日 上午9:35:02
	 * @author lys
	 */
	boolean unBindSubbranch(Long userId, Long subbranchId) throws SubbranchException;
	
	
	/**
	 * 
	 * @Title: findHistorySubbranchByUserIdPage 
	 * @Description: 分页查询历史分店
	 * @param userId
	 * @param pagination
	 * @return
	 * @throws SubbranchException
	 * @date 2015年7月13日 下午2:37:19  
	 * @author cbc
	 */
	Pagination<Subbranch> findHistorySubbranchByUserIdPage(Long userId, Pagination<Subbranch> pagination) throws SubbranchException;
	
	/**
	 * 
	 * @Title: isExistHistorySubbranch 
	 * @Description: 是否存在历史分店
	 * @param userId
	 * @return
	 * @date 2015年7月13日 下午3:01:05  
	 * @author cbc
	 */
	boolean isExistHistorySubbranch(Long userId) throws SubbranchException;
	/**
	 * @Description: 分享建立分销关系
	 * @param shopId
	 * @param subId
	 * @param submitNickName
	 * @param submitPhoneNo
	 * @return
	 * @throws SubbranchException
	 * @author: huangxilei
	 */
	Long applyAsSubByIdWithoutUser(String shopId, Long subId, String submitNickName, String submitPhoneNo) throws SubbranchException;
	
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
	 * 角色：总店<br>
	 * 查询正常状态下的下级分店<br>
	 * 查询关键词：姓名(模糊查询userName字段)，电话(模糊查询userBindingCell字段)，是否有发货权限<br>
	 * @Title: findSubbranchWithNamePhoneDelievery 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param pagination
	 * @param subbranchVo
	 * @return
	 * @date 2015年8月14日 上午10:50:10  
	 * @author cbc
	 */
	Pagination<SubbranchVo> findSubbranchWithNamePhoneDelievery(Pagination<SubbranchVo> pagination, SubbranchVo subbranchVo);

	/**
	 * 
	 * @Title: getActiveSubbranchByUserId 
	 * @Description: 获取用户有效状态下的分店
	 * @param userId
	 * @return
	 * @date 2015年8月25日 上午10:01:04  
	 * @author cbc
	 */
	SubbranchVo getActiveSubbranchByUserId(Long userId);
}
