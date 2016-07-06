package com.zjlp.face.web.server.user.favorites.bussiness.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.FavoritesException;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.operation.subbranch.service.SubbranchService;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.product.good.service.GoodService;
import com.zjlp.face.web.server.user.favorites.bussiness.FavoritesBussiness;
import com.zjlp.face.web.server.user.favorites.domain.Favorites;
import com.zjlp.face.web.server.user.favorites.domain.dto.FavoritesDto;
import com.zjlp.face.web.server.user.favorites.service.FavoritesService;
import com.zjlp.face.web.server.user.shop.domain.Shop;
import com.zjlp.face.web.server.user.shop.service.ShopService;
import com.zjlp.face.web.server.user.user.domain.User;
import com.zjlp.face.web.server.user.user.service.UserService;

@Component
public class FavoritesBussinessImpl implements FavoritesBussiness {
	
	@Autowired
	private UserService userService;
	@Autowired
	private GoodService goodService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private FavoritesService favoritesService;
	@Autowired
	private SubbranchService subbranchService;
	

	@Override
	public Long addFavorites(Favorites favorites) throws FavoritesException {
		try {
			AssertUtil.notNull(favorites, "参数favorites不能为空");
			AssertUtil.notNull(favorites.getUserId(), "参数favorites.userId不能为空");
			AssertUtil.notNull(favorites.getRemoteType(), "参数favorites.remoteType不能为空");
			AssertUtil.notNull(favorites.getRemoteId(), "参数favorites.remoteId不能为空");
			AssertUtil.isTrue(this.checkFavorites(favorites), "参数验证失败");
			return favoritesService.addFavorites(favorites);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FavoritesException(e);
		}
	}

	@Override
	public void updateFavoritesStatus(Long userId, Long id, Integer status) {
		try {
			AssertUtil.notNull(userId, "userId不能为空");
			AssertUtil.notNull(id, "id不能为空");
			AssertUtil.notNull(status, "status不能为空");
			AssertUtil.isTrue(Favorites.checkStatus(status), "status验证不通过");
			AssertUtil.isTrue(this.checkUserFavorites(userId, id), "权限验证未通过");
			Favorites favorites = new Favorites();
			favorites.setUserId(userId);
			favorites.setId(id);
			favorites.setStatus(status);
			favoritesService.updateFavorites(favorites);
		} catch (Exception e) {
			throw new FavoritesException(e);
		}
	}

	/**
	 * 
	 * @Title: checkUserFavorites 
	 * @Description: 验证用户是否有修改收藏的权限,有权限则返回true
	 * @param userId
	 * @param id
	 * @return
	 * @date 2015年9月2日 上午10:27:11  
	 * @author cbc
	 */
	private boolean checkUserFavorites(Long userId, Long id) {
		Favorites favorites = this.getFavorites(id);
		return (favorites != null && favorites.getUserId().longValue() == userId);
	}

	/**
	 * 
	 * @Title: checkFavorites 
	 * @Description: 验证参数合法性
	 * @param favorites
	 * @return
	 * @date 2015年8月31日 下午3:04:37  
	 * @author cbc
	 */
	private boolean checkFavorites(Favorites favorites) {
		boolean boo = false;
		if (this.checkUser(favorites.getUserId()) && this.checkGoodOrShop(favorites.getRemoteType(), favorites.getRemoteId())) {
			boo = true;
		}
		return boo;
	}

	/**
	 * 
	 * @Title: checkGoodOrShop 
	 * @Description: 验证收藏的物品是否有效
	 * @param remoteType
	 * @param remoteId
	 * @return
	 * @date 2015年8月31日 下午3:10:29  
	 * @author cbc
	 */
	private boolean checkGoodOrShop(Integer remoteType, String remoteId) {
		boolean boo = false;
		if (remoteType.intValue() == Favorites.TYPE_GOOD) {
			Good good = goodService.getGoodById(Long.valueOf(remoteId));
			boo = (good != null && good.getStatus() == Constants.VALID);
		}
		if (remoteType.intValue() == Favorites.TYPE_SHOP) {
			Shop shop = shopService.getShopByNo(remoteId);
			boo = (shop != null && shop.getStatus() == Constants.VALID);
		}
		if (remoteType.intValue() == Favorites.TYPE_SUBBRANCH) {
			Subbranch subbranch = subbranchService.findByPrimarykey(Long.valueOf(remoteId));
			boo = (subbranch != null && subbranch.getStatus() == Constants.VALID);
		}
		return boo;
	}

	/**
	 * 
	 * @Title: checkUser 
	 * @Description: 验证用户是否存在
	 * @param userId
	 * @return
	 * @date 2015年8月31日 下午3:09:43  
	 * @author cbc
	 */
	private boolean checkUser(Long userId) {
		User user = userService.getById(userId);
		return user != null;
	}


	@Override
	public Pagination<FavoritesDto> findFavorites(Pagination<FavoritesDto> pagination, Long userId, List<Integer> remoteTypes, Integer status) {
		try {
			AssertUtil.notNull(pagination, "参数pagination不能为空");
			AssertUtil.notNull(userId, "参数userId不能为空");
			AssertUtil.notEmpty(remoteTypes, "参数remoteTypes不能为空");
			return favoritesService.findFavorites(pagination, userId, remoteTypes, status);
		} catch (Exception e) {
			throw new FavoritesException(e);
		}
	}

	@Override
	public void calcelFavoritesList(List<Long> ids, Long userId) {
		try {
			AssertUtil.notEmpty(ids, "ids不能为空");
			AssertUtil.notNull(userId, "userId不能为空");
			favoritesService.updateFavoritesStatusBatch(ids, userId, Constants.NOTDEFAULT);
		} catch (Exception e) {
			throw new FavoritesException(e);
		}
	}

	@Override
	public Favorites getFavorites(Long userId, Integer remoteType,
			String remoteId) {
		try {
			AssertUtil.notNull(userId, "userId不能为空");
			AssertUtil.notNull(remoteType, "remoteType不能为空");
			AssertUtil.hasLength(remoteId, "remoteId不能为空");
			return favoritesService.getFavorites(userId, remoteType, remoteId);
		} catch (Exception e) {
			throw new FavoritesException(e);
		}
	}

	@Override
	public Integer countFavorites(Long userId, List<Integer> remoteTypes, Integer status) {
		try {
			AssertUtil.notNull(userId, "userId不能为空");
			AssertUtil.notEmpty(remoteTypes, "remoteTypes不能为空");
			return favoritesService.countFavorites(userId, remoteTypes, status);
		} catch (Exception e) {
			throw new FavoritesException(e);
		}
	}

	@Override
	public Favorites getFavorites(Long id) throws FavoritesException {
		AssertUtil.notNull(id, "id不能为空");
		return favoritesService.getById(id);
	}

	@Override
	public List<Favorites> findInvalideFavorite(Long userId, List<Integer> remoteTypeList) {
		return favoritesService.findInvalideFavorite(userId, remoteTypeList);
	}


}
