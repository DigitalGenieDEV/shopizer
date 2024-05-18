package com.salesmanager.core.model.catalog.product.search;

import com.salesmanager.core.model.catalog.product.Product;

import java.util.List;
import java.util.Map;

public class SearchProductResult {

    private List<Product> productList;

    private Map<String, List<String>> attrForFilt;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Map<String, List<String>> getAttrForFilt() {
        return attrForFilt;
    }

    public void setAttrForFilt(Map<String, List<String>> attrForFilt) {
        this.attrForFilt = attrForFilt;
    }
}
