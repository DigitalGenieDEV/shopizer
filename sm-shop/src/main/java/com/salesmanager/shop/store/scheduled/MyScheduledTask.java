package com.salesmanager.shop.store.scheduled;

import com.google.api.client.util.Lists;
import com.salesmanager.core.business.alibaba.fenxiao.crossborder.param.*;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.ProductRepository;
import com.salesmanager.core.business.services.alibaba.product.AlibabaProductService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.store.controller.product.facade.AlibabaProductFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
    private ProductService productService;

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
                try {
                    alibabaProductFacade.importProduct(Collections.singletonList(offerId), "ko", merchantStore, null);
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
            if (pageInfo.getTotalPage() > currentPage + 110) {
                currentPage++;
            } else {
                hasMorePages = false;
            }
        }

        // Log completion
        System.out.println("Processing completed. Total records processed: " + processedRecords);

    }



    public void deleteProductsInParallel() {
        List<Long> listByOutId = productRepository.findListByOutId();

        // Define the number of threads to use
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        for (Long id : listByOutId) {
            executorService.submit(() -> {
                try {
                    Optional<Product> byId = productRepository.findById(id);
                    if (byId.isPresent()) {
                        Product product = byId.get();
                        productService.delete(product);
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // or use a logger
                }
            });
        }

        executorService.shutdown();
        try {
            // Wait for all tasks to complete
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("ExecutorService did not terminate");
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}

