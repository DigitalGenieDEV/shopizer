package com.salesmanager.shop.store.controller.product.facade;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.product.information.PersistableProductInformation;
import com.salesmanager.shop.model.catalog.product.product.information.ReadableProductInformation;

import springfox.documentation.annotations.ApiIgnore;

public interface ProductInformationFacade {

	ReadableProductInformation getList(MerchantStore merchantStore, Language language,  int page, int count, String division) throws Exception;
	
	PersistableProductInformation saveProductInformation(PersistableProductInformation data, MerchantStore merchantStore,  Language language) throws Exception;
	
	void deleteProductInformation(Long id) throws Exception;
}
