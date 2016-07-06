package com.zjlp.face.web.job.init.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zjlp.face.web.exception.ext.AddressException;
import com.zjlp.face.web.server.user.user.dao.VaearDao;
import com.zjlp.face.web.server.user.user.domain.VArea;
import com.zjlp.face.web.server.user.user.domain.dto.VaearTree;

@Service("loadAddressJob")
public class LoadAddressJob {
	
	private static final String HAIWAI_CODE = "990000";

	@Autowired
	private VaearDao vaearDao;

	private static VaearTree vaearTree;

	private static VaearTree proviceVaear;

	public void init() {
		this.initVaearTree();
		this.initProviceVaear();
	}

	public static VaearTree getVaearTree() {
		return vaearTree;
	}

	public static VaearTree getProviceVaear() {
		return proviceVaear;
	}

	private void initVaearTree() throws AddressException {
		try {
			List<VArea> list = vaearDao.findAllByParentId(null);
			if (null != list && !list.isEmpty()) {
				vaearTree = VaearTree.init(list);
			}
			vaearTree.removeChildrenByCode(HAIWAI_CODE);
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}

	private void initProviceVaear() throws AddressException {
		try {
			List<VArea> list = vaearDao.findAllByParentId(VaearTree.CODE_FIRST);
			if (null != list && !list.isEmpty()) {
				proviceVaear = VaearTree.init(list);
			}
			proviceVaear.removeChildrenByCode(HAIWAI_CODE);
		} catch (Exception e) {
			throw new AddressException(e);
		}
	}
}
