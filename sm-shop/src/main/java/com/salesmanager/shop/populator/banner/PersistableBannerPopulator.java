package com.salesmanager.shop.populator.banner;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.banner.Banner;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.banner.PersistableBanner;


@Component
public class PersistableBannerPopulator extends AbstractDataPopulator<PersistableBanner, Banner> {
	public Banner populate(PersistableBanner source, Banner target)
			throws ConversionException {

		try {
			
			Validate.notNull(target, "Banner target cannot be null");
			target.setId(source.getId());
			target.setName(source.getName());
			target.setImage(source.getImage());
			target.setAlt(source.getAlt());
			target.setSite(source.getSite());
			target.setPosition(source.getPosition());
			target.setSdate(source.getSdate());
			target.setEdate(source.getEdate());
			target.setTarget(source.getTarget());
			target.setOrd(source.getOrd());
			target.setVisible(source.getVisible());
			target.setUrl(source.getUrl());
			target.getAuditSection().setRegId(source.getUserId());
			target.getAuditSection().setRegIp(source.getUserIp());
			target.getAuditSection().setModId(source.getUserId());
			target.getAuditSection().setModIp(source.getUserIp());
		
			return target;


		} catch(Exception e) {
			throw new ConversionException(e);
		}

	}

	@Override
	public Banner populate(PersistableBanner source, Banner target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Banner createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
}
