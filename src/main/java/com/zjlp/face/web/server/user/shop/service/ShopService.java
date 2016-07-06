package com.zjlp.face.web.server.user.shop.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.FoundShopDto;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;
import com.zjlp.face.web.server.user.shop.domain.vo.RecommendShopVo;


public interface ShopService {
	
	/**
	 * @Description: 查询用户店铺status>-1
	 * @param userId
	 * @return
	 * @date: 2015年3月10日 下午5:28:45
	 * @author: zyl
	 */
	List<Shop> findShopListByUserId(Long userId);
	
	/**
	 * 编辑店铺
	 * @Title: editShop
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param shop
	 * @date: 2015年3月10日 下午5:28:45
	 * @author: zyl
	 */
	void editShop(Shop shop);
	
	/**
	 * 查询产品
	 * @Title: getShopByNo
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param no
	 * @return
	 * @date: 2015年3月10日 下午5:28:45
	 * @author: zyl
	 */
	Shop getShopByNo(String no);
	
	/**
	 * @Description: 通过邀请码查询店铺
	 * @param invitationCode
	 * @return
	 * @date: 2015年3月20日 上午9:41:01
	 * @author: zyl
	 */
	Shop getShopByInvitationCode(String invitationCode);
	
	/**
	 * @Description: 新增店铺
	 * @param shop
	 * @date: 2015年3月20日 下午1:48:14
	 * @author: zyl
	 */
	void addShop(Shop shop);
	
	
	/**
	 * 
	 * @Title: findSellerIdByShopNo 
	 * @Description: 通过SHOPNO查询sellerID
	 * @param shopNo
	 * @return
	 * @date 2015年4月15日 下午5:26:22  
	 * @author cbc
	 */
	Long findSellerIdByShopNo(String shopNo);

	/**
	 * 根据会员用户id查询店铺列表
	 * @Title: findShopListByMemberCardId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param memberCardId
	 * @return
	 * @date 2015年4月22日 上午9:20:37  
	 * @author ah
	 */
	List<Shop> findShopListByMemberCardId(Long memberCardId);
	/**
	 * 查找发现店铺
	 * 
	 * @param shopCriteria
	 * @param pagination
	 * @return
	 */
	Pagination<Shop> getFoundShop(FoundShopDto shopCriteria,
			Pagination<Shop> pagination);

	/**
	 * 通过店铺的授权码获取店铺
	 * @Title: getShopByAutoCode 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param code 授权码
	 * @return
	 * @date 2015年5月6日 下午4:35:50  
	 * @author lys
	 */
	Shop getShopByAutoCode(String code);

	/**
	 * 
	 * @Title: findRecruitShop 
	 * @Description: 对所有在招募的店铺进行分页查询
	 * @param pagination
	 * @return
	 * @date 2015年5月8日 上午11:42:23  
	 * @author cbc
	 * @param keyword 
	 */
	Pagination<Shop> findRecruitShop(Pagination<Shop> pagination, String keyword);

	/**
	 * 根据用户id查询店铺status>-1
	 * @Title: getShopByUserId 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @return
	 * @date 2015年5月12日 上午11:45:29  
	 * @author ah
	 */
	Shop getShopByUserId(Long userId);
	/**
	 * 初始化二维码
	 * @Title: generateTwoDimensionalCode 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @param shopNo
	 * @return
	 * @date 2015年5月12日 下午8:49:39  
	 * @author ah
	 */
	String generateTwoDimensionalCode(Long userId, String shopNo);

	/**
	 * 根据userId,店铺no,店铺名称查询店铺
	 * @param userId		用户id
	 * @param shopNo		店铺编号
	 * @param shopName		店铺名称
	 * @return
	 */
	List<ShopDto> findShopList(Long userId, String shopNo, String shopName);
	
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

	/**
	 * 查询店铺是否设置过联系方式
	 * @param shopNo	店铺NO
	 * @return
	 */
	boolean isShopCell(String shopNo);
}
