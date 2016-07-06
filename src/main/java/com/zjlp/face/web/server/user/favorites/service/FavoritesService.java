package com.zjlp.face.web.server.user.favorites.service;

import java.util.List;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.server.user.favorites.domain.Favorites;
import com.zjlp.face.web.server.user.favorites.domain.dto.FavoritesDto;

public interface FavoritesService {

	/**
	 * 
	 * @Title: addFavorites 
	 * @Description: 增加收藏
	 * @param favorites
	 * @return
	 * @date 2015年8月31日 下午3:23:40  
	 * @author cbc
	 */
	Long addFavorites(Favorites favorites);

	/**
	 * 
	 * @Title: findFavorites 
	 * @Description: 分页查询收藏
	 * @param pagination
	 * @param favorites
	 * @return
	 * @date 2015年8月31日 下午4:16:16  
	 * @author cbc
	 */
	Pagination<FavoritesDto> findFavorites(Pagination<FavoritesDto> pagination,
			 Long userId, List<Integer> remoteTypes, Integer status);

	/**
	 * 
	 * @Title: getFavorites 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param userId
	 * @param remoteType
	 * @param remoteId
	 * @return
	 * @date 2015年8月31日 下午5:21:25  
	 * @author cbc
	 */
	Favorites getFavorites(Long userId, Integer remoteType, String remoteId);

	/**
	 * 
	 * @Title: updateFavorites 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param oldFavorites
	 * @date 2015年8月31日 下午5:28:49  
	 * @author cbc
	 */
	void updateFavorites(Favorites oldFavorites);

	/**
	 * 
	 * @Title: updateFavoritesStatusBatch 
	 * @Description: 批量更新状态
	 * @param id
	 * @param userId
	 * @param status
	 * @date 2015年8月31日 下午5:56:18  
	 * @author cbc
	 */
	void updateFavoritesStatusBatch(List<Long> id, Long userId,
			Integer status);

	/**
	 * 
	 * @Title: countFavorites 
	 * @Description: 查询收藏个数
	 * @param userId
	 * @param remoteType
	 * @return
	 * @date 2015年9月1日 上午11:10:45  
	 * @author cbc
	 */
	Integer countFavorites(Long userId, List<Integer> remoteTypes, Integer status);

	/**
	 * 
	 * @Title: getById 
	 * @Description: 通过ID获取收藏 
	 * @param id
	 * @return
	 * @date 2015年9月2日 上午10:30:33  
	 * @author cbc
	 */
	Favorites getById(Long id);

	/**
	 * 
	 * @Title: findInvalideFavorite 
	 * @Description: 获取用户失效收藏 
	 * @param userId
	 * @param remoteType
	 * @return
	 * @date 2015年9月4日 上午9:06:52  
	 * @author cbc
	 */
	List<Favorites> findInvalideFavorite(Long userId, List<Integer> remoteTypeList);

	/**
	 * @Title: findFavoritesGoods
	 * @Description: (查找某商品/店铺被收藏总数)
	 * @param rermoteType
	 * @param remoteId
	 * @return
	 * @return List<Favorites> 返回类型
	 * @throws
	 * @author Baojiang Yang
	 * @date 2015年9月5日 上午11:00:12
	 */
	List<Favorites> findFavoritesGoods(Integer remoteType, String remoteId);


}
