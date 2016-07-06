package com.zjlp.face.web.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zjlp.face.web.server.user.favorites.domain.Favorites;
import com.zjlp.face.web.server.user.favorites.domain.dto.FavoritesDto;

public interface FavoritesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Favorites record);

    int insertSelective(Favorites record);

    Favorites selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Favorites record);

    int updateByPrimaryKey(Favorites record);

	Integer countFavorites(@Param("userId")Long userId, @Param("remoteTypes")List<Integer> remoteTypes, @Param("status")Integer status);

	List<FavoritesDto> findFavoritesPage(Map<String, Object> map);

	Favorites getFavorites(@Param("userId")Long userId, @Param("remoteType")Integer remoteType, @Param("remoteId")String remoteId);

	void updateFavoriteByIdAndUserId(Favorites favorites);

	void updateFavoritesStatusBatch(@Param("ids")List<Long> ids, @Param("userId")Long userId, @Param("status")Integer status);

	List<Favorites> findInvalideFavorite(@Param("userId")Long userId, @Param("remoteTypeList")List<Integer> remoteTypeList);

	List<Favorites> findFavoritesGoods(@Param("remoteType") Integer remoteType, @Param("remoteId") String remoteId);

}