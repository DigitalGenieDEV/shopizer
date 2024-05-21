package com.salesmanager.shop.model.manager;

import java.util.ArrayList;

public class PersistableManagerAuthList {
	private ArrayList<ManagerAuthEntity> data = null;

	private ArrayList<ManagerAuthCategoryEntity> categoryData = null;

	public ArrayList<ManagerAuthEntity> getData() {
		return data;
	}

	public void setData(ArrayList<ManagerAuthEntity> data) {
		this.data = data;
	}

	public ArrayList<ManagerAuthCategoryEntity> getCategoryData() {
		return categoryData;
	}

	public void setCategoryData(ArrayList<ManagerAuthCategoryEntity> categoryData) {
		this.categoryData = categoryData;
	}

}
