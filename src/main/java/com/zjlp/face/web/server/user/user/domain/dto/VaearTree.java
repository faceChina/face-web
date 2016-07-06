package com.zjlp.face.web.server.user.user.domain.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zjlp.face.web.server.user.user.domain.VArea;

public class VaearTree extends VArea {

	private static final long serialVersionUID = 3210713596021721036L;

	public static final Integer CODE_ROOT = 0;

	public static final String CODE_FIRST = "1";

	private List<VaearTree> children = new ArrayList<VaearTree>();

	public VaearTree() {
	}

	public VaearTree(VArea vaear) {
		this.setCode(vaear.getCode());
		this.setParentId(vaear.getParentId());
		this.setPinyin(vaear.getPinyin());
		this.setArea(vaear.getArea());
	}

	public VaearTree(Integer code) {
		this.setCode(code);
	}

	public List<VaearTree> getChildren() {
		return children;
	}

	public void setChildren(List<VaearTree> children) {
		this.children = children;
	}

	public void addChildren(VaearTree vaearTree) {
		this.children.add(vaearTree);
	}

	public void addChildren(VArea vaear) {
		this.children.add(new VaearTree(vaear));
	}

	public List<VaearTree> getChildrenByParentId(Integer code) {
		VaearTree parent = this.getByCode(code);
		return parent.children;
	}
	
	public VaearTree removeChildrenByCode(Integer code) {
		if (null == code || null == this.children 
				|| this.children.isEmpty()) {
			return null;
		}
		for (VaearTree vaearTree : children) {
			if (null == vaearTree) {
				continue;
			}
			if (code.equals(vaearTree.getCode())){
				children.remove(vaearTree);
				return vaearTree;
			}
		}
		return null;
	}
	
	public VaearTree removeChildrenByCode(String code){
		if (StringUtils.isBlank(code)) {
			return null;
		}
		return this.removeChildrenByCode(Integer.valueOf(code));
	}
	
	public static List<VaearTree> getInherits(Integer code, VaearTree tree) {
		if (null == tree) {
			return null;
		}
		List<VaearTree> list = new LinkedList<VaearTree>();
		VaearTree vaearTree = tree.getByCode(code);
		while (null != vaearTree) {
			list.add(0, vaearTree);
			vaearTree = tree.getByCode(vaearTree.getParentId());
		}
		return list;
	}

	public VaearTree getByCode(Integer code) {
		if (null == code)
			return null;
		if (code.equals(this.getCode())) {
			return this;
		}
		if (null == children) {
			return null;
		}
		VaearTree result = null;
		for (VaearTree vaearTree : children) {
			result = vaearTree.getByCode(code);
			if (null != result)
				break;
		}
		return result;
	}

	public VaearTree getByCode(String code) {
		return this.getByCode(Integer.valueOf(code));
	}

	public String getAreaByCode(String code) {
		VaearTree node = this.getByCode(code);
		if (null == node) {
			return null;
		}
		return node.getArea();
	}

	public static VaearTree init(List<VArea> list) {
		VaearTree vaearTree = new VaearTree(VaearTree.CODE_ROOT);
		Map<String, List<VaearTree>> map = new HashMap<String, List<VaearTree>>();
		for (VArea vArea : list) {
			if (null == vArea)
				continue;
			if (VaearTree.CODE_FIRST.equals(vArea.getParentId())) {
				vaearTree.addChildren(new VaearTree(vArea));
				continue;
			}
			if (map.containsKey(vArea.getParentId())) {
				map.get(vArea.getParentId()).add(new VaearTree(vArea));
			} else {
				map.put(vArea.getParentId(), new ArrayList<VaearTree>());
				map.get(vArea.getParentId()).add(new VaearTree(vArea));
			}
		}
		for (VaearTree tree : vaearTree.getChildren()) {
			add(tree, map);
		}
		return vaearTree;
	}

	private static void add(VaearTree parent, Map<String, List<VaearTree>> map) {
		List<VaearTree> list = null;
		if (null != (list = map.get(String.valueOf(parent.getCode())))) {
			for (VaearTree child : list) {
				parent.addChildren(child);
				add(child, map);
			}
		}
	}
}
