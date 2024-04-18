package com.salesmanager.shop.store.scheduled;

import com.google.api.client.util.Lists;
import com.salesmanager.core.business.alibaba.fenxiao.crossborder.param.*;
import com.salesmanager.core.business.services.alibaba.category.AlibabaCategoryService;
import com.salesmanager.core.business.services.alibaba.product.AlibabaProductService;
import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionValueService;
import com.salesmanager.core.business.services.catalog.product.manufacturer.ManufacturerService;
import com.salesmanager.core.business.services.catalog.product.variation.ProductVariationService;
import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategoryDescription;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.ProductCriteria;
import com.salesmanager.core.model.catalog.product.attribute.*;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer;
import com.salesmanager.core.model.catalog.product.manufacturer.ManufacturerDescription;
import com.salesmanager.core.model.catalog.product.variation.ProductVariation;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.PersistableImage;
import com.salesmanager.shop.model.catalog.product.PersistableProductPrice;
import com.salesmanager.shop.model.catalog.product.ProductDescription;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductAttribute;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductOptionValue;
import com.salesmanager.shop.model.catalog.product.attribute.ProductPropertyOption;
import com.salesmanager.shop.model.catalog.product.product.PersistableProduct;
import com.salesmanager.shop.model.catalog.product.product.PersistableProductInventory;
import com.salesmanager.shop.model.catalog.product.product.definition.PriceRange;
import com.salesmanager.shop.model.catalog.product.product.variant.PersistableProductVariant;
import com.salesmanager.shop.model.catalog.product.variation.PersistableProductVariation;
import com.salesmanager.shop.model.catalog.product.variation.ReadableProductVariation;
import com.salesmanager.shop.store.controller.product.facade.ProductCommonFacade;
import com.salesmanager.shop.store.controller.product.facade.ProductVariationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.salesmanager.shop.store.controller.ControllerConstants.Tiles.Product.product;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;

@Component
public class MyScheduledTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyScheduledTask.class);


    @Autowired
    private LanguageService languageService;

    @Inject
    protected MerchantStoreService merchantService;

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


//    @Scheduled(cron = "*/5 * * * * *") // 每隔五秒执行一次
    public void execute1688ProductImportTask() {
        ProductSearchKeywordQueryParamOfferQueryParam productSearchKeywordQueryParamOfferQueryParam = new ProductSearchKeywordQueryParamOfferQueryParam();
        productSearchKeywordQueryParamOfferQueryParam.setPageSize(10);
        productSearchKeywordQueryParamOfferQueryParam.setCountry("ko");
        int processedRecords = 0;
        int totalRecords = 0;
        int currentPage = 0;
        boolean hasMorePages = true;

        while (hasMorePages) {
            productSearchKeywordQueryParamOfferQueryParam.setBeginPage(currentPage);
            ProductSearchKeywordQueryModelPageInfoV pageInfo = alibabaProductService.searchKeyword(productSearchKeywordQueryParamOfferQueryParam);
            totalRecords = pageInfo.getTotalRecords();
            ProductSearchKeywordQueryModelProductInfoModelV[] result = pageInfo.getData();

            for (int i = 0; i < result.length; i++) {
                ProductSearchKeywordQueryModelProductInfoModelV productSearchKeywordQueryModelProductInfoModelV = result[i];
                Long offerId = productSearchKeywordQueryModelProductInfoModelV.getOfferId();

                ProductSearchQueryProductDetailParamOfferDetailParam productSearchQueryProductDetailParamOfferDetailParam = new ProductSearchQueryProductDetailParamOfferDetailParam();
                productSearchQueryProductDetailParamOfferDetailParam.setOfferId(offerId);
                productSearchQueryProductDetailParamOfferDetailParam.setCountry("ko");
                ProductSearchQueryProductDetailModelProductDetailModel productSearchQueryProductDetailModelProductDetailModel = alibabaProductService.queryProductDetail(productSearchQueryProductDetailParamOfferDetailParam);
                try {
                    saveProduct(productSearchQueryProductDetailModelProductDetailModel);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                // Increment the processed records count
                processedRecords++;
            }

            // Log the progress
            System.out.println("Processed " + processedRecords + " out of " + totalRecords + " records.");

            // Check if there are more pages
            if (pageInfo.getTotalPage() > currentPage + 1) {
                currentPage++;
            } else {
                hasMorePages = false;
            }
        }

        // Log completion
        System.out.println("Processing completed. Total records processed: " + processedRecords);

    }


    private void saveProduct(ProductSearchQueryProductDetailModelProductDetailModel productDetailModel) throws Exception {

        Language ko = languageService.getByCode("ko");

        MerchantStore store = merchantService.getByCode(MerchantStore.DEFAULT_STORE);

        PersistableProduct product = new PersistableProduct();

        Long categoryId =   productDetailModel.getCategoryId();

        CategoryTranslationGetByIdCategory category1688 =  alCategoryService.getCategoryById(categoryId, "ko");

        Category category =  categoryService.getByCode(store, category1688.getChineseName());
        List<com.salesmanager.shop.model.catalog.category.Category> list = new ArrayList<>();

        com.salesmanager.shop.model.catalog.category.Category category1 =  new com.salesmanager.shop.model.catalog.category.Category();
        category1.setCode(category1688.getChineseName());
        com.salesmanager.shop.model.catalog.category.CategoryDescription categoryDescription = new com.salesmanager.shop.model.catalog.category.CategoryDescription();
        categoryDescription.setName(category1688.getTranslatedName());
        categoryDescription.setLanguage("ko");
        Set<com.salesmanager.shop.model.catalog.category.CategoryDescription> descriptions =
                new HashSet<com.salesmanager.shop.model.catalog.category.CategoryDescription>();
        descriptions.add(categoryDescription);
        category1.setDescription(categoryDescription);
        list.add(category1);

        if (category == null){
            category = new Category();
            category.setMerchantStore(store);
            category.setCode(category1688.getChineseName());

            CategoryDescription shoesDescription = new CategoryDescription();
            shoesDescription.setName(category1688.getTranslatedName());
            shoesDescription.setCategory(category);
            shoesDescription.setLanguage(ko);
            Set<CategoryDescription> categoryDescriptions = new HashSet<CategoryDescription>();
            categoryDescriptions.add(shoesDescription);
            category.setDescriptions(categoryDescriptions);
            categoryService.create(category);
        }

        product.setCategories(list);


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

        createAttribute(ko, store, productDetailModel.getProductAttribute(),  product);

        product.setAvailable(true);

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
        product.setImages(persistableImages);

        com.salesmanager.shop.model.catalog.product.ProductDescription description = new ProductDescription();
        description.setLanguage("ko");
        description.setName(productDetailModel.getSubject());
        description.setTitle(productDetailModel.getSubjectTrans());
        description.setDescription(productDetailModel.getDescription());
        product.getDescriptions().add(description);

        product.setOutProductId(productDetailModel.getOfferId());
        if (productDetailModel.getSellerMixSetting()!=null && productDetailModel.getSellerMixSetting().getGeneralHunpi()){
            product.setMixNumber(productDetailModel.getSellerMixSetting().getMixNumber());
            product.setMixAmount(productDetailModel.getSellerMixSetting().getMixAmount());
        }
        product.setMinOrderQuantity(productDetailModel.getMinOrderQuantity());
        createProductVariant(ko, store, productDetailModel, product);

        productCommonFacade.saveProduct(store, product, ko);

    }


    private void createAttribute(Language language, MerchantStore store, ProductSearchQueryProductDetailModelProductAttribute[] productAttribute, PersistableProduct product) throws Exception {

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

                ProductPropertyOption propertyOption = new ProductPropertyOption();
                propertyOption.setCode(attributeName);
                persistableProductAttribute.setOption(propertyOption);
            }

            ProductOptionValue optionValue =  productOptionValueService.getByCode(store, value);
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

                PersistableProductOptionValue persistentOptionValue = new PersistableProductOptionValue();
                persistentOptionValue.setCode(value);
                persistableProductAttribute.setOptionValue(persistentOptionValue);
            }
            persistableProductAttribute.setSortOrder(i);
            attributes.add(persistableProductAttribute);
        }
        product.setAttributes(attributes);

    }



    private void createProductVariant(Language language, MerchantStore store,ProductSearchQueryProductDetailModelProductDetailModel productDetailModel, PersistableProduct product) throws Exception {

        List<PersistableProductVariant> variants = new ArrayList<PersistableProductVariant>();


        ProductSearchQueryProductDetailModelSkuInfo[] productSkuInfos = productDetailModel.getProductSkuInfos();
        for (int i=0; i<productSkuInfos.length; i++){
            ProductSearchQueryProductDetailModelSkuInfo productSkuInfo = productSkuInfos[i];

            //开始构建 PersistableProductVariant
            PersistableProductVariant persistableProductVariant = new PersistableProductVariant();
            PersistableProductInventory productInventory = new PersistableProductInventory();

            PersistableProductPrice price = new PersistableProductPrice();

            //阶梯供货价
            if (productDetailModel.getProductSaleInfo().getQuoteType() != null && productDetailModel.getProductSaleInfo().getQuoteType() == 2){
                ProductSearchQueryProductDetailModelPriceRangeV[] priceRangeList = productDetailModel.getProductSaleInfo().getPriceRangeList();
                List<PriceRange> ranges = new ArrayList<>();
                for(int p =0;p<priceRangeList.length;p++){
                    ProductSearchQueryProductDetailModelPriceRangeV productSearchQueryProductDetailModelPriceRangeV = priceRangeList[i];
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

                    //创建 PersistableProductVariant

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

            persistableProductVariant.setInventory(productInventory);
            variants.add(persistableProductVariant);
        }



        product.setVariants(variants);

    }

}

