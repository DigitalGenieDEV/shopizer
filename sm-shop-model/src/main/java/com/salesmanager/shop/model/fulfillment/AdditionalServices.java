package com.salesmanager.shop.model.fulfillment;

import lombok.Data;

public class AdditionalServices {


    private Long id;

    private String code;

    private String subCode;

    private String price;

    private Long merchantId;

    private AdditionalServicesDescription description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public AdditionalServicesDescription getDescription() {
        return description;
    }

    public void setDescription(AdditionalServicesDescription description) {
        this.description = description;
    }
}
