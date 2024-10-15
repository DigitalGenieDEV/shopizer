package com.salesmanager.core.business.services.catalog.product.attribute;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.attribute.PageableProductOptionRepository2;
import com.salesmanager.core.business.repositories.catalog.product.attribute.ProductOptionValueRepository2;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.attribute.ProductOption;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOption;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOption2;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOptionValue;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOptionValue2;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;

@Service("productOptionService2")
public class ProductOptionServiceImpl2 extends SalesManagerEntityServiceImpl<Long, ProductOption>
		implements ProductOptionService2 {

	@Autowired
	private PageableProductOptionRepository2 pageableProductOptionRepository2;
	
	private ProductOptionValueRepository2 productOptionValueRepository2;
	
	@Inject
	public ProductOptionServiceImpl2(
			ProductOptionValueRepository2 productOptionValueRepository2) {
			super(productOptionValueRepository2);
			this.productOptionValueRepository2 = productOptionValueRepository2;
	}
	
	public Page<ReadProductOption> getListOption(MerchantStore store, Language language, String name, int categoryId,
			int page, int count) throws ServiceException {
		Validate.notNull(store, "MerchantStore cannot be null");
		Pageable p = PageRequest.of(page, count);
		return pageableProductOptionRepository2.listOptions(store.getId(), name, categoryId, language.getId(), p);
	}
	
	public List<ReadProductOptionValue>  getListOptionValues(MerchantStore store, Language language,  int setId, int categoryId) throws ServiceException{
		return productOptionValueRepository2.getListOptionValues(setId, categoryId, language.getId());
	}
	
	@Transactional
	public void deleteValues(Long setId, Long valueId)throws ServiceException{
		productOptionValueRepository2.deleteProductOptSet0ptValue(setId, valueId);
		productOptionValueRepository2.deleteProductOptionValueDescription(valueId);
		productOptionValueRepository2.deleteProductOptionValue(valueId);
	}
	
	@Transactional
	public void deleteOption(Long setId, Long optionId)  throws ServiceException {
		List<Long> valueList = productOptionValueRepository2.getOptSetOptValueList(setId);
		if(valueList.size() > 0 ) {
			for(Long valueId : valueList) {
				productOptionValueRepository2.deleteProductOptSet0ptValue(setId, valueId);
				productOptionValueRepository2.deleteProductOptionValueDescription(valueId);
				productOptionValueRepository2.deleteProductOptionValue(valueId);
			}
		}
		
		productOptionValueRepository2.deleteProductOptSet(setId, optionId);
		productOptionValueRepository2.deleteProductOptionDescription(optionId);
		productOptionValueRepository2.deleteProductOption(optionId);
	
		
	}
	
	public List<ReadProductOption2> getProductListOption(int code, int languageId, int categoryId, String division) throws ServiceException{
		return productOptionValueRepository2.getProductListOption( languageId, categoryId,division);
	}
	
	public List<ReadProductOptionValue2> getProductListOptionValue(int code, int languageId, int categoryId, String division) throws ServiceException{
		return productOptionValueRepository2.getProductListOptionValue(languageId, categoryId,division);
	}
	
	
}
