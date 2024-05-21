package com.salesmanager.core.model.manager;

import java.util.ArrayList;

public class ReadableCategoryAuth {
	private int grpId = 0;
	private int id = 0;
	private int parentId = 0;
	private String categoryName = "";
	private String categoryNamePath = "";
	private String lineage = "";
	private int depth = 0;
	private ArrayList<ReadableCategoryAuth> children = null;

	public int getGrpId() {
		return grpId;
	}

	public void setGrpId(int grpId) {
		this.grpId = grpId;
	}

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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryNamePath() {
		return categoryNamePath;
	}

	public void setCategoryNamePath(String categoryNamePath) {
		this.categoryNamePath = categoryNamePath;
	}

	public String getLineage() {
		return lineage;
	}

	public void setLineage(String lineage) {
		this.lineage = lineage;
	}

	public ArrayList<ReadableCategoryAuth> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<ReadableCategoryAuth> children) {
		this.children = children;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

}
