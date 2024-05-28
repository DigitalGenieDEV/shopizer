package com.salesmanager.shop.populator.privacy;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.privacy.Privacy;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.privacy.PersistablePrivacy;

@Component
public class PersistablePrivacyPopulator extends AbstractDataPopulator<PersistablePrivacy, Privacy> {
	
	
	public Privacy populate(PersistablePrivacy source, Privacy target)
			throws ConversionException {

		try {

			Validate.notNull(target, "Privacy target cannot be null");
			target.setId(source.getId());
			target.setTitle(source.getTitle());
			target.setDivision(source.getDivision());
			target.setContent(source.getContent());
			target.setVisible(source.getVisible());
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
	public Privacy populate(PersistablePrivacy source, Privacy target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Privacy createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

}
