package com.salesmanager.shop.model.popup;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.banner.BannerEntity;
import com.salesmanager.shop.model.entity.ReadableList;

public class ReadablePopupList  extends ReadableList {
	/**
	  * 
	  */

	private static final long serialVersionUID = 1L;

	private List<PopupEntity> data = new ArrayList<PopupEntity>();

	public List<PopupEntity> getData() {
		return data;
	}

	public void setData(List<PopupEntity> data) {
		this.data = data;
	}
}
