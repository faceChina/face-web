package com.zjlp.face.web.server.user.favorites.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.util.exception.AssertUtil;
import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.server.operation.subbranch.dao.SubbranchDao;
import com.zjlp.face.web.server.operation.subbranch.domain.Subbranch;
import com.zjlp.face.web.server.product.good.dao.GoodDao;
import com.zjlp.face.web.server.product.good.domain.Good;
import com.zjlp.face.web.server.user.favorites.dao.FavoritesDao;
import com.zjlp.face.web.server.user.favorites.domain.Favorites;
import com.zjlp.face.web.server.user.favorites.domain.dto.FavoritesDto;
import com.zjlp.face.web.server.user.favorites.service.FavoritesService;
import com.zjlp.face.web.server.user.shop.dao.ShopDao;
import com.zjlp.face.web.server.user.shop.domain.Shop;

@Service
public class FavoritesServiceImpl implements FavoritesService {
	
	@Autowired
	private FavoritesDao favoritesDao;
	@Autowired
	private ShopDao shopDao;
	@Autowired
	private GoodDao goodDao;
	@Autowired
	private SubbranchDao subbranchDao;

	@Override
	public Long addFavorites(Favorites favorites) {
		AssertUtil.notTrue(this.isExist(favorites), "请勿重复收藏 ");
		if (null == favorites.getStatus()) {
			favorites.setStatus(Constants.VALID);
		}
		Date date = new Date();
		favorites.setCreateTime(date);
		favorites.setUpdateTime(date);
		return favoritesDao.addFavorites(favorites);
	}

	/**
	 * 
	 * @Title: isExist 
	 * @Description: 存在这个收藏则返回true
	 * @param favorites
	 * @return
	 * @date 2015年8月31日 下午5:37:23  
	 * @author cbc
	 */
	private boolean isExist(Favorites favorites) {
		Favorites oldFavorites = this.getFavorites(favorites.getUserId(), favorites.getRemoteType(), favorites.getRemoteId());
		return (oldFavorites != null && oldFavorites.getStatus().intValue() == Constants.VALID);
	}


	@Override
	public Pagination<FavoritesDto> findFavorites(
			Pagination<FavoritesDto> pagination,  Long userId, List<Integer> remoteTypes, Integer status) {
		Integer totalRow = favoritesDao.countFavorites(userId, remoteTypes, status);
		List<FavoritesDto> datas = favoritesDao.findFavoritesPage(pagination, userId, remoteTypes, status);
		for (FavoritesDto data : datas) {
			if (data.getRemoteType().intValue() == Favorites.TYPE_GOOD) {
				Good good = goodDao.getById(Long.valueOf(data.getRemoteId()));
				data.setRemotePicUrl(good.getPicUrl());
				data.setRemotePrice(good.getSalesPrice());
				data.setRemoteName(good.getName());
				data.setGoodShopNo(good.getShopNo());
			}
			if (data.getRemoteType().intValue() == Favorites.TYPE_SHOP) {
				Shop shop = shopDao.getShopByNo(data.getRemoteId());
				data.setRemotePicUrl(shop.getShopLogoUrl());
				data.setRemoteName(shop.getName());
			}
			if (data.getRemoteType().intValue() == Favorites.TYPE_SUBBRANCH) {
				Subbranch subbranch = subbranchDao.findByPrimarykey(Long.valueOf(data.getRemoteId()));
				Shop shop = shopDao.getShopByNo(subbranch.getSupplierShopNo());
				data.setRemotePicUrl(shop.getShopLogoUrl());
				data.setRemoteName(shop.getName()+"-"+subbranch.getShopName());
			}
		}
		pagination.setTotalRow(totalRow);
		pagination.setDatas(datas);
		return pagination;
	}

	@Override
	public Favorites getFavorites(Long userId, Integer remoteType,
			String remoteId) {
		return favoritesDao.getFavorites(userId, remoteType, remoteId);
	}


	@Override
	public void updateFavorites(Favorites favorites) {
		favorites.setUpdateTime(new Date());
		favoritesDao.updateFavorites(favorites);
	}

	@Override
	public void updateFavoritesStatusBatch(List<Long> id, Long userId,
			Integer status) {
		favoritesDao.updateFavoritesStatusBatch(id, userId, status);
	}

	@Override
	public Integer countFavorites(Long userId, List<Integer> remoteTypes, Integer status) {
		return favoritesDao.countFavorites(userId, remoteTypes, status);
	}

	@Override
	public Favorites getById(Long id) {
		return favoritesDao.getById(id);
	}

	@Override
	public List<Favorites> findInvalideFavorite(Long userId, List<Integer> remoteTypeList) {
		return favoritesDao.findInvalideFavorite(userId, remoteTypeList);
	}

	@Override
	public List<Favorites> findFavoritesGoods(Integer remoteType, String remoteId) {
		return this.favoritesDao.findFavoritesGoods(remoteType, remoteId);
	}

}
