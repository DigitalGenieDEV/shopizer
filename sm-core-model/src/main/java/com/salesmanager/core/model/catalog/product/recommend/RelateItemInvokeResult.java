package com.salesmanager.core.model.catalog.product.recommend;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RelateItemInvokeResult {

    @JsonProperty("product_list")
    private List<ProductResult> productList;

    @JsonProperty("cacheid")
    private String cacheid;

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
}
