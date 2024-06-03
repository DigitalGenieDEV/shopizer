package com.salesmanager.shop.populator.term;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.term.Terms;
import com.salesmanager.shop.model.term.PersistableTerms;
import com.salesmanager.shop.model.term.ReadableTerms;
import com.salesmanager.shop.model.term.TermsEntity;

@Component
public class ReadableTermsPopulator extends AbstractDataPopulator<Terms, ReadableTerms> {

	@Override
	public ReadableTerms populate(
			Terms source,
			ReadableTerms target,
			MerchantStore store,
			Language language
	) throws ConversionException {
		Validate.notNull(
				source,
				"Terms cannot be null"
		);

		return new ReadableTerms(
				source.getId(),
				source.getDescription(),
				source.isRequired(),
				source.isUsed()
		);
	}

	@Override
	protected ReadableTerms createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
}
