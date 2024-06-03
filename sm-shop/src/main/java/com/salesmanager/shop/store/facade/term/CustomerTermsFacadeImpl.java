package com.salesmanager.shop.store.facade.term;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.salesmanager.core.business.services.terms.CustomerTermsService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.term.CustomerTerms;
import com.salesmanager.shop.model.term.ReadableCustomerTerms;
import com.salesmanager.shop.populator.term.ReadableCustomerTermsPopulator;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;
import com.salesmanager.shop.store.controller.term.facade.CustomerTermsFacade;

import lombok.RequiredArgsConstructor;

@Service("customerTermsFacade")
@RequiredArgsConstructor
public class CustomerTermsFacadeImpl implements CustomerTermsFacade{
	
	private final ReadableCustomerTermsPopulator customerTermsPopulator;
	
	private final CustomerTermsService customerTermsService;
	
	@Override
	public List<ReadableCustomerTerms> findByCustomerId(Long id, MerchantStore store, Language language) {
		List<CustomerTerms> values = customerTermsService.findByCustomerId(id);
		return values.stream().map(
					it -> convertToReadable(it, store, language)
				).collect(Collectors.toList());
	}
	
	private ReadableCustomerTerms convertToReadable(CustomerTerms source, MerchantStore store, Language language) {
		ReadableCustomerTerms readable = new ReadableCustomerTerms();
		try {
			readable = customerTermsPopulator.populate(source, readable, store, language);
		} catch (Exception e) {
			throw new ConversionRuntimeException("Error while populating CustomerTerms " + e.getMessage());
		}
		return readable;	
	}
}
