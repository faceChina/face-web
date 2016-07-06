package com.zjlp.face.web.server.user.favorites.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.mapper.FavoritesMapper;
import com.zjlp.face.web.server.user.favorites.domain.Favorites;
import com.zjlp.face.web.server.user.favorites.domain.dto.FavoritesDto;

@Repository
public class FavoritesDaoImpl implements FavoritesDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Long addFavorites(Favorites favorites) {
		sqlSession.getMapper(FavoritesMapper.class).insertSelective(favorites);
		return favorites.getId();
	}

	@Override
	public Integer countFavorites(Long userId, List<Integer> remoteTypes, Integer status) {
		return sqlSession.getMapper(FavoritesMapper.class).countFavorites(userId, remoteTypes, status);
	}

	@Override
	public List<FavoritesDto> findFavoritesPage(Pagination<FavoritesDto> pagination,
			Long userId, List<Integer> remoteTypes, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		map.put("userId", userId);
		map.put("remoteTypes", remoteTypes);
		map.put("status", status);
		return sqlSession.getMapper(FavoritesMapper.class).findFavoritesPage(map);
	}

	@Override
	public Favorites getFavorites(Long userId, Integer remoteType,
			String remoteId) {
		return sqlSession.getMapper(FavoritesMapper.class).getFavorites(userId, remoteType, remoteId);
	}

	@Override
	public void updateFavorites(Favorites favorites) {
		sqlSession.getMapper(FavoritesMapper.class).updateFavoriteByIdAndUserId(favorites);
	}

	@Override
	public void updateFavoritesStatusBatch(List<Long> id, Long userId,
			Integer status) {
		sqlSession.getMapper(FavoritesMapper.class).updateFavoritesStatusBatch(id, userId, status);
	}

	@Override
	public Favorites getById(Long id) {
		return sqlSession.getMapper(FavoritesMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public List<Favorites> findInvalideFavorite(Long userId, List<Integer> remoteTypeList) {
		return sqlSession.getMapper(FavoritesMapper.class).findInvalideFavorite(userId, remoteTypeList);
	}

	@Override
	public List<Favorites> findFavoritesGoods(Integer remoteType, String remoteId) {
		return this.sqlSession.getMapper(FavoritesMapper.class).findFavoritesGoods(remoteType, remoteId);
	}
	
}
