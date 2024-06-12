package com.salesmanager.core.business.services.banner;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.banner.BannerRepository;
import com.salesmanager.core.business.repositories.banner.PageableBannerRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.banner.Banner;
import com.salesmanager.core.model.banner.ReadBanner;

@Service("bannerService")
public class BannerServiceImpl extends SalesManagerEntityServiceImpl<Integer, Banner> implements BannerService {

	@Inject
	private BannerRepository bannerRepository;

	@Inject
	private PageableBannerRepository pageableBannerRepository;

	@Inject
	public BannerServiceImpl(BannerRepository bannerRepository) {
		super(bannerRepository);
		this.bannerRepository = bannerRepository;
	}
	
	public Page<ReadBanner> getBannerList(String site, String keyword, int page, int count)
			throws ServiceException {
		Pageable pageRequest = PageRequest.of(page, count);
		return pageableBannerRepository.getBannerList(site, keyword, pageRequest);
	}
	
	public void saveOrUpdate(Banner banner) throws ServiceException {
		// save or update (persist and attach entities
		if (banner.getId() != null && banner.getId() > 0) {
			super.update(banner);
		} else {
			this.create(banner);
		}
	}

}
