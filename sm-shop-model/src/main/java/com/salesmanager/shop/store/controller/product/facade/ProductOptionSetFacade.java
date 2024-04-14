package com.salesmanager.shop.store.controller.product.facade;

import java.util.List;

import com.salesmanager.core.model.catalog.product.attribute.OptionSetForSaleType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.attribute.optionset.PersistableProductOptionSet;
import com.salesmanager.shop.model.catalog.product.attribute.optionset.ReadableProductOptionSet;

public interface ProductOptionSetFacade {
	
	
	ReadableProductOptionSet get(Long id, MerchantStore store, Language language);
	boolean exists(String code, MerchantStore store);
	List<ReadableProductOptionSet> list(MerchantStore store, Language language);
	List<ReadableProductOptionSet> list(MerchantStore store, Language language, String type);
	void create(PersistableProductOptionSet optionSet, MerchantStore store, Language language);
	void update(Long id, PersistableProductOptionSet optionSet, MerchantStore store, Language language);
	void delete(Long id, MerchantStore store);


	/**
	 * Get property set by category
	 * @param language
	 * @param categoryId
	 * @param optionSetForSaleType
	 * @return
	 */
	List<ReadableProductOptionSet> listByCategoryId(Language language, Long categoryId, OptionSetForSaleType optionSetForSaleType);

}
