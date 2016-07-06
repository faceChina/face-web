package com.zjlp.face.web.mapper;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.GoodImg;
import com.zjlp.face.web.server.product.good.domain.vo.GoodImgVo;

public interface GoodImgMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GoodImg record);

    int insertSelective(GoodImg record);

    GoodImg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GoodImg record);

    int updateByPrimaryKey(GoodImg record);

	void deleteByGoodId(Long goodId);

	GoodImg selectFirst(Long goodId);

	List<GoodImg> selectByGoodIdAndType(GoodImg goodImg);

	List<GoodImgVo> selectZoomByGoodIdAndType(GoodImg goodImg);
}