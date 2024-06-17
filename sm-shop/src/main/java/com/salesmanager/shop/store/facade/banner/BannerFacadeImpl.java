package com.salesmanager.shop.store.facade.banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.banner.BannerService;
import com.salesmanager.core.model.banner.Banner;
import com.salesmanager.core.model.banner.ReadBanner;
import com.salesmanager.core.model.banner.ReadUserBanner;
import com.salesmanager.shop.model.banner.BannerEntity;
import com.salesmanager.shop.model.banner.BannerUserEntity;
import com.salesmanager.shop.model.banner.PersistableBanner;
import com.salesmanager.shop.model.banner.ReadableBanner;
import com.salesmanager.shop.model.banner.ReadableBannerList;
import com.salesmanager.shop.model.banner.ReadableUserBannerList;
import com.salesmanager.shop.populator.banner.PersistableBannerPopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.banner.facade.BannerFacade;
import com.salesmanager.shop.store.controller.content.facade.ContentFacade;

@Service
public class BannerFacadeImpl implements BannerFacade {

	@Inject
	private ContentFacade contentFacade;

	@Inject
	private BannerService bannerService;

	@Inject
	private PersistableBannerPopulator persistableBannerPopulator;

	@Inject
	private ObjectMapper objectMapper;

	public ReadableUserBannerList getBannerUserList(String site) throws Exception {
		try {
			ReadableUserBannerList returnList = new ReadableUserBannerList();
			List<BannerUserEntity> targetList = new ArrayList<BannerUserEntity>();
			List<ReadUserBanner> dataList = bannerService.getBannerUserList(site);

			if (dataList.size() > 0) {
				for (ReadUserBanner data : dataList) {
					BannerUserEntity targetData = objectMapper.convertValue(data, BannerUserEntity.class);
					targetList.add(targetData);
				}
			}
			returnList.setData(targetList);
			return returnList;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException(e);
		}

	}

	public ReadableBannerList getBannerList(String site, String keyword, int page, int count) throws Exception {
		try {
			List<ReadBanner> banner = null;
			List<BannerEntity> targetList = new ArrayList<BannerEntity>();
			ReadableBannerList returnList = new ReadableBannerList();
			Page<ReadBanner> pageable = bannerService.getBannerList(site, keyword, page, count);
			banner = pageable.getContent();
			returnList.setRecordsTotal(pageable.getTotalElements());
			returnList.setTotalPages(pageable.getTotalPages());
			returnList.setNumber(banner.size());
			if (banner.size() > 0) {
				for (ReadBanner data : banner) {
					objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
					BannerEntity targetData = objectMapper.convertValue(data, BannerEntity.class);
					targetList.add(targetData);

				}
			}
			returnList.setData(targetList);
			return returnList;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException(e);
		}
	}

	public PersistableBanner saveBanner(PersistableBanner banner) throws Exception {
		try {

			int bannerId = banner.getId();
			Banner target = Optional.ofNullable(bannerId).filter(id -> id > 0).map(bannerService::getById)
					.orElse(new Banner());

			Banner dbBanner = populateBanner(banner, target);
			bannerService.saveOrUpdate(dbBanner);

			return banner;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while updating Banner", e);
		}
	}

	private Banner populateBanner(PersistableBanner banner, Banner target) {
		try {
			return persistableBannerPopulator.populate(banner, target);
		} catch (ConversionException e) {
			throw new ServiceRuntimeException(e);
		}
	}

	public ReadableBanner getById(int id) throws Exception {
		Banner data = bannerService.getById(id);
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		ReadableBanner targetData = objectMapper.convertValue(data, ReadableBanner.class);
		return targetData;
	}

	public void deleteBanner(int id) throws Exception {
		Banner banner = new Banner();
		banner.setId(id);
		bannerService.delete(banner);
	}
}