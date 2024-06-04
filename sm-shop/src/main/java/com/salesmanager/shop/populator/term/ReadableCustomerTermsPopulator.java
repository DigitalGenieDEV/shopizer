package com.salesmanager.shop.populator.term;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.term.CustomerTerms;
import com.salesmanager.shop.model.term.ReadableCustomerTerms;

@Component
public class ReadableCustomerTermsPopulator extends AbstractDataPopulator<CustomerTerms, ReadableCustomerTerms> {

	@Override
	public ReadableCustomerTerms populate(
			CustomerTerms source,
			ReadableCustomerTerms target,
			MerchantStore store,
			Language language
	) throws ConversionException {
		Validate.notNull(
				source,
				"CustomerTerms cannot be null"
		);
		
		return new ReadableCustomerTerms(
				source.getId(),
				source.isConsented(),
				source.getExpiredDate(),
				source.getModifiedDate(),
				source.getPrivacyCode(),
				source.getPrivacyValue()
		);
	}

	@Override
	protected ReadableCustomerTerms createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
}
