package com.salesmanager.core.model.catalog.product.recommend;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RelateItemResult {

    @JsonProperty("product_list")
    private List<ProductResult> productList;

    public List<ProductResult> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductResult> productList) {
        this.productList = productList;
    }
}
