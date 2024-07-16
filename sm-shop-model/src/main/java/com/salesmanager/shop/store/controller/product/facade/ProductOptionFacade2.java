package com.salesmanager.shop.store.controller.product.facade;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.attribute.DeleteProductValue;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionList2;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionList3;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionValueList2;

public interface ProductOptionFacade2 {

	ReadableProductOptionList2 getListOption(MerchantStore store, Language language, String name, int categoryId,
			int page, int count) throws Exception;

	ReadableProductOptionValueList2 getListOptionValues(MerchantStore store, Language language, int setId,
			int categoryId) throws Exception;
	
	void deleteOption(DeleteProductValue delOption)  throws Exception;
	
	void deleteValues(DeleteProductValue delValue)  throws Exception;
	
	ReadableProductOptionList3 getProductListOption(MerchantStore store, Language language,  int categoryId, String division) throws Exception;
}
