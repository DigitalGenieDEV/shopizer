package com.salesmanager.core.model.catalog.product.search;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.salesmanager.core.model.catalog.product.recommend.ProductResult;

import java.util.List;
import java.util.Map;

public class SearchResult {

    @JsonProperty("took_time")
    private Integer tookTime;

    @JsonProperty("hit_number")
    private Integer hitNumber;

    @JsonProperty("product_list")
    private List<ProductResult> productList;

    @JsonProperty("filter_options")
    private Map<String, List<String>> filterOptions;

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

    public Map<String, List<String>> getFilterOptions() {
        return filterOptions;
    }

    public void setFilterOptions(Map<String, List<String>> filterOptions) {
        this.filterOptions = filterOptions;
    }

    public Integer getHitNumber() {
        return hitNumber;
    }

    public void setHitNumber(Integer hitNumber) {
        this.hitNumber = hitNumber;
    }

    //    public static class ProductResult {
//
//        @JsonProperty("product_id")
//        private String productId;
//
//        public String getProductId() {
//            return productId;
//        }
//
//        public void setProductId(String productId) {
//            this.productId = productId;
//        }
//    }
}
