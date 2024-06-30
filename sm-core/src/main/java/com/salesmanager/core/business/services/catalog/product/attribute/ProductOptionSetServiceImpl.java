package com.salesmanager.core.business.services.catalog.product.attribute;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.attribute.OptionSetForSaleType;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.attribute.ProductOptionSetRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionSet;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import org.springframework.util.CollectionUtils;

@Service("productOptionSetService")
public class ProductOptionSetServiceImpl extends
		SalesManagerEntityServiceImpl<Long, ProductOptionSet> implements ProductOptionSetService {

	
	private ProductOptionSetRepository productOptionSetRepository;
	

	@Inject
	public ProductOptionSetServiceImpl(
			ProductOptionSetRepository productOptionSetRepository) {
			super(productOptionSetRepository);
			this.productOptionSetRepository = productOptionSetRepository;
	}


	@Override
	public List<ProductOptionSet> listByStore(MerchantStore store, Language language) throws ServiceException {
		return productOptionSetRepository.findByStore(store.getId(), language.getId());
	}


	@Override
	public ProductOptionSet getById(MerchantStore store, Long optionSetId, Language lang) {
		return productOptionSetRepository.findOne(store.getId(), optionSetId, lang.getId());
	}


	@Override
	public ProductOptionSet getCode(MerchantStore store, String code) {
		return productOptionSetRepository.findByCode(store.getId(), code);
	}


	@Override
	public List<ProductOptionSet> getByProductType(Long productTypeId, MerchantStore store, Language lang) {
		return productOptionSetRepository.findByProductType(productTypeId, store.getId(), lang.getId());
	}

	@Override
	public List<Category> getAnnouncementCategory(Language language) {
		List<ProductOptionSet> productOptionSet =  productOptionSetRepository.findByOptionSetForSaleType(language.getId(), OptionSetForSaleType.ANNOUNCEMENT.name());
		if (CollectionUtils.isEmpty(productOptionSet)){
			return null;
		}
		return productOptionSet.stream().map(ProductOptionSet::getCategory).collect(Collectors.toList());
	}


	@Override
	public List<ProductOptionSet> getByCategoryId(Long categoryId, Language lang, OptionSetForSaleType optionSetForSaleType) {
		return productOptionSetRepository.findByCategoryId(categoryId, lang.getId(), optionSetForSaleType.name());
	}


}
