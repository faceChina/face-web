package com.zjlp.face.web.util.pattern;

import java.util.List;

public interface ElementSuit<T> {
	
	/**
	 * 添加子节点， 返回子节点
	 * @Title: addChild 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param child 子节点
	 * @return
	 * @date 2015年7月14日 下午4:43:50  
	 * @author lys
	 */
	ElementSuit<T> addChild(ElementSuit<T> child);
	
	/**
	 * 添加子节点（根据数据节点），返回子节点
	 * @Title: addChildByData 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param data 数据节点
	 * @return
	 * @date 2015年7月14日 下午4:44:04  
	 * @author lys
	 */
	ElementSuit<T> addChildByData(T data);
	
	/**
	 * 添加子节点，返回自身
	 * @Title: addChild 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param child 子节点
	 * @return
	 * @date 2015年7月14日 下午4:43:50  
	 * @author lys
	 */
	ElementSuit<T> add(ElementSuit<T> child);
	
	/**
	 * 添加子节点（根据数据节点），返回自身
	 * @Title: addChildByData 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param data 数据节点
	 * @return
	 * @date 2015年7月14日 下午4:44:04  
	 * @author lys
	 */
	ElementSuit<T> addByData(T data);
	
	/**
	 * 移除子节点
	 * @Title: removeChild 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @param child 子节点
	 * @return
	 * @date 2015年7月14日 下午4:45:02  
	 * @author lys
	 */
	ElementSuit<T> removeChild(ElementSuit<T> child);
	
	/**
	 * 获取当前节点的数据
	 * @Title: getData 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年7月14日 下午4:45:23  
	 * @author lys
	 */
	T getData();
	
	/**
	 * 获取所有的子节点
	 * @Title: getChilds 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年7月14日 下午4:45:38  
	 * @author lys
	 */
	List<ElementSuit<T>> getChilds();
	
	/**
	 * 是否有子节点
	 * @Title: hasChild 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年7月14日 下午5:43:03  
	 * @author lys
	 */
	Boolean hasChild();
	
	/**
	 * 是否根节点
	 * @Title: isRoot 
	 * @Description: (这里用一句话描述这个方法的作用) 
	 * @return
	 * @date 2015年9月26日 下午2:05:32  
	 * @author lys
	 */
	Boolean isRoot();

}
