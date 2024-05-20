package com.salesmanager.shop.model.manager;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.entity.ReadableList;

public class ReadableManagerGroupList extends ReadableList {

	private static final long serialVersionUID = 1L;

	private List<ReadableManagerGroup> data = new ArrayList<ReadableManagerGroup>();

	public List<ReadableManagerGroup> getData() {
		return data;
	}

	public void setData(List<ReadableManagerGroup> data) {
		this.data = data;
	}
}
