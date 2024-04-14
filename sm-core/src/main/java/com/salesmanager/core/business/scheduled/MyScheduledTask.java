package com.salesmanager.core.business.scheduled;

import com.salesmanager.core.business.alibaba.fenxiao.crossborder.param.*;
import com.salesmanager.core.business.services.alibaba.product.AlibabaProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduledTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyScheduledTask.class);




    @Autowired
    private AlibabaProductService alibabaProductService;

    @Scheduled(cron = "0 10 14 * * *")
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



}

