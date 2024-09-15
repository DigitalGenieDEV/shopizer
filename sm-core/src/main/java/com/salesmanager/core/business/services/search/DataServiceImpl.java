//tmpzk
package com.salesmanager.core.business.services.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.business.services.amazonaws.LambdaInvokeService;
import com.salesmanager.core.model.catalog.product.dataservice.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("dataService")
public class DataServiceImpl implements DataService{


    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceImpl.class);

    @Inject
    private LambdaInvokeService lambdaInvokeService;

    private static final String LAMBDA_SR_DATASERVICE_PRODUCT_INFO = "sr_dataservice_product_bi_info_api";
    private static final String LAMBDA_SR_DATASERVICE_MEMBER_INFO = "sr_dataservice_member_bi_info_api";
    private static final String LAMBDA_SR_DATASERVICE_BUYER_INFO = "sr_dataservice_member_buyer_info_api";
    private static final String LAMBDA_SR_DATASERVICE_SALE_INFO = "sr_dataservice_sales_bi_info_api";
    private static final String LAMBDA_SR_DATASERVICE_TRAFFIC_INFO = "sr_dataservice_traffic_bi_info_api";

    @Override
    public BIProductInfoResult biproductinfo(BIProductInfoRequest request) {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String response = "";
        try {
            response = lambdaInvokeService.invoke(LAMBDA_SR_DATASERVICE_PRODUCT_INFO, objectMapper.writeValueAsString(request));
            return objectMapper.readValue(response, BIProductInfoResult.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            LOGGER.error("BI-product-info exception", e);
        }

        return null;
    }

    @Override
    public BIMemberInfoResult bimemberinfo(BIMemberInfoRequest request) {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String response = "";
        try {
            response = lambdaInvokeService.invoke(LAMBDA_SR_DATASERVICE_MEMBER_INFO, objectMapper.writeValueAsString(request));
            return objectMapper.readValue(response, BIMemberInfoResult.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            LOGGER.error("BI-member-info exception", e);
        }

        return null;
    }

    @Override
    public BIBuyerInfoResult bibuyerinfo(BIBuyerInfoRequest request) {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String response = "";
        try {
            response = lambdaInvokeService.invoke(LAMBDA_SR_DATASERVICE_BUYER_INFO, objectMapper.writeValueAsString(request));
            return objectMapper.readValue(response, BIBuyerInfoResult.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            LOGGER.error("BI-buyer-info exception", e);
        }

        return null;
    }

    @Override
    public BISaleInfoResult bisaleinfo(BISaleInfoRequest request) {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String response = "";
        try {
            response = lambdaInvokeService.invoke(LAMBDA_SR_DATASERVICE_SALE_INFO, objectMapper.writeValueAsString(request));
            return objectMapper.readValue(response, BISaleInfoResult.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            LOGGER.error("BI-sale-info exception", e);
        }

        return null;
    }

    @Override
    public BITrafficInfoResult bitrafficinfo(BITrafficInfoRequest request) {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String response = "";
        try {
            response = lambdaInvokeService.invoke(LAMBDA_SR_DATASERVICE_TRAFFIC_INFO, objectMapper.writeValueAsString(request));
            return objectMapper.readValue(response, BITrafficInfoResult.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            LOGGER.error("BI-traffic-info exception", e);
        }

        return null;
    }

}
