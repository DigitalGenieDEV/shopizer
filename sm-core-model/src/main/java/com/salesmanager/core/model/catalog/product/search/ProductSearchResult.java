package com.salesmanager.core.model.catalog.product.search;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductSearchResult {

    @JsonProperty("took_time")
    private Integer tookTime;

    @JsonProperty("product_list")
    private List<ProductResult> productList;

    public Integer getTookTime() {
        return tookTime;
    }

    public void setTookTime(Integer tookTime) {
        this.tookTime = tookTime;
    }

    public List<ProductResult> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductResult> productList) {
        this.productList = productList;
    }

    public static class ProductResult {

        @JsonProperty("product_id")
        private String productId;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }
    }
}
