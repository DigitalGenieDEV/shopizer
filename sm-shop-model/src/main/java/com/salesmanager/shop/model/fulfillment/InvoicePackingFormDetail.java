package com.salesmanager.shop.model.fulfillment;

import lombok.Data;


@Data
public class InvoicePackingFormDetail {


    private Long id;

    private String numbers;

    private String description;

    private String cartonQty;

    private String unitQty;

    private String unit;

    private String unitPrice;

    private String netWeight;

    private String grossWeight;

    private String cbm;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
