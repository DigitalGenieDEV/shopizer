package com.salesmanager.shop.model.fulfillment;

import lombok.Data;

import java.util.List;

@Data
public class ReadableAdditionalServices {


    private Long id;

    private String code;

    private String subCode;

    private String price;


    private Long merchantId;

    private AdditionalServicesDescription description;

    private List<ReadableAdditionalServices> SubReadableAdditionalServices;
}
