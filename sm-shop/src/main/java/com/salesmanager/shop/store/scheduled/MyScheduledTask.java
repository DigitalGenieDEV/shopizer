package com.salesmanager.shop.store.scheduled;

import com.alibaba.fastjson.JSON;
import com.google.api.client.util.Lists;
import com.salesmanager.core.business.alibaba.fenxiao.crossborder.param.*;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.ProductRepository;
import com.salesmanager.core.business.repositories.catalog.product.attribute.ProductAnnouncementAttributeRepository;
import com.salesmanager.core.business.repositories.catalog.product.attribute.ProductAttributeRepository;
import com.salesmanager.core.business.repositories.catalog.product.feature.ProductFeatureRepository;
import com.salesmanager.core.business.services.alibaba.product.AlibabaProductService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductAttributeService;
import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.PublishWayEnums;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.store.controller.product.facade.AlibabaProductFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class MyScheduledTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyScheduledTask.class);

    @Autowired
    private AlibabaProductFacade alibabaProductFacade;

    @Autowired
    private AlibabaProductService alibabaProductService;
    @Inject
    protected MerchantStoreService merchantService;

    @Autowired
    private ProductFeatureRepository productFeatureRepository;

    @Autowired
    private ProductAnnouncementAttributeRepository productAnnouncementAttributeRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private ProductRepository productRepository;

//    @Scheduled(cron = "0/5 * * * * *")
    void execute1688ProductImportTask() throws ServiceException {
//        deleteProductsInParallel();

        ProductSearchKeywordQueryParamOfferQueryParam productSearchKeywordQueryParamOfferQueryParam = new ProductSearchKeywordQueryParamOfferQueryParam();
        productSearchKeywordQueryParamOfferQueryParam.setPageSize(10);
        productSearchKeywordQueryParamOfferQueryParam.setCountry("ko");
        int processedRecords = 0;
        int totalRecords = 0;
        int currentPage = 0;
        boolean hasMorePages = true;

        MerchantStore merchantStore = merchantService.getByCode("DEFAULT");

        while (hasMorePages) {
            productSearchKeywordQueryParamOfferQueryParam.setBeginPage(currentPage);
            ProductSearchKeywordQueryModelPageInfoV pageInfo = alibabaProductService.searchKeyword(productSearchKeywordQueryParamOfferQueryParam);
            totalRecords = pageInfo.getTotalRecords();
            ProductSearchKeywordQueryModelProductInfoModelV[] result = pageInfo.getData();

            for (int i = 0; i < result.length; i++) {
                ProductSearchKeywordQueryModelProductInfoModelV productSearchKeywordQueryModelProductInfoModelV = result[i];
                Long offerId = productSearchKeywordQueryModelProductInfoModelV.getOfferId();
//                offerId = 705621031065L;
                try {
                    alibabaProductFacade.importProduct(Collections.singletonList(offerId), "ko", merchantStore,
                            null, PublishWayEnums.BATCH_IMPORT_BY_1688);
                } catch (Exception e) {
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++："
                            +offerId);
                    LOGGER.error("import product error", e);
                }
                // Increment the processed records count
                processedRecords++;
            }

            // Log the progress
            System.out.println("Processed " + processedRecords + " out of " + totalRecords + " records.");

            // Check if there are more pages
            if (pageInfo.getTotalPage() > currentPage) {
                currentPage++;
            } else {
                hasMorePages = false;
            }
        }

        System.out.println("Processing completed. Total records processed: " + processedRecords);

    }


    @Transactional
    public void deleteProductsInParallel() throws ServiceException {
        List<Long> listByOutId = productRepository.findListByOutId();
        for (Long id : listByOutId) {
            Optional<Product> byId = productRepository.findById(id);
            if (byId.isPresent()) {
                Product product = byId.get();
                productAnnouncementAttributeRepository.deleteByProductId(product.getId());

                List<ProductAttribute> attributes = productAttributeRepository.findByProductId(product.getId());

                List<Long> collect = attributes.stream().map(ProductAttribute::getId).collect(Collectors.toList());

                productAttributeRepository.deleteProductAttributesByIds(collect);

                productFeatureRepository.deleteByProductId(product.getId());

                productService.delete(product);
            }
        }
    }

}

