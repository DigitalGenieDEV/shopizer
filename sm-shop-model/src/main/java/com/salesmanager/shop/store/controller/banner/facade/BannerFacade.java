package com.salesmanager.shop.store.controller.banner.facade;

import com.salesmanager.shop.model.banner.PersistableBanner;
import com.salesmanager.shop.model.banner.ReadableBanner;
import com.salesmanager.shop.model.banner.ReadableBannerList;

public interface BannerFacade {

	ReadableBannerList getBannerList(String site, String keyword, int page, int count) throws Exception;

	PersistableBanner saveBanner(PersistableBanner banner) throws Exception;

	ReadableBanner getById(int id) throws Exception;
	
	void deleteBanner(int id) throws Exception;
}
