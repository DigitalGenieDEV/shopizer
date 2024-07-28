package com.salesmanager.shop.populator.storelibrary;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.core.model.merchant.library.StoreLibrary;
import com.salesmanager.shop.model.entity.ReadableList;
import com.salesmanager.shop.model.storelibrary.ReadableStoreLibrary;

public class ReadableStoreLibraryList extends ReadableList {
	private static final long serialVersionUID = 1L;
	private List<ReadableStoreLibrary> data = new ArrayList<ReadableStoreLibrary>();

	public List<ReadableStoreLibrary> getData() {
		return data;
	}

	public void setData(List<ReadableStoreLibrary> data) {
		this.data = data;
	}
}
