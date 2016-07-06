package com.zjlp.face.web.server.operation.subbranch.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 代理关系组合
 * @ClassName: SubbranchSuit 
 * @Description: (这里用一句话描述这个类的作用) 
 * @author lys
 * @date 2015年7月13日 上午11:05:07
 */
public class SubbranchSuit {

	public static final Long ROOT_TYPE = 0L;
	
	private boolean isRoot = false;
	
	private Subbranch data;
	
	protected List<SubbranchSuit> childs;
	
	public SubbranchSuit(Subbranch data){
		if (null == data) {
			throw new RuntimeException("SubbranchSuit data is null, can't init an element.");
		}
		this.data = data;
		if (ROOT_TYPE.equals(data.getPid())) {
			isRoot = true;
		}
	}
	
	public SubbranchSuit addChild(SubbranchSuit child){
		if (null == childs) {
			childs = new ArrayList<SubbranchSuit>();
		}
		childs.add(child);
		return child;
	}
	
	public SubbranchSuit addChild(Subbranch data){
		return this.addChild(new SubbranchSuit(data));
	}
	
	public SubbranchSuit addList(List<Subbranch> datas){
		if (null != datas && !datas.isEmpty()) {
			for (Subbranch data : datas) {
				this.addChild(data);
			}
		}
		return this;
	}
	
	public SubbranchSuit remove(SubbranchSuit child) {
		if (null != childs && !childs.isEmpty()) {
			childs.remove(child);
		}
		return this;
	}
	
	public List<Subbranch> coverToList(){
		List<Subbranch> list  = new ArrayList<Subbranch>();
		this.addToList(list, this);
		return list;
	}
	
	private List<Subbranch> addToList(List<Subbranch> list, SubbranchSuit suit) {
		if (null == suit) return list;
		list.add(suit.data);
		if (null != suit.childs && !suit.childs.isEmpty()) {
			for (SubbranchSuit elementSuit : suit.childs) {
				addToList(list, elementSuit);
			}
		}
		return list;
	}

	public Subbranch getData() {
		return data;
	}

	public List<? extends SubbranchSuit> getChilds() {
		return childs;
	}
	public boolean isRoot() {
		return isRoot;
	}
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
}
