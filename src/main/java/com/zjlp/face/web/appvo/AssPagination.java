package com.zjlp.face.web.appvo;

import java.io.Serializable;
import java.util.List;

public class AssPagination<E> implements Serializable {

	/**
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 4323620712370057184L;
	
	private int totalRow = 0; // 共多少行
	private int start = 0;// 当前页起始行
	private int curPage = 1; // 当前页
	private int pageSize = 10; // 每页多少行
	
	private List<E> datas;//分页查询结果
	public int getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public List<E> getDatas() {
		return datas;
	}
	public void setDatas(List<E> datas) {
		this.datas = datas;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
