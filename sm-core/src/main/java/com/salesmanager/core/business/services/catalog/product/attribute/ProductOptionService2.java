package com.salesmanager.core.business.services.catalog.product.attribute;

import java.util.List;

import org.springframework.data.domain.Page;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.attribute.ProductOption;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOption;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOption2;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOptionValue;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOptionValue2;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;

public interface ProductOptionService2  extends SalesManagerEntityService<Long, ProductOption> {
	
	String getOptionCode() throws ServiceException;
	
	String getOptionValueCode() throws ServiceException;

	Page<ReadProductOption> getListOption(MerchantStore store, Language language, String name, int categoryId, int page, int count) throws ServiceException;
	
	List<ReadProductOptionValue>  getListOptionValues(MerchantStore store, Language language,  int setId, int categoryId) throws ServiceException;
	
	void deleteSetValues(Long setId, Long valueId)throws ServiceException;
	
	void deleteSetOption(Long setId, Long optionId)  throws ServiceException;
	
	List<ReadProductOption2> getProductListOption(int code, int languageId, int categoryId, String division) throws ServiceException;
	
	List<ReadProductOptionValue2> getProductListOptionValue(int code, int languageId, int categoryId, String division) throws ServiceException;
	
	int getOptionSet(MerchantStore store, Language language,  Long optionId) throws ServiceException;
	
	int getOptionSetValue(MerchantStore store, Language language,  Long valueId) throws ServiceException;
	
	int getOptionNameCount(MerchantStore store, Language language,  String name) throws ServiceException;
	
	int getOptionValueNameCount(MerchantStore store, Language language,  String name) throws ServiceException;
	
	List<ReadProductOption> getListOptionKeyword(MerchantStore store, Language language, String keyword, int categoryId) throws ServiceException;
	
	List<ReadProductOptionValue>  getListOptionKeywordValues(MerchantStore store, Language language,  int setId, int categoryId, String keyword) throws ServiceException;

	void insertOptionSetValue(Long setID, Long valueId)  throws ServiceException;
	
	void deleteProductAttribute(Long productId)  throws ServiceException;
}

