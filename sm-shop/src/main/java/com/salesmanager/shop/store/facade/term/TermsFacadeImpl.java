package com.salesmanager.shop.store.facade.term;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.terms.TermsService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.term.Terms;
import com.salesmanager.shop.model.term.PersistableTerms;
import com.salesmanager.shop.model.term.ReadableTerms;
import com.salesmanager.shop.populator.term.PersistableTermsPopulator;
import com.salesmanager.shop.populator.term.ReadableTermsPopulator;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.term.facade.TermsFacade;

import lombok.RequiredArgsConstructor;

@Service("termsFacade")
@RequiredArgsConstructor
public class TermsFacadeImpl implements TermsFacade {
	private final PersistableTermsPopulator persistableTermsPopulator;
	private final ReadableTermsPopulator readableTermsPopulator;
	private final TermsService termsService;

	@Override
	public ReadableTerms createTerms(
			PersistableTerms terms,
			MerchantStore store,
			Language language
	) {
		Terms target = convertToTerms(
				terms,
				store,
				language
		);
		saveTerms(target);
		ReadableTerms value = convertToReadableTerms(
				target,
				store,
				language
		);
		return value;
	}

	@Override
	public List<ReadableTerms> terms(
			MerchantStore store,
			Language language
	) {
		List<Terms> values = termsService.findAll();
		return values.stream().map(
				it -> convertToReadableTerms(
						it,
						store,
						language
				)
		).collect(Collectors.toList());
	}

	private void saveTerms(Terms terms) {
		try {
			termsService.save(terms);
		} catch (ServiceException exception) {
			throw new ServiceRuntimeException(exception);
		}

	}

	private Terms convertToTerms(
			PersistableTerms source,
			MerchantStore store,
			Language language
	) {
		Terms value = null;
		try {
			value = persistableTermsPopulator.populate(
					source,
					value,
					store,
					language
			);
		} catch (Exception e) {
			throw new ConversionRuntimeException("Error while populating PersistableTerms " + e.getMessage());
		}
		return value;
	}

	private ReadableTerms convertToReadableTerms(
			Terms source,
			MerchantStore store,
			Language language
	) {
		ReadableTerms value = null;
		try {
			value = readableTermsPopulator.populate(
					source,
					value,
					store,
					language
			);
		} catch (Exception e) {
			throw new ConversionRuntimeException("Error while populating ReadableTerms " + e.getMessage());
		}
		return value;
	}
}
