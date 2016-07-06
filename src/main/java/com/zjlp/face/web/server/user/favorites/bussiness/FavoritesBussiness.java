package com.zjlp.face.web.server.user.favorites.bussiness;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.exception.ext.FavoritesException;
import com.zjlp.face.web.server.user.favorites.domain.Favorites;
import com.zjlp.face.web.server.user.favorites.domain.dto.FavoritesDto;

public interface FavoritesBussiness {
	
	/**
	 * 
	 * @Title: addFavorites 
	 * @Description: 增加收藏
	 * @param favorites
	 * @return
	 * @date 2015年8月31日 下午2:58:45  
	 * @author cbc
	 */
	Long addFavorites (Favorites favorites) throws FavoritesException;
	
	/**
	 * 
	 * @Title: findFavorites 
	 * @Description: 分页查询收藏
	 * @param pagination
	 * @param userId
	 * @param remoteTypes
	 * @param status 查询全部收藏传1 查询失效收藏传0
	 * @return
	 * @throws FavoritesException
	 * @date 2015年9月4日 下午2:29:28  
	 * @author cbc
	 */
	Pagination<FavoritesDto> findFavorites(Pagination<FavoritesDto> pagination, Long userId, List<Integer> remoteTypes, Integer status) throws FavoritesException;

	/**
	 * 
	 * @Title: calcelFavoritesList 
	 * @Description: 清除失效收藏
	 * @param id
	 * @param userId
	 * @date 2015年8月31日 下午2:59:33  
	 * @author cbc
	 */
	void calcelFavoritesList(List<Long> ids, Long userId) throws FavoritesException;
	
	/**
	 * 
	 * @Title: getFavorites 
	 * @Description: 获取收藏
	 * @param userId
	 * @param remoteType
	 * @param remoteId
	 * @return
	 * @date 2015年9月1日 上午9:32:25  
	 * @author cbc
	 */
	Favorites getFavorites(Long userId, Integer remoteType, String remoteId) throws FavoritesException;
	
	/**
	 * 
	 * @Title: getFavorites 
	 * @Description: 通过ID获取收藏
	 * @param id
	 * @return
	 * @throws FavoritesException
	 * @date 2015年9月2日 上午10:29:23  
	 * @author cbc
	 */
	Favorites getFavorites(Long id) throws FavoritesException;

	/**
	 * 
	 * @Title: updateFavoritesStatus 
	 * @Description: 更新收藏状态
	 * @param userId
	 * @param id
	 * @param status 为null时，查询全部收藏
	 * @param status
	 * @date 2015年9月1日 上午11:03:29  
	 * @author cbc
	 */
	void updateFavoritesStatus(Long userId, Long id, Integer status) throws FavoritesException;
	
	/**
	 * 
	 * @Title: countFavorites 
	 * @Description: 获取收藏的数量
	 * @param userId
	 * @param remoteType
	 * @param status 查询全部收藏传null 查询失效收藏传0
	 * @return
	 * @date 2015年9月1日 上午11:09:20  
	 * @author cbc
	 */
	Integer countFavorites(Long userId, List<Integer> remoteTypes, Integer status) throws FavoritesException;

	/**
	 * 
	 * @Title: findInvalideFavorite 
	 * @Description: 获取用户失效收藏
	 * @param userId
	 * @param remoteType
	 * @return
	 * @date 2015年9月4日 上午9:06:12  
	 * @author cbc
	 */
	List<Favorites> findInvalideFavorite(Long userId, List<Integer> remoteTypeList);


	
}
