package com.salesmanager.core.business.services.banner;

import java.util.List;

import org.springframework.data.domain.Page;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.banner.Banner;
import com.salesmanager.core.model.banner.ReadBanner;
import com.salesmanager.core.model.banner.ReadUserBanner;

public interface BannerService extends SalesManagerEntityService<Integer, Banner> {
	
	List<ReadUserBanner> getBannerUserList(String site) throws ServiceException;
	
	Page<ReadBanner> getBannerList(String site, String keyword, int page, int count) throws ServiceException;
	
	void saveOrUpdate(Banner banner) throws ServiceException;
	
	

}
