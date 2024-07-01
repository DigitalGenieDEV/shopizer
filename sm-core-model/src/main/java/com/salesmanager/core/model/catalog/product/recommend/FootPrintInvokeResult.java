package com.salesmanager.core.model.catalog.product.recommend;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FootPrintInvokeResult {

    @JsonProperty("product_list")
    private List<ProductResult> productList;

    @JsonProperty("cacheid")
    private String cacheid;

    @JsonProperty("hit_number")
    private Integer hitNumber;

    public List<ProductResult> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductResult> productList) {
        this.productList = productList;
    }

    public String getCacheid() {
        return cacheid;
    }

    public void setCacheid(String cacheid) {
        this.cacheid = cacheid;
    }

    public Integer getHitNumber() {
        return hitNumber;
    }

    public void setHitNumber(Integer hitNumber) {
        this.hitNumber = hitNumber;
    }
}
