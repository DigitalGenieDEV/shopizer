package com.salesmanager.shop.model.fulfillment;

import lombok.Data;


public class PersistableProductAdditionalService{

    private Integer quantity;


    private Long additionalServiceId;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getAdditionalServiceId() {
        return additionalServiceId;
    }

    public void setAdditionalServiceId(Long additionalServiceId) {
        this.additionalServiceId = additionalServiceId;
    }
}
