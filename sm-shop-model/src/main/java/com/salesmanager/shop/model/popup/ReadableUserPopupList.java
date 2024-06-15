package com.salesmanager.shop.model.popup;

import java.util.ArrayList;
import java.util.List;

public class ReadableUserPopupList {
	/**
	  * 
	  */

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private List<PopupUserEntity> data = new ArrayList<PopupUserEntity>();

	public List<PopupUserEntity> getData() {
		return data;
	}

	public void setData(List<PopupUserEntity> data) {
		this.data = data;
	}
}
