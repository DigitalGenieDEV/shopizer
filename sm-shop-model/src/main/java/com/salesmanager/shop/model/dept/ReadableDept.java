package com.salesmanager.shop.model.dept;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class ReadableDept {
	private static final long serialVersionUID = 1L;

	private int id = 0;
	private int parentId = 0;
	private String deptCode = "";
	private String deptName = "";
	private String tel = "";
	private String content = "";
	private int ord = 0;
	private String deptNamePath = "";
	private int depth = 0;
	private int visible = 0;
	private ArrayList<ReadableDept> children = null;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public String getDeptNamePath() {
		return deptNamePath;
	}

	public void setDeptNamePath(String deptNamePath) {
		this.deptNamePath = deptNamePath;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}

	public ArrayList<ReadableDept> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<ReadableDept> children) {
		this.children = children;
	}


}
