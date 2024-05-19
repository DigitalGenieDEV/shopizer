package com.salesmanager.core.model.catalog.product.search;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.salesmanager.core.model.catalog.product.recommend.ProductResult;

import java.util.List;
import java.util.Map;

public class SearchResult {

    @JsonProperty("took_time")
    private Integer tookTime;

    @JsonProperty("product_list")
    private List<ProductResult> productList;

    @JsonProperty("attr_for_filt")
    private Map<String, List<String>> attrForFilt;

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

    public Map<String, List<String>> getAttrForFilt() {
        return attrForFilt;
    }

    public void setAttrForFilt(Map<String, List<String>> attrForFilt) {
        this.attrForFilt = attrForFilt;
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
