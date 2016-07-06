package com.zjlp.face.web.server.user.shop.bussiness;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.ShopException;
import com.zjlp.face.web.exception.ext.UserException;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.FoundShopDto;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopLocationDto;
import com.zjlp.face.web.server.user.shop.domain.vo.RecommendShopVo;

public interface ShopBusiness {
	
	/**
	 * @Description: 新增普通店铺
	 * @param dto
	 * @return
	 * @throws ShopException
	 * @date: 2015年3月20日 上午10:14:04
	 * @author: zyl
	 */
	Shop addCommonShop(ShopDto dto) throws ShopException;
	

	/**
	 * @Description: 通过shopNo获得shop
	 * @param shopNo
	 * @return
	 * @date: 2015年3月11日 下午4:56:15
	 * @author: zyl
	 */
	Shop getShopByNo(String shopNo);
	
	/**
	 * 查询店铺列表status>-1
	 * @Title: findShopListByUserId
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param userId
	 * @return
	 * @date: 2015年3月11日 下午4:56:15
	 * @author: zyl
	 */
	List<Shop> findShopListByUserId(Long userId);
	
	/**
	 * @Description: 修改店铺信息
	 * @param shop
	 * @date: 2015年3月11日 下午4:57:22
	 * @author: zyl
	 */
	void editShop(Shop shop);
	
	/**
	 * @Description: 激活店铺
	 * @param code
	 * @param loginAccount
	 * @return 返回值：0,未使用;1,已使用;-1,查无此码;
	 * @date: 2015年3月11日 下午4:57:22
	 * @author: zyl
	 */
	Integer activateShop(String code, String loginAccount);
	
	Shop getShopByInvitationCode(String invitationCode);
	
	/**
	 * 检查产品是否过期
	 * @Title: checkDate
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param no 店铺编号
	 * @return
	 * @throws UserException
	 * @date 2014年7月25日 下午6:58:34
	 * @author fjx
	 */
	String checkDate(String no) throws ShopException;
	
	/**
	 * 检查用户是否具有进入该店铺资格
	 * @Title: checkIntoShop
	 * @Description: (这里用一句话描述这个方法的作用)
	 * @param no
	 * @return
	 * @date 2014年7月19日 下午3:48:38
	 * @author fjx
	 */
	boolean checkIntoShop(String no,Long userId) throws ShopException;

	/**
	 * 店铺绑定微信
	 * @Title: bindshop 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param shop
	 * @date 2015年4月1日 下午4:46:07  
	 * @author ah
	 */
	void bindshop(Shop shop);

	/**
	 * 
	 * @Title: findSellerIdByShopNo 
	 * @Description: 通过shopNo查询用户ID
	 * @param shopNo
	 * @return
	 * @date 2015年4月14日 下午7:28:31  
	 * @author cbc
	 */
	Long findSellerIdByShopNo(String shopNo);

	/**
	 * 
	 * @Title: findRecruitShop 
	 * @Description: 对所有的在招募店铺进行分页查询 
	 * @param pagination
	 * @return
	 * @date 2015年5月8日 上午11:40:11  
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
	 * @date 2015年5月12日 上午11:40:45  
	 * @author ah
	 */
	Shop getShopByUserId(Long userId);

	/**
	 * 
	 * @Title: getShopByAuthCode 
	 * @Description:通过授权码查询店铺 
	 * @param code
	 * @return
	 * @date 2015年5月16日 下午2:43:18  
	 * @author cbc
	 */
	Shop getShopByAuthCode(String code);

	
	/**
	 * 查找发现店铺
	 * 
	 * @param shopCriteria
	 * @param pagination
	 * @return
	 * @throws ShopException
	 */
	Pagination<Shop> getFoundShop(FoundShopDto shopCriteria,
			Pagination<Shop> pagination) throws ShopException;
	
	List<ShopDto> findShopList(Long userId, String shopNo,String shopName) throws ShopException;

	/**
	 * 店铺解绑公众号
	 * @param shop
	 */
	void unbindshop(Shop shop) throws ShopException;
	
	/**
	 * 
	 * @Title: getRecommendShop 
	 * @Description: 根据当前用户 推荐店铺（总店和分店）
	 * @param userId
	 * @return
	 * @date 2015年9月2日 上午16:50:24  
	 * @author talo
	 */
	List<RecommendShopVo> getRecommendShop (Long userId) throws ShopException;
	
	/**
	 * 查询店铺是否设置过联系方式/地址
	 * @param shopNo	店铺NO
	 * @return
	 */
	boolean isShopLocation(String shopNo);
	
	/**
	 * 查询店铺联系方式/地址
	 * @param shopNo	店铺NO
	 * @return
	 */
	ShopLocationDto getShopLocation(String shopNo);

	/**
	 * 添加店铺联系方式/地址
	 * @param shopNo	店铺NO
	 * @param lat		纬度
	 * @param lng		经度
	 * @param address	地址
	 * @param cell		联系方式
	 * @param area		区域名称
	 */
	void addShopLocation(String shopNo, String lat, String lng, String address,String cell, String area);

	/**
	 * 修改店铺联系方式/地址
	 * @param shopNo	店铺NO
	 * @param lat		纬度
	 * @param lng		经度
	 * @param address	地址
	 * @param cell		联系方式
	 * @param area		区域名称
	 */
	void editShopLocation(String shopNo, String lat, String lng,String address, String cell, String area);
}
