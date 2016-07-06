package com.zjlp.face.web.server.social.businesscircle.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zjlp.face.util.page.Pagination;
import com.zjlp.face.web.mapper.AppCircleMsgMapper;
import com.zjlp.face.web.server.social.businesscircle.dao.AppCircleMsgDao;
import com.zjlp.face.web.server.social.businesscircle.domain.AppCircleMsg;
import com.zjlp.face.web.server.social.businesscircle.domain.vo.AppCircleMsgVo;

@Repository("appCircleMsgDao")
public class AppCircleMsgDaoImpl implements AppCircleMsgDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public Long add(AppCircleMsg appCircle) {
		 sqlSession.getMapper(AppCircleMsgMapper.class).insert(appCircle);
		 return appCircle.getId();
	}

	@Override
	public void delete(Long id) {
		sqlSession.getMapper(AppCircleMsgMapper.class).deleteByPrimaryKey(id);

	}

	@Override
	public AppCircleMsgVo getAppCircleById(Long id) {
		return sqlSession.getMapper(AppCircleMsgMapper.class).selectByPrimaryKey(id);
	}

	@Override
	public Pagination<AppCircleMsgVo> findAppCircleMsgVoByUserId(Long userId,Pagination<AppCircleMsgVo> pagination) {
		Integer totalRow = sqlSession.getMapper(AppCircleMsgMapper.class).getCount(userId);
		pagination.setTotalRow(totalRow);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		List<AppCircleMsgVo> list = sqlSession.getMapper(AppCircleMsgMapper.class).selectAppCircleMsgByUserId(map);
		pagination.setDatas(list);
		return pagination;
	}

	@Override
	public Pagination<AppCircleMsgVo> queryAppCircleMsgVo(String username,Long upMsgId,Long downMsgId,
			Pagination<AppCircleMsgVo> pagination) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		map.put("upMsgId", upMsgId);
		map.put("downMsgId", downMsgId);
		map.put("start", pagination.getStart());
		map.put("pageSize", pagination.getPageSize());
		Integer totalRow = sqlSession.getMapper(AppCircleMsgMapper.class).getCountByTimes(map);
		pagination.setTotalRow(totalRow);
		List<AppCircleMsgVo> list = sqlSession.getMapper(AppCircleMsgMapper.class).selectAppCircleMsgPage(map);
		pagination.setDatas(list);
		return pagination;
	}

	@Override
	public void edit(AppCircleMsg appCircle) {
		sqlSession.getMapper(AppCircleMsgMapper.class).update(appCircle);
	}
}
