package com.salesmanager.core.business.services.banner;

import org.springframework.data.domain.Page;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.banner.Banner;
import com.salesmanager.core.model.banner.ReadBanner;

public interface BannerService extends SalesManagerEntityService<Integer, Banner> {
	
	Page<ReadBanner> getBannerList(String site, String keyword, int page, int count) throws ServiceException;
	
	void saveOrUpdate(Banner banner) throws ServiceException;
	
	

}
