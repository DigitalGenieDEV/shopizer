package com.salesmanager.shop.store.controller.term.facade;

import java.util.List;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.term.PersistableTerms;
import com.salesmanager.shop.model.term.ReadableTerms;

public interface TermsFacade {
	public ReadableTerms createTerms(PersistableTerms terms, MerchantStore store, Language language);
	public List<ReadableTerms> terms(MerchantStore store, Language language);
}
