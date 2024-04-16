//package com.salesmanager.shop.store.scheduled;
//
//import com.google.api.client.util.Lists;
//import com.salesmanager.core.business.alibaba.fenxiao.crossborder.param.*;
//import com.salesmanager.core.business.services.alibaba.product.AlibabaProductService;
//import com.salesmanager.core.business.services.catalog.category.CategoryService;
//import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionService;
//import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionValueService;
//import com.salesmanager.core.business.services.catalog.product.manufacturer.ManufacturerService;
//import com.salesmanager.core.business.services.merchant.MerchantStoreService;
//import com.salesmanager.core.business.services.reference.language.LanguageService;
//import com.salesmanager.core.model.catalog.category.Category;
//import com.salesmanager.core.model.catalog.category.CategoryDescription;
//import com.salesmanager.core.model.catalog.product.Product;
//import com.salesmanager.core.model.catalog.product.ProductCriteria;
//import com.salesmanager.core.model.catalog.product.attribute.*;
//import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
//import com.salesmanager.core.model.catalog.product.description.ProductDescription;
//import com.salesmanager.core.model.catalog.product.manufacturer.Manufacturer;
//import com.salesmanager.core.model.catalog.product.manufacturer.ManufacturerDescription;
//import com.salesmanager.core.model.merchant.MerchantStore;
//import com.salesmanager.core.model.reference.language.Language;
//import com.salesmanager.shop.model.catalog.product.product.PersistableProduct;
//import com.salesmanager.shop.store.controller.product.facade.ProductCommonFacade;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import javax.inject.Inject;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.empty;
//import static org.hamcrest.Matchers.not;
//import static org.junit.Assert.assertNotNull;
//
//@Component
//public class MyScheduledTask {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(MyScheduledTask.class);
//
//
//    @Autowired
//    private LanguageService languageService;
//
//    @Inject
//    protected MerchantStoreService merchantService;
//
//    @Autowired
//    private CategoryService categoryService;
//
//    @Autowired
//    private ManufacturerService manufacturerService;
//
//    @Autowired
//    private ProductOptionValueService productOptionValueService;
//
//    @Autowired
//    private ProductOptionService productOptionService;
//
//    @Autowired
//    private AlibabaProductService alibabaProductService;
//
//    @Autowired
//    private ProductCommonFacade productCommonFacade;
//
//    @Scheduled(cron = "0 10 14 * * *")
//    public void execute1688ProductImportTask() {
//        ProductSearchKeywordQueryParamOfferQueryParam productSearchKeywordQueryParamOfferQueryParam = new ProductSearchKeywordQueryParamOfferQueryParam();
//        productSearchKeywordQueryParamOfferQueryParam.setPageSize(10);
//        productSearchKeywordQueryParamOfferQueryParam.setCountry("ko");
//        int processedRecords = 0;
//        int totalRecords = 0;
//        int currentPage = 0;
//        boolean hasMorePages = true;
//
//        while (hasMorePages) {
//            productSearchKeywordQueryParamOfferQueryParam.setBeginPage(currentPage);
//            ProductSearchKeywordQueryModelPageInfoV pageInfo = alibabaProductService.searchKeyword(productSearchKeywordQueryParamOfferQueryParam);
//            totalRecords = pageInfo.getTotalRecords();
//            ProductSearchKeywordQueryModelProductInfoModelV[] result = pageInfo.getData();
//
//            for (int i = 0; i < result.length; i++) {
//                ProductSearchKeywordQueryModelProductInfoModelV productSearchKeywordQueryModelProductInfoModelV = result[i];
//                Long offerId = productSearchKeywordQueryModelProductInfoModelV.getOfferId();
//
//                ProductSearchQueryProductDetailParamOfferDetailParam productSearchQueryProductDetailParamOfferDetailParam = new ProductSearchQueryProductDetailParamOfferDetailParam();
//                productSearchQueryProductDetailParamOfferDetailParam.setOfferId(offerId);
//                productSearchQueryProductDetailParamOfferDetailParam.setCountry("ko");
//                ProductSearchQueryProductDetailModelProductDetailModel productSearchQueryProductDetailModelProductDetailModel = alibabaProductService.queryProductDetail(productSearchQueryProductDetailParamOfferDetailParam);
//
//                // Increment the processed records count
//                processedRecords++;
//            }
//
//            // Log the progress
//            System.out.println("Processed " + processedRecords + " out of " + totalRecords + " records.");
//
//            // Check if there are more pages
//            if (pageInfo.getTotalPage() > currentPage + 1) {
//                currentPage++;
//            } else {
//                hasMorePages = false;
//            }
//        }
//
//        // Log completion
//        System.out.println("Processing completed. Total records processed: " + processedRecords);
//
//    }
//
//
//    private void saveProduct(ProductSearchQueryProductDetailModelProductDetailModel productDetailModel) throws Exception {
//
//        Language ko = languageService.getByCode("ko");
//
//        MerchantStore store = merchantService.getByCode(MerchantStore.DEFAULT_STORE);
//
//        PersistableProduct product = new PersistableProduct();
//
//        String categoryName = productDetailModel.getCategoryName();
//
//        List<Category> byName = categoryService.getByName(store, categoryName, ko);
//
//        /**
//         * Category
//         */
//        Category shoes = new Category();
//        shoes.setMerchantStore(store);
//        shoes.setCode("shoes");
//
//        CategoryDescription shoesDescription = new CategoryDescription();
//        shoesDescription.setName("Shoes");
//        shoesDescription.setCategory(shoes);
//        shoesDescription.setLanguage(ko);
//
//        Set<CategoryDescription> descriptions = new HashSet<CategoryDescription>();
//        descriptions.add(shoesDescription);
//
//        shoes.setDescriptions(descriptions);
//
//        categoryService.create(shoes);
//
//
//        List<Category> list = new ArrayList<>();
//        product.setCategories(list);
//        //
//
//        /**
//         * Manufacturer / Brand
//         */
//
//        Manufacturer brown = new Manufacturer();
//        brown.setMerchantStore(store);
//        brown.setCode("brown");
//
//        ManufacturerDescription brownd = new ManufacturerDescription();
//        brownd.setLanguage(en);
//        brownd.setName("Brown's");
//        brownd.setManufacturer(brown);
//        brown.getDescriptions().add(brownd);
//
//        manufacturerService.create(brown);
//        //
//
//
//        // PRODUCT
//
//        // -- non variable informations
//
//        Product summerShoes = new Product();
//        summerShoes.setProductHeight(new BigDecimal(3));
//        summerShoes.setProductLength(new BigDecimal(9));//average
//        summerShoes.setProductWidth(new BigDecimal(4));
//        summerShoes.setSku("BR12345");
//        summerShoes.setManufacturer(brown);
//        summerShoes.setMerchantStore(store);
//
//        //is available
//        summerShoes.setAvailable(true);
//
//        // Product description
//        ProductDescription description = new ProductDescription();
//        description.setName("Summer shoes");
//        description.setLanguage(en);
//        description.setProduct(summerShoes);
//
//        summerShoes.getDescriptions().add(description);
//
//        //add product to shoes category
//        summerShoes.getCategories().add(shoes);
//
//        // -- end non variable informations
//
//        // --- add attributes to the product (size)
//        createOptions(summerShoes);
//
//
//        // --- create attributes (available options)
//        /**
//         * Add options to product
//         * Those are attributes
//         */
//
//        ProductAttribute size_nine = new ProductAttribute();
//        size_nine.setProduct(summerShoes);
//        size_nine.setProductOption(size);
//        size_nine.setAttributeDefault(true);
//        size_nine.setProductAttributePrice(new BigDecimal(0));//no price variation
//        size_nine.setProductAttributeWeight(new BigDecimal(0));//no weight variation
//        size_nine.setProductOptionValue(nine);
//
//        summerShoes.getAttributes().add(size_nine);
//
//
//
//        // ---- variable informations - inventory - variants - prices
//        ProductAvailability availability = createInventory(store, 100, new BigDecimal("99.99"));
//        summerShoes.getAvailabilities().add(availability);
//        // ---- add available sizes
//
//        //DEFAULT (total quantity of 100 distributed)
//
//        //TODO use pre 3.0 variation
//
//        //40 of 9
///*	    ProductVariant size_nine_DEFAULT = new ProductVariant();
//	    size_nine_DEFAULT.setAttribute(size_nine);
//	    size_nine_DEFAULT.setProductQuantity(40);
//	    size_nine_DEFAULT.setProductAvailability(availability);*/
//
//        //availability.getVariants().add(size_nine_DEFAULT);
//
//        //30 of 9.5
///*	    ProductVariant size_nine_half_DEFAULT = new ProductVariant();
//	    size_nine_half_DEFAULT.setAttribute(size_nine_half);
//	    size_nine_half_DEFAULT.setProductQuantity(30);
//	    size_nine_half_DEFAULT.setProductAvailability(availability);*/
//
//        //availability.getVariants().add(size_nine_half_DEFAULT);
//
//        //30 of ten
///*	    ProductVariant size_ten_DEFAULT = new ProductVariant();
//	    size_ten_DEFAULT.setAttribute(size_nine_half);
//	    size_ten_DEFAULT.setProductQuantity(30);
//	    size_ten_DEFAULT.setProductAvailability(availability);*/
//
//        //availability.getVariants().add(size_ten_DEFAULT);
//
//        //inventory for store DEFAULT and product summerShoes
//        availability.setProduct(summerShoes);
//        availability.setMerchantStore(store);
//
//
//        /**
//         * Create product
//         */
//        productService.saveProduct(summerShoes);
//
//        productCommonFacade.saveProduct(store, product, en);
//
//    }
//
//
//    private void createOptions(Product product) throws Exception {
//
//
//        /**
//         * An attribute can be created dynamicaly but the attached Option and Option value need to exist
//         */
//
//        MerchantStore store = product.getMerchantStore();
//        Language en = languageService.getByCode("en");
//
//
//        /**
//         * Create size option
//         */
//        ProductOption size = new ProductOption();
//        size.setMerchantStore(store);
//        size.setCode("SHOESIZE");
//        size.setProductOptionType(ProductOptionType.Radio.name());
//
//        ProductOptionDescription sizeDescription = new ProductOptionDescription();
//        sizeDescription.setLanguage(en);
//        sizeDescription.setName("Size");
//        sizeDescription.setDescription("Show size");
//        sizeDescription.setProductOption(size);
//
//        size.getDescriptions().add(sizeDescription);
//
//        //create option
//        productOptionService.saveOrUpdate(size);
//
//
//        /**
//         * Create size values (9, 9.5, 10)
//         */
//
//        //option value 9
//        ProductOptionValue nine = new ProductOptionValue();
//        nine.setMerchantStore(store);
//        nine.setCode("nine");
//
//        ProductOptionValueDescription nineDescription = new ProductOptionValueDescription();
//        nineDescription.setLanguage(en);
//        nineDescription.setName("9");
//        nineDescription.setDescription("Size 9");
//        nineDescription.setProductOptionValue(nine);
//
//        nine.getDescriptions().add(nineDescription);
//
//        //create an option value
//        productOptionValueService.saveOrUpdate(nine);
//
//
//        //option value 9.5
//        ProductOptionValue nineHalf = new ProductOptionValue();
//        nineHalf.setMerchantStore(store);
//        nineHalf.setCode("nineHalf");
//
//        ProductOptionValueDescription nineHalfDescription = new ProductOptionValueDescription();
//        nineHalfDescription.setLanguage(en);
//        nineHalfDescription.setName("9.5");
//        nineHalfDescription.setDescription("Size 9.5");
//        nineHalfDescription.setProductOptionValue(nineHalf);
//
//        nineHalf.getDescriptions().add(nineHalfDescription);
//
//        //create an option value
//        productOptionValueService.saveOrUpdate(nineHalf);
//
//
//        //option value 10
//        ProductOptionValue ten = new ProductOptionValue();
//        ten.setMerchantStore(store);
//        ten.setCode("ten");
//
//        ProductOptionValueDescription tenDescription = new ProductOptionValueDescription();
//        tenDescription.setLanguage(en);
//        tenDescription.setName("10");
//        tenDescription.setDescription("Size 10");
//        tenDescription.setProductOptionValue(ten);
//
//        ten.getDescriptions().add(tenDescription);
//
//        //create an option value
//        productOptionValueService.saveOrUpdate(ten);
//
//
//        // end options / options values
//
//
//    }
//
//}
//
