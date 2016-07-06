package com.zjlp.face.web.server.user.shop.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.PickUpStoreMapper;
import com.zjlp.face.web.server.user.shop.dao.PickUpStoreDao;
import com.zjlp.face.web.server.user.shop.domain.PickUpStore;
import com.zjlp.face.web.server.user.shop.domain.dto.PickUpStoreDto;

@Repository("pickUpStoreDao")
public class PickUpStoreDaoImpl implements PickUpStoreDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public PickUpStore getByIdAndShopNo(PickUpStore store) {
		return sqlSession.getMapper(PickUpStoreMapper.class).selectByIdAndShopNo(store);
	}

	@Override
	public void removeById(Long id) {
		sqlSession.getMapper(PickUpStoreMapper.class).deleteByPrimaryKey(id);
	}

	@Override
	public List<PickUpStore> findList(String shopNo) {
		return sqlSession.getMapper(PickUpStoreMapper.class).selectListByShopNo(shopNo);
	}

	@Override
	public void editById(PickUpStore pickUpStore) {
		sqlSession.getMapper(PickUpStoreMapper.class).updateByPrimaryKeySelective(pickUpStore);
	}

	@Override
	public Long add(PickUpStore pickUpStore) {
		sqlSession.getMapper(PickUpStoreMapper.class).insertSelective(pickUpStore);
		return pickUpStore.getId();
	}

	@Override
	public Integer getZtCount(PickUpStoreDto dto) {
		return sqlSession.getMapper(PickUpStoreMapper.class).selectZtCount(dto);
	}

	@Override
	public List<PickUpStoreDto> findZtPage(PickUpStoreDto dto) {
		return sqlSession.getMapper(PickUpStoreMapper.class).selectZtPage(dto);
	}

}
