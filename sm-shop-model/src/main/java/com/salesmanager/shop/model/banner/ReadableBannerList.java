package com.salesmanager.shop.model.banner;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.board.ReadableBoard;
import com.salesmanager.shop.model.entity.ReadableList;

public class ReadableBannerList extends ReadableList {
	/**
	  * 
	  */

	private static final long serialVersionUID = 1L;

	private List<BannerEntity> data = new ArrayList<BannerEntity>();

	public List<BannerEntity> getData() {
		return data;
	}

	public void setData(List<BannerEntity> data) {
		this.data = data;
	}

}
