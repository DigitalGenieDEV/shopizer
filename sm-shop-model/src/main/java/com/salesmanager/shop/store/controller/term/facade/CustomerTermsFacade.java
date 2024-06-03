package com.salesmanager.shop.store.controller.term.facade;

import java.util.List;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.term.PersistableTerms;
import com.salesmanager.shop.model.term.ReadableCustomerTerms;
import com.salesmanager.shop.model.term.ReadableTerms;

/**
 * Access to all methods for managing customer terms
 * 
 * @author BE
 *
 */
public interface CustomerTermsFacade {
	public List<ReadableCustomerTerms> findByCustomerId(Long id, MerchantStore store, Language language);
}
