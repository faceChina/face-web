package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;
import com.zjlp.face.web.server.user.shop.domain.vo.RecommendShopVo;

public interface ShopMapper {
    int deleteByPrimaryKey(String no);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(String no);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKeyWithBLOBs(Shop record);

    int updateByPrimaryKey(Shop record);
	
	List<Shop> selectShopListByUserId(Long userId);
	
	Shop getShopByInvitationCode(String invitationCode);
	
    int countShop(Long userId);
    
    List<Shop> queryAllShopByName(Map<String,Object> map);

	List<ShopDto> selectShopPasswd(Long managedAccountId);

	List<ShopDto> findAllShopDtoByMtr(Long userId);
	
	Integer countShopByType(Map<String,Object> map);

	Integer selectCount(ShopDto shopDto);
	
	Shop getShopByUserId(Long userId);

	Long findSellerIdByShopNo(String shopNo);

	List<Shop> findShopListByMemberCardId(Long memberCardId);

	List<Shop> selectFoundShop(Map<String, Object> map);

	Integer getFoundShopsPageCount(Map<String, Object> map);

	List<Shop> selectFoundShops(Map<String, Object> map);

	Shop selectByAuthCode(String code);

	Integer getRecruitShopCount(@Param("keyword")String keyword);

	List<Shop> findRecruitShop(@Param("pageStart")Integer start, @Param("pageSize")Integer pageSize, @Param("keyword")String keyword);

	List<ShopDto> findShopList(ShopDto shop);
	
	List<RecommendShopVo> getRecommendShop (Long userId);

}