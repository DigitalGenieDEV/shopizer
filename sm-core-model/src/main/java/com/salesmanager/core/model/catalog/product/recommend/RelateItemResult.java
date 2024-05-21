package com.salesmanager.core.model.catalog.product.recommend;

import com.salesmanager.core.model.catalog.product.Product;

import java.util.List;

public class RelateItemResult {

    private List<Product> productList;

    private String cacheid;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getCacheid() {
        return cacheid;
    }

    public void setCacheid(String cacheid) {
        this.cacheid = cacheid;
    }
}
