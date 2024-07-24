package com.salesmanager.core.model.catalog.product;

import lombok.Data;

@Data
public class SellerProductShippingTextInfo {

    private String deliveryRange;

    private String expressFee;

    private String deliveryDay;

    private String exchangeReturnPeriod;

    private String exchangeReturnMethod;

    private String exchangeReturnPossibleCase;

    private Long id;
}
