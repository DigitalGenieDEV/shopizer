package com.salesmanager.shop.model.fulfillment;

import lombok.Data;

import java.util.List;

@Data
public class ReadableQcInfo extends QcInfo{


    private List<ReadableProductAdditionalService> readableProductAdditionalServices;

    private List<ReadableQcInfoHistory> readableQcInfoHistoryList;

}
