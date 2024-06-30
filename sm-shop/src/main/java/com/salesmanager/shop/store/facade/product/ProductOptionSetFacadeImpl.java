package com.salesmanager.shop.store.facade.product;

import java.util.List;
import java.util.stream.Collectors;

import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.attribute.OptionSetForSaleType;
import com.salesmanager.shop.mapper.catalog.ReadableCategoryMapper;
import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.store.controller.category.facade.CategoryFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionSetService;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionSet;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.catalog.PersistableProductOptionSetMapper;
import com.salesmanager.shop.mapper.catalog.ReadableProductOptionSetMapper;
import com.salesmanager.shop.model.catalog.product.attribute.optionset.PersistableProductOptionSet;
import com.salesmanager.shop.model.catalog.product.attribute.optionset.ReadableProductOptionSet;
import com.salesmanager.shop.model.catalog.product.type.ReadableProductType;
import com.salesmanager.shop.store.api.exception.OperationNotAllowedException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.product.facade.ProductOptionSetFacade;
import com.salesmanager.shop.store.controller.product.facade.ProductTypeFacade;

@Service
public class ProductOptionSetFacadeImpl implements ProductOptionSetFacade {

	@Autowired
	private PersistableProductOptionSetMapper persistableProductOptionSetMapper;

	@Autowired
	private ReadableProductOptionSetMapper readableProductOptionSetMapper;

	@Autowired
	private ReadableCategoryMapper readableCategoryMapper;

	@Autowired
	private ProductOptionSetService productOptionSetService;

	@Autowired
	private ProductTypeFacade productTypeFacade;;

	@Autowired
	private CategoryService categoryService;

	@Override
	public ReadableProductOptionSet get(Long id, MerchantStore store, Language language) {
		Validate.notNull(store, "MerchantStore cannot be null");
		Validate.notNull(language, "Language cannot be null");
		ProductOptionSet optionSet = productOptionSetService.getById(store, id, language);
		if (optionSet == null) {
			throw new ResourceNotFoundException(
					"ProductOptionSet not found for id [" + id + "] and store [" + store.getCode() + "]");
		}

		return readableProductOptionSetMapper.convert(optionSet, store, language);
	}

	@Override
	public List<ReadableProductOptionSet> list(MerchantStore store, Language language) {
		Validate.notNull(store, "MerchantStore cannot be null");
		Validate.notNull(language, "Language cannot be null");

		try {
			List<ProductOptionSet> optionSets = productOptionSetService.listByStore(store, language);
			return optionSets.stream().map(opt -> this.convert(opt, store, language)).collect(Collectors.toList());
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Exception while listing ProductOptionSet", e);
		}

	}

	private ReadableProductOptionSet convert(ProductOptionSet optionSet, MerchantStore store, Language language) {
		return readableProductOptionSetMapper.convert(optionSet, store, language);
	}

	@Override
	public void create(PersistableProductOptionSet optionSet, MerchantStore store, Language language) {
		Validate.notNull(store, "MerchantStore cannot be null");
		Validate.notNull(language, "Language cannot be null");
		Validate.notNull(optionSet, "PersistableProductOptionSet cannot be null");

		if (this.exists(optionSet.getCode(), store)) {
			throw new OperationNotAllowedException("Option set with code [" + optionSet.getCode() + "] already exist");
		}

		ProductOptionSet opt = persistableProductOptionSetMapper.convert(optionSet, store, language);
		try {
			opt.setStore(store);
			productOptionSetService.create(opt);
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Exception while creating ProductOptionSet", e);
		}

	}

	@Override
	public void update(Long id, PersistableProductOptionSet optionSet, MerchantStore store, Language language) {
		Validate.notNull(store, "MerchantStore cannot be null");
		Validate.notNull(language, "Language cannot be null");
		Validate.notNull(optionSet, "PersistableProductOptionSet cannot be null");

		ProductOptionSet opt = productOptionSetService.getById(store, id, language);
		if (opt == null) {
			throw new ResourceNotFoundException(
					"ProductOptionSet not found for id [" + id + "] and store [" + store.getCode() + "]");
		}

		optionSet.setId(id);
		optionSet.setCode(opt.getCode());
		ProductOptionSet model = persistableProductOptionSetMapper.convert(optionSet, store, language);
		try {
			model.setStore(store);
			productOptionSetService.save(model);
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Exception while creating ProductOptionSet", e);
		}

	}

	@Override
	public void delete(Long id, MerchantStore store) {
		Validate.notNull(store, "MerchantStore cannot be null");
		Validate.notNull(id, "id cannot be null");
		ProductOptionSet opt = productOptionSetService.getById(id);
		if (opt == null) {
			throw new ResourceNotFoundException(
					"ProductOptionSet not found for id [" + id + "] and store [" + store.getCode() + "]");
		}
		if (!opt.getStore().getCode().equals(store.getCode())) {
			throw new ResourceNotFoundException(
					"ProductOptionSet not found for id [" + id + "] and store [" + store.getCode() + "]");
		}
		try {
			productOptionSetService.delete(opt);
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Exception while deleting ProductOptionSet", e);
		}

	}

	@Override
	public List<ReadableCategory> getAnnouncementCategory(Language language) {
		List<Category> announcementCategory = productOptionSetService.getAnnouncementCategory(language);
		if (CollectionUtils.isEmpty(announcementCategory)){
			return null;
		}
		return announcementCategory.stream().map(category->{
			return readableCategoryMapper.convert(category, null, language);
		}).collect(Collectors.toList());
	}

	@Override
	public boolean exists(String code, MerchantStore store) {
		Validate.notNull(store, "MerchantStore cannot be null");
		Validate.notNull(code, "code cannot be null");
		ProductOptionSet optionSet = productOptionSetService.getCode(store, code);
		if (optionSet != null) {
			return true;
		}

		return false;
	}

	@Override
	public List<ReadableProductOptionSet> list(MerchantStore store, Language language, String type) {
		Validate.notNull(store, "MerchantStore cannot be null");
		Validate.notNull(language, "Language cannot be null");
		Validate.notNull(type, "Product type cannot be null");

		// find product type by id
		ReadableProductType readable = productTypeFacade.get(store, type, language);
		
		if(readable == null) {
			throw new ResourceNotFoundException("Can't fing product type [" + type + "] fpr merchand [" + store.getCode() +"]");
		}

		List<ProductOptionSet> optionSets = productOptionSetService.getByProductType(readable.getId(), store, language);
		return optionSets.stream().map(opt -> this.convert(opt, store, language)).collect(Collectors.toList());

	}

	@Override
	public List<ReadableProductOptionSet> listByCategoryId(Language language, Long categoryId, OptionSetForSaleType optionSetForSaleType) {
		Validate.notNull(language, "Language cannot be null");
		Validate.notNull(categoryId, "Category id cannot be null");
		Validate.notNull(optionSetForSaleType, "optionSetForSaleType cannot be null");

		// find product type by id
		Category category  = categoryService.findById(categoryId);

		if(category == null) {
			throw new ResourceNotFoundException("Can't fing category [" + categoryId + "]");
		}

		List<ProductOptionSet> optionSets = productOptionSetService.getByCategoryId(categoryId, language, optionSetForSaleType);
		return optionSets.stream().map(opt -> this.convert(opt, null, language)).collect(Collectors.toList());

	}

}
