package com.salesmanager.shop.mapper.catalog.product;


import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.salesmanager.shop.model.catalog.product.variation.ReadableProductVariation;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.variant.ProductVariantImage;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.mapper.catalog.ReadableProductVariationMapper;
import com.salesmanager.shop.mapper.inventory.ReadableInventoryMapper;
import com.salesmanager.shop.model.catalog.product.ReadableImage;
import com.salesmanager.shop.model.catalog.product.inventory.ReadableInventory;
import com.salesmanager.shop.model.catalog.product.product.variant.ReadableProductVariant;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.utils.DateUtil;
import com.salesmanager.shop.utils.ImageFilePath;

@Component
public class ReadableProductVariantMapper implements Mapper<ProductVariant, ReadableProductVariant> {

	
	@Autowired
	private ReadableProductVariationMapper readableProductVariationMapper;
	
	@Autowired
	private ReadableInventoryMapper readableInventoryMapper;
	
	@Inject
	@Qualifier("img")
	private ImageFilePath imagUtils;

	@Override
	public ReadableProductVariant convert(ProductVariant source, MerchantStore store, Language language) {
		ReadableProductVariant readableproductVariant = new ReadableProductVariant();
		return this.merge(source, readableproductVariant, store, language);
	}

	@Override
	public ReadableProductVariant merge(ProductVariant source, ReadableProductVariant destination,
			MerchantStore store, Language language) {
		Validate.notNull(source, "Product instance cannot be null");
		Validate.notNull(source.getProduct(), "Product cannot be null");
		
		if(destination == null) {
			destination = new ReadableProductVariant();
		}
		
		destination.setSortOrder(source.getSortOrder() != null ? source.getSortOrder().intValue():0);
		destination.setAvailable(source.isAvailable());
		destination.setDateAvailable(DateUtil.formatDate(source.getDateAvailable()));
		destination.setId(source.getId());
		destination.setDefaultSelection(source.isDefaultSelection());
		destination.setProductId(source.getProduct().getId());
		destination.setSku(source.getSku());
		destination.setSortOrder(source.getSortOrder());
		destination.setCode(source.getCode());
		destination.setSpecId(source.getSpecId());
		//get product
		Product baseProduct = source.getProduct();
		if(baseProduct == null) {
			throw new ResourceNotFoundException("Product instances do not include the parent product [" + destination.getSku() + "]");
		}
		
		destination.setProductShipeable(baseProduct.isProductShipeable());

		destination.setImageUrl(source.getImageUrl());

		if (source.getVariations() != null) {
			List<ReadableProductVariation> variations = source.getVariations().stream()
					.map(v -> readableProductVariationMapper.convert(v, store, language))
					.collect(Collectors.toList());
			destination.setVariations(variations);
		}


		if(source.getAvailabilities() != null) {
			List<ReadableInventory> inventories = source.getAvailabilities().stream().filter(Objects::nonNull).map(i -> readableInventoryMapper.convert(i, store, language)).collect(Collectors.toList());
			destination.setInventory(inventories);
		}
		
		return destination;
	}
	

}
