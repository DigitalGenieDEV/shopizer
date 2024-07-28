package com.salesmanager.core.model.merchant.library;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.core.model.common.EntityList;

public class StoreLibraryList extends EntityList {

	private static final long serialVersionUID = -3108842276158069739L;
	private List<StoreLibrary> storeLibraries = new ArrayList<StoreLibrary>();

	public List<StoreLibrary> getStoreLibraries() {
		return storeLibraries;
	}

	public void setStoreLibraries(List<StoreLibrary> storeLibraries) {
		this.storeLibraries = storeLibraries;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
