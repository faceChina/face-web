package com.zjlp.face.web.server.product.material.service.impl;
/**
 * 关联集合表统一添加类
 */
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjlp.face.web.exception.ext.GroupListException;
import com.zjlp.face.web.server.product.material.dao.GroupListDao;
import com.zjlp.face.web.server.product.material.domain.GroupList;
import com.zjlp.face.web.server.product.material.service.GroupListService;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class GroupListServiceImpl implements GroupListService {

	@Autowired
	private GroupListDao groupListDao;
	
	@Override
	public List<GroupList> findByGroupId(Long groupId) {
		return groupListDao.findByGroupId(groupId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Long addGroupList(List<GroupList> groupList) throws GroupListException{
		try {
			Long groupId = groupListDao.findMaxGroupId()+1;
			for (GroupList gl : groupList) {
				if(null == gl.getPartId() || StringUtils.isBlank(gl.getAssociationTable())){
					throw new GroupListException("参数异常");
				}
				gl.setGroupId(groupId);
				groupListDao.addGroupList(gl);
			}
			return groupId;
		} catch (Exception e) {
			throw new GroupListException("添加GROUPLIST数据异常",e);
		}

	}


}
