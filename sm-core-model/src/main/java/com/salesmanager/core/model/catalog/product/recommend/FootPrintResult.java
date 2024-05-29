package com.salesmanager.core.model.catalog.product.recommend;

import com.salesmanager.core.model.catalog.product.Product;

import java.util.ArrayList;
import java.util.List;

public class FootPrintResult {

    private List<Product> productList = new ArrayList<>();

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
