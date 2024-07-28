package com.salesmanager.shop.populator.storepropertyrightsfile;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.entity.ReadableList;
import com.salesmanager.shop.model.storepropertyrightsfile.ReadablePropertyRightsConfig;

public class ReadablePropertyRightsConfigList extends ReadableList {
	private static final long serialVersionUID = 1L;
	private List<ReadablePropertyRightsConfig> data = new ArrayList<ReadablePropertyRightsConfig>();

	public List<ReadablePropertyRightsConfig> getData() {
		return data;
	}

	public void setData(List<ReadablePropertyRightsConfig> data) {
		this.data = data;
	}

}
