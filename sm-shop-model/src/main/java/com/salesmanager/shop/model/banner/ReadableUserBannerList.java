package com.salesmanager.shop.model.banner;

import java.util.ArrayList;
import java.util.List;

public class ReadableUserBannerList {
	/**
	  * 
	  */

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private List<BannerUserEntity> data = new ArrayList<BannerUserEntity>();

	public List<BannerUserEntity> getData() {
		return data;
	}

	public void setData(List<BannerUserEntity> data) {
		this.data = data;
	}
}
