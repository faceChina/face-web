package com.zjlp.face.web.server.user.weixin.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.web.mapper.CustomMenuMapper;
import com.zjlp.face.web.server.user.weixin.dao.CustomMenuDao;
import com.zjlp.face.web.server.user.weixin.domain.CustomMenu;
import com.zjlp.face.web.server.user.weixin.domain.dto.CustomMenuDto;
import com.zjlp.face.web.server.user.weixin.domain.vo.CustomMenuVo;

@Repository
public class CustomMenuDaoImpl implements CustomMenuDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void add(CustomMenu customMenu) {
		sqlSession.getMapper(CustomMenuMapper.class).insertSelective(customMenu);
	}

	@Override
	public void edit(CustomMenu customMenu) {
		sqlSession.getMapper(CustomMenuMapper.class).updateByPrimaryKeySelective(customMenu);
	}

	@Override
	public CustomMenu getCustomMenuById(Long id) {
		return sqlSession.getMapper(CustomMenuMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public List<CustomMenuVo> findCustomMenuVos(CustomMenuDto customMenuDto) {
		return sqlSession.getMapper(CustomMenuMapper.class).selectList(customMenuDto);
	}

	@Override
	public void deleteCustomByShopNo(String shopNo) {
		sqlSession.getMapper(CustomMenuMapper.class).deleteCustomByShopNo(shopNo);
	}

}
