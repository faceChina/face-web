package com.zjlp.face.web.ctl.wap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zjlp.face.web.constants.Constants;
import com.zjlp.face.web.exception.ext.FavoritesException;
import com.zjlp.face.web.server.user.favorites.bussiness.FavoritesBussiness;
import com.zjlp.face.web.server.user.favorites.domain.Favorites;

@Controller
@RequestMapping("/wap/{shopNo}/buy/favorites")
public class FavoritesWapCtl extends WapCtl {
	
	
	@Autowired
	private FavoritesBussiness favoritesBussiness;

	/**
	 * 
	 * @Title: favorites 
	 * @Description: 收藏按钮
	 * @param model
	 * @param remoteType
	 * @param remoteId
	 * @return
	 * @date 2015年9月1日 下午5:06:27  
	 * @author cbc
	 */
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public String favorites(Model model, Integer remoteType, String remoteId) {
		try {
			Long userId = super.getUserId();
			if (null == remoteType || (remoteType.intValue() != Favorites.TYPE_GOOD && remoteType.intValue() != Favorites.TYPE_SHOP && remoteType.intValue() != Favorites.TYPE_SUBBRANCH)) {
				throw new FavoritesException("无效url");
			}
			Favorites oldFavorites = favoritesBussiness.getFavorites(userId, remoteType, remoteId);
			if (null == oldFavorites) {
				Favorites favorites = new Favorites();
				favorites.setUserId(getUserId());
				favorites.setRemoteType(remoteType);
				favorites.setRemoteId(remoteId);
				favoritesBussiness.addFavorites(favorites);
				return super.getReqJson(true, "收藏成功");
			} else if (oldFavorites.getStatus().intValue() == Constants.VALID) {
				favoritesBussiness.updateFavoritesStatus(userId, oldFavorites.getId(), Constants.NOTDEFAULT);
				return super.getReqJson(true, "取消收藏");
			} else if (oldFavorites.getStatus().intValue() == Constants.NOTDEFAULT) {
				favoritesBussiness.updateFavoritesStatus(userId, oldFavorites.getId(), Constants.VALID);
				return super.getReqJson(true, "收藏成功");
			} else {
				throw new FavoritesException("无效操作");
			}
		} catch (Exception e) {
			return super.getReqJson(false, "操作失败");
		}
	}
	
	/**
	 * 
	 * @Title: favoritesUnlogin 
	 * @Description: 未登陆状态下收藏
	 * @param model
	 * @param remoteType
	 * @param remoteId
	 * @return
	 * @date 2015年9月2日 下午4:49:44  
	 * @author cbc
	 */
	@RequestMapping("addNoLogin/{remoteType}/{remoteId}")
	public String favoritesUnlogin(Model model, @PathVariable Integer remoteType, @PathVariable String remoteId) {
			Long userId = super.getUserId();
			Favorites oldFavorites = favoritesBussiness.getFavorites(userId, remoteType, remoteId);
			if (null == oldFavorites) {
				Favorites favorites = new Favorites();
				favorites.setUserId(getUserId());
				favorites.setRemoteType(remoteType);
				favorites.setRemoteId(remoteId);
				favoritesBussiness.addFavorites(favorites);
			} else if (oldFavorites.getStatus().intValue() == Constants.VALID) {
				favoritesBussiness.updateFavoritesStatus(userId, oldFavorites.getId(), Constants.NOTDEFAULT);
			} else if (oldFavorites.getStatus().intValue() == Constants.NOTDEFAULT) {
				favoritesBussiness.updateFavoritesStatus(userId, oldFavorites.getId(), Constants.VALID);
			} else {
				throw new FavoritesException("无效操作");
			}
			if (Favorites.TYPE_GOOD.intValue() == remoteType) {
				return super.getRedirectUrl("/wap/"+super.getShopNo()+"/any/item/"+remoteId);
			} else if (Favorites.TYPE_SHOP.intValue() == remoteType) {
				return super.getRedirectUrl("/wap/"+super.getShopNo()+"/any/gwscIndex");
			} else if (Favorites.TYPE_SUBBRANCH.intValue() == remoteType) {
				return super.getRedirectUrlNoHtm("/wap/"+super.getShopNo()+"/any/gwscIndex.htm?subbranchId="+remoteId);
			} else {
				throw new FavoritesException("无效操作");
			}
	}
	
	/**
	 * 
	 * @Title: cancelFavoriteBatch 
	 * @Description: 批量取消收藏
	 * @param model
	 * @param ids
	 * @return
	 * @date 2015年9月1日 下午5:17:37  
	 * @author cbc
	 */
	@RequestMapping(value="cancel", method=RequestMethod.POST)
	@ResponseBody
	public String cancelFavoriteBatch(Model model, @RequestParam String ids) {
		try {
			String[] split = ids.split(",");
			List<Long> idList = new ArrayList<Long>();
			for (String string : split) {
				idList.add(Long.valueOf(string));
			}
			favoritesBussiness.calcelFavoritesList(idList, super.getUserId());
			return super.getReqJson(true, "操作成功");
		} catch (Exception e) {
			return super.getReqJson(false, "操作失败");
		}
	}
	
	@RequestMapping(value="delInvalide", method=RequestMethod.POST)
	@ResponseBody
	public String delInvalidFavorite(Model model, Integer remoteType) {
		try {
			Long userId = super.getUserId();
			List<Integer> remoteTypeList = new ArrayList<Integer>();
			if (1 == remoteType) {
				remoteTypeList.add(1);
			} else {
				remoteTypeList.add(2);
				remoteTypeList.add(3);
			}
			List<Favorites> list = favoritesBussiness.findInvalideFavorite(userId, remoteTypeList);
			List<Long> ids = new ArrayList<Long>();
			for (Favorites favorites : list) {
				ids.add(favorites.getId());
			}
			favoritesBussiness.calcelFavoritesList(ids, userId);
			return super.getReqJson(true, "操作成功");
		} catch (Exception e) {
			return super.getReqJson(false, "操作失败");
		}
	}
	
}
