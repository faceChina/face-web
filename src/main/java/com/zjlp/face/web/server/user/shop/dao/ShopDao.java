package com.zjlp.face.web.server.user.shop.dao;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.FoundShopDto;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;
import com.zjlp.face.web.server.user.shop.domain.vo.RecommendShopVo;

public interface ShopDao {
	
	
	/**
	 * @Description: 查询用户店铺status>-1
	 * @param userId
	 * @return
	 * @date: 2015年3月10日 下午5:28:45
	 * @author: zyl
	 */
	List<Shop> findShopListByUserId(Long userId);
	
	/**
	 * @Description: 修改店铺
	 * @param shop
	 * @date: 2015年3月11日 下午5:00:13
	 * @author: zyl
	 */
	void editShop(Shop shop);
	
	
	/**
	 * @Description: 查询店铺
	 * @param no
	 * @return
	 * @date: 2015年3月11日 下午5:00:08
	 * @author: zyl
	 */
	Shop getShopByNo(String no);
	
	Shop getShopByInvitationCode(String invitationCode);
	
	/**
	 * 查询店铺
	 * @param shop
	 * @return
	 */
	List<ShopDto> findShopList(ShopDto shop);
	
	/**
	 * @Description: 新增店铺
	 * @param shop
	 * @date: 2015年3月20日 下午1:47:51
	 * @author: zyl
	 */
	void addShop(Shop shop);
	
	/**
	 * 查询店铺
	 * @Title: queryAllShopByName 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param managedAccountsId
	 * @param sosuo
	 * @return
	 * @date 2015年1月7日 下午2:45:48  
	 * @author fjx
	 */
	List<Shop> queryAllShopByName(Long managedAccountsId,String sosuo);
	
	/**
	 * 统计该用户的产品数量
	 * @Title: countShop 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @return
	 * @date 2014年7月22日 上午10:22:52  
	 * @author fjx
	 */
	Integer countShop(Long userId);
	
	
	/**
	 * 查询产品及产品支付密码
	 * @Title: selectShopPasswd 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param managedAccountsId
	 * @return
	 * @date 2015年1月7日 下午2:46:22  
	 * @author Administrator
	 */
	List<ShopDto> selectShopPasswd(Long managedAccountsId);


	/**
	 * 查询未分配的产品
	 * @Title: findAllShopDtoByMtr 
	 * @Description: (查询未分配的产品) 
	 * @param userId
	 * @return
	 * @date 2014年8月21日 下午3:31:58  
	 * @author ah
	 */
	List<ShopDto> findAllShopDtoByMtr(Long userId);

	/**
	 * 查找店铺列表个数
	 * 
	 * @Title: getCount 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shopDto
	 * @return
	 * @date 2015年1月16日 下午7:01:32  
	 * @author lys
	 */
	Integer getCount(ShopDto shopDto);

	/**
	 * 根据用户id查询店铺status>-1
	 * @Title: getShopByUserId 
	 * @Description: (根据用户id查询店铺status>-1) 
	 * @param userId
	 * @return
	 * @date 2015年2月10日 下午4:15:34  
	 * @author ah
	 */
	Shop getShopByUserId(Long userId);

	/**
	 * 
	 * @Title: findSellerIdByShopNo 
	 * @Description: 通过shopNO查询sellerID 
	 * @param shopNo
	 * @return
	 * @date 2015年4月15日 下午5:27:41  
	 * @author cbc
	 */
	Long findSellerIdByShopNo(String shopNo);

	/**
	 * 根据会员卡id查询店铺列表
	 * @Title: findShopListByMemberUserId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberCardId
	 * @return
	 * @date 2015年4月22日 上午9:22:30  
	 * @author ah
	 */
	List<Shop> findShopListByMemberCardId(Long memberCardId);
	/**
	 * 查找发现店铺统计
	 * 
	 * @param shopCriteria
	 * @return
	 */
	Integer getPageCount(FoundShopDto shopCriteria);

	/**
	 * 查找发现店铺数据s
	 * 
	 * @param shopCriteria
	 * @return
	 */
	List<Shop> getFoundShop(FoundShopDto shopCriteria, Pagination<Shop> pagination);

	/**
	 * 通过授权码查询店铺
	 * @Title: getByAuthCode 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param code
	 * @return
	 * @date 2015年5月6日 下午4:37:06  
	 * @author lys
	 */
	Shop getByAuthCode(String code);

	/**
	 * 
	 * @Title: getRecruitShopCount 
	 * @Description: 获取在招募的店铺数量
	 * @return
	 * @date 2015年5月8日 上午11:46:58  
	 * @author cbc
	 * @param keyword 
	 */
	Integer getRecruitShopCount(String keyword);

	/**
	 * 
	 * @Title: findRecruitShop 
	 * @Description: 分页查询所有在招募的店铺
	 * @param start
	 * @param pageSize
	 * @return
	 * @date 2015年5月8日 上午11:53:24  
	 * @author cbc
	 * @param keyword 
	 */
	List<Shop> findRecruitShop(Integer start, Integer pageSize, String keyword);
	
	/**
	 * 
	 * @Title: getRecommendShop 
	 * @Description: 根据当前用户 推荐店铺（总店和分店）
	 * @param userId
	 * @return
	 * @date 2015年9月2日 上午16:50:24  
	 * @author talo
	 */
	List<RecommendShopVo> getRecommendShop (Long userId);

}
