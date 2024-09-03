package com.salesmanager.shop.store.facade.product;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.salesmanager.core.business.alibaba.fenxiao.crossborder.param.*;
import com.salesmanager.core.business.exception.ServiceException;
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
import com.salesmanager.shop.model.catalog.ProductMaterial;
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
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class AlibabaProductFacadeImpl implements AlibabaProductFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlibabaProductFacadeImpl.class);


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

    private static int corePoolSize = 20;  // 核心线程数
    private static int maxPoolSize = 20;   // 最大线程数
    private static int queueCapacity = 100; // 有界队列大小
    private static long keepAliveTime = 60L; // 线程保持活动时间

    private static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(queueCapacity);

    // 使用 CallerRunsPolicy，当队列满时，让提交任务的线程执行任务，避免阻塞
    private static RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

    private static ExecutorService executor = new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            TimeUnit.SECONDS,
            queue,
            handler
    );

    @Override
    public List<Long> importProduct(List<Long> productIds, String language,
                              MerchantStore merchantStore, List<Long> categoryIds, PublishWayEnums importType) throws ServiceException {
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
                        language, merchantStore, categoryIds, importType);

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
    public void adminBatchImportProduct(Long productId, String language, MerchantStore merchantStore, List<Long> categoryIds, PublishWayEnums importType) throws Exception {
        // 创建一个线程池
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // 创建并行任务
        CompletableFuture<ProductSearchQueryProductDetailModelProductDetailModel> futureForEn = CompletableFuture.supplyAsync(() -> {
            try {
                ProductSearchQueryProductDetailParamOfferDetailParam paramForEn = new ProductSearchQueryProductDetailParamOfferDetailParam();
                paramForEn.setOfferId(productId);
                paramForEn.setCountry("en");
                return alibabaProductService.queryProductDetail(paramForEn);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }, executor);

        CompletableFuture<ProductSearchQueryProductDetailModelProductDetailModel> futureForLanguage = CompletableFuture.supplyAsync(() -> {
            try {
                ProductSearchQueryProductDetailParamOfferDetailParam paramForLang = new ProductSearchQueryProductDetailParamOfferDetailParam();
                paramForLang.setOfferId(productId);
                paramForLang.setCountry(language);
                return alibabaProductService.queryProductDetail(paramForLang);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }, executor);

        // 等待两个任务都完成
        CompletableFuture.allOf(futureForEn, futureForLanguage).join();

        // 获取查询结果
        ProductSearchQueryProductDetailModelProductDetailModel productDetailModelForEn = futureForEn.get();
        ProductSearchQueryProductDetailModelProductDetailModel productDetailModelForLanguage = futureForLanguage.get();

        // 保存产品信息
        saveProduct(productDetailModelForEn, productDetailModelForLanguage, language, merchantStore, categoryIds, importType);

        // 关闭线程池
        executor.shutdown();
    }


    // 重试方法
    private ProductSearchQueryProductDetailModelProductDetailModel retrySearch(ProductSearchQueryProductDetailParamOfferDetailParam param) throws ServiceException {
        int retryCount = 0;
        while (retryCount < 999999999) { // 最大重试次数
            try {
                return alibabaProductService.queryProductDetail(param);
            } catch (Exception e) {
//				if ("403".equals(e.getMessage())) {
                //现在所有异常都重试
                try {
                    Thread.sleep(10000); // 等待10秒
                    System.out.println("等待中。。。。。。。");
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ex);
                }
                retryCount++;
//				} else {
//					throw e; // 其他异常不重试
//				}
            }
        }
        throw new RuntimeException("Failed to retrieve data after retries");
    }


    @Override
    public ReadableProductPageInfo searchProductByKeywords(AlibabaProductSearchKeywordQueryParam queryParam) throws ServiceException {
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
                            String language, MerchantStore store, List<Long> categoryIds, PublishWayEnums importType) throws Exception {
        if (productSearchQueryProductDetailModelProductDetailModelForEn.getProductSkuInfos() == null){
            return null;
        }

        PersistableProductDefinition productDefinition = new PersistableProductDefinition();

        PersistableProduct persistableProduct =  new PersistableProduct();

        Language en = languageService.getByCode("en");

        Language ko = languageService.getByCode(language);

        List<com.salesmanager.shop.model.catalog.category.Category> categoryList =  new ArrayList<>();

        if (!CollectionUtils.isEmpty(categoryIds)){

            Long leftCategoryId = categoryIds.get(0);
            Category leftCategory = categoryService.findOneById(leftCategoryId);
            if (leftCategory == null){
                throw new ResourceNotFoundException("leftCategory is null [" + leftCategoryId + "]");
            }
            com.salesmanager.shop.model.catalog.category.Category convertCategory = new  com.salesmanager.shop.model.catalog.category.Category();
            convertCategory.setCode(leftCategory.getCode());
            convertCategory.setId(leftCategory.getId());
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
        for (ProductSearchQueryProductDetailModelProductAttribute attribute : productAttribute) {
            if ("2176".equals(attribute.getAttributeId())) {
                manufacturers = manufacturerService.getByCode(store, attribute.getValue());

                if (manufacturers == null) {
                    manufacturers = new Manufacturer();
                    manufacturers.setMerchantStore(store);
                    manufacturers.setCode(attribute.getValue());

                    ManufacturerDescription manufacturersDescription = new ManufacturerDescription();
                    manufacturersDescription.setLanguage(ko);
                    manufacturersDescription.setName(attribute.getValueTrans());
                    manufacturersDescription.setManufacturer(manufacturers);

                    Set<ManufacturerDescription> descriptions = new HashSet<>();
                    descriptions.add(manufacturersDescription);
                    manufacturers.setDescriptions(descriptions);


                    //todo
                    try {
                        manufacturerService.create(manufacturers);
                    } catch (org.springframework.dao.DataIntegrityViolationException e) {
                        // 如果捕获到唯一性约束异常，可以不处理，继续执行后续逻辑
                        System.out.println("manufacturerService Unique constraint violation occurred, ignoring");
                    }catch (Exception e){
                        LOGGER.error("manufacturerService create error", e );
                    }
                }
                break;  // 找到后立即退出循环，提高效率
            }
        }


        productDefinition.setIdentifier(productDetailModel.getSubjectTrans());
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
        persistableProduct.setSellerOpenId(productDetailModel.getSellerOpenId());
        ProductSearchQueryProductDetailModelProductImage productImage = productDetailModel.getProductImage();
        String[] images = productImage.getImages();
        List<PersistableImage> persistableImages =  new ArrayList<>();
        for (int i = 0;i<images.length;i++){
            String image = images[i];
            PersistableImage persistableImage = new PersistableImage();
            if (i == 0){
                persistableImage.setDefaultImage(true);
            }
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

        if (productDetailModel != null ) {
            if (productDetailModel.getProductShippingInfo() !=null && productDetailModel.getProductShippingInfo().getSkuShippingInfoList() != null&& productDetailModel.getProductShippingInfo().getSkuShippingInfoList().length >0 ){
                ComAlibabaCbuOfferModelSkuShippingInfo[] skuShippingInfoList = productDetailModel.getProductShippingInfo().getSkuShippingInfoList();
                ComAlibabaCbuOfferModelSkuShippingInfo comAlibabaCbuOfferModelSkuShippingInfo = skuShippingInfoList[0];
                ProductSpecification productSpecification = new ProductSpecification();
                productSpecification.setHeight(convertToBigDecimal(comAlibabaCbuOfferModelSkuShippingInfo.getHeight()));
                productSpecification.setLength(convertToBigDecimal(comAlibabaCbuOfferModelSkuShippingInfo.getLength()));
                productSpecification.setWeight(gramsToKilograms(comAlibabaCbuOfferModelSkuShippingInfo.getWeight()));
                productSpecification.setWidth(convertToBigDecimal(comAlibabaCbuOfferModelSkuShippingInfo.getWidth()));
                persistableProduct.setProductSpecifications(productSpecification);
            }else{
                if (productDetailModel.getProductShippingInfo() !=null){
                    ProductSpecification productSpecification = new ProductSpecification();
                    productSpecification.setHeight(convertToBigDecimal(productDetailModel.getProductShippingInfo().getHeight()));
                    productSpecification.setLength(convertToBigDecimal(productDetailModel.getProductShippingInfo().getLength()));
                    productSpecification.setWeight(convertToBigDecimal(productDetailModel.getProductShippingInfo().getWeight()));
                    productSpecification.setWidth(convertToBigDecimal(productDetailModel.getProductShippingInfo().getWidth()));
                    persistableProduct.setProductSpecifications(productSpecification);
                }
            }
        }

        persistableProduct.setMinOrderQuantity(productDetailModel.getMinOrderQuantity());
        createProductVariant(ko, store, productDetailModel, persistableProduct);

        createAttribute(productSearchQueryProductDetailModelProductDetailModelForEn.getProductAttribute(), en, ko, store, productDetailModel.getProductAttribute(),  persistableProduct);
        persistableProduct.setPublishWay(importType == null ? PublishWayEnums.IMPORT_BY_1688.name() : PublishWayEnums.IMPORT_BY_1688.name());
        PersistableAnnouncement persistableAnnouncement = new PersistableAnnouncement();
        persistableAnnouncement.setProductId(productId);


//        PersistableAnnouncement.AnnouncementField annotationField = new PersistableAnnouncement.AnnouncementField();
//        annotationField.setComment("3333");
//        annotationField.setField("222");
//        annotationField.setValue("1111");
//        persistableAnnouncement.setAnnouncementFields(Lists.newArrayList(annotationField));
//        persistableProduct.setAnnouncement(persistableAnnouncement);
//        persistableAnnouncement.setAnnouncementFields();
//        persistableProduct.setAnnouncementAttributes();
        productCommonFacade.saveProduct(store, persistableProduct, ko);
        return productId;
    }


    private void createAttribute(ProductSearchQueryProductDetailModelProductAttribute[] productAttributeForEn, Language en, Language language, MerchantStore store, ProductSearchQueryProductDetailModelProductAttribute[] productAttribute, PersistableProduct product) throws Exception {

        // 创建一个线程池

        // 使用线程安全的Map来存储英文属性
        ConcurrentHashMap<String, ProductSearchQueryProductDetailModelProductAttribute> attributeEnMap = (ConcurrentHashMap<String, ProductSearchQueryProductDetailModelProductAttribute>) Arrays.stream(productAttributeForEn)
                .collect(Collectors.toConcurrentMap(
                        attribute -> attribute.getAttributeId() + attribute.getValue(),  // 使用 attributeId 和 value 组合成键
                        attribute -> attribute
                ));

        // 使用线程安全的集合来存储属性
        CopyOnWriteArrayList<PersistableProductAttribute> attributes = new CopyOnWriteArrayList<>();

        // 并行处理每个属性
        List<CompletableFuture<Void>> futures = Arrays.stream(productAttribute)
                .map(attr -> CompletableFuture.runAsync(() -> {
                        if ("2176".equals(attr.getAttributeId())) {
                            return; // 跳过ID为2176的属性
                        }

                        PersistableProductAttribute persistableProductAttribute = new PersistableProductAttribute();
                        String attributeName = attr.getAttributeName();
                        String value = attr.getValue();

                        Long productOptionId  = productOptionService.getByCode(attributeName);

                        if (productOptionId == null) {
                            ProductOption option = new ProductOption();
                            option.setMerchantStore(store);
                            option.setCode(attributeName);
                            option.setProductOptionType(ProductOptionType.Select.name());

                            ProductOptionDescription sizeDescription = new ProductOptionDescription();
                            sizeDescription.setLanguage(language);
                            sizeDescription.setName(attr.getValueTrans());
                            sizeDescription.setDescription(attr.getValueTrans());
                            sizeDescription.setProductOption(option);

                            ProductSearchQueryProductDetailModelProductAttribute attrEn = attributeEnMap.get(attr.getAttributeId() + attr.getValue());
                            if (attrEn != null) {
                                ProductOptionDescription sizeDescriptionForEn = new ProductOptionDescription();
                                sizeDescriptionForEn.setLanguage(en);
                                sizeDescriptionForEn.setName(attrEn.getValueTrans());
                                sizeDescriptionForEn.setDescription(attrEn.getValueTrans());
                                sizeDescriptionForEn.setProductOption(option);
                                option.getDescriptions().add(sizeDescriptionForEn);
                            }

                            option.getDescriptions().add(sizeDescription);
                            try {
                                productOptionService.saveOrUpdate(option);
                            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                                // 如果捕获到唯一性约束异常，可以不处理，继续执行后续逻辑
                                System.out.println("Unique constraint violation occurred, ignoring");
                            } catch (ServiceException e) {
                                throw new RuntimeException(e);
                            }
                            productOptionId = productOptionService.getByCode(attributeName);
                        }

                        PersistableProductOptionEntity propertyOption = new PersistableProductOptionEntity();
                        propertyOption.setCode(attributeName);
                        propertyOption.setId(productOptionId);
                        persistableProductAttribute.setOption(propertyOption);

                        Long optionValueId = productOptionValueService.getByCode(value);

                        if (optionValueId == null) {
                            ProductOptionValue productOptionValue = new ProductOptionValue();
                            productOptionValue.setMerchantStore(store);
                            productOptionValue.setCode(value);

                            ProductOptionValueDescription valueDescription = new ProductOptionValueDescription();
                            valueDescription.setLanguage(language);
                            valueDescription.setName(attr.getValueTrans());
                            valueDescription.setDescription(attr.getValueTrans());
                            valueDescription.setProductOptionValue(productOptionValue);

                            ProductSearchQueryProductDetailModelProductAttribute attrEn = attributeEnMap.get(attr.getAttributeId() + attr.getValue());
                            if (attrEn != null) {
                                ProductOptionValueDescription valueDescriptionForEn = new ProductOptionValueDescription();
                                valueDescriptionForEn.setLanguage(en);
                                valueDescriptionForEn.setName(attrEn.getValueTrans());
                                valueDescriptionForEn.setDescription(attrEn.getValueTrans());
                                valueDescriptionForEn.setProductOptionValue(productOptionValue);
                                productOptionValue.getDescriptions().add(valueDescriptionForEn);
                            }

                            productOptionValue.getDescriptions().add(valueDescription);
                            try {
                                productOptionValueService.saveOrUpdate(productOptionValue);
                            } catch (org.springframework.dao.DataIntegrityViolationException e) {
                                // 如果捕获到唯一性约束异常，可以不处理，继续执行后续逻辑
                                System.out.println("Unique constraint violation occurred, ignoring");
                            } catch (ServiceException e) {
                                throw new RuntimeException(e);
                            }
                            optionValueId = productOptionValueService.getByCode(value);
                        }

                        PersistableProductOptionValue persistentOptionValue = new PersistableProductOptionValue();
                        persistentOptionValue.setCode(value);
                        persistentOptionValue.setId(optionValueId);
                        persistableProductAttribute.setOptionValue(persistentOptionValue);
                        persistableProductAttribute.setSortOrder(Arrays.asList(productAttribute).indexOf(attr));
                        persistableProductAttribute.setAttributeDisplayOnly(true);
                        attributes.add(persistableProductAttribute);
                }, executor)).collect(Collectors.toList());

        // 等待所有任务完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // 设置产品属性
        product.setProperties(attributes);

    }




    private void createProductVariant(Language language, MerchantStore store,
                                      ProductSearchQueryProductDetailModelProductDetailModel productDetailModel, PersistableProduct product) throws Exception {

        // 使用线程安全的 List 来存储产品变体
        List<PersistableProductVariant> variants = Collections.synchronizedList(new ArrayList<>());

        // 使用 AtomicReference 来确保线程安全的默认价格计算
        AtomicReference<BigDecimal> defaultPrice = new AtomicReference<>(new BigDecimal(Integer.MAX_VALUE));

        ProductSearchQueryProductDetailModelSkuInfo[] productSkuInfos = productDetailModel.getProductSkuInfos();

        // 创建并行任务
        List<Callable<Void>> tasks = new ArrayList<>();

        for (ProductSearchQueryProductDetailModelSkuInfo productSkuInfo : productSkuInfos) {
            tasks.add(() -> {
                PersistableProductVariant persistableProductVariant = new PersistableProductVariant();
                PersistableProductInventory productInventory = new PersistableProductInventory();

                PersistableProductPrice price = new PersistableProductPrice();
                price.setCurrency("CNY");

                if (productDetailModel.getProductSaleInfo().getQuoteType() != null &&
                        productDetailModel.getProductSaleInfo().getQuoteType() == 2) {

                    ProductSearchQueryProductDetailModelPriceRangeV[] priceRangeList = productDetailModel.getProductSaleInfo().getPriceRangeList();
                    List<PriceRange> ranges = new ArrayList<>();
                    for (ProductSearchQueryProductDetailModelPriceRangeV priceRangeV : priceRangeList) {
                        PriceRange priceRange = new PriceRange();
                        priceRange.setPrice(priceRangeV.getPrice());
                        priceRange.setStartQuantity(priceRangeV.getStartQuantity());
                        ranges.add(priceRange);
                    }
                    price.setPriceRangeList(ranges);
                    product.setPriceRangeList(ranges);
                    productInventory.setQuantity(productDetailModel.getProductSaleInfo().getAmountOnSale() ==null ?
                            0: productDetailModel.getProductSaleInfo().getAmountOnSale());
                } else {
                    BigDecimal skuPrice = new BigDecimal(StringUtils.isEmpty(productSkuInfo.getPromotionPrice()) ?
                            productSkuInfo.getPrice() : productSkuInfo.getPromotionPrice());
                    price.setPrice(skuPrice);

                    // 更新默认价格
                    defaultPrice.updateAndGet(currentPrice -> skuPrice.compareTo(currentPrice) < 0 ? skuPrice : currentPrice);

                    productInventory.setQuantity(productDetailModel.getProductSaleInfo().getAmountOnSale() ==null ?
                            0: productDetailModel.getProductSaleInfo().getAmountOnSale());
                }
                productInventory.setPrice(price);

                product.setQuoteType(productDetailModel.getProductSaleInfo().getQuoteType());
                ProductSearchQueryProductDetailModelSkuAttribute[] skuAttributes = productSkuInfo.getSkuAttributes();
                List<PersistableVariation> persistableVariationList = new ArrayList<>();

                for (ProductSearchQueryProductDetailModelSkuAttribute skuAttribute : skuAttributes) {
                    PersistableProductVariation productVariant = new PersistableProductVariation();
                    String attributeName = skuAttribute.getAttributeName();
                    String value = skuAttribute.getValue();

                    // 处理产品选项
                    Long productOptionId = productOptionService.getByCode(attributeName);
                    if (productOptionId == null) {
                        ProductOption option = new ProductOption();
                        option.setMerchantStore(store);
                        option.setCode(attributeName);
                        option.setProductOptionType(ProductOptionType.Select.name());

                        ProductOptionDescription sizeDescription = new ProductOptionDescription();
                        sizeDescription.setLanguage(language);
                        sizeDescription.setName(skuAttribute.getAttributeNameTrans());
                        sizeDescription.setDescription(skuAttribute.getAttributeNameTrans());
                        sizeDescription.setProductOption(option);
                        option.getDescriptions().add(sizeDescription);

                        // 创建选项并捕获唯一性约束异常
                        try {
                            productOptionService.saveOrUpdate(option);
                        } catch (org.springframework.dao.DataIntegrityViolationException e) {
                            System.out.println("Unique constraint violation occurred, ignoring");
                        }

                        productOptionId = productOptionService.getByCode(attributeName);
                    }
                    productVariant.setOption(productOptionId);

                    // 处理产品选项值
                    Long optionValueId = productOptionValueService.getByCode(value);
                    if (optionValueId == null) {
                        ProductOptionValue productOptionValue = new ProductOptionValue();
                        productOptionValue.setMerchantStore(store);
                        productOptionValue.setCode(value);

                        ProductOptionValueDescription nineDescription = new ProductOptionValueDescription();
                        nineDescription.setLanguage(language);
                        nineDescription.setName(skuAttribute.getValueTrans());
                        nineDescription.setDescription(skuAttribute.getValueTrans());
                        nineDescription.setProductOptionValue(productOptionValue);
                        productOptionValue.getDescriptions().add(nineDescription);

                        // 创建选项值并捕获唯一性约束异常
                        try {
                            productOptionValueService.saveOrUpdate(productOptionValue);
                        } catch (org.springframework.dao.DataIntegrityViolationException e) {
                            System.out.println("Unique constraint violation occurred, ignoring");
                        }

                        optionValueId = productOptionValueService.getByCode(value);
                    }
                    productVariant.setOptionValue(optionValueId);
                    productVariant.setCode(attributeName + ":" + value);

                    Optional<ProductVariation> byCode = productVariationService.getByCode(productVariant.getCode());
                    Long productVariationId = null;
                    if (byCode.isPresent()) {
                        ProductVariation productVariation = byCode.get();
                        productVariationId = productVariation.getId();
                    } else {
                        try {
                            productVariationId = productVariationFacade.create(productVariant, store, language);
                        } catch (org.springframework.dao.DataIntegrityViolationException e) {
                            System.out.println("productVariationId Unique constraint violation occurred, ignoring");
                        }
                    }
                    PersistableVariation persistableVariation = new PersistableVariation();
                    persistableVariation.setVariationId(productVariationId);
                    persistableVariation.setOptionId(productOptionId);
                    persistableVariation.setOptionValueId(optionValueId);
                    persistableVariationList.add(persistableVariation);
                }

                persistableProductVariant.setProductVariations(persistableVariationList);
                persistableProductVariant.setSku(UUID.randomUUID().toString().replace("-", ""));
                persistableProductVariant.setInventory(productInventory);
                persistableProductVariant.setSpecId(productSkuInfo.getSpecId());
                variants.add(persistableProductVariant);

                return null;
            });
        }

        // 执行并等待所有任务完成
        executor.invokeAll(tasks);

        // 设置产品变体
        product.setVariants(variants);

        // 设置默认价格
        if (CollectionUtils.isEmpty(product.getPriceRangeList())) {
            product.setPrice(defaultPrice.get());
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


    public static BigDecimal gramsToKilograms(Long grams) {
        if (grams == null) {
            throw new IllegalArgumentException("Input grams value cannot be null");
        }

        // Convert Long to BigDecimal
        BigDecimal gramsBigDecimal = new BigDecimal(grams);

        // Define the conversion factor from grams to kilograms
        BigDecimal conversionFactor = new BigDecimal(1000);

        // Perform the conversion
        return gramsBigDecimal.divide(conversionFactor);
    }

}
