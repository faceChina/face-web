package com.zjlp.face.web.server.user.shop.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.jredis.annotation.RedisCached;
import com.zjlp.face.jredis.annotation.enums.CachedMethod;
import com.zjlp.face.jredis.annotation.enums.CachedMode;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.mapper.ShopMapper;
import com.zjlp.face.web.server.user.shop.dao.ShopDao;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.domain.dto.FoundShopDto;
import com.zjlp.face.web.server.user.shop.domain.dto.ShopDto;
import com.zjlp.face.web.server.user.shop.domain.vo.RecommendShopVo;

@Repository
public class ShopDaoImpl implements ShopDao {
	
	@Autowired
	private SqlSession sqlSession;
	@Override
	public List<Shop> findShopListByUserId(Long userId){
		return sqlSession.getMapper(ShopMapper.class).selectShopListByUserId(userId);
	}
	
	@Override
	@RedisCached(mode=CachedMode.CLEAR,key={"shop:no"})
	public void editShop(Shop shop){
		sqlSession.getMapper(ShopMapper.class).updateByPrimaryKeySelective(shop);
	}
	
	@Override
	@RedisCached(mode=CachedMode.GET,method=CachedMethod.GET_SET,key={"shop"})
	public Shop getShopByNo(String no){
		return sqlSession.getMapper(ShopMapper.class).selectByPrimaryKey(no);
	}
	
	@Override
	public Shop getShopByInvitationCode(String invitationCode){
		return sqlSession.getMapper(ShopMapper.class).getShopByInvitationCode(invitationCode);
	}

	@Override
	public void addShop(Shop shop){
		sqlSession.getMapper(ShopMapper.class).insertSelective(shop);
	}
	
	@Override
	public Integer countShop(Long userId) {
		return sqlSession.getMapper(ShopMapper.class).countShop(userId);
	}

	@Override
	public List<Shop> queryAllShopByName(Long managedAccountsId, String sosuo) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("managedAccountsId", managedAccountsId);
		map.put("sosuo", sosuo);
		return sqlSession.getMapper(ShopMapper.class).queryAllShopByName(map);
	}

	@Override
	public List<ShopDto> selectShopPasswd(Long managedAccountsId) {
		return sqlSession.getMapper(ShopMapper.class).selectShopPasswd(managedAccountsId);
	}

	@Override
	public List<ShopDto> findAllShopDtoByMtr(Long userId) {
		return sqlSession.getMapper(ShopMapper.class).findAllShopDtoByMtr(userId);
	}

	@Override
	public Integer getCount(ShopDto shopDto) {
		return sqlSession.getMapper(ShopMapper.class).selectCount(shopDto);
	}

	@Override
	public Shop getShopByUserId(Long userId) {
		return sqlSession.getMapper(ShopMapper.class).getShopByUserId(userId);
	}

	@Override
	public Long findSellerIdByShopNo(String shopNo) {
		return sqlSession.getMapper(ShopMapper.class).findSellerIdByShopNo(shopNo);
	}

	@Override
	public List<Shop> findShopListByMemberCardId(Long memberCardId) {
		return sqlSession.getMapper(ShopMapper.class).findShopListByMemberCardId(memberCardId);
	}
	@Override
	public Integer getPageCount(FoundShopDto shopCriteria) {
		Map<String, Object> map = new HashMap<String, Object>();
		return this.sqlSession.getMapper(ShopMapper.class).getFoundShopsPageCount(map);
	}

	@Override
	public List<Shop> getFoundShop(FoundShopDto shopCriteria, Pagination<Shop> pagination) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("searchKey", shopCriteria.getSearchKey());
		map.put("orderBy", shopCriteria.getOrderBy());
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		return this.sqlSession.getMapper(ShopMapper.class).selectFoundShops(map);
	}

	@Override
	public Shop getByAuthCode(String code) {
		return this.sqlSession.getMapper(ShopMapper.class).selectByAuthCode(code);
	}

	@Override
	public Integer getRecruitShopCount(String keyword) {
		return this.sqlSession.getMapper(ShopMapper.class).getRecruitShopCount(keyword);
	}

	@Override
	public List<Shop> findRecruitShop(Integer start, Integer pageSize, String keyword) {
		return this.sqlSession.getMapper(ShopMapper.class).findRecruitShop(start, pageSize, keyword);
	}

	@Override
	public List<ShopDto> findShopList(ShopDto shop) {
		return sqlSession.getMapper(ShopMapper.class).findShopList(shop);
	}

	@Override
	public List<RecommendShopVo> getRecommendShop(Long userId) {
		return sqlSession.getMapper(ShopMapper.class).getRecommendShop(userId);
	}

}
