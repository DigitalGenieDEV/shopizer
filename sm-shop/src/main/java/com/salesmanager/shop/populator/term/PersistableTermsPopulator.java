package com.salesmanager.shop.populator.term;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.term.Terms;
import com.salesmanager.shop.model.term.PersistableTerms;

@Component
public class PersistableTermsPopulator extends AbstractDataPopulator<PersistableTerms, Terms> {
	
	@Override
	public Terms populate(
			PersistableTerms source,
			Terms target,
			MerchantStore store,
			Language language
	) throws ConversionException {
		Validate.notNull(source, "Terms cannot be null");
		target = Terms.builder()
				.description(source.getDescription())
				.id(source.getId())
				.required(source.isRequired())
				.used(source.isUsed())
				.build();
		
		return target;
	}
	
	@Override
	protected Terms createTarget() {
		// TODO Auto-generated method stub
		return Terms.builder().build();
	}
}
