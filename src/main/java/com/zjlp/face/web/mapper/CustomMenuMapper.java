package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.user.weixin.domain.CustomMenu;
import com.zjlp.face.web.server.user.weixin.domain.dto.CustomMenuDto;
import com.zjlp.face.web.server.user.weixin.domain.vo.CustomMenuVo;

public interface CustomMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CustomMenu record);

    int insertSelective(CustomMenu record);

    CustomMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CustomMenu record);

    int updateByPrimaryKey(CustomMenu record);

	List<CustomMenuVo> selectList(CustomMenuDto customMenuDto);

	void deleteCustomByShopNo(String shopNo);
}