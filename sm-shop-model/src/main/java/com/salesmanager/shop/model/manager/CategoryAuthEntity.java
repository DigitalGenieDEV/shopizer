package com.salesmanager.shop.model.manager;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@SuppressWarnings("unused")
@JsonInclude(Include.NON_NULL)
public class CategoryAuthEntity {

	private static final long serialVersionUID = 1L;

	private int grpId = 0;
	private int id = 0;
	private int parentId = 0;
	private String categoryName = "";
	private String categoryPathName = "";
	private String lineage = "";

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

	public String getCategoryPathName() {
		return categoryPathName;
	}

	public void setCategoryPathName(String categoryPathName) {
		this.categoryPathName = categoryPathName;
	}

	public String getLineage() {
		return lineage;
	}

	public void setLineage(String lineage) {
		this.lineage = lineage;
	}

}
