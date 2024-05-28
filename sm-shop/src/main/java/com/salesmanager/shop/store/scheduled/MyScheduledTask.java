package com.salesmanager.shop.store.scheduled;

import com.google.api.client.util.Lists;
import com.salesmanager.core.business.alibaba.fenxiao.crossborder.param.*;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.alibaba.product.AlibabaProductService;
import com.salesmanager.core.business.services.merchant.MerchantStoreService;
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

@Component
public class MyScheduledTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyScheduledTask.class);

    @Autowired
    private AlibabaProductFacade alibabaProductFacade;

    @Autowired
    private AlibabaProductService alibabaProductService;
    @Inject
    protected MerchantStoreService merchantService;


    @Scheduled(cron = "0/5 * * * * *")
    void execute1688ProductImportTask() throws ServiceException {
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

}

