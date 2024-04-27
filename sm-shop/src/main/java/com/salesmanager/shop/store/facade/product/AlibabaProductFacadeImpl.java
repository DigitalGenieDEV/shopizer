package com.salesmanager.shop.store.facade.product;

import com.alibaba.fastjson.JSON;
import com.salesmanager.core.business.alibaba.fenxiao.crossborder.param.*;
import com.salesmanager.core.business.exception.ServiceException;
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
import com.salesmanager.core.business.utils.ObjectConvert;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.attribute.*;
import com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer;
import com.salesmanager.core.model.catalog.product.manufacturer.ManufacturerDescription;
import com.salesmanager.core.model.catalog.product.variation.ProductVariation;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.category.CategoryDescription;
import com.salesmanager.shop.model.catalog.product.PersistableImage;
import com.salesmanager.shop.model.catalog.product.PersistableProductPrice;
import com.salesmanager.shop.model.catalog.product.ProductDescription;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductAttribute;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductOptionValue;
import com.salesmanager.shop.model.catalog.product.attribute.ProductPropertyOption;
import com.salesmanager.shop.model.catalog.product.product.PersistableProduct;
import com.salesmanager.shop.model.catalog.product.product.PersistableProductInventory;
import com.salesmanager.shop.model.catalog.product.product.alibaba.AlibabaProductSearchKeywordQueryParam;
import com.salesmanager.shop.model.catalog.product.product.alibaba.AlibabaProductSearchQueryModelProduct;
import com.salesmanager.shop.model.catalog.product.product.alibaba.ReadableAlibabaProductSearchQueryPriceInfo;
import com.salesmanager.shop.model.catalog.product.product.alibaba.ReadableProductPageInfo;
import com.salesmanager.shop.model.catalog.product.product.definition.PersistableProductDefinition;
import com.salesmanager.shop.model.catalog.product.product.definition.PriceRange;
import com.salesmanager.shop.model.catalog.product.product.variant.PersistableProductVariant;
import com.salesmanager.shop.model.catalog.product.variation.PersistableProductVariation;
import com.salesmanager.shop.store.controller.product.facade.AlibabaProductFacade;
import com.salesmanager.shop.store.controller.product.facade.ProductCommonFacade;
import com.salesmanager.shop.store.controller.product.facade.ProductDefinitionFacade;
import com.salesmanager.shop.store.controller.product.facade.ProductVariationFacade;
import com.salesmanager.shop.store.scheduled.MyScheduledTask;
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
    public void importProduct(List<Long> productIds, String language,
                              MerchantStore merchantStore, List<Long> categoryIds) throws ServiceException {
        if (CollectionUtils.isEmpty(productIds)){
            throw new ServiceException(ServiceException.EXCEPTION_VALIDATION,
                    "Invalid date format","validaion.param.error");
        }
        productIds.forEach(productId ->{
            ProductSearchQueryProductDetailParamOfferDetailParam productSearchQueryProductDetailParamOfferDetailParam = new ProductSearchQueryProductDetailParamOfferDetailParam();
            productSearchQueryProductDetailParamOfferDetailParam.setOfferId(productId);
            productSearchQueryProductDetailParamOfferDetailParam.setCountry(language);
            ProductSearchQueryProductDetailModelProductDetailModel productSearchQueryProductDetailModelProductDetailModel = alibabaProductService.queryProductDetail(productSearchQueryProductDetailParamOfferDetailParam);
            try {
                saveProduct(productSearchQueryProductDetailModelProductDetailModel,
                        language, merchantStore, categoryIds);
            } catch (Exception e) {
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++："
                        +productSearchQueryProductDetailModelProductDetailModel.getOfferId());
                LOGGER.error("import product error", e);
            }
        });

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

    @Transactional
    public void saveProduct(ProductSearchQueryProductDetailModelProductDetailModel productDetailModel,
                            String language, MerchantStore store, List<Long> categoryIds) throws Exception {
        PersistableProductDefinition productDefinition = new PersistableProductDefinition();

        Language ko = languageService.getByCode(language);

        List<com.salesmanager.shop.model.catalog.category.Category> categoryList =  new ArrayList<>();

        if (!CollectionUtils.isEmpty(categoryIds)){
            categoryIds.stream().map(categoryId->{
                Category category = null;
                try {
                    category = categoryService.getById(store, categoryId);
                } catch (ServiceException e) {
                    throw new RuntimeException(e);
                }
                com.salesmanager.shop.model.catalog.category.Category convertCategory = ObjectConvert.convert(category, com.salesmanager.shop.model.catalog.category.Category.class);
                convertCategory.setDescription(ObjectConvert.convert(category.getDescription(), CategoryDescription.class));
                return convertCategory;
            }).collect(Collectors.toList());
        }else{
            Long categoryId =   productDetailModel.getCategoryId();
            CategoryTranslationGetByIdCategory category1688 =  alCategoryService.getCategoryById(categoryId, language);

            Category category =  categoryService.getByCode(store, category1688.getChineseName());

            if (category == null){
                createCategory(category1688, ko, store);
            }

        }

        productDefinition.setCategories(categoryList);

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
        productDefinition.setManufacturer(manufacturers.getCode());
        productDefinition.setOutProductId(productDetailModel.getOfferId());
        Product productByOutId = productService.findByOutId(productDetailModel.getOfferId());

        Long productId = null;
        if (productByOutId == null){
            createAttribute(ko, store, productDetailModel.getProductAttribute(),  productDefinition);
            productId = productDefinitionFacade.saveProductDefinition(store, productDefinition, ko);
        }else {
            productId = productByOutId.getId();
//            productDefinitionFacade.update(productId, productDefinition, store, ko);
        }

        PersistableProduct persistableProduct =  new PersistableProduct();
        persistableProduct.setId(productId);
        persistableProduct.setAvailable(true);

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
        description.setName(productDetailModel.getSubject());
        description.setTitle(productDetailModel.getSubjectTrans());
        description.setDescription(productDetailModel.getDescription());
        persistableProduct.getDescriptions().add(description);

        persistableProduct.setOutProductId(productDetailModel.getOfferId());
        if (productDetailModel.getSellerMixSetting()!=null && productDetailModel.getSellerMixSetting().getGeneralHunpi()){
            persistableProduct.setMixNumber(productDetailModel.getSellerMixSetting().getMixNumber());
            persistableProduct.setMixAmount(productDetailModel.getSellerMixSetting().getMixAmount());
        }
        persistableProduct.setMinOrderQuantity(productDetailModel.getMinOrderQuantity());
        createProductVariant(ko, store, productDetailModel, persistableProduct);

        String jsonString = JSON.toJSONString(persistableProduct);

        System.out.println(jsonString);
        productCommonFacade.saveProduct(store, persistableProduct, ko);

        Thread.sleep(1000);
    }


    private void createAttribute(Language language, MerchantStore store, ProductSearchQueryProductDetailModelProductAttribute[] productAttribute, PersistableProductDefinition product) throws Exception {

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
                sizeDescription.setName(productSearchQueryProductDetailModelProductAttribute.getAttributeNameTrans());
                sizeDescription.setDescription(productSearchQueryProductDetailModelProductAttribute.getAttributeNameTrans());
                sizeDescription.setProductOption(option);

                option.getDescriptions().add(sizeDescription);

                //create option
                productOptionService.saveOrUpdate(option);
                ProductOption byCode = productOptionService.getByCode(store, attributeName);
                productOptionId = byCode.getId();

            }
            ProductPropertyOption propertyOption = new ProductPropertyOption();
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
            attributes.add(persistableProductAttribute);
        }
        product.setProperties(attributes);

    }



    private void createProductVariant(Language language, MerchantStore store,ProductSearchQueryProductDetailModelProductDetailModel productDetailModel, PersistableProduct product) throws Exception {

        List<PersistableProductVariant> variants = new ArrayList<PersistableProductVariant>();


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
                    priceRange.setPrice(productSearchQueryProductDetailModelPriceRangeV.getPrice());
                    priceRange.setStartQuantity(productSearchQueryProductDetailModelPriceRangeV.getStartQuantity());
                    ranges.add(priceRange);
                }
                price.setPriceRangeList(ranges);
                productInventory.setQuantity(productDetailModel.getProductSaleInfo().getAmountOnSale());
            }else {
                price.setPrice(new BigDecimal(productSkuInfo.getPrice()));
            }
            product.setQuoteType(productDetailModel.getProductSaleInfo().getQuoteType());

            ProductSearchQueryProductDetailModelSkuAttribute[] skuAttributes = productSkuInfo.getSkuAttributes();



            productInventory.setPrice(price);
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
                persistableProductVariant.setVariation(productVariationId);
            }

            persistableProductVariant.setSku(UUID.randomUUID().toString());
            persistableProductVariant.setInventory(productInventory);
            variants.add(persistableProductVariant);
        }



        product.setVariants(variants);

    }

    /**
     *
     * @param category1688
     * @param language
     * @return
     */
    void createCategory(CategoryTranslationGetByIdCategory category1688, Language language, MerchantStore merchantStore) throws ServiceException {
        Category category = new Category();
        category.setMerchantStore(merchantStore);
        category.setCode(category1688.getChineseName());

        com.salesmanager.core.model.catalog.category.CategoryDescription shoesDescription = new com.salesmanager.core.model.catalog.category.CategoryDescription();
        shoesDescription.setName(category1688.getTranslatedName());
        shoesDescription.setCategory(category);
        shoesDescription.setLanguage(language);
        Set<com.salesmanager.core.model.catalog.category.CategoryDescription> categoryDescriptions = new HashSet<com.salesmanager.core.model.catalog.category.CategoryDescription>();
        categoryDescriptions.add(shoesDescription);
        category.setDescriptions(categoryDescriptions);
        categoryService.create(category);
    }


}
