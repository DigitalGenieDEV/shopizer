package com.salesmanager.shop.store.facade.product;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.salesmanager.core.business.alibaba.fenxiao.crossborder.param.*;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.AnnouncementInfo;
import com.salesmanager.core.business.modules.enmus.ExchangeRateEnums;
import com.salesmanager.core.business.repositories.catalog.product.ProductRepository;
import com.salesmanager.core.business.services.alibaba.category.AlibabaCategoryService;
import com.salesmanager.core.business.services.alibaba.product.AlibabaProductService;
import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionValueService;
import com.salesmanager.core.business.services.catalog.product.manufacturer.ManufacturerService;
import com.salesmanager.core.business.services.catalog.product.variation.ProductVariationService;
import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.utils.ExchangeRateConfig;
import com.salesmanager.core.business.utils.ObjectConvert;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.PublishWayEnums;
import com.salesmanager.core.model.catalog.product.attribute.*;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionDescription;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValue;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValueDescription;
import com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer;
import com.salesmanager.core.model.catalog.product.manufacturer.ManufacturerDescription;
import com.salesmanager.core.model.catalog.product.price.PriceRange;
import com.salesmanager.core.model.catalog.product.variation.ProductVariation;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.category.CategoryDescription;
import com.salesmanager.shop.model.catalog.product.PersistableImage;
import com.salesmanager.shop.model.catalog.product.PersistableProductPrice;
import com.salesmanager.shop.model.catalog.product.ProductDescription;
import com.salesmanager.shop.model.catalog.product.attribute.*;
import com.salesmanager.shop.model.catalog.product.attribute.api.PersistableProductOptionEntity;
import com.salesmanager.shop.model.catalog.product.product.PersistableProduct;
import com.salesmanager.shop.model.catalog.product.product.PersistableProductInventory;
import com.salesmanager.shop.model.catalog.product.product.ProductSpecification;
import com.salesmanager.shop.model.catalog.product.product.alibaba.AlibabaProductSearchKeywordQueryParam;
import com.salesmanager.shop.model.catalog.product.product.alibaba.AlibabaProductSearchQueryModelProduct;
import com.salesmanager.shop.model.catalog.product.product.alibaba.ReadableAlibabaProductSearchQueryPriceInfo;
import com.salesmanager.shop.model.catalog.product.product.alibaba.ReadableProductPageInfo;
import com.salesmanager.shop.model.catalog.product.product.definition.PersistableProductDefinition;
import com.salesmanager.shop.model.catalog.product.product.variant.PersistableProductVariant;
import com.salesmanager.shop.model.catalog.product.product.variant.PersistableVariation;
import com.salesmanager.shop.model.catalog.product.variation.PersistableProductVariation;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.controller.product.facade.AlibabaProductFacade;
import com.salesmanager.shop.store.controller.product.facade.ProductCommonFacade;
import com.salesmanager.shop.store.controller.product.facade.ProductDefinitionFacade;
import com.salesmanager.shop.store.controller.product.facade.ProductVariationFacade;
import com.salesmanager.shop.utils.UniqueIdGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlibabaProductFacadeImpl implements AlibabaProductFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlibabaProductFacadeImpl.class);

    @Autowired
    private ExchangeRateConfig examRateConfig;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ProductOptionValueService productOptionValueService;

    @Autowired
    private ProductOptionService productOptionService;

    @Autowired
    private AlibabaProductService alibabaProductService;

    @Autowired
    private ProductCommonFacade productCommonFacade;
    @Autowired
    private AlibabaCategoryService alCategoryService;

    @Autowired
    private ProductVariationFacade productVariationFacade;

    @Autowired
    private ProductVariationService productVariationService;

    @Autowired
    private ProductDefinitionFacade productDefinitionFacade;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private ProductService productService;

    @Override
    public List<Long> importProduct(List<Long> productIds, String language,
                              MerchantStore merchantStore, List<Long> categoryIds) throws ServiceException {
        if (CollectionUtils.isEmpty(productIds)){
            throw new ServiceException(ServiceException.EXCEPTION_VALIDATION,
                    "Invalid date format","validaion.param.error");
        }
        List<Long> productIdList = new ArrayList<>();
        productIds.forEach(productId ->{
            ProductSearchQueryProductDetailParamOfferDetailParam productSearchQueryProductDetailParamOfferDetailParam = new ProductSearchQueryProductDetailParamOfferDetailParam();
            productSearchQueryProductDetailParamOfferDetailParam.setOfferId(productId);
            productSearchQueryProductDetailParamOfferDetailParam.setCountry(language);

            ProductSearchQueryProductDetailParamOfferDetailParam productSearchQueryProductDetailParamOfferDetailParamForEn = new ProductSearchQueryProductDetailParamOfferDetailParam();
            productSearchQueryProductDetailParamOfferDetailParamForEn.setOfferId(productId);
            productSearchQueryProductDetailParamOfferDetailParamForEn.setCountry("en");
            ProductSearchQueryProductDetailModelProductDetailModel productSearchQueryProductDetailModelProductDetailModelForEn = alibabaProductService.queryProductDetail(productSearchQueryProductDetailParamOfferDetailParamForEn);

            ProductSearchQueryProductDetailModelProductDetailModel productSearchQueryProductDetailModelProductDetailModel = alibabaProductService.queryProductDetail(productSearchQueryProductDetailParamOfferDetailParam);
            try {

                Long itemId = saveProduct(productSearchQueryProductDetailModelProductDetailModelForEn, productSearchQueryProductDetailModelProductDetailModel,
                        language, merchantStore, categoryIds);

                productIdList.add(itemId);
            } catch (Exception e) {
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++："
                        +productSearchQueryProductDetailModelProductDetailModel.getOfferId());
                LOGGER.error("import product error", e);
            }
        });
        return productIdList;
    }


    @Override
    public ReadableProductPageInfo searchProductByKeywords(AlibabaProductSearchKeywordQueryParam queryParam) {
        ProductSearchKeywordQueryParamOfferQueryParam param = ObjectConvert.convert(queryParam, ProductSearchKeywordQueryParamOfferQueryParam.class);
        ProductSearchKeywordQueryModelPageInfoV productSearchKeywordQueryModelPageInfoV = alibabaProductService.searchKeyword(param);
        ReadableProductPageInfo productPageInfo = ObjectConvert.convert(productSearchKeywordQueryModelPageInfoV, ReadableProductPageInfo.class);

        Optional<ProductSearchKeywordQueryModelProductInfoModelV[]> queryPriceInfoList =  Optional.ofNullable(productSearchKeywordQueryModelPageInfoV.getData());

        queryPriceInfoList.ifPresent(arr -> {
            List<AlibabaProductSearchQueryModelProduct> resultList = Arrays.stream(arr)
                    .map(item -> {
                        AlibabaProductSearchQueryModelProduct alibabaProductSearchQueryModelProduct = ObjectConvert.convert(item, AlibabaProductSearchQueryModelProduct.class);

                        alibabaProductSearchQueryModelProduct.setPriceInfo(ObjectConvert.convert(item.getPriceInfo(), ReadableAlibabaProductSearchQueryPriceInfo.class));
                        return alibabaProductSearchQueryModelProduct;
                    })
                    .collect(Collectors.toList());
            productPageInfo.setData(resultList);
        });
        return productPageInfo;
    }

    public Long saveProduct(ProductSearchQueryProductDetailModelProductDetailModel productSearchQueryProductDetailModelProductDetailModelForEn,ProductSearchQueryProductDetailModelProductDetailModel productDetailModel,
                            String language, MerchantStore store, List<Long> categoryIds) throws Exception {
        PersistableProductDefinition productDefinition = new PersistableProductDefinition();

        PersistableProduct persistableProduct =  new PersistableProduct();

        Language en = languageService.getByCode("en");


        Language ko = languageService.getByCode(language);

        List<com.salesmanager.shop.model.catalog.category.Category> categoryList =  new ArrayList<>();

        if (!CollectionUtils.isEmpty(categoryIds)){

            Long leftCategoryId = categoryIds.get(0);
            Category leftCategory = categoryService.getById(store, leftCategoryId);
            if (leftCategory == null){
                throw new ResourceNotFoundException("leftCategory is null [" + leftCategoryId + "]");
            }
            com.salesmanager.shop.model.catalog.category.Category convertCategory = ObjectConvert.convert(leftCategory, com.salesmanager.shop.model.catalog.category.Category.class);
            convertCategory.setDescription(ObjectConvert.convert(leftCategory.getDescription(), CategoryDescription.class));
            categoryList.add(convertCategory);
        }else{
            Long categoryId =   productDetailModel.getCategoryId();
            Long topCategoryId =   productDetailModel.getTopCategoryId();
            Long secondCategoryId =   productDetailModel.getSecondCategoryId();
            Long thirdCategoryId =   productDetailModel.getThirdCategoryId();
            persistableProduct.setLeftCategoryId(categoryId);

            Category category =  categoryService.getByCode(store, categoryId.toString());
            if (category == null){
                Category topCategory = buildCategory(topCategoryId, en, ko, store);
                Category secondCategory = buildCategory(secondCategoryId, en, ko, store);
                Category thirdCategory = buildCategory(thirdCategoryId, en, ko, store);
                Category category3 =  createCategoryList(topCategory, secondCategory, thirdCategory, store);
                com.salesmanager.shop.model.catalog.category.Category category2 = new com.salesmanager.shop.model.catalog.category.Category();
                category2.setId(category3.getId());
                categoryList.add(category2);
            }else{
                com.salesmanager.shop.model.catalog.category.Category convertCategory = ObjectConvert.convert(category, com.salesmanager.shop.model.catalog.category.Category.class);
                convertCategory.setDescription(ObjectConvert.convert(category.getDescription(), CategoryDescription.class));
                categoryList.add(convertCategory);
            }
        }


        ProductSearchQueryProductDetailModelProductAttribute[] productAttribute = productDetailModel.getProductAttribute();
        Manufacturer manufacturers = null;
        for(int i =0; i<productAttribute.length ;i++){
            ProductSearchQueryProductDetailModelProductAttribute productSearchQueryProductDetailModelProductAttribute = productAttribute[i];
            if ("2176".equals(productSearchQueryProductDetailModelProductAttribute.getAttributeId())){
                manufacturers =  manufacturerService.getByCode(store, productSearchQueryProductDetailModelProductAttribute.getValue());
                if (manufacturers == null){
                    manufacturers = new Manufacturer();
                    manufacturers.setMerchantStore(store);
                    manufacturers.setCode(productSearchQueryProductDetailModelProductAttribute.getValue());

                    ManufacturerDescription manufacturersDescription = new ManufacturerDescription();
                    manufacturersDescription.setLanguage(ko);
                    manufacturersDescription.setName(productSearchQueryProductDetailModelProductAttribute.getValueTrans());
                    manufacturersDescription.setManufacturer(manufacturers);
                    manufacturers.getDescriptions().add(manufacturersDescription);
                    manufacturerService.create(manufacturers);
                }
            }
        }

        productDefinition.setIdentifier(productDetailModel.getSubjectTrans());
        productDefinition.setOutProductId(productDetailModel.getOfferId());
        productDefinition.setOutProductId(productDetailModel.getOfferId());
        Product productByOutId = productService.findByOutId(productDetailModel.getOfferId());

        Long productId = null;
        if (productByOutId == null){
            persistableProduct.setSku(UniqueIdGenerator.generateUniqueId());
//            productId = productDefinitionFacade.saveProductDefinition(store, productDefinition, ko);
        }else {
            productId = productByOutId.getId();
        }

        persistableProduct.setId(productId);
        persistableProduct.setAvailable(true);
        persistableProduct.setCategories(categoryList);

        ProductSearchQueryProductDetailModelProductImage productImage = productDetailModel.getProductImage();
        String[] images = productImage.getImages();
        List<PersistableImage> persistableImages =  new ArrayList<>();
        for (int i = 0;i<images.length;i++){
            String image = images[i];
            PersistableImage persistableImage = new PersistableImage();
            persistableImage.setImageUrl(image);
            persistableImage.setImageType(1);
            persistableImages.add(persistableImage);
        }

        persistableProduct.setImages(persistableImages);

        com.salesmanager.shop.model.catalog.product.ProductDescription description = new ProductDescription();
        description.setLanguage("ko");
        description.setName(productDetailModel.getSubjectTrans());
        description.setTitle(productDetailModel.getSubjectTrans());
        description.setDescription(productDetailModel.getDescription());
        com.salesmanager.shop.model.catalog.product.ProductDescription descriptionForEn = new ProductDescription();
        descriptionForEn.setLanguage("en");
        descriptionForEn.setName(productSearchQueryProductDetailModelProductDetailModelForEn.getSubjectTrans());
        descriptionForEn.setTitle(productSearchQueryProductDetailModelProductDetailModelForEn.getSubjectTrans());
        descriptionForEn.setDescription(productSearchQueryProductDetailModelProductDetailModelForEn.getDescription());
        persistableProduct.getDescriptions().add(descriptionForEn);

        persistableProduct.getDescriptions().add(description);

        persistableProduct.setOutProductId(productDetailModel.getOfferId());
        if (productDetailModel.getSellerMixSetting()!=null && productDetailModel.getSellerMixSetting().getGeneralHunpi()){
            persistableProduct.setMixNumber(productDetailModel.getSellerMixSetting().getMixNumber());
            persistableProduct.setMixAmount(productDetailModel.getSellerMixSetting().getMixAmount());
        }

        if (productDetailModel != null && productDetailModel.getProductShippingInfo() != null) {
            ProductSpecification productSpecification = new ProductSpecification();
            ProductSearchQueryProductDetailModelProductShippingInfo shippingInfo = productDetailModel.getProductShippingInfo();
            productSpecification.setHeight(convertToBigDecimal(shippingInfo.getHeight()));
            productSpecification.setLength(convertToBigDecimal(shippingInfo.getLength()));
            productSpecification.setWeight(convertToBigDecimal(shippingInfo.getWeight()));
            productSpecification.setWidth(convertToBigDecimal(shippingInfo.getWidth()));
            persistableProduct.setProductSpecifications(productSpecification);
        }

        persistableProduct.setMinOrderQuantity(productDetailModel.getMinOrderQuantity());
        createProductVariant(ko, store, productDetailModel, persistableProduct);

        createAttribute(productSearchQueryProductDetailModelProductDetailModelForEn.getProductAttribute(), en, ko, store, productDetailModel.getProductAttribute(),  persistableProduct);
        persistableProduct.setPublishWay(PublishWayEnums.IMPORT_BY_1688.name());
        PersistableAnnouncement persistableAnnouncement = new PersistableAnnouncement();
        persistableAnnouncement.setProductId(productId);


        PersistableAnnouncement.AnnouncementField annotationField = new PersistableAnnouncement.AnnouncementField();
        annotationField.setComment("3333");
        annotationField.setField("222");
        annotationField.setValue("1111");
        persistableAnnouncement.setAnnouncementFields(Lists.newArrayList(annotationField));
        persistableProduct.setAnnouncement(persistableAnnouncement);
//        persistableAnnouncement.setAnnouncementFields();
//        persistableProduct.setAnnouncementAttributes();
        productCommonFacade.saveProduct(store, persistableProduct, ko);
        return productId;
    }


    private void createAttribute(ProductSearchQueryProductDetailModelProductAttribute[] productAttributeForEn,Language en ,Language language, MerchantStore store, ProductSearchQueryProductDetailModelProductAttribute[] productAttribute, PersistableProduct product) throws Exception {

        Map<String, ProductSearchQueryProductDetailModelProductAttribute> attributeEnMap = Arrays.stream(productAttributeForEn)
                .collect(Collectors.toMap(
                        attribute -> attribute.getAttributeId() + attribute.getValue(),  // 使用 attributeId 和 value 组合成键
                        attribute -> attribute,
                        (existing, replacement) -> existing  // 在遇到重复键时选择保留现有值
                ));



        List<PersistableProductAttribute> attributes = new ArrayList<PersistableProductAttribute>();

        for(int i =0; i<productAttribute.length ;i++) {
            ProductSearchQueryProductDetailModelProductAttribute productSearchQueryProductDetailModelProductAttribute = productAttribute[i];
            if ("2176".equals(productSearchQueryProductDetailModelProductAttribute.getAttributeId())) {
                continue;
            }
            PersistableProductAttribute persistableProductAttribute = new PersistableProductAttribute();

            String attributeName = productSearchQueryProductDetailModelProductAttribute.getAttributeName();
            String value = productSearchQueryProductDetailModelProductAttribute.getValue();

            ProductOption productOption =  productOptionService.getByCode(store, attributeName);
            Long productOptionId = productOption == null ? null : productOption.getId();
            if (productOption == null){
                ProductOption option =  new ProductOption();
                option.setMerchantStore(store);
                option.setCode(attributeName);
                option.setProductOptionType(ProductOptionType.Select.name());

                ProductOptionDescription sizeDescription = new ProductOptionDescription();
                sizeDescription.setLanguage(language);
                sizeDescription.setName(productSearchQueryProductDetailModelProductAttribute.getValueTrans());
                sizeDescription.setDescription(productSearchQueryProductDetailModelProductAttribute.getValueTrans());
                sizeDescription.setProductOption(option);

                ProductSearchQueryProductDetailModelProductAttribute productSearchQueryProductDetailModelProductAttribute1 = attributeEnMap.get(productSearchQueryProductDetailModelProductAttribute.getAttributeId() + productSearchQueryProductDetailModelProductAttribute.getValue());
                if (productSearchQueryProductDetailModelProductAttribute1!=null){
                    ProductOptionDescription sizeDescriptionForEn = new ProductOptionDescription();
                    sizeDescriptionForEn.setLanguage(en);
                    sizeDescriptionForEn.setName(productSearchQueryProductDetailModelProductAttribute1 == null? null :productSearchQueryProductDetailModelProductAttribute1.getValueTrans());
                    sizeDescriptionForEn.setDescription(productSearchQueryProductDetailModelProductAttribute1 == null ? null : productSearchQueryProductDetailModelProductAttribute1.getValueTrans());
                    sizeDescriptionForEn.setProductOption(option);
                    option.getDescriptions().add(sizeDescriptionForEn);
                }


                option.getDescriptions().add(sizeDescription);

                //create option
                productOptionService.saveOrUpdate(option);
                ProductOption byCode = productOptionService.getByCode(store, attributeName);
                productOptionId = byCode.getId();

            }
            PersistableProductOptionEntity propertyOption = new PersistableProductOptionEntity();
            propertyOption.setCode(attributeName);
            propertyOption.setId(productOptionId);
            persistableProductAttribute.setOption(propertyOption);

            ProductOptionValue optionValue =  productOptionValueService.getByCode(store, value);
            Long optionValueId = optionValue == null ? null : optionValue.getId();

            if (optionValue == null){
                ProductOptionValue productOptionValue = new ProductOptionValue();
                productOptionValue.setMerchantStore(store);
                productOptionValue.setCode(value);

                ProductOptionValueDescription nineDescription = new ProductOptionValueDescription();
                nineDescription.setLanguage(language);
                nineDescription.setName(productSearchQueryProductDetailModelProductAttribute.getValueTrans());
                nineDescription.setDescription(productSearchQueryProductDetailModelProductAttribute.getValueTrans());
                nineDescription.setProductOptionValue(productOptionValue);

                ProductSearchQueryProductDetailModelProductAttribute productSearchQueryProductDetailModelProductAttribute1 = attributeEnMap.get(productSearchQueryProductDetailModelProductAttribute.getAttributeId() + productSearchQueryProductDetailModelProductAttribute.getValue());
                if (productSearchQueryProductDetailModelProductAttribute1 !=null){
                    ProductOptionValueDescription nineDescriptionForEn = new ProductOptionValueDescription();
                    nineDescriptionForEn.setLanguage(en);
                    nineDescriptionForEn.setName(productSearchQueryProductDetailModelProductAttribute1 == null? null : productSearchQueryProductDetailModelProductAttribute1.getValueTrans());
                    nineDescriptionForEn.setDescription(productSearchQueryProductDetailModelProductAttribute1== null? null : productSearchQueryProductDetailModelProductAttribute1.getValueTrans());
                    nineDescriptionForEn.setProductOptionValue(productOptionValue);
                    productOptionValue.getDescriptions().add(nineDescriptionForEn);
                }
                productOptionValue.getDescriptions().add(nineDescription);

                //create an option value
                productOptionValueService.saveOrUpdate(productOptionValue);

                ProductOptionValue byCode = productOptionValueService.getByCode(store, value);
                optionValueId = byCode.getId();
            }
            PersistableProductOptionValue persistentOptionValue = new PersistableProductOptionValue();
            persistentOptionValue.setCode(value);
            persistentOptionValue.setId(optionValueId);
            persistableProductAttribute.setOptionValue(persistentOptionValue);
            persistableProductAttribute.setSortOrder(i);
            persistableProductAttribute.setAttributeDisplayOnly(true);
            attributes.add(persistableProductAttribute);
        }
        product.setProperties(attributes);

    }



    private void createProductVariant(Language language, MerchantStore store,
                                      ProductSearchQueryProductDetailModelProductDetailModel productDetailModel, PersistableProduct product) throws Exception {

        List<PersistableProductVariant> variants = new ArrayList<PersistableProductVariant>();

        BigDecimal defaultPrice = new BigDecimal(Integer.MAX_VALUE); // 初始化为一个较大的值

        ProductSearchQueryProductDetailModelSkuInfo[] productSkuInfos = productDetailModel.getProductSkuInfos();
        for (int i=0; i<productSkuInfos.length; i++){
            ProductSearchQueryProductDetailModelSkuInfo productSkuInfo = productSkuInfos[i];

            PersistableProductVariant persistableProductVariant = new PersistableProductVariant();
            PersistableProductInventory productInventory = new PersistableProductInventory();

            PersistableProductPrice price = new PersistableProductPrice();

            if (productDetailModel.getProductSaleInfo().getQuoteType() != null && productDetailModel.getProductSaleInfo().getQuoteType() == 2){
                ProductSearchQueryProductDetailModelPriceRangeV[] priceRangeList = productDetailModel.getProductSaleInfo().getPriceRangeList();
                List<PriceRange> ranges = new ArrayList<>();
                for(int p =0;p<priceRangeList.length;p++){
                    ProductSearchQueryProductDetailModelPriceRangeV productSearchQueryProductDetailModelPriceRangeV = priceRangeList[p];
                    PriceRange priceRange = new PriceRange();
                    priceRange.setPrice(String.valueOf(examRateConfig.getRate(ExchangeRateEnums.CNY_KRW).multiply(new BigDecimal(productSearchQueryProductDetailModelPriceRangeV.getPrice()))));
                    priceRange.setStartQuantity(productSearchQueryProductDetailModelPriceRangeV.getStartQuantity());
                    ranges.add(priceRange);
                }
                price.setPriceRangeList(ranges);
                product.setPriceRangeList(ranges);
                productInventory.setQuantity(productDetailModel.getProductSaleInfo().getAmountOnSale());
            } else {
                BigDecimal skuPrice = new BigDecimal(StringUtils.isEmpty(productSkuInfo.getPromotionPrice())?
                        productSkuInfo.getPrice() : productSkuInfo.getPromotionPrice());
                skuPrice =  examRateConfig.getRate(ExchangeRateEnums.CNY_KRW).multiply(skuPrice);
                price.setPrice(skuPrice);
                if (skuPrice.compareTo(defaultPrice) < 0) {
                    defaultPrice = skuPrice;
                }
                productInventory.setQuantity(productDetailModel.getProductSaleInfo().getAmountOnSale());
            }
            product.setQuoteType(productDetailModel.getProductSaleInfo().getQuoteType());
            ProductSearchQueryProductDetailModelSkuAttribute[] skuAttributes = productSkuInfo.getSkuAttributes();
            productInventory.setPrice(price);
            List<PersistableVariation> persistableVariationList = new ArrayList<>();
            for (int s =0; s<skuAttributes.length; s++){
                PersistableProductVariation productVariant = new PersistableProductVariation();

                ProductSearchQueryProductDetailModelSkuAttribute skuAttribute = skuAttributes[s];

                String attributeName = skuAttribute.getAttributeName();
                String value = skuAttribute.getValue();
                ProductOption productOption =  productOptionService.getByCode(store, attributeName);
                if (productOption == null){
                    ProductOption option =  new ProductOption();
                    option.setMerchantStore(store);
                    option.setCode(attributeName);
                    option.setProductOptionType(ProductOptionType.Select.name());

                    ProductOptionDescription sizeDescription = new ProductOptionDescription();
                    sizeDescription.setLanguage(language);
                    sizeDescription.setName(skuAttribute.getAttributeNameTrans());
                    sizeDescription.setDescription(skuAttribute.getAttributeNameTrans());
                    sizeDescription.setProductOption(option);

                    option.getDescriptions().add(sizeDescription);

                    //create option
                    productOptionService.saveOrUpdate(option);

                    productOption = productOptionService.getByCode(store, attributeName);


                }
                productVariant.setOption(productOption.getId());

                ProductOptionValue optionValue =  productOptionValueService.getByCode(store, value);
                if (optionValue == null){
                    ProductOptionValue productOptionValue = new ProductOptionValue();
                    productOptionValue.setMerchantStore(store);
                    productOptionValue.setCode(value);

                    ProductOptionValueDescription nineDescription = new ProductOptionValueDescription();
                    nineDescription.setLanguage(language);
                    nineDescription.setName(skuAttribute.getValueTrans());
                    nineDescription.setDescription(skuAttribute.getValueTrans());
                    nineDescription.setProductOptionValue(productOptionValue);

                    productOptionValue.getDescriptions().add(nineDescription);

                    //create an option value
                    productOptionValueService.saveOrUpdate(productOptionValue);

                    optionValue = productOptionValueService.getByCode(store, value);
                }
                productVariant.setOptionValue(optionValue.getId());

                productVariant.setCode(attributeName+":"+value);

                Optional<ProductVariation> byCode = productVariationService.getByCode(store, productVariant.getCode());

                Long productVariationId  = null;
                if (byCode.isPresent()){
                    ProductVariation productVariation = byCode.get();
                    productVariationId  = productVariation.getId();
                }else {
                    productVariationId = productVariationFacade.create(productVariant, store, language);
                }
                PersistableVariation persistableVariation = new PersistableVariation();
                persistableVariation.setVariationId(productVariationId);
                persistableVariation.setOptionId(productOption.getId());
                persistableVariation.setOptionValueId(optionValue.getId());
                persistableVariationList.add(persistableVariation);
            }
            persistableProductVariant.setProductVariations(persistableVariationList);
            persistableProductVariant.setSku(UUID.randomUUID().toString().replace("-", ""));
            persistableProductVariant.setInventory(productInventory);
            persistableProductVariant.setSpecId(productSkuInfo.getSpecId());
            variants.add(persistableProductVariant);
        }
        product.setVariants(variants);
        if (CollectionUtils.isEmpty(product.getPriceRangeList())){
            product.setPrice(defaultPrice);
        }
    }

    private Category createCategoryList(Category firstCategory, Category secondCategory, Category thirdCategory, MerchantStore merchantStore) throws ServiceException {
        try {
            // 持久化第一级分类
            Category firstCategoryByFind =  categoryService.getByCode(merchantStore, firstCategory.getCode());
            if (firstCategoryByFind == null){
                categoryService.create(firstCategory);
            }else {
                firstCategory = firstCategoryByFind;
            }

            Category secondCategoryByFind =  categoryService.getByCode(merchantStore, secondCategory.getCode());

            if (secondCategoryByFind == null){
                categoryService.create(secondCategory);
            }else {
                secondCategory = secondCategoryByFind;
            }

            secondCategory.setParent(firstCategory);
            categoryService.create(secondCategory);

            // 设置并持久化第三级分类（如果存在）
            if (thirdCategory != null) {
                thirdCategory.setParent(secondCategory);
                categoryService.create(thirdCategory);
                return thirdCategory;
            }else {
                return secondCategory;
            }

        } catch (NullPointerException e) {
            throw new ServiceException("Error creating category list: a required category is null", e);
        } catch (Exception e) {
            LOGGER.error("create createCategoryList error", e);
            throw new ServiceException("Error creating category list", e);
        }
    }




    /**
     *
     * @param language
     * @return
     */
    Category buildCategory(Long category1688Id, Language languageForEn, Language language, MerchantStore merchantStore) throws ServiceException {
        if (category1688Id == null){
            return null;
        }
        CategoryTranslationGetByIdCategory category1688 =  alCategoryService.getCategoryById(category1688Id, language.getCode());
        CategoryTranslationGetByIdCategory category1688ForEn =  alCategoryService.getCategoryById(category1688Id, "en");
        Category category = new Category();
        category.setMerchantStore(merchantStore);
        category.setCode(category1688Id.toString());
        com.salesmanager.core.model.catalog.category.CategoryDescription shoesDescription = new com.salesmanager.core.model.catalog.category.CategoryDescription();
        shoesDescription.setName(category1688.getTranslatedName());
        shoesDescription.setCategory(category);
        shoesDescription.setLanguage(language);
        com.salesmanager.core.model.catalog.category.CategoryDescription shoesDescription1 = new com.salesmanager.core.model.catalog.category.CategoryDescription();
        shoesDescription1.setName(category1688ForEn.getTranslatedName());
        shoesDescription1.setCategory(category);
        shoesDescription1.setLanguage(languageForEn);
        Set<com.salesmanager.core.model.catalog.category.CategoryDescription> categoryDescriptions = new HashSet<com.salesmanager.core.model.catalog.category.CategoryDescription>();
        categoryDescriptions.add(shoesDescription);
        categoryDescriptions.add(shoesDescription1);
        category.setDescriptions(categoryDescriptions);
        return category;
    }
    private BigDecimal convertToBigDecimal(Double value) {
        return value != null ? BigDecimal.valueOf(value) : BigDecimal.ZERO;
    }


}
