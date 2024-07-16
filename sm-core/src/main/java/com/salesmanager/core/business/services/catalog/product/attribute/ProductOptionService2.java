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

	Page<ReadProductOption> getListOption(MerchantStore store, Language language, String name, int categoryId, int page, int count) throws ServiceException;
	
	List<ReadProductOptionValue>  getListOptionValues(MerchantStore store, Language language,  int setId, int categoryId) throws ServiceException;
	
	void deleteValues(int setId, int valueId)throws ServiceException;
	
	void deleteOption(int setId, int optionId)  throws ServiceException;
	
	List<ReadProductOption2> getProductListOption(int code, int languageId, int categoryId, String division) throws ServiceException;
	
	List<ReadProductOptionValue2> getProductListOptionValue(int code, int languageId, int categoryId, String division) throws ServiceException;

}
