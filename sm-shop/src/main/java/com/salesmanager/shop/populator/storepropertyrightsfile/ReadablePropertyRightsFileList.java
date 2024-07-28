package com.salesmanager.shop.populator.storepropertyrightsfile;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.entity.ReadableList;
import com.salesmanager.shop.model.storepropertyrightsfile.ReadablePropertyRightsFile;

public class ReadablePropertyRightsFileList extends ReadableList {
	private static final long serialVersionUID = 1L;
	private List<ReadablePropertyRightsFile> data = new ArrayList<ReadablePropertyRightsFile>();

	public List<ReadablePropertyRightsFile> getData() {
		return data;
	}

	public void setData(List<ReadablePropertyRightsFile> data) {
		this.data = data;
	}

}
