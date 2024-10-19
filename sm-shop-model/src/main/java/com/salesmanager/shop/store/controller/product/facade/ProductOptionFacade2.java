package com.salesmanager.shop.store.controller.product.facade;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.attribute.DeleteProductValue;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductOptionSetValueEntity;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionList2;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionList3;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionValueList2;

public interface ProductOptionFacade2 {

	ReadableProductOptionList2 getListOption(MerchantStore store, Language language, String name, int categoryId,
			int page, int count) throws Exception;

	ReadableProductOptionValueList2 getListOptionValues(MerchantStore store, Language language, int setId,
			int categoryId) throws Exception;
	
	void deleteSetOption(DeleteProductValue delOption)  throws Exception;
	
	void deleteSetValues(DeleteProductValue delValue)  throws Exception;
	
	ReadableProductOptionList3 getProductListOption(MerchantStore store, Language language,  int categoryId, String division) throws Exception;
	
	int getOptionSet(MerchantStore store, Language language,  Long optionId) throws Exception;
	
	int getOptionSetValue(MerchantStore store, Language language,  Long valueId) throws Exception;
	
	int getOptionNameCount(MerchantStore store, Language language,  String name) throws Exception;
	
	int getOptionValueNameCount(MerchantStore store, Language language,  String name) throws Exception;
	
	ReadableProductOptionList2 getListOptionKeyword(MerchantStore store, Language language, String keyword, int categoryId) throws Exception;
	
	void insertOptionSetValue(PersistableProductOptionSetValueEntity data)  throws Exception;
	
	ReadableProductOptionValueList2 getListOptionKeywordValues(MerchantStore store, Language language, String keyword, int categoryId, Integer setId) throws Exception;
}
