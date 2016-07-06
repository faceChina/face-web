package com.zjlp.face.web.server.product.good.dao;

import java.util.List;

import com.zjlp.face.web.server.product.good.domain.Classification;

/**
 * 商品类目持久层接口
 * @ClassName: ClassificationDao
 * @Description: (这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2015年3月11日 下午1:50:49
 */
public interface ClassificationDao {

	void add(Classification classification);
	
	void edit(Classification classification);
	
	Classification getById(Long id);
	
	void delete(Long id);

	List<Classification> findClassificationByPid(Long pid,Integer level);
}
