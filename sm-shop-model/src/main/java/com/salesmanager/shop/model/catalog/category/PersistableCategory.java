package com.salesmanager.shop.model.catalog.category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersistableCategory extends CategoryEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CategoryDescription> descriptions;//always persist description
	private List<PersistableCategory> children = new ArrayList<PersistableCategory>();

	private Long adminCategoryId;

	/**
	 * admin/user
	 */
	private String categoryType;

	public List<CategoryDescription> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(List<CategoryDescription> descriptions) {
		this.descriptions = descriptions;
	}
	public List<PersistableCategory> getChildren() {
		return children;
	}
	public void setChildren(List<PersistableCategory> children) {
		this.children = children;
	}

	public Long getAdminCategoryId() {
		return adminCategoryId;
	}

	public void setAdminCategoryId(Long adminCategoryId) {
		this.adminCategoryId = adminCategoryId;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
}
