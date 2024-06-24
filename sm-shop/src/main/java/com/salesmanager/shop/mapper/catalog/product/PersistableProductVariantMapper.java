package com.salesmanager.shop.mapper.catalog.product;

import java.math.BigDecimal;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.attribute.ProductOptionRepository;
import com.salesmanager.core.business.repositories.catalog.product.attribute.ProductOptionValueRepository;
import com.salesmanager.core.model.catalog.product.attribute.ProductOption;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValue;
import com.salesmanager.core.model.catalog.product.price.ProductPrice;
import com.salesmanager.shop.model.catalog.product.product.variant.PersistableVariation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.variation.ProductVariationService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import com.salesmanager.core.model.catalog.product.variation.ProductVariation;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.catalog.product.product.variant.PersistableProductVariant;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.utils.DateUtil;

@Component
public class PersistableProductVariantMapper  {
	
	@Autowired
	private ProductVariationService productVariationService;
	
	@Autowired
	private PersistableProductAvailabilityMapper persistableProductAvailabilityMapper;
	
	@Autowired
	private ProductService productService;


	@Autowired
	private ProductOptionRepository productOptionRepository;
	@Autowired
	private ProductOptionValueRepository productOptionValueRepository;


	public ProductVariant convert(PersistableProductVariant source, MerchantStore store, Language language, Product product) {
		ProductVariant productVariantModel = new ProductVariant();
		return this.merge(source, productVariantModel, store, language, product);
	}

	public ProductVariant merge(PersistableProductVariant source, ProductVariant destination, MerchantStore store,
			Language language, Product product) {

		List<PersistableVariation> persistableVariations = source.getProductVariations();

		if (CollectionUtils.isEmpty(persistableVariations)){
			throw new ServiceRuntimeException("persistableVariations cant null");
		}

		Set<ProductVariation> variations = new HashSet<>();


		if (CollectionUtils.isNotEmpty(persistableVariations)){
			List<PersistableVariation> persistableVariationList = source.getProductVariations();
			persistableVariationList.forEach(persistableVariation->{
				if (persistableVariation.getVariationId() != null && persistableVariation.getVariationId() != 0){
					Optional<ProductVariation> productVariationOptional = productVariationService.getById(store, persistableVariation.getVariationId());
					if (productVariationOptional.isPresent()){
						variations.add(productVariationOptional.get());
					}else {
						throw new ServiceRuntimeException("Cant find  ProductVariation id [" + persistableVariation.getVariationId() + "]");
					}
				}else if(persistableVariation.getOptionId() != null && persistableVariation.getOptionValueId() != null){
					ProductVariation byOptionAndValue = productVariationService.findByOptionAndValue(store.getId(),
							persistableVariation.getOptionId(), persistableVariation.getOptionValueId());
					if (byOptionAndValue != null){
						variations.add(byOptionAndValue);
					}else {
						try {
							ProductVariation productVariation = createProductVariationIds(persistableVariation, store);
							variations.add(productVariation);
						} catch (ServiceException e) {
							throw new RuntimeException(e);
						}
					}
				}else {
					try {
						ProductVariation productVariation = createProductVariationIds(persistableVariation, store);
						variations.add(productVariation);
					} catch (ServiceException e) {
						throw new ServiceRuntimeException("createProductVariationIds error [" + JSON.toJSONString(persistableVariation) + "]");
					}
				}

			});
		}

		destination.setVariations(variations);

		StringBuilder instanceCode = new StringBuilder();
		for (ProductVariation variation : variations) {
			instanceCode.append(variation.getCode()).append(";");
		}
		destination.setCode(instanceCode.substring(0, instanceCode.length() - 1)); // Remove trailing colon
		destination.setImageUrl(source.getImageUrl());
		destination.setAvailable(source.isAvailable());
		destination.setDefaultSelection(source.isDefaultSelection());
		destination.setSku(source.getSku());

		if (StringUtils.isBlank(source.getDateAvailable())) {
			source.setDateAvailable(DateUtil.formatDate(new Date()));
		}

		if (source.getDateAvailable() != null) {
			try {
				destination.setDateAvailable(DateUtil.getDate(source.getDateAvailable()));
			} catch (Exception e) {
				throw new ServiceRuntimeException("Cant format date [" + source.getDateAvailable() + "]");
			}
		}

		destination.setSortOrder(source.getSortOrder());

		if (source.getInventory() != null) {
			ProductAvailability availability = persistableProductAvailabilityMapper.convert(source.getInventory(), store, language);
			availability.setProductVariant(destination);
			destination.getAvailabilities().add(availability);

			Set<ProductPrice> prices = availability.getPrices();
			if (CollectionUtils.isNotEmpty(prices)){
				ProductPrice price = prices.stream().iterator().next();
				if (StringUtils.isNotEmpty(price.getPriceRangeList()) && product != null){
					product.setPriceRangeList(price.getPriceRangeList());
				}else {
					if (product !=null){
						BigDecimal defaultPrice = product.getPrice() == null ? new BigDecimal(Integer.MAX_VALUE) : product.getPrice();
						if (price.getProductPriceAmount().compareTo(defaultPrice) < 0) {
							product.setPrice(price.getProductPriceAmount());
						}
					}
				}
			}
		}

		destination.setProduct(product);
		return destination;

	}

	ProductVariation createProductVariationIds(PersistableVariation persistableVariation, MerchantStore store) throws ServiceException {
		Long optionId = persistableVariation.getOptionId();
		ProductOption productOption = productOptionRepository.findOne(optionId);
		Long optionValueId = persistableVariation.getOptionValueId();
		ProductOptionValue productOptionValue = productOptionValueRepository.findOne(optionValueId);
		ProductVariation productVariation = new ProductVariation();
		productVariation.setProductOption(productOption);
		productVariation.setProductOptionValue(productOptionValue);
		productVariation.setCode(productOption.getCode()+":"+productOptionValue.getCode());
		productVariation.setMerchantStore(store);
		productVariationService.saveOrUpdate(productVariation);
		return productVariation;
	}

}
