package com.salesmanager.shop.mapper.catalog.product;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.erp.ProductMaterialService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.ProductMaterial;
import com.salesmanager.core.model.catalog.product.SellerProductShippingTextInfo;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionDescription;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValue;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValueDescription;
import com.salesmanager.core.model.catalog.product.attribute.*;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.description.ProductDescription;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.catalog.product.price.ProductPrice;
import com.salesmanager.core.model.catalog.product.price.ProductPriceDescription;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import com.salesmanager.core.model.catalog.product.variation.ProductVariation;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.mapper.catalog.ReadableCategoryMapper;
import com.salesmanager.shop.mapper.catalog.ReadableManufacturerMapper;
import com.salesmanager.shop.mapper.catalog.ReadableProductTypeMapper;
import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.catalog.manufacturer.ReadableManufacturer;
import com.salesmanager.shop.model.catalog.product.ReadableImage;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.catalog.product.ReadableProductPrice;
import com.salesmanager.shop.model.catalog.product.ReadableProductSnapshot;
import com.salesmanager.shop.model.catalog.product.attribute.*;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionValue;
import com.salesmanager.shop.model.catalog.product.product.ProductSpecification;
import com.salesmanager.shop.model.catalog.product.product.variant.ReadableProductVariant;
import com.salesmanager.shop.model.catalog.product.type.ReadableProductType;
import com.salesmanager.shop.model.references.DimensionUnitOfMeasure;
import com.salesmanager.shop.model.references.WeightUnitOfMeasure;
import com.salesmanager.shop.model.store.ReadableMerchantStore;
import com.salesmanager.shop.populator.store.ReadableMerchantStorePopulator;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;
import com.salesmanager.shop.store.controller.product.facade.SellerTextInfoFacade;
import com.salesmanager.shop.utils.DateUtil;
import com.salesmanager.shop.utils.ImageFilePath;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Works for product v2 model
 * 
 * @author carlsamson
 *
 */
@Component
public class ReadableOrderProductSnapshotMapper implements Mapper<Product, ReadableProductSnapshot> {

	// uses code that is similar to ProductDefinition
	@Autowired
	@Qualifier("img")
	private ImageFilePath imageUtils;

	@Autowired
	private ReadableCategoryMapper readableCategoryMapper;

	@Autowired
	private ReadableProductTypeMapper readableProductTypeMapper;

	@Autowired
	private ReadableProductVariantMapper readableProductVariantMapper;

	@Autowired
	private ReadableManufacturerMapper readableManufacturerMapper;

	@Autowired
	private ReadableMerchantStorePopulator readableMerchantStorePopulator;

	@Autowired
	private PricingService pricingService;

	@Autowired
	private SellerTextInfoFacade sellerTextInfoFacade;

	@Autowired
	private ProductMaterialService productMaterialService;

	@Override
	public ReadableProductSnapshot convert(Product source, MerchantStore store, Language language) {
		ReadableProductSnapshot product = new ReadableProductSnapshot();
		return this.merge(source, product, store, language);
	}

	@Override
	public ReadableProductSnapshot merge(Product source, ReadableProductSnapshot destination, MerchantStore store, Language language) {

		Validate.notNull(source, "Product cannot be null");
		Validate.notNull(destination, "Product destination cannot be null");

		destination.setPublishWay(source.getPublishWay() == null? null : source.getPublishWay().name());
		destination.setSku(source.getSku());
		destination.setOutProductId(source.getOutProductId());
		destination.setIdentifier(source.getSku());
		destination.setRefSku(source.getRefSku());
		destination.setId(source.getId());
		destination.setHsCode(source.getHsCode());
		destination.setSellerOpenId(source.getSellerOpenId());
		destination.setMerchantStore(store(source.getMerchantStore(), language));
		destination.setDateAvailable(DateUtil.formatDate(source.getDateAvailable()));
		destination.setStoreName(source.getMerchantStore().getStorename());
		destination.setMinOrderQuantity(source.getMinOrderQuantity());
		destination.setProductStatus(source.getProductStatus() == null? null : source.getProductStatus().name());
		ProductDescription description = null;
		if (source.getDescriptions() != null && source.getDescriptions().size() > 0) {
			for (ProductDescription desc : source.getDescriptions()) {
				if (language != null && desc.getLanguage() != null
						&& desc.getLanguage().getId().intValue() == language.getId().intValue()) {
					description = desc;
					break;
				}else {
					com.salesmanager.shop.model.catalog.product.ProductDescription tragetDescription = populateDescription(
							desc);
					destination.getDescriptions().add(tragetDescription);
				}
			}
		}


		if (language == null){
			language = store.getDefaultLanguage();
		}

		Language finalLanguage = language;
		destination.setOrderQuantityType(source.getOrderQuantityType());
		destination.setCertificateOfOrigin(source.getCertificateOfOrigin());

		destination.setCertificationDocument(source.getCertificationDocument());
		destination.setIntellectualPropertyDocuments(source.getIntellectualPropertyDocuments());
		destination.setShippingTemplateId(source.getShippingTemplateId());
		destination.setId(source.getId());
		destination.setAvailable(source.isAvailable());
		destination.setProductShipeable(source.isProductShipeable());
		destination.setProductAuditStatus(source.getProductAuditStatus() == null?
				null : source.getProductAuditStatus().name());
		destination.setPreOrder(source.isPreOrder());
		destination.setRefSku(source.getRefSku());
		destination.setSortOrder(source.getSortOrder());

		if (source.getType() != null) {
			ReadableProductType readableType = readableProductTypeMapper.convert(source.getType(), store, language);
			destination.setType(readableType);
		}

		if (source.getDateAvailable() != null) {
			destination.setDateAvailable(DateUtil.formatDate(source.getDateAvailable()));
		}

		if (source.getAuditSection() != null) {
			destination.setCreationDate(DateUtil.formatDate(source.getAuditSection().getDateCreated()));
		}


		if (source.getProductReviewCount() != null) {
			destination.setRatingCount(source.getProductReviewCount().intValue());
		}


		List<ProductMaterial> productMaterialList = productMaterialService.queryByProductId(source.getId());
		if (CollectionUtils.isNotEmpty(productMaterialList)){
			List<com.salesmanager.shop.model.catalog.ProductMaterial> collect = productMaterialList.stream().map(productMaterial -> {
				com.salesmanager.shop.model.catalog.ProductMaterial pm = new com.salesmanager.shop.model.catalog.ProductMaterial();
				pm.setProductId(source.getId());
				pm.setMaterialId(productMaterial.getMaterialId());
				pm.setWeight(productMaterial.getWeight());
				pm.setId(productMaterial.getId());
				return pm;
			}).filter(Objects::nonNull).collect(Collectors.toList());
			destination.setProductMaterials(collect);
		}


		// images
		Set<ProductImage> images = source.getImages();
		if (CollectionUtils.isNotEmpty(images)) {

			List<ReadableImage> imageList = images.parallelStream().map(i -> this.convertImage(source, i, store))
					.collect(Collectors.toList());
			destination.setImages(imageList);
			imageList.forEach(image->{
				if(image.isDefaultImage()){
					destination.setImage(image);
				}});
		}




		// availability
		ProductAvailability availability = null;
		for (ProductAvailability a : source.getAvailabilities()) {

			/**
			 * Default availability
			 * store
			 * product
			 * instance null
			 * region variant null
			 */


			availability = a;
			destination.setQuantity(availability.getProductQuantity() == null ? 1 : availability.getProductQuantity());
			if (availability.getProductQuantity().intValue() > 0 && destination.isAvailable()) {
				destination.setCanBePurchased(true);
			}

			if(a.getProductVariant()==null && StringUtils.isEmpty(a.getRegionVariant())) {
				break;
			}
		}
		if (source.getSellerTextInfoId() != null){
			SellerProductShippingTextInfo sellerProductShippingTextInfo = sellerTextInfoFacade.getSellerProductShippingTextById(source.getSellerTextInfoId());
			destination.setSellerProductShippingTextInfo(sellerProductShippingTextInfo);
		}

		//if default instance

		destination.setSku(source.getSku());

		try {
			FinalPrice price = pricingService.calculateProductPrice(source, false);
			if (price != null) {
				destination.setPriceRangeList(price.getPriceRanges());
				destination.setFinalPrice(pricingService.getDisplayAmount(price.getFinalPrice(), store));
				destination.setPrice(price.getFinalPrice());
				destination.setOriginalPrice(pricingService.getDisplayAmount(price.getOriginalPrice(), store));

				if (price.isDiscounted()) {
					destination.setDiscounted(true);
				}

				// price appender
				if (availability != null) {
					Set<ProductPrice> prices = availability.getPrices();
					if (!CollectionUtils.isEmpty(prices)) {
						ReadableProductPrice readableProductPrice = new ReadableProductPrice();
						readableProductPrice.setDiscounted(destination.isDiscounted());
						readableProductPrice.setFinalPrice(destination.getFinalPrice());
						readableProductPrice.setOriginalPrice(destination.getOriginalPrice());

						Optional<ProductPrice> pr = prices.stream()
								.filter(p -> p.getCode().equals(ProductPrice.DEFAULT_PRICE_CODE)).findFirst();

						destination.setProductPrice(readableProductPrice);

						if (pr.isPresent() && language !=null) {
							readableProductPrice.setId(pr.get().getId());
							Optional<ProductPriceDescription> d = pr.get().getDescriptions().stream()
									.filter(desc -> desc.getLanguage().getCode().equals(finalLanguage.getCode()))
									.findFirst();
							if (d.isPresent()) {
								com.salesmanager.shop.model.catalog.product.ProductPriceDescription priceDescription = new com.salesmanager.shop.model.catalog.product.ProductPriceDescription();
								priceDescription.setLanguage(language.getCode());
								priceDescription.setId(d.get().getId());
								priceDescription.setPriceAppender(d.get().getPriceAppender());
								readableProductPrice.setDescription(priceDescription);
							}
						}

					}
				}

			}

		} catch (Exception e) {
			throw new ConversionRuntimeException("An error while converting product price", e);
		}

		if (source.getProductReviewAvg() != null) {
			double avg = source.getProductReviewAvg().doubleValue();
			double rating = Math.round(avg * 2) / 2.0f;
			destination.setRating(rating);
		}

		if (source.getProductReviewCount() != null) {
			destination.setRatingCount(source.getProductReviewCount().intValue());
		}

		if (description != null) {
			com.salesmanager.shop.model.catalog.product.ProductDescription tragetDescription = populateDescription(
					description);
			destination.setDescription(tragetDescription);

		}

		if (!CollectionUtils.isEmpty(source.getCategories())) {
			List<ReadableCategory> categoryList = new ArrayList<ReadableCategory>();
			for (Category category : source.getCategories()) {
				ReadableCategory readableCategory = readableCategoryMapper.convert(category, store, language);
				categoryList.add(readableCategory);

			}
			destination.setCategories(categoryList);
		}

		ProductSpecification specifications = new ProductSpecification();
		specifications.setHeight(source.getProductHeight());
		specifications.setLength(source.getProductLength());
		specifications.setWeight(source.getProductWeight());
		specifications.setWidth(source.getProductWidth());
		if (!StringUtils.isBlank(store.getSeizeunitcode())) {
			specifications
					.setDimensionUnitOfMeasure(DimensionUnitOfMeasure.valueOf(store.getSeizeunitcode().toLowerCase()));
		}
		if (!StringUtils.isBlank(store.getWeightunitcode())) {
			specifications.setWeightUnitOfMeasure(WeightUnitOfMeasure.valueOf(store.getWeightunitcode().toLowerCase()));
		}
		destination.setProductSpecifications(specifications);

		destination.setSortOrder(source.getSortOrder());

		destination.setSupportSample(source.getSupportSample());
		destination.setSamplePrice(source.getSamplePrice());
		destination.setSamplePriceCurrency(source.getSamplePriceCurrency());

		return destination;
	}

	private ReadableImage convertImage(Product product, ProductImage image, MerchantStore store) {
		ReadableImage prdImage = new ReadableImage();
		prdImage.setImageName(image.getProductImage());
		prdImage.setDefaultImage(image.isDefaultImage());

		// TODO product variant image
		StringBuilder imgPath = new StringBuilder();
		imgPath.append(imageUtils.getContextPath())
				.append(imageUtils.buildProductImageUtils(store, product.getSku(), image.getProductImage()));

		prdImage.setImageUrl(imgPath.toString());
		prdImage.setId(image.getId());
		prdImage.setImageType(image.getImageType());
		if (image.getProductImageUrl() != null) {
			prdImage.setExternalUrl(image.getProductImageUrl());
		}
		if (image.getImageType() == 1 && image.getProductImageUrl() != null) {// video
			prdImage.setVideoUrl(image.getProductImageUrl());
		}

		if (prdImage.isDefaultImage()) {
			prdImage.setDefaultImage(true);
		}

		return prdImage;
	}

	/**
	 * 不处理详描信息
	 * @param description
	 * @return
	 */
	private com.salesmanager.shop.model.catalog.product.ProductDescription populateDescription(
			ProductDescription description) {
		if (description == null) {
			return null;
		}

		com.salesmanager.shop.model.catalog.product.ProductDescription tragetDescription = new com.salesmanager.shop.model.catalog.product.ProductDescription();
		tragetDescription.setFriendlyUrl(description.getSeUrl());
		tragetDescription.setName(description.getName());
		tragetDescription.setId(description.getId());
		if (!StringUtils.isBlank(description.getMetatagTitle())) {
			tragetDescription.setTitle(description.getMetatagTitle());
		} else {
			tragetDescription.setTitle(description.getName());
		}
//		tragetDescription.setMetaDescription(description.getMetatagDescription());
//		tragetDescription.setDescription(description.getDescription());
//		tragetDescription.setHighlights(description.getProductHighlight());
//		tragetDescription.setLanguage(description.getLanguage().getCode());
//		tragetDescription.setKeyWords(description.getMetatagKeywords());

		if (description.getLanguage() != null) {
			tragetDescription.setLanguage(description.getLanguage().getCode());
		}
		return tragetDescription;
	}


	private ReadableMerchantStore store(MerchantStore store, Language language) {
		if (language == null) {
			language = store.getDefaultLanguage();
		}
		/*
		 * ReadableMerchantStorePopulator populator = new
		 * ReadableMerchantStorePopulator();
		 * populator.setCountryService(countryService);
		 * populator.setZoneService(zoneService);
		 */
		try {
			return readableMerchantStorePopulator.populate(store, new ReadableMerchantStore(), store, language);
		} catch (ConversionException e) {
			throw new RuntimeException(e);
		}
	}




}
