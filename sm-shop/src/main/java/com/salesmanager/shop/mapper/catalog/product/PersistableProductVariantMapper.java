package com.salesmanager.shop.mapper.catalog.product;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.attribute.ProductOptionRepository;
import com.salesmanager.core.business.repositories.catalog.product.attribute.ProductOptionValueRepository;
import com.salesmanager.core.business.services.catalog.product.variant.ProductVariantGroupService;
import com.salesmanager.core.model.catalog.product.attribute.ProductOption;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValue;
import com.salesmanager.core.model.catalog.product.price.ProductPrice;
import com.salesmanager.core.model.catalog.product.variant.ProductVariantGroup;
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
import com.salesmanager.shop.model.catalog.product.product.variant.PersistableProductVariant;
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
	private ProductVariantGroupService productVariantGroupService;


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
		AtomicReference<ProductVariantGroup> productVariantGroupAtomicReference = new AtomicReference<>(new ProductVariantGroup());

		if (CollectionUtils.isNotEmpty(persistableVariations)) {
			List<PersistableVariation> persistableVariationList = source.getProductVariations();

			persistableVariationList.forEach(persistableVariation -> {
				ProductVariation productVariation = null;

				// 根据 variationId 查找
				if (persistableVariation.getVariationId() != null && persistableVariation.getVariationId() != 0) {
					productVariation = productVariationService.findOneById(persistableVariation.getVariationId())
							.orElseThrow(() -> new ServiceRuntimeException("Can't find ProductVariation id [" + persistableVariation.getVariationId() + "]"));

					// 根据 optionId 和 optionValueId 查找
				} else if (persistableVariation.getOptionId() != null && persistableVariation.getOptionValueId() != null) {
					productVariation = productVariationService.findByOptionAndValue(store.getId(),
							persistableVariation.getOptionId(), persistableVariation.getOptionValueId());

					if (productVariation == null) {
						try {
							productVariation = createProductVariationIds(persistableVariation, store);
						} catch (ServiceException e) {
							throw new RuntimeException(e);
						}
					}

					// 创建新的 ProductVariation
				} else {
					try {
						productVariation = createProductVariationIds(persistableVariation, store);
					} catch (ServiceException e) {
						throw new ServiceRuntimeException("createProductVariationIds error [" + JSON.toJSONString(persistableVariation) + "]");
					}
				}

				// 设置图片 URL 并添加到集合
				if (productVariation != null) {
					if (persistableVariation.getImageUrl() != null) {
						ProductVariantGroup productVariantGroup = new ProductVariantGroup();
						productVariantGroup.setProductVariation(productVariation);
						productVariantGroup.setImageUrl(persistableVariation.getImageUrl());
						productVariantGroupAtomicReference.set(productVariantGroup); // 更新带图片的变体
					}
					variations.add(productVariation);
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
		destination.setAlias(source.getAlias());
		destination.setSpecId(source.getSpecId());
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

		if (source.getId() != null && source.getId() !=0){
			ProductVariantGroup productVariantGroup = destination.getProductVariantGroup();
			productVariantGroup.setImageUrl(productVariantGroupAtomicReference.get() ==null? null: productVariantGroupAtomicReference.get().getImageUrl());
			try {
				productVariantGroupService.saveOrUpdate(productVariantGroup);
			} catch (ServiceException e) {
				throw new RuntimeException(e);
			}

		}else {
			try {
				ProductVariantGroup productVariantGroup = productVariantGroupAtomicReference.get();
				productVariantGroupService.saveOrUpdate(productVariantGroup);
				destination.setProductVariantGroup(productVariantGroup);
			} catch (ServiceException e) {
				throw new RuntimeException(e);
			}

		}
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
