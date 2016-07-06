package com.zjlp.face.web.util.pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合类
 * @ClassName: AbstractElementSuit 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年7月14日 下午5:38:04 
 * @param <T> 元数据类型
 */
public abstract class AbstractElementSuit<T> implements ElementSuit<T> {

	protected static final Integer ROOT = 0;
	//元数据
	protected T data;
	//子节点集合
	protected List<ElementSuit<T>> childs;
	//当前节点等级 0->1->2....
	protected Integer level = ROOT;
	
	
	public AbstractElementSuit(T data){
		if (null == data) {
			throw new RuntimeException("ElementSuit's data is null, can't init an element.");
		}
		this.data = data;
	}

	@Override
	public ElementSuit<T> addChild(ElementSuit<T> child) {
		if (null != child) {
			if (null == childs) {
				childs = new ArrayList<ElementSuit<T>>();
			}
			//等级
			((AbstractElementSuit<T>)child).level = level + 1;
			childs.add(child);
		}
		return child;
	}

	@Override
	public ElementSuit<T> addChildByData(T data) {
		return this.addChild(cover(data));
	}
	
	@Override
	public ElementSuit<T> add(ElementSuit<T> child) {
		this.addChild(child);
		return this;
	}

	@Override
	public ElementSuit<T> addByData(T data) {
		this.addChild(cover(data));
		return this;
	}

	protected abstract ElementSuit<T> cover(T data);

	@Override
	public ElementSuit<T> removeChild(ElementSuit<T> child) {
		childs.remove(child);
		return this;
	}

	@Override
	public T getData() {
		return data;
	}

	@Override
	public List<ElementSuit<T>> getChilds() {
		return childs;
	}

	@Override
	public Boolean hasChild() {
		return null != childs && !childs.isEmpty();
	}
	
	public Integer getLevel() {
		return level;
	}

	public List<T> listDatas(){
		List<T> list = new ArrayList<T>(5);
		this.addForWhile(list, this);
		return list;
	}

	private void addForWhile(List<T> list, ElementSuit<T> element) {
		list.add(element.getData());
		if (element.hasChild()) {
			for (ElementSuit<T> child : element.getChilds()) {
				this.addForWhile(list, child);
			}
		}
	}

	@Override
	public Boolean isRoot() {
		return ROOT.equals(level);
	}
}
