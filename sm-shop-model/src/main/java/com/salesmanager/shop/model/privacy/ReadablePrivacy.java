package com.salesmanager.shop.model.privacy;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.entity.ReadableList;

public class ReadablePrivacy extends ReadableList {

	private static final long serialVersionUID = 1L;

	private List<PrivacyEntity> data = new ArrayList<PrivacyEntity>();

	public List<PrivacyEntity> getData() {
		return data;
	}

	public void setData(List<PrivacyEntity> data) {
		this.data = data;
	}
	
}
