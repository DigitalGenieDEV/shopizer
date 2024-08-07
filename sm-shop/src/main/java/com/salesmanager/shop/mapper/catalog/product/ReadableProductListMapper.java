package com.salesmanager.shop.mapper.catalog.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.enmus.ExchangeRateEnums;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.feature.ProductFeatureService;
import com.salesmanager.core.business.utils.ExchangeRateConfig;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.PublishWayEnums;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionDescription;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValue;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValueDescription;
import com.salesmanager.core.model.catalog.product.attribute.*;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.description.ProductDescription;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.catalog.product.price.PriceRange;
import com.salesmanager.core.model.catalog.product.price.ProductPrice;
import com.salesmanager.core.model.catalog.product.price.ProductPriceDescription;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import com.salesmanager.core.model.catalog.product.variation.ProductVariation;
import com.salesmanager.core.model.feature.ProductFeature;
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
import com.salesmanager.shop.model.catalog.product.attribute.*;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionValue;
import com.salesmanager.shop.model.catalog.product.product.ProductSpecification;
import com.salesmanager.shop.model.catalog.product.product.variant.ReadableProductVariant;
import com.salesmanager.shop.model.catalog.product.type.ReadableProductType;
import com.salesmanager.shop.model.references.DimensionUnitOfMeasure;
import com.salesmanager.shop.model.references.WeightUnitOfMeasure;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;
import com.salesmanager.shop.utils.DateUtil;
import com.salesmanager.shop.utils.ImageFilePath;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Works for product v2 model
 * 
 * @author carlsamson
 *
 */
@Component
public class ReadableProductListMapper implements Mapper<Product, ReadableProduct> {

	// uses code that is similar to ProductDefinition
	@Autowired
	@Qualifier("img")
	private ImageFilePath imageUtils;

	@Autowired
	private ExchangeRateConfig examRateConfig;

	@Autowired
	private ProductFeatureService productFeatureService;

	@Autowired
	private ReadableCategoryMapper readableCategoryMapper;

	@Autowired
	private ReadableProductTypeMapper readableProductTypeMapper;

	@Autowired
	private ReadableProductVariantMapper readableProductVariantMapper;

	@Autowired
	private ReadableManufacturerMapper readableManufacturerMapper;

	@Autowired
	private PricingService pricingService;

	@Override
	public ReadableProduct convert(Product source, MerchantStore store, Language language) {
		ReadableProduct product = new ReadableProduct();
		return this.merge(source, product, store, language);
	}

	@Override
	public ReadableProduct merge(Product source, ReadableProduct destination, MerchantStore store, Language language) {

		Validate.notNull(source, "Product cannot be null");
		Validate.notNull(destination, "Product destination cannot be null");
		destination.setSku(source.getSku());
		destination.setIdentifier(source.getSku());
		destination.setRefSku(source.getRefSku());
		destination.setId(source.getId());
		destination.setDateAvailable(DateUtil.formatDate(source.getDateAvailable()));
		destination.setStoreName(source.getMerchantStore().getStorename());
		ProductDescription description = null;
		if (source.getDescriptions() != null && source.getDescriptions().size() > 0) {
			for (ProductDescription desc : source.getDescriptions()) {
				if (language != null && desc.getLanguage() != null
						&& desc.getLanguage().getId().intValue() == language.getId().intValue()) {
					description = desc;
					break;
				}
			}
		}

		if (language == null){
			language = source.getMerchantStore().getDefaultLanguage();
		}


		destination.setId(source.getId());
		destination.setAvailable(source.isAvailable());
		destination.setProductShipeable(source.isProductShipeable());
		destination.setProductAuditStatus(source.getProductAuditStatus() == null?
				null : source.getProductAuditStatus().name());
		destination.setPreOrder(source.isPreOrder());
		destination.setRefSku(source.getRefSku());
		destination.setSortOrder(source.getSortOrder());
		destination.setDiscountedNum(source.getDiscount());
		if (source.getDiscount() != null){
			destination.setDiscounted(true);
		}
		destination.setFeatureSort(source.getFeatureSort());
		if (source.getType() != null) {
			ReadableProductType readableType = readableProductTypeMapper.convert(source.getType(), source.getMerchantStore(), language);
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

		if (source.getManufacturer() != null) {
			ReadableManufacturer manufacturer = readableManufacturerMapper.convert(source.getManufacturer(), source.getMerchantStore(),
					language);
			destination.setManufacturer(manufacturer);
		}

		// images
		Set<ProductImage> images = source.getImages();
		if (CollectionUtils.isNotEmpty(images)) {

			List<ReadableImage> imageList = images.stream().map(i -> this.convertImage(source, i, source.getMerchantStore()))
					.collect(Collectors.toList());
			destination.setImages(imageList);
			imageList.forEach(image->{
				if(image.isDefaultImage()){
					destination.setImage(image);
				}});
		}


		destination.setSku(source.getSku());

		try {
			BigDecimal price = source.getPrice();
			List<PriceRange> priceRanges = StringUtils.isEmpty(source.getPriceRangeList()) ? null :
					JSON.parseObject(source.getPriceRangeList(), new TypeReference<List<PriceRange>>() {});

			if (source.getPublishWay() !=null && source.getPublishWay() == PublishWayEnums.IMPORT_BY_1688){
				BigDecimal rate = examRateConfig.getRate(ExchangeRateEnums.CNY_KRW);

				if (price != null){
					price =  rate.multiply(price).setScale(2, RoundingMode.HALF_UP);
				}
				if (CollectionUtils.isNotEmpty(priceRanges)){
					priceRanges.forEach(priceRange -> {
						if (StringUtils.isNotEmpty(priceRange.getPromotionPrice())){
							priceRange.setPromotionPrice(
									rate.multiply(new BigDecimal(priceRange.getPromotionPrice()))
											.setScale(2, RoundingMode.HALF_UP)
											.toString());
						}
						priceRange.setPrice(rate.multiply(new BigDecimal(priceRange.getPrice()))
								.setScale(2, RoundingMode.HALF_UP)
								.toString());
					});
				}
			}
			destination.setPrice(price);
			destination.setFinalPrice(pricingService.getDisplayAmount(price, source.getMerchantStore()));
			destination.setOriginalPrice(pricingService.getDisplayAmount(price, source.getMerchantStore()));
			destination.setPriceRangeList(priceRanges);


			List<ProductFeature> productFeatures = productFeatureService.findListByProductId(source.getId());
			List<String> tags = productFeatures.stream().filter(productFeature -> "1".equals(productFeature.getValue())).map(ProductFeature::getKey).collect(Collectors.toList());
			destination.setTags(tags);

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
				ReadableCategory readableCategory = readableCategoryMapper.convert(category, source.getMerchantStore(), language);
				categoryList.add(readableCategory);

			}
			destination.setCategories(categoryList);
		}

		ProductSpecification specifications = new ProductSpecification();
		specifications.setHeight(source.getProductHeight());
		specifications.setLength(source.getProductLength());
		specifications.setWeight(source.getProductWeight());
		specifications.setWidth(source.getProductWidth());
		if (!StringUtils.isBlank(source.getMerchantStore().getSeizeunitcode())) {
			specifications
					.setDimensionUnitOfMeasure(DimensionUnitOfMeasure.valueOf(source.getMerchantStore().getSeizeunitcode().toLowerCase()));
		}
		if (!StringUtils.isBlank(source.getMerchantStore().getWeightunitcode())) {
			specifications.setWeightUnitOfMeasure(WeightUnitOfMeasure.valueOf(source.getMerchantStore().getWeightunitcode().toLowerCase()));
		}
		destination.setProductSpecifications(specifications);

		destination.setSortOrder(source.getSortOrder());

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
		tragetDescription.setMetaDescription(description.getMetatagDescription());
		tragetDescription.setDescription(description.getDescription());
		tragetDescription.setHighlights(description.getProductHighlight());
		tragetDescription.setLanguage(description.getLanguage().getCode());
		tragetDescription.setKeyWords(description.getMetatagKeywords());

		if (description.getLanguage() != null) {
			tragetDescription.setLanguage(description.getLanguage().getCode());
		}
		return tragetDescription;
	}

	private ReadableProductProperty createProperty(ProductAttribute productAttribute, Language language) {

		ReadableProductProperty attr = new ReadableProductProperty();
		attr.setId(productAttribute.getProductOption().getId());// attribute of the option
		attr.setType(productAttribute.getProductOption().getProductOptionType());

		List<ProductOptionDescription> descriptions = productAttribute.getProductOption().getDescriptionsSettoList();

		ReadableProductPropertyValue propertyValue = new ReadableProductPropertyValue();

		if (descriptions != null && descriptions.size() > 0) {
			for (ProductOptionDescription optionDescription : descriptions) {
				com.salesmanager.shop.model.catalog.product.attribute.ProductOptionValueDescription productOptionValueDescription = new com.salesmanager.shop.model.catalog.product.attribute.ProductOptionValueDescription();
				productOptionValueDescription.setId(optionDescription.getId());
				productOptionValueDescription.setLanguage(optionDescription.getLanguage().getCode());
				productOptionValueDescription.setName(optionDescription.getName());
				propertyValue.getValues().add(productOptionValueDescription);

			}
		}

		attr.setCode(productAttribute.getProductOption().getCode());
		return attr;

	}

	private Optional<ReadableProductOptionValue> optionValue(ProductOptionValue optionValue, MerchantStore store,
			Language language) {

		if (optionValue == null) {
			return Optional.empty();
		}

		ReadableProductOptionValue optValue = new ReadableProductOptionValue();

		com.salesmanager.shop.model.catalog.product.attribute.ProductOptionValueDescription valueDescription = new com.salesmanager.shop.model.catalog.product.attribute.ProductOptionValueDescription();
		valueDescription.setLanguage(language.getCode());

		if (!StringUtils.isBlank(optionValue.getProductOptionValueImage())) {
			optValue.setImage(
					imageUtils.buildProductPropertyImageUtils(store, optionValue.getProductOptionValueImage()));
		}
		optValue.setSortOrder(0);

		if (optionValue.getProductOptionValueSortOrder() != null) {
			optValue.setSortOrder(optionValue.getProductOptionValueSortOrder().intValue());
		}

		optValue.setCode(optionValue.getCode());

		List<ProductOptionValueDescription> podescriptions = optionValue.getDescriptionsSettoList();
		ProductOptionValueDescription podescription = null;
		if (podescriptions != null && podescriptions.size() > 0) {
			podescription = podescriptions.get(0);
			if (podescriptions.size() > 1) {
				for (ProductOptionValueDescription optionValueDescription : podescriptions) {
					if (optionValueDescription.getLanguage().getId().intValue() == language.getId().intValue()) {
						podescription = optionValueDescription;
						break;
					}
				}
			}
		}
		valueDescription.setName(podescription.getName());
		valueDescription.setDescription(podescription.getDescription());
		optValue.setDescription(valueDescription);

		return Optional.of(optValue);

	}

	private void instanceToOption(TreeMap<Long, ReadableProductOption> selectableOptions, ProductVariant instance,
			MerchantStore store, Language language) {


		if (instance == null || instance.getVariations() == null) {
			return;
		}

		for (ProductVariation variation : instance.getVariations()) {
			ReadableProductOption option = this.option(selectableOptions, variation.getProductOption(), language);
			option.setVariant(true);

			Optional<ReadableProductOptionValue> optionValueOpt = this.optionValue(variation.getProductOptionValue(), store, language);
			if (optionValueOpt.isPresent()) {
				ReadableProductOptionValue optionValue = optionValueOpt.get();
				optionValue.setId(instance.getId());
				if (instance.isDefaultSelection()) {
					optionValue.setDefaultValue(true);
				}
				addOptionValue(option, optionValue);
			}
		}


	}
	
	private void addOptionValue(ReadableProductOption option, ReadableProductOptionValue optionValue) {
		
		ReadableProductOptionValue find = option.getOptionValues().stream()
				  .filter(optValue -> optValue.getCode()==optionValue.getCode())
				  .findAny()
				  .orElse(null);
		
		if(find == null) {
			option.getOptionValues().add(optionValue);
		}
	}
	
	private ReadableProductOption option(TreeMap<Long, ReadableProductOption> selectableOptions, ProductOption option, Language language) {
		if(selectableOptions.containsKey(option.getId())) {
			return selectableOptions.get(option.getId());
		}

		ReadableProductOption readable = this.createOption(option, language);
		selectableOptions.put(readable.getId(), readable);
		return readable;
	}

	private ReadableProductOption createOption(ProductOption opt, Language language) {

		ReadableProductOption option = new ReadableProductOption();
		option.setId(opt.getId());// attribute of the option
		option.setType(opt.getProductOptionType());
		option.setCode(opt.getCode());
		List<ProductOptionDescription> descriptions = opt.getDescriptionsSettoList();
		ProductOptionDescription description = null;
		if (descriptions != null && descriptions.size() > 0) {
			description = descriptions.get(0);
			if (descriptions.size() > 1) {
				for (ProductOptionDescription optionDescription : descriptions) {
					if (optionDescription.getLanguage().getCode().equals(language.getCode())) {
						description = optionDescription;
						break;
					}
				}
			}
		}

		if (description == null) {
			return null;
		}

		option.setLang(language.getCode());
		option.setName(description.getName());
		option.setCode(opt.getCode());

		return option;

	}

}
